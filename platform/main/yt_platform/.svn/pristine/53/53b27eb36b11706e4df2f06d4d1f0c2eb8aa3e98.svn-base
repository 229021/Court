package com.san.platform.control;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="tbl_control")
public class Control implements Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 2715117580337219596L;

    /** 布控信息i */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, length = 10)
    private Integer id;

    /** guid */
    @Column(name = "conGuid", nullable = true, length = 10)
    private String conGuid;

    /** 设备ID */
    @Column(name = "conDeviceId", nullable = true, length = 10)
    private Integer conDeviceId;

    /** 地图ID */
    @Column(name = "conMapId", nullable = true, length = 10)
    private Integer conMapId;

    /** 布控备注 */
    @Column(name = "conMemo", nullable = true, length = 255)
    private String conMemo;

    /** 设备坐标X */
    @Column(name = "deviceMapX", nullable = true, length = 10)
    private String deviceMapX;

    /** 设备坐标Y */
    @Column(name = "deviceMapY", nullable = true, length = 10)
    private String deviceMapY;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getConGuid() {
        return conGuid;
    }

    public void setConGuid(String conGuid) {
        this.conGuid = conGuid;
    }

    public Integer getConDeviceId() {
        return conDeviceId;
    }

    public void setConDeviceId(Integer conDeviceId) {
        this.conDeviceId = conDeviceId;
    }

    public Integer getConMapId() {
        return conMapId;
    }

    public void setConMapId(Integer conMapId) {
        this.conMapId = conMapId;
    }

    public String getConMemo() {
        return conMemo;
    }

    public void setConMemo(String conMemo) {
        this.conMemo = conMemo;
    }

    public String getDeviceMapX() {
        return deviceMapX;
    }

    public void setDeviceMapX(String deviceMapX) {
        this.deviceMapX = deviceMapX;
    }

    public String getDeviceMapY() {
        return deviceMapY;
    }

    public void setDeviceMapY(String deviceMapY) {
        this.deviceMapY = deviceMapY;
    }
}
