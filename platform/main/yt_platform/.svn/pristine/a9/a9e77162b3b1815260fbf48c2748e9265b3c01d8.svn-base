package com.san.platform.rabbitmq.rabbitMQDirect;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.san.platform.BaseController;
import com.san.platform.JSON.*;
import com.san.platform.alert.*;
import com.san.platform.config.HkErrorInfo;
import com.san.platform.device.Device;
import com.san.platform.device.DeviceService;
import com.san.platform.hikvision.HikvisonService;
import com.san.platform.personnel.Personnel;
import com.san.platform.personnel.PersonnelService;
import com.san.platform.task.Task;
import com.san.platform.task.TaskResult;
import com.san.platform.task.TaskResultService;
import com.san.platform.task.TaskService;
import com.san.platform.visitor.RepairCard;
import com.san.platform.display.DisplayService;
import com.san.platform.hikface.HikfaceService;
import com.san.platform.track.TrackService;
import com.san.platform.visitor.RepairCardService;
import com.san.platform.visitor.Visitor;
import com.san.platform.visitor.VisitorService;
import com.san.platform.websockt.MyWebSocket;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


@RestController
public class ReceiverTopic1 extends BaseController {
    @Reference
    private AlertService alertService;
    @Reference
    private AlertListService alertListService;
    @Reference
    private MyWebSocket mywebSocket;
    @Reference
    private VisitorService visitorService;
    @Reference
    private DisplayService displayService;
    @Reference
    private HikfaceService hikfaceService;
    @Reference
    private TrackService trackService;
    @Reference
    private RepairCardService repairCardService;
    @Reference
    private HikvisonService hikvisonService;
    @Reference
    private TaskService taskService;
    @Reference
    private DeviceService deviceService;
    @Reference
    private PersonnelService personnelService;
    @Reference
    private TaskResultService taskResultService;

    @Value("${path.baseUrl}")
    private String baseUrl;
    //存储图片访客路径
    @Value("${path.visit}")
    private String visit;
    @Value("${path.leave}")
    private String leave1;
    @Value("${path.leave2}")
    private String leave2;


    @RabbitListener(queues = "topic_visit")
    @SendTo("topic_visit")
    public void process1(Visitor visitor) {
        logger.info("[rebbitmq]-[topic_visit method have been called.");
        try {
            visitor.setSignCode(105);
            String s = JSONObject.toJSONString(visitor);
            String visitCardNumber = visitor.getVisitCardNumber();
            String visitPicture = visitor.getVisitPicture();
            String visitName = visitor.getVisitName();
            Date today = new Date();
            Device device = deviceService.queryDeviceByIP(visitor.getDeviceIP());
            //进场离场闸机判断
            if (device.getDeviceType()==1002) {
            // 20482 人脸对比成功
            if (visitName == null || "".equals(visitName)) {
                Visitor visitor1 = visitorService.queryVisitorByIDNoToday(visitCardNumber);
                if (visitor1 == null || "".equals(visitor1)) {
                    logger.info("[rebbitmq]-[topic_visit 20482 per and face come in method have been called.");
                    Visitor visitor2 = visitorService.queryVisitorByIDNo(visitCardNumber);
                    if (visitor2 != null && !"".equals(visitor2)) {
                        visitor2.setVisitTime(today);
                        visitor2.setVisitPicture(visitPicture);
                        visitor2.setVisitCause("其他事宜");
                        visitor2.setLeaveTime(null);
                        visitor2.setSignCode(105);
                        visitorService.createVisitor(visitor2);
                        ///////////////////名单下发//////////////
                        this.dealTask(visitor);
                        String s1 = JSONObject.toJSONString(visitor2);
                        mywebSocket.sendInfo(s1);
                        logger.info("topic_queue_1:消费了 :" + visitor);
                    }
                }
            } else { // 20992 认证对比成功
                Visitor visitor1 = visitorService.queryVisitorByIDNoToday(visitCardNumber);
                if (visitor1 == null || "".equals(visitor1)) {
                    logger.info("[rebbitmq]-[topic_visit 20992 pre and card come in method have been called.");
                    Personnel personnel = new Personnel();
                    personnel.setPerNumber(visitCardNumber);
                    Personnel personnel1 = personnelService.selectTroublePersonnel(personnel);
                    if (personnel1 != null && !"".equals(personnel1)) {
                        visitor.setCardPhoto(visitor.getCardPhoto());
                        visitor.setSignCode(118);
                        visitor.setVisitCause("其他事宜");
                        visitorService.createVisitor(visitor);
                        String string = JSONObject.toJSONString(visitor);
                        mywebSocket.sendInfo(string);
                    } else {
                        visitor.setVisitCause("其他事宜");
                        visitorService.createVisitor(visitor);
                        ///////////////////名单下发//////////////
                        this.dealTask(visitor);
                        mywebSocket.sendInfo(s);
                        System.out.println("topic_queue_1:消费了 :" + visitor);
                        logger.info("topic_queue_1:消费了 :" + visitor);
                    }
                }
            }
        } else { //离场
                Visitor visitor2 = visitorService.queryVisitorIdByIDNoToday(visitCardNumber);
                visitor2.setLeaveTime(today);
                visitor2.setSignCode(100);
                visitorService.updateVisitCause(visitor2);
                String s1 = JSONObject.toJSONString(visitor2);
                mywebSocket.sendInfo(s1);
            }
        }catch (Exception e){
            logger.warn("[rebbitmq]-[topic_visit Exception" + e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/6/10 16:04
     * @Description: 添加人脸
     */
    @RabbitListener(queues = "topic_face_add")
    @SendTo("topic_face_add")
    public void addFace(Visitor visitor) {
        try {
            logger.info("[rebbitmq]-[topic_face_add method have been called.");
            //          添加人脸
            // *********************************************************************
            CreateFace createFace = new CreateFace();
            createFace.setFaceURL(visitor.getFaceSrc());
            createFace.setFaceLibType("blackFD");
            createFace.setFDID("1");
            createFace.setFPID("");
            createFace.setName(visitor.getVisitName());
            if (1 == visitor.getVisitSex()) {
                createFace.setGender("male");
            } else {
                createFace.setGender("female");
            }
            createFace.setBornTime(visitor.getBornTime());
            String substring = visitor.getVisitCardNumber().substring(0, 6);
            createFace.setCity(substring);
            createFace.setCertificateType("ID");
            createFace.setCertificateNumber(visitor.getVisitCardNumber());
            createFace.setCaseInfo("");
            createFace.setTag("");
            createFace.setAddress("");
            createFace.setCustomInfo("");
            String string2 = JSONObject.toJSONString(createFace);
            // string2 添加和修改需要的 信息
            //          查询人脸
            // *********************************************************************
            QueryFace queryFace = new QueryFace();
            queryFace.setSearchResultPosition(0);
            queryFace.setMaxResults(100);
            queryFace.setFaceLibType("blackFD");
            queryFace.setFDID("1");
            queryFace.setCertificateNumber(visitor.getVisitCardNumber());
            String six = JSONObject.toJSONString(queryFace);
            String result = hikfaceService.FaceApi(six, 6); // 查询
            JSONObject jsonObject = JSONObject.parseObject(result);
            // 如果没查到 就添加
            if (jsonObject.getString("totalMatches").equals("0")) {
                hikfaceService.FaceApi(string2, 3); // 添加
            } else if (!jsonObject.getString("totalMatches").equals("0")) {
                //          修改人脸
                // *********************************************************************
                JSONArray list = jsonObject.getJSONArray("MatchList");
                for (int i = 0; i < list.size(); i++) {
                    JSONObject object = list.getJSONObject(i);
                    // 获取数组里的FPID
                    String fpid = object.getString("FPID");
                    hikfaceService.updateFace(string2, fpid); // 修改
                }
            }
        } catch (Exception e) {
            logger.warn("[rebbitmq]-[addFace Exception" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/6/10 16:04
     * @Description: 修改人脸
     */
    @RabbitListener(queues = "topic_face_update")
    @SendTo("topic_face_update")
    public void updateFace(Visitor visitor) {
        try {
            logger.info("[rebbitmq]-[topic_face_update method have been called.");
//            Visitor visitor1 = visitorService.queryVisitorByIDNoToday(visitor.getVisitCardNumber());
            // 先查询人脸再修改人脸
            // *********************************************************************
            QueryFace queryFace = new QueryFace();
            queryFace.setSearchResultPosition(0);
            queryFace.setMaxResults(100);
            queryFace.setFaceLibType("blackFD");
            queryFace.setFDID("1");
            // 根据身份证号查询
            queryFace.setCertificateNumber(visitor.getVisitCardNumber());
            String six = JSONObject.toJSONString(queryFace);
            String result = hikfaceService.FaceApi(six, 6); // 查询
            // 将字符串 转换成 JSON对象
            JSONObject jsonObject = JSONObject.parseObject(result);
            // *********************************************************************
            // 根据身份证查询
            Visitor visitor1 = visitorService.queryVisitorByIDNo(visitor.getVisitCardNumber());
                CreateFace createFace = new CreateFace();
                createFace.setFaceURL(visitor.getFaceSrc()); // 照片
                createFace.setFaceLibType("blackFD");
                createFace.setFDID("1");
                createFace.setFPID("");
                createFace.setName(visitor1.getVisitName()); // 姓名
            System.out.println("名字：" + visitor1.getVisitName());
                if (1 == visitor1.getVisitSex()) { // 性别
                    createFace.setGender("male");
                } else {
                    createFace.setGender("female");
                }
//            createFace.setBornTime(visitor.getBornTime()); // 出生日期
                // 处理出生日期
                String year = visitor1.getVisitCardNumber().substring(6, 10);
                String month = visitor1.getVisitCardNumber().substring(10, 12);
                String day = visitor1.getVisitCardNumber().substring(12, 14);
                String bornTime = year + "-" + month + "-" + day;
                createFace.setBornTime(bornTime); // 出生日期
                String substring = visitor1.getVisitCardNumber().substring(0, 6);
                createFace.setCity(substring); // 地区
                createFace.setCertificateType("ID");
                createFace.setCertificateNumber(visitor.getVisitCardNumber()); // 身份证号
                createFace.setCaseInfo("");
                createFace.setTag("");
                createFace.setAddress("");
                createFace.setCustomInfo("");
                String string2 = JSONObject.toJSONString(createFace);
                // string2 添加和修改需要的 信息
                // *************************************************************************
                if (!jsonObject.getString("totalMatches").equals("0")) {
                    // 如果查到了 就修改人脸
                    // 查询到人脸得到人脸的FPID
                    JSONArray list = jsonObject.getJSONArray("MatchList");
                    for (int i = 0; i < list.size(); i++) {
                        JSONObject object = list.getJSONObject(i);
                        // 获取数组里的FPID
                        String fpid = object.getString("FPID");
                        hikfaceService.updateFace(string2, fpid); // 修改
                    }
                    // *********************************************************************
                } else if (jsonObject.getString("totalMatches").equals("0")) {
                    // 否则如果没查到  就添加人脸
                    // 如果查询的totalMatches为0  没查到添加人脸
                    hikfaceService.FaceApi(string2, 3); // 添加
                }
        } catch (Exception e) {
            logger.warn("[rebbitmq]-[updateFace Exception" + e.getMessage());
            e.printStackTrace();
        }

    }


    @RabbitListener(queues = "topic_face_snap")
    @SendTo("topic_face_snap")
    public void faceSnap(Visitor visitor) {
        try {
//            判断离场球机
            String deviceIP2 = visitor.getDeviceIP();
            if (deviceIP2 != null && !"".equals(deviceIP2)) {
                if (leave1.equals(deviceIP2)||leave2.equals(deviceIP2)) {
                    String modeData = this.getModelData(visitor.getSmallJpg());
                    if (modeData != null && !"".equals(modeData)) {
                        FaceInfo faceInfo = this.dealFaceInfo(modeData);
                        if (faceInfo != null) {
                            String idNumber1 = faceInfo.getCertificateNumber();
                            Visitor visitor1 = visitorService.queryVisitorIdByIDNoToday(idNumber1);
                            if (visitor1 != null && !"".equals(visitor1)) {
                                Date today = new Date();
                                visitor1.setLeaveTime(today);
//                        visitor1.setSignCode(100);
                                visitorService.updateVisitCause(visitor1);
                            }
                        }
                    }
                }
            }
            logger.info("[rebbitmq]-[topic_face_snap method have been called.");
            //String modeData = this.getModelData(visitor.getSmallSrc());
            String modeData = this.getModelData(visitor.getSmallJpg());
//        if(modeData!=null&&!"".equals(modeData)){}
            if (modeData != null && !"".equals(modeData)) {
                FaceInfo faceInfo = this.dealFaceInfo(modeData);
                if (faceInfo != null) {
                    int result = trackService.createTrack(faceInfo.getCertificateNumber(), visitor.getDeviceIP(), visitor.getSmallSrc(), visitor.getBigSrc());
                    if (result > 0) {
                        logger.info("入库轨迹信息成功！！");
                    } else {
                        logger.info("入库轨迹信息失败！！");
                    }
                    alertListService.alarmwindow(visitor.getDeviceIP(), faceInfo.getCertificateNumber(), visitor.getSmallSrc(), visitor.getBigSrc());
                }
            }
        }catch (Exception e) {
            logger.warn("[rebbitmq]-[topic_face_snap Exception" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/6/11 10:22
     * @Description: 补卡登记 有身份证
     */
    @RabbitListener(queues = "topic_repair_card")
    @SendTo("topic_repair_card")
    public void repairCard(Visitor visitor) {
        try {
            logger.info("[rebbitmq]-[topic_repair_card method have been called.");
            RepairCard repairCard = new RepairCard();
            repairCard.setCardId(visitor.getVisitId());
            repairCard.setCardId(repairCard.getCardId());
            repairCard.setVisitName(visitor.getVisitName());
            repairCard.setVisitSex(visitor.getVisitSex());
            repairCard.setVisitCardNumber(visitor.getVisitCardNumber());
            repairCard.setVisitPicture(visitor.getVisitPicture());
            repairCard.setVisitCause(visitor.getVisitCause());
            repairCard.setCardPhoto(visitor.getCardPhoto());
            repairCard.setVisitTime(visitor.getVisitTime());
            repairCard.setVisitNation(visitor.getVisitNation());
            repairCard.setVisitAddress(visitor.getVisitAddress());
            repairCard.setVisitCardType(visitor.getVisitCardType());
            repairCard.setBornTime(visitor.getBornTime());
            repairCard.setSignCode(112);
            // 放到临时表里
            if (repairCard != null && !"".equals(repairCard)) {
                repairCardService.createRepairCard(repairCard);
            }
            // 对象转String
            String string = JSONObject.toJSONString(repairCard);
            logger.info("[rebbitmq]-[topic_repair_card socket send" + string);
            // 发送信息
            mywebSocket.sendInfo(string);
        } catch (Exception e) {
            logger.warn("[rebbitmq]-[topic_repair_card Exception" + e.getMessage());
            e.printStackTrace();
        }

    }


    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/6/11 10:22
     * @Description: 补卡登记 没身份证
     */
    @RabbitListener(queues = "topic_repair_cards")
    @SendTo("topic_repair_cards")
    public void repairCards(Visitor visitor) {
        try {
            logger.info("[rebbitmq]-[topic_repair_cards method have been called.");
            RepairCard repairCard = new RepairCard();
            repairCard.setVisitPicture(visitor.getVisitPicture());
//            repairCard.setSignCode(76);
            // 放到临时表里
            if (repairCard != null && !"".equals(repairCard)) {
                repairCardService.createRepairCard(repairCard);
            }
            // 对象转String
            String string = JSONObject.toJSONString(repairCard);
            logger.info("[rebbitmq]-[topic_repair_cards socket send" + string);
            // 发送信息
            mywebSocket.sendInfo(string);
        } catch (Exception e) {
            logger.warn("[rebbitmq]-[topic_repair_cards Exception" + e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * 人脸解析-返回modeDate
     *
     * @param smallSrc
     * @return
     */
    public String getModelData(String smallSrc) {
        logger.info("[rebbitmq]-[ReceiverTopic1.getModelData method have been called.");
        logger.info("com.san.platform.hikvison.hkmethod::getModelData人脸解析-返回modeDate");
        String modeDate = "";
        AnalysisFace analysisFace = new AnalysisFace();
        analysisFace.setImagesData(smallSrc);
        String faceinfo = JSONObject.toJSONString(analysisFace);
        String result = hikfaceService.FaceApi(faceinfo, 4);
        if (!result.equals("") && result != "") {
            JSONObject jsonObject = JSON.parseObject(result);
            Long code = Long.parseLong(jsonObject.getString("errorCode"));
            if (code == 1) {
                JSONArray array = jsonObject.getJSONArray("targets");
                for (int i = 0; i < array.size(); i++) {
                    JSONObject jsonTargets = JSONObject.parseObject("" + array.get(i));
                    modeDate = jsonTargets.getString("targetModelData");
                }
            } else {
                logger.info("人脸解析-返回modeDate::人脸解析失败");
            }
        } else {
            logger.info("人脸解析-返回modeDate:：人脸服务器登陆失败或地址参数错误");
        }
        return modeDate;
    }

    /**
     * 以图搜图-返回匹配的人员信息
     *
     * @param modeDate
     * @return
     */
    public FaceInfo dealFaceInfo(String modeDate) {
        logger.info("[rebbitmq]-[ReceiverTopic1.dealFaceInfo method have been called.");
        FaceInfo faceInfo = null;
        SearchFace searchFace = new SearchFace();
        searchFace.setTargetModelData(modeDate);
        String faceinfo = JSONObject.toJSONString(searchFace);
        String result = hikfaceService.FaceApi(faceinfo, 5);
        if (!result.equals("") && result != "") {
            JSONObject jsonObject = JSON.parseObject(result); //将json字符串转换成jsonObject对象
            Long code = Long.parseLong(jsonObject.getString("errorCode"));
            if (code == 1) {
                JSONArray array = jsonObject.getJSONArray("MatchList");
                if (array != null) {
                    List<FaceInfo> faceInfoList = array.toJavaList(FaceInfo.class);
                    if (faceInfoList.size() > 0) {
                        double similarity = 0;
                        for (FaceInfo f : faceInfoList) {
                            if (similarity == 0 || f.getSimilarity() > similarity) {
                                similarity = f.getSimilarity();
                                faceInfo = f;
                            }
                        }
                    }
                }
            } else {
                logger.info("以图搜图-返回匹配的人员信息::没有匹配到人脸信息");
            }
        } else {
            logger.info("人脸解析-返回modeDate:：人脸服务器登陆失败或地址参数错误");
        }
        return faceInfo;
    }


    @RabbitListener(queues = "topic_later")
    @SendTo("topic_later")
    public void laterss(Visitor visitor) {
        try {
            logger.info("[rebbitmq]-[topic_later method have been called.");
            if (visitor != null && !"".equals(visitor)) {
                String visitName = visitor.getVisitName();
                String visitCardNumber = visitor.getVisitCardNumber();
                String visitPicture = visitor.getVisitPicture();
                int iii = 0;
                while (iii < 5) {
                    iii += 1;
                    Thread.sleep(60000);
                    boolean b = hikvisonService.updateHikDevice(visitName, visitCardNumber, visitPicture);
                    if (b == true) {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            logger.warn("[rebbitmq]-[topic_later Exception" + e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * 下发今日/历史名单
     * @param visitor
     */
    public void dealTask (Visitor visitor) {
        //今日来访任务
        List<Task> list100 = taskService.queryTaskByType(-100);
        // 历史来访任务
        List<Task> list200 = taskService.queryTaskByType(-200);

        if (list100 != null && list100.size()>0) {
            for (Task task: list100) {
                this.sendVisitor(task,visitor);
            }
        }
        if (list200 != null &&list200.size()>0) {
            for (Task task: list200) {
                this.sendVisitor(task,visitor);
            }
        }
    }

    /**
     * 调用海康服务执行名单下发
     * @param task
     * @param visitor
     */
    public void sendVisitor (Task task, Visitor visitor) {
        // 得到当前设备信息
        Device device = new Device();
        device.setDeviceId(task.getTaskDeviceId());
        device = deviceService.queryDeviceById(device);
        boolean is = true;
        int resultCode = 1;
        if (device != null) {
            // 名单下发
            if (visitor.getVisitPicture() == null || visitor.getVisitPicture().equals("")||visitor.getVisitPicture().equals("null")){
                resultCode = 20;
                logger.info("[rebbitmq.api]-[sendVisitor] pic is null");
            }else{
                String jpg = visitor.getVisitPicture().substring(7);
                File pic = new File(baseUrl + visit + jpg);
                if (pic.exists()) {
                    HashMap resultInfo = hikvisonService.sendPerNew(device.getDeviceip() , visitor.getVisitName(),visitor.getVisitCardNumber(),baseUrl + visit + jpg);
                    is = (boolean)resultInfo.get("is");
                    resultCode = Integer.parseInt(resultInfo.get("result").toString());
                    logger.info("[rebbitmq.api]-[sendVisitor]hk send into....");
                }else{
                    resultCode = 18;
                    logger.info("[rebbitmq.api]-[sendVisitor]pic file is not exist");
                }
            }
            // 如果人员信息下发失败，记录当前人员信息
            if (is == false || resultCode == 0) {
                logger.info("[rebbitmq.api]-[sendVisitor]hk send error is=false or code=0");
                // 封装下发结果实体类，并入库
                this.createTaskResult(task.getTaskGuid(),visitor.getVisitName(),visitor.getVisitCardNumber(),resultCode);
            }else{
                logger.info("[rebbitmq.api]-[sendVisitor]hk send error code:"+resultCode);
                // 封装下发结果实体类，并入库
                this.createTaskResult(task.getTaskGuid(),visitor.getVisitName(),visitor.getVisitCardNumber(),resultCode);
            }
        }else{
            this.createTaskResult(task.getTaskGuid(),"NULL","NULL",21);
            logger.info("[rebbitmq]-[sendVisitor] device is null");
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
        logger.info("[rebbitmq.api]-[createTaskResult] method have been called.");
        TaskResult taskResult = new TaskResult();
        taskResult.setTaskGuid(taskGuid);
        taskResult.setPerName(perName);
        taskResult.setPerNumber(perNumber);
        taskResult.setResultCode(resultCode);
        // hk 错误码转化
        taskResult.setResultInfo(HkErrorInfo.errorInfo(resultCode));
        taskResult.setTaskDate(new Date());
        taskResultService.createResult(taskResult);
        logger.info("[rebbitmq.api]-[createTaskResult] info:"+perName+" and code:"+resultCode);
    }

}
