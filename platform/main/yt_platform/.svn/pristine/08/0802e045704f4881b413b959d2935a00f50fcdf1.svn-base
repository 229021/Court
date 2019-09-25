package com.san.platform.setting.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.san.platform.BaseController;
import com.san.platform.config.Global;
import com.san.platform.setting.Setting;
import com.san.platform.setting.SettingService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class SettingController extends BaseController {

    @Reference
    private SettingService settingService;


    @GetMapping(value = "/api/setting/queryByKey")
    public HashMap<String, Object> queryByKey () {
        logger.info("[setting.api]-[get]-[/api/setting/queryByKey] method have been called.");
        HashMap<String, Object> hashMap = new HashMap<>();
        try {
            Setting setting = new Setting();

            setting.setSetKey("todaySave");
            Setting todaySave = settingService.queryByKey(setting);
            if (todaySave !=  null) {
                hashMap.put("todaySave",todaySave.getSetValue());
            } else {
                hashMap.put("todaySave",0);
            }
            setting.setSetKey("historySave");
            Setting shistory = settingService.queryByKey(setting);
            if (shistory != null) {
                hashMap.put("historySave",shistory.getSetValue());
            }else {
                hashMap.put("historySave",0);
            }
            // map加入code信息 200
            hashMap.put("code", Global.CODE_SUCCESS); // 数据正常，返回code 200
        } catch (Exception e) {
            hashMap.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[setting.api]-[get]-[/api/setting/queryByKey] CODE_DB_QUERY_EXCEPTION exception:" + e.getMessage());
        }
        return hashMap;
    }

    @ResponseBody
    @RequestMapping(value = "/api/setting/updateByKey")
    public HashMap<String, Object> updateByKey (@RequestBody HashMap map) {
        logger.info("[setting.api]-[get]-[/api/setting/queryByKey] method have been called.");
        HashMap<String, Object> hashMap = new HashMap<>();
        Setting setting = new Setting();
        setting.setSetKey("todaySave");
        String todaySave = map.get("todaySave").toString();
        setting.setSetValue(todaySave);
        settingService.updateByKey(setting);

        setting.setSetKey("historySave");
        String historySave = map.get("historySave").toString();
        setting.setSetValue(historySave);
        settingService.updateByKey(setting);


        hashMap.put("code", Global.CODE_SUCCESS); // 数据正常，返回code 200
        return hashMap;
    }

}
