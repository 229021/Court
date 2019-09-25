package com.san.platform.alert.controller;

import com.san.platform.BaseController;
import com.san.platform.alert.Alert;
import com.san.platform.alert.AlertService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.san.platform.alert.PersonType;
import com.san.platform.config.Global;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import java.util.*;

/**
 * @Author: Zhouzhongqing
 * @Date: 2019/4/22 15:19
 * @Description: 告警模块 Controller
 */

@RestController
public class AlertController extends BaseController {

    @Reference
    private AlertService alertService;

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/4/22 19:10
     * @Description: 查询告警（分页）
     */
    @ResponseBody
    @GetMapping(value = "/api/alert/quertAlertPage")
    public HashMap<String, Object> quertAlertPage(Alert alert) {
        logger.info("[alert.api]-[get]-[/api/alert/quertAlertPage] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            map = alertService.queryAllAlert(alert);
            System.out.println(" alert : " + map);
            map.put("code", Global.CODE_SUCCESS);
        } catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[alert.api]-[get]-[/api/option/queryOptionPage] CODE_DB_QUERY_EXCEPTION exception,sel_all:" + e.getMessage());
        }
        return map;
    }

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/4/22 19:45
     * @Description: 查询告警（根据ID）
     */
    @PostMapping(value = "/api/alert/selectByAlertId")
    public HashMap<String, Object> selectByAlertId(@RequestBody Alert alert) {
        System.out.println(alert.getAlertId());
        logger.info("[alert.api]-[get]-[/api    /alert/selectByAlertId] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        Alert alert1 = null;
        try {
            alert1 = alertService.selectByAlertId(alert);
            List<Alert> list = new ArrayList<>();
            list.add(alert1);
            map.put("code", Global.CODE_SUCCESS);
            map.put("results", list);
        } catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[alert.api]-[get]-[/api/alert/selectByAlertId] CODE_DB_QUERY_EXCEPTION exception,sel_all:" + e.getMessage());
        }
        return map;
    }

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/4/22 19:11
     * @Description: 增加告警
     */
    @PostMapping(value = "/api/alert/createAlert")
    public HashMap<String, Object> createAlert(@RequestBody Alert alert) {
        logger.info("[alert.api]-[post]-[/api/alert/createAlert] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            Alert alert1 = new Alert();
            alert1.setAlertName(alert.getAlertName());
            Alert alert2 = alertService.selectByAlertId(alert1);
            if (alert2 != null) {
                map.put("code", Global.CODE_ALERT_NAME_IS_EXIST);
            } else {
                alert.setAlertCreateTime(new Date());
                String[] str = alert.getAlertPlace();
                String[] str1 = alert.getBlackList();
                String[] str2 = alert.getWhiteList();
                String string = StringUtils.join(str, ",");
                String string1 = StringUtils.join(str1, ",");
                String string2 = StringUtils.join(str2, ",");
                alert.setAlertPlacestrr(string);
                alert.setBlackListstrr(string1);
                alert.setWhiteListstrr(string2);
                int i = alertService.createAlert(alert);
                if (i != 0) {
                    map.put("code", Global.CODE_SUCCESS);
                } else {
                    map.put("code", Global.CODE_DATA_INSERT_EXCEPION);
                }
            }
        } catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[alert.api]-[post]-[/api/alert/createAlert] CODE_DATA_INSERT_EXCEPION exception,sel_all:" + e.getMessage());
        }
        return map;
    }

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/4/22 19:29
     * @Description: 删除告警（多个）
     */
    @PostMapping(value = "/api/alert/deleteAlert")
    public HashMap<String, Object> deleteAlert(@RequestBody List<Integer> alertId) {
        logger.info("[alert.api]-[post]-[/api/alert/deleteAlert] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            for (Integer id : alertId) {
                alertService.removeAlert(id);
            }
            map.put("code", Global.CODE_SUCCESS);
        } catch (Exception e) {
            map.put("code", Global.CODE_DATA_DELETE_EXCEPION);
            logger.info("[alert.api]-[post]-[/api/alert/deleteAlert] CODE_DATA_DELETE_EXCEPION exception,sel_all:" + e.getMessage());
        }
        return map;
    }

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/4/22 19:36
     * @Description: 删除告警（单个）
     */
    @PostMapping("/api/alert/deleteByAlertId")
    public HashMap<String, Object> deleteBuAlertId(@RequestBody Alert alertId) {
        logger.info("[alert.api]-[post]-[/api/alert/deleteByAlertId] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            alertService.removeByAlertId(alertId);
            map.put("code", Global.CODE_SUCCESS);
        } catch (Exception e) {
            map.put("code", Global.CODE_DATA_DELETE_EXCEPION);
            logger.info("[alert.api]-[post]-[/api/alert/deleteByAlertId] CODE_DATA_DELETE_EXCEPION exception,sel_all:" + e.getMessage());
        }
        return map;
    }

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/4/22 19:41
     * @Description: 修改告警
     */
    @PostMapping("/api/alert/updateAlert")
    public HashMap<String, Object> updateAlert(@RequestBody Alert alert) {
        logger.info("[alert.api]-[post]-[/api/alert/updateAlert] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            alert.setAlertModTime(new Date());
//            alert.setAlertCreateTime(new Date());
            String[] str = alert.getAlertPlace();
            String[] str1 = alert.getBlackList();
            String[] str2 = alert.getWhiteList();
            String string = StringUtils.join(str,",");
            String string1 = StringUtils.join(str1,",");
            String string2 = StringUtils.join(str2,",");
            alert.setAlertPlacestrr(string);
            alert.setBlackListstrr(string1);
            alert.setWhiteListstrr(string2);
            alertService.updateAlert(alert);
            map.put("code", Global.CODE_SUCCESS);
        } catch (Exception e) {
            map.put("code", Global.CODE_DATA_UPDATE_EXCEPION);
            logger.info("[alert.api]-[post]-[/api/alert/updateAlert] CODE_DATA_UPDATE_EXCEPION exception,sel_all:" + e.getMessage());
        }
        return map;
    }


    @GetMapping("/api/personType/selectAll")
    public HashMap<String, Object> selectAll() {
        logger.info("[alert.api]-[get]-[/api/alert/selectAll] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        List list = new ArrayList();
        try {
            List<PersonType> personTypes = alertService.selectPersonType();
//            if (personTypes.size() > 0) {
                for (PersonType pt : personTypes) {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("text", pt.getTypeName());
                    hashMap.put("value", pt.getTypeId().toString());
                    list.add(hashMap);
                }
                map.put("results", list);
//            }
            map.put("code", Global.CODE_SUCCESS);
        } catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[alert.api]-[get]-[/api/alert/selectAll] CODE_DB_QUERY_EXCEPTION exception:" + e.getMessage());
        }
        return map;
    }

}

