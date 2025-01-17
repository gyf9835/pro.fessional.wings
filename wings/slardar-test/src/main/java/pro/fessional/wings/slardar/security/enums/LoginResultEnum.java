package pro.fessional.wings.slardar.security.enums;

import org.jetbrains.annotations.NotNull;
import pro.fessional.mirana.data.CodeEnum;

/**
 * @author trydofor
 * @since 2021-02-01
 */
public enum LoginResultEnum implements CodeEnum {
    Success("200", "登录成功"),
    Failure("401", "登录失败");

    private final String code;
    private final String hint;

    LoginResultEnum(String code, String hint) {
        this.code = code;
        this.hint = hint;
    }

    @Override
    public @NotNull String getCode() {
        return code;
    }

    @Override
    public @NotNull String getHint() {
        return hint;
    }
}

