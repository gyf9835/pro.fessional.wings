package pro.fessional.wings.example.init;

import pro.fessional.wings.faceless.jooqgen.WingsCodeGenerator;

/**
 * @author trydofor
 * @since 2019-05-31
 */
public class WingsExampleJooqCodeGenerator {

    public static void main(String[] args) {
        WingsCodeGenerator.builder()
                          .jdbcDriver("com.mysql.cj.jdbc.Driver")
                          .jdbcUrl("jdbc:mysql://127.0.0.1/wings")
                          .jdbcUser("trydofor")
                          .jdbcPassword("moilioncircle")
                          .databaseSchema("wings")
//                          .databaseIncludes(".*")
//                          .databaseExcludes(".*\\$log # 日志表\n"
//                                  + "| SPRING.* # Spring\n"
//                                  + "| SYS_SCALE_SEQUENCE # 特殊处理")
                          .databaseVersionProvider("SELECT MAX(REVISION) FROM SYS_SCHEMA_VERSION WHERE APPLY_DT > '1000-01-01'")
                          .targetPackage("pro.fessional.wings.example.database.autogen")
                          .targetDirectory("wings-example/src/main/java/")
                          .buildAndGenerate();
    }
}
