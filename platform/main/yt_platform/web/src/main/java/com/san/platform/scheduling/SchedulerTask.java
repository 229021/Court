package com.san.platform.scheduling;
import com.alibaba.dubbo.config.annotation.Reference;
import com.san.platform.BaseController;
import com.san.platform.device.Device;
import com.san.platform.device.DeviceService;
import com.san.platform.hikvision.HikvisonService;
import com.san.platform.personnel.PersonnelService;
import com.san.platform.setting.Setting;
import com.san.platform.setting.SettingService;
import com.san.platform.task.Task;
import com.san.platform.task.TaskService;
import com.san.platform.visitor.Visitor;
import com.san.platform.visitor.VisitorService;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import java.time.LocalDateTime;
import java.util.List;

@Component
@Configurable
@EnableScheduling
@EnableAsync
@Controller
public class SchedulerTask extends BaseController {

    @Reference
    private SettingService settingService;

    @Reference
    private VisitorService visitorService;

    @Reference
    private HikvisonService hikvisonService;

    @Reference
    private DeviceService deviceService;

    @Reference
    private PersonnelService personnelService;

    @Reference
    private TaskService taskService;

    /**
     * 删除今日、历史来访数据---每天中午12点执行
     */
    @Async
    @Scheduled(cron = "0 0 12 * * ?")
    public void dealTodayInfo() {
        logger.info("[SchedulerTask]dealTodayInfo is running : " + LocalDateTime.now().toLocalTime() + "\r\nThread : " + Thread.currentThread().getName());
        try {
            List<Task> tasks100 = taskService.queryTaskByType(-100);
            if (tasks100 != null) {
                logger.info("[SchedulerTask]dealTodayInfo tasks100 size"+tasks100.size());
                Setting setting = new Setting();
                setting.setSetKey("todaySave");
                Setting todaySave = settingService.queryByKey(setting);
                if (todaySave!=null) {
                    logger.info("[SchedulerTask]dealTodayInfo tasks100 todaySave is"+todaySave.getSetValue());
                    if (todaySave.getSetValue().equals("0.5")) {
                        this.deleteTodayInfo(tasks100);
                    }
                }
            }else {
                logger.info("[SchedulerTask]dealTodayInfo tasks100 is NULL");
            }
            //删除历史来访
            List<Task> tasks200 = taskService.queryTaskByType(-200);
            if (tasks200 != null) {
                logger.info("[SchedulerTask]dealTodayInfo tasks200 size"+tasks200.size());
                this.deleteHistoryInfo(tasks200);
            }else{
                logger.info("[SchedulerTask]dealTodayInfo tasks200 is NULL");
            }
        } catch (Exception e) {
            logger.warn("[SchedulerTask]dealTodayInfo Exception: "+e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 删除今日、历史来访数据---每天晚上12点执行
     */
    @Async
    @Scheduled(cron = "0 0 0 * * ?")
    public void dealTodayInfo2() {
        logger.info("[SchedulerTask]dealTodayInfo is running : " + LocalDateTime.now().toLocalTime() + "\r\nThread : " + Thread.currentThread().getName());
        try {
            // 删除今日来访
            List<Task> tasks100 = taskService.queryTaskByType(-100);
            if (tasks100 != null) {
                logger.info("[SchedulerTask]dealTodayInfo2 tasks100 size"+tasks100.size());
                this.deleteTodayInfo(tasks100);
            }else{
                logger.info("[SchedulerTask]dealTodayInfo2 tasks100 is NULL");
            }
            //删除历史来访
            List<Task> tasks200 = taskService.queryTaskByType(-200);
            if (tasks200 != null) {
                logger.info("[SchedulerTask]dealTodayInfo2 tasks200 size"+tasks200.size());
                this.deleteHistoryInfo(tasks200);
            }
        } catch (Exception e) {
            logger.warn("[SchedulerTask]dealTodayInfo Exception: "+e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 删除今日来访数据--调用hk服务
     */
    public void deleteTodayInfo (List<Task> tasks) {
        logger.info("[SchedulerTask]deleteTodayInfo is running");
        if (tasks.size() > 0) {
            for (Task task : tasks) {
                // 得到当前设备信息
                Device device = new Device();
                device.setDeviceId(task.getTaskDeviceId());
                device = deviceService.queryDeviceById(device);
                if (device != null) {
                    logger.info("[SchedulerTask]deleteTodayInfo deviceIP is "+device.getDeviceip());
                    // 查询今日来访
                    Visitor v = new Visitor();
                    v.setIsToday("yes");
                    List<Visitor> visitorList =  visitorService.queryVistorByDate(v);
                    if (visitorList.size() > 0) {
                        logger.info("[SchedulerTask]deleteTodayInfo visitorList size is "+visitorList.size());
                        for (Visitor visitor : visitorList) {
                            int count = personnelService.queryCountByNumber(v.getVisitCardNumber());
                            if (count == 0) {
                                logger.info("[SchedulerTask]deleteTodayInfo is no in personnel,deal deleteTask");
                                boolean result = hikvisonService.deleteTask(device.getDeviceip(),visitor.getVisitCardNumber());
                                logger.info("[SchedulerTask]deleteTodayInfo result1 is "+ result+",and number is "+ visitor.getVisitCardNumber());
                                // 失败执行三次
                                if (result == false) {
                                    result = hikvisonService.deleteTask(device.getDeviceip(),visitor.getVisitCardNumber());
                                    logger.info("[SchedulerTask]deleteTodayInfo result2 is "+ result+",and number is "+ visitor.getVisitCardNumber());
                                    if (result == false) {
                                        result = hikvisonService.deleteTask(device.getDeviceip(),visitor.getVisitCardNumber());
                                        logger.info("[SchedulerTask]deleteTodayInfo result3 is "+ result+",and number is "+ visitor.getVisitCardNumber());
                                        if (result == false) {
                                            result = hikvisonService.deleteTask(device.getDeviceip(),visitor.getVisitCardNumber());
                                            logger.info("[SchedulerTask]deleteTodayInfo result4 is "+ result+",and number is "+ visitor.getVisitCardNumber());
                                        }
                                    }
                                }
                            }
                        }
                    }else{
                        logger.info("[SchedulerTask]deleteTodayInfo visitorList is NULL");
                    }
                }
            }
        }else {
            logger.info("[SchedulerTask]deleteTodayInfo tasks size is "+ tasks.size());
        }
    }

    /**
     * 删除历史来访数据-调用海康
     */
    public void deleteHistoryInfo (List<Task> tasks) {
        logger.info("[SchedulerTask]deleteHistoryInfo is running");
        Setting setting = new Setting();
        setting.setSetKey("historySave");
        Setting historySave = settingService.queryByKey(setting);
        if (historySave!=null) {
            logger.info("[SchedulerTask]deleteHistoryInfo historySave is"+historySave.getSetValue());
            if (tasks.size() > 0) {
                for (Task task : tasks) {
                    // 得到当前设备信息
                    Device device = new Device();
                    device.setDeviceId(task.getTaskDeviceId());
                    device = deviceService.queryDeviceById(device);
                    if (device != null) {
                        logger.info("[SchedulerTask]deleteHistoryInfo deviceIP is"+device.getDeviceip());
                        Visitor visitor = new Visitor();
                        visitor.setMonth(Integer.parseInt(historySave.getSetValue()));
                        List<Visitor> visitorList = visitorService.queryOldDateByMonth(visitor);
                        if (visitorList.size()>0) {
                            logger.info("[SchedulerTask]deleteHistoryInfo visitorList size is "+visitorList.size());
                            for (Visitor v : visitorList) {
                                int count = personnelService.queryCountByNumber(v.getVisitCardNumber());
                                if (count == 0) {
                                    boolean result = hikvisonService.deleteTask(device.getDeviceip(),v.getVisitCardNumber());
                                    logger.info("[SchedulerTask]deleteHistoryInfo result1 is "+result +",and number is"+v.getVisitCardNumber());
                                    // 失败 执行三次
                                    if (result == false) {
                                        result = hikvisonService.deleteTask(device.getDeviceip(),v.getVisitCardNumber());
                                        logger.info("[SchedulerTask]deleteHistoryInfo result2 is "+result +",and number is"+v.getVisitCardNumber());
                                        if (result == false) {
                                            result = hikvisonService.deleteTask(device.getDeviceip(),v.getVisitCardNumber());
                                            logger.info("[SchedulerTask]deleteHistoryInfo result3 is "+result +",and number is"+v.getVisitCardNumber());
                                            if (result == false) {
                                                hikvisonService.deleteTask(device.getDeviceip(),v.getVisitCardNumber());
                                                logger.info("[SchedulerTask]deleteHistoryInfo result4 is "+result +",and number is"+v.getVisitCardNumber());
                                            }
                                        }
                                    }
                                }else{
                                    logger.info("[SchedulerTask]deleteHistoryInfo this visitor is in personnel and number is "+v.getVisitCardNumber());
                                }
                            }
                        }else{
                            logger.info("[SchedulerTask]deleteHistoryInfo visitorList size is < 0");
                        }
                    }
                }
            }else{
                logger.info("[SchedulerTask]deleteHistoryInfo tasks size is < 0");
            }
        }else{
            logger.info("[SchedulerTask]deleteHistoryInfo historySave is NULL");
        }
    }
}
