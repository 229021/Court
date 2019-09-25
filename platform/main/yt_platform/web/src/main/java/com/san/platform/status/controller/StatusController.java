package com.san.platform.status.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.san.platform.BaseController;
import com.san.platform.Exception.CustomException;
import com.san.platform.config.Global;
import com.san.platform.config.OSUtil;
import com.san.platform.setting.SystemStateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class StatusController extends BaseController {

    @Reference
    private SystemStateService systemStateService;
    //获得系统运行状态
    @GetMapping("/api/DeviceStatus/queryStatus")
    public HashMap<String, Object> queryStatus() {
        logger.info("[status.api]-[get]-[/api/DeviceStatus/getStatus] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        if (!OSUtil.getOSName().toLowerCase().contains("linux")) {
            map.put("code", Global.CODE_STATUS_OS_IS_WRONG);
        } else {
            //获取cpu状态
            try {
                String cpuPerc = systemStateService.queryCpuPerc();
                map.put("cpuPerc", cpuPerc);
            } catch (CustomException e) {
                logger.warn("[status.api]-[get]-[/api/DeviceStatus/getStatus] CODE = "+Global.CODE_DB_QUERY_EXCEPTION +",exception message:" + e.getMessage());
                map.put("code", e.getCode());
                return map;
            }

            //获取内存状态
            try {
                int[] physicalMemory = systemStateService.queryPhysicalMemory();
                map.put("physicalMemory", physicalMemory);
            } catch (CustomException e) {
                logger.warn("[status.api]-[get]-[/api/DeviceStatus/getStatus] CODE = "+Global.CODE_DB_QUERY_EXCEPTION +",exception message:" + e.getMessage());
                map.put("code", e.getCode());
                return map;
            }
            //获取硬盘状态
            try {
                List<String[]> fileSystemInfo = systemStateService.queryFileSystemInfo();
                map.put("fileSystemInfo", fileSystemInfo);
            } catch (CustomException e) {
                logger.warn("[status.api]-[get]-[/api/DeviceStatus/getStatus] CODE = "+Global.CODE_DB_QUERY_EXCEPTION +",exception message:" + e.getMessage());
                map.put("code",e.getCode());
                return map;
            }

            //获取系统运行时间
            try {
                String sysRuntime = systemStateService.querySysRuntime();
                map.put("sysRuntime", sysRuntime);
            } catch (CustomException e) {
                logger.warn("[status.api]-[get]-[/api/DeviceStatus/getStatus] CODE = "+Global.CODE_DB_QUERY_EXCEPTION +",exception message:" + e.getMessage());
                map.put("code", e.getCode());
                System.out.println(e.getMessage());
                return map;
            }
            map.put("code", Global.CODE_SUCCESS);
        }
        return map;
    }
}