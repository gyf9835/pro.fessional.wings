/*
 * This file is generated by jOOQ.
 */
package pro.fessional.wings.oracle.database.autogen.tables;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Name;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;
import org.jooq.impl.TableImpl;

import pro.fessional.wings.oracle.database.autogen.DefaultSchema;
import pro.fessional.wings.oracle.database.autogen.tables.records.SysSchemaManagerRecord;
import pro.fessional.wings.oracle.service.lightid.LightIdAware;


/**
 * The table <code>wings.SYS_SCHEMA_MANAGER</code>.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9",
        "schema version:2019051201"
    },
    date = "2019-06-03T13:01:45.550Z",
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class SysSchemaManager extends TableImpl<SysSchemaManagerRecord> implements LightIdAware {

    private static final long serialVersionUID = 2015537585;

    /**
     * The reference instance of <code>SYS_SCHEMA_MANAGER</code>
     */
    public static final SysSchemaManager SYS_SCHEMA_MANAGER = new SysSchemaManager();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<SysSchemaManagerRecord> getRecordType() {
        return SysSchemaManagerRecord.class;
    }

    /**
     * The column <code>SYS_SCHEMA_MANAGER.TABLE_NAME</code>.
     */
    public final TableField<SysSchemaManagerRecord, String> TABLE_NAME = createField("TABLE_NAME", org.jooq.impl.SQLDataType.VARCHAR(100).nullable(false), this, "主表表名");

    /**
     * The column <code>SYS_SCHEMA_MANAGER.CREATE_DT</code>.
     */
    public final TableField<SysSchemaManagerRecord, LocalDateTime> CREATE_DT = createField("CREATE_DT", org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "创建日时");

    /**
     * The column <code>SYS_SCHEMA_MANAGER.MODIFY_DT</code>.
     */
    public final TableField<SysSchemaManagerRecord, LocalDateTime> MODIFY_DT = createField("MODIFY_DT", org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false).defaultValue(org.jooq.impl.DSL.inline("1000-01-01 00:00:00", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "修改日时");

    /**
     * The column <code>SYS_SCHEMA_MANAGER.COMMIT_ID</code>.
     */
    public final TableField<SysSchemaManagerRecord, Long> COMMIT_ID = createField("COMMIT_ID", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "提交ID");

    /**
     * The column <code>SYS_SCHEMA_MANAGER.LOG_UPDATE</code>.
     */
    public final TableField<SysSchemaManagerRecord, Boolean> LOG_UPDATE = createField("LOG_UPDATE", org.jooq.impl.SQLDataType.BOOLEAN.nullable(false).defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.BOOLEAN)), this, "保留被更新记录");

    /**
     * The column <code>SYS_SCHEMA_MANAGER.LOG_DELETE</code>.
     */
    public final TableField<SysSchemaManagerRecord, Boolean> LOG_DELETE = createField("LOG_DELETE", org.jooq.impl.SQLDataType.BOOLEAN.nullable(false).defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.BOOLEAN)), this, "保留被删除记录");

    /**
     * The column <code>SYS_SCHEMA_MANAGER.SHARD_AUTO</code>.
     */
    public final TableField<SysSchemaManagerRecord, Integer> SHARD_AUTO = createField("SHARD_AUTO", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.INTEGER)), this, "自动水平分表数量，1为不分表。");

    /**
     * Create a <code>SYS_SCHEMA_MANAGER</code> table reference
     */
    public SysSchemaManager() {
        this(DSL.name("SYS_SCHEMA_MANAGER"), null);
    }

    /**
     * Create an aliased <code>SYS_SCHEMA_MANAGER</code> table reference
     */
    public SysSchemaManager(String alias) {
        this(DSL.name(alias), SYS_SCHEMA_MANAGER);
    }

    /**
     * Create an aliased <code>SYS_SCHEMA_MANAGER</code> table reference
     */
    public SysSchemaManager(Name alias) {
        this(alias, SYS_SCHEMA_MANAGER);
    }

    private SysSchemaManager(Name alias, Table<SysSchemaManagerRecord> aliased) {
        this(alias, aliased, null);
    }

    private SysSchemaManager(Name alias, Table<SysSchemaManagerRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("表结构管理"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return DefaultSchema.DEFAULT_SCHEMA;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<SysSchemaManagerRecord> getPrimaryKey() {
        return Internal.createUniqueKey(pro.fessional.wings.oracle.database.autogen.tables.SysSchemaManager.SYS_SCHEMA_MANAGER, "KEY_SYS_SCHEMA_MANAGER_PRIMARY", pro.fessional.wings.oracle.database.autogen.tables.SysSchemaManager.SYS_SCHEMA_MANAGER.TABLE_NAME);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<SysSchemaManagerRecord>> getKeys() {
        return Arrays.<UniqueKey<SysSchemaManagerRecord>>asList(
              Internal.createUniqueKey(pro.fessional.wings.oracle.database.autogen.tables.SysSchemaManager.SYS_SCHEMA_MANAGER, "KEY_SYS_SCHEMA_MANAGER_PRIMARY", pro.fessional.wings.oracle.database.autogen.tables.SysSchemaManager.SYS_SCHEMA_MANAGER.TABLE_NAME)
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysSchemaManager as(String alias) {
        return new SysSchemaManager(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysSchemaManager as(Name alias) {
        return new SysSchemaManager(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public SysSchemaManager rename(String name) {
        return new SysSchemaManager(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public SysSchemaManager rename(Name name) {
        return new SysSchemaManager(name, null);
    }
}
