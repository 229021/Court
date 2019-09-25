package com.san.platform.setting;

public interface SettingService {

    /**
     * 根据key查询
     * @param setting
     * @return
     */
    Setting queryByKey (Setting setting);

    /**
     * 添加设置信息
     * @param setting
     * @return
     */
    int createSetting (Setting setting);

    /**
     *
     * @param setting
     * @return
     */
    int updateByKey (Setting setting);
}
