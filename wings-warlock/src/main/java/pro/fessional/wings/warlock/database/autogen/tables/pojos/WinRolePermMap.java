/*
 * This file is generated by jOOQ.
 */
package pro.fessional.wings.warlock.database.autogen.tables.pojos;


import pro.fessional.wings.warlock.database.autogen.tables.interfaces.IWinRolePermMap;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


/**
 * The table <code>wings_warlock.win_role_perm_map</code>.
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
@Entity
@Table(
    name = "win_role_perm_map",
    uniqueConstraints = {
        @UniqueConstraint(name = "KEY_win_role_perm_map_PRIMARY", columnNames = { "refer_role", "grant_perm" })
    }
)
public class WinRolePermMap implements IWinRolePermMap {

    private static final long serialVersionUID = 1L;

    private Long          referRole;
    private Long          grantPerm;
    private LocalDateTime createDt;
    private LocalDateTime modifyDt;
    private LocalDateTime deleteDt;
    private Long          commitId;

    public WinRolePermMap() {}

    public WinRolePermMap(IWinRolePermMap value) {
        this.referRole = value.getReferRole();
        this.grantPerm = value.getGrantPerm();
        this.createDt = value.getCreateDt();
        this.modifyDt = value.getModifyDt();
        this.deleteDt = value.getDeleteDt();
        this.commitId = value.getCommitId();
    }

    public WinRolePermMap(
        Long          referRole,
        Long          grantPerm,
        LocalDateTime createDt,
        LocalDateTime modifyDt,
        LocalDateTime deleteDt,
        Long          commitId
    ) {
        this.referRole = referRole;
        this.grantPerm = grantPerm;
        this.createDt = createDt;
        this.modifyDt = modifyDt;
        this.deleteDt = deleteDt;
        this.commitId = commitId;
    }

    /**
     * Getter for <code>win_role_perm_map.refer_role</code>.
     */
    @Column(name = "refer_role", nullable = false, precision = 19)
    @NotNull
    @Override
    public Long getReferRole() {
        return this.referRole;
    }

    /**
     * Setter for <code>win_role_perm_map.refer_role</code>.
     */
    @Override
    public void setReferRole(Long referRole) {
        this.referRole = referRole;
    }

    /**
     * Getter for <code>win_role_perm_map.grant_perm</code>.
     */
    @Column(name = "grant_perm", nullable = false, precision = 19)
    @NotNull
    @Override
    public Long getGrantPerm() {
        return this.grantPerm;
    }

    /**
     * Setter for <code>win_role_perm_map.grant_perm</code>.
     */
    @Override
    public void setGrantPerm(Long grantPerm) {
        this.grantPerm = grantPerm;
    }

    /**
     * Getter for <code>win_role_perm_map.create_dt</code>.
     */
    @Column(name = "create_dt", nullable = false, precision = 3)
    @Override
    public LocalDateTime getCreateDt() {
        return this.createDt;
    }

    /**
     * Setter for <code>win_role_perm_map.create_dt</code>.
     */
    @Override
    public void setCreateDt(LocalDateTime createDt) {
        this.createDt = createDt;
    }

    /**
     * Getter for <code>win_role_perm_map.modify_dt</code>.
     */
    @Column(name = "modify_dt", nullable = false, precision = 3)
    @Override
    public LocalDateTime getModifyDt() {
        return this.modifyDt;
    }

    /**
     * Setter for <code>win_role_perm_map.modify_dt</code>.
     */
    @Override
    public void setModifyDt(LocalDateTime modifyDt) {
        this.modifyDt = modifyDt;
    }

    /**
     * Getter for <code>win_role_perm_map.delete_dt</code>.
     */
    @Column(name = "delete_dt", nullable = false, precision = 3)
    @Override
    public LocalDateTime getDeleteDt() {
        return this.deleteDt;
    }

    /**
     * Setter for <code>win_role_perm_map.delete_dt</code>.
     */
    @Override
    public void setDeleteDt(LocalDateTime deleteDt) {
        this.deleteDt = deleteDt;
    }

    /**
     * Getter for <code>win_role_perm_map.commit_id</code>.
     */
    @Column(name = "commit_id", nullable = false, precision = 19)
    @NotNull
    @Override
    public Long getCommitId() {
        return this.commitId;
    }

    /**
     * Setter for <code>win_role_perm_map.commit_id</code>.
     */
    @Override
    public void setCommitId(Long commitId) {
        this.commitId = commitId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final WinRolePermMap other = (WinRolePermMap) obj;
        if (referRole == null) {
            if (other.referRole != null)
                return false;
        }
        else if (!referRole.equals(other.referRole))
            return false;
        if (grantPerm == null) {
            if (other.grantPerm != null)
                return false;
        }
        else if (!grantPerm.equals(other.grantPerm))
            return false;
        if (createDt == null) {
            if (other.createDt != null)
                return false;
        }
        else if (!createDt.equals(other.createDt))
            return false;
        if (modifyDt == null) {
            if (other.modifyDt != null)
                return false;
        }
        else if (!modifyDt.equals(other.modifyDt))
            return false;
        if (deleteDt == null) {
            if (other.deleteDt != null)
                return false;
        }
        else if (!deleteDt.equals(other.deleteDt))
            return false;
        if (commitId == null) {
            if (other.commitId != null)
                return false;
        }
        else if (!commitId.equals(other.commitId))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.referRole == null) ? 0 : this.referRole.hashCode());
        result = prime * result + ((this.grantPerm == null) ? 0 : this.grantPerm.hashCode());
        result = prime * result + ((this.createDt == null) ? 0 : this.createDt.hashCode());
        result = prime * result + ((this.modifyDt == null) ? 0 : this.modifyDt.hashCode());
        result = prime * result + ((this.deleteDt == null) ? 0 : this.deleteDt.hashCode());
        result = prime * result + ((this.commitId == null) ? 0 : this.commitId.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("WinRolePermMap (");

        sb.append(referRole);
        sb.append(", ").append(grantPerm);
        sb.append(", ").append(createDt);
        sb.append(", ").append(modifyDt);
        sb.append(", ").append(deleteDt);
        sb.append(", ").append(commitId);

        sb.append(")");
        return sb.toString();
    }

    // -------------------------------------------------------------------------
    // FROM and INTO
    // -------------------------------------------------------------------------

    @Override
    public void from(IWinRolePermMap from) {
        setReferRole(from.getReferRole());
        setGrantPerm(from.getGrantPerm());
        setCreateDt(from.getCreateDt());
        setModifyDt(from.getModifyDt());
        setDeleteDt(from.getDeleteDt());
        setCommitId(from.getCommitId());
    }

    @Override
    public <E extends IWinRolePermMap> E into(E into) {
        into.from(this);
        return into;
    }
}
