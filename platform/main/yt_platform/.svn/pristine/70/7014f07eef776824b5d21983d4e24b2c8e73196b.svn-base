package com.san.platform.innerlog;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.san.platform.page.Page;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 操作审计实体类
 */
@Entity
@Table(name = "tbl_innerlog")
public class Innerlog extends Page implements Serializable {
    /**
     *主键id
     */
    @Id
    @Column(name = "logId", unique = true, nullable = false, length = 10)
    private Integer logId;
    /**
     *日志级别
     */
    @Column(name = "logLevel", nullable = true, length = 100)
    private String logLevel;
    /**
     *操作人
     */
    @Column(name = "opName", nullable = true, length = 100)
    private String opName;
    /**
     *操作人角色
     */
    @Column(name = "opRole", nullable = true, length = 100)
    private String opRole;
    /**
     *操作类型
     */
    @Column(name = "opType", nullable = true, length = 100)
    private String opType;
    /**
     *操作实体
     */
    @Column(name = "opModel", nullable = true, length = 100)
    private String opModel;
    /**
     *操作内容
     */
    @Column(name = "opInfo", nullable = true, length = 100)
    private String opInfo;
    /**
     *操作时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "opTime")
    private Date opTime;
    /**
     * 查询时间用的临时属性
     */
    @Transient
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date startTime;
    @Transient
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date endTime;

    public Date getStartTime() {
        return startTime;
    }

    // 方法内处理了由于时区问题时间相差8个小时
    public void setStartTime(Date startTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar ca = Calendar.getInstance();
        ca.setTime(startTime);
        ca.add(Calendar.HOUR_OF_DAY,8);
        this.startTime = ca.getTime();
    }

    public Date getEndTime() {
        return endTime;
    }

    // 方法内处理了由于时区问题时间相差8个小时
    public void setEndTime(Date endTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar ca = Calendar.getInstance();
        ca.setTime(endTime);
        ca.add(Calendar.HOUR_OF_DAY,8);
        this.endTime = ca.getTime();
    }

    /**
     *操作客户端ip
     */
    @Column(name = "clientIp", nullable = true, length = 100)
    private String clientIp;

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public String getOpName() {
        return opName;
    }

    public void setOpName(String opName) {
        this.opName = opName;
    }

    public String getOpRole() {
        return opRole;
    }

    public void setOpRole(String opRole) {
        this.opRole = opRole;
    }

    public String getOpType() {
        return opType;
    }

    public void setOpType(String opType) {
        this.opType = opType;
    }

    public String getOpModel() {
        return opModel;
    }

    public void setOpModel(String opModel) {
        this.opModel = opModel;
    }

    public String getOpInfo() {
        return opInfo;
    }

    public void setOpInfo(String opInfo) {
        this.opInfo = opInfo;
    }

    public Date getOpTime() {
        return opTime;
    }

    public void setOpTime(Date opTime) {
        this.opTime = opTime;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }
}
