package com.san.platform.config;

public class HkErrorInfo {

    /**
     * 根据错误码获得具体的错误信息
     * @param resultCode
     * @return
     */
    public static String errorInfo (int resultCode) {
        String info = "";
        switch (resultCode){
            case 0:
                info = "网络连接错误";
                break;
            case 2:
                info = "人脸质量差";
                break;
            case 3:
                info = "内存已满";
                break;
            case 4:
                info = "已存在该人脸";
                break;
            case 5:
                info = "非法人脸ID";
                break;
            case 6:
                info = "算法建模失败";
                break;
            case 7:
                info = "未下发卡权限";
                break;
            case 8:
                info = "未定义";
                break;
            case 9:
                info = "人眼间距小";
                break;
            case 10:
                info = "图片数据长度小于1KB";
                break;
            case 11:
                info = "图片格式不符（png/jpg/bmp）";
                break;
            case 12:
                info = "图片像素数量超过上限";
                break;
            case 13:
                info = "图片像素数量低于下限";
                break;
            case 14:
                info= "图片信息校验失败";
                break;
            case 15:
                info= "图片解码失败";
                break;
            case 16:
                info= "人脸检测失败";
                break;
            case 17:
                info= "人脸评分失败";
                break;
            case 18:
                info = "图像文件不存在";
                break;
            case 19:
                info = "人员信息不存在";
                break;
            case 20:
                info = "未上传图像信息";
                break;
            case 21:
                info = "设备信息不存在";
                break;
            default:
                info = "网络连接错误";
                break;
        }
        return info;
    }
}
