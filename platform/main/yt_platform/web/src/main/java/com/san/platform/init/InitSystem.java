package com.san.platform.init;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.san.platform.device.Device;
import com.san.platform.device.DeviceService;
import com.san.platform.hikvision.HikvisonService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.*;

//@Service
//public class InitSystem implements ApplicationRunner {
//
//    @Reference
//    private DeviceService deviceService;
//    @Reference
//    private HikvisonService hikvisonService;
//    @Override
//    public void run(ApplicationArguments applicationArguments) throws Exception {
//
//        //初始化设备信息
////        List<Device> deviceList = deviceService.queryAllDevice();
////            if (deviceList.size()>0) {
////                for (Device device : deviceList) {
////                     hikvisonService.initDevice(device);
////                }
////            }
//        //初始化系统设置信息
//    }
//}
