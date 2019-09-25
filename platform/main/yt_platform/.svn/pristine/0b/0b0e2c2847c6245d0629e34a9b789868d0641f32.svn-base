package com.san.platform.task.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.san.platform.BaseController;
import com.san.platform.JSON.FaceInfo;
import com.san.platform.config.Global;
import com.san.platform.config.HkErrorInfo;
import com.san.platform.config.RandomUtil;
import com.san.platform.device.Device;
import com.san.platform.device.DeviceService;
import com.san.platform.hikvision.HikvisonService;
import com.san.platform.personnel.Personnel;
import com.san.platform.personnel.PersonnelService;
import com.san.platform.task.*;
import com.san.platform.visitor.Visitor;
import com.san.platform.visitor.VisitorService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import java.io.File;
import java.util.*;

@RestController
public class TaskController extends BaseController {

    //存储图片根路径
    @Value("${path.baseUrl}")
    private String baseUrl;
    //存储图片人像路径
    @Value("${path.person}")
    private String person;
    @Value("${path.visit}")
    private String pathVisit;

    @Reference
    private TaskService taskService;

    @Reference
    private DeviceService deviceService;

    @Reference
    private PersonnelService personnelService;

    @Reference
    private HikvisonService hikvisonService;

    @Reference
    private VisitorService visitorService;

    @Reference
    private TaskResultService taskResultService;

    @ResponseBody
    @GetMapping(value = "/api/task/queryTaskPage")
    public HashMap<String, Object> queryTaskPage (Task task) {
        logger.info("[task.api]-[get]-[/api/task/queryTaskPage] method have been called.");
        HashMap<String, Object> hashMap = new HashMap<>();
        try {
            hashMap = taskService.queryAllTask(task);
            // map加入code信息 200
            hashMap.put("code", Global.CODE_SUCCESS); // 数据正常，返回code 200
        } catch (Exception e) {
            hashMap.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.warn("[task.api]-[get]-[/api/task/queryTaskPag] CODE_DB_QUERY_EXCEPTION exception:" + e.getMessage());
        }
        return hashMap;
    }

    @ResponseBody
    @RequestMapping(value = "/api/task/createTask")
    public HashMap<String, Object> createTask(@RequestBody Task task) {
        logger.info("[task.api]-[post]-[/api/task/createTask] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            task.setTaskGuid(RandomUtil.GetGuid32());
            task.setTaskStatus(0);// 默认 下发任务状态下发中
            int i = taskService.createTask(task);
            if (i == 0) { // 创建设备失败，返回code 3842
                map.put("code", Global.CODE_TASK_CREATE_EXCEPTION);
            } else { // 创建设备成功，返回code 200
                map.put("code", Global.CODE_SUCCESS);
                map.put("taskGuid",task.getTaskGuid());
            }
        }catch (Exception e){
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.warn("[task.api]-[post]-[/api/task/createTask] CODE_DB_QUERY_EXCEPTION exception:" + e.getMessage());
        }
        return map;
    }

    /**
     * 执行任务
     * @param task
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/api/task/startTask")
    public HashMap<String, Object> startTask(@RequestBody Task task) {
        logger.info("[task.api]-[post]-[/api/task/startTask] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        Task taskNew = taskService.queryTaskByGuid(task.getTaskGuid());
        try {
            if (taskNew != null) {
                this.sendPerNew(taskNew,taskNew.getTaskDeviceId(),taskNew.getTaskPerType());
                map.put("code", Global.CODE_SUCCESS);
            } else {
                // 更新任务状态信息
                Task errTask = new Task();
                errTask.setTaskId(taskNew.getTaskId());
                errTask.setTaskStatus(-1);
                taskService.updateTaskStatus(errTask);
                map.put("code",Global.CODE_TASK_IS_NOT_EXIST);
            }
        }catch (Exception e){
            // 处理异常，更新任务状态
            Task errTask = new Task();
            errTask.setTaskId(taskNew.getTaskId());
            errTask.setTaskStatus(-1);
            taskService.updateTaskStatus(errTask);
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.warn("[task.api]-[post]-[/api/task/startTask] CODE_DB_QUERY_EXCEPTION exception:" + e.getMessage());
        }
        return map;
    }

    /**
     * 查询任务详情
     * @param task
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/api/task/queryErrorByTaskGuid")
    public HashMap<String, Object> queryErrorByTaskGuid(Task task) {
        logger.info("[task.api]-[post]-[/api/task/queryErrorByTaskGuid] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            map = taskResultService.queryByTaskGuid(task);
            map.put("code", Global.CODE_SUCCESS);
        }catch (Exception e){
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.warn("[task.api]-[post]-[/api/task/queryErrorByTaskGuid] CODE_DB_QUERY_EXCEPTION exception:" + e.getMessage());
        }
        return map;
    }

    /**
     * 删除任务信息
     * @param task
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/api/task/removeTaskById")
    public Map<String, Object> removeTaskById(@RequestBody Task task) {
        logger.info("[task.api]-[post]-[/api/task/removeTaskById] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            int i = taskService.removeTask(task);
            if (i == 0) { // 删除异常，返回code 3843
                map.put("code", Global.CODE_TASK_REMOVE_EXCEPTION);
            } else { // 删除成功，返回code200
                map.put("code", Global.CODE_SUCCESS);
            }
        } catch (Exception e) { //数据异常，返回code 2003
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.warn("[task.api]-[post]-[/api/task/removeTaskById] CODE_DB_QUERY_EXCEPTION exception:" + e.getMessage());
        }
        return map;
    }

    @ResponseBody
    @GetMapping(value = "/api/task/queryDeviceByType")
    public HashMap<String, Object> queryDeviceByType() {
        logger.info("[task.api]-[get]-[/api/task/queryDeviceByType] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        List list = new ArrayList();
        try {
            // select选择 默认值
            HashMap<String, Object> mapDefault = new HashMap<>();
            mapDefault.put("text","请选择---");
            mapDefault.put("value",0);
            List<Device> deviceList = deviceService.queryDeviceByType();
            if (deviceList.size()>0) {
                for (Device device : deviceList) {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("text", device.getDeviceName());
                    hashMap.put("value", device.getDeviceId().toString());
                    list.add(hashMap);
                }
                map.put("results", list);
            }
            // map加入code信息 200
            map.put("code", Global.CODE_SUCCESS); // 数据正常，返回code 200
        } catch (Exception e) { // 数据异常，返回code 2003
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[task.api]-[get]-[/api/task/queryDeviceByType] CODE_DB_QUERY_EXCEPTION exception:" + e.getMessage());
        }
        return map;
    }

    /**
     * 重新全部下发--更新状态
     * @param task
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/api/task/reStartTask")
    public Map<String, Object> reStartTask(@RequestBody Task task) {
        logger.info("[task.api]-[post]-[/api/task/reStartTask] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            Task taskNew = new Task();
            taskNew.setTaskStatus(0);
            taskNew.setTaskId(task.getTaskId());
            taskService.updateTaskStatus(taskNew);
            map.put("code", Global.CODE_SUCCESS);
        } catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.warn("[task.api]-[post]-[/api/task/reStartTask] CODE_DB_QUERY_EXCEPTION exception:" + e.getMessage());
        }

        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/api/task/updateTaskStatus")
    public Map<String, Object> updateTaskStatus(@RequestBody Task task) {
        logger.info("[task.api]-[post]-[/api/task/updateTaskStatus] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            Task taskNew = new Task();
            taskNew = taskService.queryTaskById(task);
            if (taskNew.getTaskError() != "" && !"".equals(taskNew.getTaskError())) {
                taskNew.setTaskStatus(0);
                taskNew.setTaskId(task.getTaskId());
                taskService.updateTaskStatus(taskNew);
                map.put("code", Global.CODE_SUCCESS);
            }else{
                map.put("code", Global.CODE_TASK_STARTERR_EXCEPTION);
            }
        } catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.warn("[task.api]-[post]-[/api/task/reStartTask] CODE_DB_QUERY_EXCEPTION exception:" + e.getMessage());
        }

        return map;
    }
    /**
     * 重新下发失败名单--
     * @param task
     */
    @ResponseBody
    @RequestMapping(value = "/api/task/startErrTask")
    public HashMap<String, Object> startErrTask (@RequestBody Task task) {
        logger.info("[task.api]-[post]-[/api/task/startErrTask] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            String sendErr = ","; // 存储下发失败人员信息
            int i = 0; // 计数下发成功人数信息
            Task taskNew = taskService.queryTaskById(task);
            Device device = new Device();
            device.setDeviceId(taskNew.getTaskDeviceId());
            Device deviceNew = deviceService.queryDeviceById(device);
            if (taskNew != null) {
                String taskError = taskNew.getTaskError();
                if (taskError != "" && !"".equals(taskError)) {
                    // 今日来访/历史来访
                    if (taskNew.getTaskPerType() == -100 || taskNew.getTaskPerType() == -200) {
                        logger.info("[task.api]-[/api/task/startErrTask] send visit");
                        for (String id :taskError.split(",")) {
                            if (id !="" && !"".equals(id)) {
                                boolean is = true;
                                int resultCode = 1;
                                Visitor visitor = new Visitor();
                                visitor.setVisitId(Integer.parseInt(id));
                                visitor = visitorService.queryVisitorById(visitor);
                                if (visitor == null) {
                                    logger.info("[task.api]-[startErrTask] Visitor is null");
                                    // 人像库中 不存在需要下发的人脸信息，记录失败
                                    this.createTaskResult(task.getTaskGuid(),"NULL","NULL",19);
                                    task.setTaskResult("0/0");
                                }else {
                                    if (visitor.getVisitPicture() == null || visitor.getVisitPicture().equals("")||visitor.getVisitPicture().equals("null")){
                                        resultCode = 20;
                                        logger.info("[task.api]-[startErrTask] pic is null");
                                    }else {
                                        String jpg = visitor.getVisitPicture().substring(7);
                                        File pic = new File(baseUrl + pathVisit + jpg);
                                        if (pic.exists()) {
                                            HashMap resultInfo = hikvisonService.sendPerNew(deviceNew.getDeviceip() , visitor.getVisitName(),visitor.getVisitCardNumber(),baseUrl + pathVisit + jpg);
                                            is = (boolean)resultInfo.get("is");
                                            resultCode = Integer.parseInt(resultInfo.get("result").toString());
                                            logger.info("[task.api]-[startErrTask]hk send into....");
                                        }else{
                                            resultCode = 18;
                                            logger.info("[task.api]-[startErrTask]pic file is not exist");
                                        }
                                        // 如果人员信息下发失败，记录当前人员信息
                                        if (is == false || resultCode == 0) {
                                            logger.info("[task.api]-[startErrTask]hk send error is=false or code=0");
                                            sendErr = sendErr + visitor.getVisitId() + ",";
                                            // 封装下发结果实体类，并入库
                                            this.createTaskResult(task.getTaskGuid(),visitor.getVisitName(),visitor.getVisitCardNumber(),resultCode);
                                        }else if (is == true && resultCode !=1) {
                                            logger.info("[task.api]-[startErrTask]hk send error code:"+resultCode);
                                            sendErr = sendErr + visitor.getVisitId() + ",";
                                            // 封装下发结果实体类，并入库
                                            this.createTaskResult(task.getTaskGuid(),visitor.getVisitName(),visitor.getVisitCardNumber(),resultCode);
                                        }else { // 如果人员信息下发成功，计数成功个数
                                            logger.info("[task.api]-[startErrTask]hk send success");
                                            i = i + 1;
                                        }
                                    }
                                }
                                // 如果人员信息下发失败，记录当前人员信息
                                if (is == false || resultCode == 0) {
                                    logger.info("[task.api]-[startErrTask]hk send error is=false or code=0");
                                    sendErr = sendErr + visitor.getVisitId() + ",";
                                    // 封装下发结果实体类，并入库
                                    this.createTaskResult(task.getTaskGuid(),visitor.getVisitName(),visitor.getVisitCardNumber(),resultCode);
                                }else if (is == true && resultCode !=1) {
                                    logger.info("[task.api]-[startErrTask]hk send error code:"+resultCode);
                                    sendErr = sendErr + visitor.getVisitId() + ",";
                                    // 封装下发结果实体类，并入库
                                    this.createTaskResult(task.getTaskGuid(),visitor.getVisitName(),visitor.getVisitCardNumber(),resultCode);
                                }else { // 如果人员信息下发成功，计数成功个数
                                    logger.info("[task.api]-[startErrTask]hk send success");
                                    i = i + 1;
                                }
                            }
                        }
                    } else { // 人像库
                        logger.info("[task.api]-[/api/task/startErrTask] send Personnel");
                        for (String id :taskError.split(",")) {
                            if (id != "" && !"".equals(id)) {
                                Personnel per = new Personnel();
                                per.setPerId(Integer.parseInt(id));
                                per = personnelService.queryPersonnelById(per);
                                boolean is = true;
                                int resultCode = 1;
                                if (per == null) {
                                    logger.info("[task.api]-[startErrTask] Visitor is null");
                                    // 人像库中 不存在需要下发的人脸信息，记录失败
                                    this.createTaskResult(task.getTaskGuid(),"NULL","NULL",19);
                                    task.setTaskResult("0/0");
                                }else{
                                    if (per.getPerPicture() == null || per.getPerPicture().equals("null") || per.getPerPicture().equals("")){
                                        resultCode = 20;
                                        logger.info("[task.api]-[startErrTask] pic is null");
                                    }else{
                                        String jpg = per.getPerPicture().substring(7);
                                        File pic = new File(baseUrl + person + jpg);
                                        if (pic.exists()) {
                                            HashMap resultInfo = hikvisonService.sendPerNew(deviceNew.getDeviceip() , per.getPerName(),per.getPerNumber(),baseUrl + person + jpg);
                                            is = (boolean)resultInfo.get("is");
                                            resultCode = Integer.parseInt(resultInfo.get("result").toString());
                                            logger.info("[task.api]-[startErrTask]hk send into....");
                                        }else{
                                            resultCode = 18;
                                            logger.info("[task.api]-[startErrTask] pic is not exist");
                                        }
                                    }
                                }
                                // 如果人员信息下发失败，记录当前人员信息
                                if (is == false || resultCode == 0) { // 长连接错误、
                                    logger.info("[task.api]-[startErrTask]send error is=false or code=0");
                                    sendErr = sendErr + per.getPerId() + ",";
                                    // 入库下发失败名单信息
                                    this.createTaskResult(task.getTaskGuid(),per.getPerName(),per.getPerNumber(),resultCode);
                                }else if (is == true && resultCode !=1) { // 人像下发失败
                                    logger.info("[task.api]-[startErrTask]send error code:"+resultCode);
                                    sendErr = sendErr + per.getPerId() + ",";
                                    // 入库下发失败名单信息
                                    this.createTaskResult(task.getTaskGuid(),per.getPerName(),per.getPerNumber(),resultCode);
                                }else { // 如果人员信息下发成功，计数成功个数
                                    logger.info("[task.api]-[startErrTask]hk send success,name:"+per.getPerName()+",number:"+per.getPerNumber());
                                    i = i + 1;
                                }
                            }
                        }
                    }
                    // 重新 设置任务结果
                    String taskResult = taskNew.getTaskResult();
                    int in = taskResult.indexOf("/");
                    int succ = Integer.parseInt(taskResult.substring(0,in));
                    succ = succ + i;
                    String to = taskResult.substring(in);
                    taskNew.setTaskResult(succ + to);
                    taskNew.setTaskError(sendErr);
                    taskNew.setTaskStatus(1);
                    taskService.updateTaskInfo(taskNew);
                    map.put("code", Global.CODE_SUCCESS);
                }else {
                    // 不存在下发失败的信息
                    map.put("code", Global.CODE_TASK_STARTERR_EXCEPTION);
                }
            }else{
                this.createTaskResult(task.getTaskGuid(),"NULL","NULL",21);
                logger.info("[task.api]-[post]-[/api/task/startErrTask]device is null");
            }
        }catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.error("[task.api]-[post]-[/api/task/startErrTask] CODE_DB_QUERY_EXCEPTION exception:" + e.getMessage());
        }
        return map;
    }

    /**
     * 名单下发---调用hk接口实现到闸机的名单下发任务
     * @param task
     * @param deviceId
     * @param typeId
     */
    public void sendPerNew(Task task, Integer deviceId, Integer typeId){
        logger.info("[task.api]-[sendPerNew] method have been called.");
        Device device = new Device();
        device.setDeviceId(deviceId);
        Device deviceNew = deviceService.queryDeviceById(device);
        String sendErr = ","; // 存储下发失败人员信息
        int i = 0; // 计数下发成功人数信息
        List<TaskErrInfo> errInfoList = new ArrayList<>();
        if (deviceNew != null) {
            logger.info("[task.api]-[sendPerNew] deviceIP :"+deviceNew.getDeviceip());
            if (typeId == -100 || typeId == -200) { // 今日来访and历史来访
                List<Visitor> visitorList = new ArrayList<>();
                if (typeId == -100) { // 今日来访
                    logger.info("[task.api]-[sendPerNew]Type100 send today visit");
                    visitorList = visitorService.queryAllVisitor();
                }else{ // 历史来访
                    logger.info("[task.api]-[sendPerNew]Type200 send histoty visit");
                    Visitor visitor = new Visitor();
                    visitor.setIsToday("yes");
                    visitorList =  visitorService.queryVistorByDate(visitor);
                }
                if (visitorList.size()>0) {
                    logger.info("[task.api]-[sendPerNew]visit is not null and size: " + visitorList.size());
                    for (Visitor v : visitorList) {
                        boolean is = true;
                        int resultCode = 1;
                        if (v.getVisitPicture() == null || v.getVisitPicture().equals("")||v.getVisitPicture().equals("null")){
                            resultCode = 20;
                            logger.info("[task.api]-[sendPerNew] pic is null");
                        }else{
                            String jpg = v.getVisitPicture().substring(7);
                            File pic = new File(baseUrl + pathVisit + jpg);
                            if (pic.exists()) {
                                HashMap resultInfo = hikvisonService.sendPerNew(deviceNew.getDeviceip() , v.getVisitName(),v.getVisitCardNumber(),baseUrl + pathVisit + jpg);
                                is = (boolean)resultInfo.get("is");
                                resultCode = Integer.parseInt(resultInfo.get("result").toString());
                                logger.info("[task.api]-[sendPerNew]hk send into....");
                            }else{
                                resultCode = 18;
                                logger.info("[task.api]-[sendPerNew]pic file is not exist");
                            }
                            // 如果人员信息下发失败，记录当前人员信息
                            if (is == false || resultCode == 0) {
                                logger.info("[task.api]-[sendPerNew]hk send error is=false or code=0");
                                sendErr = sendErr + v.getVisitId() + ",";
                                // 封装下发结果实体类，并入库
                                this.createTaskResult(task.getTaskGuid(),v.getVisitName(),v.getVisitCardNumber(),resultCode);
                            }else if (is == true && resultCode !=1) {
                                logger.info("[task.api]-[sendPerNew]hk send error code:"+resultCode);
                                sendErr = sendErr + v.getVisitId() + ",";
                                // 封装下发结果实体类，并入库
                                this.createTaskResult(task.getTaskGuid(),v.getVisitName(),v.getVisitCardNumber(),resultCode);
                            }else { // 如果人员信息下发成功，计数成功个数
                                logger.info("[task.api]-[sendPerNew]hk send success");
                                i = i + 1;
                            }
                        }
                    }
                    // 记录任务结果 失败个数/总个数
                    task.setTaskResult(i+"/"+visitorList.size());
                }else{
                    // 人像库中 不存在需要下发的人脸信息，记录失败
                    logger.info("[task.api]-[sendPerNew] visit is null");
                    this.createTaskResult(task.getTaskGuid(),"NULL","NULL",19);
                    task.setTaskResult("0/0");
                }
            }else {
                logger.info("[task.api]-[sendPerNew] send Personnel");
                List<Personnel> personnelList = personnelService.queryAllByType(typeId);
                if (personnelList.size() > 0) {
                    logger.info("[task.api]-[sendPerNew] Personnel is not null and size: " + personnelList.size());
                    // 循环调用hk接口，将名单信息下发到设备上
                    for (Personnel per : personnelList) {
                        boolean is = true;
                        int resultCode = 1;
                        if (per.getPerPicture() == null || per.getPerPicture().equals("null") || per.getPerPicture().equals("")){
                            resultCode = 20;
                            logger.info("[task.api]-[sendPerNew] pic is null");
                        }else{
                            String jpg = per.getPerPicture().substring(7);
                            File pic = new File(baseUrl + person + jpg);
                            if (pic.exists()) {
                                HashMap resultInfo = hikvisonService.sendPerNew(deviceNew.getDeviceip() , per.getPerName(),per.getPerNumber(),baseUrl + person + jpg);
                                is = (boolean)resultInfo.get("is");
                                resultCode = Integer.parseInt(resultInfo.get("result").toString());
                                logger.info("[task.api]-[sendPerNew]hk send into....");
                            }else{
                                resultCode = 18;
                                logger.info("[task.api]-[sendPerNew] pic is not exist");
                            }
                        }
                        // 如果人员信息下发失败，记录当前人员信息
                        if (is == false || resultCode == 0) { // 长连接错误、
                            logger.info("[task.api]-[sendPerNew]send error is=false or code=0");
                            sendErr = sendErr + per.getPerId() + ",";
                            // 入库下发失败名单信息
                            this.createTaskResult(task.getTaskGuid(),per.getPerName(),per.getPerNumber(),resultCode);
                        }else if (is == true && resultCode !=1) { // 人像下发失败
                            logger.info("[task.api]-[sendPerNew]send error code:"+resultCode);
                            sendErr = sendErr + per.getPerId() + ",";
                            // 入库下发失败名单信息
                            this.createTaskResult(task.getTaskGuid(),per.getPerName(),per.getPerNumber(),resultCode);
                        }else { // 如果人员信息下发成功，计数成功个数
                            logger.info("[task.api]-[sendPerNew]hk send success,name:"+per.getPerName()+",number:"+per.getPerNumber());
                            i = i + 1;
                        }
                    }
                    // 记录任务结果 失败个数/总个数
                    task.setTaskResult(i+"/"+personnelList.size());
                }else{
                    logger.info("[task.api]-[sendPerNew] Personnel is null");
                    // 人像库中 不存在需要下发的人脸信息，记录失败
                    this.createTaskResult(task.getTaskGuid(),"NULL","NULL",19);
                    task.setTaskResult("0/0");
                }
            }
            //存入失败id信息，更新任务信息
            task.setTaskError(sendErr);
            task.setTaskStatus(1);  // 更新下发任务状态信息
            logger.info("[task.api]-[sendPerNew]update task info into");
            taskService.updateTaskInfo(task);
        }else{
            this.createTaskResult(task.getTaskGuid(),"NULL","NULL",21);
            logger.info("[task.api]-[sendPerNew]device is null");
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
        logger.info("[task.api]-[createTaskResult] method have been called.");
        TaskResult taskResult = new TaskResult();
        taskResult.setTaskGuid(taskGuid);
        taskResult.setPerName(perName);
        taskResult.setPerNumber(perNumber);
        taskResult.setResultCode(resultCode);
        // hk 错误码转化
        taskResult.setResultInfo(HkErrorInfo.errorInfo(resultCode));
        taskResult.setTaskDate(new Date());
        taskResultService.createResult(taskResult);
        logger.info("[task.api]-[createTaskResult]info:"+perName+" and code:"+resultCode);
    }
}
