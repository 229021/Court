package com.san.platform.statistics.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.san.platform.BaseController;
import com.san.platform.config.CSVUtil;
import com.san.platform.config.DateUtil;
import com.san.platform.config.Global;
import com.san.platform.display.DisplayService;
import com.san.platform.option.Option;
import com.san.platform.option.OptionService;
import com.san.platform.statistics.Statistics;
import com.san.platform.statistics.StatisticsService;
import com.san.platform.visitor.Visitor;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.util.*;

@RestController
public class StatisticsController extends BaseController {

    @Autowired
    HttpServletResponse response;
    @Autowired
    HttpServletRequest request;
    @Reference
    private StatisticsService statisticsService;
    @Reference
    private DisplayService displayService;
    @Reference
    private OptionService optionService;


    @GetMapping("/api/statistics/queryStatistics")
    public HashMap<String, Object> queryEnter() {
        logger.info("[statistics.api]-[get]-[/api/statistics/queryStatistics] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
//        List<Visitor> visitorList = statisticsService.queryEnter();
            HashMap<String, Object> visitNum = displayService.queryVisitNum();
            List<Integer> enterHoursList = (List<Integer>) visitNum.get("enterHoursList");
            List<Integer> leaveHoursList = (List<Integer>) visitNum.get("leaveHoursList");
            List<Visitor> enterList = statisticsService.queryEnter();
            List<Visitor> leaveList = statisticsService.queryLeave();
            List<Option> causeList = optionService.queryAllOption();
            map.put("enterCount", enterList);
            map.put("leaveCount", leaveList);
            map.put("enter", enterHoursList);
            map.put("leave", leaveHoursList);
            map.put("causeList", causeList);
            map.put("visitNum", visitNum);
            map.put("code", Global.CODE_SUCCESS);
        } catch (Exception e) {
            logger.warn("[statistics.api]-[get]-[/api/statistics/queryStatistics]  " +
                    "CODE = " + Global.CODE_DB_QUERY_EXCEPTION + "," +
                    "exception message:" + e.getMessage());
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
        }
        return map;
    }

    @RequestMapping("/api/statistics/StatisticsExcel")
    public void StatisticsExcel() {
//        HashMap<String, Object> map = new HashMap<>();
        logger.info("[statistics.api]-[get]-[/api/statistics/StatisticsExcel] method have been called.");
        try {
            HashMap<String, Object> visitNum = displayService.queryVisitNum();
            List<Integer> enterHoursList = (List<Integer>) visitNum.get("enterHoursList");
            List<Integer> leaveHoursList = (List<Integer>) visitNum.get("leaveHoursList");
//            String[] strTime = new String[]{"07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00"};
            Date date = new Date();
            String format = DateFormatUtils.format(date, "yyyy/MM/dd");
            String[] strTime = {format + " " + "07:00", format + " " + "08:00", format + " " + "09:00", format + " " + "10:00", format + " " + "11:00", format + " " + "12:00", format + " " + "13:00", format + " " + "14:00", format + " " + "15:00", format + " " + "16:00", format + " " + "17:00", format + " " + "18:00"};
            List listarr = Arrays.asList(strTime);
            String fileName = "今日进出场统计" + DateUtil.putDateToTimeStr10(new Date()) + ".csv";
            String title = "进出时间,进场人数,出场人数";
            String mapKey = "time,enter,leave";

            List<Map<String, Object>> list = new ArrayList<>();
            List<Statistics> statisticsList = new ArrayList<>();
            for (int i = 0; i < listarr.size(); i++) {
                Statistics statistics = new Statistics();
                statistics.setTime(listarr.get(i).toString());
                statistics.setEnter(enterHoursList.get(i));
                statistics.setLeave(leaveHoursList.get(i));
                statisticsList.add(statistics);
            }
//            Map<String,Object> map = null;
//            HashMap<String, Object> map = new HashMap<>();
            for (Statistics statistics : statisticsList) {
                Map<String, Object> map = new HashMap<>();
//                map =  new HashMap<>();
                map.put("time", statistics.getTime());
                map.put("enter", statistics.getEnter());
                map.put("leave", statistics.getLeave());
                list.add(map);
            }
            String content = CSVUtil.formatCsvData(list, title, mapKey);
            CSVUtil.exportCsv(fileName, content, request, response);
        } catch (Exception e) {
            logger.warn("[statistics.api]-[get]-[/api/statistics/StatisticsExcel]  " +
                    "CODE = " + Global.CODE_DB_QUERY_EXCEPTION + "," +
                    "exception message:" + e.getMessage());
        }
    }

    @RequestMapping("/api/statistics/StatisticsWord")
    public void StatisticsWord(HttpServletResponse response) {
        // HashMap<String, Object> map = new HashMap<>();
        logger.info("[statistics.api]-[get]-[/api/statistics/StatisticsWord] method have been called.");
        try {

            //空白文件
            XWPFDocument document = new XWPFDocument();

            //生成word文档的系统路径
            Date date = new Date();
            String format = org.apache.commons.lang.time.DateFormatUtils.format(date, "yyyy-MM-dd");
            FileOutputStream out = new FileOutputStream(new File("D:/" + "今日进出场统计" + format + ".doc"));
            //添加标题
            XWPFParagraph titleParagraph = document.createParagraph();
            //设置段落居中
            titleParagraph.setAlignment(ParagraphAlignment.CENTER);

            XWPFRun titleParagraphRun = titleParagraph.createRun();
            titleParagraphRun.setText("今日进出场统计");  // 标题
            titleParagraphRun.setColor("000000");   // 颜色
            titleParagraphRun.setFontSize(20);      // 字体大小

            //两个表格之间加个换行
            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun paragraphRun = paragraph.createRun();
            paragraphRun.setText("\r");

            //工作经历表格
            XWPFTable ComTable = document.createTable();

            //列宽自动分割
            CTTblWidth comTableWidth = ComTable.getCTTbl().addNewTblPr().addNewTblW();
            comTableWidth.setType(STTblWidth.DXA);
            comTableWidth.setW(BigInteger.valueOf(9072));

            //表格第一行
            XWPFTableRow comTableTitle = ComTable.getRow(0);
            comTableTitle.getCell(0).setText("进出时间");
            comTableTitle.addNewTableCell().setText("进场人数");
            comTableTitle.addNewTableCell().setText("出场人数");

            HashMap<String, Object> visitNum = displayService.queryVisitNum();
            List<Integer> enterHoursList = (List<Integer>) visitNum.get("enterHoursList");
            List<Integer> leaveHoursList = (List<Integer>) visitNum.get("leaveHoursList");
            Date date1 = new Date();
            String time = DateFormatUtils.format(date1, "yyyy/MM/dd");
            String[] strTime = {time + " " + "07:00", time + " " + "08:00", time + " " + "09:00", time + " " + "10:00", time + " " + "11:00", time + " " + "12:00", time + " " + "13:00", time + " " + "14:00", time + " " + "15:00", time + " " + "16:00", time + " " + "17:00", time + " " + "18:00"};
            List listarr = Arrays.asList(strTime);
            List<Statistics> statisticsList = new ArrayList<>();
            for (int i = 0; i < listarr.size(); i++) {
                Statistics statistics = new Statistics();
                statistics.setTime(listarr.get(i).toString());
                statistics.setEnter(enterHoursList.get(i));
                statistics.setLeave(leaveHoursList.get(i));
                statisticsList.add(statistics);
            }
            //表格第二行
            for (Statistics statistics : statisticsList) {
                XWPFTableRow comTableRowTwo = ComTable.createRow();
                comTableRowTwo.getCell(0).setText(statistics.getTime());
                comTableRowTwo.getCell(1).setText(statistics.getEnter().toString());
                comTableRowTwo.getCell(2).setText(statistics.getLeave().toString());
            }

            CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
            XWPFHeaderFooterPolicy policy = new XWPFHeaderFooterPolicy(document, sectPr);

            //添加页眉
            CTP ctpHeader = CTP.Factory.newInstance();
            CTR ctrHeader = ctpHeader.addNewR();
            CTText ctHeader = ctrHeader.addNewT();
            String headerText = "仰天信息";
            ctHeader.setStringValue(headerText);
            XWPFParagraph headerParagraph = new XWPFParagraph(ctpHeader, document);
            //设置为右对齐
            headerParagraph.setAlignment(ParagraphAlignment.RIGHT);
            XWPFParagraph[] parsHeader = new XWPFParagraph[1];
            parsHeader[0] = headerParagraph;
            policy.createHeader(XWPFHeaderFooterPolicy.DEFAULT, parsHeader);

            //添加页脚
            CTP ctpFooter = CTP.Factory.newInstance();
            CTR ctrFooter = ctpFooter.addNewR();
            CTText ctFooter = ctrFooter.addNewT();
            String footerText = "http://yangtianinfo.cn/";
            ctFooter.setStringValue(footerText);
            XWPFParagraph footerParagraph = new XWPFParagraph(ctpFooter, document);
            headerParagraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFParagraph[] parsFooter = new XWPFParagraph[1];
            parsFooter[0] = footerParagraph;
            policy.createFooter(XWPFHeaderFooterPolicy.DEFAULT, parsFooter);

            document.write(out);
            out.close(); // 关闭流
            logger.info("[statistics.api]-[get]-[/api/statistics/StatisticsWord] method ExportWord Success!");
            System.out.println("Success");

        } catch (Exception e) {
            logger.warn("[statistics.api]-[get]-[/api/statistics/StatisticsWord]  " +
                    "CODE = " + Global.CODE_DB_QUERY_EXCEPTION + "," +
                    "exception message:" + e.getMessage());
        }

    }

}
