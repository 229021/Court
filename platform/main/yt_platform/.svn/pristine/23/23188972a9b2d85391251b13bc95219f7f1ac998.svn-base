package com.san.platform.hikvison.hkmethod;

import com.alibaba.dubbo.config.annotation.Service;
import com.san.platform.config.LocalHost;
import com.san.platform.device.Device;
import com.san.platform.hikvision.HikvisonService;
import com.san.platform.hikvison.hkinterface.HCNetSDK;
import com.san.platform.personnel.Personnel;
import com.san.platform.visitor.Visitor;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import org.apache.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Scheduled;
import tk.mybatis.mapper.util.StringUtil;

import java.io.*;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class HKMethod implements HikvisonService {

    private Logger logger = Logger.getLogger(HKMethod.class);
    //存储图片根路径
    @Value("${path.baseUrl}")
    private String baseUrl;
    //存储图片访客路径
    @Value("${path.visit}")
    private String visit;

    //存储抓拍路径
    @Value("${path.snap}")
    private String snap;
    //访问路径和存储路径
    @Value("${path.access}")
    private String access;
    @Value("${server.port}")
    private int port;
    @Value("${path.localpath}")
    private String localpath;

    @Autowired
    private AmqpTemplate rabbitTemplate;

    private List<HKMainClass> mainClassList = new ArrayList<>() ;

    private List<Device> deviceList = new ArrayList<>();

    private HashMap<String,Object> hashMap = new HashMap();

    private int errorCode = 0; // 名单下发失败code


    public void AlarmDataHandle(int lCommand, HCNetSDK.NET_DVR_ALARMER pAlarmer, Pointer pAlarmInfo, int dwBufLen, Pointer pUser) {
        try {
            String sAlarmType = new String();
            HashMap<String, String> data = new HashMap<String, String>();
            //报警时间
            Date today = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String[] sIP = new String[2];

            sAlarmType = new String("lCommand=") + lCommand;
            logger.info("alarm lCommand enter switch case" + lCommand);
            //lCommand是传的报警类型
            switch (lCommand) {
                case HCNetSDK.COMM_ALARM_V40:
                    HCNetSDK.NET_DVR_ALARMINFO_V40 struAlarmInfoV40 = new HCNetSDK.NET_DVR_ALARMINFO_V40();
                    struAlarmInfoV40.write();
                    Pointer pInfoV40 = struAlarmInfoV40.getPointer();
                    pInfoV40.write(0, pAlarmInfo.getByteArray(0, struAlarmInfoV40.size()), 0, struAlarmInfoV40.size());
                    struAlarmInfoV40.read();

                    switch (struAlarmInfoV40.struAlarmFixedHeader.dwAlarmType) {
                        case 0:
                            struAlarmInfoV40.struAlarmFixedHeader.ustruAlarm.setType(HCNetSDK.struIOAlarm.class);
                            struAlarmInfoV40.read();
                            sAlarmType = sAlarmType + new String("：信号量报警") + "，" + "报警输入口：" + struAlarmInfoV40.struAlarmFixedHeader.ustruAlarm.struioAlarm.dwAlarmInputNo;
                            break;
                        case 1:
                            sAlarmType = sAlarmType + new String("：硬盘满");
                            break;
                        case 2:
                            sAlarmType = sAlarmType + new String("：信号丢失");
                            break;
                        case 3:
                            struAlarmInfoV40.struAlarmFixedHeader.ustruAlarm.setType(HCNetSDK.struAlarmChannel.class);
                            struAlarmInfoV40.read();
                            int iChanNum = struAlarmInfoV40.struAlarmFixedHeader.ustruAlarm.sstrualarmChannel.dwAlarmChanNum;
                            sAlarmType = sAlarmType + new String("：移动侦测") + "，" + "报警通道个数：" + iChanNum + "，" + "报警通道号：";

                            for (int i = 0; i < iChanNum; i++) {
                                byte[] byChannel = struAlarmInfoV40.pAlarmData.getByteArray(i * 4, 4);

                                int iChanneNo = 0;
                                for (int j = 0; j < 4; j++) {
                                    int ioffset = j * 8;
                                    int iByte = byChannel[j] & 0xff;
                                    iChanneNo = iChanneNo + (iByte << ioffset);
                                }

                                sAlarmType = sAlarmType + "+ch[" + iChanneNo + "]";
                            }

                            break;
                        case 4:
                            sAlarmType = sAlarmType + new String("：硬盘未格式化");
                            break;
                        case 5:
                            sAlarmType = sAlarmType + new String("：读写硬盘出错");
                            break;
                        case 6:
                            sAlarmType = sAlarmType + new String("：遮挡报警");
                            break;
                        case 7:
                            sAlarmType = sAlarmType + new String("：制式不匹配");
                            break;
                        case 8:
                            sAlarmType = sAlarmType + new String("：非法访问");
                            break;
                    }
                    data.put("datetime", dateFormat.format(today));
                    //报警类型
                    data.put("alermType", sAlarmType);
                    //报警设备IP地址
                    sIP = new String(pAlarmer.sDeviceIP).split("\0", 2);
                    data.put("ipaddr", sIP[0]);
                    break;
                case HCNetSDK.COMM_ALARM_V30:
                    HCNetSDK.NET_DVR_ALARMINFO_V30 strAlarmInfoV30 = new HCNetSDK.NET_DVR_ALARMINFO_V30();
                    strAlarmInfoV30.write();
                    Pointer pInfoV30 = strAlarmInfoV30.getPointer();
                    pInfoV30.write(0, pAlarmInfo.getByteArray(0, strAlarmInfoV30.size()), 0, strAlarmInfoV30.size());
                    strAlarmInfoV30.read();
                    switch (strAlarmInfoV30.dwAlarmType) {
                        case 0:
                            sAlarmType = sAlarmType + new String("：信号量报警") + "，" + "报警输入口：" + (strAlarmInfoV30.dwAlarmInputNumber + 1);
                            break;
                        case 1:
                            sAlarmType = sAlarmType + new String("：硬盘满");
                            break;
                        case 2:
                            sAlarmType = sAlarmType + new String("：信号丢失");
                            break;
                        case 3:
                            sAlarmType = sAlarmType + new String("：移动侦测") + "，" + "报警通道：";
                            for (int i = 0; i < 64; i++) {
                                if (strAlarmInfoV30.byChannel[i] == 1) {
                                    sAlarmType = sAlarmType + "ch" + (i + 1) + " ";
                                }
                            }
                            break;
                        case 4:
                            sAlarmType = sAlarmType + new String("：硬盘未格式化");
                            break;
                        case 5:
                            sAlarmType = sAlarmType + new String("：读写硬盘出错");
                            break;
                        case 6:
                            sAlarmType = sAlarmType + new String("：遮挡报警");
                            break;
                        case 7:
                            sAlarmType = sAlarmType + new String("：制式不匹配");
                            break;
                        case 8:
                            sAlarmType = sAlarmType + new String("：非法访问");
                            break;
                    }
                    data.put("datetime", dateFormat.format(today));
                    //报警类型
                    data.put("alermType", sAlarmType);
                    //报警设备IP地址
                    sIP = new String(pAlarmer.sDeviceIP).split("\0", 2);
                    data.put("ipaddr", sIP[0]);
                    break;
                case HCNetSDK.COMM_ALARM_RULE:
                    HCNetSDK.NET_VCA_RULE_ALARM strVcaAlarm = new HCNetSDK.NET_VCA_RULE_ALARM();
                    strVcaAlarm.write();
                    Pointer pVcaInfo = strVcaAlarm.getPointer();
                    pVcaInfo.write(0, pAlarmInfo.getByteArray(0, strVcaAlarm.size()), 0, strVcaAlarm.size());
                    strVcaAlarm.read();

                    switch (strVcaAlarm.struRuleInfo.wEventTypeEx) {
                        case 1:
                            sAlarmType = sAlarmType + new String("：穿越警戒面") + "，" +
                                    "_wPort:" + strVcaAlarm.struDevInfo.wPort +
                                    "_byChannel:" + strVcaAlarm.struDevInfo.byChannel +
                                    "_byIvmsChannel:" + strVcaAlarm.struDevInfo.byIvmsChannel +
                                    "_Dev IP：" + new String(strVcaAlarm.struDevInfo.struDevIP.sIpV4);
                            break;
                        case 2:
                            sAlarmType = sAlarmType + new String("：目标进入区域") + "，" +
                                    "_wPort:" + strVcaAlarm.struDevInfo.wPort +
                                    "_byChannel:" + strVcaAlarm.struDevInfo.byChannel +
                                    "_byIvmsChannel:" + strVcaAlarm.struDevInfo.byIvmsChannel +
                                    "_Dev IP：" + new String(strVcaAlarm.struDevInfo.struDevIP.sIpV4);
                            break;
                        case 3:
                            sAlarmType = sAlarmType + new String("：目标离开区域") + "，" +
                                    "_wPort:" + strVcaAlarm.struDevInfo.wPort +
                                    "_byChannel:" + strVcaAlarm.struDevInfo.byChannel +
                                    "_byIvmsChannel:" + strVcaAlarm.struDevInfo.byIvmsChannel +
                                    "_Dev IP：" + new String(strVcaAlarm.struDevInfo.struDevIP.sIpV4);
                            break;
                        default:
                            sAlarmType = sAlarmType + new String("：其他行为分析报警，事件类型：")
                                    + strVcaAlarm.struRuleInfo.wEventTypeEx +
                                    "_wPort:" + strVcaAlarm.struDevInfo.wPort +
                                    "_byChannel:" + strVcaAlarm.struDevInfo.byChannel +
                                    "_byIvmsChannel:" + strVcaAlarm.struDevInfo.byIvmsChannel +
                                    "_Dev IP：" + new String(strVcaAlarm.struDevInfo.struDevIP.sIpV4);
                            break;
                    }
                    data.put("datetime", dateFormat.format(today));
                    //报警类型
                    data.put("alermType", sAlarmType);
                    //报警设备IP地址
                    sIP = new String(pAlarmer.sDeviceIP).split("\0", 2);
                    data.put("ipaddr", sIP[0]);

                    if (strVcaAlarm.dwPicDataLen > 0) {
                        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                        String newName = sf.format(new Date());
                        FileOutputStream fout;
                        try {
                            fout = new FileOutputStream(System.getProperty("user.dir") + "\\pic\\" + new String(pAlarmer.sDeviceIP).trim()
                                    + "wEventTypeEx[" + strVcaAlarm.struRuleInfo.wEventTypeEx + "]_" + newName + "_vca.jpg");
                            //将字节写入文件
                            long offset = 0;
                            ByteBuffer buffers = strVcaAlarm.pImage.getByteBuffer(offset, strVcaAlarm.dwPicDataLen);
                            byte[] bytes = new byte[strVcaAlarm.dwPicDataLen];
                            buffers.rewind();
                            buffers.get(bytes);
                            fout.write(bytes);
                            fout.close();
                        } catch (FileNotFoundException e) {
                            // TODO Auto-generated catch block
                            logger.warn("case::"+HCNetSDK.COMM_ALARM_RULE+e.getMessage());
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            logger.warn("case::"+HCNetSDK.COMM_ALARM_RULE+e.getMessage());
                            e.printStackTrace();
                        }
                    }
                    break;
                case HCNetSDK.COMM_UPLOAD_PLATE_RESULT:
                    HCNetSDK.NET_DVR_PLATE_RESULT strPlateResult = new HCNetSDK.NET_DVR_PLATE_RESULT();
                    strPlateResult.write();
                    Pointer pPlateInfo = strPlateResult.getPointer();
                    pPlateInfo.write(0, pAlarmInfo.getByteArray(0, strPlateResult.size()), 0, strPlateResult.size());
                    strPlateResult.read();
                    try {
                        String srt3 = new String(strPlateResult.struPlateInfo.sLicense, "GBK");
                        sAlarmType = sAlarmType + "：交通抓拍上传，车牌：" + srt3;
                    } catch (UnsupportedEncodingException e1) {
                        // TODO Auto-generated catch block
                        logger.warn("case::"+HCNetSDK.COMM_UPLOAD_PLATE_RESULT+e1.getMessage());
                        e1.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        logger.warn("case::"+HCNetSDK.COMM_UPLOAD_PLATE_RESULT+e.getMessage());
                        e.printStackTrace();
                    }

                    data.put("datetime", dateFormat.format(today));
                    //报警类型
                    data.put("alermType", sAlarmType);
                    //报警设备IP地址
                    sIP = new String(pAlarmer.sDeviceIP).split("\0", 2);
                    data.put("ipaddr", sIP[0]);

                    if (strPlateResult.dwPicLen > 0) {
                        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                        String newName = sf.format(new Date());
                        FileOutputStream fout;
                        try {
                            fout = new FileOutputStream(System.getProperty("user.dir") + "\\pic\\" + new String(pAlarmer.sDeviceIP).trim() + "_"
                                    + newName + "_plateResult.jpg");
                            //将字节写入文件
                            long offset = 0;
                            ByteBuffer buffers = strPlateResult.pBuffer1.getByteBuffer(offset, strPlateResult.dwPicLen);
                            byte[] bytes = new byte[strPlateResult.dwPicLen];
                            buffers.rewind();
                            buffers.get(bytes);
                            fout.write(bytes);
                            fout.close();
                        } catch (FileNotFoundException e) {
                            // TODO Auto-generated catch block
                            logger.warn("case::"+HCNetSDK.COMM_UPLOAD_PLATE_RESULT+e.getMessage());
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            logger.warn("case::"+HCNetSDK.COMM_UPLOAD_PLATE_RESULT+e.getMessage());
                            e.printStackTrace();
                        }
                    }
                    break;
                case HCNetSDK.COMM_ITS_PLATE_RESULT:
                    HCNetSDK.NET_ITS_PLATE_RESULT strItsPlateResult = new HCNetSDK.NET_ITS_PLATE_RESULT();
                    strItsPlateResult.write();
                    Pointer pItsPlateInfo = strItsPlateResult.getPointer();
                    pItsPlateInfo.write(0, pAlarmInfo.getByteArray(0, strItsPlateResult.size()), 0, strItsPlateResult.size());
                    strItsPlateResult.read();
                    try {
                        String srt3 = new String(strItsPlateResult.struPlateInfo.sLicense, "GBK");
                        sAlarmType = sAlarmType + ",车辆类型：" + strItsPlateResult.byVehicleType + ",交通抓拍上传，车牌：" + srt3;
                    } catch (UnsupportedEncodingException e1) {
                        // TODO Auto-generated catch block
                        logger.warn("case::"+HCNetSDK.COMM_ITS_PLATE_RESULT+e1.getMessage());
                        e1.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        logger.warn("case::"+HCNetSDK.COMM_ITS_PLATE_RESULT+e.getMessage());
                        e.printStackTrace();
                    }

                    data.put("datetime", dateFormat.format(today));
                    //报警类型
                    data.put("alermType", sAlarmType);
                    //报警设备IP地址
                    sIP = new String(pAlarmer.sDeviceIP).split("\0", 2);
                    data.put("ipaddr", sIP[0]);

                    for (int i = 0; i < strItsPlateResult.dwPicNum; i++) {
                        if (strItsPlateResult.struPicInfo[i].dwDataLen > 0) {
                            SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                            String newName = sf.format(new Date());
                            FileOutputStream fout;
                            try {
                                String filename = System.getProperty("user.dir") + "\\pic\\" + new String(pAlarmer.sDeviceIP).trim() + "_"
                                        + newName + "_type[" + strItsPlateResult.struPicInfo[i].byType + "]_ItsPlate.jpg";
                                fout = new FileOutputStream(filename);
                                //将字节写入文件
                                long offset = 0;
                                ByteBuffer buffers = strItsPlateResult.struPicInfo[i].pBuffer.getByteBuffer(offset, strItsPlateResult.struPicInfo[i].dwDataLen);
                                byte[] bytes = new byte[strItsPlateResult.struPicInfo[i].dwDataLen];
                                buffers.rewind();
                                buffers.get(bytes);
                                fout.write(bytes);
                                fout.close();
                            } catch (FileNotFoundException e) {
                                // TODO Auto-generated catch block
                                logger.warn("case::"+HCNetSDK.COMM_ITS_PLATE_RESULT+e.getMessage());
                                e.printStackTrace();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                logger.warn("case::"+HCNetSDK.COMM_ITS_PLATE_RESULT+e.getMessage());
                                e.printStackTrace();
                            }
                        }
                    }
                    break;
                case HCNetSDK.COMM_ALARM_PDC:
                    HCNetSDK.NET_DVR_PDC_ALRAM_INFO strPDCResult = new HCNetSDK.NET_DVR_PDC_ALRAM_INFO();
                    strPDCResult.write();
                    Pointer pPDCInfo = strPDCResult.getPointer();
                    pPDCInfo.write(0, pAlarmInfo.getByteArray(0, strPDCResult.size()), 0, strPDCResult.size());
                    strPDCResult.read();

                    if (strPDCResult.byMode == 0) {
                        strPDCResult.uStatModeParam.setType(HCNetSDK.NET_DVR_STATFRAME.class);
                        sAlarmType = sAlarmType + "：客流量统计，进入人数：" + strPDCResult.dwEnterNum + "，离开人数：" + strPDCResult.dwLeaveNum +
                                ", byMode:" + strPDCResult.byMode + ", dwRelativeTime:" + strPDCResult.uStatModeParam.struStatFrame.dwRelativeTime +
                                ", dwAbsTime:" + strPDCResult.uStatModeParam.struStatFrame.dwAbsTime;
                    }
                    if (strPDCResult.byMode == 1) {
                        strPDCResult.uStatModeParam.setType(HCNetSDK.NET_DVR_STATTIME.class);
                        String strtmStart = "" + String.format("%04d", strPDCResult.uStatModeParam.struStatTime.tmStart.dwYear) +
                                String.format("%02d", strPDCResult.uStatModeParam.struStatTime.tmStart.dwMonth) +
                                String.format("%02d", strPDCResult.uStatModeParam.struStatTime.tmStart.dwDay) +
                                String.format("%02d", strPDCResult.uStatModeParam.struStatTime.tmStart.dwHour) +
                                String.format("%02d", strPDCResult.uStatModeParam.struStatTime.tmStart.dwMinute) +
                                String.format("%02d", strPDCResult.uStatModeParam.struStatTime.tmStart.dwSecond);
                        String strtmEnd = "" + String.format("%04d", strPDCResult.uStatModeParam.struStatTime.tmEnd.dwYear) +
                                String.format("%02d", strPDCResult.uStatModeParam.struStatTime.tmEnd.dwMonth) +
                                String.format("%02d", strPDCResult.uStatModeParam.struStatTime.tmEnd.dwDay) +
                                String.format("%02d", strPDCResult.uStatModeParam.struStatTime.tmEnd.dwHour) +
                                String.format("%02d", strPDCResult.uStatModeParam.struStatTime.tmEnd.dwMinute) +
                                String.format("%02d", strPDCResult.uStatModeParam.struStatTime.tmEnd.dwSecond);
                        sAlarmType = sAlarmType + "：客流量统计，进入人数：" + strPDCResult.dwEnterNum + "，离开人数：" + strPDCResult.dwLeaveNum +
                                ", byMode:" + strPDCResult.byMode + ", tmStart:" + strtmStart + ",tmEnd :" + strtmEnd;
                    }

                    data.put("datetime", dateFormat.format(today));
                    //报警类型
                    data.put("alermType", sAlarmType);
                    //报警设备IP地址
                    sIP = new String(pAlarmer.sDeviceIP).split("\0", 2);
                    data.put("ipaddr", sIP[0]);
                    break;

                case HCNetSDK.COMM_ITS_PARK_VEHICLE:
                    HCNetSDK.NET_ITS_PARK_VEHICLE strItsParkVehicle = new HCNetSDK.NET_ITS_PARK_VEHICLE();
                    strItsParkVehicle.write();
                    Pointer pItsParkVehicle = strItsParkVehicle.getPointer();
                    pItsParkVehicle.write(0, pAlarmInfo.getByteArray(0, strItsParkVehicle.size()), 0, strItsParkVehicle.size());
                    strItsParkVehicle.read();
                    try {
                        String srtParkingNo = new String(strItsParkVehicle.byParkingNo).trim(); //车位编号
                        String srtPlate = new String(strItsParkVehicle.struPlateInfo.sLicense, "GBK").trim(); //车牌号码
                        sAlarmType = sAlarmType + ",停产场数据,车位编号：" + srtParkingNo + ",车位状态："
                                + strItsParkVehicle.byLocationStatus + ",车牌：" + srtPlate;
                    } catch (UnsupportedEncodingException e1) {
                        // TODO Auto-generated catch block
                        logger.warn("case::"+HCNetSDK.COMM_ITS_PARK_VEHICLE+e1.getMessage());
                        e1.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        logger.warn("case::"+HCNetSDK.COMM_ITS_PARK_VEHICLE+e.getMessage());
                        e.printStackTrace();
                    }

                    data.put("datetime", dateFormat.format(today));
                    //报警类型
                    data.put("alermType", sAlarmType);
                    //报警设备IP地址
                    sIP = new String(pAlarmer.sDeviceIP).split("\0", 2);
                    data.put("ipaddr", sIP[0]);

                    for (int i = 0; i < strItsParkVehicle.dwPicNum; i++) {
                        if (strItsParkVehicle.struPicInfo[i].dwDataLen > 0) {
                            SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                            String newName = sf.format(new Date());
                            FileOutputStream fout;
                            try {
                                String filename = System.getProperty("user.dir") + "\\pic\\" + new String(pAlarmer.sDeviceIP).trim() + "_"
                                        + newName + "_type[" + strItsParkVehicle.struPicInfo[i].byType + "]_ParkVehicle.jpg";
                                fout = new FileOutputStream(filename);
                                //将字节写入文件
                                long offset = 0;
                                ByteBuffer buffers = strItsParkVehicle.struPicInfo[i].pBuffer.getByteBuffer(offset, strItsParkVehicle.struPicInfo[i].dwDataLen);
                                byte[] bytes = new byte[strItsParkVehicle.struPicInfo[i].dwDataLen];
                                buffers.rewind();
                                buffers.get(bytes);
                                fout.write(bytes);
                                fout.close();
                            } catch (FileNotFoundException e) {
                                // TODO Auto-generated catch block
                                logger.warn("case::"+HCNetSDK.COMM_ITS_PARK_VEHICLE+e.getMessage());
                                e.printStackTrace();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                logger.warn("case::"+HCNetSDK.COMM_ITS_PARK_VEHICLE+e.getMessage());
                                e.printStackTrace();
                            }
                        }
                    }
                    break;
                case HCNetSDK.COMM_ALARM_TFS:
                    HCNetSDK.NET_DVR_TFS_ALARM strTFSAlarmInfo = new HCNetSDK.NET_DVR_TFS_ALARM();
                    strTFSAlarmInfo.write();
                    Pointer pTFSInfo = strTFSAlarmInfo.getPointer();
                    pTFSInfo.write(0, pAlarmInfo.getByteArray(0, strTFSAlarmInfo.size()), 0, strTFSAlarmInfo.size());
                    strTFSAlarmInfo.read();

                    try {
                        String srtPlate = new String(strTFSAlarmInfo.struPlateInfo.sLicense, "GBK").trim(); //车牌号码
                        sAlarmType = sAlarmType + "：交通取证报警信息，违章类型：" + strTFSAlarmInfo.dwIllegalType + "，车牌号码：" + srtPlate
                                + "，车辆出入状态：" + strTFSAlarmInfo.struAIDInfo.byVehicleEnterState;
                    } catch (UnsupportedEncodingException e1) {
                        // TODO Auto-generated catch block
                        logger.warn("case::"+HCNetSDK.COMM_ALARM_TFS+e1.getMessage());
                        e1.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        logger.warn("case::"+HCNetSDK.COMM_ALARM_TFS+e.getMessage());
                        e.printStackTrace();
                    }

                    data.put("datetime", dateFormat.format(today));
                    //报警类型
                    data.put("alermType", sAlarmType);
                    //报警设备IP地址
                    sIP = new String(pAlarmer.sDeviceIP).split("\0", 2);
                    data.put("ipaddr", sIP[0]);
                    break;
                case HCNetSDK.COMM_ALARM_AID_V41:
                    HCNetSDK.NET_DVR_AID_ALARM_V41 struAIDAlarmInfo = new HCNetSDK.NET_DVR_AID_ALARM_V41();
                    struAIDAlarmInfo.write();
                    Pointer pAIDInfo = struAIDAlarmInfo.getPointer();
                    pAIDInfo.write(0, pAlarmInfo.getByteArray(0, struAIDAlarmInfo.size()), 0, struAIDAlarmInfo.size());
                    struAIDAlarmInfo.read();
                    sAlarmType = sAlarmType + "：交通事件报警信息，交通事件类型：" + struAIDAlarmInfo.struAIDInfo.dwAIDType + "，规则ID："
                            + struAIDAlarmInfo.struAIDInfo.byRuleID + "，车辆出入状态：" + struAIDAlarmInfo.struAIDInfo.byVehicleEnterState;

                    data.put("datetime", dateFormat.format(today));
                    //报警类型
                    data.put("alermType", sAlarmType);
                    //报警设备IP地址
                    sIP = new String(pAlarmer.sDeviceIP).split("\0", 2);
                    data.put("ipaddr", sIP[0]);
                    break;
                case HCNetSDK.COMM_ALARM_TPS_V41:
                    HCNetSDK.NET_DVR_TPS_ALARM_V41 struTPSAlarmInfo = new HCNetSDK.NET_DVR_TPS_ALARM_V41();
                    struTPSAlarmInfo.write();
                    Pointer pTPSInfo = struTPSAlarmInfo.getPointer();
                    pTPSInfo.write(0, pAlarmInfo.getByteArray(0, struTPSAlarmInfo.size()), 0, struTPSAlarmInfo.size());
                    struTPSAlarmInfo.read();

                    sAlarmType = sAlarmType + "：交通统计报警信息，绝对时标：" + struTPSAlarmInfo.dwAbsTime
                            + "，能见度:" + struTPSAlarmInfo.struDevInfo.byIvmsChannel
                            + "，车道1交通状态:" + struTPSAlarmInfo.struTPSInfo.struLaneParam[0].byTrafficState
                            + "，监测点编号：" + new String(struTPSAlarmInfo.byMonitoringSiteID).trim()
                            + "，设备编号：" + new String(struTPSAlarmInfo.byDeviceID).trim()
                            + "，开始统计时间：" + struTPSAlarmInfo.dwStartTime
                            + "，结束统计时间：" + struTPSAlarmInfo.dwStopTime;

                    data.put("datetime", dateFormat.format(today));
                    //报警类型
                    data.put("alermType", sAlarmType);
                    //报警设备IP地址
                    sIP = new String(pAlarmer.sDeviceIP).split("\0", 2);
                    data.put("ipaddr", sIP[0]);
                    break;
                case HCNetSDK.COMM_UPLOAD_FACESNAP_RESULT://4370 1112 抓拍
                    logger.info("case::"+HCNetSDK.COMM_UPLOAD_FACESNAP_RESULT + "method have been called.");
                    //实时人脸抓拍上传
                    HCNetSDK.NET_VCA_FACESNAP_RESULT strFaceSnapInfo = new HCNetSDK.NET_VCA_FACESNAP_RESULT();
                    strFaceSnapInfo.write();
                    Pointer pFaceSnapInfo = strFaceSnapInfo.getPointer();
                    pFaceSnapInfo.write(0, pAlarmInfo.getByteArray(0, strFaceSnapInfo.size()), 0, strFaceSnapInfo.size());
                    strFaceSnapInfo.read();
                    sAlarmType = sAlarmType + "：人脸抓拍上传，人脸评分：" + strFaceSnapInfo.dwFaceScore + "，年龄段：" + strFaceSnapInfo.struFeature.byAgeGroup + "，性别：" + strFaceSnapInfo.struFeature.bySex;
                    data.put("datetime", dateFormat.format(today));
                    //报警类型
                    data.put("alermType", sAlarmType);
                    logger.info("case::"+HCNetSDK.COMM_UPLOAD_FACESNAP_RESULT + "报警类型:"+sAlarmType);
                    //报警设备IP地址
                    sIP = new String(pAlarmer.sDeviceIP).split("\0", 2);
                    data.put("ipaddr", sIP[0]);
                    logger.info("case::"+HCNetSDK.COMM_UPLOAD_FACESNAP_RESULT + "报警设备IP地址:"+sIP[0]);
                    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss"); //设置日期格式
                    String time = df.format(new Date()); // new Date()为获取当前系统时间
                    //人脸图片写文件
                    //人脸图片写文件
                    /*String smallSrc = baseUrl + snap + time + "small.jpg";
                    String bigSrc = baseUrl + snap + time + "big.jpg" ;*/
                    String smallJpg = baseUrl + snap + time + "small.jpg";
                    String bigJpg = baseUrl + snap + time + "big.jpg" ;
                    String smallSrc = access + time + "small.jpg";
                    String bigSrc = access + time + "big.jpg" ;
                    /*String smallJpg = access + time + "small.jpg";
                    String bigJpg = access + time + "big.jpg" ;*/
                    try {
                        logger.info("case::"+ HCNetSDK.COMM_UPLOAD_FACESNAP_RESULT +" ,抓拍图片即将写入");
                        String realPath =  baseUrl + snap;
                        File dest = new File(realPath);
                        //判断文件目录是否存在
                        if (!dest.isDirectory() && !dest.exists()) {
                            dest.mkdirs();
                        }
                        FileOutputStream small = new FileOutputStream(smallJpg);
                        FileOutputStream big = new FileOutputStream(bigJpg);

                        if (strFaceSnapInfo.dwFacePicLen > 0) {
                            try {
                                small.write(strFaceSnapInfo.pBuffer1.getByteArray(0, strFaceSnapInfo.dwFacePicLen), 0, strFaceSnapInfo.dwFacePicLen);
                                small.close();
                            } catch (IOException ex) {
                                logger.warn("case::"+HCNetSDK.COMM_UPLOAD_FACESNAP_RESULT + "抓拍小图写入"+ex.getMessage());
                            }

                        }
                        if (strFaceSnapInfo.dwFacePicLen > 0) {
                            try {
                                big.write(strFaceSnapInfo.pBuffer2.getByteArray(0, strFaceSnapInfo.dwBackgroundPicLen), 0, strFaceSnapInfo.dwBackgroundPicLen);
                                big.close();
                            } catch (IOException ex) {
                                logger.warn("case::"+HCNetSDK.COMM_UPLOAD_FACESNAP_RESULT + "抓拍大图写入"+ex.getMessage());
                            }
                        }
                        Visitor visitor = new Visitor();
                        visitor.setDeviceIP(sIP[0]);
                        visitor.setSmallSrc(smallSrc);
                        visitor.setBigSrc(bigSrc);
                        String url = "http://"+localpath+":"+port+smallSrc;
                        visitor.setSmallJpg(url);// 用于脸谱解析图像
                        logger.info("case::"+HCNetSDK.COMM_UPLOAD_FACESNAP_RESULT + "开始人脸分析");
                        rabbitTemplate.convertAndSend("topic_face_snap",visitor);
                        logger.info("case::"+HCNetSDK.COMM_UPLOAD_FACESNAP_RESULT + "人脸分析完成");
                    } catch (Exception ex) {
                        logger.warn("case::"+HCNetSDK.COMM_UPLOAD_FACESNAP_RESULT + "Exception" + ex.getMessage());
                    }
                    break;
                case HCNetSDK.COMM_SNAP_MATCH_ALARM:
                    //人脸黑名单比对报警
                    HCNetSDK.NET_VCA_FACESNAP_MATCH_ALARM strFaceSnapMatch = new HCNetSDK.NET_VCA_FACESNAP_MATCH_ALARM();
                    strFaceSnapMatch.write();
                    Pointer pFaceSnapMatch = strFaceSnapMatch.getPointer();
                    pFaceSnapMatch.write(0, pAlarmInfo.getByteArray(0, strFaceSnapMatch.size()), 0, strFaceSnapMatch.size());
                    strFaceSnapMatch.read();

                    if ((strFaceSnapMatch.dwSnapPicLen > 0) && (strFaceSnapMatch.byPicTransType == 0)) {
                        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                        String newName = sf.format(new Date());
                        FileOutputStream fout;
                        try {
                            String filename = System.getProperty("user.dir") + "\\pic\\" + newName + "_pSnapPicBuffer" + ".jpg";
                            fout = new FileOutputStream(filename);
                            //将字节写入文件
                            long offset = 0;
                            ByteBuffer buffers = strFaceSnapMatch.pSnapPicBuffer.getByteBuffer(offset, strFaceSnapMatch.dwSnapPicLen);
                            byte[] bytes = new byte[strFaceSnapMatch.dwSnapPicLen];
                            buffers.rewind();
                            buffers.get(bytes);
                            fout.write(bytes);
                            fout.close();
                        } catch (FileNotFoundException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    if ((strFaceSnapMatch.struSnapInfo.dwSnapFacePicLen > 0) && (strFaceSnapMatch.byPicTransType == 0)) {
                        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                        String newName = sf.format(new Date());
                        FileOutputStream fout;
                        try {
                            String filename = System.getProperty("user.dir") + "\\pic\\" + newName + "_struSnapInfo_pBuffer1" + ".jpg";
                            fout = new FileOutputStream(filename);
                            //将字节写入文件
                            long offset = 0;
                            ByteBuffer buffers = strFaceSnapMatch.struSnapInfo.pBuffer1.getByteBuffer(offset, strFaceSnapMatch.struSnapInfo.dwSnapFacePicLen);
                            byte[] bytes = new byte[strFaceSnapMatch.struSnapInfo.dwSnapFacePicLen];
                            buffers.rewind();
                            buffers.get(bytes);
                            fout.write(bytes);
                            fout.close();
                        } catch (FileNotFoundException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    if ((strFaceSnapMatch.struBlackListInfo.dwBlackListPicLen > 0) && (strFaceSnapMatch.byPicTransType == 0)) {
                        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                        String newName = sf.format(new Date());
                        FileOutputStream fout;
                        try {
                            String filename = System.getProperty("user.dir") + "\\pic\\" + newName + "_fSimilarity_" + strFaceSnapMatch.fSimilarity + "_struBlackListInfo_pBuffer1" + ".jpg";
                            fout = new FileOutputStream(filename);
                            //将字节写入文件
                            long offset = 0;
                            ByteBuffer buffers = strFaceSnapMatch.struBlackListInfo.pBuffer1.getByteBuffer(offset, strFaceSnapMatch.struBlackListInfo.dwBlackListPicLen);
                            byte[] bytes = new byte[strFaceSnapMatch.struBlackListInfo.dwBlackListPicLen];
                            buffers.rewind();
                            buffers.get(bytes);
                            fout.write(bytes);
                            fout.close();
                        } catch (FileNotFoundException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                    sAlarmType = sAlarmType + "：人脸黑名单比对报警，相识度：" + strFaceSnapMatch.fSimilarity + "，黑名单姓名：" + new String(strFaceSnapMatch.struBlackListInfo.struBlackListInfo.struAttribute.byName, "GBK").trim() + "，\n黑名单证件信息：" + new String(strFaceSnapMatch.struBlackListInfo.struBlackListInfo.struAttribute.byCertificateNumber).trim();

                    //获取人脸库ID
                    byte[] FDIDbytes;
                    if ((strFaceSnapMatch.struBlackListInfo.dwFDIDLen > 0) && (strFaceSnapMatch.struBlackListInfo.pFDID != null)) {
                        ByteBuffer FDIDbuffers = strFaceSnapMatch.struBlackListInfo.pFDID.getByteBuffer(0, strFaceSnapMatch.struBlackListInfo.dwFDIDLen);
                        FDIDbytes = new byte[strFaceSnapMatch.struBlackListInfo.dwFDIDLen];
                        FDIDbuffers.rewind();
                        FDIDbuffers.get(FDIDbytes);
                        sAlarmType = sAlarmType + "，人脸库ID:" + new String(FDIDbytes).trim();
                    }
                    //获取人脸图片ID
                    byte[] PIDbytes;
                    if ((strFaceSnapMatch.struBlackListInfo.dwPIDLen > 0) && (strFaceSnapMatch.struBlackListInfo.pPID != null)) {
                        ByteBuffer PIDbuffers = strFaceSnapMatch.struBlackListInfo.pPID.getByteBuffer(0, strFaceSnapMatch.struBlackListInfo.dwPIDLen);
                        PIDbytes = new byte[strFaceSnapMatch.struBlackListInfo.dwPIDLen];
                        PIDbuffers.rewind();
                        PIDbuffers.get(PIDbytes);
                        sAlarmType = sAlarmType + "，人脸图片ID:" + new String(PIDbytes).trim();
                    }
                    data.put("datetime", dateFormat.format(today));
                    //报警类型
                    data.put("alermType", sAlarmType);
                    //报警设备IP地址
                    sIP = new String(pAlarmer.sDeviceIP).split("\0", 2);
                    data.put("ipaddr", sIP[0]);
                    break;
                case HCNetSDK.COMM_ALARM_ACS: //门禁主机报警信息20482
                    logger.info("case::"+HCNetSDK.COMM_ALARM_ACS + "method have been called." );
                    HCNetSDK.NET_DVR_ACS_ALARM_INFO strACSInfo = new HCNetSDK.NET_DVR_ACS_ALARM_INFO();
                    strACSInfo.write();
                    Pointer pACSInfo = strACSInfo.getPointer();
                    pACSInfo.write(0, pAlarmInfo.getByteArray(0, strACSInfo.size()), 0, strACSInfo.size());
                    strACSInfo.read();

                    sAlarmType = sAlarmType + "：门禁主机报警信息，卡号：" + new String(strACSInfo.struAcsEventInfo.byCardNo).trim() + "，卡类型：" +
                            strACSInfo.struAcsEventInfo.byCardType + "，报警主类型：" + strACSInfo.dwMajor + "，报警次类型：" + strACSInfo.dwMinor;

                    data.put("datetime", dateFormat.format(today));
                    //报警类型
                    data.put("alermType", sAlarmType);
                    logger.info("case::"+HCNetSDK.COMM_ALARM_ACS + "报警类型" );
                    //报警设备IP地址
                    sIP = new String(pAlarmer.sDeviceIP).split("\0", 2);
                    data.put("ipaddr", sIP[0]);
                    logger.info("case::"+HCNetSDK.COMM_ALARM_ACS + "报警设备IP地址" +sIP[0]);
                    if (strACSInfo.dwPicDataLen > 0) {
                        logger.info("case::"+ HCNetSDK.COMM_ALARM_ACS +" ,图片即将写入");
                        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                        String newName = sf.format(new Date());
                        FileOutputStream fout;
                        try {
                            if (strACSInfo.dwMinor == 75) {
                                String filename = baseUrl + visit + "_byCardNo[" + new String(strACSInfo.struAcsEventInfo.byCardNo).trim() +
                                        "_" + newName + "_Acs.jpg";

                                String realPath = baseUrl + visit;
                                File dest = new File(realPath);
                                //判断文件目录是否存在
                                if (!dest.isDirectory() && !dest.exists()) {
                                    dest.mkdirs();
                                }
                                fout = new FileOutputStream(filename);
                                //将字节写入文件
                                long offset = 0;
                                ByteBuffer buffers = strACSInfo.pPicData.getByteBuffer(offset, strACSInfo.dwPicDataLen);
                                byte[] bytes = new byte[strACSInfo.dwPicDataLen];
                                buffers.rewind();
                                buffers.get(bytes);
                                fout.write(bytes);
                                fout.close();
                                Visitor visitor = new Visitor();

                                String incard = new String(strACSInfo.struAcsEventInfo.byCardNo).trim();
                                if (!"".equals(incard) && incard != null) {
                                    visitor.setVisitCardNumber(incard);
                                    String filename2 = access +
                                            "_byCardNo[" + new String(strACSInfo.struAcsEventInfo.byCardNo).trim() +
                                            "_" + newName + "_Acs.jpg";
                                    visitor.setVisitPicture(filename2);
                                    String url = "http://"+localpath+":"+port+filename2;
                                    visitor.setFaceSrc(url);// 本地闸机人脸
                                    // maq 人脸信息修改
                                    logger.info("case::"+ HCNetSDK.COMM_ALARM_ACS +" ,人脸图片修改");
                                    rabbitTemplate.convertAndSend("topic_face_update",visitor);
                                    logger.info("case::"+ HCNetSDK.COMM_ALARM_ACS +" ,人脸图片修改完成");
                                }

                                if (visitor!=null&&!"".equals(visitor)){
                                    visitor.setDeviceIP(sIP[0]);
                                    queryHKData(visitor);
                                    logger.info("case::"+ HCNetSDK.COMM_ALARM_ACS +" ,人员信息入库今日访客完成");
                                }
                            } else if (strACSInfo.dwMinor == 76){
                                String filename = baseUrl + visit + "_byCardNo[" + new String(strACSInfo.struAcsEventInfo.byCardNo).trim() +
                                        "_" + newName + "_Acs.jpg";
                                String realPath = baseUrl + visit;
                                File dest = new File(realPath);
                                //判断文件目录是否存在
                                if (!dest.isDirectory() && !dest.exists()) {
                                    dest.mkdirs();
                                }
                                fout = new FileOutputStream(filename);
                                //将字节写入文件
                                long offset = 0;
                                ByteBuffer buffers = strACSInfo.pPicData.getByteBuffer(offset, strACSInfo.dwPicDataLen);
                                byte[] bytes = new byte[strACSInfo.dwPicDataLen];
                                buffers.rewind();
                                buffers.get(bytes);
                                fout.write(bytes);
                                fout.close();
                                String filename1 = access + "_byCardNo[" + new String(strACSInfo.struAcsEventInfo.byCardNo).trim() +
                                        "_" + newName + "_Acs.jpg";
                                Visitor visitor = new Visitor();
                                visitor.setVisitPicture(filename1);
                                rabbitTemplate.convertAndSend("topic_repair_cards",visitor);
                                logger.info("case::"+ HCNetSDK.COMM_ALARM_ACS +" ,人员补卡完成");
                            }
                        } catch (FileNotFoundException e) {
                            logger.warn("case::"+ HCNetSDK.COMM_ALARM_ACS +" ,FileNotFoundException："+e.getMessage());
                            e.printStackTrace();
                        } catch (IOException e) {
                            logger.warn("case::"+ HCNetSDK.COMM_ALARM_ACS +" ,IOException："+e.getMessage());
                            e.printStackTrace();
                        }catch (Exception e){
                            logger.warn("case::"+ HCNetSDK.COMM_ALARM_ACS +" ,Exception："+e.getMessage());
                            e.printStackTrace();
                        }
                    }
                    break;
                case HCNetSDK.COMM_ID_INFO_ALARM: //身份证信息20992
                    logger.info("case::"+HCNetSDK.COMM_ID_INFO_ALARM + "method have been called.");
                    HCNetSDK.NET_DVR_ID_CARD_INFO_ALARM strIDCardInfo = new HCNetSDK.NET_DVR_ID_CARD_INFO_ALARM();
                    strIDCardInfo.write();
                    Pointer pIDCardInfo = strIDCardInfo.getPointer();
                    pIDCardInfo.write(0, pAlarmInfo.getByteArray(0, strIDCardInfo.size()), 0, strIDCardInfo.size());
                    strIDCardInfo.read();

                    sAlarmType = sAlarmType + "：门禁身份证刷卡信息，身份证号码：" + new String(strIDCardInfo.struIDCardCfg.byIDNum).trim() + "，姓名：" +
                            new String(strIDCardInfo.struIDCardCfg.byName).trim() + "，报警主类型：" + strIDCardInfo.dwMajor + "，报警次类型：" + strIDCardInfo.dwMinor;

                    data.put("datetime", dateFormat.format(today));
                    //报警类型
                    data.put("alermType", sAlarmType);
                    logger.info("case::"+ HCNetSDK.COMM_ID_INFO_ALARM +" ,报警类型" + sAlarmType);
                    //报警设备IP地址
                    sIP = new String(pAlarmer.sDeviceIP).split("\0", 2);
                    data.put("ipaddr", sIP[0]);
                    logger.info("case::"+ HCNetSDK.COMM_ID_INFO_ALARM +" ,报警设备IP地址" + sIP[0]);
                    //身份证图片
                    if (strIDCardInfo.dwPicDataLen > 0) {
                        logger.info("case::"+ HCNetSDK.COMM_ID_INFO_ALARM +" ,身份证图片即将写入");
                        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                        String newName = sf.format(new Date());
                        FileOutputStream fout;
                        try {
                            // 身份证人脸
                            // 验证通过105  失败112
                            if (strIDCardInfo.dwMinor == 105 || strIDCardInfo.dwMinor == 112) {
                                String filename = baseUrl + visit + "_byCardNo[" + new String(strIDCardInfo.struIDCardCfg.byIDNum).trim() +
                                        "_" + newName + "_IDInfoPic.jpg";
                                String realPath = baseUrl + visit;
                                File dest = new File(realPath);
                                //判断文件目录是否存在
                                if (!dest.isDirectory() && !dest.exists()) {
                                    dest.mkdirs();
                                }
                                fout = new FileOutputStream(filename);


                                //将字节写入文件
                                long offset = 0;
                                ByteBuffer buffers = strIDCardInfo.pPicData.getByteBuffer(offset, strIDCardInfo.dwPicDataLen);
                                byte[] bytes = new byte[strIDCardInfo.dwPicDataLen];
                                buffers.rewind();
                                buffers.get(bytes);
                                fout.write(bytes);
                                fout.close();
                                logger.info("case::"+ HCNetSDK.COMM_ID_INFO_ALARM +" ,身份证图片写入完成");
                            }
                        } catch (FileNotFoundException e) {
                            // TODO Auto-generated catch block
                            logger.warn("case::"+ HCNetSDK.COMM_ID_INFO_ALARM +" ,FileNotFoundException："+e.getMessage());
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            logger.warn("case::"+ HCNetSDK.COMM_ID_INFO_ALARM +" ,IOException："+e.getMessage());
                            e.printStackTrace();
                        }
                    }

                    // 闸机抓拍人脸图片
                    if (strIDCardInfo.dwCapturePicDataLen > 0) {
                        logger.info("case::"+ HCNetSDK.COMM_ID_INFO_ALARM +" ,闸机抓拍人脸图片即将写入");
                        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                        String newName = sf.format(new Date());
                        FileOutputStream fout;
                        try {
                            // 抓拍的人脸
                            //验证通过105  失败112
                            if (strIDCardInfo.dwMinor == 105||strIDCardInfo.dwMinor == 112) {
                                String filename = baseUrl + visit + "_byCardNo[" + new String(strIDCardInfo.struIDCardCfg.byIDNum).trim() +
                                        "_" + newName + "_IDInfoCapturePic.jpg";
                                String realPath = baseUrl + visit;
                                File dest = new File(realPath);
                                //判断文件目录是否存在
                                if (!dest.isDirectory() && !dest.exists()) {
                                    dest.mkdirs();
                                }
                                fout = new FileOutputStream(filename);
                                //将字节写入文件
                                long offset = 0;
                                ByteBuffer buffers = strIDCardInfo.pCapturePicData.getByteBuffer(offset, strIDCardInfo.dwCapturePicDataLen);
                                byte[] bytes = new byte[strIDCardInfo.dwCapturePicDataLen];

                                Visitor visitor = new Visitor();
                                visitor.setVisitCardNumber(new String(strIDCardInfo.struIDCardCfg.byIDNum).trim());
                                visitor.setVisitName(new String(strIDCardInfo.struIDCardCfg.byName).trim());
                                visitor.setVisitSex((int) strIDCardInfo.struIDCardCfg.bySex);
                                String nation = null;
//                                strIDCardInfo.struIDCardCfg.byNation
                                switch (strIDCardInfo.struIDCardCfg.byNation) {
                                    case 1:
                                        nation = "汉族";
                                        break;
                                    case 2:
                                        nation = "蒙古族";
                                        break;
                                    case 3:
                                        nation = "回族";
                                        break;
                                    case 4:
                                        nation = "藏族";
                                        break;
                                    case 5:
                                        nation = "维吾尔族";
                                        break;
                                    case 6:
                                        nation = "苗族";
                                        break;
                                    case 7:
                                        nation = "彝族";
                                        break;
                                    case 8:
                                        nation = "壮族";
                                        break;
                                    case 9:
                                        nation = "布依族";
                                        break;
                                    case 10:
                                        nation = "朝鲜族";
                                        break;
                                    case 11:
                                        nation = "满族";
                                        break;
                                    case 12:
                                        nation = "侗族";
                                        break;
                                    case 13:
                                        nation = "瑶族";
                                        break;
                                    case 14:
                                        nation = "白族";
                                        break;
                                    case 15:
                                        nation = "土家族";
                                        break;
                                    case 16:
                                        nation = "哈尼族";
                                        break;
                                    case 17:
                                        nation = "哈萨克族";
                                        break;
                                    case 18:
                                        nation = "傣族";
                                        break;
                                    case 19:
                                        nation = "黎族";
                                        break;
                                    case 20:
                                        nation = "傈僳族";
                                        break;
                                    case 21:
                                        nation = "佤族";
                                        break;
                                    case 22:
                                        nation = "畲族";
                                        break;
                                    case 23:
                                        nation = "高山族";
                                        break;
                                    case 24:
                                        nation = "拉祜族";
                                        break;
                                    case 25:
                                        nation = "水族";
                                        break;
                                    case 26:
                                        nation = "东乡族";
                                        break;
                                    case 27:
                                        nation = "纳西族";
                                        break;
                                    case 28:
                                        nation = "景颇族";
                                        break;
                                    case 29:
                                        nation = "柯尔克孜族";
                                        break;
                                    case 30:
                                        nation = "土族";
                                        break;
                                    case 31:
                                        nation = "达翰尔族";
                                        break;
                                    case 32:
                                        nation = "仫佬族";
                                        break;
                                    case 33:
                                        nation = "羌族";
                                        break;
                                    case 34:
                                        nation = "布朗族";
                                        break;
                                    case 35:
                                        nation = "撒拉族";
                                        break;
                                    case 36:
                                        nation = "毛南族";
                                        break;
                                    case 37:
                                        nation = "仡佬族";
                                        break;
                                    case 38:
                                        nation = "锡伯族";
                                        break;
                                    case 39:
                                        nation = "阿昌族";
                                        break;
                                    case 40:
                                        nation = "普米族";
                                        break;
                                    case 41:
                                        nation = "塔吉克族";
                                        break;
                                    case 42:
                                        nation = "怒族";
                                        break;
                                    case 43:
                                        nation = "乌兹别克族";
                                        break;
                                    case 44:
                                        nation = "俄罗斯族";
                                        break;
                                    case 45:
                                        nation = "鄂温克族";
                                        break;
                                    case 46:
                                        nation = "德昂族";
                                        break;
                                    case 47:
                                        nation = "保安族";
                                        break;
                                    case 48:
                                        nation = "裕固族";
                                        break;
                                    case 49:
                                        nation = "京族";
                                        break;
                                    case 50:
                                        nation = "塔塔尔族";
                                        break;
                                    case 51:
                                        nation = "独龙族";
                                        break;
                                    case 52:
                                        nation = "鄂伦春族";
                                        break;
                                    case 53:
                                        nation = "赫哲族";
                                        break;
                                    case 54:
                                        nation = "门巴族";
                                        break;
                                    case 55:
                                        nation = "珞巴族";
                                        break;
                                    case 56:
                                        nation = "基诺族";
                                        break;
                                }
                                visitor.setVisitNation(nation);
                                String filename2 = access + "_byCardNo[" + new String(strIDCardInfo.struIDCardCfg.byIDNum).trim() +
                                        "_" + newName + "_IDInfoCapturePic.jpg";
                                String filename3 =access + "_byCardNo[" + new String(strIDCardInfo.struIDCardCfg.byIDNum).trim() +
                                        "_" + newName + "_IDInfoPic.jpg";
                                visitor.setVisitPicture(filename2);// 闸机人脸
                                visitor.setCardPhoto(filename3); // 身份证人脸
                                String url = "http://"+localpath+":"+port+filename2;
                                visitor.setFaceSrc(url);// 本地闸机人脸
                                visitor.setVisitTime(today);
                                visitor.setVisitCardType(1);
                                visitor.setDeviceIP(sIP[0]);
                                // 如果人证失败 入库之前需要补卡
                                if (strIDCardInfo.dwMinor == 105) {
                                    logger.info("case::"+ HCNetSDK.COMM_ID_INFO_ALARM +" ,认证对比成功");
                                    if (visitor != null && !"".equals(visitor)) {
                                        queryHKData(visitor);
                                        logger.info("case::"+ HCNetSDK.COMM_ID_INFO_ALARM +" ,人员信息入库今日访客完成");
                                    }
                                    //添加人脸信息
                                    byte byMonth = strIDCardInfo.struIDCardCfg.struBirth.byMonth;
                                    byte byDay = strIDCardInfo.struIDCardCfg.struBirth.byDay;
                                    String month1 = "";
                                    if (byMonth < 10) {
                                        month1 = "0" + byMonth;
                                    } else {
                                        month1 = "" + byMonth;
                                    }
                                    String day = "";
                                    if (byDay < 10) {
                                        day = "0" + byDay;
                                    } else {
                                        day = "" + byDay;
                                    }
                                    String bornTime = strIDCardInfo.struIDCardCfg.struBirth.wYear + "-" + month1 + "-" + day;
                                    visitor.setBornTime(bornTime);
                                    logger.info("case::"+ HCNetSDK.COMM_ID_INFO_ALARM +" ,添加人像信息到脸谱");
                                    rabbitTemplate.convertAndSend("topic_face_add", visitor);
                                    logger.info("case::"+ HCNetSDK.COMM_ID_INFO_ALARM +" ,添加人像信息到脸谱成功");
                                } else if (strIDCardInfo.dwMinor == 112) {
                                    logger.info("case::"+ HCNetSDK.COMM_ID_INFO_ALARM +" ,认证信息对比失败，补卡登记");
                                    // 补卡登记
                                    rabbitTemplate.convertAndSend("topic_repair_card", visitor);
                                    logger.info("case::"+ HCNetSDK.COMM_ID_INFO_ALARM +" ,补卡登记完成");
                                }

                                buffers.rewind();
                                buffers.get(bytes);
                                fout.write(bytes);
                                fout.close();
                            }

                        } catch (FileNotFoundException e) {
                            // TODO Auto-generated catch block
                            logger.warn("case::"+ HCNetSDK.COMM_ID_INFO_ALARM +" ,FileNotFoundException："+e.getMessage());
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            logger.warn("case::"+ HCNetSDK.COMM_ID_INFO_ALARM +" ,IOException："+e.getMessage());
                            e.printStackTrace();
                        }catch (Exception e){
                            logger.warn("case::"+ HCNetSDK.COMM_ID_INFO_ALARM +" ,Exception："+e.getMessage());
                            e.printStackTrace();
                        }
                    }
                    break;
                case HCNetSDK.COMM_ISAPI_ALARM: //ISAPI协议报警信息
                    HCNetSDK.NET_DVR_ALARM_ISAPI_INFO struEventISAPI = new HCNetSDK.NET_DVR_ALARM_ISAPI_INFO();
                    struEventISAPI.write();
                    Pointer pEventISAPI = struEventISAPI.getPointer();
                    pEventISAPI.write(0, pAlarmInfo.getByteArray(0, struEventISAPI.size()), 0, struEventISAPI.size());
                    struEventISAPI.read();

                    sAlarmType = sAlarmType + "：ISAPI协议报警信息, 数据格式:" + struEventISAPI.byDataType +
                            ", 图片个数:" + struEventISAPI.byPicturesNumber;

                    data.put("datetime", dateFormat.format(today));
                    //报警类型
                    data.put("alermType", sAlarmType);
                    //报警设备IP地址
                    sIP = new String(pAlarmer.sDeviceIP).split("\0", 2);
                    data.put("ipaddr", sIP[0]);

                    SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMddHHmmss");
                    String curTime = sf1.format(new Date());
                    FileOutputStream foutdata;
                    try {
                        String jsonfilename = System.getProperty("user.dir") + "\\pic\\" + new String(pAlarmer.sDeviceIP).trim() + curTime + "_ISAPI_Alarm_" + ".json";
                        foutdata = new FileOutputStream(jsonfilename);
                        //将字节写入文件
                        ByteBuffer jsonbuffers = struEventISAPI.pAlarmData.getByteBuffer(0, struEventISAPI.dwAlarmDataLen);
                        byte[] jsonbytes = new byte[struEventISAPI.dwAlarmDataLen];
                        jsonbuffers.rewind();
                        jsonbuffers.get(jsonbytes);
                        foutdata.write(jsonbytes);
                        foutdata.close();
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    for (int i = 0; i < struEventISAPI.byPicturesNumber; i++) {
                        HCNetSDK.NET_DVR_ALARM_ISAPI_PICDATA struPicData = new HCNetSDK.NET_DVR_ALARM_ISAPI_PICDATA();
                        struPicData.write();
                        Pointer pPicData = struPicData.getPointer();
                        pPicData.write(0, struEventISAPI.pPicPackData.getByteArray(i * struPicData.size(), struPicData.size()), 0, struPicData.size());
                        struPicData.read();

                        FileOutputStream fout;
                        try {
                            String filename = System.getProperty("user.dir") + "\\pic\\" + new String(pAlarmer.sDeviceIP).trim() + curTime +
                                    "_ISAPIPic_" + i + "_" + new String(struPicData.szFilename).trim() + ".jpg";
                            fout = new FileOutputStream(filename);
                            //将字节写入文件
                            long offset = 0;
                            ByteBuffer buffers = struPicData.pPicData.getByteBuffer(offset, struPicData.dwPicLen);
                            byte[] bytes = new byte[struPicData.dwPicLen];
                            buffers.rewind();
                            buffers.get(bytes);
                            fout.write(bytes);
                            fout.close();
                        } catch (FileNotFoundException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    break;
                default:
                    data.put("datetime", dateFormat.format(today));
                    //报警类型
                    data.put("alermType", sAlarmType);
                    //报警设备IP地址
                    sIP = new String(pAlarmer.sDeviceIP).split("\0", 2);
                    data.put("ipaddr", sIP[0]);
                    break;
            }

            logger.info("a new data recv...");
            logger.info("datetime:" + data.get("datetime"));
            logger.info("alermType:" + data.get("alermType"));
            logger.info("ipaddr:" + data.get("ipaddr"));
            logger.info("end.");

        } catch (UnsupportedEncodingException ex) {
            //Logger.getLogger("main").log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 名单下发
     * 白鹏7979
     * 调用海康接口
     */
    @Override
    public boolean updateHikDevice(String name, String number ,String visitPicture) {
        if (hashMap.size() > 0 ) {
            for (Object value : hashMap.values()) {
                if((HKMainClass)value!=null&&!"".equals((HKMainClass)value)){
                    if (name!=null&&!"".equals(name)){
                        try {
                            boolean b = setCardInfo((HKMainClass)value, name, number);
                            if (b==false){
                                return b;
                            } else {
                                boolean b1 = setFaceCfg((HKMainClass)value, number, visitPicture);
                                if (b1==false){
                                    return b1;
                                }
                            }
                            return true;
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 设备注册--
     * @param device
     * @return ret(-1:注册失败 -2:布防失败)
     */
    public long initDevice (Device device)  {
        logger.info("inteDevice now is " + device.getDeviceip());
        hashMap.put(device.getDeviceip(),new HKMainClass(device.getDeviceip(),device.getDevicePort(),device.getDeviceLogin(),device.getDevicePass()));
        long ret = 0;
        ret = reg_dev((HKMainClass) hashMap.get(device.getDeviceip()));
        if(ret == -1) {
            logger.info("Reg Device Error...");
            deviceList.add(device);
            return ret;
        }
        logger.info("Hikvison Device Register OK!");
        // 将闸机类型的设备存储，用于进场名单下发
        if (device.getDeviceType() == 1002 || device.getDeviceType() == 1003) {
            hashMap.put(device.getDeviceip(),(HKMainClass) hashMap.get(device.getDeviceip()));
        }
        ret = setup_alerm((HKMainClass) hashMap.get(device.getDeviceip()));
        if(ret == -2) {
            logger.info("Wraning Setup Alerm No Register...");
            deviceList.add(device);
            return ret;
        } else if(ret == -1) {
            logger.info("Error Setup Alerm...");
            deviceList.add(device);
            return ret;
        }
        if (deviceList != null) {
            logger.info("initDevice：device err list size"+deviceList.size());
        }
        logger.info("Hikvison Device Setup Alerm OK!");
        logger.info("Loop starting...");
        return ret;
    }

    /**
     * 定时任务执行设备注册失败---时间间隔1分钟
     */
    @Scheduled(initialDelay = 1000*60,fixedRate = 1000*60)
    private void initTask () {
        logger.info("initTask is running" + new Date());
        // 判断当前是否有 注册失败的设备
        if (deviceList !=null &&  deviceList.size() > 0) {
            // 循环注册 失败设备
            logger.info("now is into for initDevice");
            logger.info("err size " + deviceList.size());
            for (int i = 0;i<deviceList.size();i++) {
                long res = this.initFor(deviceList.get(i));
                if (res == 0) {
                    deviceList.remove(i);
                }
            }
        }
        logger.info("initTask is finish");
    }

    /**
     * 循环设备注册使用方法
     * @param device
     * @return
     */
    public long initFor (Device device)  {
        logger.info("initFor now is " + device.getDeviceip());
        hashMap.put(device.getDeviceip(),new HKMainClass(device.getDeviceip(),device.getDevicePort(),device.getDeviceLogin(),device.getDevicePass()));
        long ret = 0;
        ret = reg_dev((HKMainClass) hashMap.get(device.getDeviceip()));
        if(ret == -1) {
            logger.info("Reg Device Error...");
            return ret;
        }
        logger.info("Hikvison Device Register OK!");
        // 将闸机类型的设备存储，用于进场名单下发
        if (device.getDeviceType() == 1002 || device.getDeviceType() == 1003) {
            hashMap.put(device.getDeviceip(),(HKMainClass) hashMap.get(device.getDeviceip()));
        }
        ret = setup_alerm((HKMainClass) hashMap.get(device.getDeviceip()));
        if(ret == -2) {
            logger.info("Wraning Setup Alerm No Register...");
            return ret;
        } else if(ret == -1) {
            logger.info("Error Setup Alerm...");
            return ret;
        }
        if (deviceList != null) {
            logger.info("initFor：device err list size"+deviceList.size());
        }
        logger.info("Hikvison Device Setup Alerm OK!");
        logger.info("Loop starting...");
        return ret;
    }

    /**
     * 名单下发----指定设备 zsa
     * @param deviceIp 设备ip
     * @param name 名字
     * @param number 证件号
     * @param pic 照片
     * @return
     */
    @Override
    public boolean sendPer(String deviceIp, String name,String number,String pic) {
        if (hashMap.size() > 0 ) {
            if((HKMainClass)hashMap.get(deviceIp)!=null&&!"".equals((HKMainClass)hashMap.get(deviceIp))){
                if (name!=null&&!"".equals(name)){
                    try {
                        boolean b = setCardInfo((HKMainClass)hashMap.get(deviceIp), name, number);
                        if (b==false){
                            return b;
                        } else {
                            boolean b1 = setFaceCfg((HKMainClass)hashMap.get(deviceIp), number,pic);
                            if (b1==false){
                                return b1;
                            }
                        }
                        return true;
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return false;
    }

    //现在用的下发名单
    public HashMap sendPerNew(String deviceIp, String name,String number,String pic) {
        HashMap<String,Object> resultMap = new HashMap();
        boolean is = false;
        if (hashMap.size() > 0 ) {
            if ((HKMainClass) hashMap.get(deviceIp) != null && !"".equals((HKMainClass) hashMap.get(deviceIp))) {
                if (name != null && !"".equals(name)) {
                    try {
                        boolean b = setCardInfo((HKMainClass) hashMap.get(deviceIp), name, number);
                        if (b == false) {
                            is = b;
                        } else {
                            boolean b1 = setFaceCfg((HKMainClass) hashMap.get(deviceIp), number, pic);
                            if (b1 == false) {
                                is = b1;
                            }
                        }
                        is = true;
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        resultMap.put("is",is);
        resultMap.put("result", this.errorCode);
        return resultMap;
    }

    public class FGPSDataCallback implements HCNetSDK.fGPSDataCallback {
        public void invoke(int nHandle, int dwState, Pointer lpBuffer, int dwBufLen, Pointer pUser) {
        }
    }

    public class FMSGCallBack_V31 implements HCNetSDK.FMSGCallBack_V31 {
        //报警信息回调函数

        public boolean invoke(int lCommand, HCNetSDK.NET_DVR_ALARMER pAlarmer, Pointer pAlarmInfo, int dwBufLen, Pointer pUser) {
            AlarmDataHandle(lCommand, pAlarmer, pAlarmInfo, dwBufLen, pUser);
            return true;
        }
    }

    public class FMSGCallBack implements HCNetSDK.FMSGCallBack {
        //报警信息回调函数

        public void invoke(int lCommand, HCNetSDK.NET_DVR_ALARMER pAlarmer, Pointer pAlarmInfo, int dwBufLen, Pointer pUser) {
            AlarmDataHandle(lCommand, pAlarmer, pAlarmInfo, dwBufLen, pUser);
        }
    }

    public class FRemoteCfgCallBackCardGet implements HCNetSDK.FRemoteConfigCallback
    {
        public void invoke(int dwType, Pointer lpBuffer, int dwBufLen, Pointer pUserData)
        {
            HCNetSDK.MY_USER_DATA m_userData = new HCNetSDK.MY_USER_DATA();
            m_userData.write();
            Pointer pUserVData = m_userData.getPointer();
            pUserVData.write(0, pUserData.getByteArray(0, m_userData.size()), 0, m_userData.size());
            m_userData.read();

            logger.info("长连接回调获取数据,NET_SDK_CALLBACK_TYPE_STATUS:" + dwType);
            switch (dwType){
                case 0: //NET_SDK_CALLBACK_TYPE_STATUS
                    HCNetSDK.REMOTECONFIGSTATUS_CARD struCfgStatus  = new HCNetSDK.REMOTECONFIGSTATUS_CARD();
                    struCfgStatus.write();
                    Pointer pCfgStatus = struCfgStatus.getPointer();
                    pCfgStatus.write(0, lpBuffer.getByteArray(0, struCfgStatus.size()), 0,struCfgStatus.size());
                    struCfgStatus.read();

                    int iStatus = 0;
                    for(int i=0;i<4;i++)
                    {
                        int ioffset = i*8;
                        int iByte = struCfgStatus.byStatus[i]&0xff;
                        iStatus = iStatus + (iByte << ioffset);
                    }

                    switch (iStatus){
                        case 1000:// NET_SDK_CALLBACK_STATUS_SUCCESS
                            logger.info("查询卡参数成功,dwStatus:" + iStatus);
                            break;
                        case 1001:
                            logger.info("正在查询卡参数中,dwStatus:" + iStatus);
                            break;
                        case 1002:
                            int iErrorCode = 0;
                            for(int i=0;i<4;i++)
                            {
                                int ioffset = i*8;
                                int iByte = struCfgStatus.byErrorCode[i]&0xff;
                                iErrorCode = iErrorCode + (iByte << ioffset);
                            }
                            logger.info("查询卡参数失败, dwStatus:" + iStatus + "错误号:" + iErrorCode);
                            break;
                    }
                    break;
                case 2: //NET_SDK_CALLBACK_TYPE_DATA
                    HCNetSDK.NET_DVR_CARD_CFG_V50 m_struCardInfo = new HCNetSDK.NET_DVR_CARD_CFG_V50();
                    m_struCardInfo.write();
                    Pointer pInfoV30 = m_struCardInfo.getPointer();
                    pInfoV30.write(0, lpBuffer.getByteArray(0, m_struCardInfo.size()), 0,m_struCardInfo.size());
                    m_struCardInfo.read();

                    String str = new String(m_struCardInfo.byCardNo);


                    try {
                        String srtName=new String(m_struCardInfo.byName,"GBK").trim(); //姓名
                        logger.info("查询到的卡号,getCardNo:" + str + "姓名:" + srtName);
                    }
                    catch (UnsupportedEncodingException e1) {
                        // TODO Auto-generated catch block
                        logger.warn("FRemoteCfgCallBackCardGet::UnsupportedEncodingException"+e1.getMessage());
                        e1.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        logger.warn("FRemoteCfgCallBackCardGet::UnsupportedEncodingException"+e.getMessage());
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public class FRemoteCfgCallBackCardSet implements HCNetSDK.FRemoteConfigCallback
    {
        public void invoke(int dwType, Pointer lpBuffer, int dwBufLen, Pointer pUserData)
        {
            logger.info("长连接回调获取数据,NET_SDK_CALLBACK_TYPE_STATUS:" + dwType);
            switch (dwType){
                case 0:// NET_SDK_CALLBACK_TYPE_STATUS
                    HCNetSDK.REMOTECONFIGSTATUS_CARD struCardStatus = new HCNetSDK.REMOTECONFIGSTATUS_CARD();
                    struCardStatus.write();
                    Pointer pInfoV30 = struCardStatus.getPointer();
                    pInfoV30.write(0, lpBuffer.getByteArray(0, struCardStatus.size()), 0,struCardStatus.size());
                    struCardStatus.read();

                    int iStatus = 0;
                    for(int i=0;i<4;i++)
                    {
                        int ioffset = i*8;
                        int iByte = struCardStatus.byStatus[i]&0xff;
                        iStatus = iStatus + (iByte << ioffset);
                    }

                    switch (iStatus){
                        case 1000:// NET_SDK_CALLBACK_STATUS_SUCCESS
                            logger.info("下发卡参数成功,dwStatus:" + iStatus);
                            break;
                        case 1001:
                            logger.info("正在下发卡参数中,dwStatus:" + iStatus);
                            break;
                        case 1002:
                            int iErrorCode = 0;
                            for(int i=0;i<4;i++)
                            {
                                int ioffset = i*8;
                                int iByte = struCardStatus.byErrorCode[i]&0xff;
                                iErrorCode = iErrorCode + (iByte << ioffset);
                            }
                            logger.info("下发卡参数失败, dwStatus:" + iStatus + "错误号:" + iErrorCode);
                            break;
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public class FRemoteCfgCallBackFaceGet implements HCNetSDK.FRemoteConfigCallback
    {
        public void invoke(int dwType, Pointer lpBuffer, int dwBufLen, Pointer pUserData)
        {
            logger.info("长连接回调获取数据,NET_SDK_CALLBACK_TYPE_STATUS:" + dwType);
            switch (dwType){
                case 0:// NET_SDK_CALLBACK_TYPE_STATUS
                    HCNetSDK.REMOTECONFIGSTATUS_CARD struCfgStatus  = new HCNetSDK.REMOTECONFIGSTATUS_CARD();
                    struCfgStatus.write();
                    Pointer pCfgStatus = struCfgStatus.getPointer();
                    pCfgStatus.write(0, lpBuffer.getByteArray(0, struCfgStatus.size()), 0,struCfgStatus.size());
                    struCfgStatus.read();

                    int iStatus = 0;
                    for(int i=0;i<4;i++)
                    {
                        int ioffset = i*8;
                        int iByte = struCfgStatus.byStatus[i]&0xff;
                        iStatus = iStatus + (iByte << ioffset);
                    }

                    switch (iStatus){
                        case 1000:// NET_SDK_CALLBACK_STATUS_SUCCESS
                            logger.info("查询人脸参数成功,dwStatus:" + iStatus);
                            break;
                        case 1001:
                            logger.info("正在查询人脸参数中,dwStatus:" + iStatus);
                            break;
                        case 1002:
                            int iErrorCode = 0;
                            for(int i=0;i<4;i++)
                            {
                                int ioffset = i*8;
                                int iByte = struCfgStatus.byErrorCode[i]&0xff;
                                iErrorCode = iErrorCode + (iByte << ioffset);
                            }
                            logger.info("查询人脸参数失败, dwStatus:" + iStatus + "错误号:" + iErrorCode);
                            break;
                    }
                    break;
                case 2: //NET_SDK_CALLBACK_TYPE_DATA
                    HCNetSDK.NET_DVR_FACE_PARAM_CFG m_struFaceInfo = new HCNetSDK.NET_DVR_FACE_PARAM_CFG();
                    m_struFaceInfo.write();
                    Pointer pInfoV30 = m_struFaceInfo.getPointer();
                    pInfoV30.write(0, lpBuffer.getByteArray(0, m_struFaceInfo.size()), 0,m_struFaceInfo.size());
                    m_struFaceInfo.read();
                    String str = new String(m_struFaceInfo.byCardNo).trim();
                    logger.info("查询到人脸数据关联的卡号,getCardNo:" + str + ",人脸数据类型:" + m_struFaceInfo.byFaceDataType);
                    if(m_struFaceInfo.dwFaceLen >0)
                    {
                        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                        String newName = sf.format(new Date());
                        FileOutputStream fout;
                        try {
                            fout = new FileOutputStream(newName +"_Card[" + str + "]_ACSFaceCfg.jpg");
                            //将字节写入文件
                            long offset = 0;
                            ByteBuffer buffers = m_struFaceInfo.pFaceBuffer.getByteBuffer(offset, m_struFaceInfo.dwFaceLen);
                            byte [] bytes = new byte[m_struFaceInfo.dwFaceLen];
                            buffers.rewind();
                            buffers.get(bytes);
                            fout.write(bytes);
                            fout.close();
                        } catch (FileNotFoundException e) {
                            // TODO Auto-generated catch block
                            logger.warn("FRemoteCfgCallBackFaceGet::FileNotFoundException"+e.getMessage());
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            logger.warn("FRemoteCfgCallBackFaceGet::IOException"+e.getMessage());
                            e.printStackTrace();
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public class FRemoteCfgCallBackFaceSet implements HCNetSDK.FRemoteConfigCallback
    {
        public String callbackInfo = null;
        public void invoke(int dwType, Pointer lpBuffer, int dwBufLen, Pointer pUserData)
        {
            logger.info("长连接回调获取数据,NET_SDK_CALLBACK_TYPE_STATUS:" + dwType);
            switch (dwType){
                case 0:// NET_SDK_CALLBACK_TYPE_STATUS
                    HCNetSDK.REMOTECONFIGSTATUS_CARD struCfgStatus  = new HCNetSDK.REMOTECONFIGSTATUS_CARD();
                    struCfgStatus.write();
                    Pointer pCfgStatus = struCfgStatus.getPointer();
                    pCfgStatus.write(0, lpBuffer.getByteArray(0, struCfgStatus.size()), 0,struCfgStatus.size());
                    struCfgStatus.read();

                    int iStatus = 0;
                    for(int i=0;i<4;i++)
                    {
                        int ioffset = i*8;
                        int iByte = struCfgStatus.byStatus[i]&0xff;
                        iStatus = iStatus + (iByte << ioffset);
                    }

                    switch (iStatus){
                        case 1000:// NET_SDK_CALLBACK_STATUS_SUCCESS
                            logger.info("下发人脸参数成功,dwStatus:" + iStatus);
                            break;
                        case 1001:
                            logger.info("正在下发人脸参数中,dwStatus:" + iStatus);
                            break;
                        case 1002:
                            int iErrorCode = 0;
                            for(int i=0;i<4;i++)
                            {
                                int ioffset = i*8;
                                int iByte = struCfgStatus.byErrorCode[i]&0xff;
                                iErrorCode = iErrorCode + (iByte << ioffset);
                            }
                            callbackInfo = "dwStatus:" + iStatus;
                            logger.info("下发人脸参数失败, dwStatus:" + iStatus + "错误号:" + iErrorCode);
                            break;
                    }
                    break;
                case 2:// 获取状态数据
                    HCNetSDK.NET_DVR_FACE_PARAM_STATUS  m_struFaceStatus = new HCNetSDK.NET_DVR_FACE_PARAM_STATUS();
                    m_struFaceStatus.write();
                    Pointer pStatusInfo = m_struFaceStatus.getPointer();
                    pStatusInfo.write(0, lpBuffer.getByteArray(0, m_struFaceStatus.size()), 0,m_struFaceStatus.size());
                    m_struFaceStatus.read();
                    String str = new String(m_struFaceStatus.byCardNo).trim();
                    callbackInfo = str + "-" + m_struFaceStatus.byCardReaderRecvStatus[0];
                    logger.info("下发人脸数据关联的卡号:" + str + ",人脸读卡器状态:" +
                            m_struFaceStatus.byCardReaderRecvStatus[0] + ",错误描述:" + new String(m_struFaceStatus.byErrorMsg).trim());
                default:
                    break;
            }
        }
    }

    public class FRemoteCfgCallBackFaceCapture implements HCNetSDK.FRemoteConfigCallback
    {
        public void invoke(int dwType, Pointer lpBuffer, int dwBufLen, Pointer pUserData)
        {
            logger.info("长连接回调获取数据,NET_SDK_CALLBACK_TYPE_STATUS:" + dwType);
            switch (dwType)
            {
                case 0:// NET_SDK_CALLBACK_TYPE_STATUS
                    HCNetSDK.REMOTECONFIGSTATUS_CARD struCfgStatus  = new HCNetSDK.REMOTECONFIGSTATUS_CARD();
                    struCfgStatus.write();
                    Pointer pCfgStatus = struCfgStatus.getPointer();
                    pCfgStatus.write(0, lpBuffer.getByteArray(0, struCfgStatus.size()), 0,struCfgStatus.size());
                    struCfgStatus.read();

                    int iStatus = 0;
                    for(int i=0;i<4;i++)
                    {
                        int ioffset = i*8;
                        int iByte = struCfgStatus.byStatus[i]&0xff;
                        iStatus = iStatus + (iByte << ioffset);
                    }

                    switch (iStatus){
                        case 1000:// NET_SDK_CALLBACK_STATUS_SUCCESS
                            logger.info("采集人脸信息成功,dwStatus:" + iStatus);
                            break;
                        case 1001:
                            logger.info("正在采集人脸信息中,dwStatus:" + iStatus);
                            break;
                        case 1002:
                            logger.info("采集人脸信息失败, dwStatus:" + iStatus);
                            break;
                    }
                    break;
                case 2:// 获取状态数据
                    HCNetSDK.NET_DVR_CAPTURE_FACE_CFG struFaceCfg = new HCNetSDK.NET_DVR_CAPTURE_FACE_CFG();
                    struFaceCfg.write();
                    Pointer pStatusInfo = struFaceCfg.getPointer();
                    pStatusInfo.write(0, lpBuffer.getByteArray(0, struFaceCfg.size()), 0, struFaceCfg.size());
                    struFaceCfg.read();
                    logger.info("采集进度:" + struFaceCfg.byCaptureProgress + ",人脸图片数据大小:" + struFaceCfg.dwFacePicSize);
                    if((struFaceCfg.byCaptureProgress==100)&&(struFaceCfg.dwFacePicSize>0))
                    {
                        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                        String newName = sf.format(new Date());
                        FileOutputStream fout;
                        try {
                            fout = new FileOutputStream(newName+"_CaptureFacePic.jpg");
                            //将字节写入文件
                            long offset = 0;
                            ByteBuffer buffers = struFaceCfg.pFacePicBuffer.getByteBuffer(offset, struFaceCfg.dwFacePicSize);
                            byte [] bytes = new byte[struFaceCfg.dwFacePicSize];
                            buffers.rewind();
                            buffers.get(bytes);
                            fout.write(bytes);
                            fout.close();
                        }catch (FileNotFoundException e) {
                            // TODO Auto-generated catch block
                            logger.warn("FRemoteCfgCallBackFaceCapture::FileNotFoundException"+e.getMessage());
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            logger.warn("FRemoteCfgCallBackFaceCapture::IOException"+e.getMessage());
                            e.printStackTrace();
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public class FRemoteCfgCallBackFingerPrint implements HCNetSDK.FRemoteConfigCallback
    {
        public void invoke(int dwType, Pointer lpBuffer, int dwBufLen, Pointer pUserData)
        {
            logger.info("长连接回调获取数据,NET_SDK_CALLBACK_TYPE_STATUS:" + dwType);
            switch (dwType)
            {
                case 0:// NET_SDK_CALLBACK_TYPE_STATUS
                    HCNetSDK.REMOTECONFIGSTATUS_CARD struCfgStatus  = new HCNetSDK.REMOTECONFIGSTATUS_CARD();
                    struCfgStatus.write();
                    Pointer pCfgStatus = struCfgStatus.getPointer();
                    pCfgStatus.write(0, lpBuffer.getByteArray(0, struCfgStatus.size()), 0,struCfgStatus.size());
                    struCfgStatus.read();

                    int iStatus = 0;
                    for(int i=0;i<4;i++)
                    {
                        int ioffset = i*8;
                        int iByte = struCfgStatus.byStatus[i]&0xff;
                        iStatus = iStatus + (iByte << ioffset);
                    }

                    switch (iStatus){
                        case 1000:// NET_SDK_CALLBACK_STATUS_SUCCESS
                            logger.info("采集指纹信息成功,dwStatus:" + iStatus);
                            break;
                        case 1001:
                            logger.info("正在采集指纹信息中,dwStatus:" + iStatus);
                            break;
                        case 1002:
                            logger.info("采集指纹信息失败, dwStatus:" + iStatus);
                            break;
                    }
                    break;
                case 2:// 获取状态数据
                    HCNetSDK.NET_DVR_CAPTURE_FINGERPRINT_CFG struFingerPrintCfg = new HCNetSDK.NET_DVR_CAPTURE_FINGERPRINT_CFG();
                    struFingerPrintCfg.write();
                    Pointer pStatusInfo = struFingerPrintCfg.getPointer();
                    pStatusInfo.write(0, lpBuffer.getByteArray(0, struFingerPrintCfg.size()), 0, struFingerPrintCfg.size());
                    struFingerPrintCfg.read();
                    logger.info("指纹编号:" + struFingerPrintCfg.byFingerNo + ",指纹质量:" + struFingerPrintCfg.byFingerPrintQuality );
                    if((struFingerPrintCfg.byFingerData != null)&&(struFingerPrintCfg.dwFingerPrintDataSize >0)) //指纹数据
                    {
                        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                        String newName = sf.format(new Date());
                        FileOutputStream fout;
                        try {
                            fout = new FileOutputStream(newName+"_FingerPrint.data");
                            //将字节写入文件
                            fout.write(struFingerPrintCfg.byFingerData);
                            fout.close();
                        }catch (FileNotFoundException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    if((struFingerPrintCfg.pFingerPrintPicBuffer != null)&&(struFingerPrintCfg.dwFingerPrintPicSize >0)) //指纹图片
                    {
                        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                        String newName = sf.format(new Date());
                        FileOutputStream fout;
                        try {
                            fout = new FileOutputStream(newName+"_CaptureFacePic.jpg");
                            //将字节写入文件
                            long offset = 0;
                            ByteBuffer buffers = struFingerPrintCfg.pFingerPrintPicBuffer.getByteBuffer(offset, struFingerPrintCfg.dwFingerPrintPicSize);
                            byte [] bytes = new byte[struFingerPrintCfg.dwFingerPrintPicSize];
                            buffers.rewind();
                            buffers.get(bytes);
                            fout.write(bytes);
                            fout.close();
                        }catch (FileNotFoundException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private long reg_dev(HKMainClass hkMainClass) {
        logger.info("hikvison reg_dev method have been called.");
        //注册之前先注销已注册的用户,预览情况下不可注销
        if (hkMainClass.lUserID > -1) {
            //先注销
            hkMainClass.hCNetSDK.NET_DVR_Logout(hkMainClass.lUserID);
            hkMainClass.lUserID = -1;
        }

        //注册
        hkMainClass.m_strLoginInfo.sDeviceAddress = new byte[HCNetSDK.NET_DVR_DEV_ADDRESS_MAX_LEN];
        System.arraycopy(hkMainClass.m_sDeviceIP.getBytes(), 0, hkMainClass.m_strLoginInfo.sDeviceAddress, 0, hkMainClass.m_sDeviceIP.length());

        hkMainClass.m_strLoginInfo.sUserName = new byte[HCNetSDK.NET_DVR_LOGIN_USERNAME_MAX_LEN];
        System.arraycopy(hkMainClass.m_sUsername.getBytes(), 0, hkMainClass.m_strLoginInfo.sUserName, 0, hkMainClass.m_sUsername.length());

        hkMainClass.m_strLoginInfo.sPassword = new byte[HCNetSDK.NET_DVR_LOGIN_PASSWD_MAX_LEN];
        System.arraycopy(hkMainClass.m_sPassword.getBytes(), 0, hkMainClass.m_strLoginInfo.sPassword, 0, hkMainClass.m_sPassword.length());

        hkMainClass.m_strLoginInfo.wPort = (short) hkMainClass.m_iDevicePort;

        hkMainClass.m_strLoginInfo.bUseAsynLogin = false; //是否异步登录：0- 否，1- 是

        hkMainClass.m_strLoginInfo.write();
        hkMainClass.lUserID = hkMainClass.hCNetSDK.NET_DVR_Login_V40(hkMainClass.m_strLoginInfo, hkMainClass.m_strDeviceInfo);

        if (hkMainClass.lUserID == -1) {
            logger.warn("注册失败，错误号:" + hkMainClass.hCNetSDK.NET_DVR_GetLastError());
        } else {
            logger.info("注册成功");
        }

        return hkMainClass.lUserID;
    }

    public int setup_alerm(HKMainClass hkMainClass) {
        if (hkMainClass.lUserID == -1) {
            logger.warn("请先进行Device注册");
            return -1;
        }
        if (hkMainClass.lAlarmHandle < 0)//尚未布防,需要布防
        {
            if (hkMainClass.fMSFCallBack_V31 == null) {
                hkMainClass.fMSFCallBack_V31 = new FMSGCallBack_V31();
                Pointer pUser = null;
                if (!hkMainClass.hCNetSDK.NET_DVR_SetDVRMessageCallBack_V31(hkMainClass.fMSFCallBack_V31, pUser)) {
                    logger.warn("设置回调函数失败!");
                }
            }
            HCNetSDK.NET_DVR_SETUPALARM_PARAM m_strAlarmInfo = new HCNetSDK.NET_DVR_SETUPALARM_PARAM();
            m_strAlarmInfo.dwSize = m_strAlarmInfo.size();
            m_strAlarmInfo.byLevel = 1;//智能交通布防优先级：0- 一等级（高），1- 二等级（中），2- 三等级（低）
            m_strAlarmInfo.byAlarmInfoType = 1;//智能交通报警信息上传类型：0- 老报警信息（NET_DVR_PLATE_RESULT），1- 新报警信息(NET_ITS_PLATE_RESULT)
            m_strAlarmInfo.byDeployType = 1; //布防类型(仅针对门禁主机、人证设备)：0-客户端布防(会断网续传)，1-实时布防(只上传实时数据)
            m_strAlarmInfo.write();
            hkMainClass.lAlarmHandle = hkMainClass.hCNetSDK.NET_DVR_SetupAlarmChan_V41(hkMainClass.lUserID, m_strAlarmInfo);
            if (hkMainClass.lAlarmHandle == -1) {
                logger.warn("布防失败，错误号:" + hkMainClass.hCNetSDK.NET_DVR_GetLastError());
            } else {
                logger.info("布防成功");
            }
        }

//        try {
//            System.in.read();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        // 以图搜图
//        HCNetSDK.NET_DVR_XML_CONFIG_INPUT lpInput = new HCNetSDK.NET_DVR_XML_CONFIG_INPUT();
//        String strUrl = "/service/Intelligent/analysisImage/face";
//        Pointer pUrl = new Memory(strUrl.toCharArray().length + 100);
//        pUrl.write(0, strUrl.toCharArray(),0, strUrl.toCharArray().length - 1);
//        lpInput.lpRequestUrl = pUrl;
//        HCNetSDK.NET_DVR_XML_CONFIG_OUTPUT lpOutput = null;
//
//        hkMainClass.hCNetSDK.NET_DVR_STDXMLConfig(hkMainClass.lUserID, lpInput, lpOutput);
        return 0;
    }

    /**
     * 下发卡号 姓名 工号
     * @param realName
     * @param cardNo
     * @throws UnsupportedEncodingException
     */
    public boolean setCardInfo(HKMainClass hkMainClass,String realName,String cardNo) throws UnsupportedEncodingException {
        int iErr = 0;
        //设置卡参数
        HCNetSDK.NET_DVR_CARD_CFG_COND m_struCardInputParamSet = new HCNetSDK.NET_DVR_CARD_CFG_COND();
        m_struCardInputParamSet.read();
        m_struCardInputParamSet.dwSize = m_struCardInputParamSet.size();
        m_struCardInputParamSet.dwCardNum = 1;
        m_struCardInputParamSet.byCheckCardNo = 1;
        Pointer lpInBuffer = m_struCardInputParamSet.getPointer();
        m_struCardInputParamSet.write();
        Pointer pUserData = null;
        hkMainClass.fRemoteCfgCallBackCardSet = new FRemoteCfgCallBackCardSet();
        int lHandle = hkMainClass.hCNetSDK.NET_DVR_StartRemoteConfig(hkMainClass.lUserID, HCNetSDK.NET_DVR_SET_CARD_CFG_V50, lpInBuffer, m_struCardInputParamSet.size(), hkMainClass.fRemoteCfgCallBackCardSet, pUserData);
        if (lHandle < 0) {
            iErr = hkMainClass.hCNetSDK.NET_DVR_GetLastError();
            return false;
        }
        HCNetSDK.NET_DVR_CARD_CFG_V50 struCardInfo = new HCNetSDK.NET_DVR_CARD_CFG_V50(); //卡参数
        struCardInfo.read();
        struCardInfo.dwSize = struCardInfo.size();
        struCardInfo.dwModifyParamType = 0x00000001 + 0x00000002 + 0x00000004 + 0x00000008 +
                0x00000010 + 0x00000020 + 0x00000080 + 0x00000100 + 0x00000200 + 0x00000400 + 0x00000800;
        /***
         * #define CARD_PARAM_CARD_VALID       0x00000001  //卡是否有效参数
         * #define CARD_PARAM_VALID            0x00000002  //有效期参数
         * #define CARD_PARAM_CARD_TYPE        0x00000004  //卡类型参数
         * #define CARD_PARAM_DOOR_RIGHT       0x00000008  //门权限参数
         * #define CARD_PARAM_LEADER_CARD      0x00000010  //首卡参数
         * #define CARD_PARAM_SWIPE_NUM        0x00000020  //最大刷卡次数参数
         * #define CARD_PARAM_GROUP            0x00000040  //所属群组参数
         * #define CARD_PARAM_PASSWORD         0x00000080  //卡密码参数
         * #define CARD_PARAM_RIGHT_PLAN       0x00000100  //卡权限计划参数
         * #define CARD_PARAM_SWIPED_NUM       0x00000200  //已刷卡次数
         * #define CARD_PARAM_EMPLOYEE_NO      0x00000400  //工号
         * #define CARD_PARAM_NAME             0x00000800  //姓名
         */
        for (int i = 0; i < HCNetSDK.ACS_CARD_NO_LEN; i++) {
            struCardInfo.byCardNo[i] = 0;
        }
        for (int i = 0; i < cardNo.length(); i++) {
            struCardInfo.byCardNo[i] = cardNo.getBytes()[i];
        }
        struCardInfo.byCardValid = 1;
        struCardInfo.byCardType = 1;
        struCardInfo.byLeaderCard = 0;
        struCardInfo.byDoorRight[0] = 1; //门1有权限
        struCardInfo.wCardRightPlan[0].wRightPlan[0] = 1; //门1关联卡参数计划模板1
        //卡有效期
        struCardInfo.struValid.byEnable = 1;
        struCardInfo.struValid.struBeginTime.wYear = 2018;
        struCardInfo.struValid.struBeginTime.byMonth = 12;
        struCardInfo.struValid.struBeginTime.byDay = 1;
        struCardInfo.struValid.struBeginTime.byHour = 0;
        struCardInfo.struValid.struBeginTime.byMinute = 0;
        struCardInfo.struValid.struBeginTime.bySecond = 0;
        struCardInfo.struValid.struEndTime.wYear = 2025;
        struCardInfo.struValid.struEndTime.byMonth = 12;
        struCardInfo.struValid.struEndTime.byDay = 1;
        struCardInfo.struValid.struEndTime.byHour = 0;
        struCardInfo.struValid.struEndTime.byMinute = 0;
        struCardInfo.struValid.struEndTime.bySecond = 0;
        struCardInfo.dwMaxSwipeTime = 0; //无次数限制
        struCardInfo.dwSwipeTime = 0;
        struCardInfo.byCardPassword = "123456".getBytes();
        //struCardInfo.dwEmployeeNo = userId;
        byte[] strCardName = realName.getBytes("GBK");
        for (int i = 0; i < HCNetSDK.NAME_LEN; i++) {
            struCardInfo.byName[i] = 0;
        }
        for (int i = 0; i < strCardName.length; i++) {
            struCardInfo.byName[i] = strCardName[i];
        }
        struCardInfo.write();
        Pointer pSendBufSet = struCardInfo.getPointer();
        if (!hkMainClass.hCNetSDK.NET_DVR_SendRemoteConfig(lHandle, 0x3, pSendBufSet, struCardInfo.size())) {
            iErr = hkMainClass.hCNetSDK.NET_DVR_GetLastError();
            logger.info("ENUM_ACS_SEND_DATA失败，错误号：" + iErr);
            if (!hkMainClass.hCNetSDK.NET_DVR_StopRemoteConfig(lHandle)) {
                iErr = hkMainClass.hCNetSDK.NET_DVR_GetLastError();
                logger.info("断开长连接失败，错误号：" + iErr);
                return false;
            }
            return false;
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            logger.warn("InterruptedException" + e.getMessage());
            e.printStackTrace();
        }
        if (!hkMainClass.hCNetSDK.NET_DVR_StopRemoteConfig(lHandle)) {
            iErr = hkMainClass.hCNetSDK.NET_DVR_GetLastError();
            logger.warn("断开长连接失败，错误号：" + iErr);
            return false;
        }
        logger.info("断开长连接成功!");
        return true;
    }


    /**
     * 根据卡号下发人脸
     * @param userid
     * @param userfacePath
     */
    public boolean setFaceCfg(HKMainClass hkMainClass,String userid,String userfacePath) {
        logger.info("setFaceCfg:设置用户人脸信息[{}] userid::"+userid);
        int iErr = 0;
        //设置人脸参数
        HCNetSDK.NET_DVR_FACE_PARAM_COND m_struFaceSetParam = new HCNetSDK.NET_DVR_FACE_PARAM_COND();
        m_struFaceSetParam.dwSize = m_struFaceSetParam.size();
        m_struFaceSetParam.byCardNo = userid.getBytes(); //人脸关联的卡号
        m_struFaceSetParam.byEnableCardReader[0] = 1;
        m_struFaceSetParam.dwFaceNum = 1;
        m_struFaceSetParam.byFaceID = 1;
        m_struFaceSetParam.write();
        Pointer lpInBuffer = m_struFaceSetParam.getPointer();
        Pointer pUserData = null;
        hkMainClass.fRemoteCfgCallBackFaceSet = new FRemoteCfgCallBackFaceSet();
        int lHandle = hkMainClass.hCNetSDK.NET_DVR_StartRemoteConfig(hkMainClass.lUserID, HCNetSDK.NET_DVR_SET_FACE_PARAM_CFG, lpInBuffer, m_struFaceSetParam.size(), hkMainClass.fRemoteCfgCallBackFaceSet, pUserData);
        if (lHandle < 0) {
            iErr = hkMainClass.hCNetSDK.NET_DVR_GetLastError();
            logger.warn("setFaceCfg:设置用户人像信息错误，错误号：" + iErr);
            return false;
        }
        logger.info( "setFaceCfg:建立设置卡参数长连接成功!");

        HCNetSDK.NET_DVR_FACE_PARAM_CFG struFaceInfo = new HCNetSDK.NET_DVR_FACE_PARAM_CFG(); //卡参数
        struFaceInfo.read();
        struFaceInfo.dwSize = struFaceInfo.size();
        struFaceInfo.byCardNo = userid.getBytes();
        struFaceInfo.byEnableCardReader[0] = 1; //需要下发人脸的读卡器，按数组表示，每位数组表示一个读卡器，数组取值：0-不下发该读卡器，1-下发到该读卡器
        struFaceInfo.byFaceID = 1; //人脸ID编号，有效取值范围：1~2
        struFaceInfo.byFaceDataType = 1; //人脸数据类型：0- 模板（默认），1- 图片
        FileInputStream picfile = null;
        int picdataLength = 0;
        try {
            picfile = new FileInputStream(new File(userfacePath));
        } catch (FileNotFoundException e) {
            logger.warn("setFaceCfg::FileNotFoundException:"+e.getMessage());
            e.printStackTrace();
        }
        try {
            picdataLength = picfile.available();
        } catch (IOException e1) {
            logger.warn("setFaceCfg::IOException:"+e1.getMessage());
            e1.printStackTrace();
        }
        if (picdataLength < 0) {
            logger.info("setFaceCfg:input file dataSize < 0");
            if (!hkMainClass.hCNetSDK.NET_DVR_StopRemoteConfig(lHandle)) {
                iErr = hkMainClass.hCNetSDK.NET_DVR_GetLastError();
                logger.info("setFaceCfg:断开长连接失败，错误号：" + iErr);
                return false;
            }
            return false;
        }

        HCNetSDK.BYTE_ARRAY ptrpicByte = new HCNetSDK.BYTE_ARRAY(picdataLength);
        try {
            picfile.read(ptrpicByte.byValue);
        } catch (IOException e2) {
            logger.warn("setFaceCfg::IOException:"+ e2.getMessage());
            e2.printStackTrace();
        }
        ptrpicByte.write();
        struFaceInfo.dwFaceLen = picdataLength;
        struFaceInfo.pFaceBuffer = ptrpicByte.getPointer();

        struFaceInfo.write();
        Pointer pSendBufSet = struFaceInfo.getPointer();

        //ENUM_ACS_INTELLIGENT_IDENTITY_DATA = 9,  //智能身份识别终端数据类型，下发人脸图片数据
        if (!hkMainClass.hCNetSDK.NET_DVR_SendRemoteConfig(lHandle, 0x9, pSendBufSet, struFaceInfo.size())) {
            iErr = hkMainClass.hCNetSDK.NET_DVR_GetLastError();
            logger.info("setFaceCfg::设置用户人像信息错误，错误号：" + iErr);
            if (!hkMainClass.hCNetSDK.NET_DVR_StopRemoteConfig(lHandle)) {
                iErr = hkMainClass.hCNetSDK.NET_DVR_GetLastError();
                logger.info("setFaceCfg::断开长连接失败，错误号：" + iErr);
                return false;
            }
            return false;
        }
        boolean isCallbackSuccess = false;
        for (int i = 0; i < 100; i++) {
            //判断是否已经回调
            if (!StringUtil.isEmpty(hkMainClass.fRemoteCfgCallBackFaceSet.callbackInfo)){
                String[] arrays = hkMainClass.fRemoteCfgCallBackFaceSet.callbackInfo.split("-");
                if (arrays.length == 2 && arrays[1].equals("1")) {
                    //设置人像成功
                    isCallbackSuccess = true;
                }
                break;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                logger.warn("setFaceCfg::InterruptedException:" +e.getMessage());
            }
        }

        if (!hkMainClass.hCNetSDK.NET_DVR_StopRemoteConfig(lHandle)) {
            iErr = hkMainClass.hCNetSDK.NET_DVR_GetLastError();
            logger.info("setFaceCfg::断开长连接失败，错误号：" + iErr);
            return isCallbackSuccess;
        }
        logger.info("setFaceCfg::断开长连接成功!");
        return isCallbackSuccess;
    }


    /**
     * 根据卡号删除人脸数据
     * @param userid
     */
    public boolean delFaceCfg(HKMainClass hkMainClass,String userid) {
        logger.info("delFaceCfg::删除用户人脸信息[{}] userid::"+userid);
        int iErr = 0;
        //删除人脸数据
        HCNetSDK.NET_DVR_FACE_PARAM_CTRL m_struFaceDel = new HCNetSDK.NET_DVR_FACE_PARAM_CTRL();
        m_struFaceDel.dwSize = m_struFaceDel.size();
        m_struFaceDel.byMode = 0; //删除方式：0- 按卡号方式删除，1- 按读卡器删除
        m_struFaceDel.struProcessMode.setType(HCNetSDK.NET_DVR_FACE_PARAM_BYCARD.class);
        m_struFaceDel.struProcessMode.struByCard.byCardNo = userid.getBytes();//需要删除人脸关联的卡号
        m_struFaceDel.struProcessMode.struByCard.byEnableCardReader[0] = 1; //读卡器
        m_struFaceDel.struProcessMode.struByCard.byFaceID[0] = 1; //人脸ID
        m_struFaceDel.write();
        Pointer lpInBuffer = m_struFaceDel.getPointer();
        boolean lRemoteCtrl = hkMainClass.hCNetSDK.NET_DVR_RemoteControl(hkMainClass.lUserID, HCNetSDK.NET_DVR_DEL_FACE_PARAM_CFG, lpInBuffer, m_struFaceDel.size());
        if (!lRemoteCtrl) {
            iErr = hkMainClass.hCNetSDK.NET_DVR_GetLastError();
            logger.warn( "delFaceCfg::删除人脸图片失败，错误号：" + iErr);
            return false;
        } else {
            logger.info("delFaceCfg::删除人脸图片成功!");
            return true;
        }
    }

    /**
     * 删除卡信息
     * @param cardNo
     */
    public boolean delCardInfo(HKMainClass hkMainClass,String cardNo) {
        logger.info("delCardInfo::删除用户卡信息[{}] cardNO::"+ cardNo);
        int iErr = 0;

        //设置卡参数
        HCNetSDK.NET_DVR_CARD_CFG_COND m_struCardInputParamSet = new HCNetSDK.NET_DVR_CARD_CFG_COND();
        m_struCardInputParamSet.read();
        m_struCardInputParamSet.dwSize = m_struCardInputParamSet.size();
        m_struCardInputParamSet.dwCardNum = 1;
        m_struCardInputParamSet.byCheckCardNo = 1;
        Pointer lpInBuffer = m_struCardInputParamSet.getPointer();
        m_struCardInputParamSet.write();
        Pointer pUserData = null;
        hkMainClass.fRemoteCfgCallBackCardSet = new FRemoteCfgCallBackCardSet();
        int lHandle = hkMainClass.hCNetSDK.NET_DVR_StartRemoteConfig(hkMainClass.lUserID, HCNetSDK.NET_DVR_SET_CARD_CFG_V50, lpInBuffer, m_struCardInputParamSet.size(), hkMainClass.fRemoteCfgCallBackCardSet, pUserData);
        if (lHandle < 0) {
            iErr = hkMainClass.hCNetSDK.NET_DVR_GetLastError();
            logger.info("delCardInfo::删除卡基本信息异常，错误号：" + iErr);
            return false;
        }
        logger.info( "delCardInfo::建立设置卡参数长连接成功!");

        // 设置卡参数
        HCNetSDK.NET_DVR_CARD_CFG_V50 struCardInfo = new HCNetSDK.NET_DVR_CARD_CFG_V50(); //卡参数
        struCardInfo.read();
        struCardInfo.dwSize = struCardInfo.size();
        struCardInfo.dwModifyParamType = 0x00000001;
        System.arraycopy(cardNo.getBytes(), 0, struCardInfo.byCardNo, 0, cardNo.length());
        struCardInfo.byCardValid = 0;
        struCardInfo.write();
        Pointer pSendBufSet = struCardInfo.getPointer();
        // 发送删除卡信息
        boolean lRemoteCtrl = HCNetSDK.INSTANCE.NET_DVR_SendRemoteConfig(hkMainClass.lUserID, 0x3, pSendBufSet, struCardInfo.size());
        if (!lRemoteCtrl) {
            iErr = hkMainClass.hCNetSDK.NET_DVR_GetLastError();
            logger.info( "delCardInfo::删除用户信息失败，错误号：" + iErr);
            if (!hkMainClass.hCNetSDK.NET_DVR_StopRemoteConfig(lHandle)) {
                iErr = hkMainClass.hCNetSDK.NET_DVR_GetLastError();
                logger.info("delCardInfo::断开长连接失败，错误号：" + iErr);
                return false;
            }
            return false;
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            logger.warn("delCardInfo:InterruptedException:"+e.getMessage());
            e.printStackTrace();
        }
        if (!hkMainClass.hCNetSDK.NET_DVR_StopRemoteConfig(lHandle)) {
            iErr = hkMainClass.hCNetSDK.NET_DVR_GetLastError();
            logger.info("delCardInfo:断开长连接失败，错误号：" + iErr);
            return false;
        }
        logger.info("delCardInfo:断开长连接成功!");
        logger.info("delCardInfo:删除用户信息成功!");
        return true;
    }

    /**
     * 下发服务器时间
     * @param iUserID
     */
    public void UpdateTimeCfg(HKMainClass hkMainClass,int iUserID) {
        logger.info("UpdateTimeCfg::下发服务器时间");
        HCNetSDK.NET_DVR_TIME time = new HCNetSDK.NET_DVR_TIME();
        Pointer lpPicConfig = time.getPointer();
        IntByReference pInt = new IntByReference(0);
        int lChannel = new Integer(0);
        boolean bRet = hkMainClass.hCNetSDK.NET_DVR_GetDVRConfig(iUserID, HCNetSDK.NET_DVR_GET_TIMECFG, lChannel, lpPicConfig, time.size(), pInt);
        time.read();
        if(bRet)
        {
            logger.info("UpdateTimeCfg::dvr time[{}]"+time.toStringTime());
        }
        Calendar now = Calendar.getInstance();

        time.dwYear = now.get(Calendar.YEAR);
        time.dwMonth = now.get(Calendar.MONTH) + 1;
        time.dwDay = now.get(Calendar.DAY_OF_MONTH);
        time.dwHour = now.get(Calendar.HOUR_OF_DAY);
        time.dwMinute = now.get(Calendar.MINUTE);
        time.dwSecond = now.get(Calendar.SECOND);
        time.write();
        bRet = hkMainClass.hCNetSDK.NET_DVR_SetDVRConfig(iUserID, HCNetSDK.NET_DVR_SET_TIMECFG, lChannel, lpPicConfig, time.size());
        logger.info("UpdateTimeCfg::下发系统时间到设备。 bRet[{}],deviceIp[{}]"+bRet+"userID"+iUserID);
        if(!bRet)
        {
            logger.info("UpdateTimeCfg::HCNetSDKByJNA.NET_DVR_SET_TIMECFG succ");
        }
    }
    public int list_send(HKMainClass hkMainClass) {
        if (hkMainClass.lUserID == -1) {
            logger.warn("list_send::请先注册");
            return -1;
        }


        return 0;
    }

    /**
     * 今日来访
     * @param visitor
     */
    public void queryHKData(Visitor visitor) {
        if (visitor == null || "".equals(visitor)) {
            logger.warn("queryHKData::今日来访入库队列：visitor is null");
            return;
        }
        rabbitTemplate.convertAndSend("topic_visit", visitor);
        logger.info("queryHKData::今日来访入库队列：visitor" + visitor);
    }

    /**
     * 删除人脸及卡信息
     * @param deviceIP
     * @param cardNO
     */
    public boolean deleteTask (String deviceIP,String cardNO) {
        logger.info("[HKMethod]deleteTask: deviceIP and cardNO" + deviceIP + ":" +cardNO);
        boolean result = false;
        try {
            if (hashMap.size()> 0 ) {
                HKMainClass hkMainClass1 = (HKMainClass)hashMap.get(deviceIP);
                result = delFaceCfg(hkMainClass1,cardNO);
                logger.info("删除人脸信息"+ result);
                if (result) {
                    result = delCardInfo(hkMainClass1,cardNO);
                    logger.info("删除卡"+result);
                }
            }
        }catch (Exception e) {
            logger.warn("[HKMethod]deleteTask Exception"+ e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
}
