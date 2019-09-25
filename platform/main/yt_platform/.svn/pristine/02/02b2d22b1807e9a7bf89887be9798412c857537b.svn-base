package com.san.platform.hikface;

/**
 * 脸谱开放接口
 */
public interface HikfaceService {

    /**
     * 脸谱服务器登陆
     * @param serverIp
     * @param serverPort
     * @param userName
     * @param userPwd
     * @return
     */
    public boolean login(String serverIp,int serverPort,String userName, String userPwd);

    /**
     * 执行方法
     * @param faceInfo
     * @param faceType
     * @return
     */
    public String FaceApi(String faceInfo, int faceType);

    /**
     * 请求方法
     * @param url
     * @param method
     * @param inbound
     * @return
     */
    public String excute(String url, String method, String inbound);

    // 修改人脸
    public String updateFace(String faceInfo,String fpid);

}
