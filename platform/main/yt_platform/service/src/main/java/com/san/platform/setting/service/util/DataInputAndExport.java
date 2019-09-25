package com.san.platform.setting.service.util;

import com.san.platform.Exception.CustomException;
import com.san.platform.config.DateUtil;
import com.san.platform.config.Global;

import java.io.*;
import java.util.Date;

public class DataInputAndExport {
    public static String backup(String saveFilePath, String ip, String user, String pwd, String databases) throws CustomException {
        String fileName = null;

        try {
            Runtime rt = Runtime.getRuntime();

            // 调用 调用mysql的安装目录的命令
            Process child = rt
                    .exec("mysqldump -h" + ip + " -u" + user + " -p" + pwd + " --databases " + databases);
            // 设置导出编码为utf-8。这里必须是utf-8
            // 把进程执行中的控制台输出信息写入.sql文件，即生成了备份文件。注：如果不对控制台信息进行读出，则会导致进程堵塞无法运行
            InputStream inputStream = child.getInputStream();// 控制台的输出信息作为输入流

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            // 设置输出流编码为utf-8。这里必须是utf-8，否则从流中读入的是乱码

            String inStr;
            StringBuffer stringBuffer = new StringBuffer("");
            String outStr;
            // 组合控制台输出信息字符串
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            Aes aes = new Aes();
            while ((inStr = bufferedReader.readLine()) != null) {
                String encrypt = aes.encrypt(inStr,"xkp0000000000000");
                System.out.println(encrypt);
                stringBuffer.append(encrypt+"\r\n");
            }
            outStr = stringBuffer.toString();
            fileName = DateUtil.putDateToTimeStr10(new Date()) + ".sql";
            File dest = new File(saveFilePath+fileName);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();// 新建文件夹
            }
            // 要用来做导入用的sql目标文件：
            FileOutputStream fileOutputStream = new FileOutputStream(dest);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "utf-8");
            outputStreamWriter.write(outStr);
            outputStreamWriter.flush();
            inputStream.close();
            bufferedReader.close();
            inputStreamReader.close();
            outputStreamWriter.close();
            fileOutputStream.close();
        } catch (IOException e) {
            throw new CustomException(Global.CODE_IO_FlOW, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return saveFilePath+fileName;
    }


    public static void restore(String FilePath, String ip, String user, String pwd) throws CustomException {
        try {


            System.out.println(FilePath);
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime
                    .exec("mysql -h" + ip + " -u" + user + " -p" + pwd + " --default-character-set=utf8 "
                            + "mysql");
            OutputStream outputStream = process.getOutputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(FilePath), "utf-8"));
            String outStr;
            String inStr;
            StringBuffer stringBuffer = new StringBuffer();
            Aes aes = new Aes();
            while ((inStr = bufferedReader.readLine()) != null) {
                String encrypt = aes.decrypt(inStr,"xkp0000000000000");
                System.out.println(encrypt);
                stringBuffer.append(encrypt+"\r\n");
            }
            outStr = stringBuffer.toString();
            // System.out.println(str);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream,
                    "utf-8");
            outputStreamWriter.write(outStr);
            outputStreamWriter.flush();
            outputStream.close();
            bufferedReader.close();
            outputStreamWriter.close();


        } catch (UnsupportedEncodingException e) {
            throw new CustomException(Global.CODE_DATA_UNSUPPORT_EDENCODING_EXCEPTION, e.getMessage());
        } catch (FileNotFoundException e) {
            throw new CustomException(Global.CODE_DATA_FILE_NOTFOUND_EXCEPTION, e.getMessage());
        } catch (IOException e) {
            throw new CustomException(Global.CODE_IO_FlOW, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
