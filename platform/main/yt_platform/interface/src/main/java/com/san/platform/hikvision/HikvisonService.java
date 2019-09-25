package com.san.platform.hikvision;

import com.san.platform.device.Device;
import com.san.platform.personnel.Personnel;

import java.util.HashMap;

public interface HikvisonService {
    /**
     * 名单下发
     * 白鹏
     * 调用海康接口
     */
    public boolean updateHikDevice(String name , String number ,String visitPicture);

    public long initDevice (Device device);

    /**
     * 名单下发----指定设备 zsa
     * @param deviceIp 设备ip
     * @param name 名字
     * @param number 证件号
     * @param pic 照片
     * @return
     */
    public boolean sendPer(String deviceIp, String name,String number,String pic);

    /**
     * 根据设备类型删除名单信息
     * @param deviceIP
     * @param cardNO
     */
    public boolean deleteTask (String deviceIP,String cardNO);

    /**
     * 名单下发----指定设备 zsa  新修改返
     * @param deviceIp 设备ip
     * @param name 名字
     * @param number 证件号
     * @param pic 照片
     * @return
     */
    public HashMap sendPerNew(String deviceIp, String name, String number, String pic);
}
