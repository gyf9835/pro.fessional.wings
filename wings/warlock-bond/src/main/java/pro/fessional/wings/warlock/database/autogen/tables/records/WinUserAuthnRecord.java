/*
 * This file is generated by jOOQ.
 */
package pro.fessional.wings.warlock.database.autogen.tables.records;


import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record14;
import org.jooq.Row14;
import org.jooq.impl.UpdatableRecordImpl;
import pro.fessional.wings.warlock.database.autogen.tables.WinUserAuthnTable;
import pro.fessional.wings.warlock.database.autogen.tables.interfaces.IWinUserAuthn;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


/**
 * The table <code>wings_warlock.win_user_authn</code>.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.14.4",
        "schema version:2020102403"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
@Entity
@Table(
    name = "win_user_authn",
    uniqueConstraints = {
        @UniqueConstraint(name = "KEY_win_user_authn_PRIMARY", columnNames = { "id" })
    }
)
public class WinUserAuthnRecord extends UpdatableRecordImpl<WinUserAuthnRecord> implements Record14<Long, LocalDateTime, LocalDateTime, LocalDateTime, Long, Long, String, String, String, String, String, LocalDateTime, Integer, Integer>, IWinUserAuthn {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>win_user_authn.id</code>.
     */
    @Override
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>win_user_authn.id</code>.
     */
    @Id
    @Column(name = "id", nullable = false, precision = 19)
    @NotNull
    @Override
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>win_user_authn.create_dt</code>.
     */
    @Override
    public void setCreateDt(LocalDateTime value) {
        set(1, value);
    }

    /**
     * Getter for <code>win_user_authn.create_dt</code>.
     */
    @Column(name = "create_dt", nullable = false, precision = 3)
    @Override
    public LocalDateTime getCreateDt() {
        return (LocalDateTime) get(1);
    }

    /**
     * Setter for <code>win_user_authn.modify_dt</code>.
     */
    @Override
    public void setModifyDt(LocalDateTime value) {
        set(2, value);
    }

    /**
     * Getter for <code>win_user_authn.modify_dt</code>.
     */
    @Column(name = "modify_dt", nullable = false, precision = 3)
    @Override
    public LocalDateTime getModifyDt() {
        return (LocalDateTime) get(2);
    }

    /**
     * Setter for <code>win_user_authn.delete_dt</code>.
     */
    @Override
    public void setDeleteDt(LocalDateTime value) {
        set(3, value);
    }

    /**
     * Getter for <code>win_user_authn.delete_dt</code>.
     */
    @Column(name = "delete_dt", nullable = false, precision = 3)
    @Override
    public LocalDateTime getDeleteDt() {
        return (LocalDateTime) get(3);
    }

    /**
     * Setter for <code>win_user_authn.commit_id</code>.
     */
    @Override
    public void setCommitId(Long value) {
        set(4, value);
    }

    /**
     * Getter for <code>win_user_authn.commit_id</code>.
     */
    @Column(name = "commit_id", nullable = false, precision = 19)
    @NotNull
    @Override
    public Long getCommitId() {
        return (Long) get(4);
    }

    /**
     * Setter for <code>win_user_authn.user_id</code>.
     */
    @Override
    public void setUserId(Long value) {
        set(5, value);
    }

    /**
     * Getter for <code>win_user_authn.user_id</code>.
     */
    @Column(name = "user_id", nullable = false, precision = 19)
    @Override
    public Long getUserId() {
        return (Long) get(5);
    }

    /**
     * Setter for <code>win_user_authn.auth_type</code>.
     */
    @Override
    public void setAuthType(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>win_user_authn.auth_type</code>.
     */
    @Column(name = "auth_type", nullable = false, length = 10)
    @NotNull
    @Size(max = 10)
    @Override
    public String getAuthType() {
        return (String) get(6);
    }

    /**
     * Setter for <code>win_user_authn.username</code>.
     */
    @Override
    public void setUsername(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>win_user_authn.username</code>.
     */
    @Column(name = "username", nullable = false, length = 200)
    @NotNull
    @Size(max = 200)
    @Override
    public String getUsername() {
        return (String) get(7);
    }

    /**
     * Setter for <code>win_user_authn.password</code>.
     */
    @Override
    public void setPassword(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>win_user_authn.password</code>.
     */
    @Column(name = "password", nullable = false, length = 200)
    @Size(max = 200)
    @Override
    public String getPassword() {
        return (String) get(8);
    }

    /**
     * Setter for <code>win_user_authn.extra_para</code>.
     */
    @Override
    public void setExtraPara(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>win_user_authn.extra_para</code>.
     */
    @Column(name = "extra_para", nullable = false, length = 3000)
    @Size(max = 3000)
    @Override
    public String getExtraPara() {
        return (String) get(9);
    }

    /**
     * Setter for <code>win_user_authn.extra_user</code>.
     */
    @Override
    public void setExtraUser(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>win_user_authn.extra_user</code>.
     */
    @Column(name = "extra_user", nullable = false, length = 9000)
    @Size(max = 9000)
    @Override
    public String getExtraUser() {
        return (String) get(10);
    }

    /**
     * Setter for <code>win_user_authn.expired_dt</code>.
     */
    @Override
    public void setExpiredDt(LocalDateTime value) {
        set(11, value);
    }

    /**
     * Getter for <code>win_user_authn.expired_dt</code>.
     */
    @Column(name = "expired_dt", nullable = false, precision = 3)
    @Override
    public LocalDateTime getExpiredDt() {
        return (LocalDateTime) get(11);
    }

    /**
     * Setter for <code>win_user_authn.failed_cnt</code>.
     */
    @Override
    public void setFailedCnt(Integer value) {
        set(12, value);
    }

    /**
     * Getter for <code>win_user_authn.failed_cnt</code>.
     */
    @Column(name = "failed_cnt", nullable = false, precision = 10)
    @Override
    public Integer getFailedCnt() {
        return (Integer) get(12);
    }

    /**
     * Setter for <code>win_user_authn.failed_max</code>.
     */
    @Override
    public void setFailedMax(Integer value) {
        set(13, value);
    }

    /**
     * Getter for <code>win_user_authn.failed_max</code>.
     */
    @Column(name = "failed_max", nullable = false, precision = 10)
    @Override
    public Integer getFailedMax() {
        return (Integer) get(13);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record14 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row14<Long, LocalDateTime, LocalDateTime, LocalDateTime, Long, Long, String, String, String, String, String, LocalDateTime, Integer, Integer> fieldsRow() {
        return (Row14) super.fieldsRow();
    }

    @Override
    public Row14<Long, LocalDateTime, LocalDateTime, LocalDateTime, Long, Long, String, String, String, String, String, LocalDateTime, Integer, Integer> valuesRow() {
        return (Row14) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return WinUserAuthnTable.WinUserAuthn.Id;
    }

    @Override
    public Field<LocalDateTime> field2() {
        return WinUserAuthnTable.WinUserAuthn.CreateDt;
    }

    @Override
    public Field<LocalDateTime> field3() {
        return WinUserAuthnTable.WinUserAuthn.ModifyDt;
    }

    @Override
    public Field<LocalDateTime> field4() {
        return WinUserAuthnTable.WinUserAuthn.DeleteDt;
    }

    @Override
    public Field<Long> field5() {
        return WinUserAuthnTable.WinUserAuthn.CommitId;
    }

    @Override
    public Field<Long> field6() {
        return WinUserAuthnTable.WinUserAuthn.UserId;
    }

    @Override
    public Field<String> field7() {
        return WinUserAuthnTable.WinUserAuthn.AuthType;
    }

    @Override
    public Field<String> field8() {
        return WinUserAuthnTable.WinUserAuthn.Username;
    }

    @Override
    public Field<String> field9() {
        return WinUserAuthnTable.WinUserAuthn.Password;
    }

    @Override
    public Field<String> field10() {
        return WinUserAuthnTable.WinUserAuthn.ExtraPara;
    }

    @Override
    public Field<String> field11() {
        return WinUserAuthnTable.WinUserAuthn.ExtraUser;
    }

    @Override
    public Field<LocalDateTime> field12() {
        return WinUserAuthnTable.WinUserAuthn.ExpiredDt;
    }

    @Override
    public Field<Integer> field13() {
        return WinUserAuthnTable.WinUserAuthn.FailedCnt;
    }

    @Override
    public Field<Integer> field14() {
        return WinUserAuthnTable.WinUserAuthn.FailedMax;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public LocalDateTime component2() {
        return getCreateDt();
    }

    @Override
    public LocalDateTime component3() {
        return getModifyDt();
    }

    @Override
    public LocalDateTime component4() {
        return getDeleteDt();
    }

    @Override
    public Long component5() {
        return getCommitId();
    }

    @Override
    public Long component6() {
        return getUserId();
    }

    @Override
    public String component7() {
        return getAuthType();
    }

    @Override
    public String component8() {
        return getUsername();
    }

    @Override
    public String component9() {
        return getPassword();
    }

    @Override
    public String component10() {
        return getExtraPara();
    }

    @Override
    public String component11() {
        return getExtraUser();
    }

    @Override
    public LocalDateTime component12() {
        return getExpiredDt();
    }

    @Override
    public Integer component13() {
        return getFailedCnt();
    }

    @Override
    public Integer component14() {
        return getFailedMax();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public LocalDateTime value2() {
        return getCreateDt();
    }

    @Override
    public LocalDateTime value3() {
        return getModifyDt();
    }

    @Override
    public LocalDateTime value4() {
        return getDeleteDt();
    }

    @Override
    public Long value5() {
        return getCommitId();
    }

    @Override
    public Long value6() {
        return getUserId();
    }

    @Override
    public String value7() {
        return getAuthType();
    }

    @Override
    public String value8() {
        return getUsername();
    }

    @Override
    public String value9() {
        return getPassword();
    }

    @Override
    public String value10() {
        return getExtraPara();
    }

    @Override
    public String value11() {
        return getExtraUser();
    }

    @Override
    public LocalDateTime value12() {
        return getExpiredDt();
    }

    @Override
    public Integer value13() {
        return getFailedCnt();
    }

    @Override
    public Integer value14() {
        return getFailedMax();
    }

    @Override
    public WinUserAuthnRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public WinUserAuthnRecord value2(LocalDateTime value) {
        setCreateDt(value);
        return this;
    }

    @Override
    public WinUserAuthnRecord value3(LocalDateTime value) {
        setModifyDt(value);
        return this;
    }

    @Override
    public WinUserAuthnRecord value4(LocalDateTime value) {
        setDeleteDt(value);
        return this;
    }

    @Override
    public WinUserAuthnRecord value5(Long value) {
        setCommitId(value);
        return this;
    }

    @Override
    public WinUserAuthnRecord value6(Long value) {
        setUserId(value);
        return this;
    }

    @Override
    public WinUserAuthnRecord value7(String value) {
        setAuthType(value);
        return this;
    }

    @Override
    public WinUserAuthnRecord value8(String value) {
        setUsername(value);
        return this;
    }

    @Override
    public WinUserAuthnRecord value9(String value) {
        setPassword(value);
        return this;
    }

    @Override
    public WinUserAuthnRecord value10(String value) {
        setExtraPara(value);
        return this;
    }

    @Override
    public WinUserAuthnRecord value11(String value) {
        setExtraUser(value);
        return this;
    }

    @Override
    public WinUserAuthnRecord value12(LocalDateTime value) {
        setExpiredDt(value);
        return this;
    }

    @Override
    public WinUserAuthnRecord value13(Integer value) {
        setFailedCnt(value);
        return this;
    }

    @Override
    public WinUserAuthnRecord value14(Integer value) {
        setFailedMax(value);
        return this;
    }

    @Override
    public WinUserAuthnRecord values(Long value1, LocalDateTime value2, LocalDateTime value3, LocalDateTime value4, Long value5, Long value6, String value7, String value8, String value9, String value10, String value11, LocalDateTime value12, Integer value13, Integer value14) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        value11(value11);
        value12(value12);
        value13(value13);
        value14(value14);
        return this;
    }

    // -------------------------------------------------------------------------
    // FROM and INTO
    // -------------------------------------------------------------------------

    @Override
    public void from(IWinUserAuthn from) {
        setId(from.getId());
        setCreateDt(from.getCreateDt());
        setModifyDt(from.getModifyDt());
        setDeleteDt(from.getDeleteDt());
        setCommitId(from.getCommitId());
        setUserId(from.getUserId());
        setAuthType(from.getAuthType());
        setUsername(from.getUsername());
        setPassword(from.getPassword());
        setExtraPara(from.getExtraPara());
        setExtraUser(from.getExtraUser());
        setExpiredDt(from.getExpiredDt());
        setFailedCnt(from.getFailedCnt());
        setFailedMax(from.getFailedMax());
    }

    @Override
    public <E extends IWinUserAuthn> E into(E into) {
        into.from(this);
        return into;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached WinUserAuthnRecord
     */
    public WinUserAuthnRecord() {
        super(WinUserAuthnTable.WinUserAuthn);
    }

    /**
     * Create a detached, initialised WinUserAuthnRecord
     */
    public WinUserAuthnRecord(Long id, LocalDateTime createDt, LocalDateTime modifyDt, LocalDateTime deleteDt, Long commitId, Long userId, String authType, String username, String password, String extraPara, String extraUser, LocalDateTime expiredDt, Integer failedCnt, Integer failedMax) {
        super(WinUserAuthnTable.WinUserAuthn);

        setId(id);
        setCreateDt(createDt);
        setModifyDt(modifyDt);
        setDeleteDt(deleteDt);
        setCommitId(commitId);
        setUserId(userId);
        setAuthType(authType);
        setUsername(username);
        setPassword(password);
        setExtraPara(extraPara);
        setExtraUser(extraUser);
        setExpiredDt(expiredDt);
        setFailedCnt(failedCnt);
        setFailedMax(failedMax);
    }
}
