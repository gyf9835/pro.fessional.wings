/*
 * This file is generated by jOOQ.
 */
package pro.fessional.wings.tiny.task.database.autogen.tables.interfaces;


import pro.fessional.wings.faceless.service.journal.JournalAware;

import javax.annotation.processing.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * The table <code>wings_radiant.win_task_define</code>.
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
@Entity
@Table(
    name = "win_task_define",
    uniqueConstraints = {
        @UniqueConstraint(name = "KEY_win_task_define_PRIMARY", columnNames = { "id" })
    }
)
public interface IWinTaskDefine extends JournalAware, Serializable {

    /**
     * Setter for <code>win_task_define.id</code>.
     */
    public void setId(Long value);

    /**
     * Getter for <code>win_task_define.id</code>.
     */
    @Id
    @Column(name = "id", nullable = false, precision = 19)
    @NotNull
    public Long getId();

    /**
     * Setter for <code>win_task_define.create_dt</code>.
     */
    public void setCreateDt(LocalDateTime value);

    /**
     * Getter for <code>win_task_define.create_dt</code>.
     */
    @Column(name = "create_dt", nullable = false, precision = 3)
    public LocalDateTime getCreateDt();

    /**
     * Setter for <code>win_task_define.modify_dt</code>.
     */
    public void setModifyDt(LocalDateTime value);

    /**
     * Getter for <code>win_task_define.modify_dt</code>.
     */
    @Column(name = "modify_dt", nullable = false, precision = 3)
    public LocalDateTime getModifyDt();

    /**
     * Setter for <code>win_task_define.delete_dt</code>.
     */
    public void setDeleteDt(LocalDateTime value);

    /**
     * Getter for <code>win_task_define.delete_dt</code>.
     */
    @Column(name = "delete_dt", nullable = false, precision = 3)
    public LocalDateTime getDeleteDt();

    /**
     * Setter for <code>win_task_define.commit_id</code>.
     */
    public void setCommitId(Long value);

    /**
     * Getter for <code>win_task_define.commit_id</code>.
     */
    @Column(name = "commit_id", nullable = false, precision = 19)
    @NotNull
    public Long getCommitId();

    /**
     * Setter for <code>win_task_define.propkey</code>.
     */
    public void setPropkey(String value);

    /**
     * Getter for <code>win_task_define.propkey</code>.
     */
    @Column(name = "propkey", nullable = false, length = 200)
    @Size(max = 200)
    public String getPropkey();

    /**
     * Setter for <code>win_task_define.enabled</code>.
     */
    public void setEnabled(Boolean value);

    /**
     * Getter for <code>win_task_define.enabled</code>.
     */
    @Column(name = "enabled", nullable = false)
    public Boolean getEnabled();

    /**
     * Setter for <code>win_task_define.autorun</code>.
     */
    public void setAutorun(Boolean value);

    /**
     * Getter for <code>win_task_define.autorun</code>.
     */
    @Column(name = "autorun", nullable = false)
    public Boolean getAutorun();

    /**
     * Setter for <code>win_task_define.version</code>.
     */
    public void setVersion(Integer value);

    /**
     * Getter for <code>win_task_define.version</code>.
     */
    @Column(name = "version", nullable = false, precision = 10)
    public Integer getVersion();

    /**
     * Setter for <code>win_task_define.tasker_bean</code>.
     */
    public void setTaskerBean(String value);

    /**
     * Getter for <code>win_task_define.tasker_bean</code>.
     */
    @Column(name = "tasker_bean", nullable = false, length = 300)
    @Size(max = 300)
    public String getTaskerBean();

    /**
     * Setter for <code>win_task_define.tasker_para</code>.
     */
    public void setTaskerPara(String value);

    /**
     * Getter for <code>win_task_define.tasker_para</code>.
     */
    @Column(name = "tasker_para", length = 65535)
    @Size(max = 65535)
    public String getTaskerPara();

    /**
     * Setter for <code>win_task_define.tasker_name</code>.
     */
    public void setTaskerName(String value);

    /**
     * Getter for <code>win_task_define.tasker_name</code>.
     */
    @Column(name = "tasker_name", nullable = false, length = 200)
    @Size(max = 200)
    public String getTaskerName();

    /**
     * Setter for <code>win_task_define.tasker_fast</code>.
     */
    public void setTaskerFast(Boolean value);

    /**
     * Getter for <code>win_task_define.tasker_fast</code>.
     */
    @Column(name = "tasker_fast", nullable = false)
    public Boolean getTaskerFast();

    /**
     * Setter for <code>win_task_define.tasker_apps</code>.
     */
    public void setTaskerApps(String value);

    /**
     * Getter for <code>win_task_define.tasker_apps</code>.
     */
    @Column(name = "tasker_apps", nullable = false, length = 500)
    @Size(max = 500)
    public String getTaskerApps();

    /**
     * Setter for <code>win_task_define.tasker_runs</code>.
     */
    public void setTaskerRuns(String value);

    /**
     * Getter for <code>win_task_define.tasker_runs</code>.
     */
    @Column(name = "tasker_runs", nullable = false, length = 100)
    @Size(max = 100)
    public String getTaskerRuns();

    /**
     * Setter for <code>win_task_define.notice_bean</code>.
     */
    public void setNoticeBean(String value);

    /**
     * Getter for <code>win_task_define.notice_bean</code>.
     */
    @Column(name = "notice_bean", nullable = false, length = 200)
    @Size(max = 200)
    public String getNoticeBean();

    /**
     * Setter for <code>win_task_define.notice_when</code>.
     */
    public void setNoticeWhen(String value);

    /**
     * Getter for <code>win_task_define.notice_when</code>.
     */
    @Column(name = "notice_when", nullable = false, length = 100)
    @Size(max = 100)
    public String getNoticeWhen();

    /**
     * Setter for <code>win_task_define.notice_conf</code>.
     */
    public void setNoticeConf(String value);

    /**
     * Getter for <code>win_task_define.notice_conf</code>.
     */
    @Column(name = "notice_conf", length = 65535)
    @Size(max = 65535)
    public String getNoticeConf();

    /**
     * Setter for <code>win_task_define.timing_zone</code>.
     */
    public void setTimingZone(String value);

    /**
     * Getter for <code>win_task_define.timing_zone</code>.
     */
    @Column(name = "timing_zone", nullable = false, length = 100)
    @Size(max = 100)
    public String getTimingZone();

    /**
     * Setter for <code>win_task_define.timing_type</code>.
     */
    public void setTimingType(String value);

    /**
     * Getter for <code>win_task_define.timing_type</code>.
     */
    @Column(name = "timing_type", nullable = false, length = 100)
    @Size(max = 100)
    public String getTimingType();

    /**
     * Setter for <code>win_task_define.timing_cron</code>.
     */
    public void setTimingCron(String value);

    /**
     * Getter for <code>win_task_define.timing_cron</code>.
     */
    @Column(name = "timing_cron", nullable = false, length = 100)
    @Size(max = 100)
    public String getTimingCron();

    /**
     * Setter for <code>win_task_define.timing_idle</code>.
     */
    public void setTimingIdle(Integer value);

    /**
     * Getter for <code>win_task_define.timing_idle</code>.
     */
    @Column(name = "timing_idle", nullable = false, precision = 10)
    public Integer getTimingIdle();

    /**
     * Setter for <code>win_task_define.timing_rate</code>.
     */
    public void setTimingRate(Integer value);

    /**
     * Getter for <code>win_task_define.timing_rate</code>.
     */
    @Column(name = "timing_rate", nullable = false, precision = 10)
    public Integer getTimingRate();

    /**
     * Setter for <code>win_task_define.timing_miss</code>.
     */
    public void setTimingMiss(Integer value);

    /**
     * Getter for <code>win_task_define.timing_miss</code>.
     */
    @Column(name = "timing_miss", nullable = false, precision = 10)
    public Integer getTimingMiss();

    /**
     * Setter for <code>win_task_define.during_from</code>.
     */
    public void setDuringFrom(String value);

    /**
     * Getter for <code>win_task_define.during_from</code>.
     */
    @Column(name = "during_from", nullable = false, length = 20)
    @Size(max = 20)
    public String getDuringFrom();

    /**
     * Setter for <code>win_task_define.during_stop</code>.
     */
    public void setDuringStop(String value);

    /**
     * Getter for <code>win_task_define.during_stop</code>.
     */
    @Column(name = "during_stop", nullable = false, length = 20)
    @Size(max = 20)
    public String getDuringStop();

    /**
     * Setter for <code>win_task_define.during_exec</code>.
     */
    public void setDuringExec(Integer value);

    /**
     * Getter for <code>win_task_define.during_exec</code>.
     */
    @Column(name = "during_exec", nullable = false, precision = 10)
    public Integer getDuringExec();

    /**
     * Setter for <code>win_task_define.during_fail</code>.
     */
    public void setDuringFail(Integer value);

    /**
     * Getter for <code>win_task_define.during_fail</code>.
     */
    @Column(name = "during_fail", nullable = false, precision = 10)
    public Integer getDuringFail();

    /**
     * Setter for <code>win_task_define.during_done</code>.
     */
    public void setDuringDone(Integer value);

    /**
     * Getter for <code>win_task_define.during_done</code>.
     */
    @Column(name = "during_done", nullable = false, precision = 10)
    public Integer getDuringDone();

    /**
     * Setter for <code>win_task_define.result_keep</code>.
     */
    public void setResultKeep(Integer value);

    /**
     * Getter for <code>win_task_define.result_keep</code>.
     */
    @Column(name = "result_keep", nullable = false, precision = 10)
    public Integer getResultKeep();

    /**
     * Setter for <code>win_task_define.last_exec</code>.
     */
    public void setLastExec(Long value);

    /**
     * Getter for <code>win_task_define.last_exec</code>.
     */
    @Column(name = "last_exec", nullable = false, precision = 19)
    public Long getLastExec();

    /**
     * Setter for <code>win_task_define.last_fail</code>.
     */
    public void setLastFail(Long value);

    /**
     * Getter for <code>win_task_define.last_fail</code>.
     */
    @Column(name = "last_fail", nullable = false, precision = 19)
    public Long getLastFail();

    /**
     * Setter for <code>win_task_define.last_done</code>.
     */
    public void setLastDone(Long value);

    /**
     * Getter for <code>win_task_define.last_done</code>.
     */
    @Column(name = "last_done", nullable = false, precision = 19)
    public Long getLastDone();

    /**
     * Setter for <code>win_task_define.next_exec</code>.
     */
    public void setNextExec(Long value);

    /**
     * Getter for <code>win_task_define.next_exec</code>.
     */
    @Column(name = "next_exec", nullable = false, precision = 19)
    public Long getNextExec();

    /**
     * Setter for <code>win_task_define.next_miss</code>.
     */
    public void setNextMiss(Long value);

    /**
     * Getter for <code>win_task_define.next_miss</code>.
     */
    @Column(name = "next_miss", nullable = false, precision = 19)
    public Long getNextMiss();

    /**
     * Setter for <code>win_task_define.next_lock</code>.
     */
    public void setNextLock(Long value);

    /**
     * Getter for <code>win_task_define.next_lock</code>.
     */
    @Column(name = "next_lock", nullable = false, precision = 19)
    public Long getNextLock();

    /**
     * Setter for <code>win_task_define.core_fail</code>.
     */
    public void setCoreFail(Long value);

    /**
     * Getter for <code>win_task_define.core_fail</code>.
     */
    @Column(name = "core_fail", nullable = false, precision = 19)
    public Long getCoreFail();

    /**
     * Setter for <code>win_task_define.sums_exec</code>.
     */
    public void setSumsExec(Long value);

    /**
     * Getter for <code>win_task_define.sums_exec</code>.
     */
    @Column(name = "sums_exec", nullable = false, precision = 19)
    public Long getSumsExec();

    /**
     * Setter for <code>win_task_define.sums_fail</code>.
     */
    public void setSumsFail(Long value);

    /**
     * Getter for <code>win_task_define.sums_fail</code>.
     */
    @Column(name = "sums_fail", nullable = false, precision = 19)
    public Long getSumsFail();

    /**
     * Setter for <code>win_task_define.sums_done</code>.
     */
    public void setSumsDone(Long value);

    /**
     * Getter for <code>win_task_define.sums_done</code>.
     */
    @Column(name = "sums_done", nullable = false, precision = 19)
    public Long getSumsDone();

    // -------------------------------------------------------------------------
    // FROM and INTO
    // -------------------------------------------------------------------------

    /**
     * Load data from another generated Record/POJO implementing the common interface IWinTaskDefine
     */
    public void from(IWinTaskDefine from);

    /**
     * Copy data into another generated Record/POJO implementing the common interface IWinTaskDefine
     */
    public <E extends IWinTaskDefine> E into(E into);
}
