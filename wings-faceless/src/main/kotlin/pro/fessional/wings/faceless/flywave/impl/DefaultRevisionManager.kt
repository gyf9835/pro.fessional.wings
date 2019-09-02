package pro.fessional.wings.faceless.flywave.impl

import org.slf4j.LoggerFactory
import pro.fessional.wings.faceless.flywave.FlywaveDataSources
import pro.fessional.wings.faceless.flywave.SchemaDefinitionLoader
import pro.fessional.wings.faceless.flywave.SchemaRevisionManager
import pro.fessional.wings.faceless.flywave.SchemaRevisionManager.Companion.INIT1ST_REVISION
import pro.fessional.wings.faceless.flywave.SqlSegmentProcessor
import pro.fessional.wings.faceless.flywave.SqlSegmentProcessor.Companion.TYPE_PLAIN
import pro.fessional.wings.faceless.flywave.SqlStatementParser
import pro.fessional.wings.faceless.flywave.util.SimpleJdbcTemplate
import java.util.LinkedList
import java.util.SortedMap
import java.util.concurrent.atomic.AtomicLong
import javax.sql.DataSource


/**
 * 根据数据库中表（含分表）的名字，进行版本管理。
 *
 * @author trydofor
 * @since 2019-06-05
 */
class DefaultRevisionManager(
        private val flywaveDataSources: FlywaveDataSources,
        private val sqlStatementParser: SqlStatementParser,
        private val sqlSegmentProcessor: SqlSegmentProcessor,
        private val schemaDefinitionLoader: SchemaDefinitionLoader) : SchemaRevisionManager {

    private val logger = LoggerFactory.getLogger(DefaultRevisionManager::class.java)

    override fun currentRevision() = flywaveDataSources.plains().map {
        val tmpl = SimpleJdbcTemplate(it.value, it.key)
        val revi = getRevision(tmpl)
        it.key to revi
    }.toMap()


    override fun publishRevision(revision: Long, commitId: Long) {
        if (revision < INIT1ST_REVISION) {
            logger.warn("skip the revision less than {}", INIT1ST_REVISION)
            return
        }
        val selectUpto = """
                SELECT
                    revision,
                    upto_sql,
                    apply_dt
                FROM sys_schema_version
                WHERE revision > ?
                    AND revision <= ?
                ORDER BY revision ASC
                """.trimIndent()
        val selectUndo = """
                SELECT
                    revision,
                    undo_sql,
                    apply_dt
                FROM sys_schema_version
                WHERE revision <= ?
                    AND revision >= ?
                ORDER BY revision DESC
                """.trimIndent()

        val shardTmpl = flywaveDataSources.shard?.let { SimpleJdbcTemplate(it, "sharding") }
        for ((plainName, plainDs) in flywaveDataSources.plains()) {
            val plainTmpl = SimpleJdbcTemplate(plainDs, plainName)
            val plainRevi = getRevision(plainTmpl)

            if (plainRevi < 0) {
                logger.warn("skip a bad version, db-revi={}, to-revi={}, db={}", plainRevi, revision, plainName)
                continue
            }
            if (plainRevi == revision) {
                logger.warn("skip the same version, db-revi={}, to-revi={}, db={}", plainRevi, revision, plainName)
                continue
            }

            val reviQuery: String
            val isUptoSql = revision > plainRevi
            if (isUptoSql) { // 升级
                logger.info("upgrade, db-revi={}, to-revi={}, db={}", plainRevi, revision, plainName)
                reviQuery = selectUpto
            } else { // 降级
                logger.info("downgrade, db-revi={}, to-revi={}", plainRevi, revision)
                reviQuery = selectUndo
            }

            val reviText = LinkedList<Triple<Long, String, String>>()

            plainTmpl.query(reviQuery, plainRevi, revision) {
                val tpl = Triple(it.getLong(1), it.getString(2), it.getString(3))
                reviText.add(tpl)
            }

            if (reviText.isEmpty()) {
                logger.warn("skip the empty revision-sqls, name={}, db-revi={}, to-revi={}", plainName, plainRevi, revision)
                continue
            }

            // 检测和处理边界
            if (isUptoSql) { // 版本从低到高
                if (reviText.last.first != revision) {
                    logger.warn("skip the different upgrade end point , name={}, db-revi={}, to-revi={}", plainName, plainRevi, revision)
                    continue
                }
                // 检测apply情况，应该全都未APPLY
                if (reviText.count { notApply(it.third) } != reviText.size) {
                    logger.warn("skip broken un-apply_dt upgrade , name={}, db-revi={}, to-revi={}", plainName, plainRevi, revision)
                    continue
                }
            } else {  // 版本从高到低
                if (reviText.last.first != revision) {
                    logger.warn("skip the different downgrade end point , name={}, db-revi={}, to-revi={}", plainName, plainRevi, revision)
                    continue
                }
                // 检测apply情况
                if (reviText.count { notApply(it.third) } != 0) {
                    logger.warn("skip broken apply_dt-ed downgrade , name={}, db-revi={}, to-revi={}", plainName, plainRevi, revision)
                    continue
                }

                // 去掉终点脚本，不需要执行
                reviText.removeLast()
            }

            val plainTbls = schemaDefinitionLoader.showTables(plainDs)
            for ((revi, text) in reviText) {
                logger.info("ready for name={} revi={}", plainName, revi)
                try {
                    applyRevisionSql(revi, text, isUptoSql, commitId, plainTmpl, shardTmpl, plainTbls)
                } catch (e: Exception) {
                    logger.error("failed to exec sql revision, name=$plainName, revi=$revi", e)
                    throw e
                }
                logger.info("done for name={}, revi={}", plainName, revi)
            }

            // 后置检查
            val newRevi = getRevision(plainTmpl)
            if (revision != newRevi) {
                val msg = "failed to post check schema revision, name=$plainName, need ${revision}, but $newRevi"
                logger.error(msg)
                throw IllegalStateException(msg)
            }
        }
    }


    override fun forceApplyBreak(revision: Long, commitId: Long, isUpto: Boolean, dataSource: String?) {
        val shardTmpl = flywaveDataSources.shard?.let { SimpleJdbcTemplate(it, "sharding") }
        val reviQuery = if (isUpto) {
            """
                SELECT
                    upto_sql,
                    apply_dt
                FROM sys_schema_version
                WHERE revision = ?
                """.trimIndent()
        } else {
            """
            SELECT
                undo_sql,
                apply_dt
            FROM sys_schema_version
            WHERE revision = ?
            """.trimIndent()
        }

        for ((plainName, plainDs) in flywaveDataSources.plains()) {

            if (!(dataSource == null || plainName.equals(dataSource, true))) {
                continue
            }

            val plainTmpl = SimpleJdbcTemplate(plainDs, plainName)
            val applySqls = LinkedList<Pair<String, String>>()

            try {
                plainTmpl.query(reviQuery, revision) {
                    applySqls.add(Pair(it.getString(1), it.getString(2)))
                }
            } catch (e: Exception) {
                assertNot1st(plainDs, e)
                logger.warn("skip, un-init-ist, revi={}, isUpto={}, db={}", applySqls.size, revision, isUpto, plainName)
                continue
            }

            if (applySqls.size != 1) {
                logger.warn("skip, find {} sqls, revi={}, isUpto={}, db={}", applySqls.size, revision, isUpto, plainName)
                continue
            }

            val reviSql = applySqls.first
            val notAppd = notApply(reviSql.second)
            if (isUpto && !notAppd) {
                logger.error("skip, applied upto, need force to undo first, revi={}, isUpto={}, db={}", revision, isUpto, plainName)
                continue
            }

            if (!isUpto && notAppd) {
                logger.error("skip, not applied undo, revi={}, isUpto={}, db={}", revision, isUpto, plainName)
                continue
            }

            logger.info("ready, revi={}, isUpto={}, db={}", revision, isUpto, plainName)
            val plainTbls = schemaDefinitionLoader.showTables(plainDs)
            applyRevisionSql(revision, reviSql.first, isUpto, commitId, plainTmpl, shardTmpl, plainTbls)
            logger.info("done, revi={}, isUpto={}, db={}", revision, isUpto, plainName)
        }
    }

    override fun checkAndInitSql(sqls: SortedMap<Long, SchemaRevisionManager.RevisionSql>, commitId: Long) {
        if (sqls.isNullOrEmpty()) {
            logger.warn("skip empty local sqls")
        }
        val selectSql = """
                    SELECT upto_sql, undo_sql, apply_dt
                    FROM sys_schema_version
                    WHERE revision = ?
                    """.trimIndent()
        val insertSql = """
                    INSERT INTO sys_schema_version
                    (revision, create_dt, commit_id, upto_sql, undo_sql)
                    VALUES(?, NOW(), ?, ?, ?)
                    """.trimIndent()

        for ((revi, entry) in sqls) {
            val undoSql = entry.undoText
            val uptoSql = entry.uptoText
            if (undoSql.isBlank() && uptoSql.isBlank()) {
                logger.warn("skip an both empty sqls, revi={}, upto-path={}, undo-path={}", entry.revision, entry.uptoPath, entry.undoPath)
                continue
            }

            for ((plainName, plainDs) in flywaveDataSources.plains()) {
                logger.info("ready to check revi={}, on db={}", revi, plainName)
                val plainTmpl = SimpleJdbcTemplate(plainDs, plainName)
                val dbVal = HashMap<String, String>()

                try {
                    plainTmpl.query(selectSql, revi) {
                        dbVal["upto_sql"] = it.getString("upto_sql")
                        dbVal["undo_sql"] = it.getString("undo_sql")
                        dbVal["apply_dt"] = it.getString("apply_dt")
                    }
                } catch (e: Exception) {
                    if (revi <= INIT1ST_REVISION) {
                        assertNot1st(plainDs, e)
                        logger.warn("try to init first version, revi={}, on db={}", revi, plainName)
                        applyRevisionSql(revi, uptoSql, true, commitId, plainTmpl, null, emptyList())
                        dbVal["upto_sql"] = ""
                        dbVal["undo_sql"] = ""
                        dbVal["apply_dt"] = ""
                    } else {
                        throw e
                    }
                }

                if (dbVal.isEmpty()) {
                    logger.info("insert for database not exist revi={}, db={}", revi, plainName)
                    val rst = plainTmpl.update(insertSql, revi, commitId, uptoSql, undoSql)
                    if (rst != 1) {
                        throw IllegalStateException("failed to insert revi=$revi, db=$plainName")
                    }
                    continue
                }

                // check
                val updSql = StringBuilder()
                val updVal = LinkedList<Any>()
                val applyd = dbVal["apply_dt"]
                val notAly = notApply(applyd)

                // check undo
                val undoDbs = dbVal["undo_sql"]
                val undoBlk = undoDbs!!.isBlank()
                if (undoSql != undoDbs) {
                    if (notAly || undoBlk) {
                        updSql.append("undo_sql = ?, ")
                        updVal.add(undoSql)
                        if (undoBlk) {
                            logger.info("empty undo-sql, update it. revi={}, db={}", revi, plainName)
                        } else {
                            logger.warn("diff undo-sql, update it. revi={}, db={}", revi, plainName)
                        }
                    } else {
                        logger.error("skip diff undo-sql but applied. revi={}, db={}", revi, plainName)
                        continue
                    }
                }

                // check upto
                val uptoDbs = dbVal["upto_sql"]
                val uptoBlk = uptoDbs!!.isBlank()
                if (uptoSql != uptoDbs) {
                    if (notAly || uptoBlk) {
                        updSql.append("upto_sql = ?, ")
                        updVal.add(uptoSql)
                        if (uptoBlk) {
                            logger.info("empty upto-sql, update it to revi={}, db={}", revi, plainName)
                        } else {
                            logger.warn("diff upto-sql, update it to revi={}, db={}", revi, plainName)
                        }
                    } else {
                        logger.error("skip diff upto-sql but applied revi={}, db={}", revi, plainName)
                        continue
                    }
                }

                // update
                if (updSql.isNotEmpty()) {
                    logger.info("update diff to database revi={}, applyDt={}, db={}", revi, applyd, plainName)
                    updVal.add(commitId)
                    updVal.add(revi)
                    val rst = plainTmpl.update("""
                        UPDATE sys_schema_version SET
                            $updSql
                            modify_dt = NOW(),
                            commit_id = ?
                        WHERE revision = ?
                        """.trimIndent(), *updVal.toArray())

                    if (rst != 1) {
                        throw IllegalStateException("failed to update revi=$revi, db=$plainName")
                    }
                } else {
                    logger.info("skip all same revi={}, applyDt={}, db={}", revi, applyd, plainName)
                }
            }
        }
    }


    override fun forceUpdateSql(revision: Long, upto: String, undo: String, commitId: Long) {
        val insertSql = """
            INSERT INTO sys_schema_version
            (revision, create_dt, commit_id, upto_sql, undo_sql)
            VALUES(?, NOW(), ?, ?, ?)
            """.trimIndent()
        val updateSql = """
            UPDATE sys_schema_version SET
                upto_sql = ?,
                undo_sql = ?,
                modify_dt = NOW(),
                commit_id = ?
            WHERE revision = ?
            """.trimIndent()

        for ((plainName, plainDs) in flywaveDataSources.plains()) {
            logger.info("ready force update revi={}, on db={}", revision, plainName)
            val tmpl = SimpleJdbcTemplate(plainDs, plainName)

            // 不要使用msyql的REPLACE INTO，使用标准SQL

            val cnt = tmpl.count("SELECT COUNT(1) FROM sys_schema_version WHERE revision= ?", revision)
            if (cnt == 0) {
                val rst = tmpl.update(insertSql, revision, commitId, upto, undo)
                logger.info("done force insert {} records, revi={}, on db={}", rst, revision, plainName)
            } else {
                val rst = tmpl.update(updateSql, upto, undo, commitId, revision)
                logger.info("done force update {} records, revi={}, on db={}", rst, revision, plainName)
            }
        }
    }
    //

    private fun applyRevisionSql(revi: Long, text: String, isUpto: Boolean, commitId: Long, plainTmpl: SimpleJdbcTemplate, shardTmpl: SimpleJdbcTemplate?, plainTbls: List<String>) {
        logger.info("parse revi-sql, revi={}, isUpto={}", revi, isUpto)

        val plainName = plainTmpl.name
        for (seg in sqlSegmentProcessor.parse(sqlStatementParser, text)) {
            if (seg.sqlText.isBlank()) {
                continue
            }
            // 不使用事务，出错时，根据日志进行回滚或数据清理
            if (seg.isPlain || shardTmpl == null) {
                logger.info("use plain to run sql-line from {} to {}, db={}", seg.lineBgn, seg.lineEnd, plainName)
                runSegment(plainTmpl, plainTbls, seg)
            } else {
                logger.info("use shard to run sql-line from {} to {}", seg.lineBgn, seg.lineEnd)
                runSegment(shardTmpl, emptyList(), seg)
            }
        }

        // update apply datetime，避免时区问题，使用SQL语法
        val applyDt = if (isUpto) {
            "NOW()"
        } else {
            "'1000-01-01'"
        }
        val cnt = try {
            plainTmpl.update("UPDATE sys_schema_version SET apply_dt=$applyDt, commit_id=? WHERE revision=?", commitId, revi)
        } catch (e: Exception) {
            assertNot1st(plainTmpl.dataSource, e)
            logger.warn("skip un-init-1st, revi={}, applyDt={}, db={}", revi, applyDt, plainName)
            return
        }
        // 执行了，必须一条，因为上面不会出现语法错误
        if (cnt == 1) {
            logger.info("update revi={}, applyDt={}, db={}", revi, applyDt, plainName)
        } else {
            throw IllegalStateException("update revi=$revi, but $cnt records affect, db=$plainName")
        }
    }


    private fun getRevision(tmpl: SimpleJdbcTemplate): Long = try {
        val rst = AtomicLong()
        tmpl.query("SELECT MAX(revision) FROM sys_schema_version WHERE apply_dt >'1000-01-01'") {
            val r = it.getLong(1)
            if (it.wasNull()) {
                rst.set(0)
            } else {
                rst.set(r)
            }
        }
        rst.get()
    } catch (e: Exception) {
        assertNot1st(tmpl.dataSource, e)
        logger.warn("failed to get un-init-1st revision, return -1, db={}", tmpl.name)
        -1
    }

    private fun runSegment(tmpl: SimpleJdbcTemplate, tables: List<String>, seg: SqlSegmentProcessor.Segment) {
        val dbName = tmpl.name
        if (seg.isBlack() || tables.isEmpty() || seg.tblName.isEmpty()) {
            logger.info("run sql on direct table, db={}", dbName)
            tmpl.execute(seg.sqlText)
        } else {
            val tblName = seg.tblName
            // 包括分表和日志表
            val tblReal = tables.filter { sqlSegmentProcessor.hasType(tblName, it) > TYPE_PLAIN }.toMutableSet()
            tblReal.add(tblName) // 保证当前执行

            for (tbl in tblReal) {
                if (tbl == tblName) {
                    logger.info("run sql on plain table={}, db={}", tbl, dbName)
                } else {
                    logger.info("run sql on shard/trace table={}, db={}", tbl, dbName)
                }
                val sql = sqlSegmentProcessor.merge(seg, tbl)
                tmpl.execute(sql)
            }
        }
    }

    private fun notApply(str: String?): Boolean {
        if (str.isNullOrEmpty()) return true
        return str.startsWith("1000-01-01")
    }

    private fun assertNot1st(ds: DataSource, er: Exception) {
        try {
            val tables = schemaDefinitionLoader.showTables(ds)
            if (tables.find { it.equals("sys_schema_version", true) } != null) {
                throw er // 存在 sys_schema_version 表，报出原异常
            }
        } catch (e: Exception) {
            // 报出原异常
            throw er
        }
    }
}