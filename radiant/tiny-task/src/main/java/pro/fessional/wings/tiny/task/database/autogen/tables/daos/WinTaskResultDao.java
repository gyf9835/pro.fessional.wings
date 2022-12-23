/*
 * This file is generated by jOOQ.
 */
package pro.fessional.wings.tiny.task.database.autogen.tables.daos;


import org.jooq.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pro.fessional.wings.faceless.database.jooq.WingsJooqDaoAliasImpl;
import pro.fessional.wings.tiny.task.database.autogen.tables.WinTaskResultTable;
import pro.fessional.wings.tiny.task.database.autogen.tables.pojos.WinTaskResult;
import pro.fessional.wings.tiny.task.database.autogen.tables.records.WinTaskResultRecord;

import javax.annotation.processing.Generated;
import java.time.LocalDateTime;
import java.util.List;


/**
 * The table <code>wings_radiant.win_task_result</code>.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.14.16",
        "schema version:2020102601"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
@Repository
public class WinTaskResultDao extends WingsJooqDaoAliasImpl<WinTaskResultTable, WinTaskResultRecord, WinTaskResult, Long> {

    /**
     * Create a new WinTaskResultDao without any configuration
     */
    public WinTaskResultDao() {
        super(WinTaskResultTable.WinTaskResult, WinTaskResult.class);
    }

    /**
     * Create a new WinTaskResultDao with an attached configuration
     */
    @Autowired
    public WinTaskResultDao(Configuration configuration) {
        super(WinTaskResultTable.WinTaskResult, WinTaskResult.class, configuration);
    }

    @Override
    public Long getId(WinTaskResult object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<WinTaskResult> fetchRangeOfId(Long lowerInclusive, Long upperInclusive) {
        return fetchRange(WinTaskResultTable.WinTaskResult.Id, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<WinTaskResult> fetchById(Long... values) {
        return fetch(WinTaskResultTable.WinTaskResult.Id, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public WinTaskResult fetchOneById(Long value) {
        return fetchOne(WinTaskResultTable.WinTaskResult.Id, value);
    }

    /**
     * Fetch records that have <code>task_id BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<WinTaskResult> fetchRangeOfTaskId(Long lowerInclusive, Long upperInclusive) {
        return fetchRange(WinTaskResultTable.WinTaskResult.TaskId, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>task_id IN (values)</code>
     */
    public List<WinTaskResult> fetchByTaskId(Long... values) {
        return fetch(WinTaskResultTable.WinTaskResult.TaskId, values);
    }

    /**
     * Fetch records that have <code>task_app BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<WinTaskResult> fetchRangeOfTaskApp(String lowerInclusive, String upperInclusive) {
        return fetchRange(WinTaskResultTable.WinTaskResult.TaskApp, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>task_app IN (values)</code>
     */
    public List<WinTaskResult> fetchByTaskApp(String... values) {
        return fetch(WinTaskResultTable.WinTaskResult.TaskApp, values);
    }

    /**
     * Fetch records that have <code>task_msg BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<WinTaskResult> fetchRangeOfTaskMsg(String lowerInclusive, String upperInclusive) {
        return fetchRange(WinTaskResultTable.WinTaskResult.TaskMsg, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>task_msg IN (values)</code>
     */
    public List<WinTaskResult> fetchByTaskMsg(String... values) {
        return fetch(WinTaskResultTable.WinTaskResult.TaskMsg, values);
    }

    /**
     * Fetch records that have <code>time_exec BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<WinTaskResult> fetchRangeOfTimeExec(LocalDateTime lowerInclusive, LocalDateTime upperInclusive) {
        return fetchRange(WinTaskResultTable.WinTaskResult.TimeExec, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>time_exec IN (values)</code>
     */
    public List<WinTaskResult> fetchByTimeExec(LocalDateTime... values) {
        return fetch(WinTaskResultTable.WinTaskResult.TimeExec, values);
    }

    /**
     * Fetch records that have <code>time_fail BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<WinTaskResult> fetchRangeOfTimeFail(LocalDateTime lowerInclusive, LocalDateTime upperInclusive) {
        return fetchRange(WinTaskResultTable.WinTaskResult.TimeFail, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>time_fail IN (values)</code>
     */
    public List<WinTaskResult> fetchByTimeFail(LocalDateTime... values) {
        return fetch(WinTaskResultTable.WinTaskResult.TimeFail, values);
    }

    /**
     * Fetch records that have <code>time_done BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<WinTaskResult> fetchRangeOfTimeDone(LocalDateTime lowerInclusive, LocalDateTime upperInclusive) {
        return fetchRange(WinTaskResultTable.WinTaskResult.TimeDone, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>time_done IN (values)</code>
     */
    public List<WinTaskResult> fetchByTimeDone(LocalDateTime... values) {
        return fetch(WinTaskResultTable.WinTaskResult.TimeDone, values);
    }

    /**
     * Fetch records that have <code>time_cost BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<WinTaskResult> fetchRangeOfTimeCost(Long lowerInclusive, Long upperInclusive) {
        return fetchRange(WinTaskResultTable.WinTaskResult.TimeCost, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>time_cost IN (values)</code>
     */
    public List<WinTaskResult> fetchByTimeCost(Long... values) {
        return fetch(WinTaskResultTable.WinTaskResult.TimeCost, values);
    }
}
