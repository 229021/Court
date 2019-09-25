package com.san.platform.display.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.san.platform.BaseController;

import com.san.platform.alert.AlertService;
import com.san.platform.alert.PersonType;
import com.san.platform.config.Global;
import com.san.platform.display.DisplayService;
import com.san.platform.option.Option;
import com.san.platform.option.OptionService;
import com.san.platform.personnel.Personnel;
import com.san.platform.personnel.PersonnelService;
import com.san.platform.visitor.Visitor;
import com.san.platform.visitor.VisitorService;
import com.san.platform.websockt.MyWebSocket;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class DisplayController extends BaseController {
    @Reference
    private DisplayService displayService;

    @Reference
    private VisitorService visitorService;
    @Reference
    private OptionService optionService;


    @Reference
    private MyWebSocket myWebSocket;


    @Reference
    private PersonnelService personnelService;

    @Reference
    private AlertService alertService;

    @RequestMapping("/api/display/queryVisit")
//    @RabbitListener(queues = "direct_queue")
    public HashMap<String, Object> test(Visitor visitor) {

        HashMap<String, Object> hashMap = new HashMap<>();
        try {
            List<Visitor> visitList = visitorService.queryVisitorDisplay(visitor);
            for (Visitor visitor1 : visitList) {
                String visitCardNumber = visitor1.getVisitCardNumber();
                if (visitCardNumber == null || "".equals(visitCardNumber)) {
                    continue;
                }
                Personnel personnel = new Personnel();
                personnel.setPerNumber(visitCardNumber);
                Personnel personnel1 = personnelService.queryPersonnelByNumber(personnel);
                if (personnel1 != null&&personnel1.getPerType()==-1) {
                    visitor1.setLawyer(true);
                }
            }
            //来访事由查询
            List<Option> options = optionService.queryAllOption();
            HashMap<String, Object> visitNum = displayService.queryVisitNum();

//            进出人数

            List<Integer> enterHoursList = (List<Integer>) visitNum.get("enterHoursList");
            List<Integer> leaveHoursList = (List<Integer>) visitNum.get("leaveHoursList");

            Integer enterNumber = 0;
            Integer leaveNumber = 0;
            for (Integer integer : enterHoursList) {
                enterNumber += integer;
            }
            for (Integer integer : leaveHoursList) {
                leaveNumber += integer;
            }
            HashMap<String, Integer> number = new HashMap<>();

            number.put("enterNumber", enterNumber);
            number.put("leaveNumber", leaveNumber);
            hashMap.put("visitAllCause",options);

            hashMap.put("VisitNum", visitNum);
            hashMap.put("Number", number);
            hashMap.put("visitList", visitList);
            hashMap.put("code", Global.CODE_SUCCESS);
        } catch (Exception e) {
            logger.warn("[display.api]-[put]-[/api/display/updateVisitCuase]  " +
                    "CODE = " + Global.CODE_DB_QUERY_EXCEPTION + "," +
                    "exception message:" + e.getMessage());

            hashMap.put("code", Global.CODE_DB_QUERY_EXCEPTION);
        }
        // System.out.println("消费者消费了队列");
        return hashMap;
    }


    @PostMapping("/api/display/updateVisitCuase")
    public HashMap<String, Object> updateVisitCuase(@RequestBody Visitor visitor) {
        logger.info("[display.api]-[put]-[/api/display/updateVisitCuase] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {

            visitorService.updateVisitCause(visitor);
            map.put("code", Global.CODE_SUCCESS);

        } catch (Exception e) {

            logger.warn("[display.api]-[put]-[/api/display/updateVisitCuase]  " +
                    "CODE = " + Global.CODE_DISPLAY_UPDATEVISITCAUSE_EXCEPTION + "," +
                    "exception message:" + e.getMessage());

            map.put("code", Global.CODE_DISPLAY_UPDATEVISITCAUSE_EXCEPTION);

        }

        return map;
    }

    @ResponseBody
    @GetMapping(value = "/api/display/queryVisitorInfo")
    public HashMap<String, Object> queryVisitorInfo(Visitor visitor){
        logger.info("[display.api]-[get]-[/api/display/queryVisitorInfo] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            map = visitorService.queryVisitorInfo(visitor);
            // map加入code信息 200
            map.put("code", Global.CODE_SUCCESS); // 数据正常，返回code 200
        } catch (Exception e){
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[display.api]-[get]-[/api/display/queryVisitorInfo] CODE_DB_QUERY_EXCEPTION exception:" + e.getMessage());
        }
        return map;
    }

    @ResponseBody
    @GetMapping(value = "/api/display/queryPerTypeByVistor")
    public HashMap<String, Object> queryPerTypeByVistor (Visitor visitor) {
        HashMap<String, Object> map = new HashMap<>();
        String typeInfo = "";
        Personnel personnel = new Personnel();
        personnel.setPerId(visitor.getVisitId());
        try {
            personnel = personnelService.queryPersonnelById(personnel);
            int type = 0;
            if (personnel == null) {
                type = -100;
            } else {
                type = personnel.getPerType();
            }
            List<PersonType> personTypes = alertService.selectPersonType();
            if (personTypes.size() > 0) {
                for (PersonType pt : personTypes) {
                    if (type == pt.getTypeId()) {
                        typeInfo = pt.getTypeName();
                        break;
                    }
                }
            }
            map.put("code", Global.CODE_SUCCESS);
            map.put("type",typeInfo);
        } catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[display.api]-[get]-[/api/display/queryPerTypeByVistor] CODE_DB_QUERY_EXCEPTION exception:" + e.getMessage());
        }
        return map;
    }

    @ResponseBody
    @GetMapping(value = "/api/display/queryVisitorLeave")
    public HashMap<String, Object> queryVisitorLeave(Visitor visitor){
        logger.info("[display.api]-[get]-[/api/display/queryVisitorLeave] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            map = visitorService.queryVisitorLeave(visitor);
            // map加入code信息 200
            map.put("code", Global.CODE_SUCCESS); // 数据正常，返回code 200
        } catch (Exception e){
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[display.api]-[get]-[/api/display/queryVisitorLeave] CODE_DB_QUERY_EXCEPTION exception:" + e.getMessage());
        }
        return map;
    }
}
