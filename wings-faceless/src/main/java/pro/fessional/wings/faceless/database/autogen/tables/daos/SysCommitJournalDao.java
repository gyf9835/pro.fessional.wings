/*
 * This file is generated by jOOQ.
 */
package pro.fessional.wings.faceless.database.autogen.tables.daos;


import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pro.fessional.wings.faceless.database.autogen.tables.SysCommitJournalTable;
import pro.fessional.wings.faceless.database.autogen.tables.pojos.SysCommitJournal;
import pro.fessional.wings.faceless.database.autogen.tables.records.SysCommitJournalRecord;


/**
 * The table <code>wings_0.sys_commit_journal</code>.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.11",
        "schema version:2019052101"
    },
    date = "2019-09-02T11:32:45.702Z",
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
@Repository
public class SysCommitJournalDao extends DAOImpl<SysCommitJournalRecord, SysCommitJournal, Long> {

    /**
     * Create a new SysCommitJournalDao without any configuration
     */
    public SysCommitJournalDao() {
        super(SysCommitJournalTable.SysCommitJournal, SysCommitJournal.class);
    }

    /**
     * Create a new SysCommitJournalDao with an attached configuration
     */
    @Autowired
    public SysCommitJournalDao(Configuration configuration) {
        super(SysCommitJournalTable.SysCommitJournal, SysCommitJournal.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Long getId(SysCommitJournal object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<SysCommitJournal> fetchById(Long... values) {
        return fetch(SysCommitJournalTable.SysCommitJournal.Id, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public SysCommitJournal fetchOneById(Long value) {
        return fetchOne(SysCommitJournalTable.SysCommitJournal.Id, value);
    }

    /**
     * Fetch records that have <code>create_dt IN (values)</code>
     */
    public List<SysCommitJournal> fetchByCreateDt(LocalDateTime... values) {
        return fetch(SysCommitJournalTable.SysCommitJournal.CreateDt, values);
    }

    /**
     * Fetch records that have <code>event_name IN (values)</code>
     */
    public List<SysCommitJournal> fetchByEventName(String... values) {
        return fetch(SysCommitJournalTable.SysCommitJournal.EventName, values);
    }

    /**
     * Fetch records that have <code>target_key IN (values)</code>
     */
    public List<SysCommitJournal> fetchByTargetKey(String... values) {
        return fetch(SysCommitJournalTable.SysCommitJournal.TargetKey, values);
    }

    /**
     * Fetch records that have <code>login_info IN (values)</code>
     */
    public List<SysCommitJournal> fetchByLoginInfo(String... values) {
        return fetch(SysCommitJournalTable.SysCommitJournal.LoginInfo, values);
    }

    /**
     * Fetch records that have <code>other_info IN (values)</code>
     */
    public List<SysCommitJournal> fetchByOtherInfo(String... values) {
        return fetch(SysCommitJournalTable.SysCommitJournal.OtherInfo, values);
    }
}
