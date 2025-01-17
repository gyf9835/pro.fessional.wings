/*
 * This file is generated by jOOQ.
 */
package pro.fessional.wings.faceless.database.autogen.tables.records;


import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;
import pro.fessional.wings.faceless.database.autogen.tables.SysConstantEnumTable;
import pro.fessional.wings.faceless.database.autogen.tables.interfaces.ISysConstantEnum;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * The table <code>wings.sys_constant_enum</code>.
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
@Entity
@Table(
    name = "sys_constant_enum",
    uniqueConstraints = {
        @UniqueConstraint(name = "KEY_sys_constant_enum_PRIMARY", columnNames = { "id" })
    }
)
public class SysConstantEnumRecord extends UpdatableRecordImpl<SysConstantEnumRecord> implements Record5<Integer, String, String, String, String>, ISysConstantEnum {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>sys_constant_enum.id</code>.
     */
    @Override
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>sys_constant_enum.id</code>.
     */
    @Id
    @Column(name = "id", nullable = false, precision = 10)
    @NotNull
    @Override
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>sys_constant_enum.type</code>.
     */
    @Override
    public void setType(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>sys_constant_enum.type</code>.
     */
    @Column(name = "type", nullable = false, length = 100)
    @NotNull
    @Size(max = 100)
    @Override
    public String getType() {
        return (String) get(1);
    }

    /**
     * Setter for <code>sys_constant_enum.code</code>.
     */
    @Override
    public void setCode(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>sys_constant_enum.code</code>.
     */
    @Column(name = "code", nullable = false, length = 100)
    @NotNull
    @Size(max = 100)
    @Override
    public String getCode() {
        return (String) get(2);
    }

    /**
     * Setter for <code>sys_constant_enum.hint</code>.
     */
    @Override
    public void setHint(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>sys_constant_enum.hint</code>.
     */
    @Column(name = "hint", nullable = false, length = 100)
    @NotNull
    @Size(max = 100)
    @Override
    public String getHint() {
        return (String) get(3);
    }

    /**
     * Setter for <code>sys_constant_enum.info</code>.
     */
    @Override
    public void setInfo(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>sys_constant_enum.info</code>.
     */
    @Column(name = "info", nullable = false, length = 500)
    @NotNull
    @Size(max = 500)
    @Override
    public String getInfo() {
        return (String) get(4);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record5 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row5<Integer, String, String, String, String> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    @Override
    public Row5<Integer, String, String, String, String> valuesRow() {
        return (Row5) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return SysConstantEnumTable.SysConstantEnum.Id;
    }

    @Override
    public Field<String> field2() {
        return SysConstantEnumTable.SysConstantEnum.Type;
    }

    @Override
    public Field<String> field3() {
        return SysConstantEnumTable.SysConstantEnum.Code;
    }

    @Override
    public Field<String> field4() {
        return SysConstantEnumTable.SysConstantEnum.Hint;
    }

    @Override
    public Field<String> field5() {
        return SysConstantEnumTable.SysConstantEnum.Info;
    }

    @Override
    public Integer component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getType();
    }

    @Override
    public String component3() {
        return getCode();
    }

    @Override
    public String component4() {
        return getHint();
    }

    @Override
    public String component5() {
        return getInfo();
    }

    @Override
    public Integer value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getType();
    }

    @Override
    public String value3() {
        return getCode();
    }

    @Override
    public String value4() {
        return getHint();
    }

    @Override
    public String value5() {
        return getInfo();
    }

    @Override
    public SysConstantEnumRecord value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public SysConstantEnumRecord value2(String value) {
        setType(value);
        return this;
    }

    @Override
    public SysConstantEnumRecord value3(String value) {
        setCode(value);
        return this;
    }

    @Override
    public SysConstantEnumRecord value4(String value) {
        setHint(value);
        return this;
    }

    @Override
    public SysConstantEnumRecord value5(String value) {
        setInfo(value);
        return this;
    }

    @Override
    public SysConstantEnumRecord values(Integer value1, String value2, String value3, String value4, String value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // FROM and INTO
    // -------------------------------------------------------------------------

    @Override
    public void from(ISysConstantEnum from) {
        setId(from.getId());
        setType(from.getType());
        setCode(from.getCode());
        setHint(from.getHint());
        setInfo(from.getInfo());
    }

    @Override
    public <E extends ISysConstantEnum> E into(E into) {
        into.from(this);
        return into;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached SysConstantEnumRecord
     */
    public SysConstantEnumRecord() {
        super(SysConstantEnumTable.SysConstantEnum);
    }

    /**
     * Create a detached, initialised SysConstantEnumRecord
     */
    public SysConstantEnumRecord(Integer id, String type, String code, String hint, String info) {
        super(SysConstantEnumTable.SysConstantEnum);

        setId(id);
        setType(type);
        setCode(code);
        setHint(hint);
        setInfo(info);
    }
}
