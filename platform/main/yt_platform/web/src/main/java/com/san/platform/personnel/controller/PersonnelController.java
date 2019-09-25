package com.san.platform.personnel.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.san.platform.BaseController;
import com.san.platform.alert.AlertListService;
import com.san.platform.alert.PersonType;
import com.san.platform.config.*;
import com.san.platform.device.Device;
import com.san.platform.device.DeviceService;
import com.san.platform.hikvision.HikvisonService;
import com.san.platform.personnel.Personnel;
import com.san.platform.personnel.PersonnelService;
import com.san.platform.task.Task;
import com.san.platform.task.TaskResult;
import com.san.platform.task.TaskResultService;
import com.san.platform.task.TaskService;
import com.san.platform.websockt.MyWebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * 律师库
 */
@Controller
public class PersonnelController extends BaseController {

    @Reference
    private PersonnelService personnelService;
    @Autowired
    HttpServletRequest request;
    @Reference
    private AlertListService alertListService;
    @Reference
    private MyWebSocket myWebSocket;

    @Reference
    private DeviceService deviceService;

    @Reference
    private HikvisonService hikvisonService;

    @Reference
    private TaskService taskService;

    @Reference
    private TaskResultService taskResultService;

    //存储图片根路径
    @Value("${path.baseUrl}")
    private String baseUrl;
    //存储图片人像路径
    @Value("${path.person}")
    private String person;
    //存储图片人像路径
    @Value("${path.access}")
    private String access;
    @Value("${localhostip.path}")
    private String localPath;
    @Value("${http.port}")
    private String serverPort;
    @Value("${export.path}")
    private String exportPath;
    @Value("${disk.path}")
    private String diskPath;
    @Value("${http.path}")
    private String httppath;


    /**
     * 律师库查询
     *
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/api/personnel/queryPersonnel")
    public Map<String, Object> queryPersonnel(Personnel personnel) {
        logger.info("[personnel.api]-[get]-[/api/personnel/queryPersonnel] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            personnel.setPerType(-1);
            map = personnelService.queryPersonnel(personnel);
            map.put("code", Global.CODE_SUCCESS);
        } catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[personnel.api]-[get]-[/api/personnel/queryPersonnel] CODE_DB_QUERY_EXCEPTION exception,sel_all:" + e.getMessage());
        }
        return map;
    }

    @ResponseBody
    @GetMapping(value = "/api/personnel/queryPerPage")
    public HashMap<String, Object> queryPerPage(Personnel personnel){
        logger.info("[personnel.api]-[get]-[/api/personnel/queryPerPage] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        map = personnelService.queryPersonnel(personnel);
        map.put("code", Global.CODE_SUCCESS);
        return map;
    }

    /**
     * 用于页面中的重置-----test(不在需求内)
     *
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/api/personnel/queryPersonnelReset")
    public Map<String, Object> queryPersonnelReset(Personnel personnel) {
        logger.info("[personnel.api]-[get]-[/api/personnel/queryPersonnelReset] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            List<Personnel> list = personnelService.queryPersonnelReset(personnel);
            if (list != null && list.size() > 0) {
                map.put("code", Global.CODE_SUCCESS);
                map.put("results", list);
            }
        } catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[personnel.api]-[get]-[/api/personnel/queryPersonnelReset] CODE_DB_QUERY_EXCEPTION exception,sel_all:" + e.getMessage());
        }
        return map;
    }

    /**
     * 闹访人员查询
     *
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/api/trouble/queryTroublePersonnel")
    public Map<String, Object> queryTroublePersonnel(Personnel personnel) {
        logger.info("[personnel.api]-[get]-[/api/personnel/queryTroublePersonnel] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            personnel.setPerType(0);
            map = personnelService.queryTroublePersonnel(personnel);
            map.put("code", Global.CODE_SUCCESS);
        } catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[personnel.api]-[get]-[/api/personnel/queryTroublePersonnel] CODE_DB_QUERY_EXCEPTION exception,sel_all:" + e.getMessage());
        }
        return map;
    }

    /**
     * 失信人员查询
     *
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/api/lowCredit/queryLowCreditPersonnel")
    public Map<String, Object> queryLowCreditPersonnel(Personnel personnel) {
        logger.info("[personnel.api]-[get]-[/api/personnel/queryLowCreditPersonnel] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            personnel.setPerType(1);
            map = personnelService.queryLowCreditPersonnel(personnel);
            map.put("code", Global.CODE_SUCCESS);
        } catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[personnel.api]-[get]-[/api/personnel/queryLowCreditPersonnel] CODE_DB_QUERY_EXCEPTION exception,sel_all:" + e.getMessage());
        }
        return map;
    }

    /**
     * 特殊人员查询
     *
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/api/special/querySpecialPersonnel")
    public Map<String, Object> querySpecialPersonnel(Personnel personnel) {
        logger.info("[personnel.api]-[get]-[/api/personnel/querySpecialPersonnel] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            personnel.setPerType(2);
            map = personnelService.querySpecialPersonnel(personnel);
            map.put("code", Global.CODE_SUCCESS);
        } catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[personnel.api]-[get]-[/api/personnel/querySpecialPersonnel] CODE_DB_QUERY_EXCEPTION exception,sel_all:" + e.getMessage());
        }
        return map;
    }

    /**
     * 内部人员查询
     *
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/api/staff/queryStaffPersonnel")
    public Map<String, Object> queryStaffPersonnel(Personnel personnel) {
        logger.info("[personnel.api]-[get]-[/api/personnel/querySpecialPersonnel] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            personnel.setPerType(3);
            map = personnelService.queryStaffPersonnel(personnel);
            map.put("code", Global.CODE_SUCCESS);
        } catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[personnel.api]-[get]-[/api/personnel/queryStaffPersonnel] CODE_DB_QUERY_EXCEPTION exception,sel_all:" + e.getMessage());
        }
        return map;
    }

    /**
     * 自定义人员查询
     *
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/api/customize/queryCustomizePersonnel")
    public Map<String, Object> queryCustomizePersonnel(Personnel personnel) {
        logger.info("[personnel.api]-[get]-[/api/personnel/queryCustomizePersonnel] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
//            personnel.setPerType(4);
            map = personnelService.queryCustomizePersonnel(personnel);
            map.put("code", Global.CODE_SUCCESS);
        } catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[personnel.api]-[get]-[/api/personnel/queryCustomizePersonnel] CODE_DB_QUERY_EXCEPTION exception,sel_all:" + e.getMessage());
        }
        return map;
    }

    /**
     * 律师库修改
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/api/personnel/updatePersonnel", method = RequestMethod.POST)
    public Map<String, Object> updatePersonnel(HttpServletRequest request, @RequestParam(value = "file", required = false) MultipartFile file, Personnel personnel) {
        logger.info("[personnel.api]-[get]-[/api/personnel/updatePersonnel] method have been called.");
        Map<String, Object> map = new HashMap<>();
        try {
            if (file != null) {
                String str = this.fileUpload(file, request);
                //定义
                personnel.setPerPicture(str);
            }
            String basePic = personnel.getBasePicture();
            if(basePic!=null&&!"".equals(basePic)){
                String base64 = basePic.substring(22);
                String uuid = UUID.randomUUID()+"";
                String photo = baseUrl + person + uuid + ".jpg";//8989
                String protoReal = access + uuid + ".jpg";
                personnel.setPerPicture(protoReal);
                boolean b = base64ChangeImage(base64, photo);
                System.out.println(b);
            }
            // 根据证件号查询，如果有同一个证件号，提示该Id已存在
            Personnel personnelJudge = personnelService.queryPersonnelByNumber(personnel);
            Integer id = personnel.getPerId();
            if (personnelJudge != null) {
                if (id.equals(personnelJudge.getPerId())) {
                    personnel.setUpdateDate(new Date());
                    personnel.setCreateDate(new Date());
                    String lawyerCard = personnel.getLawyerCard();
                    String phoneNumber = personnel.getPerPhone();
                    if(lawyerCard==null||lawyerCard.equals("null")){
                        personnel.setLawyerCard("");
                    }
                    if(phoneNumber==null||phoneNumber.equals("null")){
                        personnel.setPerPhone("");
                    }
                    personnelService.updatePersonnel(personnel);

                    map.put("code", Global.CODE_SUCCESS);
                } else {
                    map.put("code", Global.CODE_USER_NOT_ALLOW_REPEAT_EXCEPTION); // 用户不能重复
                }
            } else {
                personnelService.updatePersonnel(personnel);
                map.put("code", Global.CODE_SUCCESS);
            }
            map.put("code", Global.CODE_SUCCESS);
        } catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[personnel.api]-[get]-[/api/personnel/updatePersonnel] CODE_DB_QUERY_EXCEPTION exception,sel_all:" + e.getMessage());
        }
        return map;
    }

    /**
     * 律师库添加
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/api/personnel/createPersonnel", method = RequestMethod.POST)
    public Map<String, Object> createPersonnel(HttpServletRequest request,
                                               @RequestParam(value = "file", required = false) MultipartFile file,
                                               Personnel personnel) {
        logger.info("[personnel.api]-[get]-[/api/personnel/createPersonnel] method have been called.");
        Map<String, Object> map = new HashMap<>();
        try {
            String str = null;
            // 判断是否上传了照片
            if (file != null && !file.isEmpty()) {
                str = this.fileUpload(file, request);
                personnel.setPerPicture(str);
            } else {
                // 设置默认图片
                personnel.setPerPicture("");
            }
            String basePic = personnel.getBasePicture();
            if(basePic!=null&&!"".equals(basePic)){
                String base64 = basePic.substring(22);
                String uuid = UUID.randomUUID()+"";
                String photo = baseUrl + person + uuid + ".jpg";//8989
                String protoReal = access + uuid + ".jpg";
                personnel.setPerPicture(protoReal);
                boolean b = base64ChangeImage(base64, photo);
                System.out.println(b);
            }
            // 根据证件号查询，如果有同一个证件号，提示该Id已存在
            Personnel personnelJudge = new Personnel();
            personnelJudge.setPerNumber(personnel.getPerNumber());
            personnelJudge = personnelService.queryPersonnelByNumber(personnelJudge);
            if (personnelJudge != null) {
                map.put("code", Global.CODE_USER_NOT_ALLOW_REPEAT_EXCEPTION); // 用户不能重复
            } else {
                personnel.setUpdateDate(new Date());
                personnel.setCreateDate(new Date());
                personnel.setPerUUID(UUID.randomUUID().toString()); // 外键ID 关联告警表
               /* String picPath = "http://localhost:8070" + str;
                personnel.setPictureUrl(picPath);*/
                personnelService.createPersonnel(personnel);
                // 名单下发任务
                this.sendPer(personnel);
                map.put("code", Global.CODE_SUCCESS);
            }
        } catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[personnel.api]-[get]-[/api/personnel/createPersonnel] CODE_DB_QUERY_EXCEPTION exception,sel_all:" + e.getMessage());
        }
        return map;
    }

    /**
     * 律师库删除
     *
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/api/personnel/removePersonnel")
    public Map<String, Object> removePersonnel(@RequestBody List<Integer> perIds) {
        logger.info("[personnel.api]-[get]-[/api/personnel/removePersonnel] method have been called.");
        Map<String, Object> map = new HashMap<>();
        try {
            for (Integer id : perIds) {
                personnelService.removePersonnelTongYong(id);
            }
            map.put("code", Global.CODE_SUCCESS);
        } catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[personnel.api]-[get]-[/api/personnel/removePersonnel] CODE_DB_QUERY_EXCEPTION exception,sel_all:" + e.getMessage());
        }
        return map;
    }

    /**
     * 编辑详情
     *
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/api/personnel/queryPersonnelById")
    public Map<String, Object> queryPersonnelById(@RequestBody Personnel personnel) {
        logger.info("[personnel.api]-[get]-[/api/personnel/queryPersonnelById] method have been called.");
        Map<String, Object> map = new HashMap<>();
        try {
            personnel = personnelService.queryPersonnelById(personnel);
            map.put("results", personnel);
            map.put("code", Global.CODE_SUCCESS);
        } catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[personnel.api]-[get]-[/api/personnel/queryPersonnelById] CODE_DB_QUERY_EXCEPTION exception,sel_all:" + e.getMessage());
        }
        return map;
    }

    /**
     * 修改人员状态--失信/闹访/特殊/内部/自定义
     *
     * @param
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/api/personnel/updatePersonnelType")
    public Map<String, Object> updatePersonnelType(@RequestBody Personnel personnel) {
        logger.info("[personnel.api]-[get]-[/api/personnel/updatePersonnelType] method have been called.");
        Map<String, Object> map = new HashMap<>();
        List<Integer> perIdsList;
        try {
            perIdsList = personnel.getPerIds();
            for (Integer perId : perIdsList) {
                personnel.setPerId(perId);
                personnel.setUpdateDate(new Date());
                personnel.setCreateDate(new Date());
                personnelService.updatePersonnelType(personnel);
                // 名单下发任务
                this.sendPer(personnel);
            }
            map.put("code", Global.CODE_SUCCESS);
        } catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[personnel.api]-[get]-[/api/personnel/updatePersonnelType] CODE_DB_QUERY_EXCEPTION exception,sel_all:" + e.getMessage());
        }
        return map;
    }

    /**
     * 导出
     *
     * @param response
     * @param perIds
     * @param perType
     */
    @GetMapping(value = "/api/personnel/downLoadPersonnelCSV")
    public void downLoadZipFile(HttpServletResponse response, @RequestParam(value = "perIds[]", required = false)
            Integer[] perIds, @RequestParam(value = "perType") Integer perType) {
        logger.info("[personnel.api]-[post]-[/api/personnel/downLoadPersonnelCSV] method have been called.");
        String tempFilePah = exportPath;
        try {
            List<Personnel> personnelList = null;
            Personnel personnelperType = new Personnel();
            if (perIds != null && perIds.length > 0) {
                if (-1 == perType) {
                    // 律师
                    personnelList = personnelService.queryPersonnelPage(Arrays.asList(perIds), perType);
                } else if (0 == perType) {
                    // 闹访
                    personnelList = personnelService.queryPersonnelPage(Arrays.asList(perIds), perType);
                } else if (1 == perType) {
                    // 失信
                    personnelList = personnelService.queryPersonnelPage(Arrays.asList(perIds), perType);
                } else if (2 == perType) {
                    // 特殊
                    personnelList = personnelService.queryPersonnelPage(Arrays.asList(perIds), perType);
                } else if (3 == perType) {
                    personnelList = personnelService.queryPersonnelPage(Arrays.asList(perIds), perType);
                } else if (4 == perType) {
                    // 内部
                    personnelList = personnelService.queryPersonnelPage(Arrays.asList(perIds), perType);
                }
            } else {
                // perId为null-->全部导出-->区分不同人员-->通过perType区分--注释同上
                if (-1 == perType) {
                    personnelperType.setPerType(perType);
                    personnelList = personnelService.queryPersonnels(personnelperType);
                } else if (0 == perType) {
                    personnelperType.setPerType(perType);
                    personnelList = personnelService.queryPersonnels(personnelperType);
                } else if (1 == perType) {
                    personnelperType.setPerType(perType);
                    personnelList = personnelService.queryPersonnels(personnelperType);
                } else if (2 == perType) {
                    personnelperType.setPerType(perType);
                    personnelList = personnelService.queryPersonnels(personnelperType);
                } else if (3 == perType) {
                    personnelperType.setPerType(perType);
                    personnelList = personnelService.queryPersonnels(personnelperType);
                } else if (4 == perType) {
                    personnelperType.setPerType(perType);
                    personnelList = personnelService.queryPersonnels(personnelperType);
                }
            }
            // csv 信息
            String fileName = "来访记录" + DateUtil.putDateToTimeStr10(new Date()) + ".csv";
            String sTitle = "标识id,*姓名,证件类型,*身份证号,状态码,性别,民族,电话号,地址,图片名,身份类型,备注,律师证号,更新日期,创建日期";
            String mapKey = "perUUID,perName,perIDType,perNumber,perStatus,perSex,perNation,perPhone,perAddress,perPicture,perType,perMemo,lawyerCard,updateDate,createDate";
            // 存放要保存的信息
            List<Map<String, Object>> dataList = new ArrayList<>();
            Map<String, Object> map = null;
            for (Personnel personnel : personnelList) {
                map = new HashMap<>();
                map.put("perUUID", personnel.getPerUUID());
                map.put("perName", personnel.getPerName());
                map.put("perIDType", personnel.getPerIDType());
                map.put("perNumber","\t" + personnel.getPerNumber());
                map.put("perStatus", personnel.getPerStatus());
                map.put("perSex", personnel.getPerSex());
                map.put("perNation", personnel.getPerNation());
                map.put("perPhone", personnel.getPerPhone());
                map.put("perAddress", personnel.getPerAddress());
                String strPath = "";
                if(personnel.getPerPicture()!=null&&!"".equals(personnel.getPerPicture()))
                {
                    strPath = personnel.getPerPicture().substring(7);
                }
                map.put("perPicture",strPath);
                map.put("perType", personnel.getPerType());
                map.put("perMemo", personnel.getPerMemo());
                map.put("lawyerCard", personnel.getLawyerCard());
                map.put("updateDate", personnel.getUpdateDate());
                map.put("createDate", personnel.getCreateDate());
                dataList.add(map);
            }
            // 保存成为csv文件
            CSVUtil.saveCsv(dataList, sTitle, mapKey, fileName, tempFilePah);
            // 将文件信息写入响应流——————————————————————————————————————
            // 打包成zip文件
            String zipName = "personnel_" + DateUtil.putDateToTimeStr10(new Date()) + ".zip";
            response.setContentType("APPLICATION/OCTET-STREAM");
            response.setHeader("Content-Disposition", "attachment; filename=" + zipName);
            // 把要下载的文件放一个文件夹
            ZipOutputStream out = new ZipOutputStream(response.getOutputStream());
          /*  PicturePath picturePath = new PicturePath();
            List<String> picturePathList = personnelService.queryPicPath();*/
            // 按照类型导出图片
            // 律师
            List<String> picturePathList = new ArrayList<>();
            File file0 = null;
            if (perIds != null && perIds.length > 0) {
                if (-1 == perType) {
                    // 律师
                    picturePathList = personnelService.queryPersonnelByTypes(Arrays.asList(perIds), perType);
                    for (int i = 0 ; i < picturePathList.size();i++){
                        if(picturePathList.get(i)!=null&&!"".equals(picturePathList.get(i))) {
                            picturePathList.set(i, httppath + localPath + ":" + serverPort + picturePathList.get(i));
                        } else {
                            picturePathList.remove(i);
                            i--;
                        }
                    }
                    // 封装好了的图片加载方法88
                    if(picturePathList.size()>0){
                        importPicture(picturePathList,file0,tempFilePah);
                    }
                }else if(0 == perType){
                    // 闹访
                    picturePathList = personnelService.queryPersonnelByTypes(Arrays.asList(perIds), perType);
                    for (int i = 0 ; i < picturePathList.size();i++){
                        if(picturePathList.get(i)!=null&&!"".equals(picturePathList.get(i))) {
                            picturePathList.set(i, httppath + localPath + ":" + serverPort + picturePathList.get(i));
                        } else {
                            picturePathList.remove(i);
                            i--;
                        }
                    }
                    // 封装好了的图片加载方法88
                    if(picturePathList.size()>0){
                        importPicture(picturePathList,file0,tempFilePah);
                    }
                } else if(1 == perType) {
                    // 失信
                    picturePathList = personnelService.queryPersonnelByTypes(Arrays.asList(perIds), perType);
                    for (int i = 0 ; i < picturePathList.size();i++){
                        if(picturePathList.get(i)!=null&&!"".equals(picturePathList.get(i))) {
                            picturePathList.set(i, httppath + localPath + ":" + serverPort + picturePathList.get(i));
                        } else {
                            picturePathList.remove(i);
                            i--;
                        }
                    }
                    // 封装好了的图片加载方法88
                    if(picturePathList.size()>0){
                        importPicture(picturePathList,file0,tempFilePah);
                    }
                } else if(2 == perType){
                    // 特殊
                    picturePathList = personnelService.queryPersonnelByTypes(Arrays.asList(perIds), perType);
                    for (int i = 0 ; i < picturePathList.size();i++){
                        if(picturePathList.get(i)!=null&&!"".equals(picturePathList.get(i))) {
                            picturePathList.set(i, httppath + localPath + ":" + serverPort + picturePathList.get(i));
                        } else {
                            picturePathList.remove(i);
                            i--;
                        }
                    }
                    // 封装好了的图片加载方法88
                    if(picturePathList.size()>0){
                        importPicture(picturePathList,file0,tempFilePah);
                    }
                } else if(3 == perType){
                    // 内部
                    picturePathList = personnelService.queryPersonnelByTypes(Arrays.asList(perIds), perType);
                    for (int i = 0 ; i < picturePathList.size();i++){
                        if(picturePathList.get(i)!=null&&!"".equals(picturePathList.get(i))) {
                            picturePathList.set(i, httppath + localPath + ":" + serverPort + picturePathList.get(i));
                        } else {
                            picturePathList.remove(i);
                            i--;
                        }
                    }
                    // 封装好了的图片加载方法88
                    if(picturePathList.size()>0){
                        importPicture(picturePathList,file0,tempFilePah);
                    }
                }
            } else {
                if (-1 == perType) {
                    // 律师
                    picturePathList = personnelService.queryPersonnelByTypeAll(perType);
                    for (int i = 0 ; i < picturePathList.size();i++){
                        if(picturePathList.get(i)!=null&&!"".equals(picturePathList.get(i))) {
                            picturePathList.set(i, httppath + localPath + ":" + serverPort + picturePathList.get(i));
                        } else {
                            picturePathList.remove(i);
                            i--;
                        }
                    }
                    // 封装好了的图片加载方法88
                    if(picturePathList.size()>0){
                        importPicture(picturePathList,file0,tempFilePah);
                    }
                }else if(0 == perType){
                    // 闹访
                    // 律师
                    picturePathList = personnelService.queryPersonnelByTypeAll(perType);
                    for (int i = 0 ; i < picturePathList.size();i++){
                        if(picturePathList.get(i)!=null&&!"".equals(picturePathList.get(i))) {
                            picturePathList.set(i, httppath + localPath + ":" + serverPort + picturePathList.get(i));
                        } else {
                            picturePathList.remove(i);
                            i--;
                        }
                    }
                    // 封装好了的图片加载方法88
                    if(picturePathList.size()>0){
                        importPicture(picturePathList,file0,tempFilePah);
                    }
                } else if(1 == perType){
                    // 律师
                    picturePathList = personnelService.queryPersonnelByTypeAll(perType);
                    for (int i = 0 ; i < picturePathList.size();i++){
                        if(picturePathList.get(i)!=null&&!"".equals(picturePathList.get(i))) {
                            picturePathList.set(i, httppath + localPath + ":" + serverPort + picturePathList.get(i));
                        } else {
                            picturePathList.remove(i);
                            i--;
                        }
                    }
                    // 封装好了的图片加载方法88
                    if(picturePathList.size()>0){
                        importPicture(picturePathList,file0,tempFilePah);
                    }
                } else if(2 == perType){
                    // 律师
                    picturePathList = personnelService.queryPersonnelByTypeAll(perType);
                    for (int i = 0 ; i < picturePathList.size();i++){
                        if(picturePathList.get(i)!=null&&!"".equals(picturePathList.get(i))) {
                            picturePathList.set(i, httppath + localPath + ":" + serverPort + picturePathList.get(i));
                        } else {
                            picturePathList.remove(i);
                            i--;
                        }
                    }
                    // 封装好了的图片加载方法88
                    if(picturePathList.size()>0){
                        importPicture(picturePathList,file0,tempFilePah);
                    }
                } else if(3 == perType){
                    // 律师
                    picturePathList = personnelService.queryPersonnelByTypeAll(perType);
                    for (int i = 0 ; i < picturePathList.size();i++){
                        if(picturePathList.get(i)!=null&&!"".equals(picturePathList.get(i))) {
                            picturePathList.set(i, httppath + localPath + ":" + serverPort + picturePathList.get(i));
                        } else {
                            picturePathList.remove(i);
                            i--;
                        }
                    }
                    // 封装好了的图片加载方法88
                    if(picturePathList.size()>0){
                        importPicture(picturePathList,file0,tempFilePah);
                    }
                }
            }
            response.flushBuffer();
            // 打包文件夹
            ZipUtils.doCompress(tempFilePah, out);//11111
            out.close();
            boolean b = deleteFolder(exportPath);
            System.out.println("134134123412");
            System.out.println(b);
        } catch (Exception e) {
            logger.warn("[personnel.api]-[post]-[/api/personnel/downLoadPersonnelCSV]  " + "CODE = " + Global.CODE_VISITOR_EXPORT_DATA_EXCEPTION + "," + "exception message:" + e.getMessage());
        }
    }
     /* 自定义人像库导出*/
     @GetMapping(value = "/api/personnel/downLoadPersonnelCSVSS")
     public void downLoadZipFileSS(HttpServletResponse response, @RequestParam(value = "perIds[]", required = false)
             Integer[] perIds, @RequestParam(value = "perType") Integer perType) {
         logger.info("[personnel.api]-[post]-[/api/personnel/downLoadPersonnelCSV] method have been called.");
         String tempFilePah = exportPath;
         try {
             List<Personnel> personnelList = null;
             Personnel personnelperType = new Personnel();
             if (perIds != null && perIds.length > 0) {
                     personnelList = personnelService.queryPersonnelPage(Arrays.asList(perIds), perType);
             } else {
                     personnelperType.setPerType(perType);
                     personnelList = personnelService.queryPersonnels(personnelperType);
             }
             // csv 信息
             String fileName = "来访记录" + DateUtil.putDateToTimeStr10(new Date()) + ".csv";
             String sTitle = "标识id,*姓名,证件类型,*身份证号,状态码,性别,民族,电话号,地址,图片名,身份类型,备注,律师证号,更新日期,创建日期";
             String mapKey = "perUUID,perName,perIDType,perNumber,perStatus,perSex,perNation,perPhone,perAddress,perPicture,perType,perMemo,lawyerCard,updateDate,createDate";
             // 存放要保存的信息
             List<Map<String, Object>> dataList = new ArrayList<>();
             Map<String, Object> map = null;
             for (Personnel personnel : personnelList) {
                 map = new HashMap<>();
                 map.put("perUUID", personnel.getPerUUID());
                 map.put("perName", personnel.getPerName());
                 map.put("perIDType", personnel.getPerIDType());
                 map.put("perNumber","\t" + personnel.getPerNumber());
                 map.put("perStatus", personnel.getPerStatus());
                 map.put("perSex", personnel.getPerSex());
                 map.put("perNation", personnel.getPerNation());
                 map.put("perPhone", personnel.getPerPhone());
                 map.put("perAddress", personnel.getPerAddress());
                 String strPath = "";
                 if(personnel.getPerPicture()!=null&&!"".equals(personnel.getPerPicture()))
                 {
                     strPath = personnel.getPerPicture().substring(7);
                 }
                 map.put("perPicture",strPath);
                 map.put("perType", personnel.getPerType());
                 map.put("perMemo", personnel.getPerMemo());
                 map.put("lawyerCard", personnel.getLawyerCard());
                 map.put("updateDate", personnel.getUpdateDate());
                 map.put("createDate", personnel.getCreateDate());
                 dataList.add(map);
             }
             // 保存成为csv文件
             CSVUtil.saveCsv(dataList, sTitle, mapKey, fileName, tempFilePah);
             // 将文件信息写入响应流——————————————————————————————————————
             // 打包成zip文件
             String zipName = "personnel_" + DateUtil.putDateToTimeStr10(new Date()) + ".zip";
             response.setContentType("APPLICATION/OCTET-STREAM");
             response.setHeader("Content-Disposition", "attachment; filename=" + zipName);
             // 把要下载的文件放一个文件夹
             ZipOutputStream out = new ZipOutputStream(response.getOutputStream());
             // 按照类型导出图片
             // 律师
             List<String> picturePathList = new ArrayList<>();
             File file0 = null;
             if (perIds != null && perIds.length > 0) {
                 picturePathList = personnelService.queryPersonnelByTypes(Arrays.asList(perIds), perType);
                 for (int i = 0; i < picturePathList.size(); i++) {
                     if (picturePathList.get(i) != null && !"".equals(picturePathList.get(i))) {
                         picturePathList.set(i, httppath + localPath + ":" + serverPort + picturePathList.get(i));
                     } else {
                         picturePathList.remove(i);
                         i--;
                     }
                 }
                 // 封装好了的图片加载方法88
                 if (picturePathList.size() > 0) {
                     importPicture(picturePathList, file0, tempFilePah);
                 }
             } else {
                 // 律师
                 picturePathList = personnelService.queryPersonnelByTypeAll(perType);
                 for (int i = 0; i < picturePathList.size(); i++) {
                     if (picturePathList.get(i) != null && !"".equals(picturePathList.get(i))) {
                         picturePathList.set(i, httppath + localPath + ":" + serverPort + picturePathList.get(i));
                     } else {
                         picturePathList.remove(i);
                         i--;
                     }
                 }
                 // 封装好了的图片加载方法88
                 if(picturePathList.size()>0){
                     importPicture(picturePathList,file0,tempFilePah);
                 }
             }
             response.flushBuffer();
             // 打包文件夹
             ZipUtils.doCompress(tempFilePah, out);//11111
             out.close();
             boolean b = deleteFolder(exportPath);
             System.out.println("134134123412");
             System.out.println(b);
         } catch (Exception e) {
             logger.warn("[personnel.api]-[post]-[/api/personnel/downLoadPersonnelCSV]  " + "CODE = " + Global.CODE_VISITOR_EXPORT_DATA_EXCEPTION + "," + "exception message:" + e.getMessage());
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
     * excel导入
     * 导入传入的zip文件，zip解压为一个文件夹，文件夹下
     * 有一个pic文件夹，和csv文件，pic中保存的为所有照片
     */
    @PostMapping("/api/personnel/upload")
    @ResponseBody
    public Map<String, Object> addKHexcel(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws IOException {
        Map<String, Object> map = new HashMap<>();
        //********************************************************存储压缩包到服务器********************************
            //设置文件上传到服务器的路径
            String localPath = exportPath;
            String originalFilename = file.getOriginalFilename();
            File file2 = new File(exportPath + originalFilename);
            File file1 = new File(localPath);
            //判断文件目录是否存在
            if (!file1.isDirectory() && !file1.exists()) {
                file1.mkdirs();
            }
            try {
                //保存文件
                file.transferTo(file2);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        //********************************************************解压压缩包********************************
        boolean flag = false;
        ZipFile zip = null;
        try {
            zip = new ZipFile(file2, Charset.forName("gbk"));//防止中文目录，乱码
            String outPath = "";
            for (Enumeration entries = zip.entries(); entries.hasMoreElements(); ) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                String zipEntryName = entry.getName();
                InputStream in = zip.getInputStream(entry);
                //指定解压后的文件夹+当前zip文件的名称
                outPath = (diskPath + zipEntryName).replace("/", File.separator);
                //判断路径是否存在,不存在则创建文件路径
                File file3 = new File(outPath.substring(0, outPath.lastIndexOf(File.separator)));
                if (!file3.exists()) {
                    file3.mkdirs();
                }
                //判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
                if (new File(outPath).isDirectory()) {
                    continue;
                }
                //保存文件路径信息（可利用md5.zip名称的唯一性，来判断是否已经解压）
                System.err.println("当前zip解压之后的路径为：" + outPath);
                OutputStream out = new FileOutputStream(outPath);
                byte[] buf1 = new byte[2048];
                int len;
                while ((len = in.read(buf1)) > 0) {
                    out.write(buf1, 0, len);
                }
                in.close();
                out.close();
            }
            flag = true;
            //必须关闭，要不然这个zip文件一直被占用着，要删删不掉，改名也不可以，移动也不行，整多了，系统还崩了。
            zip.close();
            //**************************************************************解压完成***************************************
            System.out.println("看看" + outPath);
            // 判断是否为csv文件/api/personnel/createPersonnel
            boolean IsTure = outPath.endsWith(".csv");
            if (IsTure) {

            //******************************************************读取指定文件的csv**************************************
            FileInputStream fileInputStream = new FileInputStream(outPath);
            BufferedReader br = null;
            InputStreamReader isr = new InputStreamReader(fileInputStream, "GBK");
            br = new BufferedReader(isr);
            //从第一行开始读取
            String line1 = br.readLine();
            String line2 = null;
            Personnel personnel = new Personnel();
//            boolean flag = true;
            int countSuccess = 0;
            int countFail = 0;
            while ((line2 = br.readLine()) != null && line2.length() > 0) {
                if (!line2.trim().equals("")) {
                    line2 = line2.replaceAll("\"", "");
                    String[] splitLine = line2.split(",");//[1,2,3,4]
                    // 存在顺序问题--一旦csv中文件顺序与我set的顺序不一致 会出现错误--优化方法写成动态
                    personnel.setPerUUID(UUID.randomUUID().toString());
                    personnel.setPerName(splitLine[1]);
                    String perIDType = splitLine[2];
                    if(!perIDType.equals("null")&&!"".equals(perIDType)){
                        personnel.setPerIDType(Integer.parseInt(perIDType));
                    }else{personnel.setPerIDType(1);}
                    String perNumber = splitLine[3];
                    if(perNumber!=null&&!"".equals(perNumber)){
                        perNumber = perNumber.replaceAll("\t","");
                        personnel.setPerNumber(perNumber);
                    }else{personnel.setPerNumber(null);}
//                    personnel.setPerNumber(splitLine[3]);
                    String perStatus = splitLine[4];
                    if(!perStatus.equals("null")&&!"".equals(perStatus)){
                        personnel.setPerStatus(Integer.parseInt(perStatus));
                    }else{personnel.setPerStatus(1);}
                    personnel.setPerSex(Integer.parseInt(splitLine[5]));
                    personnel.setPerNation(splitLine[6]);
                    String perPhone = splitLine[7];
                    if(!perPhone.equals("null")&&!"".equals(perPhone)){
                        personnel.setPerPhone(perPhone);
                    }else{ personnel.setPerPhone("");}
                    personnel.setPerAddress(splitLine[8]);
                    String newPicturePath = splitLine[9];
//                    String fileName = newPicturePath.substring(7);
//                    String substring ="D:/lib-tem/pic/" + fileName;
                    if (newPicturePath != null && !"".equals(newPicturePath)) {
                        String substring = exportPath + "pic/" + newPicturePath;
                        File file3 = new File(substring);

                        if (file3.exists()) {
                            String fileName1 = FileNameUtils.getFileName(newPicturePath);
                            File file4 = new File(baseUrl + person + fileName1);
                            file3.renameTo(file4);
                            personnel.setPerPicture(access + fileName1);
                        } else {
                            newPicturePath = "";
                            personnel.setPerPicture(newPicturePath);
                        }
                    }
                    personnel.setPerType(Integer.parseInt(splitLine[10]));
                    personnel.setPerMemo(splitLine[11]);
                    String lawyerCard = splitLine[12];
                    if(!lawyerCard.equals("null")&&!"".equals(lawyerCard)){
                        personnel.setLawyerCard(lawyerCard);
                    }else{ personnel.setLawyerCard("");}
                    personnel.setUpdateDate(new Date());
                    personnel.setCreateDate(new Date());

                    // 查询数据库判断该名人员是否存在(证件号)
                    Personnel personnelJuage = new Personnel();
                    personnelJuage = personnelService.queryPersonnelByNumber(personnel);
                    if (personnelJuage != null) {
                        // 不让添加
                        countFail += 1;
                    } else {
                        // 可以添加
                        personnelService.importPersonnel(personnel);
                        countSuccess += 1;
                    }
                    map.put("countFail", countFail);
                    map.put("countSuccess", countSuccess);
                }
            }
            if (br != null) {
                try {
                    //关流
                    br.close();
                    fileInputStream.close();
                    line2 = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            boolean b = deleteFolder(exportPath);
            System.out.println("删除是否成功");
            map.put("code", Global.CODE_SUCCESS);
        } else { map.put("code", Global.CODE_DATA_UPDATE_EXCEPION); }
        //***************************************************************************************************************
        } catch (Exception e) {
            map.put("code", Global.CODE_DATA_UPDATE_EXCEPION);
            e.printStackTrace();
            return map;
        }

        System.out.println(flag);
        return map;
//****************************************************************************************************************************
//        Map<String, Object> map = new HashMap<>();
//        List<String> list = new ArrayList<>();
//        BufferedReader br = null;
//        InputStreamReader isr = new InputStreamReader(file.getInputStream(), "GBK");
//        br = new BufferedReader(isr);
//        //定义读取
//        //br = new BufferedReader(file.ge));
//        //从第一行开始读取
//        String line1 = br.readLine();
//        String line2 = null;
//        //读取到最后一行
//        //int len = 0;
//        Personnel personnel = new Personnel();
//        boolean flag = true;
//        int countSuccess = 0;
//        int countFail = 0;
//        try {
//            while ((line2 = br.readLine()) != null && line2.length() > 0) {
//                /*while (((line2 = br.readLine()) != null)){*/
//                //line2 = br.readLine();
//                if (!line2.trim().equals("")) {
//                    /*if(line2.indexOf("\"")==0) line2 = line2.substring(1,line2.length());   //去掉第一个 "
//                    if(line2.lastIndexOf("\"")==(line2.length()-1)) line2 = line2.substring(0,line2.length()-1);*/
//                    line2 = line2.replaceAll("\"", "");
//                    String[] splitLine = line2.split(",");//[1,2,3,4]
//                    // 存在顺序问题--一旦csv中文件顺序与我set的顺序不一致 会出现错误--优化方法写成动态
//                    personnel.setPerUUID(splitLine[0]);
//                    personnel.setPerName(splitLine[1]);
//                    personnel.setPerIDType(Integer.parseInt(splitLine[2]));
//                    personnel.setPerNumber(splitLine[3]);
//                    personnel.setPerStatus(Integer.parseInt(splitLine[4]));
//                    personnel.setPerSex(Integer.parseInt(splitLine[5]));
//                    personnel.setPerNation(splitLine[6]);
//                    personnel.setPerPhone(splitLine[7]);
//                    personnel.setPerAddress(splitLine[8]);
//                    String newPicturePath = splitLine[9];
////                    newPicturePath = newPicturePath.replaceFirst(".","/static/img");
//                    personnel.setPerPicture(newPicturePath);
//                    personnel.setPerType(Integer.parseInt(splitLine[10]));
//                    personnel.setPerMemo(splitLine[11]);
//                    personnel.setLawyerCard(splitLine[12]);
//                    personnel.setUpdateDate(new Date());
//                    personnel.setCreateDate(new Date());
//
//                    // 查询数据库判断该名人员是否存在(证件号)
//                    Personnel personnelJuage = new Personnel();
//                    personnelJuage = personnelService.queryPersonnelByNumber(personnel);
//                    if (personnelJuage != null) {
//                        // 不让添加
//                        countFail += 1;
//                    } else {
//                        // 可以添加
//                        personnelService.importPersonnel(personnel);
//                        countSuccess += 1;
//                    }
//                        map.put("countFail", countFail);
//                        map.put("countSuccess", countSuccess);
//                }
//            }
//            if (br != null) {
//                try {
//                    br.close();
//                    line2 = null;
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            map.put("code", Global.CODE_SUCCESS);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
////        finally {
////
////        }
//        return map;

    }
    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        //创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        //每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        //使用一个输入流从buffer里把数据读取出来
        while ((len = inStream.read(buffer)) != -1) {
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        //关闭输入流
        inStream.close();
        //把outStream里的数据写入内存
        return outStream.toByteArray();
    }

    /**
     * 封装下载流程
     */
    public  void importPicture(List<String> picturePathList,File file0,String tempFilePah) throws Exception{
        String[] strings = picturePathList.toArray(new String[0]);
        FileOutputStream outStream = null;
        for (int i = 0; i < strings.length; i++) {
            URL url = new URL(strings[i]);
//            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            InputStream inStream = conn.getInputStream();
            byte[] data = readInputStream(inStream);
            file0 = new File(tempFilePah  + "\\pic");
            if (!file0.isDirectory() && !file0.exists()) { file0.mkdirs(); }
            String str = strings[i];
            str = str.replace(httppath + localPath + ":" + serverPort + access,"");
            outStream = new FileOutputStream(file0 + "\\" + str);
            outStream.write(data);
            outStream.close();
        }
        outStream.close();

    }

    /**
     * 图像上传
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

        /*String courseFile = null;
        try {
            courseFile = directory.getCanonicalPath();
            courseFile = courseFile.replaceAll("\\\\","//");
            courseFile = courseFile.replaceAll("main//","");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String peojectName = request.getContextPath();//项目名
        String basePath = request.getScheme()+"://" +request.getServerName()+":"+request.getServerPort()+request.getContextPath()  +"/";
        String filePath = courseFile + peojectName + "//static//img//pic//"; // 上传后的路径
      *//*  String filePath = "C://Users//xiaoz//Desktop//platform//yt_platform//static//img//pic//";*//* // 上传后的路径\*/

        ///////update by zsa 2019-06-13
        //人像库存放路径
        String realPath =  baseUrl + person;
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
     * 名单下发-下发律师信息到闸机
     * @param personnel
     */
    public void sendPer (Personnel personnel) {
        logger.info("[personnel.api]-sendPer method have been called.");
        List<Task> tasks = taskService.queryTaskByType(personnel.getPerType());
        if (tasks != null) {
            for (Task task : tasks) {
                // 得到当前设备信息
                Device device = new Device();
                device.setDeviceId(task.getTaskDeviceId());
                device = deviceService.queryDeviceById(device);
                boolean is = true;
                int resultCode = 1;
                if (device == null ) {
                    this.createTaskResult(task.getTaskGuid(),"NULL","NULL",21);
                    logger.info("[personnel.api]-sendPer device is null");
                }else {
                    if (personnel.getPerPicture() == null || personnel.getPerPicture().equals("null") || personnel.getPerPicture().equals("")){
                        resultCode = 20;
                        logger.info("[personnel.api]-sendPer pic is null");
                    }else{
                        String jpg = personnel.getPerPicture().substring(7);
                        File pic = new File(baseUrl + person + jpg);
                        if (pic.exists()) {
                            HashMap resultInfo = hikvisonService.sendPerNew(device.getDeviceip() , personnel.getPerName(),personnel.getPerNumber(),baseUrl + person + jpg);
                            is = (boolean)resultInfo.get("is");
                            resultCode = Integer.parseInt(resultInfo.get("result").toString());
                            logger.info("[personnel.api]-sendPer hk send into....");
                        }else{
                            resultCode = 18;
                            logger.info("[personnel.api]-sendPer pic is not exist");
                        }
                    }
                    // 如果人员信息下发失败，记录当前人员信息
                    if (is == false || resultCode == 0) { // 长连接错误、
                        logger.info("[personnel.api]-sendPer send error is=false or code=0");
                        // 入库下发失败名单信息
                        this.createTaskResult(task.getTaskGuid(),personnel.getPerName(),personnel.getPerNumber(),resultCode);
                    }else if (is == true && resultCode !=1) { // 人像下发失败
                        logger.info("[personnel.api]-sendPer send error code:"+resultCode);
                        // 入库下发失败名单信息
                        this.createTaskResult(task.getTaskGuid(),personnel.getPerName(),personnel.getPerNumber(),resultCode);
                    }else { // 如果人员信息下发成功，计数成功个数
                        logger.info("[personnel.api]-sendPer hk send success,name:"+personnel.getPerName()+",number:"+personnel.getPerNumber());
                    }
                }
            }
        }
    }

    /**
     * 名单下发失败--记录失败信息
     * @param taskGuid 任务guid
     * @param perName 人员名称
     * @param perNumber 人员证件号
     * @param resultCode 错误码
     */
    public void createTaskResult (String taskGuid,String perName, String perNumber, int resultCode) {
        logger.info("[personnel.api]-[createTaskResult] method have been called.");
        TaskResult taskResult = new TaskResult();
        taskResult.setTaskGuid(taskGuid);
        taskResult.setPerName(perName);
        taskResult.setPerNumber(perNumber);
        taskResult.setResultCode(resultCode);
        // hk 错误码转化
        taskResult.setResultInfo(HkErrorInfo.errorInfo(resultCode));
        taskResult.setTaskDate(new Date());
        taskResultService.createResult(taskResult);
        logger.info("[personnel.api]-[createTaskResult] info:"+perName+" and code:"+resultCode);
    }

    /**
     * BASE转图片
     *
     * @param baseStr   base64字符串
     * @param imagePath 生成的图片
     * @return
     */
    public boolean base64ChangeImage(String baseStr, String imagePath) {
        if (baseStr == null) {
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // 解密
            byte[] b = decoder.decodeBuffer(baseStr);
            // 处理数据
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream(imagePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }


//**********************************************************************************************************************************************************************
    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/6/16 12:12
     * @Description: 查询自定义人像库
     */
    @GetMapping("/api/personType/queryCustomize")
    @ResponseBody
    public HashMap<String, Object> queryCustomize() {
        logger.info("[personType.api]-[get]-[/api/personType/queryCustomize] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        List<PersonType> personTypes;
        try {
            Integer typeSign = 1;
            personTypes = personnelService.queryCustomize(typeSign);
            map.put("result", personTypes);
            map.put("code", Global.CODE_SUCCESS);
        } catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[personType.api]-[get]-[/api/personType/queryCustomize] CODE_DB_QUERY_EXCEPTION exception,sel_all:" + e.getMessage());
        }
        return map;
    }
    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/6/16 12:11
     * @Description: 添加自定义人像库
     */

    @PostMapping("/api/personType/createCustomize")
    @ResponseBody
    public HashMap<String, Object> createCustomize(@RequestBody PersonType personType) {
        logger.info("[personType.api]-[post]-[/api/personType/createCustomize] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            // 自定义标识 1
            personType.setTypeSign(1);
            personType.setCreateTime(new Date());
            personnelService.createCustomize(personType);
            map.put("code", Global.CODE_SUCCESS);
        } catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[personType.api]-[post]-[/api/personType/createCustomize] CODE_DB_QUERY_EXCEPTION exception,sel_all:" + e.getMessage());
        }
        return map;
    }

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/6/16 12:10
     * @Description: 删除一个自定义人像库
     */

    @ResponseBody
    @RequestMapping(value = "/api/personType/deleteCustomizeById")
    public Map<String, Object> deleteCustomizeById(@RequestBody PersonType personTypeId) {
        logger.info("[personType.api]-[post]-[/api/personType/deleteCustomize] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            personnelService.deleteCustomizeById(personTypeId);
            map.put("code", Global.CODE_SUCCESS);
        } catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[personType.api]-[post]-[/api/personType/deleteCustomize] CODE_DB_QUERY_EXCEPTION exception,sel_all:" + e.getMessage());
        }
        return map;
    }

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/6/16 12:10
     * @Description: 删除多个自定义人像库
     */

    @ResponseBody
    @RequestMapping(value = "/api/personType/deleteCustomizeByIds")
    public Map<String, Object> deleteCustomizeByIds(@RequestBody List<Integer> personTypeId) {
        logger.info("[personType.api]-[post]-[/api/personType/deleteCustomizeById] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            for (Integer id : personTypeId) {
                personnelService.deleteCustomizeByIds(id);
            }
            map.put("code", Global.CODE_SUCCESS);
        } catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[personType.api]-[post]-[/api/personType/deleteCustomizeById] CODE_DB_QUERY_EXCEPTION exception,sel_all:" + e.getMessage());
        }
        return map;
    }

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/6/16 12:16
     * @Description: 修改自定义人像库
     */
    @ResponseBody
    @RequestMapping(value = "/api/personType/updateCustomize")
    public HashMap<String, Object> updateCustomize(@RequestBody PersonType personType) {
        logger.info("[personType.api]-[post]-[/api/personType/updateOption] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            personType.setModTime(new Date());
            personnelService.updateCustomize(personType);
            map.put("code", Global.CODE_SUCCESS);
        } catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[personType.api]-[post]-[/api/personType/updateCustomize] CODE_DB_QUERY_EXCEPTION exception,sel_all:" + e.getMessage());
        }
        return map;
    }

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/6/16 15:18
     * @Description: 按id查询自定义人像库
     */

    @ResponseBody
    @RequestMapping(value = "/api/personType/queryPersonTypeById")
    public Map<String, Object> queryPersonTypeById(@RequestBody PersonType personType) {
        logger.info("[personType.api]-[get]-[/api/personType/queryPersonTypeById] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        PersonType personType1 = null;
        try {
            personType1 = personnelService.queryPersonTypeById(personType);
            List<PersonType> list = new ArrayList<>();
            list.add(personType1);
            map.put("code", Global.CODE_SUCCESS);
            map.put("results", list);

        } catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[personType.api]-[get]-[/api/personType/queryPersonTypeById] CODE_DB_QUERY_EXCEPTION exception,sel_all:" + e.getMessage());
        }
        return map;
    }

//    @ResponseBody
//    @RequestMapping(value = "/api/personType/selectPersonnelByPerType")
//    public Map<String, Object> queryPersonnelByType(@RequestBody Personnel perType) {
//        logger.info("[personType.api]-[get]-[/api/personType/queryPersonTypeById] method have been called.");
//        HashMap<String, Object> map = new HashMap<>();
//        try {
//            List<Personnel> list1 = personnelService.queryPersonnels(perType);
//            map.put("results",list1);
//            map.put("code",Global.CODE_SUCCESS);
//        } catch (Exception e) {
//            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
//            logger.info("[personType.api]-[get]-[/api/personType/queryPersonTypeById] CODE_DB_QUERY_EXCEPTION exception,sel_all:" + e.getMessage());
//        }
//        return map;
//    }
}