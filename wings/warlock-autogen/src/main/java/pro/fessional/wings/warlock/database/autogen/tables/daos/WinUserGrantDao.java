/*
 * This file is generated by jOOQ.
 */
package pro.fessional.wings.warlock.database.autogen.tables.daos;


import org.jooq.Configuration;
import org.jooq.Record3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pro.fessional.wings.faceless.database.jooq.WingsJooqDaoJournalImpl;
import pro.fessional.wings.warlock.database.autogen.tables.WinUserGrantTable;
import pro.fessional.wings.warlock.database.autogen.tables.pojos.WinUserGrant;
import pro.fessional.wings.warlock.database.autogen.tables.records.WinUserGrantRecord;
import pro.fessional.wings.warlock.enums.autogen.GrantType;

import javax.annotation.processing.Generated;
import java.time.LocalDateTime;
import java.util.List;


/**
 * The table <code>wings.win_user_grant</code>.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.17.7",
        "schema version:2020102701"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
@Repository
public class WinUserGrantDao extends WingsJooqDaoJournalImpl<WinUserGrantTable, WinUserGrantRecord, WinUserGrant, Record3<Long, GrantType, Long>> {

    /**
     * Create a new WinUserGrantDao without any configuration
     */
    public WinUserGrantDao() {
        super(WinUserGrantTable.WinUserGrant, WinUserGrant.class);
    }

    /**
     * Create a new WinUserGrantDao with an attached configuration
     */
    @Autowired
    public WinUserGrantDao(Configuration configuration) {
        super(WinUserGrantTable.WinUserGrant, WinUserGrant.class, configuration);
    }

    @Override
    public Record3<Long, GrantType, Long> getId(WinUserGrant object) {
        return compositeKeyRecord(object.getReferUser(), object.getGrantType(), object.getGrantEntry());
    }

    /**
     * Fetch records that have <code>refer_user BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<WinUserGrant> fetchRangeOfReferUser(Long lowerInclusive, Long upperInclusive) {
        return fetchRange(WinUserGrantTable.WinUserGrant.ReferUser, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>refer_user IN (values)</code>
     */
    public List<WinUserGrant> fetchByReferUser(Long... values) {
        return fetch(WinUserGrantTable.WinUserGrant.ReferUser, values);
    }

    /**
     * Fetch records that have <code>grant_type BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<WinUserGrant> fetchRangeOfGrantType(GrantType lowerInclusive, GrantType upperInclusive) {
        return fetchRange(WinUserGrantTable.WinUserGrant.GrantType, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>grant_type IN (values)</code>
     */
    public List<WinUserGrant> fetchByGrantType(GrantType... values) {
        return fetch(WinUserGrantTable.WinUserGrant.GrantType, values);
    }

    /**
     * Fetch records that have <code>grant_entry BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<WinUserGrant> fetchRangeOfGrantEntry(Long lowerInclusive, Long upperInclusive) {
        return fetchRange(WinUserGrantTable.WinUserGrant.GrantEntry, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>grant_entry IN (values)</code>
     */
    public List<WinUserGrant> fetchByGrantEntry(Long... values) {
        return fetch(WinUserGrantTable.WinUserGrant.GrantEntry, values);
    }

    /**
     * Fetch records that have <code>create_dt BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<WinUserGrant> fetchRangeOfCreateDt(LocalDateTime lowerInclusive, LocalDateTime upperInclusive) {
        return fetchRange(WinUserGrantTable.WinUserGrant.CreateDt, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>create_dt IN (values)</code>
     */
    public List<WinUserGrant> fetchByCreateDt(LocalDateTime... values) {
        return fetch(WinUserGrantTable.WinUserGrant.CreateDt, values);
    }

    /**
     * Fetch records that have <code>commit_id BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<WinUserGrant> fetchRangeOfCommitId(Long lowerInclusive, Long upperInclusive) {
        return fetchRange(WinUserGrantTable.WinUserGrant.CommitId, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>commit_id IN (values)</code>
     */
    public List<WinUserGrant> fetchByCommitId(Long... values) {
        return fetch(WinUserGrantTable.WinUserGrant.CommitId, values);
    }


    /**
     * Fetch records that have <code>refer_user BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<WinUserGrant> fetchRangeOfReferUserLive(Long lowerInclusive, Long upperInclusive) {
        return fetchRangeLive(WinUserGrantTable.WinUserGrant.ReferUser, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>refer_user IN (values)</code>
     */
    public List<WinUserGrant> fetchByReferUserLive(Long... values) {
        return fetchLive(WinUserGrantTable.WinUserGrant.ReferUser, values);
    }

    /**
     * Fetch records that have <code>grant_type BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<WinUserGrant> fetchRangeOfGrantTypeLive(GrantType lowerInclusive, GrantType upperInclusive) {
        return fetchRangeLive(WinUserGrantTable.WinUserGrant.GrantType, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>grant_type IN (values)</code>
     */
    public List<WinUserGrant> fetchByGrantTypeLive(GrantType... values) {
        return fetchLive(WinUserGrantTable.WinUserGrant.GrantType, values);
    }

    /**
     * Fetch records that have <code>grant_entry BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<WinUserGrant> fetchRangeOfGrantEntryLive(Long lowerInclusive, Long upperInclusive) {
        return fetchRangeLive(WinUserGrantTable.WinUserGrant.GrantEntry, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>grant_entry IN (values)</code>
     */
    public List<WinUserGrant> fetchByGrantEntryLive(Long... values) {
        return fetchLive(WinUserGrantTable.WinUserGrant.GrantEntry, values);
    }

    /**
     * Fetch records that have <code>create_dt BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<WinUserGrant> fetchRangeOfCreateDtLive(LocalDateTime lowerInclusive, LocalDateTime upperInclusive) {
        return fetchRangeLive(WinUserGrantTable.WinUserGrant.CreateDt, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>create_dt IN (values)</code>
     */
    public List<WinUserGrant> fetchByCreateDtLive(LocalDateTime... values) {
        return fetchLive(WinUserGrantTable.WinUserGrant.CreateDt, values);
    }

    /**
     * Fetch records that have <code>commit_id BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<WinUserGrant> fetchRangeOfCommitIdLive(Long lowerInclusive, Long upperInclusive) {
        return fetchRangeLive(WinUserGrantTable.WinUserGrant.CommitId, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>commit_id IN (values)</code>
     */
    public List<WinUserGrant> fetchByCommitIdLive(Long... values) {
        return fetchLive(WinUserGrantTable.WinUserGrant.CommitId, values);
    }
}