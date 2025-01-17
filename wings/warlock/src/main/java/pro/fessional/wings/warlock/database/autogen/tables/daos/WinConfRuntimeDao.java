/*
 * This file is generated by jOOQ.
 */
package pro.fessional.wings.warlock.database.autogen.tables.daos;


import org.jooq.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pro.fessional.wings.faceless.database.jooq.WingsJooqDaoAliasImpl;
import pro.fessional.wings.warlock.database.autogen.tables.WinConfRuntimeTable;
import pro.fessional.wings.warlock.database.autogen.tables.pojos.WinConfRuntime;
import pro.fessional.wings.warlock.database.autogen.tables.records.WinConfRuntimeRecord;

import javax.annotation.processing.Generated;
import java.util.List;


/**
 * The table <code>wings_warlock.win_conf_runtime</code>.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.14.15",
        "schema version:2021102301"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
@Repository
public class WinConfRuntimeDao extends WingsJooqDaoAliasImpl<WinConfRuntimeTable, WinConfRuntimeRecord, WinConfRuntime, String> {

    /**
     * Create a new WinConfRuntimeDao without any configuration
     */
    public WinConfRuntimeDao() {
        super(WinConfRuntimeTable.WinConfRuntime, WinConfRuntime.class);
    }

    /**
     * Create a new WinConfRuntimeDao with an attached configuration
     */
    @Autowired
    public WinConfRuntimeDao(Configuration configuration) {
        super(WinConfRuntimeTable.WinConfRuntime, WinConfRuntime.class, configuration);
    }

    @Override
    public String getId(WinConfRuntime object) {
        return object.getKey();
    }

    /**
     * Fetch records that have <code>key BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<WinConfRuntime> fetchRangeOfKey(String lowerInclusive, String upperInclusive) {
        return fetchRange(WinConfRuntimeTable.WinConfRuntime.Key, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>key IN (values)</code>
     */
    public List<WinConfRuntime> fetchByKey(String... values) {
        return fetch(WinConfRuntimeTable.WinConfRuntime.Key, values);
    }

    /**
     * Fetch a unique record that has <code>key = value</code>
     */
    public WinConfRuntime fetchOneByKey(String value) {
        return fetchOne(WinConfRuntimeTable.WinConfRuntime.Key, value);
    }

    /**
     * Fetch records that have <code>current BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<WinConfRuntime> fetchRangeOfCurrent(String lowerInclusive, String upperInclusive) {
        return fetchRange(WinConfRuntimeTable.WinConfRuntime.Current, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>current IN (values)</code>
     */
    public List<WinConfRuntime> fetchByCurrent(String... values) {
        return fetch(WinConfRuntimeTable.WinConfRuntime.Current, values);
    }

    /**
     * Fetch records that have <code>previous BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<WinConfRuntime> fetchRangeOfPrevious(String lowerInclusive, String upperInclusive) {
        return fetchRange(WinConfRuntimeTable.WinConfRuntime.Previous, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>previous IN (values)</code>
     */
    public List<WinConfRuntime> fetchByPrevious(String... values) {
        return fetch(WinConfRuntimeTable.WinConfRuntime.Previous, values);
    }

    /**
     * Fetch records that have <code>initial BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<WinConfRuntime> fetchRangeOfInitial(String lowerInclusive, String upperInclusive) {
        return fetchRange(WinConfRuntimeTable.WinConfRuntime.Initial, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>initial IN (values)</code>
     */
    public List<WinConfRuntime> fetchByInitial(String... values) {
        return fetch(WinConfRuntimeTable.WinConfRuntime.Initial, values);
    }

    /**
     * Fetch records that have <code>comment BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<WinConfRuntime> fetchRangeOfComment(String lowerInclusive, String upperInclusive) {
        return fetchRange(WinConfRuntimeTable.WinConfRuntime.Comment, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>comment IN (values)</code>
     */
    public List<WinConfRuntime> fetchByComment(String... values) {
        return fetch(WinConfRuntimeTable.WinConfRuntime.Comment, values);
    }

    /**
     * Fetch records that have <code>handler BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<WinConfRuntime> fetchRangeOfHandler(String lowerInclusive, String upperInclusive) {
        return fetchRange(WinConfRuntimeTable.WinConfRuntime.Handler, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>handler IN (values)</code>
     */
    public List<WinConfRuntime> fetchByHandler(String... values) {
        return fetch(WinConfRuntimeTable.WinConfRuntime.Handler, values);
    }
}
