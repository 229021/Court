package com.san.platform.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.san.platform.page.Page;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 名单下发任务结果表
 */
@Entity
@Table(name = "tbl_taskresult")
public class TaskResult extends Page implements Serializable {
    @Id
    @Column(name = "id")
    private Integer id;
    /**任务guid*/
    @Column(name = "taskGuid")
    private String taskGuid;
    /**人员 名称*/
    @Column(name = "perName")
    private String perName;
    /**错误码code*/
    @Column(name = "resultCode")
    private Integer resultCode;
    /**错误信息*/
    @Column(name = "resultInfo")
    private String resultInfo;
    /**任务执行时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "taskDate")
    private Date taskDate;
    @Column(name = "perNumber")
    private String perNumber;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTaskGuid() {
        return taskGuid;
    }

    public void setTaskGuid(String taskGuid) {
        this.taskGuid = taskGuid;
    }

    public String getPerName() {
        return perName;
    }

    public void setPerName(String perName) {
        this.perName = perName;
    }

    public Date getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(Date taskDate) {
        this.taskDate = taskDate;
    }

    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(String resultInfo) {
        this.resultInfo = resultInfo;
    }

    public String getPerNumber() {
        return perNumber;
    }

    public void setPerNumber(String perNumber) {
        this.perNumber = perNumber;
    }
}
