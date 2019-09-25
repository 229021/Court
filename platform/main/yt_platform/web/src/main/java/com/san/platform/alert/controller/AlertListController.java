package com.san.platform.alert.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.san.platform.BaseController;
import com.san.platform.alert.*;
import com.san.platform.config.*;
import com.san.platform.display.DisplayService;
import com.san.platform.hikface.HikfaceService;
import com.san.platform.personnel.Personnel;
import com.san.platform.personnel.PersonnelService;
import com.san.platform.visitor.VisitorService;
import com.san.platform.websockt.MyWebSocket;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.zip.ZipOutputStream;

import static com.san.platform.personnel.controller.PersonnelController.readInputStream;

/**
 * @Author: Zhouzhongqing
 * @Date: 2019/4/26 12:10
 * @Description: 告警列表controller
 */
@RestController
public class AlertListController extends BaseController {
    @Reference
    private HikfaceService hikfaceService;

    @Reference
    private MyWebSocket myWebSocket;

    @Reference
    private VisitorService visitorService;

    @Reference
    private AlertService alertService;

    @Reference
    private DisplayService displayService;

    @Reference
    private AlertListService alertListService;

    @Reference
    private PersonnelService personnelService;
    //存储图片人像路径
    @Value("${path.access}")
    private String access;
    @Value("${localhostip.path}")
    private String localPath;
    @Value("${http.port}")
    private String serverPort;
    @Value("${export.path}")
    private String exportPath;
    @Value("${http.path}")
    private String httppath;

    private String picFilePath = "D:/pic/";



    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/5/7 16:13
     * @Description: 查询告警列表全部
     */

    @GetMapping(value = "/api/alert/queryAlertListPage")
    public HashMap<String, Object> queryAlertLists(AlertList alertList) {
        logger.info("[alertList.api]-[get]-[/api/alert/queryAlertListPage] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            map = alertListService.queryAlertListPage(alertList);
            map.put("code", Global.CODE_SUCCESS);
        } catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[alertList.api]-[get]-[/api/alert/queryAlertListPage] CODE_DB_QUERY_EXCEPTION exception,sel_all:" + e.getMessage());
        }
        return map;
    }

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/4/26 12:18
     * @Description: 删除告警列表（多个）
     */

    @PostMapping(value = "/api/alert/deleteAlertList")
    public HashMap<String, Object> deleteAlertList(@RequestBody List<Integer> alertId) {
        logger.info("[alertList.api]-[post]-[/api/alert/deleteAlertList] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            for (Integer id : alertId) {
                alertListService.removeAlertList(id);
            }
            map.put("code", Global.CODE_SUCCESS);
        } catch (Exception e) {
            map.put("code", Global.CODE_DATA_DELETE_EXCEPION);
            logger.info("[alertList.api]-[post]-[/api/alert/deleteAlertList] CODE_DATA_DELETE_EXCEPION exception,sel_all:" + e.getMessage());
        }
        return map;
    }

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/4/26 12:34
     * @Description: 删除告警列表 （单个）
     */

    @PostMapping(value = "/api/alert/deleteByAlertListId")
    public HashMap<String, Object> deleteByAlertListId(@RequestBody AlertList alertList) {
        logger.info("[alertList.api]-[post]-[/api/alert/deleteByAlertListId] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            // 调用删除接口
            alertListService.removeByAlertListId(alertList);
            map.put("code", Global.CODE_SUCCESS);
        } catch (Exception e) {
            map.put("code", Global.CODE_DATA_DELETE_EXCEPION);
            logger.info("[alertList.api]-[post]-[/api/alert/deleteByAlertListId] CODE_DATA_DELETE_EXCEPION exception,sel_all:" + e.getMessage());
        }
        return map;

    }


    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/4/28 13:42
     * @Description: 导出CSV告警列表
     */

    @GetMapping(value = "/api/alert/exportAlertListCSV")
    public void exportAlertListCSV(HttpServletResponse response,
                                   @RequestParam(value = "alertIds[]", required = false) Integer[] alertIds) {
        List<AlertList> alertLists = null;
//        String tempFilePah = "D:/tem" + new Date().getTime() + "/";
        String tempFilePah = exportPath;
        logger.info("[alert.api]-[post]-[/api/alert/exportAlertListCSV] method have been called.");
        if (alertIds != null && alertIds.length > 0) {
            // 根据alertId 查询
            alertLists = alertListService.queryAlertLists(Arrays.asList(alertIds));
        } else {
            // 查询全部告列表
//            AlertList alertList = new AlertList();
//            alertList.setAlert(alert);
            alertLists = alertListService.queryAlertList();
        }
        // csv信息(文件名、标题、对应的数据)
        String fileName = "告警列表" + DateUtil.putDateToTimeStr10(new Date()) + ".csv";
        String sTitle = "’ID,照片,姓名,性别,证件号,告警地点,告警规则,创建时间,修改时间";
        String mapKey = "alertId,perPicture,perName,perSex,perNumber,alertPlace,alertName,alertCreateTime,alertModTime";
        // 存放要保存的数据
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = null;
        for (AlertList al : alertLists) {
            map = new HashMap<>();
            // 获取对应字段数据
            map.put("alertId", al.getAlertId());
            map.put("perPicture", al.getPerPicture());
            map.put("perName", al.getPerName());
            map.put("perSex", al.getPerSex());
            map.put("perNumber", al.getPerNumber());
            map.put("alertPlace", al.getAlertPlace());
            map.put("alertName", al.getAlertName());

            if (al.getAlertCreateTime() != null) {
                map.put("alertCreateTime", DateFormatUtils.format(al.getAlertCreateTime(), "yyyy-MM-dd HH:mm"));
            }
            if (al.getAlertModTime() != null) {
                map.put("alertModTime", DateFormatUtils.format(al.getAlertModTime(), "yyyy-MM-dd HH:mm"));
            }
            list.add(map);
        }

        //保存成为csv文件
        CSVUtil.saveCsv(list, sTitle, mapKey, fileName, tempFilePah);

        String zipName = "alertList_" + DateUtil.putDateToTimeStr10(new Date()) + ".zip";
        // 读取字符编码
        String csvEncoding = "UTF-8";
        // 设置响应
        response.setCharacterEncoding(csvEncoding);
        response.setContentType("APPLICATION/OCTET-STREAM" + csvEncoding);
        response.setHeader("Content-Disposition", "attachment; filename=" + zipName);
        List<String> picturePathList = new ArrayList<>();
        File file0 = null;
        try {
//            把要下载的文件放一个文件夹
            ZipOutputStream out = new ZipOutputStream(response.getOutputStream());

            for (AlertList all : alertLists) {
                String alertListPicture = all.getPerPicture();
                picturePathList.add("http://" + localPath + ":" + serverPort + alertListPicture);
            }
            if (picturePathList != null && picturePathList.size() > 0) {
                importPicture(picturePathList, file0, tempFilePah);
            }
            response.flushBuffer();
//            打包文件夹
            ZipUtils.doCompress(tempFilePah, out);
            out.close();
            boolean b = deleteFolder(exportPath);
            System.out.println(b + "删除文件夹ok");
        } catch (Exception e) {
            logger.warn("[alert.api]-[post]-[/api/alert/exportAlertListCSV]  " +
                    "CODE = " + Global.CODE_VISITOR_EXPORT_DATA_EXCEPTION + "," +
                    "exception message:" + e.getMessage());
        }
    }
    /**
     * 删除文件
     */
    public boolean deleteFolder(String url) {
        File file = new File(url);
        if (!file.exists()) {
            return false;
        }
        if (file.isFile()) {
            file.delete();
            return true;
        } else {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                String root = files[i].getAbsolutePath();//得到子文件或文件夹的绝对路径
                //System.out.println(root);
                deleteFolder(root);
            }
            file.delete();
            return true;
        }
    }
    /**
     * 封装图片下载流程
     */
    public void importPicture(List<String> picturePathList, File file0, String tempFilePah) throws Exception {
        String[] strings = picturePathList.toArray(new String[0]);
        FileOutputStream outStream = null;
        for (int i = 0; i < strings.length; i++) {
            URL url = new URL(strings[i]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            InputStream inStream = conn.getInputStream();
            byte[] data = readInputStream(inStream);
            file0 = new File(tempFilePah + "\\pic");
            if (!file0.isDirectory() && !file0.exists()) {
                file0.mkdirs();
            }
            String str = strings[i];
            str = str.replace(httppath + localPath + ":" + serverPort + access,"");
            outStream = new FileOutputStream(file0 + "\\" + str);
            outStream.write(data);
        }
        outStream.close();

    }

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/5/6 10:25
     * @Description: 保存到不同类型的人员库
     */

    @PostMapping(value = "/api/alert/updateType")
    public Map<String, Object> updateType(@RequestBody Personnel personnel) {
        logger.info("[alert.api]-[post]-[/api/alert/updateType] method have been called.");
        Map<String, Object> map = new HashMap<>();
        try {
            // 按id查找告警列表
            List<AlertList> list = alertListService.queryAlertListById(personnel.getPerIds());
            // 遍历结果集
            Personnel personnel1 = new Personnel();
            Personnel personnel2 = new Personnel();

            for (AlertList alertList : list) {
                // 将告警列表内容赋值给人像库
                personnel1.setPerPicture(alertList.getPerPicture());
                personnel1.setPerName(alertList.getPerName());
                personnel1.setPerSex(alertList.getPerSex());
                personnel1.setPerNumber(alertList.getPerNumber());
                personnel1.setPerType(personnel.getPerType());
                String uuid = UUID.randomUUID().toString();
                personnel1.setPerUUID(uuid);
                personnel1.setPerStatus(0);
                personnel1.setCreateDate(new Date());
                // 判断要保存的人 人像库是否存在
                // 获取身份证号
                personnel2.setPerNumber(alertList.getPerNumber());
                // 用身份证号查询人像库
                Personnel personnel3 = personnelService.queryPersonnelByNumber(personnel2);
                // 判断人像库是否存在
                if (personnel3 != null && !"".equals(personnel3)) {
                    // 有就修改
                    personnelService.updatePersonnel(personnel1);
                } else {
                    // 没有新建
                    personnelService.createPersonnel(personnel1);
                }
                map.put("code", Global.CODE_SUCCESS);
            }

        } catch (Exception e) {
            map.put("code", Global.CODE_DATA_INSERT_EXCEPION);
            logger.info("[alert.api]-[post]-[/api/alert/updateType] CODE_DATA_INSERT_EXCEPION exception,sel_all:" + e.getMessage());
        }
        return map;
    }

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/5/24 19:46
     * @Description: 告警列表信息
     */

//    @RequestMapping(value = "/api/query/toDay")
//    public HashMap<String, Object> alarmList(@RequestBody Visitor visitor) {
//        HashMap<String, Object> map = new HashMap<>();
//        try {
//            // 接收传过来的数据
//            Visitor visitor1 = new Visitor();
////            visitor1 = alertListService.passToday(visitor);
//            // 判断传过来的对象是否为空
//            if (visitor1 != null && !"".equals(visitor1)) {
//                // 不为空 用身份证号查人像库
//                Personnel personnel = new Personnel();
//                personnel = alertListService.queryPersonnelByNumber(visitor1.getVisitCardNumber());
//                // 判断查询的对象是否为空
//                if (personnel != null && !"".equals(personnel)) {
//                    // 如果查到了看他的类型和区域  是否符合告警规则
//                    Integer integer = personnel.getPerType();  // 这个人的类型
//                    // 查询所有告警规则 list
//                    Alert alert = new Alert();
//                    List<Alert> list = alertListService.selectAlertAll(alert);
//                    // 遍历所有告警规则
//                    for (Alert alert1 : list) {
//                        // 获取场所
//                        String[] place = alert1.getAlertPlacestrr().split(",");
//                        // 遍历场所
//                        for (String s : place) {
//                            // 根据球机IP 查询出所在的区域
//                            Integer id = alertListService.selectRegionById("球机IP");
////                            Integer.valueOf(s).intValue();
//                            // 判断所在区域是否和规则区域匹配
//                            if (id == Integer.parseInt(s)) {
//                                // 获取黑名单
//                                String[] blackList = alert1.getBlackListstrr().split(",");
//                                // 遍历黑名单
//                                for (String s1 : blackList) {
//                                    // 判断所在的库 是否为黑名单
//                                    if (integer == Integer.parseInt(s1)) {
//                                        // 入库
//                                        AlertList alertList1 = new AlertList();
//                                        alertList1.setPerName(personnel.getPerName());
//                                        alertList1.setPerSex(personnel.getPerSex());
//                                        alertList1.setPerNumber(personnel.getPerNumber());
//                                        alertList1.setPerPicture(personnel.getPerPicture());
//                                        alertList1.setPerType(personnel.getPerType());
//                                        alertList1.setAlertPlace(id.toString());
//                                        alertList1.setAlertName(alert1.getAlertName());
//                                        alertList1.setPerUUID(personnel.getPerUUID());
//                                        alertList1.setAlertCreateTime(new Date());
//                                        alertListService.createAlertList(alertList1);
//                                        // 弹窗
//                                        // 将java对象转换成字符串
//                                        String alertList = JSONObject.toJSONString(alertList1);
//                                        myWebSocket.sendInfo(alertList);
//                                    }
//                                }
//                            }
////                            break;
//                        }
//                    }
//                }
//            } else {   /*----- 人像库没有查到 -----*/
//                // 用身份证号查今日来访
//                Visitor visitor2 = new Visitor();
//                visitor1 = visitorService.queryVisitorByIDNoToday(visitor1.getVisitCardNumber());
//                // 如果查到了 看他所在的区域  告警规则
//                if (visitor1 != null && !"".equals(visitor1)) {
//                    // 黑名单 弹窗 入库
//                    Alert alert = new Alert();
//                    // 查询所有告警规则
//                    List<Alert> list = alertListService.selectAlertAll(alert);
//                    // 遍历所有告警规则
//                    for (Alert alert1 : list) {
//                        // 获取场所
//                        String[] place = alert1.getAlertPlacestrr().split(",");
//                        // 遍历场所
//                        for (String s : place) {
//                            // 根据查询出球机IP 所在的区域
//                            Integer id = alertListService.selectRegionById("球机IP");
//                            // 判断所在区域是否和规则区域匹配
//                            if (id == Integer.parseInt(s)) {
//                                // 获取黑名单 查询今日访客库是否在黑名单下
//                                Integer integer = -100;
//                                String[] blackList = alert1.getBlackListstrr().split(",");
//                                // 遍历黑名单
//                                for (String s1 : blackList) {
//                                    if (integer == Integer.parseInt(s1)) {
//                                        // 入库
//                                        AlertList alertList1 = new AlertList();
//                                        alertList1.setAlertName(visitor1.getVisitName());
//                                        alertList1.setPerSex(visitor1.getVisitSex());
//                                        alertList1.setPerNumber(visitor1.getVisitCardNumber());
//                                        alertList1.setPerPicture(visitor1.getVisitPicture());
//                                        alertList1.setAlertPlace(id.toString());
//                                        alertList1.setAlertName(alert1.getAlertName());
//                                        alertList1.setAlertCreateTime(new Date());
//                                        alertListService.createAlertList(alertList1);
//                                        // 弹窗
//                                        // 将java对象转换成字符串
//                                        String alertList = JSONObject.toJSONString(alertList1);
//                                        myWebSocket.sendInfo(alertList);
//                                    }
//                                }
//                            }
//                        }
//                    }
//
//                }
//                // 如果都没匹配到告警
//                map.put("code", Global.CODE_SUCCESS);
//            }
//        } catch (Exception e) {
//            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
//        }
//        return map;
//    }


}











