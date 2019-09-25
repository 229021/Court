package com.san.platform.config;

import java.util.HashMap;

/**
 * 全局状态code码
 */
public class Global {

    /**
     * 公用全局变量定义3000以内数值
     */
    public static int PAGESIZE = 10; // 每页条数
    public static int CODE_SUCCESS = 200; //
    public static int CODE_DATA_NOT_FOUND_EXCEPTION = 2001; //未查询到指定数据异常
    public static int CODE_DATA_ID_REPEAT_EXCEPTION = 2002; //根据ID查询数据重复异常,
    public static int CODE_DB_QUERY_EXCEPTION = 2003;      // 数据库查询异常
    public static int CODE_SESSION_TIMEOUT_EXCEPTION = 2004; //session超时
    public static int CODE_API_PERMISSION_DENIED_EXCEPTION = 2005; //权限异常
    public static int CODE_SESSION_DESTORY_EXCEPTION = 2006;//session销毁异常
    public static int CODE_FORMAT_CONVERSION = 2007; //格式转换错误
    public static int CODE_IO_FlOW = 2008; //io流错误
    public static int CODE_INTERRUPTED_EXCEPTION = 2009;   //中断异常
    public static int CODE_SERVER_NOTFOUND_EXCEPTION = 2999; //服务端连段不到
    public static int CODE_DATA_INSERT_EXCEPION = 2888; //数据添加失败
    public static int CODE_DATA_UPDATE_EXCEPION = 2889; //数据更新失败
    public static int CODE_DATA_DELETE_EXCEPION = 2900; //数据更新失败


    /**
     * 角色模块全局变量定义3101开头
     */
    public static int CODE_USER_LOGNUM = 5;//用户登录次数
    public static int CODE_ROLE_IS_DELETED_EXCEPTION = 3101;//删除角色异常
    public static int CODE_ROLE_NAME_IS_EXIST = 3102;//角色名称已存在


    /**
     * 用户模块应用全局code3200开始
     */
    public static int CODE_USER_NAME_NOT_EXIST = 3201;// 当前登录用户不存在
    public static int CODE_USER_PASS_NOT_WRONG = 3202;//密码错误
    public static int CODE_USER_NAME_IS_EXIST = 3203;//用户名称已存在
    public static int CODE_USER_CHANGE_PASS = 3204;//用户密码过期
    public static int CODE_USER_IS_LOCK = 3205;//用户被锁定
    public static int CODE_USER_ISNOT_LOCK = 3206;//用户
    public static int CODE_USER_CHANGE_PASS_SUCCESS = 3207;//修改密码成功
    public static int CODE_USER_NOT_ALLOW_REPEAT_EXCEPTION = 3208;//用户不允许重复
    public static int CODE_USER_ONLINE_MAX_COUNT_OVERFLOW_EXCEPTION = 3209;//在线用户
    public static int CODE_USER_CODE_WRONG = 3210;//验证码错误
    public static int CODE_USER_CHECK_IS_RIGHT = 3211;//
    public static int CODE_USER_CHECK_IS_WRONG = 3212;//
    public static int CODE_USER_IP_DENY_EXCEPTION = 3213;//IP限制


    /**
     * 系统状态全局变量code，以3301开始
     */
    public static int CODE_STATUS_CPU_EXCEPTION = 3301;
    public static int CODE_STATUS_MEMORY_EXCEPTION = 3302;
    public static int CODE_STATUS_DISK_EXCEPTION = 3303;
    public static int CODE_STATUS_SYSRUNTIME_EXCEPTION = 3304;
    public static int CODE_STATUS_OS_IS_WRONG = 3305;

    /**
     * 网卡模块全局变量3501开始
     */
    public static int CODE_NETWORK_IS_EXIST = 3501;//网卡已存在
    public static int CODE_NETWORK_CREATE_EXCEPTION = 3502;   //创建网卡异常
    public static int CODE_NETWORK_QUERY_EXCEPTION = 3503;     //查询网卡异常
    public static int CODE_NETWORK_UPDATE_EXCEPTION = 3504;     //更新网卡异常
    public static int CODE_NETWORK_REMOVE_EXCEPTION = 3505;     //删除网卡异常
    public static int CODE_NETWORK_MODIFY_CONFIGURATION_FILES_EXCEPTION = 3512;   //修改网卡配置文件出现IO流异常
    public static int CODE_NETWORK_FILE_DOES_NOT_EXIST = 3511;      //网卡配置文件不存在
    public static int CODE_NETWORK_NETWORK_CARD_DOES_NOT_EXIST = 3521; //网卡不存在
    public static int CODE_NETWORK_FAILURE_TO_RESTART_NETWORK_CARD_SERVICE = 3531; //重启网卡服务失败

    /*****/

    /**
     * 数据备份   3601 -3650
     */
    public static  int CODE_DATA_ILLEGAL_STATEEXC_EPTION = 3604;   //非法状态例外
    public static  int CODE_DATA_UNSUPPORT_EDENCODING_EXCEPTION = 3601;   //不支持编码
    public static  int CODE_DATA_FILE_NOTFOUND_EXCEPTION = 3602;     //sql文件找不到
    public static  int CODE_DATA_PATH_EXCEPTION = 3603;  //数据路径异常

    /**
     * 访客 3650-3699
     */
    public static  int CODE_VISITOR_CREATE_EXCEPTION  = 3651;   //创建访客异常
    public static  int CODE_VISITOR_QUERY_EXCEPTION = 3652;     //查询访客异常
    public static  int CODE_VISITOR_REMOVE_EXCEPTION = 3653;    //删除访客异常
    public static  int CODE_VISITOR_SAVETOPERSONNEL_EXCEPTION = 3654;   //保存到人像库异常
    public static  int CODE_VISITOR_EXPORT_DATA_EXCEPTION = 3655;     //导出数据异常

    /**
     * 大屏显示 3700 -3725
     */

    public static  int CODE_DISPLAY_QUERY_EXCEPTION = 3701;     //查询数据异常
    public static  int CODE_DISPLAY_UPDATEVISITCAUSE_EXCEPTION = 3702;     //更新访客事由


    /**
     * 设备信息code 3801---
     */
    public static int CODE_DEVICE_NAME_IS_EXIST = 3801; // 设备名称已存在
    public static int CODE_DEVICE_CREATE_EXCEPTION = 3802; //添加设备信息失败
    public static int CODE_DEVICE_REMOVE_EXCEPTION = 3803;//删除设备信息失败
    public static int CODE_DEVICE_UPDATE_EXCEPTION = 3804; //更新设备信息失败
    public static int CODE_DEVICE_INIT_EXCEPTION = 3805;// 设备注册布防失败

    /**
     * 布控信息code 3001---
     */
    public static int CODE_CONTROL_NAME_IS_EXIST = 3901; // 此设备已布控
    public static int CODE_CONTROL_CREATE_EXCEPTION = 3902; //添加布控信息失败
    public static int CODE_CONTROL_REMOVE_EXCEPTION = 3903;//删除布控信息失败
    public static int CODE_CONTROL_UPDATE_EXCEPTION = 3904; //更新布控坐标信息失败

    /**
     * 地图信息 code 3821---
     */
    public static int CODE_MAP_NAME_IS_EXIST = 3821;
    public static int CODE_MAP_CREATE_EXCEPTION = 3822;
    public static int CODE_MAP_REMOVE_EXCEPTION = 3823;
    public static int CODE_MAP_UPDATE_EXCEPITON = 3824;
    /**
     * 区域信息
     */
    public static int CODE_REGION_NAME_IS_EXIST = 3831;
    public static int CODE_REGION_CREATE_EXCEPTION = 3832;
    public static int CODE_REGION_REMOVE_EXCEPTION = 3833;
    public static int CODE_REGION_UPDATE_EXCEPITON = 3834;

    /**
     * 名单下发
     */
    public static int CODE_TASK_CREATE_EXCEPTION = 3842;
    public static int CODE_TASK_REMOVE_EXCEPTION = 3843;
    public static int CODE_TASK_STARTERR_EXCEPTION = 3844;
    public static int CODE_TASK_IS_NOT_EXIST = 3845;

    /**
     * 告警规则   code 3771---
     */
    public static int CODE_ALERT_NAME_IS_EXIST = 3771;

    /**
     * 选项管理   code 3781---
     */
    public static int CODE_OPTION_NAME_IS_EXIST = 3781;


    public static int CODE_FACE_INSERT_EXCEPTION = 3791; // 人脸不合法
    public static int CODE_FACE_IS_NULL = 3792; // 人脸为空
    public static int CODE_FACE_TO_DETECT_FACE = 3793; // 未检测到脸部
    public static int CODE_FACE_INVALIDID = 3794; // 身份证号码不正确
    public static int CODE_LAWYER_IS_EXIST = 3795; // 律师重复



}

