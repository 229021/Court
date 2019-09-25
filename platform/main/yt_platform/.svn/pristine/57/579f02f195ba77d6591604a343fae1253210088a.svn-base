package com.san.platform.hikvison.hkmethod;


import com.san.platform.hikvison.hkinterface.HCNetSDK;
import org.apache.log4j.Logger;

public class HKMainClass {
    private Logger logger = Logger.getLogger(HKMainClass.class);
    public static HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;
    public HCNetSDK.NET_DVR_USER_LOGIN_INFO m_strLoginInfo = new HCNetSDK.NET_DVR_USER_LOGIN_INFO();//设备登录信息
    public HCNetSDK.NET_DVR_DEVICEINFO_V40 m_strDeviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V40();//设备信息
//    HCNetSDK.NET_DVR_DEVICEINFO_V30 m_strDeviceInfo = ;//设备信息
    public String m_sDeviceIP;//已登录设备的IP地址
    public int m_iDevicePort;//已登录设备的端口
    public String m_sUsername;//设备用户名
    public  String m_sPassword;//设备密码
    public HKMethod hkMethod;

    public int lUserID;//用户句柄
    public  int lAlarmHandle;//报警布防句柄
    public  int lListenHandle;//报警监听句柄

    public  HKMethod.FMSGCallBack fMSFCallBack;//报警回调函数实现
    public HKMethod.FMSGCallBack_V31 fMSFCallBack_V31;//报警回调函数实现
    public  HKMethod.FRemoteCfgCallBackCardGet fRemoteCfgCallBackCardGet;
    public  HKMethod.FRemoteCfgCallBackCardSet fRemoteCfgCallBackCardSet;
    public  HKMethod.FRemoteCfgCallBackFaceGet fRemoteCfgCallBackFaceGet;
    public  HKMethod.FRemoteCfgCallBackFaceSet fRemoteCfgCallBackFaceSet;
    public HKMethod.FGPSDataCallback fGpsCallBack;//GPS信息查询回调函数实现

    //构造函数
    public HKMainClass(String inputIp, int inputPort, String inputAccount, String inputPasswd) {

        m_sDeviceIP = inputIp;
        m_iDevicePort = inputPort;
        m_sUsername = inputAccount;
        m_sPassword = inputPasswd;

        lUserID = -1;
        lAlarmHandle = -1;
        lListenHandle = -1;
        fMSFCallBack = null;
        fMSFCallBack_V31 = null;
        fGpsCallBack = null;

        fRemoteCfgCallBackCardGet = null;
        fRemoteCfgCallBackCardSet = null;
        fRemoteCfgCallBackFaceGet = null;
        fRemoteCfgCallBackFaceSet = null;

        boolean initSuc = hCNetSDK.NET_DVR_Init();
        if (initSuc != true) {
            System.out.println("Init Error...");
        }

        HCNetSDK.NET_DVR_LOCAL_GENERAL_CFG struGeneralCfg = new HCNetSDK.NET_DVR_LOCAL_GENERAL_CFG();
        struGeneralCfg.byAlarmJsonPictureSeparate =1; //控制JSON透传报警数据和图片是否分离，0-不分离，1-分离（分离后走COMM_ISAPI_ALARM回调返回）
        struGeneralCfg.write();

        if(!hCNetSDK.NET_DVR_SetSDKLocalCfg(17, struGeneralCfg.getPointer()))
        {
            logger.warn("NET_DVR_SetSDKLocalCfg失败");
        }


    }
}
