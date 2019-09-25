package com.san.platform.map;

import com.alibaba.dubbo.config.annotation.Reference;
import com.san.platform.BaseController;
import com.san.platform.config.Global;
import com.san.platform.config.PictureUtil;
import com.san.platform.control.Control;
import com.san.platform.control.ControlService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
public class MapController extends BaseController {

    @Reference
    private MapService mapService;

    //存储图片根路径
    @Value("${path.baseUrl}")
    private String baseUrl;
    //存储图片人像路径
    @Value("${path.map}")
    private String map;
    //存储图片人像路径
    @Value("${path.access}")
    private String access;

    @ResponseBody
    @GetMapping(value = "/api/map/queryMapPage")
    public HashMap<String, Object> queryMapPage(Map map) {
        logger.info("[map.api]-[get]-[/api/map/queryMapPage] method have been called.");
        HashMap<String, Object> hashMap = new HashMap<>();
        try {
            hashMap = mapService.queryMapPage(map);
            // map加入code信息 200
            hashMap.put("code", Global.CODE_SUCCESS); // 数据正常，返回code 200
        } catch (Exception e) {
            hashMap.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[map.api]-[get]-[/api/map/queryMapPage] CODE_DB_QUERY_EXCEPTION exception:" + e.getMessage());
        }

        return hashMap;
    }

    @ResponseBody
    @RequestMapping(value = "/api/map/createMap", method = RequestMethod.POST)
    public HashMap<String, Object> createMap(HttpServletRequest request, @RequestParam(value = "file", required = false) MultipartFile file, Map map) {
        logger.info("[map.api]-[post]-[/api/map/createMap] method have been called.");
        HashMap<String, Object> hashMap = new HashMap<>();
        try {
            if (file != null && !file.isEmpty()) {
                String str = this.fileUpload(file, request);
                map.setMapImage(str);
            } else {
                map.setMapImage("/static/img/tmp/movingTrack.png");
            }
            int i = mapService.createMap(map);
            if (i == 0) {
                hashMap.put("code", Global.CODE_MAP_CREATE_EXCEPTION);
            } else {
                hashMap.put("code", Global.CODE_SUCCESS);
            }
        } catch (Exception e) {
            hashMap.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[map.api]-[post]-[/api/map/createMap] CODE_DB_QUERY_EXCEPTION exception:" + e.getMessage());
        }
        return hashMap;
    }

    @ResponseBody
    @RequestMapping(value = "/api/map/updateMap")
    public HashMap<String, Object> updateMap(@RequestBody Map map) {
        logger.info("[map.api]-[post]-[/api/map/updateMap] method have been called.");
        HashMap<String, Object> hashMap = new HashMap<>();
        try {
            int i = mapService.updateMap(map);
            if (i == 0) {
                hashMap.put("code", Global.CODE_MAP_UPDATE_EXCEPITON);
            } else {
                hashMap.put("code", Global.CODE_SUCCESS);
            }
        } catch (Exception e) {
            hashMap.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[map.api]-[post]-[/api/map/updateMap] CODE_DB_QUERY_EXCEPTION exception:" + e.getMessage());
        }
        return hashMap;
    }

    @ResponseBody
    @RequestMapping(value = "/api/map/queryMapById")
    public HashMap<String, Object> queryMapById(@RequestBody Map map) {
        logger.info("[map.api]-[post]-[/api/map/queryMapById] method have been called.");
        HashMap<String, Object> hashMap = new HashMap<>();
        try {
            Map map1 = mapService.queryMapById(map);
            List<Map> mapList = new ArrayList<>();
            mapList.add(map1);
            hashMap.put("code", Global.CODE_SUCCESS); // 数据正常，返回code 200
            hashMap.put("results", mapList);
        } catch (Exception e) {
            hashMap.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[map.api]-[post]-[/api/map/queryMapById] CODE_DB_QUERY_EXCEPTION exception:" + e.getMessage());
        }
        return hashMap;
    }

    @ResponseBody
    @RequestMapping(value = "/api/map/removeMapById")
    public HashMap<String, Object> removeMapById(@RequestBody Map map) {
        logger.info("[map.api]-[post]-[/api/map/removeMapById] method have been called.");
        HashMap<String, Object> hashMap = new HashMap<>();
        try {
            int i = mapService.removeDevice(map);
            if (i == 0) {
                hashMap.put("code", Global.CODE_MAP_REMOVE_EXCEPTION);
            } else {
                hashMap.put("code", Global.CODE_SUCCESS);
            }
        } catch (Exception e) {
            hashMap.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[map.api]-[post]-[/api/map/removeMapById] CODE_DB_QUERY_EXCEPTION exception:" + e.getMessage());
        }
        return hashMap;
    }

    /**
     * 图像上传
     *
     * @param file
     * @param request
     * @return
     */
    public String fileUpload(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request) {
        if (file.isEmpty()) {
            System.out.println("文件为空");
        }
        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        File directory = new File("");// 参数为空
        //存放路径
        String realPath = baseUrl + map;
        File dest = new File(realPath);
        //判断文件目录是否存在
        if (!dest.isDirectory() && !dest.exists()) {
            dest.mkdirs();
        }
        try {
            //定义新的文件名称
            fileName = UUID.randomUUID() + suffixName; // 新文件名
            File per = new File(realPath + fileName);
            //上传图像文件
            file.transferTo(per);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String filename = access + fileName;
        return filename;
    }

    /**
     * 查询map
     */
    @ResponseBody
    @RequestMapping(value = "/api/map/SelectMap")
    public HashMap<String, Object> SelectMap(Map map) {
        logger.info("[map.api]-[get]-[/api/map/SelectMap] method have been called.");
        HashMap<String, Object> hashMap = new HashMap<>();
        List SelectList=new ArrayList();
        try {
            List<Map> mapList=mapService.queryAllMap(map);
            if(mapList.size()>0){
                for(Map mapReg:mapList){
                    HashMap<String, Object> HashMapReg = new HashMap<>();
                    HashMapReg.put("text",mapReg.getMapName());
                    HashMapReg.put("value",mapReg.getMapId());
                    SelectList.add(HashMapReg);
                }

                hashMap.put("results",SelectList);
            }
            hashMap.put("code", Global.CODE_SUCCESS); // 数据正常，返回code 200
//            hashMap.put("results", mapList);
        }catch (Exception e){
            hashMap.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[map.api]-[get]-[/api/map/SelectMap] CODE_DB_QUERY_EXCEPTION exception:" + e.getMessage());
        }
        return hashMap;
    }

    // 根据Mapid查询
    @ResponseBody
    @RequestMapping(value = "/api/map/queryMapSelectById")
    public HashMap<String, Object> queryMapSelectById(Map map) {
        logger.info("[map.api]-[get]-[/api/map/queryMapSelectById] method have been called.");
        HashMap<String, Object> hashMap = new HashMap<>();
        try {
            // Map表的查询
            List<Map> MapList= mapService.queryMapBySelectId(map);
            String MapPath="\\img-map\\";
            //存放路径
            String realPath = baseUrl + MapPath;
            hashMap.put("code", Global.CODE_SUCCESS); // 数据正常，返回code 200
            hashMap.put("results", MapList);
            hashMap.put("realPath", realPath);
        } catch (Exception e) {
            hashMap.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[map.api]-[get]-[/api/map/queryMapSelectById] CODE_DB_QUERY_EXCEPTION exception:" + e.getMessage());
        }
        return hashMap;
    }
}
