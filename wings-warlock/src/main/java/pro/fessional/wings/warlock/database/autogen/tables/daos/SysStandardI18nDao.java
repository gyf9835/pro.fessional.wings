/*
 * This file is generated by jOOQ.
 */
package pro.fessional.wings.warlock.database.autogen.tables.daos;


import org.jooq.Configuration;
import org.jooq.Record4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pro.fessional.wings.faceless.database.jooq.WingsJooqDaoImpl;
import pro.fessional.wings.warlock.database.autogen.tables.SysStandardI18nTable;
import pro.fessional.wings.warlock.database.autogen.tables.pojos.SysStandardI18n;
import pro.fessional.wings.warlock.database.autogen.tables.records.SysStandardI18nRecord;

import javax.annotation.Generated;
import java.util.List;


/**
 * The table <code>wings_warlock.sys_standard_i18n</code>.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.14.4",
        "schema version:2020102401"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
@Repository
public class SysStandardI18nDao extends WingsJooqDaoImpl<SysStandardI18nTable, SysStandardI18nRecord, SysStandardI18n, Record4<String, String, String, String>> {

    /**
     * Create a new SysStandardI18nDao without any configuration
     */
    public SysStandardI18nDao() {
        super(SysStandardI18nTable.SysStandardI18n, SysStandardI18n.class);
    }

    /**
     * Create a new SysStandardI18nDao with an attached configuration
     */
    @Autowired
    public SysStandardI18nDao(Configuration configuration) {
        super(SysStandardI18nTable.SysStandardI18n, SysStandardI18n.class, configuration);
    }

    @Override
    public Record4<String, String, String, String> getId(SysStandardI18n object) {
        return compositeKeyRecord(object.getBase(), object.getKind(), object.getUkey(), object.getLang());
    }

    /**
     * Fetch records that have <code>base BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<SysStandardI18n> fetchRangeOfBase(String lowerInclusive, String upperInclusive) {
        return fetchRange(SysStandardI18nTable.SysStandardI18n.Base, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>base IN (values)</code>
     */
    public List<SysStandardI18n> fetchByBase(String... values) {
        return fetch(SysStandardI18nTable.SysStandardI18n.Base, values);
    }

    /**
     * Fetch records that have <code>kind BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<SysStandardI18n> fetchRangeOfKind(String lowerInclusive, String upperInclusive) {
        return fetchRange(SysStandardI18nTable.SysStandardI18n.Kind, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>kind IN (values)</code>
     */
    public List<SysStandardI18n> fetchByKind(String... values) {
        return fetch(SysStandardI18nTable.SysStandardI18n.Kind, values);
    }

    /**
     * Fetch records that have <code>ukey BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<SysStandardI18n> fetchRangeOfUkey(String lowerInclusive, String upperInclusive) {
        return fetchRange(SysStandardI18nTable.SysStandardI18n.Ukey, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>ukey IN (values)</code>
     */
    public List<SysStandardI18n> fetchByUkey(String... values) {
        return fetch(SysStandardI18nTable.SysStandardI18n.Ukey, values);
    }

    /**
     * Fetch records that have <code>lang BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<SysStandardI18n> fetchRangeOfLang(String lowerInclusive, String upperInclusive) {
        return fetchRange(SysStandardI18nTable.SysStandardI18n.Lang, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>lang IN (values)</code>
     */
    public List<SysStandardI18n> fetchByLang(String... values) {
        return fetch(SysStandardI18nTable.SysStandardI18n.Lang, values);
    }

    /**
     * Fetch records that have <code>hint BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<SysStandardI18n> fetchRangeOfHint(String lowerInclusive, String upperInclusive) {
        return fetchRange(SysStandardI18nTable.SysStandardI18n.Hint, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>hint IN (values)</code>
     */
    public List<SysStandardI18n> fetchByHint(String... values) {
        return fetch(SysStandardI18nTable.SysStandardI18n.Hint, values);
    }
}
