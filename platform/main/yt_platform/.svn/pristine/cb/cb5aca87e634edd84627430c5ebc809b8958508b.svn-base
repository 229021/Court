package com.san.platform.alert;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.san.platform.page.Page;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Table(name = "tbl_persontype")
public class PersonType extends Page implements Serializable {
    private static final long serialVersionUID = 1L;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "personTypeId")
    private Integer personTypeId;
    // 类型
    @Column(name = "typeId")
    private Integer typeId;
    // 名字
    @Column(name = "typeName")
    private String typeName;
    // 标识
    @Column(name = "typeSign")
    private Integer typeSign;
    // 有效期
    @Column(name = "validTime")
    private Integer validTime;
    // 备注
    @Column(name = "typeMemo")
    private String typeMemo;
    // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "CreateTime")
    private Date CreateTime;
    // 修改时间
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "ModTime")
    private Date ModTime;

    public String getTypeMemo() {
        return typeMemo;
    }

    public void setTypeMemo(String typeMemo) {
        this.typeMemo = typeMemo;
    }

    public Integer getValidTime() {
        return validTime;
    }

    public void setValidTime(Integer validTime) {
        this.validTime = validTime;
    }

    public Date getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Date createTime) {
        CreateTime = createTime;
    }

    public Date getModTime() {
        return ModTime;
    }

    public void setModTime(Date modTime) {
        ModTime = modTime;
    }

    public Integer getTypeSign() {
        return typeSign;
    }

    public void setTypeSign(Integer typeSign) {
        this.typeSign = typeSign;
    }

    public Integer getPersonTypeId() {
        return personTypeId;
    }

    public void setPersonTypeId(Integer personTypeId) {
        this.personTypeId = personTypeId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "PersonType{" +
                "personTypeId=" + personTypeId +
                ", typeId=" + typeId +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}
