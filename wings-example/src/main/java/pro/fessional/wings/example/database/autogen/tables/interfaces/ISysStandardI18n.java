/*
 * This file is generated by jOOQ.
 */
package pro.fessional.wings.example.database.autogen.tables.interfaces;


import java.io.Serializable;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


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
public interface ISysStandardI18n extends Serializable {

    /**
     * Setter for <code>sys_standard_i18n.base</code>.
     */
    public void setBase(String value);

    /**
     * Getter for <code>sys_standard_i18n.base</code>.
     */
    @Column(name = "base", nullable = false, length = 100)
    @NotNull
    @Size(max = 100)
    public String getBase();

    /**
     * Setter for <code>sys_standard_i18n.kind</code>.
     */
    public void setKind(String value);

    /**
     * Getter for <code>sys_standard_i18n.kind</code>.
     */
    @Column(name = "kind", nullable = false, length = 100)
    @NotNull
    @Size(max = 100)
    public String getKind();

    /**
     * Setter for <code>sys_standard_i18n.ukey</code>.
     */
    public void setUkey(String value);

    /**
     * Getter for <code>sys_standard_i18n.ukey</code>.
     */
    @Column(name = "ukey", nullable = false, length = 200)
    @NotNull
    @Size(max = 200)
    public String getUkey();

    /**
     * Setter for <code>sys_standard_i18n.lang</code>.
     */
    public void setLang(String value);

    /**
     * Getter for <code>sys_standard_i18n.lang</code>.
     */
    @Column(name = "lang", nullable = false, length = 5)
    @NotNull
    @Size(max = 5)
    public String getLang();

    /**
     * Setter for <code>sys_standard_i18n.hint</code>.
     */
    public void setHint(String value);

    /**
     * Getter for <code>sys_standard_i18n.hint</code>.
     */
    @Column(name = "hint", nullable = false, length = 3000)
    @NotNull
    @Size(max = 3000)
    public String getHint();

    // -------------------------------------------------------------------------
    // FROM and INTO
    // -------------------------------------------------------------------------

    /**
     * Load data from another generated Record/POJO implementing the common interface ISysStandardI18n
     */
    public void from(ISysStandardI18n from);

    /**
     * Copy data into another generated Record/POJO implementing the common interface ISysStandardI18n
     */
    public <E extends ISysStandardI18n> E into(E into);
}
