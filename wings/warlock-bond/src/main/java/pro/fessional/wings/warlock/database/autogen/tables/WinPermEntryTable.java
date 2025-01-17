/*
 * This file is generated by jOOQ.
 */
package pro.fessional.wings.warlock.database.autogen.tables;


import org.jetbrains.annotations.NotNull;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.Row8;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import pro.fessional.wings.faceless.convention.EmptyValue;
import pro.fessional.wings.faceless.database.jooq.WingsJournalTable;
import pro.fessional.wings.faceless.service.journal.JournalService;
import pro.fessional.wings.faceless.service.lightid.LightIdAware;
import pro.fessional.wings.warlock.database.autogen.DefaultSchemaWarlockBond;
import pro.fessional.wings.warlock.database.autogen.tables.records.WinPermEntryRecord;

import javax.annotation.processing.Generated;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The table <code>wings_warlock.win_perm_entry</code>.
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
public class WinPermEntryTable extends TableImpl<WinPermEntryRecord> implements WingsJournalTable<WinPermEntryTable>, LightIdAware {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>win_perm_entry</code>
     */
    public static final WinPermEntryTable WinPermEntry = new WinPermEntryTable();
    public static final WinPermEntryTable asM2 = WinPermEntry.as(pro.fessional.wings.faceless.database.jooq.WingsJooqEnv.uniqueAlias());

    /**
     * The class holding records for this type
     */
    @Override
    public Class<WinPermEntryRecord> getRecordType() {
        return WinPermEntryRecord.class;
    }

    /**
     * The column <code>win_perm_entry.id</code>.
     */
    public final TableField<WinPermEntryRecord, Long> Id = createField(DSL.name("id"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>win_perm_entry.create_dt</code>.
     */
    public final TableField<WinPermEntryRecord, LocalDateTime> CreateDt = createField(DSL.name("create_dt"), SQLDataType.LOCALDATETIME(3).nullable(false).defaultValue(DSL.field("CURRENT_TIMESTAMP(3)", SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>win_perm_entry.modify_dt</code>.
     */
    public final TableField<WinPermEntryRecord, LocalDateTime> ModifyDt = createField(DSL.name("modify_dt"), SQLDataType.LOCALDATETIME(3).nullable(false).defaultValue(DSL.inline("1000-01-01 00:00:00.000", SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>win_perm_entry.delete_dt</code>.
     */
    public final TableField<WinPermEntryRecord, LocalDateTime> DeleteDt = createField(DSL.name("delete_dt"), SQLDataType.LOCALDATETIME(3).nullable(false).defaultValue(DSL.inline("1000-01-01 00:00:00.000", SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>win_perm_entry.commit_id</code>.
     */
    public final TableField<WinPermEntryRecord, Long> CommitId = createField(DSL.name("commit_id"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>win_perm_entry.scopes</code>.
     */
    public final TableField<WinPermEntryRecord, String> Scopes = createField(DSL.name("scopes"), SQLDataType.VARCHAR(200).nullable(false), this, "");

    /**
     * The column <code>win_perm_entry.action</code>.
     */
    public final TableField<WinPermEntryRecord, String> Action = createField(DSL.name("action"), SQLDataType.VARCHAR(50).nullable(false), this, "");

    /**
     * The column <code>win_perm_entry.remark</code>.
     */
    public final TableField<WinPermEntryRecord, String> Remark = createField(DSL.name("remark"), SQLDataType.VARCHAR(500).nullable(false).defaultValue(DSL.inline("", SQLDataType.VARCHAR)), this, "");

    private WinPermEntryTable(Name alias, Table<WinPermEntryRecord> aliased) {
        this(alias, aliased, null);
    }

    private WinPermEntryTable(Name alias, Table<WinPermEntryRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>win_perm_entry</code> table reference
     */
    public WinPermEntryTable(String alias) {
        this(DSL.name(alias), WinPermEntry);
    }

    /**
     * Create an aliased <code>win_perm_entry</code> table reference
     */
    public WinPermEntryTable(Name alias) {
        this(alias, WinPermEntry);
    }

    /**
     * Create a <code>win_perm_entry</code> table reference
     */
    public WinPermEntryTable() {
        this(DSL.name("win_perm_entry"), null);
    }

    @Override
    public Schema getSchema() {
        return DefaultSchemaWarlockBond.DEFAULT_SCHEMA;
    }

    @Override
    public UniqueKey<WinPermEntryRecord> getPrimaryKey() {
        return Internal.createUniqueKey(WinPermEntryTable.WinPermEntry, DSL.name("KEY_win_perm_entry_PRIMARY"), new TableField[] { WinPermEntryTable.WinPermEntry.Id }, true);
    }

    @Override
    public List<UniqueKey<WinPermEntryRecord>> getKeys() {
        return Arrays.<UniqueKey<WinPermEntryRecord>>asList(
              Internal.createUniqueKey(WinPermEntryTable.WinPermEntry, DSL.name("KEY_win_perm_entry_PRIMARY"), new TableField[] { WinPermEntryTable.WinPermEntry.Id }, true)
        );
    }

    @Override
    public WinPermEntryTable as(String alias) {
        return new WinPermEntryTable(DSL.name(alias), this);
    }

    @Override
    public WinPermEntryTable as(Name alias) {
        return new WinPermEntryTable(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public WinPermEntryTable rename(String name) {
        return new WinPermEntryTable(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public WinPermEntryTable rename(Name name) {
        return new WinPermEntryTable(name, null);
    }

    // -------------------------------------------------------------------------
    // Row8 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row8<Long, LocalDateTime, LocalDateTime, LocalDateTime, Long, String, String, String> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    /**
     * LightIdAware seqName
     */
    @Override
    @NotNull
    public String getSeqName() {
        return "win_perm_entry";
    }


    /**
     * alias asM2
     */
    @Override
    @NotNull
    public WinPermEntryTable getAliasTable() {
        return asM2;
    }


    /**
     * The colDel <code>delete_dt</code> condition
     */
    public final Condition onlyDiedData = DeleteDt.gt(EmptyValue.DATE_TIME);
    public final Condition onlyLiveData = DeleteDt.eq(EmptyValue.DATE_TIME);

    @Override
    @NotNull
    public Condition getOnlyDied() {
        return onlyDiedData;
    }

    @Override
    @NotNull
    public Condition getOnlyLive() {
        return onlyLiveData;
    }

    @Override
    @NotNull
    public Map<Field<?>, ?> markDelete(JournalService.Journal commit) {
        Map<org.jooq.Field<?>, Object> map = new HashMap<>();
        map.put(DeleteDt, commit.getCommitDt());
        map.put(CommitId, commit.getCommitId());
        return map;
    }
}
