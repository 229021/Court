package com.san.platform.control;

import com.alibaba.dubbo.config.annotation.Reference;
import com.san.platform.BaseController;
import com.san.platform.config.Global;
import com.san.platform.device.Device;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class ControlController extends BaseController {

    @Reference
    private ControlService controlService;

    // 更新坐标
    @ResponseBody
    @RequestMapping(value = "/api/control/UpdateControl")
    public HashMap<String, Object> UpdateControl(@RequestBody Control control) {
        logger.info("[control.api]-[post]-[/api/control/UpdateControl] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            // 更新操作
            int update=controlService.updateControl(control);
            if(update==0){
                map.put("code", Global.CODE_CONTROL_UPDATE_EXCEPTION);
            }else{
                map.put("code", Global.CODE_SUCCESS);
            }
        }catch (Exception e){
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[control.api]-[post]-[/api/control/UpdateControl] CODE_DB_QUERY_EXCEPTION exception:" + e.getMessage());
        }
        return map;
    }

    // 添加摄像头到地图上
    @ResponseBody
    @RequestMapping(value = "/api/control/createControl")
    public HashMap<String, Object> createControl(@RequestBody Control control) {
        logger.info("[control.api]-[post]-[/api/control/createControl] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            int  insert = controlService.createControl(control);
            if(insert==0){
                map.put("code", Global.CODE_CONTROL_CREATE_EXCEPTION);
            }else{
                map.put("code", Global.CODE_SUCCESS);
            }
        }catch (Exception e){
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[control.api]-[post]-[/api/control/createControl] CODE_DB_QUERY_EXCEPTION exception:" + e.getMessage());
        }
        return map;
    }

    // 查询所有
    @ResponseBody
        @RequestMapping(value = "/api/control/queryControl")
    public HashMap<String, Object> queryControl(Control control) {
        logger.info("[control.api]-[get]-[/api/control/queryControl] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        List mapList=new ArrayList();
        try {
            mapList=controlService.queryControl(control);
            if(mapList.size()!=0){
                map.put("code", Global.CODE_SUCCESS);
            }
            map.put("results",mapList);
        }catch (Exception e){
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[control.api]-[get]-[/api/control/queryControl] CODE_DB_QUERY_EXCEPTION exception:" + e.getMessage());
        }
        return map;
    }

    // 查询数据库不带conMapId的用于前台遍历循环隐藏图片
    @ResponseBody
    @RequestMapping(value = "/api/control/queryControlNotconMapId")
    public HashMap<String, Object> queryControlNotconMapId(Integer mapId) {
        logger.info("[control.api]-[get]-[/api/control/queryControlNotconMapId] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        List insertList=new ArrayList();
        try {
            List<Control> ControlList=controlService.queryControlNotconMapId(mapId);
            if(ControlList.size()!=0){
                map.put("results",ControlList);
                map.put("code", Global.CODE_SUCCESS);
            }
        }catch (Exception e){
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[control.api]-[get]-[/api/control/queryControlNotconMapId] CODE_DB_QUERY_EXCEPTION exception:" + e.getMessage());
        }
            return map;
    }

    // 查询mapId查询摄像头在哪个图上
    @ResponseBody
    @RequestMapping(value = "/api/control/queryControlMapId")
    public HashMap<String, Object> queryControlMapId(Integer mapId) {
        logger.info("[control.ap    i]-[get]-[/api/control/queryControlMapId] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        List insertList=new ArrayList();
        try {
            // 根据conMapid查询有几个设备
            List<Control> ControlList= controlService.queryControlMapId(mapId);
            map.put("results",ControlList);
            map.put("code", Global.CODE_SUCCESS);
        }catch (Exception e){
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[control.api]-[get]-[/api/control/queryControlMapId] CODE_DB_QUERY_EXCEPTION exception:" + e.getMessage());
        }
        return map;
    }

    // 根据MapId删除此区域
    @ResponseBody
    @RequestMapping(value = "/api/control/removeControlByMapId")
    public HashMap<String, Object> removeControlByMapId(Control control) {
        logger.info("[control.api]-[get]-[/api/control/removeControlByMapId] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
           int i=controlService.removeDeviceconDeviceId(control);
            if( i==0){
                map.put("code", Global.CODE_CONTROL_REMOVE_EXCEPTION);
            }else{
                map.put("code", Global.CODE_SUCCESS);
            }
        }catch (Exception e){
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[control.api]-[get]-[/api/control/removeControlByMapId] CODE_DB_QUERY_EXCEPTION exception:" + e.getMessage());
        }
        return map;
    }
}
