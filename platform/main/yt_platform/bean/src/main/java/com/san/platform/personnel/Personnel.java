package com.san.platform.personnel;

import com.san.platform.page.Page;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Table(name = "tbl_personnel")
public class Personnel extends Page implements Serializable {
    private static final long serialVersionUID = 1L;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "perId")
    private Integer perId;
    @Column(name = "perName")
    private String perName;
    @Column(name = "perIDType")
    private Integer perIDType;
    @Column(name = "perNumber")
    private String perNumber;
    @Column(name = "perStatus")
    private Integer perStatus;
    @Column(name = "perBirthDate")
    private Date perBirthDate;
    @Column(name = "perSex")
    private Integer perSex;
    @Column(name = "perNation")
    private String perNation;
    @Column(name = "perPhone")
    private String perPhone;
    @Column(name = "perAddress")
    private String perAddress;
    @Column(name = "perCarNumber")
    private String perCarNumber;
    @Column(name = "perPicture")
    private String perPicture;
    @Column(name = "perType")
    private Integer perType;
    @Column(name = "perMemo")
    private String perMemo;
    @Column(name = "updateDate")
    private Date updateDate;
    @Column(name = "createDate")
    private Date createDate;
    @Column(name = "perIds")
    private List<Integer> perIds;
    @Column(name = "perUUID")
    private String perUUID;
    @Column(name = "whetherUse")
    private String whetherUse;
    // 律师证件号
    @Column(name = "lawyerCard")
    private String lawyerCard;
    @Transient
    private String basePicture;

    @Transient
    private String isTday; // 今日日期

    @Transient
    String cardPhoto;
    @Transient
    String visitPicture;
    @Transient
    Integer signCode;

    public String getCardPhoto() {
        return cardPhoto;
    }

    public void setCardPhoto(String cardPhoto) {
        this.cardPhoto = cardPhoto;
    }

    public String getVisitPicture() {
        return visitPicture;
    }

    public void setVisitPicture(String visitPicture) {
        this.visitPicture = visitPicture;
    }

    public Integer getSignCode() {
        return signCode;
    }

    public void setSignCode(Integer signCode) {
        this.signCode = signCode;
    }

    @Override
    public String toString() {
        return "Personnel{" +
                "perId=" + perId +
                ", perName='" + perName + '\'' +
                ", perIDType=" + perIDType +
                ", perNumber='" + perNumber + '\'' +
                ", perStatus=" + perStatus +
                ", perBirthDate=" + perBirthDate +
                ", perSex=" + perSex +
                ", perNation='" + perNation + '\'' +
                ", perPhone='" + perPhone + '\'' +
                ", perAddress='" + perAddress + '\'' +
                ", perCarNumber='" + perCarNumber + '\'' +
                ", perPicture='" + perPicture + '\'' +
                ", perType=" + perType +
                ", perMemo='" + perMemo + '\'' +
                ", updateDate=" + updateDate +
                ", createDate=" + createDate +
                ", perIds=" + perIds +
                ", perUUID='" + perUUID + '\'' +
                ", whetherUse='" + whetherUse + '\'' +
                ", lawyerCard='" + lawyerCard + '\'' +
                ", isTday='" + isTday + '\'' +
                '}';
    }

    public String getLawyerCard() {
        return lawyerCard;
    }

    public void setLawyerCard(String lawyerCard) {
        this.lawyerCard = lawyerCard;
    }

    public String getWhetherUse() {
        return whetherUse;
    }

    public void setWhetherUse(String whetherUse) {
        this.whetherUse = whetherUse;
    }

    public String getIsTday() {
        return isTday;
    }

    public void setIsTday(String isTday) {
        this.isTday = isTday;
    }

    public String getPerUUID() {
        return perUUID;
    }

    public void setPerUUID(String perUUID) {
        this.perUUID = perUUID;
    }

    public Integer getPerId() {
        return perId;
    }

    public void setPerId(Integer perId) {
        this.perId = perId;
    }

    public String getPerName() {
        return perName;
    }

    public void setPerName(String perName) {
        this.perName = perName;
    }

    public Integer getPerIDType() {
        return perIDType;
    }

    public void setPerIDType(Integer perIDType) {
        this.perIDType = perIDType;
    }

    public String getPerNumber() {
        return perNumber;
    }

    public void setPerNumber(String perNumber) {
        this.perNumber = perNumber;
    }

    public Integer getPerStatus() {
        return perStatus;
    }

    public void setPerStatus(Integer perStatus) {
        this.perStatus = perStatus;
    }

    public Date getPerBirthDate() {
        return perBirthDate;
    }

    public void setPerBirthDate(Date perBirthDate) {
        this.perBirthDate = perBirthDate;
    }

    public Integer getPerSex() {
        return perSex;
    }

    public void setPerSex(Integer perSex) {
        this.perSex = perSex;
    }

    public String getPerNation() {
        return perNation;
    }

    public void setPerNation(String perNation) {
        this.perNation = perNation;
    }

    public String getPerPhone() {
        return perPhone;
    }

    public void setPerPhone(String perPhone) {
        this.perPhone = perPhone;
    }

    public String getPerAddress() {
        return perAddress;
    }

    public void setPerAddress(String perAddress) {
        this.perAddress = perAddress;
    }

    public String getPerCarNumber() {
        return perCarNumber;
    }

    public void setPerCarNumber(String perCarNumber) {
        this.perCarNumber = perCarNumber;
    }

    public String getPerPicture() {
        return perPicture;
    }

    public void setPerPicture(String perPicture) {
        this.perPicture = perPicture;
    }

    public Integer getPerType() {
        return perType;
    }

    public void setPerType(Integer perType) {
        this.perType = perType;
    }

    public String getPerMemo() {
        return perMemo;
    }

    public void setPerMemo(String perMemo) {
        this.perMemo = perMemo;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public List<Integer> getPerIds() {
        return perIds;
    }

    public void setPerIds(List<Integer> perIds) {
        this.perIds = perIds;
    }

    public String getBasePicture() {
        return basePicture;
    }

    public void setBasePicture(String basePicture) {
        this.basePicture = basePicture;
    }
}
