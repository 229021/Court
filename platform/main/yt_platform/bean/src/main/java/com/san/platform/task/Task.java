package com.san.platform.task;

import com.san.platform.page.Page;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "tbl_task")
public class Task extends Page implements Serializable {

    @Id
    @Column(name = "taskId")
    Integer taskId;
    @Column(name = "taskGuid")
    String taskGuid;
    @Column(name = "taskDeviceId")
    Integer taskDeviceId;
    @Column(name = "taskDeviceName")
    String taskDeviceName;
    @Column(name = "taskPerType")
    Integer taskPerType;
    @Column(name = "taskPerName")
    String taskPerName;
    @Column(name = "taskError")
    String taskError;
    @Column(name = "taskResult")
    String taskResult;
    @Column(name="taskStatus")
    Integer taskStatus;

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getTaskGuid() {
        return taskGuid;
    }

    public void setTaskGuid(String taskGuid) {
        this.taskGuid = taskGuid;
    }

    public Integer getTaskDeviceId() {
        return taskDeviceId;
    }

    public void setTaskDeviceId(Integer taskDeviceId) {
        this.taskDeviceId = taskDeviceId;
    }

    public Integer getTaskPerType() {
        return taskPerType;
    }

    public void setTaskPerType(Integer taskPerType) {
        this.taskPerType = taskPerType;
    }

    public String getTaskError() {
        return taskError;
    }

    public void setTaskError(String taskError) {
        this.taskError = taskError;
    }

    public String getTaskResult() {
        return taskResult;
    }

    public void setTaskResult(String taskResult) {
        this.taskResult = taskResult;
    }

    public String getTaskDeviceName() {
        return taskDeviceName;
    }

    public void setTaskDeviceName(String taskDeviceName) {
        this.taskDeviceName = taskDeviceName;
    }

    public String getTaskPerName() {
        return taskPerName;
    }

    public void setTaskPerName(String taskPerName) {
        this.taskPerName = taskPerName;
    }

    public Integer getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }
}
