package com.san.platform.device;

import com.alibaba.dubbo.config.annotation.Reference;
import com.san.platform.BaseController;
import com.san.platform.config.Global;
import com.san.platform.config.RandomUtil;
import com.san.platform.hikvision.HikvisonService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @Author: zsaqueryAllDevice
 * @Date: 2019/4/29 13:42
 * @Description: 设备管理controller
 */
@RestController
public class DeviceController extends BaseController {

    @Reference
    private DeviceService deviceService;
    @Reference
    private HikvisonService hikvisonService;


    @ResponseBody
    @GetMapping(value = "/api/device/queryAllDevice")
    public HashMap<String, Object> queryAllDevice() {
        logger.info("[device.api]-[get]-[/api/device/queryAllDevice] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        List list = new ArrayList();
        try {
            // select选择 默认值
            HashMap<String, Object> mapDefault = new HashMap<>();
            mapDefault.put("text","请选择---");
            mapDefault.put("value",0);
            List<Device> deviceList = deviceService.queryAllDevice();
            if (deviceList.size()>0) {
                for (Device device : deviceList) {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("text", device.getDeviceName());
                    hashMap.put("value", device.getDeviceId().toString());
                    list.add(hashMap);
                }
                map.put("results", list);
            }
            // map加入code信息 200
            map.put("code", Global.CODE_SUCCESS); // 数据正常，返回code 200
        } catch (Exception e) { // 数据异常，返回code 2003
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[device.api]-[get]-[/api/device/queryAllDevice] CODE_DB_QUERY_EXCEPTION exception:" + e.getMessage());
        }
        return map;
    }
    @ResponseBody
    @GetMapping(value = "/api/device/queryDevicePage")
    public HashMap<String, Object> queryDevicePage(Device device){
        logger.info("[device.api]-[get]-[/api/device/queryDevicePage] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            // 分页查询设备信息，返回map集合
            map = deviceService.queryDevicePage(device);
            // map加入code信息 200
            map.put("code", Global.CODE_SUCCESS); // 数据正常，返回code 200
        } catch (Exception e) { // 数据异常，返回code 2003
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[device.api]-[get]-[/api/device/queryDevicePage] CODE_DB_QUERY_EXCEPTION exception:" + e.getMessage());
        }
        return map;
    }

    /**
     * 添加设备
     * @param device
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/api/device/createDevice")
    public HashMap<String, Object> createDevice(@RequestBody Device device) {
        logger.info("[device.api]-[post]-[/api/device/createDevice] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            Device device1 = new Device();
            device1.setDeviceName(device.getDeviceName());
            // 查询当前设备名称是否已存在
            Device device2 = deviceService.queryDeviceOne(device1);
            int i = 0;
            long ret = 0;
            if (device2 == null) { // 当前设备名称不存在
                device.setDeviceGuid(RandomUtil.GetGuid32());
                device.setDeviceCreateTime(new Date());
                device.setDeviceModTime(new Date());
                i = deviceService.createDevice(device);
                if (i != 0) {
                    /*ret = hikvisonService.initDevice(device);
                    if (ret == 0){*/
                        map.put("code", Global.CODE_SUCCESS);
                   /* } else {
                        map.put("code", Global.CODE_DEVICE_INIT_EXCEPTION);
                    }*/
                }else {
                    map.put("code", Global.CODE_DEVICE_CREATE_EXCEPTION);
                }
            } else { // 设备名称存在，返回code 3801
                map.put("code", Global.CODE_DEVICE_NAME_IS_EXIST);
            }
        } catch (Exception e) { //数据异常，返回code 2003
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[device.api]-[get]-[/api/device/createDevice] CODE_DB_QUERY_EXCEPTION exception:" + e.getMessage());
        }
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/api/device/updateDevice")
    public HashMap<String, Object> updateDevice(@RequestBody Device device) {
        logger.info("[device.api]-[post]-[/api/device/updateDevice] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            // 查询到当前设备
            Device device1 = deviceService.queryDeviceById(device);
            int i = 0,j=0;
            long ret = 0;
            // 当前 没有修改设备名称
            if (device1.getDeviceName().equals(device.getDeviceName())) {
                device.setDeviceModTime(new Date());
                i = deviceService.updateDevice(device);
                if (i !=0 ) {
                    /*ret = hikvisonService.initDevice(device);
                    if (ret == 0) {*/
                        map.put("code", Global.CODE_SUCCESS);
                   /* }else {
                        map.put("code", Global.CODE_DEVICE_INIT_EXCEPTION);
                    }*/
                }else {
                    map.put("code", Global.CODE_DEVICE_UPDATE_EXCEPTION);
                }
            } else {// 当前 修改了设备名称
                Device device2 = new Device();
                device2.setDeviceName(device.getDeviceName());
                // 判断当前修改的设备名称是否已存在，
                Device device3 = deviceService.queryDeviceOne(device2);
                if (device3 == null) { // 当前设备名称不存在，直接进行修改
                    device.setDeviceModTime(new Date());
                    i = deviceService.updateDevice(device);
                    if (i == 0) { // 创建设备失败，返回code 3704
                        map.put("code", Global.CODE_DEVICE_UPDATE_EXCEPTION);
                    } else { // 创建设备成功，返回code 200
                        map.put("code", Global.CODE_SUCCESS);
                    }
                }else{ // 设备名称存在，返回code 3701
                    map.put("code", Global.CODE_DEVICE_NAME_IS_EXIST);
                }
            }
        } catch (Exception e) { //数据异常，返回code 2003
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[device.api]-[get]-[/api/device/updateDevice] CODE_DB_QUERY_EXCEPTION exception:" + e.getMessage());
        }
        return map;
    }
    @ResponseBody
    @RequestMapping(value = "/api/device/updateAbility")
    public HashMap<String, Object> updateAbility(@RequestBody Device device) {
        logger.info("[device.api]-[post]-[/api/device/updateAbility] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        int result = deviceService.updateDevice(device);
        if (result == 0) { // 创建设备失败，返回code 3704
            map.put("code", Global.CODE_DEVICE_UPDATE_EXCEPTION);
        } else { // 创建设备成功，返回code 200
            map.put("code", Global.CODE_SUCCESS);
        }
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/api/device/queryDeviceById")
    public HashMap<String, Object> queryDeviceById(@RequestBody Device device) {
        logger.info("[device.api]-[get]-[/api/device/queryDeviceById] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            Device device1 = deviceService.queryDeviceById(device);
            List<Device> deviceList = new ArrayList<>();
            deviceList.add(device1);
            map.put("code", Global.CODE_SUCCESS); // 数据正常，返回code 200
            map.put("results", deviceList);
        } catch (Exception e) { //数据异常，返回code 2003
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[device.api]-[get]-[/api/device/queryDeviceById] method have been called.");
        }
        return map;
    }


    @ResponseBody
    @RequestMapping(value = "/api/device/removeDeviceById")
    public Map<String, Object> removeDeviceById(@RequestBody Device device) {
        logger.info("[device.api]-[post]-[/api/device/removeDeviceById] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            int i = deviceService.removeDevice(device);
            if (i == 0) { // 删除异常，返回code 3803
                map.put("code", Global.CODE_DEVICE_REMOVE_EXCEPTION);
            } else { // 删除成功，返回code200
                map.put("code", Global.CODE_SUCCESS);
            }
        } catch (Exception e) { //数据异常，返回code 2003
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[device.api]-[get]-[/api/device/removeDeviceById] CODE_DB_QUERY_EXCEPTION exception:" + e.getMessage());
        }
        return map;
    }

    //查询所有
    @ResponseBody
    @RequestMapping(value = "/api/device/queryAllDevice")
    public Map<String, Object> queryAllDevice(Device device) {
        logger.info("[device.api]-[get]-[/api/device/queryAllDevice] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        List DevList=new ArrayList();
        try{
            List<Device> DeviceList=deviceService.queryAllDeviceCount(device);
            if(DeviceList.size()>0){
                for(Device dev:DeviceList){
                    Map<String,Object> HashMap=new HashMap<>();
                    HashMap.put("deviceId",dev.getDeviceId());
                    HashMap.put("deviceIP",dev.getDeviceip());
                    HashMap.put("deviceName",dev.getDeviceName());
                    HashMap.put("deviceIP",dev.getDeviceMemo());
                    DevList.add(HashMap);
                }
                map.put("results",DevList);
            }
            map.put("code", Global.CODE_SUCCESS);
        }catch (Exception e){
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[device.api]-[get]-[/api/device/queryAllDevice] CODE_DB_QUERY_EXCEPTION exception:" + e.getMessage());
        }
        return map;
    }

    /**
     * 根据DeviceType查询下面的类型设备
     */
    @ResponseBody
    @RequestMapping(value = "/api/device/queryDeviceType")
    public HashMap<String, Object> queryDeviceType(Device device) {
        logger.info("[device.api]-[get]-[/api/device/queryDeviceType] method have been called.");
        HashMap<String, Object> hashMap = new HashMap<>();
        List SelectList=new ArrayList();
        try {
            List<Device> deviceIdList=deviceService.queryDeviceIdNot(device);
            List<Device> mapList=deviceService.queryDeviceType(device);
            if(mapList.size()>0){
                for(Device deviceType:mapList){
                    HashMap<String, Object> HashMapReg = new HashMap<>();
                    HashMapReg.put("text",deviceType.getDeviceName());
                    HashMapReg.put("value",deviceType.getDeviceId());
                    SelectList.add(HashMapReg);
                }

                hashMap.put("results",SelectList);
            }
            hashMap.put("deviceNot", deviceIdList);
            hashMap.put("code", Global.CODE_SUCCESS); // 数据正常，返回code 200
//            hashMap.put("results", mapList);
        }catch (Exception e){
            hashMap.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[device.api]-[get]-[/api/device/queryDeviceType] CODE_DB_QUERY_EXCEPTION exception:" + e.getMessage());
        }
        return hashMap;
    }

    /**
     * 根据DeviceName的Id查询下面的信息
     */
    @ResponseBody
    @RequestMapping(value = "/api/device/queryDeviceId")
    public HashMap<String, Object> queryDeviceId(Device device) {
        logger.info("[device.api]-[get]-[/api/device/queryDeviceId] method have been called.");
        HashMap<String, Object> hashMap = new HashMap<>();
        List<Device> SelectList=new ArrayList();
        try {
            Device deviceResult=deviceService.queryDeviceByIdSQL(device);
            SelectList.add(deviceResult);
            hashMap.put("results", SelectList);
            hashMap.put("code", Global.CODE_SUCCESS); // 数据正常，返回code 200
//            hashMap.put("results", mapList);
        }catch (Exception e){
            hashMap.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[device.api]-[get]-[/api/device/queryDeviceId] CODE_DB_QUERY_EXCEPTION exception:" + e.getMessage());
        }
        return hashMap;
    }
}
