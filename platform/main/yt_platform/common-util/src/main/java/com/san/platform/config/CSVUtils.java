package com.san.platform.config;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 导出 .csv格式
 */
public class CSVUtils {
    public static boolean exportCsv(File file, List<String> dataList) {
        boolean isSucess = false;

        FileOutputStream out = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        try {
            //OutputStreamWriter in_=new OutputStreamWriter(new FileOutputStream("文件名"), "gbk");
            // BOM头，用于excel识别utf-8的数据，通知excel此csv文件格式为utf-8
          /*  osw.write(new String(new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF}));*/
            //osw = new OutputStreamWriter(out, "utf-8");
            out = new FileOutputStream(file);
            //定义流在写出的时候按照jbk编码来写
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                    file), "UTF-8"), 1024);
            osw = new OutputStreamWriter(out, "UTF-8");
            bw = new BufferedWriter(osw);
            if (dataList != null && !dataList.isEmpty()) {
                for (String data : dataList) {
                    bw.append(data).append("\r");
                }
            }
            isSucess = true;
        } catch (Exception e) {
            isSucess = false;
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                    bw = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (osw != null) {
                try {
                    osw.close();
                    osw = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                    out = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return isSucess;
    }
    /**
     * 导入
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

    public static void main(String[] args) {
        File file = new File("D:/你好.csv");
        //读
        List<String> lstrs = CSVUtils.importCsv(file);
        for (String str : lstrs) {
            System.out.println(str.toString());
        }
        //写
        File ofile = new File("D:/别太放gh肆啊.csv");
        CSVUtils.exportCsv(ofile, lstrs);
    }
}