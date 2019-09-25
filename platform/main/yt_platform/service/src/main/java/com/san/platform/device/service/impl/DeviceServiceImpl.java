package com.san.platform.device.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.san.platform.config.Global;
import com.san.platform.device.Device;
import com.san.platform.device.DeviceService;
import com.san.platform.device.mapper.DeviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: zsa
 * @Date: 2019/4/29 11:22
 * @Description: 设备管理service实现类
 */
@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceMapper deviceMapper;

    @Override
    public List<Device> queryAllDevice() {
        HashMap<String, Object> map = new HashMap<>();
        List<Device> deviceList = deviceMapper.selectAll();
        return deviceList;
    }
    /**
     * 查询所有设备信息----搜索查询 分页
     * @param device
     * @return
     */
    @Override
    public HashMap<String, Object> queryDevicePage(Device device) {
        HashMap<String, Object> map = new HashMap<>();
        // 当前页码 pageNum 与 每页显示条数 pageSize分页
        PageHelper.startPage(device.getPageNum(), Global.PAGESIZE);
        // 分页查询
        List<Device> devices = deviceMapper.queryAllDevice(device);
        // 得到page相关的info
        PageInfo pageInfo = new PageInfo(devices);
        // 将list与相关的page信息传入map到web端
        map.put("pages",new Integer(pageInfo.getPages()));
        map.put("total",pageInfo.getTotal());
        map.put("results",devices);
        return map;
    }

    /**
     * 根据id查询设备信息
     * @param device
     * @return int
     */
    @Override
    public Device queryDeviceById(Device device) {
        return deviceMapper.selectByPrimaryKey(device);
    }

    /**
     * name唯一查询
     * @param device
     * @return
     */
    public Device queryDeviceOne (Device device){
        return deviceMapper.selectOne(device);
    }

    /**
     *  相同设备名的有多少条
     * @param device
     * @return
     */
    public int queryDeviceCount(Device device) {
        return deviceMapper.selectCount(device);
    }
    /**
     * 根据id删除设备信息
     * @param device
     * @return int
     */
    @Override
    public int removeDevice(Device device) {
        return deviceMapper.deleteByPrimaryKey(device);
    }

    /**
     * 添加设备信息
     * @param device
     * @return int
     */
    @Override
    public int createDevice(Device device) {
        return deviceMapper.insertSelective(device);
    }

    /**
     * 更新设备信息
     * @param device
     * @return int
     */
    @Override
    public int updateDevice(Device device) {
        return deviceMapper.updateByPrimaryKeySelective(device);
    }

    /**
     * 查询设备所有信息
     * @param device
     */
    @Override
    public List<Device> queryAllDeviceCount(Device device) {
        return deviceMapper.queryAllDeviceCount(device);
    }

    @Override
    public List<Device> queryDeviceType(Device device) {
        return deviceMapper.queryDeviceType(device);
    }

    @Override
    public List<Device> queryDeviceIdNot(Device device) {
        return deviceMapper.queryDeviceIdNot(device);
    }

    @Override
    public Device queryDeviceByIdSQL(Device device) {
        return deviceMapper.queryDeviceByIdSQL(device);
    }

    /**
     * 查询所有的闸机设备-type=1002,1003
     * @return
     */
    public List<Device>queryDeviceByType(){
        return deviceMapper.queryDeviceByType();
    }

    /**
     * 根据设备IP查询设备信息
     * @param deviceIP
     * @return
     */
    @Override
    public Device queryDeviceByIP(String deviceIP) {
        return deviceMapper.queryDeviceByIP(deviceIP);
    }

//    @Override
//    public List<Device> selectDeviceByType() {
//        return deviceMapper.selectDeviceByType();
//    }
}
