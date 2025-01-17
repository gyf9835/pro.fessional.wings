package pro.fessional.wings.warlock.enums.autogen;


import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pro.fessional.wings.faceless.enums.ConstantEnum;
import pro.fessional.wings.faceless.enums.StandardI18nEnum;

/**
 * @author trydofor
 * @since 2022-10-03
 */
public enum UserGender implements ConstantEnum, StandardI18nEnum {

    SUPER(1200100, "user_gender", "性别", "classpath:/wings-tmpl/ConstantEnumTemplate.java"),
    MALE(1200101, "male", "男", "通常"),
    FEMALE(1200102, "female", "女", "通常"),
    UNKNOWN(1200103, "unknown", "未知", "通常"),
    ;
    public static final String $SUPER = "user_gender";
    public static final String $MALE = "male";
    public static final String $FEMALE = "female";
    public static final String $UNKNOWN = "unknown";
    public static final boolean useIdAsKey = false;

    private final int id;
    private final String code;
    private final String hint;
    private final String info;

    private final String ukey;
    private final String rkey;

    UserGender(int id, String code, String hint, String info) {
        this.id = id;
        this.code = code;
        this.hint = hint;
        this.info = info;
        this.ukey = "user_gender." + (useIdAsKey ? "id." + id : code);
        this.rkey = "sys_constant_enum.hint." + ukey;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public @NotNull String getType() {
        return "user_gender";
    }

    @Override
    public @NotNull String getInfo() {
        return info;
    }

    @Override
    public @NotNull String getBase() {
        return "sys_constant_enum";
    }

    @Override
    public @NotNull String getKind() {
        return "hint";
    }

    @Override
    public @NotNull String getUkey() {
        return ukey;
    }

    @Override
    public @NotNull String getCode() {
        return code;
    }

    @Override
    public @NotNull String getHint() {
        return hint;
    }

    @Override
    public @NotNull String getI18nCode() {
        return rkey;
    }

    @Nullable
    public static UserGender valueOf(int id) {
        for (UserGender v : UserGender.values()) {
            if (id == v.id) return v;
        }
        return null;
    }

    @Contract("_, !null -> !null")
    public static UserGender idOf(Integer id, UserGender elz) {
        if (id == null) return elz;
        final int i = id;
        for (UserGender v : UserGender.values()) {
            if (i == v.id) return v;
        }
        return elz;
    }

    @Contract("_, !null -> !null")
    public static UserGender codeOf(String code, UserGender elz) {
        if (code == null) return elz;
        for (UserGender v : UserGender.values()) {
            if (code.equalsIgnoreCase(v.code)) return v;
        }
        return elz;
    }

    @Contract("_, !null -> !null")
    public static UserGender nameOf(String name, UserGender elz) {
        if (name == null) return elz;
        for (UserGender v : UserGender.values()) {
            if (name.equalsIgnoreCase(v.name())) return v;
        }
        return elz;
    }
}
