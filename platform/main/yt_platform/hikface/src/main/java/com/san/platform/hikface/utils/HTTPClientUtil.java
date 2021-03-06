package com.san.platform.hikface.utils;




import org.apache.commons.httpclient.HttpClient;

import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.DeleteMethod;

public class HTTPClientUtil {

    public HTTPClientUtil() throws Exception
    {

    }

    public static HttpClient client = new HttpClient();

    public static String doGet(String url, String charset) throws Exception {

        GetMethod method = new GetMethod(url);
        method.setDoAuthentication(true);

        int statusCode = client.executeMethod(method);

//        if (statusCode != HttpStatus.SC_OK) {// 打印服务器返回的状态
//            return "Method failed: " + method.getStatusLine();
//        }
        // 返回响应消息
        byte[] responseBody = method.getResponseBodyAsString().getBytes(method.getResponseCharSet());
        // 在返回响应消息使用编码(utf-8或gb2312)
        String response = new String(responseBody, "utf-8");
        // 释放连接
        method.releaseConnection();
        return response;
    }

    public static String doPut(String url, String inbound,String charset) throws Exception {

        PutMethod method = new PutMethod(url);
        method.setDoAuthentication(true);

        method.setRequestBody(inbound);

        int statusCode = client.executeMethod(method);

//        if (statusCode != HttpStatus.SC_OK) {// 打印服务器返回的状态
//            return "Method failed: " + method.getStatusLine();
//        }
        // 返回响应消息
        byte[] responseBody = method.getResponseBodyAsString().getBytes(method.getResponseCharSet());
        // 在返回响应消息使用编码(utf-8或gb2312)
        String response = new String(responseBody, "utf-8");
        // 释放连接
        method.releaseConnection();
        return response;
    }

    public static String doPutWithType(String url, String inbound,String charset,String Type) throws Exception {

        PutMethod method = new PutMethod(url);
        method.setDoAuthentication(true);

        method.setRequestBody(inbound);
        method.addRequestHeader("Content-Type", Type);

        int statusCode = client.executeMethod(method);

//        if (statusCode != HttpStatus.SC_OK) {// 打印服务器返回的状态
//            return "Method failed: " + method.getStatusLine();
//        }
        // 返回响应消息
        byte[] responseBody = method.getResponseBodyAsString().getBytes(method.getResponseCharSet());
        // 在返回响应消息使用编码(utf-8或gb2312)
        String response = new String(responseBody, "utf-8");
        // 释放连接
        method.releaseConnection();
        return response;
    }

    public static String doPost(String url, String inbound,String charset) throws Exception {

        PostMethod method = new PostMethod(url);
        method.setDoAuthentication(true);

        method.setRequestBody(inbound);

        int statusCode = client.executeMethod(method);

        //       if (statusCode != HttpStatus.SC_OK) {// 打印服务器返回的状态
        //           return "Method failed: " + method.getStatusLine();
        //       }
        // 返回响应消息
        byte[] responseBody = method.getResponseBodyAsString().getBytes(method.getResponseCharSet());
        // 在返回响应消息使用编码(utf-8或gb2312)
        String response = new String(responseBody, "utf-8");
        // 释放连接
        method.releaseConnection();
        return response;
    }

    public static String doPostWithType(String url, String inbound,String charset,String Type) throws Exception {

        PostMethod method = new PostMethod(url);
        method.setDoAuthentication(true);
        method.addRequestHeader("Content-Type", Type);
        method.setRequestBody(inbound);

        int statusCode = client.executeMethod(method);

//        if (statusCode != HttpStatus.SC_OK) {// 打印服务器返回的状态
//            return "Method failed: " + method.getStatusLine();
//        }
        // 返回响应消息
        byte[] responseBody = method.getResponseBodyAsString().getBytes(method.getResponseCharSet());
        // 在返回响应消息使用编码(utf-8或gb2312)
        String response = new String(responseBody, "utf-8");
        // 释放连接
        method.releaseConnection();
        return response;
    }

    public static String doDelete(String url,String charset) throws Exception {

        DeleteMethod method = new DeleteMethod(url);
        method.setDoAuthentication(true);

        int statusCode = client.executeMethod(method);

//        if (statusCode != HttpStatus.SC_OK) {// 打印服务器返回的状态
//            return "Method failed: " + method.getStatusLine();
//        }
        // 返回响应消息
        byte[] responseBody = method.getResponseBodyAsString().getBytes(method.getResponseCharSet());
        // 在返回响应消息使用编码(utf-8或gb2312)
        String response = new String(responseBody, "utf-8");
        // 释放连接
        method.releaseConnection();
        return response;
    }

    public static String doModFacePicRecord(String url, String json,String faceimage,String boundary) throws Exception {

        PutMethod method = new PutMethod(url);
        method.setDoAuthentication(true);

        method.addRequestHeader("Accept", "text/html, application/xhtml+xml");
        method.addRequestHeader("Accept-Language", "zh-CN");
        method.addRequestHeader("Content-Type","multipart/form-data; boundary=" + boundary);
        method.addRequestHeader("User-Agent","Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)");
        method.addRequestHeader("Accept-Encoding","gzip, deflate");
        method.addRequestHeader("Connection","Keep-Alive");
        method.addRequestHeader("Cache-Control","no-cache");

        String bodyParam =
                "--" + boundary + "\r\n"
                        + "Content-Disposition: form-data; name=\"FaceDataRecord\";\r\n"
                        + "Content-Type: text/json\r\n"
                        + "Content-Length: " + Integer.toString(json.length()) + "\r\n\r\n"
                        +  json + "\r\n"
                        + "--" + boundary + "\r\n"
                        + "Content-Disposition: form-data; name=\"FaceImage\";\r\n"
                        + "Content-Type: image/jpeg\r\n"
                        + "Content-Length: " + Integer.toString(faceimage.length()) + "\r\n\r\n"
                        + faceimage
                        + "\r\n--" + boundary + "--\r\n";

        method.setRequestBody(bodyParam);
        int statusCode = client.executeMethod(method);

//        if (statusCode != HttpStatus.SC_OK) {// 打印服务器返回的状态
//            return "Method failed: " + method.getStatusLine();
//        }
        // 返回响应消息
        byte[] responseBody = method.getResponseBodyAsString().getBytes(method.getResponseCharSet());
        // 在返回响应消息使用编码(utf-8或gb2312)
        String response = new String(responseBody, "utf-8");
        // 释放连接
        method.releaseConnection();
        return response;
    }

    public static String doPostStorageCloud(String url, String json,String faceimage,String boundary) throws Exception {

        PostMethod method = new PostMethod(url);
        method.setDoAuthentication(true);

        method.addRequestHeader("Accept", "text/html, application/xhtml+xml");
        method.addRequestHeader("Accept-Language", "zh-CN");
        method.addRequestHeader("Content-Type","multipart/form-data; boundary=" + boundary);
        method.addRequestHeader("User-Agent","Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)");
        method.addRequestHeader("Accept-Encoding","gzip, deflate");
        method.addRequestHeader("Connection","Keep-Alive");
        method.addRequestHeader("Cache-Control","no-cache");

        String bodyParam =
                "--" + boundary + "\r\n"
                        + "Content-Disposition: form-data; name=\"uploadStorageCloud\";\r\n"
                        + "Content-Type: text/json\r\n"
                        + "Content-Length: " + Integer.toString(json.length()) + "\r\n\r\n"
                        +  json + "\r\n"
                        + "--" + boundary + "\r\n"
                        + "Content-Disposition: form-data; name=\"imageData\";\r\n"
                        + "Content-Type: image/jpeg\r\n"
                        + "Content-Length: " + Integer.toString(faceimage.length()) + "\r\n\r\n"
                        + faceimage
                        + "\r\n--" + boundary + "--\r\n";

        method.setRequestBody(bodyParam);
        int statusCode = client.executeMethod(method);

//        if (statusCode != HttpStatus.SC_OK) {// 打印服务器返回的状态
//            return "Method failed: " + method.getStatusLine();
//        }
        // 返回响应消息
        byte[] responseBody = method.getResponseBodyAsString().getBytes(method.getResponseCharSet());
        // 在返回响应消息使用编码(utf-8或gb2312)
        String response = new String(responseBody, "utf-8");
        // 释放连接
        method.releaseConnection();
        return response;
    }

//    public static String doPostPicture(String url,JSONObject jsonData,String boundary,File file)
//    {
//        String strRoute = "";
//        try {
//            HttpPost postMethod = new HttpPost(url);
//            URI serverURI = new URI(url);
//            postMethod.addHeader("Accept", "text/html, application/xhtml+xml");
//            postMethod.addHeader("Accept-Language", "zh-CN");
//            postMethod.addHeader("Content-Type","multipart/form-data; boundary=" + boundary);
//            postMethod.addHeader("User-Agent","Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)");
//            postMethod.addHeader("Accept-Encoding","gzip, deflate");
//            postMethod.addHeader("Connection","Keep-Alive");
//            postMethod.addHeader("Cache-Control","no-cache");
//
//            String jsonSendData;
//            String name1;
//            String name2;
//
//            if(jsonData.has("faceLibType")){
//                jsonSendData =  "\"faceLibType\": \"blackFD\",\r\n" +
//                                "\"FDID\": \"1\",\r\n" +
//                                "\"name\": \"" + jsonData.getString("name") + "\",\r\n" +
//                                "\"bornTime\": \"" + jsonData.getString("bornTime")+ "\",\r\n";
//                jsonSendData = "{\r\n" + jsonSendData + "}\r\n" ;
//                name1 = "FaceDataRecord";
//                name2 = "FaceImage";
//            }else{
//                jsonSendData =
//                    "{\r\n" +
//                    "\"FDID\": " + jsonData.getString("FDID") + ",\r\n" +
//                    "\"storageType\": " + jsonData.getString("storageType") + "\r\n" +
//                    "}\r\n";
//                name1 = "uploadStorageCloud";
//                name2 = "imageData";
//            }
//
//            FileInputStream uploadPic = new FileInputStream(file);
//            byte[] bytePic = new byte[uploadPic.available()];
//            uploadPic.read(bytePic);
//            String strPic = new String(bytePic, "ISO-8859-1");
//            String bodyParam = "--" + boundary + "\r\n" +
//                "Content-Disposition: form-data; name=\"" + name1 + "\";\r\n" +
//                "Content-Type: text/json\r\n" +
//                "Content-Length: " + Integer.toString(jsonSendData.length()) + "\r\n\r\n" +
//                jsonSendData +
//                "--" + boundary + "\r\n" +
//                "Content-Disposition: form-data; name=\"" + name2 + "\";\r\n" +
//                "Content-Type: image/jpeg\r\n" +
//                "Content-Length: " + Integer.toString(strPic.length()) + "\r\n\r\n" +
//                strPic +
//                "\r\n--" + boundary + "--\r\n";
//
//            ByteArrayEntity httpEntity = new ByteArrayEntity(bodyParam.getBytes("ISO-8859-1"));
//            postMethod.setEntity(httpEntity);
//
//            org.apache.http.auth.UsernamePasswordCredentials creds = new org.apache.http.auth.UsernamePasswordCredentials("admin","hik12345");
//            org.apache.http.auth.AuthScope auth=new org.apache.http.auth.AuthScope(serverURI.getHost(),serverURI.getPort());
//            
//            HTTPClientUtil.httpClient.getCredentialsProvider().setCredentials(auth,(Credentials) creds);
//            HTTPClientUtil.httpClient.getParams().setParameter(AuthPolicy.DIGEST, Collections.singleton(AuthPolicy.DIGEST));
//            HTTPClientUtil.httpClient.getAuthSchemes().register(AuthPolicy.DIGEST,new DigestSchemeFactory());
//
//            CloseableHttpResponse httpResponse = HTTPClientUtil.httpClient.execute(postMethod);
//
//            JSONObject uploadRes = new JSONObject(EntityUtils.toString(httpResponse.getEntity()));
//            System.out.println(uploadRes);
//            if(uploadRes.has("URL")) {
//                strRoute = uploadRes.getString("URL");
//            }else{
//                strRoute = uploadRes.getString("FPID");
//            }
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//        return strRoute;
//    }

//    public static String doPost(String url,String bodyParam)
//    {
//        String strRet = "";
//        try {
//            HttpPost postMethod = new HttpPost(url);
//            URI serverURI = new URI(url);
//            postMethod.addHeader("Accept", "text/html, application/xhtml+xml");
//            postMethod.addHeader("Accept-Language", "zh-CN");
//            postMethod.addHeader("Accept-Encoding","gzip, deflate");
//            postMethod.addHeader("Connection","Keep-Alive");
//
//            ByteArrayEntity httpEntity = new ByteArrayEntity(bodyParam.getBytes("ISO-8859-1"));
//            postMethod.setEntity(httpEntity);
//            org.apache.http.auth.UsernamePasswordCredentials creds = new org.apache.http.auth.UsernamePasswordCredentials("admin","hik12345");
//            org.apache.http.auth.AuthScope auth=new org.apache.http.auth.AuthScope(serverURI.getHost(),serverURI.getPort());
//            HTTPClientUtil.httpClient.getCredentialsProvider().setCredentials(auth,(Credentials) creds);
//            HTTPClientUtil.httpClient.getParams().setParameter(AuthPolicy.DIGEST, Collections.singleton(AuthPolicy.DIGEST));
//            HTTPClientUtil.httpClient.getAuthSchemes().register(AuthPolicy.DIGEST,new DigestSchemeFactory());
//            CloseableHttpResponse httpResponse = HTTPClientUtil.httpClient.execute(postMethod);
//            return EntityUtils.toString(httpResponse.getEntity());
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//        return strRet;
//    }

//    public static String doFacePicAnalysis_simple(String url,String picUrl)
//    {
//        String strData = "";
//        try {
//            JSONObject jsonFaceAnalysis = new JSONObject();
//            jsonFaceAnalysis.put("imagesType","URL");
//            jsonFaceAnalysis.put("imagesData",picUrl);
//            jsonFaceAnalysis.put("algorithmType","faceDetect,faceModel");
//            jsonFaceAnalysis.put("mode","mutiface");
//
//            System.out.println(url);
//            System.out.println(jsonFaceAnalysis.toString());
//
//            JSONObject faceAnalysisRes = new JSONObject(HTTPClientUtil.doPost(url,jsonFaceAnalysis.toString()));
//            System.out.println(faceAnalysisRes.toString());
//            strData = faceAnalysisRes.getJSONArray("targets").getJSONObject(0).getString("targetModelData");
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return strData;
//    }

//    public static String doGet(String url)
//    {
//        String strRet = "";
//        try{
//            HttpGet getMethod = new HttpGet(url);
//            URI serverURI = new URI(url);
//
//            org.apache.http.auth.UsernamePasswordCredentials creds = new org.apache.http.auth.UsernamePasswordCredentials("admin","hik12345");
//            org.apache.http.auth.AuthScope auth=new org.apache.http.auth.AuthScope(serverURI.getHost(),serverURI.getPort());
//            HTTPClientUtil.httpClient.getCredentialsProvider().setCredentials(auth,(Credentials) creds);
//            HTTPClientUtil.httpClient.getParams().setParameter(AuthPolicy.DIGEST, Collections.singleton(AuthPolicy.DIGEST));
//            HTTPClientUtil.httpClient.getAuthSchemes().register(AuthPolicy.DIGEST,new DigestSchemeFactory());
//            CloseableHttpResponse httpResponse = HTTPClientUtil.httpClient.execute(getMethod);
//            return EntityUtils.toString(httpResponse.getEntity());
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return strRet;
//    }


}
