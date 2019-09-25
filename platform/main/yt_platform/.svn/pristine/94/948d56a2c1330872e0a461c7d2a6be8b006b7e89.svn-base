package com.san.platform.display.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.san.platform.display.Display;
import com.san.platform.display.DisplayService;
import com.san.platform.display.mapper.DisplayMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class DisplayServiceImpl implements DisplayService {


    @Autowired
    private DisplayMapper displayMapper;


    @Override
    public HashMap<String, Object> queryVisitNum() {
        List<Display> enterHours = displayMapper.queryEnterHours();
        List<Display> leaveHours = displayMapper.queryLeaveHours();
        List<Display> causeList = displayMapper.queryCauseNumber();

        HashMap<String, Integer> enterHoursMap = new HashMap<>();
        HashMap<String, Integer> leaveHoursMap = new HashMap<>();
        HashMap<String, Integer> causeNumberMap = new HashMap<>();

        for (Display enterHour : enterHours) {
            enterHoursMap.put(enterHour.getHours(), enterHour.getNumber());
        }
        for (Display leaveHour : leaveHours) {
            leaveHoursMap.put(leaveHour.getHours(), leaveHour.getNumber());
        }
        for (Display causeNumber : causeList) {
            causeNumberMap.put(causeNumber.getVisitCause(),causeNumber.getNumber());
        }


        List<Integer> enterHoursList = new ArrayList<>();
        List<Integer> leaveHoursList = new ArrayList<>();
        List<Integer> causeNumberList = new ArrayList<>();
        List<String> causeNameList = new ArrayList<>();


        //事由
        for (Display cause : causeList) {
            causeNameList.add(cause.getVisitCause());
            causeNumberList.add(cause.getNumber());
        }
        String[] time = {"07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18"};
//        时间
        for (String s : time) {
            if (enterHoursMap.get(s) != null) {
                enterHoursList.add(enterHoursMap.get(s));
            } else {
                enterHoursList.add(0);
            }
            if (leaveHoursMap.get(s) != null) {
                leaveHoursList.add(leaveHoursMap.get(s));
            } else {
                leaveHoursList.add(0);
            }
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("enterHoursList",enterHoursList);
        map.put("leaveHoursList",leaveHoursList);
        map.put("causeNumberList",causeNumberList);
        map.put("causeNameList",causeNameList);
        return map;
    }

//    @Override
//    public HashMap<String, Integer> queryNumber() {
//        HashMap<String,Integer> map = new HashMap<>();
//        map.put("enterNumber",displayMapper.queryEnterNumber());
//        map.put("leaveNumber",displayMapper.queryLeaveNumber());
//        return map;
//    }
}
