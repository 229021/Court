package com.san.platform.device;

import java.util.HashMap;
import java.util.List;

/**
 * 接口
 */
public interface DeviceService {

    public List<Device> queryAllDevice();
    /**
     * 查询所有设备信息----搜索查询
     * @param device
     * @return
     */
    public HashMap<String,Object> queryDevicePage(Device device);

    /**
     * 根据iD查询设备信息
     * @param device
     * @return
     */
    public Device queryDeviceById (Device device);


    /**
     *
     * @param device
     * @return
     */
    public Device queryDeviceOne (Device device);

    /**
     *相同设备名的有多少条
     * @param device
     * @return
     */
    public int queryDeviceCount(Device device);

    /**
     *删除设备信息
     * @param device
     */
    public int removeDevice (Device device);

    /**
     * 添加设备信息
     * @param device
     */
    public int createDevice (Device device);

    /**
     * 修改设备信息
     * @param device
     */
    public int updateDevice (Device device);

    /**
     * 查询设备所有信息
     * @param device
     */
    public List<Device> queryAllDeviceCount(Device device);

    /**
     * 根据DeviceType查询设备name
     * @param device
     * @return
     */
    public List<Device> queryDeviceType (Device device);

    /**
     * 筛选
     * @param
     * @return
     */
    public List<Device> queryDeviceIdNot (Device device);

    /**
     * 根据iD查询设备信息
     * @param device
     * @return
     */
    public Device queryDeviceByIdSQL (Device device);

    /**
     * 查询所有的闸机设备-type=1002,1003
     * @return
     */
    public List<Device> queryDeviceByType();

    /**
     * 根据设备IP查询设备信息
     * @param deviceIP
     * @return
     */
    public Device queryDeviceByIP (String deviceIP);

//    /**
//     * @Author: Zhouzhongqing
//     * @Date: 2019/8/14 15:54
//     * @Description: 查询所有球机
//     */
//    public List<Device> selectDeviceByType();


}
