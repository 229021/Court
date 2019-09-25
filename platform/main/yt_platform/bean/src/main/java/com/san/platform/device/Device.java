package com.san.platform.device;
import com.san.platform.page.Page;
import java.util.Date;
import javax.persistence.*;

/**
 * TBL_DEVICE
 *
 * @author zsa
 * @version 1.0.0 2019-04-29
 */
@Entity
@Table(name = "tbl_device")
public class Device extends Page implements java.io.Serializable {

    /** deviceId */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deviceId", unique = true, nullable = false, length = 10)
    private Integer deviceId;

    /** 设备名称 */
    @Column(name = "deviceName", nullable = true, length = 50)
    private String deviceName;

    /** guid */
    @Column(name = "deviceGuid", nullable = true, length = 20)
    private String deviceGuid;

    /** 设备类型 */
    @Column(name = "deviceType", nullable = true, length = 10)
    private Integer deviceType;

    /** deviceAbility */
    @Column(name = "deviceAbility", nullable = true, length = 255)
    private String deviceAbility;

    /** 设备IP */
    @Column(name = "deviceIP", nullable = true, length = 20)
    private String deviceip;

    /** 设备端口 */
    @Column(name = "devicePort", nullable = true, length = 10)
    private Integer devicePort;

    /** 登陆用户名 */
    @Column(name = "deviceLogin", nullable = true, length = 50)
    private String deviceLogin;

    /** 登陆密码 */
    @Column(name = "devicePass", nullable = true, length = 50)
    private String devicePass;

    /** 通道数量 */
    @Column(name = "deviceChannel", nullable = true, length = 10)
    private Integer deviceChannel;

    /** 创建时间 */
    @Column(name = "deviceCreateTime", nullable = true, length = 26)
    private Date deviceCreateTime;

    /** 修改时间 */
    @Column(name = "deviceModTime", nullable = true, length = 26)
    private Date deviceModTime;

    /** 备注 */
    @Column(name = "deviceMemo", nullable = true, length = 65535)
    private String deviceMemo;

    /**
     * 获取deviceId
     *
     * @return deviceId
     */
    public Integer getDeviceId() {
        return this.deviceId;
    }

    /**
     * 设置deviceId
     *
     * @param deviceId
     */
    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * 获取设备名称
     *
     * @return 设备名称
     */
    public String getDeviceName() {
        return this.deviceName;
    }

    /**
     * 设置设备名称
     *
     * @param deviceName
     *          设备名称
     */
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    /**
     * 获取guid
     *
     * @return guid
     */
    public String getDeviceGuid() {
        return this.deviceGuid;
    }

    /**
     * 设置guid
     *
     * @param deviceGuid
     *          guid
     */
    public void setDeviceGuid(String deviceGuid) {
        this.deviceGuid = deviceGuid;
    }

    /**
     * 获取设备类型
     *
     * @return 设备类型
     */
    public Integer getDeviceType() {
        return this.deviceType;
    }

    /**
     * 设置设备类型
     *
     * @param deviceType
     *          设备类型
     */
    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    /**
     * 获取deviceAbility
     *
     * @return deviceAbility
     */
    public String getDeviceAbility() {
        return this.deviceAbility;
    }

    /**
     * 设置deviceAbility
     *
     * @param deviceAbility
     */
    public void setDeviceAbility(String deviceAbility) {
        this.deviceAbility = deviceAbility;
    }

    /**
     * 获取设备IP
     *
     * @return 设备IP
     */
    public String getDeviceip() {
        return this.deviceip;
    }

    /**
     * 设置设备IP
     *
     * @param deviceip
     *          设备IP
     */
    public void setDeviceip(String deviceip) {
        this.deviceip = deviceip;
    }

    /**
     * 获取设备端口
     *
     * @return 设备端口
     */
    public Integer getDevicePort() {
        return this.devicePort;
    }

    /**
     * 设置设备端口
     *
     * @param devicePort
     *          设备端口
     */
    public void setDevicePort(Integer devicePort) {
        this.devicePort = devicePort;
    }

    /**
     * 获取登陆用户名
     *
     * @return 登陆用户名
     */
    public String getDeviceLogin() {
        return this.deviceLogin;
    }

    /**
     * 设置登陆用户名
     *
     * @param deviceLogin
     *          登陆用户名
     */
    public void setDeviceLogin(String deviceLogin) {
        this.deviceLogin = deviceLogin;
    }

    /**
     * 获取登陆密码
     *
     * @return 登陆密码
     */
    public String getDevicePass() {
        return this.devicePass;
    }

    /**
     * 设置登陆密码
     *
     * @param devicePass
     *          登陆密码
     */
    public void setDevicePass(String devicePass) {
        this.devicePass = devicePass;
    }

    /**
     * 获取通道数量
     *
     * @return 通道数量
     */
    public Integer getDeviceChannel() {
        return this.deviceChannel;
    }

    /**
     * 设置通道数量
     *
     * @param deviceChannel
     *          通道数量
     */
    public void setDeviceChannel(Integer deviceChannel) {
        this.deviceChannel = deviceChannel;
    }

    /**
     * 获取创建时间
     *
     * @return 创建时间
     */
    public Date getDeviceCreateTime() {
        return this.deviceCreateTime;
    }

    /**
     * 设置创建时间
     *
     * @param deviceCreateTime
     *          创建时间
     */
    public void setDeviceCreateTime(Date deviceCreateTime) {
        this.deviceCreateTime = deviceCreateTime;
    }

    /**
     * 获取修改时间
     *
     * @return 修改时间
     */
    public Date getDeviceModTime() {
        return this.deviceModTime;
    }

    /**
     * 设置修改时间
     *
     * @param deviceModTime
     *          修改时间
     */
    public void setDeviceModTime(Date deviceModTime) {
        this.deviceModTime = deviceModTime;
    }

    /**
     * 获取备注
     *
     * @return 备注
     */
    public String getDeviceMemo() {
        return this.deviceMemo;
    }

    /**
     * 设置备注
     *
     * @param deviceMemo
     *          备注
     */
    public void setDeviceMemo(String deviceMemo) {
        this.deviceMemo = deviceMemo;
    }
}