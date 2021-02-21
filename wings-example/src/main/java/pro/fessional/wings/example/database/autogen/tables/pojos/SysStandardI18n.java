/*
 * This file is generated by jOOQ.
 */
package pro.fessional.wings.example.database.autogen.tables.pojos;


import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import pro.fessional.wings.example.database.autogen.tables.interfaces.ISysStandardI18n;


/**
 * The table <code>wings_example.sys_standard_i18n</code>.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.14.4",
        "schema version:2019070403"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
@Entity
@Table(
    name = "sys_standard_i18n",
    uniqueConstraints = {
        @UniqueConstraint(name = "KEY_sys_standard_i18n_PRIMARY", columnNames = { "base", "kind", "ukey", "lang" })
    }
)
public class SysStandardI18n implements ISysStandardI18n {

    private static final long serialVersionUID = 1L;

    private String base;
    private String kind;
    private String ukey;
    private String lang;
    private String hint;

    public SysStandardI18n() {}

    public SysStandardI18n(ISysStandardI18n value) {
        this.base = value.getBase();
        this.kind = value.getKind();
        this.ukey = value.getUkey();
        this.lang = value.getLang();
        this.hint = value.getHint();
    }

    public SysStandardI18n(
        String base,
        String kind,
        String ukey,
        String lang,
        String hint
    ) {
        this.base = base;
        this.kind = kind;
        this.ukey = ukey;
        this.lang = lang;
        this.hint = hint;
    }

    /**
     * Getter for <code>sys_standard_i18n.base</code>.
     */
    @Column(name = "base", nullable = false, length = 100)
    @NotNull
    @Size(max = 100)
    @Override
    public String getBase() {
        return this.base;
    }

    /**
     * Setter for <code>sys_standard_i18n.base</code>.
     */
    @Override
    public void setBase(String base) {
        this.base = base;
    }

    /**
     * Getter for <code>sys_standard_i18n.kind</code>.
     */
    @Column(name = "kind", nullable = false, length = 100)
    @NotNull
    @Size(max = 100)
    @Override
    public String getKind() {
        return this.kind;
    }

    /**
     * Setter for <code>sys_standard_i18n.kind</code>.
     */
    @Override
    public void setKind(String kind) {
        this.kind = kind;
    }

    /**
     * Getter for <code>sys_standard_i18n.ukey</code>.
     */
    @Column(name = "ukey", nullable = false, length = 200)
    @NotNull
    @Size(max = 200)
    @Override
    public String getUkey() {
        return this.ukey;
    }

    /**
     * Setter for <code>sys_standard_i18n.ukey</code>.
     */
    @Override
    public void setUkey(String ukey) {
        this.ukey = ukey;
    }

    /**
     * Getter for <code>sys_standard_i18n.lang</code>.
     */
    @Column(name = "lang", nullable = false, length = 5)
    @NotNull
    @Size(max = 5)
    @Override
    public String getLang() {
        return this.lang;
    }

    /**
     * Setter for <code>sys_standard_i18n.lang</code>.
     */
    @Override
    public void setLang(String lang) {
        this.lang = lang;
    }

    /**
     * Getter for <code>sys_standard_i18n.hint</code>.
     */
    @Column(name = "hint", nullable = false, length = 3000)
    @NotNull
    @Size(max = 3000)
    @Override
    public String getHint() {
        return this.hint;
    }

    /**
     * Setter for <code>sys_standard_i18n.hint</code>.
     */
    @Override
    public void setHint(String hint) {
        this.hint = hint;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final SysStandardI18n other = (SysStandardI18n) obj;
        if (base == null) {
            if (other.base != null)
                return false;
        }
        else if (!base.equals(other.base))
            return false;
        if (kind == null) {
            if (other.kind != null)
                return false;
        }
        else if (!kind.equals(other.kind))
            return false;
        if (ukey == null) {
            if (other.ukey != null)
                return false;
        }
        else if (!ukey.equals(other.ukey))
            return false;
        if (lang == null) {
            if (other.lang != null)
                return false;
        }
        else if (!lang.equals(other.lang))
            return false;
        if (hint == null) {
            if (other.hint != null)
                return false;
        }
        else if (!hint.equals(other.hint))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.base == null) ? 0 : this.base.hashCode());
        result = prime * result + ((this.kind == null) ? 0 : this.kind.hashCode());
        result = prime * result + ((this.ukey == null) ? 0 : this.ukey.hashCode());
        result = prime * result + ((this.lang == null) ? 0 : this.lang.hashCode());
        result = prime * result + ((this.hint == null) ? 0 : this.hint.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("SysStandardI18n (");

        sb.append(base);
        sb.append(", ").append(kind);
        sb.append(", ").append(ukey);
        sb.append(", ").append(lang);
        sb.append(", ").append(hint);

        sb.append(")");
        return sb.toString();
    }

    // -------------------------------------------------------------------------
    // FROM and INTO
    // -------------------------------------------------------------------------

    @Override
    public void from(ISysStandardI18n from) {
        setBase(from.getBase());
        setKind(from.getKind());
        setUkey(from.getUkey());
        setLang(from.getLang());
        setHint(from.getHint());
    }

    @Override
    public <E extends ISysStandardI18n> E into(E into) {
        into.from(this);
        return into;
    }
}
