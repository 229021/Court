package com.san.platform.visitor;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: Zhouzhongqing
 * @Date: 2019/6/11 12:53
 * @Description: 补卡登记 临时表
*/

@Entity
@Table(name = "tbl_repaircard")
public class RepairCard implements Serializable {
    // ID
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "cardId")
    private Integer cardId;
    @Column(name = "visitPicture")
    private String visitPicture;
    @Column(name = "visitName")
    private String visitName;
    @Column(name = "visitSex")
    private Integer visitSex;
    @Column(name = "visitCardNumber")
    private String visitCardNumber;
    @Column(name = "visitNation")
    private String visitNation;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "visitTime", nullable = true, length = 26)
    private Date visitTime;
    @Column(name = "visitCause")
    private String visitCause;
    @Column(name = "cardPhoto")
    private String cardPhoto;

    @Column(name = "visitAddress")
    private String visitAddress;
    @Column(name = "visitCardType")
    private Integer visitCardType;
    // 出生日期
    @Transient
    String  bornTime;
    @Transient
    Integer signCode;
    @Transient
    String lawyerCard;

    public String getLawyerCard() {
        return lawyerCard;
    }

    public void setLawyerCard(String lawyerCard) {
        this.lawyerCard = lawyerCard;
    }

    public Integer getSignCode() {
        return signCode;
    }

    public void setSignCode(Integer signCode) {
        this.signCode = signCode;
    }

    public String getBornTime() {
        return bornTime;
    }

    public void setBornTime(String bornTime) {
        this.bornTime = bornTime;
    }

    public String getVisitAddress() {
        return visitAddress;
    }

    public void setVisitAddress(String visitAddress) {
        this.visitAddress = visitAddress;
    }

    public Integer getVisitCardType() {
        return visitCardType;
    }

    public void setVisitCardType(Integer visitCardType) {
        this.visitCardType = visitCardType;
    }

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public String getVisitPicture() {
        return visitPicture;
    }

    public void setVisitPicture(String visitPicture) {
        this.visitPicture = visitPicture;
    }

    public String getVisitName() {
        return visitName;
    }

    public void setVisitName(String visitName) {
        this.visitName = visitName;
    }

    public Integer getVisitSex() {
        return visitSex;
    }

    public void setVisitSex(Integer visitSex) {
        this.visitSex = visitSex;
    }

    public String getVisitCardNumber() {
        return visitCardNumber;
    }

    public void setVisitCardNumber(String visitCardNumber) {
        this.visitCardNumber = visitCardNumber;
    }

    public String getVisitNation() {
        return visitNation;
    }

    public void setVisitNation(String visitNation) {
        this.visitNation = visitNation;
    }

    public Date getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(Date visitTime) {
        this.visitTime = visitTime;
    }

    public String getVisitCause() {
        return visitCause;
    }

    public void setVisitCause(String visitCause) {
        this.visitCause = visitCause;
    }

    public String getCardPhoto() {
        return cardPhoto;
    }

    public void setCardPhoto(String cardPhoto) {
        this.cardPhoto = cardPhoto;
    }

    @Override
    public String toString() {
        return "RepairCard{" +
                "cardId=" + cardId +
                ", visitPicture='" + visitPicture + '\'' +
                ", visitName='" + visitName + '\'' +
                ", visitSex=" + visitSex +
                ", visitCardNumber='" + visitCardNumber + '\'' +
                ", visitNation='" + visitNation + '\'' +
                ", visitTime=" + visitTime +
                ", visitCause='" + visitCause + '\'' +
                ", cardPhoto='" + cardPhoto + '\'' +
                ", visitAddress='" + visitAddress + '\'' +
                ", visitCardType=" + visitCardType +
                '}';
    }
}
