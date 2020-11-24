/*
 * This file is generated by jOOQ.
 */
package pro.fessional.wings.example.database.autogen.tables.daos;


import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pro.fessional.wings.example.database.autogen.tables.WinAuthRoleTable;
import pro.fessional.wings.example.database.autogen.tables.pojos.WinAuthRole;
import pro.fessional.wings.example.database.autogen.tables.records.WinAuthRoleRecord;
import pro.fessional.wings.faceless.database.jooq.WingsJooqDaoImpl;


/**
 * The table <code>wings_example.win_auth_role</code>.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.4",
        "schema version:2019070403"
    },
    date = "2020-08-13T07:33:30.191Z",
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
@Repository
public class WinAuthRoleDao extends WingsJooqDaoImpl<WinAuthRoleTable, WinAuthRoleRecord, WinAuthRole, Long> {

    /**
     * Create a new WinAuthRoleDao without any configuration
     */
    public WinAuthRoleDao() {
        super(WinAuthRoleTable.WinAuthRole, WinAuthRole.class);
    }

    /**
     * Create a new WinAuthRoleDao with an attached configuration
     */
    @Autowired
    public WinAuthRoleDao(Configuration configuration) {
        super(WinAuthRoleTable.WinAuthRole, WinAuthRole.class, configuration);
    }

    @Override
    public Long getId(WinAuthRole object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<WinAuthRole> fetchRangeOfId(Long lowerInclusive, Long upperInclusive) {
        return fetchRange(WinAuthRoleTable.WinAuthRole.Id, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<WinAuthRole> fetchById(Long... values) {
        return fetch(WinAuthRoleTable.WinAuthRole.Id, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public WinAuthRole fetchOneById(Long value) {
        return fetchOne(WinAuthRoleTable.WinAuthRole.Id, value);
    }

    /**
     * Fetch records that have <code>create_dt BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<WinAuthRole> fetchRangeOfCreateDt(LocalDateTime lowerInclusive, LocalDateTime upperInclusive) {
        return fetchRange(WinAuthRoleTable.WinAuthRole.CreateDt, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>create_dt IN (values)</code>
     */
    public List<WinAuthRole> fetchByCreateDt(LocalDateTime... values) {
        return fetch(WinAuthRoleTable.WinAuthRole.CreateDt, values);
    }

    /**
     * Fetch records that have <code>modify_dt BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<WinAuthRole> fetchRangeOfModifyDt(LocalDateTime lowerInclusive, LocalDateTime upperInclusive) {
        return fetchRange(WinAuthRoleTable.WinAuthRole.ModifyDt, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>modify_dt IN (values)</code>
     */
    public List<WinAuthRole> fetchByModifyDt(LocalDateTime... values) {
        return fetch(WinAuthRoleTable.WinAuthRole.ModifyDt, values);
    }

    /**
     * Fetch records that have <code>delete_dt BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<WinAuthRole> fetchRangeOfDeleteDt(LocalDateTime lowerInclusive, LocalDateTime upperInclusive) {
        return fetchRange(WinAuthRoleTable.WinAuthRole.DeleteDt, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>delete_dt IN (values)</code>
     */
    public List<WinAuthRole> fetchByDeleteDt(LocalDateTime... values) {
        return fetch(WinAuthRoleTable.WinAuthRole.DeleteDt, values);
    }

    /**
     * Fetch records that have <code>commit_id BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<WinAuthRole> fetchRangeOfCommitId(Long lowerInclusive, Long upperInclusive) {
        return fetchRange(WinAuthRoleTable.WinAuthRole.CommitId, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>commit_id IN (values)</code>
     */
    public List<WinAuthRole> fetchByCommitId(Long... values) {
        return fetch(WinAuthRoleTable.WinAuthRole.CommitId, values);
    }

    /**
     * Fetch records that have <code>role_type BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<WinAuthRole> fetchRangeOfRoleType(Integer lowerInclusive, Integer upperInclusive) {
        return fetchRange(WinAuthRoleTable.WinAuthRole.RoleType, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>role_type IN (values)</code>
     */
    public List<WinAuthRole> fetchByRoleType(Integer... values) {
        return fetch(WinAuthRoleTable.WinAuthRole.RoleType, values);
    }

    /**
     * Fetch records that have <code>role_name BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<WinAuthRole> fetchRangeOfRoleName(String lowerInclusive, String upperInclusive) {
        return fetchRange(WinAuthRoleTable.WinAuthRole.RoleName, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>role_name IN (values)</code>
     */
    public List<WinAuthRole> fetchByRoleName(String... values) {
        return fetch(WinAuthRoleTable.WinAuthRole.RoleName, values);
    }

    /**
     * Fetch records that have <code>desc BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<WinAuthRole> fetchRangeOfDesc(String lowerInclusive, String upperInclusive) {
        return fetchRange(WinAuthRoleTable.WinAuthRole.Desc, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>desc IN (values)</code>
     */
    public List<WinAuthRole> fetchByDesc(String... values) {
        return fetch(WinAuthRoleTable.WinAuthRole.Desc, values);
    }

    /**
     * Fetch records that have <code>auth_set BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<WinAuthRole> fetchRangeOfAuthSet(String lowerInclusive, String upperInclusive) {
        return fetchRange(WinAuthRoleTable.WinAuthRole.AuthSet, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>auth_set IN (values)</code>
     */
    public List<WinAuthRole> fetchByAuthSet(String... values) {
        return fetch(WinAuthRoleTable.WinAuthRole.AuthSet, values);
    }
}