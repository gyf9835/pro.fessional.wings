package pro.fessional.wings.warlock.project;

import lombok.Getter;
import lombok.Setter;
import pro.fessional.wings.faceless.codegen.ConstantEnumGenerator;
import pro.fessional.wings.faceless.codegen.ConstantEnumGenerator.Builder;
import pro.fessional.wings.faceless.codegen.ConstantEnumGenerator.ConstantEnum;
import pro.fessional.wings.faceless.codegen.ConstantEnumJdbcLoader;
import pro.fessional.wings.faceless.codegen.JdbcDataLoadHelper;

import java.util.List;
import java.util.function.Consumer;

/**
 * idea中，main函数执行和spring执行，workdir不同
 *
 * @author trydofor
 * @since 2021-02-20
 */
@Setter
@Getter
public class Warlock2EnumGenerator {

    private String targetDir = "./wings-warlock/src/main/java/";
    private String targetPkg = "pro.fessional.wings.warlock.enums.autogen";

    @SafeVarargs
    public final void gen(List<ConstantEnum> enums, Consumer<Builder>... customize) {
        final Builder builder = ConstantEnumGenerator
                .builder()
                .targetDirectory(targetDir)
                .targetPackage(targetPkg);
        for (Consumer<Builder> consumer : customize) {
            consumer.accept(builder);
        }

        builder.generate(enums);
    }

    @SafeVarargs
    public final void gen(String jdbc, String user, String pass, Consumer<Builder>... customize) {
        final JdbcDataLoadHelper helper = JdbcDataLoadHelper.use(jdbc, user, pass);
        List<ConstantEnum> enums = ConstantEnumJdbcLoader.load(helper);
        gen(enums, customize);
    }

    ///
    public static Consumer<Builder> excludeStandard() {
        return builder -> builder
                .excludeType("standard_boolean")
                .excludeType("standard_language")
                .excludeType("standard_timezone");
    }

    public static Consumer<Builder> excludeWarlock() {
        return builder -> builder
                .excludeType("user_gender")
                .excludeType("user_status");
    }
}
