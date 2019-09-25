package com.san.platform.setting.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.san.platform.setting.Setting;
import com.san.platform.setting.SettingService;
import com.san.platform.setting.mapper.SettingMapper;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class SettingServiceImpl implements SettingService {

    @Autowired
    SettingMapper settingMapper;

    /**
     * 根据key查询value信息
     * @param setting
     * @return
     */
    @Override
    public Setting queryByKey(Setting setting) {
        return settingMapper.queryByKey(setting);
    }

    /**
     * 添加设置信息
     * @param setting
     * @return
     */
    @Override
    public int createSetting(Setting setting) {
        return settingMapper.insertSelective(setting);
    }

    /**
     * 更新设置信息
     * @param setting
     * @return
     */
    public int updateByKey (Setting setting){return settingMapper.updateByKey(setting);}
}
