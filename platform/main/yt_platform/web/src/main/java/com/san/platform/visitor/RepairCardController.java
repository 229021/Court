package com.san.platform.visitor;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.san.platform.BaseController;
import com.san.platform.JSON.CreateFace;
import com.san.platform.JSON.QueryFace;
import com.san.platform.config.Global;
import com.san.platform.config.HkErrorInfo;
import com.san.platform.device.Device;
import com.san.platform.device.DeviceService;
import com.san.platform.hikface.HikfaceService;

import com.san.platform.hikvision.HikvisonService;
import com.san.platform.personnel.Personnel;
import com.san.platform.personnel.PersonnelService;
import com.san.platform.task.Task;
import com.san.platform.task.TaskResult;
import com.san.platform.task.TaskResultService;
import com.san.platform.task.TaskService;
import com.san.platform.websockt.MyWebSocket;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * @Author: Zhouzhongqing
 * @Date: 2019/6/12 15:06
 * @Description: 补卡登记Controller
 */

@RestController
public class RepairCardController extends BaseController {
    @Reference
    private MyWebSocket mywebSocket;
    @Reference
    private VisitorService visitorService;
    @Reference
    private HikfaceService hikfaceService;
    @Reference
    private RepairCardService repairCardService;
    @Reference
    private HikvisonService hikvisonService;
    @Autowired
    private AmqpTemplate rabbitTemplate;
    @Reference
    private TaskService taskService;
    @Reference
    private DeviceService deviceService;
    @Reference
    private TaskResultService taskResultService;
    @Reference
    private PersonnelService personnelService;

    @Value("${path.baseUrl}")
    private String baseUrl;
    //存储图片访客路径
    @Value("${path.visit}")
    private String visit;
    @Value("${http.port}")
    private String port;
    @Value("${localhostip.path}")
    private String localhostip;
    @Value("${path.access}")
    private String access;
    @Value("${http.path}")
    private String httppath;

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/6/11 13:52
     * @Description: 有身份证 补卡登记 名单下发 入脸谱 入今日来访库
     */
    @RequestMapping(value = "/api/repairCard/createRepairCard")
    public HashMap<String, Object> createRepairCard(@RequestBody RepairCard repairCard) {
        logger.info("[repairCard.api]-[post]-[/api/repairCard/createRepairCard] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            // 判断对象是否为空
            if (repairCard != null && !"".equals(repairCard)) {
                Visitor visitor1 = visitorService.queryVisitorByIDNoToday(repairCard.getVisitCardNumber());
                // 判断查询的人员是否为空
                if (visitor1 == null || "".equals(visitor1)) {
                    Visitor visitor = new Visitor();
                    visitor.setVisitName(repairCard.getVisitName());
                    visitor.setVisitSex(repairCard.getVisitSex());
                    visitor.setVisitCardNumber(repairCard.getVisitCardNumber());
                    visitor.setVisitPicture(repairCard.getVisitPicture()); // 人脸照片
                    visitor.setVisitTime(new Date());
                    visitor.setVisitNation(repairCard.getVisitNation());
                    visitor.setVisitCause(repairCard.getVisitCause());
                    visitor.setCardPhoto(repairCard.getCardPhoto());
                    visitor.setVisitAddress(repairCard.getVisitAddress());
                    visitor.setVisitCardType(repairCard.getVisitCardType());
                    ////////////////////////名单下发
                    this.dealTask(visitor);
                    // -------************添加人脸************-------
                    CreateFace createFace = new CreateFace();
                    String faceUrl = httppath + localhostip + ":" + port + repairCard.getVisitPicture();
                    // http://138.65.1.127:18089/image/_byCardNo[_20190704180633_Acs.jpg
                    createFace.setFaceURL(faceUrl);
                    createFace.setFaceLibType("blackFD"); // 必填
                    createFace.setFDID("1"); // 必填
                    createFace.setFPID("");
                    createFace.setName(repairCard.getVisitName()); // 必填
                    if (1 == repairCard.getVisitSex()) {
                        createFace.setGender("male");
                    } else {
                        createFace.setGender("female");
                    }
                    // 处理出生日期
                    String year = repairCard.getVisitCardNumber().substring(6, 10);
                    String month = repairCard.getVisitCardNumber().substring(10, 12);
                    String day = repairCard.getVisitCardNumber().substring(12, 14);
                    String bornTime = year + "-" + month + "-" + day;
                    createFace.setBornTime(bornTime); // 出生日期 必填
                    String substring = repairCard.getVisitCardNumber().substring(0, 6);
                    createFace.setCity(substring);
                    createFace.setCertificateType("ID");
                    createFace.setCertificateNumber(repairCard.getVisitCardNumber());
                    createFace.setCaseInfo("");
                    createFace.setTag("");
                    createFace.setAddress("");
                    createFace.setCustomInfo("");
                    String string = JSONObject.toJSONString(createFace);
                    // 添加之前先查询
                    QueryFace queryFace = new QueryFace();
                    queryFace.setSearchResultPosition(0);
                    queryFace.setMaxResults(100);
                    queryFace.setFaceLibType("blackFD");
                    queryFace.setFDID("1");
                    queryFace.setCertificateNumber(repairCard.getVisitCardNumber());
                    String six = JSONObject.toJSONString(queryFace);
                    String result = hikfaceService.FaceApi(six, 6);
                    JSONObject jsonObject = JSONObject.parseObject(result);
                    // 如果没查到就添加
                    if (jsonObject.getString("totalMatches").equals("0")) {
                        hikfaceService.FaceApi(string, 3);
                    }
                    // 入今日来访库
                    visitorService.createVisitor(visitor);
                }
                map.put("code", Global.CODE_SUCCESS);
            } else {
                map.put("code", Global.CODE_DATA_INSERT_EXCEPION);
            }
        } catch (Exception e) {
            logger.warn("[repairCard.api]-[post]-[/api/repairCard/createRepairCard]  " +
                    "CODE = " + Global.CODE_DATA_INSERT_EXCEPION + "," +
                    "exception message:" + e.getMessage());
            map.put("code", Global.CODE_DATA_INSERT_EXCEPION);
        }
        return map;
    }


    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/6/19 13:48
     * @Description: 没有身份证 补卡登记 名单下发 入脸谱 入今日来访库
     */
//    @RequestMapping(value = "/api/repairCard/createRepairCards")
//    public HashMap<String, Object> createRepairCards(@RequestBody RepairCard repairCard) {
//        logger.info("[repairCard.api]-[post]-[/api/repairCard/createRepairCard] method have been called.");
//        HashMap<String, Object> map = new HashMap<>();
//        try {
//            // 判断身份证号是否为空
//            if (repairCard.getVisitCardNumber() != null && !"".equals(repairCard.getVisitCardNumber())) {
//                // 判断姓名是否为空
//                if (repairCard.getVisitName() != null && !"".equals(repairCard.getVisitName())) {
//                    Visitor visitor1 = visitorService.queryVisitorByIDNoToday(repairCard.getVisitCardNumber());
//                    // 判断查询的人员是否为空
//                    if (visitor1 == null || "".equals(visitor1)) {
//                        Visitor visitor = new Visitor();
//                        visitor.setVisitName(repairCard.getVisitName());
//                        visitor.setVisitSex(repairCard.getVisitSex());
//                        visitor.setVisitCardNumber(repairCard.getVisitCardNumber());
//                        String base64 = repairCard.getVisitPicture().substring(22);
//                        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
//                        String newDate = sf.format(new Date());
//                        String photo = baseUrl + visit + "_byCardNo[" + repairCard.getVisitCardNumber() + "_" + newDate + "_IDInfoCapturePic.jpg";
//                        boolean b = base64ChangeImage(base64, photo);
////                        Thumbnails.of(photo).size(480,640).toFile(photo);
//                        String pic = access + photo.substring(23);
//                        if (b == true) {
//                            visitor.setVisitPicture(pic);
//                        }
//                        visitor.setVisitTime(new Date());
//                        visitor.setVisitNation(repairCard.getVisitNation());
//                        visitor.setVisitCause(repairCard.getVisitCause());
//                        visitor.setVisitCardType(repairCard.getVisitCardType());
//                        visitor.setLawyerCard(repairCard.getLawyerCard()); // 律师证号
//                        // 判断照片是否为空
//                        if (pic == null && "".equals(pic)) {
//                            map.put("code", Global.CODE_FACE_IS_NULL);
//                            return map;
//                        }
//                        ///////////////////////////////////名单下发
//                        this.dealTask(visitor);
//                        // *********************************添加人脸*****************************************
//                        CreateFace createFace = new CreateFace();
//                        String faceUrl = httppath + localhostip + ":" + port + pic;
//                        createFace.setFaceURL(faceUrl);
//                        createFace.setFaceLibType("blackFD"); // 必填
//                        createFace.setFDID("1"); // 必填
//                        createFace.setFPID("");
//                        createFace.setName(repairCard.getVisitName()); // 必填
//                        if (1 == repairCard.getVisitSex()) {
//                            createFace.setGender("male");
//                        } else {
//                            createFace.setGender("female");
//                        }
//                        // 处理出生日期
//                        String year = repairCard.getVisitCardNumber().substring(6, 10);
//                        String month = repairCard.getVisitCardNumber().substring(10, 12);
//                        String day = repairCard.getVisitCardNumber().substring(12, 14);
//                        String bornTime = year + "-" + month + "-" + day;
//                        createFace.setBornTime(bornTime); // 出生日期 必填
//                        String substring = repairCard.getVisitCardNumber().substring(0, 6);
//                        createFace.setCity(substring);
//                        createFace.setCertificateType("ID");
//                        createFace.setCertificateNumber(repairCard.getVisitCardNumber());
//                        createFace.setCaseInfo("");
//                        createFace.setTag("");
//                        createFace.setAddress("");
//                        createFace.setCustomInfo("");
//                        String string = JSONObject.toJSONString(createFace);
//                        // ****************************************查询人脸************************************
//                        QueryFace queryFace = new QueryFace();
//                        queryFace.setSearchResultPosition(0);
//                        queryFace.setMaxResults(100);
//                        queryFace.setFaceLibType("blackFD");
//                        queryFace.setFDID("1");
//                        queryFace.setCertificateNumber(repairCard.getVisitCardNumber());
//                        String six = JSONObject.toJSONString(queryFace);
//                        String s = hikfaceService.FaceApi(six, 6);
//                        JSONObject jsonObject = JSONObject.parseObject(s);
//                        // ****************************************************************************
//                        if (jsonObject.getString("totalMatches").equals("0")) {
//                            String addFaceResult = hikfaceService.FaceApi(string, 3);
//                            JSONObject jsonObject1 = JSONObject.parseObject(addFaceResult);
//                            String subStatusCode = jsonObject1.getString("subStatusCode");
//                            switch (subStatusCode) {
//                                case "ok":
//                                    map.put("code", Global.CODE_SUCCESS);
//                                    break;
//                                case "invalidID":
//                                    map.put("code", Global.CODE_FACE_INVALIDID);
//                                    break;
//                                case "faceDetectFailed":
//                                    map.put("code", Global.CODE_FACE_TO_DETECT_FACE);
//                                    break;
//                            }
//                            int i = (int) map.get("code");
//                            if (i != 200) {
//                                return map;
//                            }
//                        }
//                        // 入今日来访库
//                        visitorService.createVisitor(visitor);
//                        // 入律师库
//                        String lawyerCards = repairCard.getLawyerCard();
//                        if (lawyerCards != null && !"".equals(lawyerCards)) {
//                        Personnel personnel1 = new Personnel();
//                        personnel1.setPerNumber(repairCard.getVisitCardNumber());
//                        Personnel personnel2 = personnelService.queryPersonnelByNumber(personnel1);
//                        if (personnel2 != null) {
//                            map.put("code", Global.CODE_LAWYER_IS_EXIST);
//                            return map;
//                        } else {
//                                Personnel personnel = new Personnel();
//                                personnel.setPerName(repairCard.getVisitName()); // 姓名
//                                personnel.setPerPicture(pic); // 照片
//                                personnel.setPerType(-1); // 人员类型
//                                personnel.setLawyerCard(lawyerCards); // 律师证号
//                                personnel.setPerNumber(repairCard.getVisitCardNumber()); // 身份证号
//                                personnel.setPerSex(repairCard.getVisitSex()); // 性别
//                                personnel.setPerNation(repairCard.getVisitNation()); // 民族
//                                personnel.setPerIDType(repairCard.getVisitCardType()); // 证件类型
//                                personnel.setCreateDate(new Date());
//                                personnel.setPerUUID(UUID.randomUUID().toString());
//                                personnel.setPerStatus(0);
//                                personnelService.createPersonnel(personnel);
//                            }
//                        }
//                        map.put("code", Global.CODE_SUCCESS);
//                    }else {
//                        map.put("code", Global.CODE_DATA_INSERT_EXCEPION);
//                        return map;
//                    }
//                }
//            } else {
//                map.put("code", Global.CODE_DATA_INSERT_EXCEPION);
//            }
//        } catch (Exception e) {
//            logger.warn("[repairCard.api]-[post]-[/api/repairCard/createRepairCard]  " + "CODE = " + Global.CODE_DATA_INSERT_EXCEPION + "," + "exception message:" + e.getMessage());
//            map.put("code", Global.CODE_DATA_INSERT_EXCEPION);
//        }
//        return map;
//    }

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/6/19 13:48
     * @Description: 没有身份证 补卡登记 名单下发 入脸谱 入今日来访库
     */
    @RequestMapping(value = "/api/repairCard/createRepairCards")
    public HashMap<String, Object> createRepairCards(@RequestBody RepairCard repairCard) {
        logger.info("[repairCard.api]-[post]-[/api/repairCard/createRepairCard] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            // 判断身份证号是否为空
            if (repairCard.getVisitCardNumber() != null && !"".equals(repairCard.getVisitCardNumber())) {
                // 判断姓名是否为空
                if (repairCard.getVisitName() != null && !"".equals(repairCard.getVisitName())) {
                    Visitor visitor1 = visitorService.queryVisitorByIDNoToday(repairCard.getVisitCardNumber());
                    // 判断查询的人员是否为空
                    if (visitor1 == null || "".equals(visitor1)) {
                        Visitor visitor = new Visitor();
                        visitor.setVisitName(repairCard.getVisitName());
                        visitor.setVisitSex(repairCard.getVisitSex());
                        visitor.setVisitCardNumber(repairCard.getVisitCardNumber());
                        String base64 = repairCard.getVisitPicture().substring(22);
                        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                        String newDate = sf.format(new Date());
                        String photo = baseUrl + visit + "_byCardNo[" + repairCard.getVisitCardNumber() + "_" + newDate + "_IDInfoCapturePic.jpg";
                        boolean b = base64ChangeImage(base64, photo);
                        Thumbnails.of(photo).size(480,640).toFile(photo);
                        String pic = access + photo.substring(23);
//                        if (b == true) {
//                            visitor.setVisitPicture(pic);
//                        }
                        if(b){
                            visitor.setVisitPicture(pic);
                        }
                        visitor.setVisitTime(new Date());
                        visitor.setVisitNation(repairCard.getVisitNation());
                        visitor.setVisitCause(repairCard.getVisitCause());
                        visitor.setVisitCardType(repairCard.getVisitCardType());
                        visitor.setLawyerCard(repairCard.getLawyerCard()); // 律师证号
                        // 判断照片是否为空
                        if (pic == null && "".equals(pic)) {
                            map.put("code", Global.CODE_FACE_IS_NULL);
                            return map;
                        }
                        /////////////////////////////////////名单下发
                        this.dealTask(visitor);
                        // *********************************添加人脸*****************************************
                        CreateFace createFace = new CreateFace();
                        String faceUrl = httppath + localhostip + ":" + port + pic;
                        createFace.setFaceURL(faceUrl);
                        createFace.setFaceLibType("blackFD"); // 必填
                        createFace.setFDID("1"); // 必填
                        createFace.setFPID("");
                        createFace.setName(repairCard.getVisitName()); // 必填
                        if (1 == repairCard.getVisitSex()) {
                            createFace.setGender("male");
                        } else {
                            createFace.setGender("female");
                        }
                        // 处理出生日期
                        String year = repairCard.getVisitCardNumber().substring(6, 10);
                        String month = repairCard.getVisitCardNumber().substring(10, 12);
                        String day = repairCard.getVisitCardNumber().substring(12, 14);
                        String bornTime = year + "-" + month + "-" + day;
                        createFace.setBornTime(bornTime); // 出生日期 必填
                        String substring = repairCard.getVisitCardNumber().substring(0, 6);
                        createFace.setCity(substring);
                        createFace.setCertificateType("ID");
                        createFace.setCertificateNumber(repairCard.getVisitCardNumber());
                        createFace.setCaseInfo("");
                        createFace.setTag("");
                        createFace.setAddress("");
                        createFace.setCustomInfo("");
                        String string = JSONObject.toJSONString(createFace);
                        // ****************************************查询人脸************************************
                        QueryFace queryFace = new QueryFace();
                        queryFace.setSearchResultPosition(0);
                        queryFace.setMaxResults(100);
                        queryFace.setFaceLibType("blackFD");
                        queryFace.setFDID("1");
                        queryFace.setCertificateNumber(repairCard.getVisitCardNumber());
                        String six = JSONObject.toJSONString(queryFace);
                        String s = hikfaceService.FaceApi(six, 6);
                        JSONObject jsonObject = JSONObject.parseObject(s);
                        // ****************************************************************************
                        if (jsonObject.getString("totalMatches").equals("0")) {
                            String addFaceResult = hikfaceService.FaceApi(string, 3);
                            JSONObject jsonObject1 = JSONObject.parseObject(addFaceResult);
                            String subStatusCode = jsonObject1.getString("subStatusCode");
                            switch (subStatusCode) {
                                case "ok":
                                    map.put("code", Global.CODE_SUCCESS);
                                    break;
                                case "invalidID":
                                    map.put("code", Global.CODE_FACE_INVALIDID);
                                    break;
                                case "faceDetectFailed":
                                    map.put("code", Global.CODE_FACE_TO_DETECT_FACE);
                                    break;
                            }
                            int i = (int) map.get("code");
                            if (i != 200) {
                                return map;
                            }
                        }
                        // 入今日来访库
                        visitorService.createVisitor(visitor);
//                         入律师库
//                        String lawyerCards = repairCard.getLawyerCard();
//                        if (lawyerCards != null && !"".equals(lawyerCards)) {
//                            Personnel personnel1 = new Personnel();
//                            personnel1.setPerNumber(repairCard.getVisitCardNumber());
//                            Personnel personnel2 = personnelService.queryPersonnelByNumber(personnel1);
//                            if (personnel2 != null) {
//                                map.put("code", Global.CODE_LAWYER_IS_EXIST);
//                                return map;
//                            } else {
//                                Personnel personnel = new Personnel();
//                                personnel.setPerName(repairCard.getVisitName()); // 姓名
//                                personnel.setPerPicture(pic); // 照片
//                                personnel.setPerType(-1); // 人员类型
//                                personnel.setLawyerCard(lawyerCards); // 律师证号
//                                personnel.setPerNumber(repairCard.getVisitCardNumber()); // 身份证号
//                                personnel.setPerSex(repairCard.getVisitSex()); // 性别
//                                personnel.setPerNation(repairCard.getVisitNation()); // 民族
//                                personnel.setPerIDType(repairCard.getVisitCardType()); // 证件类型
//                                personnel.setCreateDate(new Date());
//                                personnel.setPerUUID(UUID.randomUUID().toString());
//                                personnel.setPerStatus(0);
//                                personnelService.createPersonnel(personnel);
//                            }
//                        }
                        map.put("code", Global.CODE_SUCCESS);
                    }else {
                        map.put("code", Global.CODE_DATA_INSERT_EXCEPION);
                        return map;
                    }
                }
            } else {
                map.put("code", Global.CODE_DATA_INSERT_EXCEPION);
            }
        } catch (Exception e) {
            logger.warn("[repairCard.api]-[post]-[/api/repairCard/createRepairCard]  " + "CODE = " + Global.CODE_DATA_INSERT_EXCEPION + "," + "exception message:" + e.getMessage());
            map.put("code", Global.CODE_DATA_INSERT_EXCEPION);
        }
        return map;
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


    /**
     * 下发今日/历史名单
     *
     * @param visitor
     */
    public void dealTask(Visitor visitor) {
        //今日来访任务
        List<Task> list100 = taskService.queryTaskByType(-100);
        // 历史来访任务
        List<Task> list200 = taskService.queryTaskByType(-200);

        if (list100 != null && list100.size() > 0) {
            for (Task task : list100) {
                this.sendVisitor(task, visitor);
            }
        }
        if (list200 != null && list200.size() > 0) {
            for (Task task : list200) {
                this.sendVisitor(task, visitor);
            }
        }
    }

    /**
     * 调用海康服务执行名单下发
     *
     * @param task
     * @param visitor
     */
    public void sendVisitor(Task task, Visitor visitor) {
        // 得到当前设备信息
        Device device = new Device();
        device.setDeviceId(task.getTaskDeviceId());
        device = deviceService.queryDeviceById(device);
        boolean is = true;
        int resultCode = 1;
        if (device != null) {
            // 名单下发
            if (visitor.getVisitPicture() == null || visitor.getVisitPicture().equals("") || visitor.getVisitPicture().equals("null")) {
                resultCode = 20;
                logger.info("[repairCard.api]-[sendVisitor] pic is null");
            } else {
                String jpg = visitor.getVisitPicture().substring(7);
                File pic = new File(baseUrl + visit + jpg);
                if (pic.exists()) {
                    HashMap resultInfo = hikvisonService.sendPerNew(device.getDeviceip(), visitor.getVisitName(), visitor.getVisitCardNumber(), baseUrl + visit + jpg);
                    is = (boolean) resultInfo.get("is");
                    resultCode = Integer.parseInt(resultInfo.get("result").toString());
                    logger.info("[repairCard.api]-[sendVisitor]hk send into....");
                } else {
                    resultCode = 18;
                    logger.info("[repairCard.api]-[sendVisitor]pic file is not exist");
                }
            }
            // 如果人员信息下发失败，记录当前人员信息
            if (is == false || resultCode == 0) {
                logger.info("[repairCard.api]-[sendVisitor]hk send error is=false or code=0");
                // 封装下发结果实体类，并入库
                this.createTaskResult(task.getTaskGuid(), visitor.getVisitName(), visitor.getVisitCardNumber(), resultCode);
            } else {
                logger.info("[repairCard.api]-[sendVisitor]hk send error code:" + resultCode);
                // 封装下发结果实体类，并入库
                this.createTaskResult(task.getTaskGuid(), visitor.getVisitName(), visitor.getVisitCardNumber(), resultCode);
            }
        } else {
            this.createTaskResult(task.getTaskGuid(), "NULL", "NULL", 21);
            logger.info("[repairCard.api]-[sendVisitor] device is null");
        }
    }

    /**
     * 名单下发失败--记录失败信息
     *
     * @param taskGuid   任务guid
     * @param perName    人员名称
     * @param perNumber  人员证件号
     * @param resultCode 错误码
     */
    public void createTaskResult(String taskGuid, String perName, String perNumber, int resultCode) {
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
        logger.info("[personnel.api]-[createTaskResult] info:" + perName + " and code:" + resultCode);
    }
}