package com.san.platform.hikface.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.san.platform.hikface.HikfaceService;
import com.san.platform.hikface.utils.HTTPClientUtil;
import com.san.platform.hikface.utils.HttpsClientUtil;
import com.san.platform.hikface.utils.JsonFormatTool;
import com.san.platform.hikface.utils.LogUtil;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.io.ByteArrayInputStream;
import java.util.Iterator;

@Service
public class HikFaceServiceImpl extends LogUtil implements HikfaceService {

    static final boolean bHttpsNeed = true;
    @Value("${face.ip}")
    String serverIp;   // 脸谱ip地址


    public boolean login(String serverIp,int serverPort,String userName, String userPwd) {
        logger.info("[HikFaceServiceImpl]-[login] method have been called");
        // 添加凭证
        HttpsClientUtil.bHttpsEnabled = HikFaceServiceImpl.bHttpsNeed;

        if(HttpsClientUtil.bHttpsEnabled)
        {
            HttpsClientUtil.httpsClientInit(serverIp, serverPort, userName, userPwd);
        }
        else
        {
            UsernamePasswordCredentials creds = new UsernamePasswordCredentials(userName, userPwd);
            HTTPClientUtil.client.getState().setCredentials(AuthScope.ANY, creds);
        }

        //登录校验代码待补充
        String strUrl = "/ISAPI/Security/userCheck";
        try
        {
            String strOut =  "";

            if(HttpsClientUtil.bHttpsEnabled)
            {
                strOut = HttpsClientUtil.httpsGet("https://" + serverIp + ":" + String.valueOf(serverPort) + strUrl);
            }
            else
            {
                strOut= HTTPClientUtil.doGet("http://" + serverIp + ":" + String.valueOf(serverPort) + strUrl, null);
            }

            logger.info(strOut);
            SAXReader saxReader = new SAXReader();
            try{

                Document document = saxReader.read(new ByteArrayInputStream(strOut.getBytes("UTF-8")));
                Element employees=document.getRootElement();

                for(Iterator i = employees.elementIterator(); i.hasNext();){

                    Element employee = (Element) i.next();

                    if(employee.getName() == "statusValue" && 0 ==  employee.getText().compareTo("200")){

                        logger.info("[HikFaceServiceImpl]-[login] Login Successed!");
                        return true;
                    }
                }
                //login fail
                logger.info("[HikFaceServiceImpl]-[login] Login fail..");
                return false;

            } catch (DocumentException e) {
                logger.warn("[HikFaceServiceImpl]-[login] Login fail DocumentException:: " + e.getMessage());
            }


        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            logger.warn("[HikFaceServiceImpl]-[login] Login fail exception:: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 脸谱开放方法
     * @param url
     * @param method
     * @param inbound
     * @return
     */
    public String excute(String url, String method, String inbound) {
        logger.info("[HikFaceServiceImpl]-[excute] method have been called");
        logger.info("url:" + url);
        logger.info("method:" + method);
        logger.info("inbound:" + inbound);
        String result = "";
        String out = "";
        JsonFormatTool JsonFormatTool = new JsonFormatTool();
        if(method.equals("GET"))
        {
            logger.info("[HikFaceServiceImpl]-[excute] method is GET");
            try
            {
                if(HttpsClientUtil.bHttpsEnabled)
                {
                    out = HttpsClientUtil.httpsGet("https://" + this.serverIp + ":" + url);
                }
                else
                {
                    out = HTTPClientUtil.doGet("http://" + this.serverIp + ":" + url, null);
                }
                logger.info("outbound(GET):");
                logger.info(JsonFormatTool.formatJson(out));
                result = JsonFormatTool.formatJson(out);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                logger.warn("[HikFaceServiceImpl]-[excute] GET Exception:" + e.getMessage());
                e.printStackTrace();
                result = "";
            }
        }
        else if(method.equals("PUT"))
        {
            logger.info("[HikFaceServiceImpl]-[excute] method is PUT");
            try
            {
                if(HttpsClientUtil.bHttpsEnabled)
                {
                    out = HttpsClientUtil.httpsPut("https://" + this.serverIp + ":" + url, inbound);
                }
                else
                {
                    out = HTTPClientUtil.doPut("http://" + this.serverIp + ":" + url, inbound, null);
                }

                logger.info("outbound(PUT):");
                logger.info(JsonFormatTool.formatJson(out));
                result = JsonFormatTool.formatJson(out);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                logger.warn("[HikFaceServiceImpl]-[excute] PUT Exception:" + e.getMessage());
                e.printStackTrace();
                result = "";
            }
        }
        else if(method.equals("POST"))
        {
            logger.info("[HikFaceServiceImpl]-[excute] method is POST");
            try
            {
                if(HttpsClientUtil.bHttpsEnabled)
                {
                    out = HttpsClientUtil.httpsPost("https://" + this.serverIp + ":" + url, inbound);
                }
                else
                {
                    out = HTTPClientUtil.doPost("http://" + this.serverIp + ":" + url, inbound, null);
                }

                logger.info("outbound(POST):");
                logger.info(JsonFormatTool.formatJson(out));
                result = JsonFormatTool.formatJson(out);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                logger.warn("[HikFaceServiceImpl]-[excute] POST Exception:" + e.getMessage());
                e.printStackTrace();
                result = "";
            }
        }
        else if(method.equals("DELETE"))
        {
            logger.info("[HikFaceServiceImpl]-[excute] method is DELETE");
            try
            {
                if(HttpsClientUtil.bHttpsEnabled)
                {
                    out = HttpsClientUtil.httpsDelete("https://" + this.serverIp + ":" + url);
                }
                else
                {
                    out = HTTPClientUtil.doDelete("http://" + this.serverIp + ":" + url, null);
                }

                logger.info("outbound(DELETE):");
                logger.info(JsonFormatTool.formatJson(out));
                result = JsonFormatTool.formatJson(out);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                logger.warn("[HikFaceServiceImpl]-[excute] DELETE Exception:" + e.getMessage());
                e.printStackTrace();
                result = "";
            }
        }
        return result;
    }

    /***
     * @param faceInfo   人脸信息
     * @param faceType   选择请求方法（1：创建人脸库 2：人脸库检索 3：人脸添加到人脸库 4：人脸分析 5：人脸库以图搜图）
     * @return
     */
    @Override
    public String FaceApi(String faceInfo, int faceType) {
        logger.info("[HikFaceServiceImpl]-[FaceApi] method have been called");
        logger.info("[HikFaceServiceImpl]-[FaceApi] faceType::"+ faceType);
        String result = "";
        String url = "";  // 脸谱api方法  url
        String method = ""; // 脸谱api方法 请求方式
        // 调用脸谱方法
        switch (faceType) {
            case 1:
                // 创建人脸库
                url = "/ISAPI/Intelligent/FDLib?format=json";
                method = "POST";
                logger.info("[HikFaceServiceImpl]-[FaceApi] case 1 is end");
                break;
            case 2:
                // 人脸库检索
                url = "/ISAPI/Intelligent/FDLib?format=json&FDID=1&faceLibType=blackFD";
                method = "GET";
                logger.info("[HikFaceServiceImpl]-[FaceApi] case 2 is end");
                break;
            case 3:
                // 添加人脸
                url = "/ISAPI/Intelligent/FDLib/FaceDataRecord?format=json";
                method = "POST";
                logger.info("[HikFaceServiceImpl]-[FaceApi] case 3 is end");
                break;
            case 4:
                // 人脸分析
                url = "/ISAPI/SDT/Face/pictureAnalysis";
                method = "POST";
                logger.info("[HikFaceServiceImpl]-[FaceApi] case 4 is end");
                break;
            case 5:
                // 人脸库以图搜图
                url = "/ISAPI/Intelligent/FDLib/searchByPic?format=json";
                method = "POST";
                logger.info("[HikFaceServiceImpl]-[FaceApi] case 5 is end");
                break;
            case 6:
                // 查询人脸
                url = "/ISAPI/Intelligent/FDLib/FDSearch?format=json";
                method = "POST";
                logger.info("[HikFaceServiceImpl]-[FaceApi] case 6 is end");
                break;
//            case 7:
//                // 修改人脸
//                url = "/ISAPI/Intelligent/FDLib/FDSearch?format=json&FDID=fdid&FPID=9b8434b5fcf340d0a53c0826931c1fb4&faceLibType=blackFD";
//                method = "PUT";
//                logger.info("[HikFaceServiceImpl]-[FaceApi] case 7 is end");
//                break;
        }
        // 接口执行
        result = excute(url, method, faceInfo);
        logger.info("[HikFaceServiceImpl]-[FaceApi] excute is end");
        return result;
    }

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/7/5 0:46
     * @Description:
     * @param faceInfo 人脸信息
     * @param fpid 人脸ID唯一标识
     */
    @Override
    public String updateFace(String faceInfo, String fpid) {
        logger.info("[HikFaceServiceImpl]-[updateFace] method have been called");
        logger.info("[HikFaceServiceImpl]-[updateFace] faceType::"+ fpid);
        String result = "";
        String url = "";  // 脸谱api方法  url
        String method = ""; // 脸谱api方法 请求方式
        url = "/ISAPI/Intelligent/FDLib/FDSearch?format=json&FDID=1&FPID=" + fpid + "&faceLibType=blackFD";
        method = "PUT";
        logger.info("[HikFaceServiceImpl]-[updateFace] updateFace is end");
        // 接口执行
        result = excute(url, method, faceInfo);
        logger.info("[HikFaceServiceImpl]-[updateFace] excute is end");
        return result;
    }
}
