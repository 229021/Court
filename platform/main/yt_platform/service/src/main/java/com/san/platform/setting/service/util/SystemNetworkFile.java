package com.san.platform.setting.service.util;


import com.san.platform.Exception.CustomException;
import com.san.platform.config.Global;
import com.san.platform.setting.Network;

import java.io.*;

public class SystemNetworkFile {
    //更新网卡配置文件
    public static void updateLinuxNetwork(Network network) throws CustomException {
        try {
//         读
            String filePath = "/etc/sysconfig/network-scripts/" + network.getNetName();
            File file = new File(filePath);
            if (!file.exists() || !file.isFile()) {
                throw new CustomException(Global.CODE_NETWORK_FILE_DOES_NOT_EXIST,"文件" + network.getNetName() + " 不存在");
            }
            FileReader in = new FileReader(file);
            BufferedReader bufIn = new BufferedReader(in);
            // 内存流, 作为临时流
            CharArrayWriter tempStream = new CharArrayWriter();
            // 替换
            String line = null;
            while ((line = bufIn.readLine()) != null) {
                // 替换每行中, 符合条件的字符串
                if (line.indexOf("IPADDR") >= 0) {
                    line = line.replaceAll(line.substring(line.indexOf("=") + 1), network.getNetIp());
                }
                if (line.indexOf("GATEWAY") >= 0) {
                    line = line.replaceAll(line.substring(line.indexOf("=") + 1), network.getNetGateWay());
                }
                if (line.indexOf("DNS1") >= 0) {
                    line = line.replaceAll(line.substring(line.indexOf("=") + 1), network.getNetDns());
                }
                if (line.indexOf("NETMASK") >= 0) {
                    line = line.replaceAll(line.substring(line.indexOf("=") + 1), network.getNetMask());
                }
                // 将该行写入内存
                tempStream.write(line);
                // 添加换行符
                tempStream.append(System.getProperty("line.separator"));
            }
            // 关闭 输入流
            bufIn.close();
            // 将内存中的流 写入 文件
            FileWriter out = new FileWriter(file);
            tempStream.writeTo(out);
            out.close();

            String command2 = "/bin/sh " + "/etc/init.d/network restart";
            if (Runtime.getRuntime().exec(command2).waitFor() != 0) {
                throw new CustomException(Global.CODE_NETWORK_FAILURE_TO_RESTART_NETWORK_CARD_SERVICE,"重启网卡服务失败!");
            }
        } catch (IOException e) {
            throw new CustomException(Global.CODE_NETWORK_MODIFY_CONFIGURATION_FILES_EXCEPTION,e.getMessage());
        } catch (InterruptedException e) {
            throw new CustomException(Global.CODE_NETWORK_FAILURE_TO_RESTART_NETWORK_CARD_SERVICE,e.getMessage());
        }
    }

//    //删除网卡配置文件
//    public static Boolean deleteLinuxNetwork(Network network) throws CustomException {
//        String filePath = "/etc/sysconfig/network-scripts/" + network.getNetName();
//        //        判断文件是否存在
//        File file = new File(filePath);
//        if (file.exists() && file.isFile()) {
//            if (file.delete()) {
//                return true;
//            } else {
//                throw new CustomException(Global.CODE_NETWORK_FAILED_TO_DELETE_FILE,"删除单个文件" + network.getNetName() + "失败！");
//            }
//        } else {
//            throw new CustomException(Global.CODE_NETWORK_FILE_DOES_NOT_EXIST,"删除单个文件失败：" + network.getNetName() + "不存在！");
//        }
//    }


    public static Boolean checkNetwork(Network network) throws CustomException {
        String filePath = "/etc/sysconfig/network-scripts/" + network.getNetName();
        //        判断文件是否存在
        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            return true;
        } else {
            throw new CustomException(Global.CODE_NETWORK_NETWORK_CARD_DOES_NOT_EXIST,"网卡不存在");
        }
    }

}
