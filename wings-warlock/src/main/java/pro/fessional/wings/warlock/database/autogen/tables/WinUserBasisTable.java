/*
 * This file is generated by jOOQ.
 */
package pro.fessional.wings.warlock.database.autogen.tables;


import org.jetbrains.annotations.NotNull;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.Row13;
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
import pro.fessional.wings.faceless.database.jooq.converter.JooqConsEnumConverter;
import pro.fessional.wings.faceless.database.jooq.converter.JooqLocaleConverter;
import pro.fessional.wings.faceless.database.jooq.converter.JooqZoneIdConverter;
import pro.fessional.wings.faceless.service.journal.JournalService;
import pro.fessional.wings.faceless.service.lightid.LightIdAware;
import pro.fessional.wings.warlock.database.autogen.DefaultSchema;
import pro.fessional.wings.warlock.database.autogen.tables.records.WinUserBasisRecord;
import pro.fessional.wings.warlock.enums.autogen.UserGender;
import pro.fessional.wings.warlock.enums.autogen.UserStatus;

import javax.annotation.Generated;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The table <code>wings_warlock.win_user_basis</code>.
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
public class WinUserBasisTable extends TableImpl<WinUserBasisRecord> implements WingsJournalTable<WinUserBasisTable>, LightIdAware {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>win_user_basis</code>
     */
    public static final WinUserBasisTable WinUserBasis = new WinUserBasisTable();
        public static final WinUserBasisTable asB2 = WinUserBasis.as("b2");

    /**
     * The class holding records for this type
     */
    @Override
    public Class<WinUserBasisRecord> getRecordType() {
        return WinUserBasisRecord.class;
    }

    /**
     * The column <code>win_user_basis.id</code>.
     */
    public final TableField<WinUserBasisRecord, Long> Id = createField(DSL.name("id"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>win_user_basis.create_dt</code>.
     */
    public final TableField<WinUserBasisRecord, LocalDateTime> CreateDt = createField(DSL.name("create_dt"), SQLDataType.LOCALDATETIME(3).nullable(false).defaultValue(DSL.field("CURRENT_TIMESTAMP(3)", SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>win_user_basis.modify_dt</code>.
     */
    public final TableField<WinUserBasisRecord, LocalDateTime> ModifyDt = createField(DSL.name("modify_dt"), SQLDataType.LOCALDATETIME(3).nullable(false).defaultValue(DSL.inline("1000-01-01 00:00:00.000", SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>win_user_basis.delete_dt</code>.
     */
    public final TableField<WinUserBasisRecord, LocalDateTime> DeleteDt = createField(DSL.name("delete_dt"), SQLDataType.LOCALDATETIME(3).nullable(false).defaultValue(DSL.inline("1000-01-01 00:00:00.000", SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>win_user_basis.commit_id</code>.
     */
    public final TableField<WinUserBasisRecord, Long> CommitId = createField(DSL.name("commit_id"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>win_user_basis.nickname</code>.
     */
    public final TableField<WinUserBasisRecord, String> Nickname = createField(DSL.name("nickname"), SQLDataType.VARCHAR(50).nullable(false).defaultValue(DSL.inline("", SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>win_user_basis.passsalt</code>.
     */
    public final TableField<WinUserBasisRecord, String> Passsalt = createField(DSL.name("passsalt"), SQLDataType.VARCHAR(100).nullable(false).defaultValue(DSL.inline("", SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>win_user_basis.gender</code>.
     */
    public final TableField<WinUserBasisRecord, UserGender> Gender = createField(DSL.name("gender"), SQLDataType.INTEGER.nullable(false).defaultValue(DSL.inline("0", SQLDataType.INTEGER)), this, "", new JooqConsEnumConverter(pro.fessional.wings.warlock.enums.autogen.UserGender.class));

    /**
     * The column <code>win_user_basis.avatar</code>.
     */
    public final TableField<WinUserBasisRecord, String> Avatar = createField(DSL.name("avatar"), SQLDataType.VARCHAR(200).nullable(false).defaultValue(DSL.inline("", SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>win_user_basis.locale</code>.
     */
    public final TableField<WinUserBasisRecord, java.util.Locale> Locale = createField(DSL.name("locale"), SQLDataType.CHAR(5).nullable(false).defaultValue(DSL.inline("zh_CN", SQLDataType.CHAR)), this, "", new JooqLocaleConverter());

    /**
     * The column <code>win_user_basis.zoneid</code>.
     */
    public final TableField<WinUserBasisRecord, ZoneId> Zoneid = createField(DSL.name("zoneid"), SQLDataType.INTEGER.nullable(false).defaultValue(DSL.inline("1010201", SQLDataType.INTEGER)), this, "", new JooqZoneIdConverter());

    /**
     * The column <code>win_user_basis.remark</code>.
     */
    public final TableField<WinUserBasisRecord, String> Remark = createField(DSL.name("remark"), SQLDataType.VARCHAR(500).nullable(false).defaultValue(DSL.inline("", SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>win_user_basis.status</code>.
     */
    public final TableField<WinUserBasisRecord, UserStatus> Status = createField(DSL.name("status"), SQLDataType.INTEGER.nullable(false).defaultValue(DSL.inline("0", SQLDataType.INTEGER)), this, "", new JooqConsEnumConverter(pro.fessional.wings.warlock.enums.autogen.UserStatus.class));

    private WinUserBasisTable(Name alias, Table<WinUserBasisRecord> aliased) {
        this(alias, aliased, null);
    }

    private WinUserBasisTable(Name alias, Table<WinUserBasisRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>win_user_basis</code> table reference
     */
    public WinUserBasisTable(String alias) {
        this(DSL.name(alias), WinUserBasis);
    }

    /**
     * Create an aliased <code>win_user_basis</code> table reference
     */
    public WinUserBasisTable(Name alias) {
        this(alias, WinUserBasis);
    }

    /**
     * Create a <code>win_user_basis</code> table reference
     */
    public WinUserBasisTable() {
        this(DSL.name("win_user_basis"), null);
    }

    @Override
    public Schema getSchema() {
        return DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public UniqueKey<WinUserBasisRecord> getPrimaryKey() {
        return Internal.createUniqueKey(WinUserBasisTable.WinUserBasis, DSL.name("KEY_win_user_basis_PRIMARY"), new TableField[] { WinUserBasisTable.WinUserBasis.Id }, true);
    }

    @Override
    public List<UniqueKey<WinUserBasisRecord>> getKeys() {
        return Arrays.<UniqueKey<WinUserBasisRecord>>asList(
              Internal.createUniqueKey(WinUserBasisTable.WinUserBasis, DSL.name("KEY_win_user_basis_PRIMARY"), new TableField[] { WinUserBasisTable.WinUserBasis.Id }, true)
        );
    }

    @Override
    public WinUserBasisTable as(String alias) {
        return new WinUserBasisTable(DSL.name(alias), this);
    }

    @Override
    public WinUserBasisTable as(Name alias) {
        return new WinUserBasisTable(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public WinUserBasisTable rename(String name) {
        return new WinUserBasisTable(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public WinUserBasisTable rename(Name name) {
        return new WinUserBasisTable(name, null);
    }

    // -------------------------------------------------------------------------
    // Row13 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row13<Long, LocalDateTime, LocalDateTime, LocalDateTime, Long, String, String, UserGender, String, java.util.Locale, ZoneId, String, UserStatus> fieldsRow() {
        return (Row13) super.fieldsRow();
    }

    /**
     * LightIdAware seqName
     */
    @Override
    @NotNull
    public String getSeqName() {
            return "win_user_basis";
    }


    /**
     * alias B2
     */
    @Override
    @NotNull
    public WinUserBasisTable getAliasTable() {
            return asB2;
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
