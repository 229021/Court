package com.san.platform.alert.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.san.platform.alert.Alert;
import com.san.platform.alert.AlertList;
import com.san.platform.alert.AlertListService;
import com.san.platform.alert.PersonType;
import com.san.platform.alert.mapper.AlertListMapper;
import com.san.platform.alert.mapper.AlertMapper;
import com.san.platform.alert.mapper.PersonTypeMapper;
import com.san.platform.config.Global;
import com.san.platform.personnel.Personnel;
import com.san.platform.personnel.mapper.PersonnelMapper;
import com.san.platform.region.Region;
import com.san.platform.visitor.Visitor;
import com.san.platform.visitor.mapper.VisitorMapper;
import com.san.platform.websockt.MyWebSocket;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.*;

/**
 * @Author: Zhouzhongqing
 * @Date: 2019/4/26 11:02
 * @Description: 告警列表  接口实现类
 */
@Service
public class AlertListServiceImpl implements AlertListService {

    @Autowired
    private AlertListMapper alertListMapper;

    @Autowired
    private AlertMapper alertMapper;

    @Autowired
    private VisitorMapper visitorMapper;

    @Autowired
    private PersonnelMapper personnelMapper;

    @Autowired
    private PersonTypeMapper personTypeMapper;
    @Reference
//    @Reference
    private MyWebSocket myWebSocket;


    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/5/7 16:11
     * @Description: 查询告警列表 + 分页
     */
    @Override
    public HashMap<String, Object> queryAlertListPage(AlertList alertList) {
        HashMap<String, Object> map = new HashMap<>();
        //当前页 pageNum 与 显示条数 pageSize分页
        PageHelper.startPage(alertList.getPageNum(), Global.PAGESIZE);
        //调用查询方法
        List<AlertList> list = alertListMapper.queryAlertListPage(alertList);
        PageInfo pageInfo = new PageInfo(list);
        //将List与相关的page信息传入map
        map.put("pages", new Integer(pageInfo.getPages()));
        map.put("total", pageInfo.getTotal());
        map.put("results", list); // 结果
        return map;
    }

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/5/9 11:32
     * @Description: 根据id查
     */
    @Override
    public List<AlertList> queryAlertListById(List<Integer> alertIds) {
        ArrayList<AlertList> list = new ArrayList<>();
        for (Integer id : alertIds) {
            System.out.println(id);
            list.add(alertListMapper.selectByPrimaryKey(id));
        }
        return list;
    }


    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/5/9 11:33
     * @Description: 查询全部
     */
    @Override
    public List<AlertList> queryAlertList() {
//        return alertListMapper.queryAlertList(alertList);
        return alertListMapper.selectAll();
    }


    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/5/7 16:11
     * @Description: 添加告警列表
     */
    @Override
    public int createAlertList(AlertList alertList) {
        return alertListMapper.insertSelective(alertList);
    }

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/5/7 16:11
     * @Description: 删除选中多个
     */
    @Override
    public int removeAlertList(Integer alertId) {
        return alertListMapper.deleteByPrimaryKey(alertId);
    }

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/5/7 16:12
     * @Description: 删除选中单个
     */
    @Override
    public int removeByAlertListId(AlertList alertId) {
        return alertListMapper.deleteByPrimaryKey(alertId);
    }


    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/5/13 11:42
     * @Description: 导出选中
     */

    @Override
    public List<AlertList> queryAlertLists(List<Integer> alertIds) {
        ArrayList<AlertList> list = new ArrayList<>();
        for (Integer id : alertIds) {
            list.add(alertListMapper.queryAlertLists(id));
        }
        return list;
    }


    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/5/20 13:47
     * @Description: 修改人员类型
     */
    @Override
    public void updatePersonnelTypes(PersonType personnel) {
        alertListMapper.updatePersonnelTypes(personnel);
    }

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/7/10 11:41
     * @Description: 按身份证号查询人像库
    */
    @Override
    public Personnel queryPersonnelByNumber(String string) {
        return alertListMapper.queryPersonnelByNumber(string);
    }


    @Override
    public void alarmwindow(String deviceIP, String number, String small, String big) {
        try {
            HashMap<Object, Object> map = new HashMap<>();
//            // 接收传过来的数据
//            Visitor visitor = new Visitor();
//            visitor = visitorMapper.queryVisitorByIDNoToday(number);
////        Visitor visitor1 = new Visitor();
//            // 判断查询的对象是否为空
//            if (visitor != null && !"".equals(visitor)) {
                // 不为空 用身份证号查人像库
                Personnel personnel = new Personnel();
//            personnel = alertListService.queryPersonnelByNumber(visitor1.getVisitCardNumber());
//                personnel = alertListMapper.queryPersonnelByNumber(visitor.getVisitCardNumber());
            personnel = alertListMapper.queryPersonnelByNumber(number);
                // 判断查询的对象是否为空
                if (personnel != null && !"".equals(personnel)) {
                    // 如果查到了看他的类型和区域  是否符合告警规则
                    Integer integer = personnel.getPerType();  // 这个人的类型
                    // 查询所有告警规则 list
                    Alert alert = new Alert();
//                List<Alert> list = alertListService.selectAlertAll(alert);
                    List<Alert> list = alertMapper.selectAll();
                    // 遍历所有告警规则
                    for (Alert alert1 : list) {
                        // 获取场所
                        String[] place = alert1.getAlertPlacestrr().split(",");
                        // 遍历场所
                        for (String s : place) {
                            // 根据球机IP 查询所在区域
//                        Integer id = alertListService.selectRegionById("球机IP");
                            Region region = alertListMapper.selectRegion(deviceIP);
//                            Integer.valueOf(s).intValue();
                            // 判断所在区域是否和规则区域匹配
                            if (region.getRegionId() == Integer.parseInt(s)) {
                                // 获取黑名单
                                String[] blackList = alert1.getBlackListstrr().split(",");
                                // 遍历黑名单
                                for (String s1 : blackList) {
                                    // 判断所在的库 是否为黑名单
                                    if (integer == Integer.parseInt(s1)) {
                                        // 入库
                                        PersonType personType = personTypeMapper.Getbyid(personnel.getPerType());
                                        AlertList alertList1 = new AlertList();
                                        alertList1.setPerName(personnel.getPerName());
                                        alertList1.setPerSex(personnel.getPerSex());
                                        alertList1.setPerNumber(personnel.getPerNumber());
                                        alertList1.setPerPicture(personnel.getPerPicture());
                                        // 类型Id
                                        alertList1.setPerType(personType.getTypeId());
                                        // 类型的Name
                                        alertList1.setTypeName(personType.getTypeName());
                                        alertList1.setAlertPlace(region.getRegionName());
                                        alertList1.setAlertName(alert1.getAlertName());
                                        alertList1.setPerUUID(personnel.getPerUUID());
                                        alertList1.setAlertCreateTime(new Date());
                                        alertList1.setAlertModTime(new Date());
//                                    alertListService.createAlertList(alertList1);
                                        alertListMapper.createAlertList(alertList1);
                                        System.out.println("入库");
                                        // 弹窗
                                        // 将java对象转换成字符串
                                        String alertList = JSONObject.toJSONString(alertList1);
                                        Integer signCode = 107;
                                        try {
                                            map.put("small", small);
                                            map.put("big", big);
                                            map.put("alertList", alertList);
                                            map.put("signCode",signCode);
                                            String photo = JSON.toJSONString(map);
                                            myWebSocket.sendInfo(photo);
                                            System.out.println("/*-/-*/*-/*/" + photo);
                                            break;
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
//                            break;
                        }
                    }
                } else {   /*----- 人像库没有查到 -----*/
                    // 用身份证号查今日来访
                    Visitor visitor1 = new Visitor();
//            visitor1 = visitorService.queryVisitorByIDNoToday(visitor1.getVisitCardNumber());
                    visitor1 = visitorMapper.queryVisitorByIDNoToday(number);
                    // 如果查到了 看他所在的区域  告警规则
                    if (visitor1 != null && !"".equals(visitor1)) {
                        // 黑名单 弹窗 入库
                        Alert alert = new Alert();
                        // 查询所有告警规则
//                List<Alert> list = alertListService.selectAlertAll(alert);
                        List<Alert> list = alertMapper.selectAll();
                        // 遍历所有告警规则
                        for (Alert alert1 : list) {
                            // 获取场所
                            String[] place = alert1.getAlertPlacestrr().split(",");
                            // 遍历场所
                            for (String s : place) {
                                // 根据球机IP 查询所在的区域
//                        Integer id = alertListService.selectRegionById("球机IP");
                                Region region = new Region();
                                region = alertListMapper.selectRegion(deviceIP);
                                // 判断所在区域是否和规则区域匹配
                                if (region.getRegionId() == Integer.parseInt(s)) {
                                    // 获取黑名单 查询今日访客库是否在黑名单下
                                    Integer integer = -100;
                                    // *************根据类型查询出类型所对应的汉字****************
                                    String[] blackList = alert1.getBlackListstrr().split(",");
                                    // 遍历黑名单
                                    for (String s1 : blackList) {
                                        if (integer == Integer.parseInt(s1)) {
                                            // 入库
                                            PersonType personType = personTypeMapper.Getbyid(integer);
                                            AlertList alertList1 = new AlertList();
                                            alertList1.setPerName(visitor1.getVisitName());
                                            alertList1.setPerSex(visitor1.getVisitSex());
                                            alertList1.setPerNumber(visitor1.getVisitCardNumber());
                                            alertList1.setPerPicture(visitor1.getVisitPicture());
                                            alertList1.setAlertPlace(region.getRegionName());
                                            alertList1.setAlertName(alert1.getAlertName());
                                            alertList1.setAlertCreateTime(new Date());
                                            alertList1.setAlertModTime(new Date());
                                            alertList1.setPerType(personType.getTypeId());
                                            alertList1.setTypeName(personType.getTypeName());
                                            alertList1.setPerUUID(UUID.randomUUID().toString());
//                                    alertListService.createAlertList(alertList1);
                                            alertListMapper.createAlertList(alertList1);
                                            System.out.println("入库");
                                            // 弹窗
                                            // 将java对象转换成字符串
                                            String alertList = JSONObject.toJSONString(alertList1);
                                            Integer signCode = 107;
                                            try {
                                                map.put("small", small);
                                                map.put("big", big);
                                                map.put("alertList", alertList);
                                                map.put("signCode",signCode);
                                                String photo = JSON.toJSONString(map);
                                                myWebSocket.sendInfo(photo);
                                                System.out.println("/*-/-*/*-/*/" + photo);
                                                break;
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public PersonType Getbyid(Integer getbyid) {
        return personTypeMapper.Getbyid(getbyid);
    }


    @Override
    public List<AlertList> selectAll() {
        return alertListMapper.selectAlertList();
    }

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/6/10 16:51
     * @Description: 根据球机IP 查询设备信息
     */
    @Override
    public Region selectRegionById(String deviceIP) {
        return alertListMapper.selectRegion(deviceIP);
    }


}
