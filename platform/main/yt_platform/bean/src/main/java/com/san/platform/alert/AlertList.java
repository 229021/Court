package com.san.platform.alert;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.san.platform.page.Page;
import com.san.platform.personnel.Personnel;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: Zhouzhongqing
 * @Date: 2019/4/26 10:39
 * @Description: 告警列表实体类
 */

@Entity
@Table(name = "tbl_alertlist")
public class AlertList extends Page implements Serializable {

    // ID
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "alertId")
    private Integer alertId;
    @Transient
    private List<Integer> alertIds;

    public List<Integer> getAlertIds() {
        return alertIds;
    }

    public void setAlertIds(List<Integer> alertIds) {
        this.alertIds = alertIds;
    }

    @Column(name = "perName")
    private String perName;
    @Column(name = "perSex")
    private Integer perSex;
    @Column(name = "perNumber")
    private String perNumber;
    @Column(name = "perType")
    private Integer perType;

    public Integer getPerType() {
        return perType;
    }

    public void setPerType(Integer perType) {
        this.perType = perType;
    }

    //perUUID
    @Column(name = "perUUID")
    private String perUUID;

    @Transient
    private String alert = "yes";
    @Transient
    String typeName;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    //告警地点
    @Column(name = "alertPlace")
    private String alertPlace;
    //告警规则
    @Column(name = "alertName")
    private String alertName;
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
    //照片
    @Column(name = "perPicture")
    private String perPicture;

    public String getPerPicture() {
        return perPicture;
    }

    public void setPerPicture(String perPicture) {
        this.perPicture = perPicture;
    }

    public Integer getAlertId() {
        return alertId;
    }

    public void setAlertId(Integer alertId) {
        this.alertId = alertId;
    }

    public String getPerUUID() {
        return perUUID;
    }

    public void setPerUUID(String perUUID) {
        this.perUUID = perUUID;
    }

    public String getAlertPlace() {
        return alertPlace;
    }

    public void setAlertPlace(String alertPlace) {
        this.alertPlace = alertPlace;
    }

    public String getAlertName() {
        return alertName;
    }

    public void setAlertName(String alertName) {
        this.alertName = alertName;
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

    public String getPerName() {
        return perName;
    }

    public void setPerName(String perName) {
        this.perName = perName;
    }

    public Integer getPerSex() {
        return perSex;
    }

    public void setPerSex(Integer perSex) {
        this.perSex = perSex;
    }

    public String getPerNumber() {
        return perNumber;
    }

    public void setPerNumber(String perNumber) {
        this.perNumber = perNumber;
    }

    @Override
    public String toString() {
        return "AlertList{" +
                "alertId=" + alertId +
                ", alertIds=" + alertIds +
                ", perName='" + perName + '\'' +
                ", perSex=" + perSex +
                ", perNumber='" + perNumber + '\'' +
                ", perType=" + perType +
                ", perUUID='" + perUUID + '\'' +
                ", alert='" + alert + '\'' +
                ", alertPlace='" + alertPlace + '\'' +
                ", alertName='" + alertName + '\'' +
                ", alertCreateTime=" + alertCreateTime +
                ", alertModTime=" + alertModTime +
                ", perPicture='" + perPicture + '\'' +
                '}';
    }
}
