package com.san.platform.limit;

import com.san.platform.page.Page;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tbl_limit")
public class Limit extends Page implements Serializable {
    /**
     *主键id
     */
    @Id
    @Column(name = "limitId", unique = true, nullable = false, length = 200)
    private String limitId;
    /**
     *父级id
     */
    @Column(name = "limitParentId",  length = 100)
    private String limitParentId;
    /**
     *菜单名（权限名）
     */
    @Column(name = "limitName",  length = 100)
    private String limitName;
    /**
     *前端路径
     */
    @Column(name = "limitPath",  length = 100)
    private String limitPath;
    /**
     *权限状态
     */
    @Column(name = "limitStatus", length = 4)
    private int limitStatus;
    /**
     *权限码（升级用）
     */
    @Column(name = "limitIndex", length = 4)
    private int limitIndex;
    /**
     *菜单等级
     */
    @Column(name = "limitLevel", length = 4)
    private int limitLevel;

    public String getLimitId() {
        return limitId;
    }

    public void setLimitId(String limitId) {
        this.limitId = limitId;
    }

    public String getLimitParentId() {
        return limitParentId;
    }

    public void setLimitParentId(String limitParentId) {
        this.limitParentId = limitParentId;
    }

    public String getLimitName() {
        return limitName;
    }

    public void setLimitName(String limitName) {
        this.limitName = limitName;
    }

    public String getLimitPath() {
        return limitPath;
    }

    public void setLimitPath(String limitPath) {
        this.limitPath = limitPath;
    }

    public int getLimitStatus() {
        return limitStatus;
    }

    public void setLimitStatus(int limitStatus) {
        this.limitStatus = limitStatus;
    }

    public int getLimitIndex() {
        return limitIndex;
    }

    public void setLimitIndex(int limitIndex) {
        this.limitIndex = limitIndex;
    }

    public int getLimitLevel() {
        return limitLevel;
    }

    public void setLimitLevel(int limitLevel) {
        this.limitLevel = limitLevel;
    }
}
