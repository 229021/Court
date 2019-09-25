package com.san.platform.alert;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.san.platform.page.Page;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: Zhouzhongqing
 * @Date: 2019/4/22 14:29
 * @Description: 告警模块实体
 */
@Entity
@Table(name = "tbl_alert")
public class Alert extends Page implements Serializable {

    //ID
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "alertId")
    private Integer alertId;
    //规则名称
    @Column(name = "alertName")
    private String alertName;
    //涉及场所
    @Column(name = "alertPlace")
    private String[] alertPlace;
    @Column(name = "alertPlace")
    private String alertPlacestrr;
    //黑名单
    @Column(name = "blackList")
    private String[] blackList;
    @Column(name = "blackList")
    private String blackListstrr;
    //白名单
    @Column(name = "whiteList")
    private String[] whiteList;
    @Column(name = "whiteList")
    private String whiteListstrr;
    //弹窗告警
    @Column(name = "alertWindow")
    private Integer alertWindow;
    //短信告警
    @Column(name = "alertSms")
    private Integer alertSms;
    //备注
    @Column(name = "alertMemo")
    private String alertMemo;
    //创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Column(name = "alertCreateTime")
    private Date alertCreateTime;
    //修改时间
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Column(name = "alertModTime")
    private Date alertModTime;


    public Integer getAlertId() {
        return alertId;
    }

    public void setAlertId(Integer alertId) {
        this.alertId = alertId;
    }

    public String getAlertName() {
        return alertName;
    }

    public void setAlertName(String alertName) {
        this.alertName = alertName;
    }

    public String getAlertMemo() {
        return alertMemo;
    }

    public void setAlertMemo(String alertMemo) {
        this.alertMemo = alertMemo;
    }

    public Date getAlertCreateTime() {
        return alertCreateTime;
    }

    public void setAlertCreateTime(Date alertCreateTime) {
        this.alertCreateTime = alertCreateTime;
    }

    public Date getAlertModTime() {
        return alertModTime;
    }

    public void setAlertModTime(Date alertModTime) {
        this.alertModTime = alertModTime;
    }

    public Integer getAlertWindow() {
        return alertWindow;
    }

    public void setAlertWindow(Integer alertWindow) {
        this.alertWindow = alertWindow;
    }

    public Integer getAlertSms() {
        return alertSms;
    }

    public void setAlertSms(Integer alertSms) {
        this.alertSms = alertSms;
    }

    public String getBlackListstrr() {
        return blackListstrr;
    }

    public void setBlackListstrr(String blackListstrr) {
        this.blackListstrr = blackListstrr;
    }

    public String getWhiteListstrr() {
        return whiteListstrr;
    }

    public void setWhiteListstrr(String whiteListstrr) {
        this.whiteListstrr = whiteListstrr;
    }

    public String getAlertPlacestrr() {
        return alertPlacestrr;
    }

    public void setAlertPlacestrr(String alertPlacestrr) {
        this.alertPlacestrr = alertPlacestrr;
    }

    public String[] getAlertPlace() {
        return alertPlace;
    }

    public void setAlertPlace(String[] alertPlace) {
        this.alertPlace = alertPlace;
    }

    public String[] getBlackList() {
        return blackList;
    }

    public void setBlackList(String[] blackList) {
        this.blackList = blackList;
    }

    public String[] getWhiteList() {
        return whiteList;
    }

    public void setWhiteList(String[] whiteList) {
        this.whiteList = whiteList;
    }

    @Override
    public String toString() {
        return "Alert{" +
                "alertId=" + alertId +
                ", alertName='" + alertName + '\'' +
                ", alertPlace='" + alertPlace + '\'' +
                ", blackList='" + blackList + '\'' +
                ", whiteList='" + whiteList + '\'' +
                ", alertWindow=" + alertWindow +
                ", alertSms=" + alertSms +
                ", alertMemo='" + alertMemo + '\'' +
                ", alertCreateTime=" + alertCreateTime +
                ", alertModTime=" + alertModTime +
                '}';
    }
}
