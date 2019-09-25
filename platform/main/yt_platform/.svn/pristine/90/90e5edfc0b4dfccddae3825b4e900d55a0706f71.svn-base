package com.san.platform.innerlog.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.san.platform.BaseController;
import com.san.platform.config.*;
import com.san.platform.innerlog.annotation.MyLog;
import com.san.platform.innerlog.Innerlog;
import com.san.platform.innerlog.InnerlogService;
import com.san.platform.visitor.Visitor;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipOutputStream;

@RestController
public class InnerController extends BaseController {
    @Autowired
    HttpServletResponse response;
    @Autowired
    HttpServletRequest request;
    @Reference
    private InnerlogService innerlogService;
    private String tempFilePah = "D:/tempPic/";

    /**
     * @MyLog(value = "Innerlog")
     * 自定义注解
     * 将需要录入审计的方法上标明注解
     * 使用方法 @MyLog(value = "方法体")
     */
    //查询列表所有信息
    @GetMapping("/api/innerlog/queryAllInnerlog")
    public HashMap<String, Object> queryAllInnerlog(Innerlog innerlog) {
        logger.info("[innerlog.api]-[get]-[/api/innerlog/queryAllInnerlog] method have been called.");
        HashMap<String, Object> map = new HashMap<>();

        try {
            // 调用高级筛选方法
            map = innerlogService.queryInnerlogByTime(innerlog);
            map.put("code", Global.CODE_SUCCESS);
        } catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[innerlog.api]-[get]-[/api/innerlog/queryAllInnerlog] CODE_DB_QUERY_EXCEPTION exception:" + e.getMessage());
        }
        return map;
    }

    /**
     * 查询列表所有信息根据起始时间
     *
     * @return
     */
    @MyLog("Innerlog")
    @GetMapping("/api/innerlog/queryInnerlogByTime")
    public HashMap<String, Object> queryInnerlogByTime(Innerlog innerlog) {
        logger.info("[innerlog.api]-[get]-[/api/innerlog/queryInnerlogByTime] method have been called.");
        HashMap<String, Object> map = new HashMap<>();

        try {
            map = innerlogService.queryInnerlogByTime(innerlog);
            map.put("code", Global.CODE_SUCCESS);
        } catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[innerlog.api]-[get]-[/api/innerlog/queryInnerlogByTime] CODE_DB_QUERY_EXCEPTION exception,sel_all:" + e.getMessage());
        }
        return map;
    }

    /**
     * 添加列表
     *
     * @return
     */
    @RequestMapping("/api/innerlog/createInnerlog")
    public HashMap<String, Object> createInnerlog(@RequestBody Innerlog innerlog) {
        logger.info("[innerlog.api]-[get]-[/api/innerlog/createInnerlog] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            map = innerlogService.createInnerlog(innerlog);
            map.put("code", Global.CODE_SUCCESS);
        } catch (Exception e) {
            map.put("code", Global.CODE_DATA_INSERT_EXCEPION);
            logger.info("[innerlog.api]-[get]-[/api/innerlog/createInnerlog] CODE_DATA_INSERT_EXCEPION exception,sel_all:" + e.getMessage());
        }
        return map;
    }

    /**
     * 导出文件
     */
    @GetMapping("/api/innerlog/downLoadInnerlogCSV")
    @ResponseBody
    public void downLoadInnerlogCSV(@RequestParam(value = "startTime", required = false)String startTime,
                                    @RequestParam(value = "endTime", required = false)String endTime) {
        logger.info("[innerlog.api]-[post]-[/api/visitor/downLoadInnerlogCSV] method have been called.");
        String start = "";
        String end = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (!startTime.equals("null")&&("".equals(startTime)==false)){
            long lt = new Long(startTime);
            Date date = new Date(lt);
            start = simpleDateFormat.format(date);
        }
        if (!endTime.equals("null")&&("".equals(endTime)==false)){
            long lt = new Long(endTime);
            Date date = new Date(lt);
            end = simpleDateFormat.format(date);
        }

        //查询所有信息
        System.out.println(endTime);
        List<Innerlog> innerlogs = innerlogService.queryAllInnerlogTwo(start,end);
        // csv 信息
        String fileName = "操作审计列表信息" + DateUtil.putDateToTimeStr10(new Date()) + ".csv";
        String sTitle = "列表ID,日志级别,操作人,操作人角色,操作类型,操作实体,操作内容,操作时间,操作客户端ip";
        String mapKey = "logId,logLevel,opName,opRole,opType,opModel,opInfo,opTime,clientIp";

        // 存放要保存的信息
        List<Map<String, Object>> dataList = new ArrayList<>();
        Map<String, Object> map = null;
        for (Innerlog order : innerlogs) {
            map = new HashMap<>();
            String[] str = order.getOpInfo().split(",");
            String opInfo = "";
            for(int i = 0; i<str.length; i++){
                opInfo += str[i] + " ";
            }
            map.put("logId", order.getLogId());
            map.put("logLevel", order.getLogLevel());
            map.put("opName", order.getOpName());
            map.put("opRole", order.getOpRole());
            map.put("opType", order.getOpType());
            map.put("opModel", order.getOpModel());
            map.put("opInfo", opInfo);
            if (order.getOpTime() != null) {
                map.put("opTime", DateFormatUtils.format(order.getOpTime(), "yyyy/MM/dd HH:mm"));
            }
            map.put("clientIp", order.getClientIp());

            dataList.add(map);
        }


        String content = CSVUtil.formatCsvData(dataList, sTitle, mapKey);
        try {
            CSVUtil.exportCsv(fileName,content,request,response);
        } catch (IOException e) {
           e.printStackTrace();
         }


    }
}
