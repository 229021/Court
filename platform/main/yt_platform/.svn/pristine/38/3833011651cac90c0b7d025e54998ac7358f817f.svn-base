package com.san.platform.role;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.san.platform.page.Page;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tbl_role")
public class Role extends Page implements Serializable {
    /**
     *主键id
     */
    @Id
    @Column(name = "roleId", unique = true, nullable = false, length = 11)
    private Integer roleId;
    /**
     *角色名称
     */
    @Column(name = "roleName",  length = 100)
    private String roleName;
    /**
     *备注
     */
    @Column(name = "roleMemo",  length = 100)
    private String roleMemo;
    /**
     *角色状态
     */
    @Column(name = "roleStatus", nullable = false, length = 11)
    private Integer roleStatus;
    /**
     *一级菜单
     */
    @Column(name = "relLimitIdLv1", length = 1024)
    private String relLimitIdLv1;
    /**
     *二级菜单
     */
    @Column(name = "relLimitIdLv2", length = 2048)
    private String relLimitIdLv2;
    /**
     *三级菜单
     */
    @Column(name = "relLimitIdLv3", length = 100)
    private String relLimitIdLv3;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleMemo() {
        return roleMemo;
    }

    public void setRoleMemo(String roleMemo) {
        this.roleMemo = roleMemo;
    }

    public Integer getRoleStatus() {
        return roleStatus;
    }

    public void setRoleStatus(Integer roleStatus) {
        this.roleStatus = roleStatus;
    }

    public String getRelLimitIdLv1() {
        return relLimitIdLv1;
    }

    public void setRelLimitIdLv1(String relLimitIdLv1) {
        this.relLimitIdLv1 = relLimitIdLv1;
    }

    public String getRelLimitIdLv2() {
        return relLimitIdLv2;
    }

    public void setRelLimitIdLv2(String relLimitIdLv2) {
        this.relLimitIdLv2 = relLimitIdLv2;
    }

    public String getRelLimitIdLv3() {
        return relLimitIdLv3;
    }

    public void setRelLimitIdLv3(String relLimitIdLv3) {
        this.relLimitIdLv3 = relLimitIdLv3;
    }
}
