package pro.fessional.wings.faceless.flywave

import org.junit.Assert
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import pro.fessional.wings.faceless.WingsTestHelper
import pro.fessional.wings.faceless.WingsTestHelper.REVISION_TEST_V1
import pro.fessional.wings.faceless.WingsTestHelper.REVISION_TEST_V2
import pro.fessional.wings.faceless.WingsTestHelper.testcaseNotice
import pro.fessional.wings.faceless.database.FacelessDataSources

/**
 * shard profile, 使用write和reader数据源
 * @author trydofor
 * @since 2019-06-17
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@ActiveProfiles("shard")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class SchemaShardingManagerTest {

    @Autowired
    lateinit var schemaShardingManager: SchemaShardingManager

    @Autowired
    lateinit var schemaRevisionManager: SchemaRevisionManager

    @Autowired
    lateinit var shardingJdbcTemplate: JdbcTemplate

    @Autowired
    lateinit var facelessDataSources: FacelessDataSources

    @Autowired
    lateinit var wingsTestHelper: WingsTestHelper

    @Test
    fun `test0🦁清表重置`() {
        wingsTestHelper.cleanAndInit()
    }

    @Test
    fun `test1🦁单库执行`() {
        schemaRevisionManager.publishRevision(REVISION_TEST_V1, 0)
        wingsTestHelper.assertHas(WingsTestHelper.Type.Table, "tst_中文也分表")

        schemaRevisionManager.forceApplyBreak(REVISION_TEST_V2, 2, true, "writer")
        Assert.assertEquals(20, countRecords("writer", "tst_中文也分表"))
        Assert.assertEquals(0, countRecords("reader", "tst_中文也分表"))

        testcaseNotice("在writer强制插入数据，用SQL查询，只有writer有数据，reader上没有")
    }

    @Test
    fun `test2🦁分表分库`() {
        schemaShardingManager.publishShard("sys_schema_journal", 2)
        wingsTestHelper.assertHas(WingsTestHelper.Type.Table, "sys_schema_journal_0", "sys_schema_journal_1")
        schemaShardingManager.publishShard("sys_schema_journal", 0)
        wingsTestHelper.assertNot(WingsTestHelper.Type.Table, "sys_schema_journal_0", "sys_schema_journal_1")
        testcaseNotice("writer 和reader上，同时多出2个sys_schema_journal_[0-1]表")
    }

    @Test
    fun `test3🦁分表并移动数据`() {
        schemaShardingManager.publishShard("tst_中文也分表", 5)
        wingsTestHelper.assertHas(WingsTestHelper.Type.Table, "tst_中文也分表",
                "tst_中文也分表_0",
                "tst_中文也分表_1",
                "tst_中文也分表_2",
                "tst_中文也分表_3",
                "tst_中文也分表_4")
        Assert.assertEquals(20, countRecords("writer", "tst_中文也分表"))
        schemaShardingManager.shardingData("tst_中文也分表", true)
        // 主表移除
        Assert.assertEquals("如果失败，单独运行，消除分表干扰", 0, countRecords("writer", "tst_中文也分表"))
        // 分表平分
        Assert.assertEquals(4, countRecords("writer", "tst_中文也分表_0"))
        Assert.assertEquals(4, countRecords("writer", "tst_中文也分表_1"))
        Assert.assertEquals(4, countRecords("writer", "tst_中文也分表_2"))
        Assert.assertEquals(4, countRecords("writer", "tst_中文也分表_3"))
        Assert.assertEquals(4, countRecords("writer", "tst_中文也分表_4"))

        val cnt = shardingJdbcTemplate.queryForObject("SELECT count(*) FROM tst_中文也分表", Int::class.java)
        testcaseNotice("writer和reader实际未配置同步，所以从库读取为0")
        Assert.assertEquals(0, cnt)
    }

    fun countRecords(db: String, tbl: String) = JdbcTemplate(facelessDataSources.plains()[db]!!)
            .queryForObject("select count(*) from $tbl", Int::class.java)
}