package com.san.platform.track.trackcontroller;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * 文件上传工具包
 */
public class FileUtils {

    /**
     *
     * @param file 文件
     * @param path 文件存放路径
     * @param fileName 源文件名
     * @return
     */
    public static HashMap<String,Object> upload(MultipartFile file, String path, String fileName){
        HashMap<String, Object> pathMap = new HashMap<>();

        //文件目录
        String realPath = path;
        //生成新的文件名
        String realPath1 = path + fileName ;
        pathMap.put("path",fileName);
        File dest = new File(realPath);
        File dest1 = new File(realPath1);
        //判断文件目录是否存在
        if (!dest.isDirectory() && !dest.exists()) {
            dest.mkdirs();
        }
        try {
            //保存文件
            file.transferTo(dest1);
            pathMap.put("isSign",true);
            return pathMap;
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            pathMap.put("isSign",false);
            return pathMap;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            pathMap.put("isSign",false);
            return pathMap;
        }

    }


}
