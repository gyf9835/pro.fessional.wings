/*
 * This file is generated by jOOQ.
 */
package pro.fessional.wings.warlock.database.autogen.tables;


import org.jetbrains.annotations.NotNull;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.Row14;
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
import pro.fessional.wings.warlock.database.autogen.DefaultSchema;
import pro.fessional.wings.warlock.database.autogen.tables.records.WinUserAnthnRecord;

import javax.annotation.Generated;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The table <code>wings_warlock.win_user_anthn</code>.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.14.4",
        "schema version:2020102402"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class WinUserAnthnTable extends TableImpl<WinUserAnthnRecord> implements WingsJournalTable<WinUserAnthnTable>, LightIdAware {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>win_user_anthn</code>
     */
    public static final WinUserAnthnTable WinUserAnthn = new WinUserAnthnTable();
        public static final WinUserAnthnTable asC2 = WinUserAnthn.as("c2");

    /**
     * The class holding records for this type
     */
    @Override
    public Class<WinUserAnthnRecord> getRecordType() {
        return WinUserAnthnRecord.class;
    }

    /**
     * The column <code>win_user_anthn.id</code>.
     */
    public final TableField<WinUserAnthnRecord, Long> Id = createField(DSL.name("id"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>win_user_anthn.create_dt</code>.
     */
    public final TableField<WinUserAnthnRecord, LocalDateTime> CreateDt = createField(DSL.name("create_dt"), SQLDataType.LOCALDATETIME(3).nullable(false).defaultValue(DSL.field("CURRENT_TIMESTAMP(3)", SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>win_user_anthn.modify_dt</code>.
     */
    public final TableField<WinUserAnthnRecord, LocalDateTime> ModifyDt = createField(DSL.name("modify_dt"), SQLDataType.LOCALDATETIME(3).nullable(false).defaultValue(DSL.inline("1000-01-01 00:00:00.000", SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>win_user_anthn.delete_dt</code>.
     */
    public final TableField<WinUserAnthnRecord, LocalDateTime> DeleteDt = createField(DSL.name("delete_dt"), SQLDataType.LOCALDATETIME(3).nullable(false).defaultValue(DSL.inline("1000-01-01 00:00:00.000", SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>win_user_anthn.commit_id</code>.
     */
    public final TableField<WinUserAnthnRecord, Long> CommitId = createField(DSL.name("commit_id"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>win_user_anthn.user_id</code>.
     */
    public final TableField<WinUserAnthnRecord, Long> UserId = createField(DSL.name("user_id"), SQLDataType.BIGINT.nullable(false).defaultValue(DSL.inline("0", SQLDataType.BIGINT)), this, "");

    /**
     * The column <code>win_user_anthn.auth_type</code>.
     */
    public final TableField<WinUserAnthnRecord, String> AuthType = createField(DSL.name("auth_type"), SQLDataType.VARCHAR(10).nullable(false), this, "");

    /**
     * The column <code>win_user_anthn.username</code>.
     */
    public final TableField<WinUserAnthnRecord, String> Username = createField(DSL.name("username"), SQLDataType.VARCHAR(200).nullable(false), this, "");

    /**
     * The column <code>win_user_anthn.password</code>.
     */
    public final TableField<WinUserAnthnRecord, String> Password = createField(DSL.name("password"), SQLDataType.VARCHAR(200).nullable(false).defaultValue(DSL.inline("", SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>win_user_anthn.extra_para</code>.
     */
    public final TableField<WinUserAnthnRecord, String> ExtraPara = createField(DSL.name("extra_para"), SQLDataType.VARCHAR(3000).nullable(false).defaultValue(DSL.inline("", SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>win_user_anthn.extra_user</code>.
     */
    public final TableField<WinUserAnthnRecord, String> ExtraUser = createField(DSL.name("extra_user"), SQLDataType.VARCHAR(9000).nullable(false).defaultValue(DSL.inline("", SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>win_user_anthn.expired_dt</code>.
     */
    public final TableField<WinUserAnthnRecord, LocalDateTime> ExpiredDt = createField(DSL.name("expired_dt"), SQLDataType.LOCALDATETIME(3).nullable(false).defaultValue(DSL.inline("1000-01-01 00:00:00.000", SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>win_user_anthn.failed_cnt</code>.
     */
    public final TableField<WinUserAnthnRecord, Integer> FailedCnt = createField(DSL.name("failed_cnt"), SQLDataType.INTEGER.nullable(false).defaultValue(DSL.inline("0", SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>win_user_anthn.failed_max</code>.
     */
    public final TableField<WinUserAnthnRecord, Integer> FailedMax = createField(DSL.name("failed_max"), SQLDataType.INTEGER.nullable(false).defaultValue(DSL.inline("5", SQLDataType.INTEGER)), this, "");

    private WinUserAnthnTable(Name alias, Table<WinUserAnthnRecord> aliased) {
        this(alias, aliased, null);
    }

    private WinUserAnthnTable(Name alias, Table<WinUserAnthnRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>win_user_anthn</code> table reference
     */
    public WinUserAnthnTable(String alias) {
        this(DSL.name(alias), WinUserAnthn);
    }

    /**
     * Create an aliased <code>win_user_anthn</code> table reference
     */
    public WinUserAnthnTable(Name alias) {
        this(alias, WinUserAnthn);
    }

    /**
     * Create a <code>win_user_anthn</code> table reference
     */
    public WinUserAnthnTable() {
        this(DSL.name("win_user_anthn"), null);
    }

    @Override
    public Schema getSchema() {
        return DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public UniqueKey<WinUserAnthnRecord> getPrimaryKey() {
        return Internal.createUniqueKey(WinUserAnthnTable.WinUserAnthn, DSL.name("KEY_win_user_anthn_PRIMARY"), new TableField[] { WinUserAnthnTable.WinUserAnthn.Id }, true);
    }

    @Override
    public List<UniqueKey<WinUserAnthnRecord>> getKeys() {
        return Arrays.<UniqueKey<WinUserAnthnRecord>>asList(
              Internal.createUniqueKey(WinUserAnthnTable.WinUserAnthn, DSL.name("KEY_win_user_anthn_PRIMARY"), new TableField[] { WinUserAnthnTable.WinUserAnthn.Id }, true)
        );
    }

    @Override
    public WinUserAnthnTable as(String alias) {
        return new WinUserAnthnTable(DSL.name(alias), this);
    }

    @Override
    public WinUserAnthnTable as(Name alias) {
        return new WinUserAnthnTable(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public WinUserAnthnTable rename(String name) {
        return new WinUserAnthnTable(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public WinUserAnthnTable rename(Name name) {
        return new WinUserAnthnTable(name, null);
    }

    // -------------------------------------------------------------------------
    // Row14 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row14<Long, LocalDateTime, LocalDateTime, LocalDateTime, Long, Long, String, String, String, String, String, LocalDateTime, Integer, Integer> fieldsRow() {
        return (Row14) super.fieldsRow();
    }

    /**
     * LightIdAware seqName
     */
    @Override
    @NotNull
    public String getSeqName() {
            return "win_user_anthn";
    }


    /**
     * alias C2
     */
    @Override
    @NotNull
    public WinUserAnthnTable getAliasTable() {
            return asC2;
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