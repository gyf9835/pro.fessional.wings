/*
 * This file is generated by jOOQ.
 */
package pro.fessional.wings.faceless.database.autogen;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;

import pro.fessional.wings.faceless.database.autogen.tables.SysConstantEnumTable;
import pro.fessional.wings.faceless.database.autogen.tables.SysStandardI18nTable;
import pro.fessional.wings.faceless.database.autogen.tables.Tst中文也分表Table;


/**
 * The schema <code>wings</code>.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.4",
        "schema version:2019060101"
    },
    date = "2020-08-11T06:15:47.219Z",
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DefaultSchema extends SchemaImpl {

    private static final long serialVersionUID = -1576858149;

    /**
     * The reference instance of <code></code>
     */
    public static final DefaultSchema DEFAULT_SCHEMA = new DefaultSchema();

    /**
     * The table <code>sys_constant_enum</code>.
     */
    public final SysConstantEnumTable SysConstantEnum = pro.fessional.wings.faceless.database.autogen.tables.SysConstantEnumTable.SysConstantEnum;

    /**
     * The table <code>sys_standard_i18n</code>.
     */
    public final SysStandardI18nTable SysStandardI18n = pro.fessional.wings.faceless.database.autogen.tables.SysStandardI18nTable.SysStandardI18n;

    /**
     * The table <code>tst_中文也分表</code>.
     */
    public final Tst中文也分表Table Tst中文也分表 = pro.fessional.wings.faceless.database.autogen.tables.Tst中文也分表Table.Tst中文也分表;

    /**
     * No further instances allowed
     */
    private DefaultSchema() {
        super("", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        List result = new ArrayList();
        result.addAll(getTables0());
        return result;
    }

    private final List<Table<?>> getTables0() {
        return Arrays.<Table<?>>asList(
            SysConstantEnumTable.SysConstantEnum,
            SysStandardI18nTable.SysStandardI18n,
            Tst中文也分表Table.Tst中文也分表);
    }
}
