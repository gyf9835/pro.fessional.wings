/*
 * This file is generated by jOOQ.
 */
package pro.fessional.wings.warlock.database.autogen;


import pro.fessional.wings.warlock.database.autogen.tables.SysConstantEnumTable;
import pro.fessional.wings.warlock.database.autogen.tables.SysStandardI18nTable;
import pro.fessional.wings.warlock.database.autogen.tables.WinConfRuntimeTable;

import javax.annotation.processing.Generated;


/**
 * Convenience access to all tables in the default schema.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.14.15",
        "schema version:2020102501"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TablesWarlock {

    /**
     * The table <code>sys_constant_enum</code>.
     */
    public static final SysConstantEnumTable SysConstantEnum = SysConstantEnumTable.SysConstantEnum;

    /**
     * The table <code>sys_standard_i18n</code>.
     */
    public static final SysStandardI18nTable SysStandardI18n = SysStandardI18nTable.SysStandardI18n;

    /**
     * The table <code>win_conf_runtime</code>.
     */
    public static final WinConfRuntimeTable WinConfRuntime = WinConfRuntimeTable.WinConfRuntime;
}
