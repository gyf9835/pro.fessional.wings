package pro.fessional.wings.faceless.flywave

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.MethodOrderer.MethodName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import pro.fessional.wings.faceless.WingsTestHelper
import pro.fessional.wings.faceless.WingsTestHelper.REVISION_TEST_V1
import pro.fessional.wings.faceless.WingsTestHelper.breakpointDebug
import pro.fessional.wings.faceless.WingsTestHelper.testcaseNotice
import pro.fessional.wings.faceless.util.FlywaveRevisionScanner
import pro.fessional.wings.faceless.util.FlywaveRevisionScanner.REVISION_2ND_IDLOGS

/**
 * 包括了分表，跟踪表的综合测试
 * @author trydofor
 * @since 2019-06-20
 */

@SpringBootTest(properties = ["debug = true",
    "wings.faceless.flywave.ver.schema-version-table=win_schema_version",
    "wings.faceless.flywave.ver.schema-journal-table=win_schema_journal"
])
@TestMethodOrder(MethodName::class)
class SchemaJournalManagerTest {

    @Autowired
    lateinit var schemaJournalManager: SchemaJournalManager

    @Autowired
    lateinit var schemaRevisionManager: SchemaRevisionManager

    @Autowired
    lateinit var wingsTestHelper: WingsTestHelper

    @Autowired
    lateinit var shcemaShardingManager: SchemaShardingManager

    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    private val schemaPrefix = "win_schema_"

    @Test
    fun `test0🦁清表`() {
        wingsTestHelper.cleanTable()
        val sqls = FlywaveRevisionScanner
                .helper()
                .master()
                .modify("更名win_schema_version") { _, sql ->
                    if (sql.revision == FlywaveRevisionScanner.REVISION_1ST_SCHEMA) {
                        sql.undoText = sql.undoText.replace("sys_schema_", schemaPrefix)
                        sql.uptoText = sql.uptoText.replace("sys_schema_", schemaPrefix)
                    }
                }
                .scan()
        schemaRevisionManager.checkAndInitSql(sqls, 0, true)
        breakpointDebug("清楚所有表，发布 REVISION_1ST_SCHEMA 版，新建 flywave 版本表")
    }

    @Test
    fun `test1🦁建表`() {
        schemaRevisionManager.publishRevision(REVISION_2ND_IDLOGS, 0)
        wingsTestHelper.assertSame(WingsTestHelper.Type.Table,
                "sys_commit_journal",
                "sys_light_sequence",
                "${schemaPrefix}journal",
                "${schemaPrefix}version"
        )
        breakpointDebug("生成测试表💰，观察数据库所有表")
        schemaRevisionManager.publishRevision(REVISION_TEST_V1, 0)
        wingsTestHelper.assertSame(WingsTestHelper.Type.Table,
                "sys_commit_journal",
                "sys_light_sequence",
                "${schemaPrefix}journal",
                "${schemaPrefix}version",
                "tst_中文也分表")
        testcaseNotice("可检查日志或debug观察，wing0和wing1表名")
    }

    @Test
    fun `test2🦁分表`() {
        schemaJournalManager.checkAndInitDdl("tst_中文也分表", 0)
        wingsTestHelper.assertNot(WingsTestHelper.Type.Table,
                "tst_中文也分表_0",
                "tst_中文也分表_1",
                "tst_中文也分表_2",
                "tst_中文也分表_3",
                "tst_中文也分表_4")
        breakpointDebug("分表测试表💰，观察数据库所有表")
        shcemaShardingManager.publishShard("tst_中文也分表", 5)
        wingsTestHelper.assertHas(WingsTestHelper.Type.Table, "tst_中文也分表_0",
                "tst_中文也分表_1",
                "tst_中文也分表_2",
                "tst_中文也分表_3",
                "tst_中文也分表_4")
        testcaseNotice("可检查日志或debug观察，wing_test，多出分表0-5")
    }

    @Test
    fun `test3🦁BU触发器`() {
        if (wingsTestHelper.isH2) {
            testcaseNotice("h2 database skip")
            return
        }

        breakpointDebug("分表触发器💰，观察数据库所有表")
        schemaJournalManager.publishUpdate("tst_中文也分表", true, 0)
        wingsTestHelper.assertHas(WingsTestHelper.Type.Table, "tst_中文也分表\$upd")
        wingsTestHelper.assertHas(WingsTestHelper.Type.Trigger, "tst_中文也分表\$bu")

        jdbcTemplate.execute("""
            INSERT INTO `tst_中文也分表_1`
            (`id`, `create_dt`, `modify_dt`, `delete_dt`, `commit_id`, `login_info`, `other_info`)
            VALUES (1,now(3),now(3),'1000-01-01',0,'赵四','老张');
        """)
        breakpointDebug("更新数据💰，查询数据库各表及数据")
        jdbcTemplate.execute("""
            UPDATE `tst_中文也分表_1` SET login_info='赵思', commit_id=1 WHERE id = 1
        """)
        breakpointDebug("更新数据🐵，查询数据库各表及数据")

        jdbcTemplate.execute("DELETE FROM `tst_中文也分表_1` WHERE id = 1")
        val del = jdbcTemplate.update("DELETE FROM `tst_中文也分表_1\$upd` WHERE id = 1")

        assertEquals(1, del, "如果失败，单独运行整个类，消除分表干扰")
        breakpointDebug("清楚数据🐵，因为trace表不会删除有数据表")

        schemaJournalManager.publishUpdate("tst_中文也分表", false, 0)
        wingsTestHelper.assertNot(WingsTestHelper.Type.Table, "tst_中文也分表\$upd")
        wingsTestHelper.assertNot(WingsTestHelper.Type.Trigger, "tst_中文也分表\$bu")
        testcaseNotice("检查日志和数据库变化，最好debug进行，wing0和wing1，同步更新表结构")
    }

    @Test
    fun `test4🦁BD触发器`() {
        if (wingsTestHelper.isH2) {
            testcaseNotice("h2 database skip")
            return
        }
        breakpointDebug("分表触发器💰，观察数据库所有表")
        schemaJournalManager.publishDelete("tst_中文也分表", true, 0)
        wingsTestHelper.assertHas(WingsTestHelper.Type.Table, "tst_中文也分表\$del")
        wingsTestHelper.assertHas(WingsTestHelper.Type.Trigger, "tst_中文也分表\$bd")

        jdbcTemplate.execute("""
            INSERT INTO `tst_中文也分表_1`
            (`id`, `create_dt`, `modify_dt`, `delete_dt`, `commit_id`, `login_info`, `other_info`)
            VALUES (1,now(3),now(3),'1000-01-01',0,'赵四','老张');
        """)
        breakpointDebug("删除数据💰，查询数据库各表及数据")

        jdbcTemplate.execute("DELETE FROM `tst_中文也分表_1` WHERE id = 1")
        breakpointDebug("删除数据🐵，查询数据库各表及数据")

        val del = jdbcTemplate.update("DELETE FROM `tst_中文也分表\$del` WHERE id = 1")

        assertEquals(1, del)
        breakpointDebug("清楚数据🐵，因为trace表不会删除有数据表")

        schemaJournalManager.publishDelete("tst_中文也分表", false, 0)
        wingsTestHelper.assertNot(WingsTestHelper.Type.Table, "tst_中文也分表\$del")
        wingsTestHelper.assertNot(WingsTestHelper.Type.Trigger, "tst_中文也分表\$bd")
        testcaseNotice("检查日志和数据库变化，最好debug进行，wing0和wing1，同步更新表结构")
    }
}
