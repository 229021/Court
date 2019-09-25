package com.san.platform.Lawyer;

import com.san.platform.page.Page;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Table(name = "tbl_lawyer")
public class Lawyer extends Page implements Serializable {
    private static final long serialVersionUID = 1L;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "lawyerId")
    private Integer lawyerId;
    @Column(name = "lawyerName")
    private String lawyerName;
    @Column(name = "lawyerNumber")
    private String lawyerNumber;
    @Column(name = "lawyerSex")
    private Integer lawyerSex;
    @Column(name = "lawyerNation")
    private String lawyerNation;
    @Column(name = "lawyerTel")
    private String lawyerTel;
    @Column(name = "lawyerAddress")
    private String lawyerAddress;
    @Column(name = "lawyerCarNumber")
    private String lawyerCarNumber;
    @Column(name = "lawyerPicture")
    private String lawyerPicture;
    @Column(name = "updateDate")
    private Date updateDate;
    @Column(name = "createDate")
    private Date createDate;
    @Column(name = "perIds")
    private List<Integer> perIds;

    public Integer getLawyerId() {
        return lawyerId;
    }

    public void setLawyerId(Integer lawyerId) {
        this.lawyerId = lawyerId;
    }

    public String getLawyerName() {
        return lawyerName;
    }

    public void setLawyerName(String lawyerName) {
        this.lawyerName = lawyerName;
    }

    public String getLawyerNumber() {
        return lawyerNumber;
    }

    public void setLawyerNumber(String lawyerNumber) {
        this.lawyerNumber = lawyerNumber;
    }

    public Integer getLawyerSex() {
        return lawyerSex;
    }

    public void setLawyerSex(Integer lawyerSex) {
        this.lawyerSex = lawyerSex;
    }

    public String getLawyerNation() {
        return lawyerNation;
    }

    public void setLawyerNation(String lawyerNation) {
        this.lawyerNation = lawyerNation;
    }

    public String getLawyerTel() {
        return lawyerTel;
    }

    public void setLawyerTel(String lawyerTel) {
        this.lawyerTel = lawyerTel;
    }

    public String getLawyerAddress() {
        return lawyerAddress;
    }

    public void setLawyerAddress(String lawyerAddress) {
        this.lawyerAddress = lawyerAddress;
    }

    public String getLawyerCarNumber() {
        return lawyerCarNumber;
    }

    public void setLawyerCarNumber(String lawyerCarNumber) {
        this.lawyerCarNumber = lawyerCarNumber;
    }

    public String getLawyerPicture() {
        return lawyerPicture;
    }

    public void setLawyerPicture(String lawyerPicture) {
        this.lawyerPicture = lawyerPicture;
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
}
