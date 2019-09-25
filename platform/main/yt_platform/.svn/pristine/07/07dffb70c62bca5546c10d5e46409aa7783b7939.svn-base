package com.san.platform.device;

import com.san.platform.page.Page;

import javax.persistence.*;
import java.util.Date;

/**
 * tbl_ability 设备智能表
 * 
 * @author zsa
 * @version 1.0.0 2019-04-29
 */
@Entity
@Table(name = "tbl_ability")
public class Ability extends Page implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 6159037570210043256L;

    /** aId */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aId", unique = true, nullable = false, length = 10)
    private Integer aId;

    /** 设备职能名称 */
    @Column(name = "aName", nullable = true, length = 50)
    private String aName;

    /** aGuid */
    @Column(name = "aGuid", nullable = true, length = 32)
    private String aGuid;

    /** 设备职能备注 */
    @Column(name = "aMemo", nullable = true, length = 65535)
    private String aMemo;

    /** 创建时间 */
    @Column(name = "aCreateTime", nullable = true, length = 26)
    private Date aCreateTime;

    /** 修改时间 */
    @Column(name = "aModTime", nullable = true, length = 26)
    private Date aModTime;

    /**
     * 获取aId
     *
     * @return aId
     */
    public Integer getaId() {
        return this.aId;
    }

    /**
     * 设置aId
     *
     * @param aId
     */
    public void setaId(Integer aId) {
        this.aId = aId;
    }

    /**
     * 获取设备职能名称
     *
     * @return 设备职能名称
     */
    public String getaName() {
        return this.aName;
    }

    /**
     * 设置设备职能名称
     *
     * @param aName
     *          设备职能名称
     */
    public void setaName(String aName) {
        this.aName = aName;
    }

    /**
     * 获取aGuid
     *
     * @return aGuid
     */
    public String getaGuid() {
        return this.aGuid;
    }

    /**
     * 设置aGuid
     *
     * @param aGuid
     */
    public void setaGuid(String aGuid) {
        this.aGuid = aGuid;
    }

    /**
     * 获取设备职能备注
     *
     * @return 设备职能备注
     */
    public String getaMemo() {
        return this.aMemo;
    }

    /**
     * 设置设备职能备注
     *
     * @param aMemo
     *          设备职能备注
     */
    public void setaMemo(String aMemo) {
        this.aMemo = aMemo;
    }

    /**
     * 获取创建时间
     *
     * @return 创建时间
     */
    public Date getaCreateTime() {
        return this.aCreateTime;
    }

    /**
     * 设置创建时间
     *
     * @param aCreateTime
     *          创建时间
     */
    public void setaCreateTime(Date aCreateTime) {
        this.aCreateTime = aCreateTime;
    }

    /**
     * 获取修改时间
     *
     * @return 修改时间
     */
    public Date getaModTime() {
        return this.aModTime;
    }

    /**
     * 设置修改时间
     *
     * @param aModTime
     *          修改时间
     */
    public void setaModTime(Date aModTime) {
        this.aModTime = aModTime;
    }
}