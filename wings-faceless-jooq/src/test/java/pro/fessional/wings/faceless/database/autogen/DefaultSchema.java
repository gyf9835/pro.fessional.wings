/*
 * This file is generated by jOOQ.
 */
package pro.fessional.wings.faceless.database.autogen;


import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;
import pro.fessional.wings.faceless.database.autogen.tables.SysConstantEnumTable;
import pro.fessional.wings.faceless.database.autogen.tables.SysStandardI18nTable;
import pro.fessional.wings.faceless.database.autogen.tables.Tst中文也分表Table;

import javax.annotation.Generated;
import java.util.Arrays;
import java.util.List;


/**
 * The schema <code>wings</code>.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.14.4",
        "schema version:2019060101"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DefaultSchema extends SchemaImpl {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>DEFAULT_SCHEMA</code>
     */
    public static final DefaultSchema DEFAULT_SCHEMA = new DefaultSchema();

    /**
     * The table <code>sys_constant_enum</code>.
     */
    public final SysConstantEnumTable SysConstantEnum = SysConstantEnumTable.SysConstantEnum;

    /**
     * The table <code>sys_standard_i18n</code>.
     */
    public final SysStandardI18nTable SysStandardI18n = SysStandardI18nTable.SysStandardI18n;

    /**
     * The table <code>tst_中文也分表</code>.
     */
    public final Tst中文也分表Table Tst中文也分表 = Tst中文也分表Table.Tst中文也分表;

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
        return Arrays.<Table<?>>asList(
            SysConstantEnumTable.SysConstantEnum,
            SysStandardI18nTable.SysStandardI18n,
            Tst中文也分表Table.Tst中文也分表);
    }
}
