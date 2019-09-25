package com.san.platform.visitor;/*
 * Welcome to use the TableGo Tools.
 *
 * http://vipbooks.iteye.com
 * http://blog.csdn.net/vipbooks
 * http://www.cnblogs.com/vipbooks
 *
 * Author:bianj
 * Email:edinsker@163.com
 * Version:5.8.8
 */

import com.fasterxml.jackson.annotation.JsonFormat;
import com.san.platform.page.Page;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;


/**
 * TBL_VISIT
 *
 * @author bianj
 * @version 1.0.0 2019-04-26
 */
@Entity
@Table(name = "tbl_visit")
public class Visitor extends Page implements Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = -7586312306593760751L;

    /**
     * visitId
     */
    @Id
    @Column(name = "visitId", unique = true, nullable = false, length = 10)
    private Integer visitId;

    /**
     * 来访人员姓名
     */
    @Column(name = "visitName", nullable = true, length = 255)
    private String visitName;

    /**
     * 证件类型
     */
    @Column(name = "visitCardType", nullable = true, length = 10)
    private Integer visitCardType;

    /**
     * 来访人员证件号
     */
    @Column(name = "visitCardNumber", nullable = true, length = 50)
    private String visitCardNumber;

    /**
     * 性别
     */
    @Column(name = "visitSex", nullable = true, length = 10)
    private Integer visitSex;

    /**
     * 民族
     */
    @Column(name = "visitNation", nullable = true, length = 50)
    private String visitNation;

    /**
     * visitAddress
     */
    @Column(name = "visitAddress", nullable = true, length = 100)
    private String visitAddress;

    /**
     * 人像
     */
    @Column(name = "visitPicture", nullable = true, length = 255)
    private String visitPicture;

    /**
     * 来访时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "visitTime", nullable = true, length = 26)
    private Date visitTime;


    /**
     * 离开时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "leaveTime", nullable = true, length = 26)
    private Date leaveTime;

    /**
     * 来访事由
     */
    @Column(name = "visitCause", nullable = true, length = 50)
    private String visitCause;
    // 出生日期
    @Transient
    String  bornTime;
    @Transient
    String  deviceIP;
    @Transient
    String  smallSrc; // 小图入库src
    @Transient
    String  bigSrc; // 大图入库src
    @Transient
    String smallJpg; // 本地小图src
    @Transient
    String faceSrc; // 脸谱face
    @Transient
    String cardPhoto;
    @Transient
    Integer signCode;
    @Transient
    String regionName;
    @Transient
    String deviceName;
    @Transient
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date trackDate;

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Date getTrackDate() {
        return trackDate;
    }

    public void setTrackDate(Date trackDate) {
        this.trackDate = trackDate;
    }

    public String getBasePicture() {
        return basePicture;
    }

    public void setBasePicture(String basePicture) {
        this.basePicture = basePicture;
    }

    @Transient
    Integer month;//历史来访数据有效月数
    @Transient
    String basePicture;

    @Column(name = "lawyerCard", nullable = true, length = 50)
    private String lawyerCard; // 律师证件号

    public String getLawyerCard() {
        return lawyerCard;
    }

    public void setLawyerCard(String lawyerCard) {
        this.lawyerCard = lawyerCard;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getSignCode() {
        return signCode;
    }

    public void setSignCode(Integer signCode) {
        this.signCode = signCode;
    }

    public String getCardPhoto() {
        return cardPhoto;
    }

    public void setCardPhoto(String cardPhoto) {
        this.cardPhoto = cardPhoto;
    }

    public String getDeviceIP() {
        return deviceIP;
    }

    public void setDeviceIP(String deviceIP) {
        this.deviceIP = deviceIP;
    }

    public String getSmallSrc() {
        return smallSrc;
    }

    public void setSmallSrc(String smallSrc) {
        this.smallSrc = smallSrc;
    }

    public String getBigSrc() {
        return bigSrc;
    }

    public void setBigSrc(String bigSrc) {
        this.bigSrc = bigSrc;
    }

    public String getBornTime() {
        return bornTime;
    }

    public void setBornTime(String bornTime) {
        this.bornTime = bornTime;
    }

    public String getSmallJpg() {
        return smallJpg;
    }

    public void setSmallJpg(String smallJpg) {
        this.smallJpg = smallJpg;
    }

    public String getFaceSrc() {
        return faceSrc;
    }

    public void setFaceSrc(String faceSrc) {
        this.faceSrc = faceSrc;
    }

    // 是否是今日访客
    @Transient
    private String isToday = "yes";

    @Transient
    private Boolean isLawyer = false;

    public Boolean getLawyer() {
        return isLawyer;
    }

    public void setLawyer(Boolean lawyer) {
        isLawyer = lawyer;
    }

    public List<Integer> getVisitorIds() {
        return visitorIds;
    }

    public void setVisitorIds(List<Integer> visitorIds) {
        this.visitorIds = visitorIds;
    }

    //搜索关键字
    @Transient
    private String keyword;

    @Transient
    private List<Integer> visitorIds;

    public List<Integer> getVisitIds() {
        return visitorIds;
    }

    public void setVisitIds(List<Integer> visitIds) {
        this.visitorIds = visitIds;
    }



    public String getIsToday() {
        return isToday;
    }

    public void setIsToday(String isToday) {
        this.isToday = isToday;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    /**
     * 获取visitId
     *
     * @return visitId
     */
    public Integer getVisitId() {
        return this.visitId;
    }

    /**
     * 设置visitId
     *
     * @param visitId
     */
    public void setVisitId(Integer visitId) {
        this.visitId = visitId;
    }

    /**
     * 获取来访人员姓名
     *
     * @return 来访人员姓名
     */
    public String getVisitName() {
        return this.visitName;
    }

    /**
     * 设置来访人员姓名
     *
     * @param visitName 来访人员姓名
     */
    public void setVisitName(String visitName) {
        this.visitName = visitName;
    }

    /**
     * 获取证件类型
     *
     * @return 证件类型
     */
    public Integer getVisitCardType() {
        return this.visitCardType;
    }

    /**
     * 设置证件类型
     *
     * @param visitCardType 证件类型
     */
    public void setVisitCardType(Integer visitCardType) {
        this.visitCardType = visitCardType;
    }

    /**
     * 获取来访人员证件号
     *
     * @return 来访人员证件号
     */
    public String getVisitCardNumber() {
        return this.visitCardNumber;
    }

    /**
     * 设置来访人员证件号
     *
     * @param visitCardNumber 来访人员证件号
     */
    public void setVisitCardNumber(String visitCardNumber) {
        this.visitCardNumber = visitCardNumber;
    }

    /**
     * 获取性别
     *
     * @return 性别
     */
    public Integer getVisitSex() {
        return this.visitSex;
    }

    /**
     * 设置性别
     *
     * @param visitSex 性别
     */
    public void setVisitSex(Integer visitSex) {
        this.visitSex = visitSex;
    }

    /**
     * 获取民族
     *
     * @return 民族
     */
    public String getVisitNation() {
        return this.visitNation;
    }

    /**
     * 设置民族
     *
     * @param visitNation 民族
     */
    public void setVisitNation(String visitNation) {
        this.visitNation = visitNation;
    }

    /**
     * 获取visitAddress
     *
     * @return visitAddress
     */
    public String getVisitAddress() {
        return this.visitAddress;
    }

    /**
     * 设置visitAddress
     *
     * @param visitAddress
     */
    public void setVisitAddress(String visitAddress) {
        this.visitAddress = visitAddress;
    }

    /**
     * 获取人像
     *
     * @return 人像
     */
    public String getVisitPicture() {
        return this.visitPicture;
    }

    /**
     * 设置人像
     *
     * @param visitPicture 人像
     */
    public void setVisitPicture(String visitPicture) {
        this.visitPicture = visitPicture;
    }

    /**
     * 获取来访时间
     *
     * @return 来访时间
     */
    public Date getVisitTime() {
        return this.visitTime;
    }

    /**
     * 设置来访时间
     *
     * @param visitTime 来访时间
     */
    public void setVisitTime(Date visitTime) {
        this.visitTime = visitTime;
    }

    /**
     * 获取离开时间
     *
     * @return 离开时间
     */
    public Date getLeaveTime() {
        return this.leaveTime;
    }

    /**
     * 设置离开时间
     *
     * @param leaveTime 离开时间
     */
    public void setLeaveTime(Date leaveTime) {
        this.leaveTime = leaveTime;
    }

    /**
     * 获取来访事由
     *
     * @return 来访事由
     */
    public String getVisitCause() {
        return this.visitCause;
    }

    /**
     * 设置来访事由
     *
     * @param visitCause 来访事由
     */
    public void setVisitCause(String visitCause) {
        this.visitCause = visitCause;
    }


    @Override
    public String toString() {
        return "Visitor{" +
                "visitId=" + visitId +
                ", visitName='" + visitName + '\'' +
                ", visitCardType=" + visitCardType +
                ", visitCardNumber='" + visitCardNumber + '\'' +
                ", visitSex=" + visitSex +
                ", visitNation='" + visitNation + '\'' +
                ", visitAddress='" + visitAddress + '\'' +
                ", visitPicture='" + visitPicture + '\'' +
                ", visitTime=" + visitTime +
                ", leaveTime=" + leaveTime +
                ", visitCause='" + visitCause + '\'' +
                ", isToday='" + isToday + '\'' +
                ", keyword='" + keyword + '\'' +
                ", visitorIds=" + visitorIds +
                ", lawyerCard=" + lawyerCard +
                '}';
    }
}