package com.san.platform.setting;/*

import java.util.Date;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

/**
 * InnoDB free: 5120 kB(TBL_NETWORK)
 *
 * @author bianj
 * @version 1.0.0 2019-04-16
 */

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tbl_network")
public class Network implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -2452615767462391626L;

    /** netId */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "netId", unique = true, nullable = false, length = 10)
    private Integer netId;

    /** 网卡名称 */
    @Column(name = "netName", nullable = true, length = 100)
    private String netName;

    /** 网卡IP */
    @Column(name = "netIp", nullable = true, length = 100)
    private String netIp;

    /** 掩码 */
    @Column(name = "netMask", nullable = true, length = 100)
    private String netMask;

    /** 网关 */
    @Column(name = "netGateWay", nullable = true, length = 100)
    private String netGateWay;

    /** DNS */
    @Column(name = "netDns", nullable = true, length = 100)
    private String netDns;

    /** 状态 */
    @Column(name = "netStatus", nullable = true, length = 100)
    private String netStatus;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "netUpdateTime", nullable = true, length = 26)
    private Date netUpdateTime;

    /**
     * 获取netId
     *
     * @return netId
     */
    public Integer getNetId() {
        return this.netId;
    }

    /**
     * 设置netId
     *
     * @param netId
     */
    public void setNetId(Integer netId) {
        this.netId = netId;
    }

    /**
     * 获取网卡名称
     *
     * @return 网卡名称
     */
    public String getNetName() {
        return this.netName;
    }

    /**
     * 设置网卡名称
     *
     * @param netName
     *          网卡名称
     */
    public void setNetName(String netName) {
        this.netName = netName;
    }

    /**
     * 获取网卡IP
     *
     * @return 网卡IP
     */
    public String getNetIp() {
        return this.netIp;
    }

    /**
     * 设置网卡IP
     *
     * @param netIp
     *          网卡IP
     */
    public void setNetIp(String netIp) {
        this.netIp = netIp;
    }

    /**
     * 获取掩码
     *
     * @return 掩码
     */
    public String getNetMask() {
        return this.netMask;
    }

    /**
     * 设置掩码
     *
     * @param netMask
     *          掩码
     */
    public void setNetMask(String netMask) {
        this.netMask = netMask;
    }

    /**
     * 获取网关
     *
     * @return 网关
     */
    public String getNetGateWay() {
        return this.netGateWay;
    }

    /**
     * 设置网关
     *
     * @param netGateWay
     *          网关
     */
    public void setNetGateWay(String netGateWay) {
        this.netGateWay = netGateWay;
    }

    /**
     * 获取DNS
     *
     * @return DNS
     */
    public String getNetDns() {
        return this.netDns;
    }

    /**
     * 设置DNS
     *
     * @param netDns
     *          DNS
     */
    public void setNetDns(String netDns) {
        this.netDns = netDns;
    }

    /**
     * 获取状态
     *
     * @return 状态
     */
    public String getNetStatus() {
        return this.netStatus;
    }

    /**
     * 设置状态
     *
     * @param netStatus
     *          状态
     */
    public void setNetStatus(String netStatus) {
        this.netStatus = netStatus;
    }

    /**
     * 获取更新时间
     *
     * @return 更新时间
     */
    public Date getNetUpdateTime() {
        return this.netUpdateTime;
    }

    /**
     * 设置更新时间
     *
     * @param netUpdateTime
     *          更新时间
     */
    public void setNetUpdateTime(Date netUpdateTime) {
        this.netUpdateTime = netUpdateTime;
    }

    @Override
    public String toString() {
        return "Network{" +
                "netId=" + netId +
                ", netName='" + netName + '\'' +
                ", netIp='" + netIp + '\'' +
                ", netMask='" + netMask + '\'' +
                ", netGateWay='" + netGateWay + '\'' +
                ", netDns='" + netDns + '\'' +
                ", netStatus='" + netStatus + '\'' +
                ", netUpdateTime=" + netUpdateTime +
                '}';
    }
}