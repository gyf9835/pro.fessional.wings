package pro.fessional.wings.warlock.project;

import lombok.Setter;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pro.fessional.wings.faceless.flywave.SchemaRevisionManager;
import pro.fessional.wings.faceless.flywave.WingsRevision;
import pro.fessional.wings.faceless.util.FlywaveRevisionScanner;

import java.util.SortedMap;

/**
 * @author trydofor
 * @since 2021-02-22
 */
@Disabled("手动初始化")
@SpringBootTest(properties = {
        "spring.datasource.url=" + Warlock0CodegenConstant.JDBC,
        "spring.datasource.username=" + Warlock0CodegenConstant.USER,
        "spring.datasource.password=" + Warlock0CodegenConstant.PASS,
        "debug = true"
})
class Warlock1SchemaManagerTest {

    @Setter(onMethod_ = {@Autowired})
    private SchemaRevisionManager schemaRevisionManager;

    @Test
    void init04AuthMain() {
        final Warlock1SchemaManager manager = new Warlock1SchemaManager(schemaRevisionManager);
        manager.init(WingsRevision.V05_20_1025_01_ConfRuntime.revision(),
                Warlock1SchemaManager.includeWarlockPath(),
                Warlock1SchemaManager.includeWarlockRevi());
    }

    @Test
    void init04AuthTest() {
        final Warlock1SchemaManager manager = new Warlock1SchemaManager(schemaRevisionManager);
        manager.init(2020_10_24_03,
                Warlock1SchemaManager.includeWarlockPath(),
                Warlock1SchemaManager.includeWarlockRevi(),
                helper -> helper.branch("test/").include(2020_10_24_03));
    }


    @Test
    void bugfixExecute() {
        final FlywaveRevisionScanner.Helper helper = FlywaveRevisionScanner.helper();
        helper.somefix("01-authn-fix");
        final SortedMap<Long, SchemaRevisionManager.RevisionSql> sqls = helper.scan();
        schemaRevisionManager.forceExecuteSql(sqls, true);
    }
}
