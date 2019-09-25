package com.san.platform.config;


import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CSVUtil {

    /**
     * CSV文件列分隔符
     */
    private static final String CSV_COLUMN_SEPARATOR = ",";

    /**
     * CSV文件列分隔符
     */
    private static final String CSV_RN = "\r\n";

    private final static Logger logger = Logger.getLogger(CSVUtils.class);

    /**
     * @param dataList 集合数据
     * @param colNames 表头部数据
     * @param mapKey   查找的对应数据
     * @param os       返回结果
     */
    public static boolean doExport(List<Map<String, Object>> dataList, String colNames, String mapKey, OutputStream os) {
        try {
            StringBuffer stringBuffer = Fluidization(dataList, colNames, mapKey);
            // 写出响应
            os.write(stringBuffer.toString().getBytes("UTF-8"));
            os.flush();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * setHeader
     */
    public static void responseSetProperties(String fileName, HttpServletResponse response) throws UnsupportedEncodingException {
        // 设置文件后缀
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String fn = fileName + sdf.format(new Date()) + ".csv";
        // 读取字符编码
        String utf = "UTF-8";

        // 设置响应
        response.setContentType("application/ms-txt.numberformat:@");
        response.setCharacterEncoding(utf);
        response.setHeader("Pragma", "public");
        response.setHeader("Cache-Control", "max-age=40");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fn, utf));
    }


    /**
     * 导入
     *
     * @param file csv文件(路径+文件)
     * @return
     */
    public static List<String> importCsv(File file) {
        List<String> dataList = new ArrayList<>();

        BufferedReader br = null;
        try {
            InputStreamReader isr = new InputStreamReader(
                    new FileInputStream(file), "UTF-8");
            br = new BufferedReader(isr);
            //定义读取
            br = new BufferedReader(new FileReader(file));
            //从第一行开始读取
            String line = "";
            //读取到最后一行
            while ((line = br.readLine()) != null) {
                dataList.add(line);
            }
        } catch (Exception e) {
        } finally {
            if (br != null) {
                try {
                    br.close();
                    br = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return dataList;
    }


    // 保存csv
    public static void saveCsv(List<Map<String, Object>> dataList, String colNames, String mapKey, String fileName, String savePath) {
        OutputStreamWriter ow = null;

        FileUtil.delAllFile(savePath);

        try {
            StringBuffer stringBuffer = Fluidization(dataList, colNames, mapKey);
            //判断是否有文件夹   没有就新建
            File destDi = new File(savePath);
            if (!destDi.exists()) {
                destDi.mkdir();
            }

            File file = new File(destDi, fileName);
            //构建输出流，同时指定编码
            ow = new OutputStreamWriter(new FileOutputStream(file), "GBK");
            ow.write(stringBuffer.toString());
            ow.flush();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                ow.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    //将数据转为StringBuffer
    public static StringBuffer Fluidization(List<Map<String, Object>> dataList, String colNames, String mapKey) {
        StringBuffer stringBuffer = new StringBuffer();

        String[] colNamesArr = null;
        String[] mapKeyArr = null;

        colNamesArr = colNames.split(",");
        mapKeyArr = mapKey.split(",");

        // 完成数据csv文件的封装
        // 输出列头
        for (String aColNamesArr : colNamesArr) {
            stringBuffer.append(aColNamesArr).append(CSV_COLUMN_SEPARATOR);
        }
        stringBuffer.append(CSV_RN);

        if (null != dataList) { // 输出数据
            for (Map<String, Object> aDataList : dataList) {
                for (String aMapKeyArr : mapKeyArr) {
                    stringBuffer.append(aDataList.get(aMapKeyArr)).append(CSV_COLUMN_SEPARATOR);
                }
                stringBuffer.append(CSV_RN);
            }
        }
        return stringBuffer;
    }

    /**
     * 白鹏
     * 数据初始化
     *
     * @param data            数据库查出来的数据
     * @param displayColNames csv表头
     * @param matchColNames   data中的key ，可以说是数据库字段了,原本为”0001”类型的数据在excel中打开会被默认改变为”1”的数据。 解决方法 :key前加"'"用于特殊处理；
     * 例如输入列名为"num"数字为 001，则传入的key值为"-num",保证输出为字符串
     * @return
     */
    public static String formatCsvData(List<Map<String, Object>> data,
                                       String displayColNames, String matchColNames) {

        StringBuffer buf = new StringBuffer();

        String[] displayColNamesArr = null;
        String[] matchColNamesMapArr = null;

        displayColNamesArr = displayColNames.split(",");
        matchColNamesMapArr = matchColNames.split(",");

        // 输出列头
        for (int i = 0; i < displayColNamesArr.length; i++) {
            buf.append(displayColNamesArr[i]).append(CSV_COLUMN_SEPARATOR);
        }
        buf.append(CSV_RN);

        if (null != data) {
            // 输出数据
            for (int i = 0; i < data.size(); i++) {

                for (int j = 0; j < matchColNamesMapArr.length; j++) {
                    //处理list<Map>中 value=null的数据
                    Object object = data.get(i).get(matchColNamesMapArr[j]);
                    if (object == null) {
                        object = data.get(i).get(matchColNamesMapArr[j].substring(1));
                    }
                    if (object == null) {
                        buf.append(CSV_COLUMN_SEPARATOR);
                    } else {
                        if (matchColNamesMapArr[j].startsWith("-")) {
                            buf.append("\t" + object.toString()).append(CSV_COLUMN_SEPARATOR);
                        } else {
                            buf.append(object).append(CSV_COLUMN_SEPARATOR);
                        }
                    }
                }
                buf.append(CSV_RN);
            }
        }
        logger.info("csv file Initialize successfully");
        return buf.toString();
    }

    /**
     * 导出
     *
     * @param fileName 文件名
     * @param content  内容
     * @param request
     * @param response
     * @throws IOException
     */
    public static void exportCsv(String fileName, String content, HttpServletRequest request,
                                 HttpServletResponse response) throws IOException {

        // 读取字符编码
        String csvEncoding = "UTF-8";

        // 设置响应
        response.setCharacterEncoding(csvEncoding);
        response.setContentType("text/csv; charset=" + csvEncoding);
        response.setHeader("Pragma", "public");
        response.setHeader("Cache-Control", "max-age=30");

        final String userAgent = request.getHeader("USER-AGENT");
        if (StringUtils.contains(userAgent, "MSIE")) {//IE浏览器
            fileName = URLEncoder.encode(fileName, "UTF8");
        } else if (StringUtils.contains(userAgent, "Mozilla")) {//google,火狐浏览器
            fileName = new String(fileName.getBytes(), "ISO8859-1");
        } else {
            fileName = URLEncoder.encode(fileName, "UTF8");//其他浏览器
        }
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

        // 写出响应
        OutputStream os = response.getOutputStream();
        os.write(content.getBytes("GBK"));
        os.flush();
        os.close();
        logger.info("csv file download completed");
    }


}
