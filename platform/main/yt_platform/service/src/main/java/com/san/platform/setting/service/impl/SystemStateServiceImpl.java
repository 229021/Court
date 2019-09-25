package com.san.platform.setting.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.san.platform.Exception.CustomException;
import com.san.platform.config.Global;
import com.san.platform.setting.SystemStateService;
import com.san.platform.setting.service.util.SystemInfoHandle;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;

@Service
public class SystemStateServiceImpl implements SystemStateService {


    //查看cpu使用量
    @Override
    public String queryCpuPerc() throws CustomException {
        HashMap<String, Object> map = new HashMap<>();
        float cpuInfo = 0;
        try {
            cpuInfo = SystemInfoHandle.getCpuInfo();
            map.put("cpuInfo", cpuInfo);
        } catch (IOException e) {
            throw new CustomException(Global.CODE_STATUS_CPU_EXCEPTION,e.getMessage());
        } catch (InterruptedException e) {
            throw new CustomException(Global.CODE_STATUS_CPU_EXCEPTION + e.getMessage());
        }

        NumberFormat numberFormat = null;
        String result;
        try {
            numberFormat = NumberFormat.getInstance();
            numberFormat.setMaximumFractionDigits(2);
            result = numberFormat.format(cpuInfo * 100)+"%";
        } catch (Exception e) {
            throw new CustomException(Global.CODE_STATUS_CPU_EXCEPTION + e.getMessage());
        }
        return result;
    }

    //    物理内存信息
    @Override
    public int[] queryPhysicalMemory() throws CustomException {
        try {
            return SystemInfoHandle.getMemInfo();
        } catch (IOException e) {
            throw new CustomException(Global.CODE_STATUS_MEMORY_EXCEPTION,e.getMessage());
        }
    }

    @Override
    public List<String[]> queryFileSystemInfo() throws CustomException {
        try {
            return  SystemInfoHandle.getDiskInfo();
        } catch (IOException e) {
            throw new CustomException(Global.CODE_STATUS_DISK_EXCEPTION,e.getMessage());
        }
    }

    public String querySysRuntime() throws CustomException {
        try {
            return SystemInfoHandle.getSysRuntime();
        } catch (IOException e) {
            throw new CustomException(Global.CODE_STATUS_SYSRUNTIME_EXCEPTION,e.getMessage());
        }
    }

    @Override
    public String ServerTest() {
        return "SystemState is OK";
    }
}
