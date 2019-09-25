package com.san.platform.data;

import com.alibaba.dubbo.config.annotation.Reference;
import com.san.platform.BaseController;
import com.san.platform.Exception.CustomException;

import com.san.platform.config.Global;
import com.san.platform.setting.DataBackupService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import java.util.HashMap;


@RestController
public class DataController extends BaseController {
    @Reference
    private DataBackupService dataBackupService;
    @ResponseBody
    @RequestMapping("/api/backup/DataBackups")
    public void DataBackUp(HttpServletRequest request, HttpServletResponse response) {
        logger.info("[backup.api]-[get]-[api/backup/DataBackups] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            String path = dataBackupService.DataBackups();
            System.out.println(path);
            if (path != null) {
                //设置文件路径
                File file = new File(path);
                //File file = new File(realPath , fileName);
                String[] split = path.split("/");
                String fileName = split[split.length-1];
                System.out.println(fileName);
                System.out.println(path);
                if (file.exists()) {
                    response.setContentType("application/force-download");// 设置强制下载不打开
                    response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
                    byte[] buffer = new byte[1024];
                    FileInputStream fis = null;
                    BufferedInputStream bis = null;
                    try {
                        fis = new FileInputStream(file);
                        bis = new BufferedInputStream(fis);
                        OutputStream os = response.getOutputStream();
                        int i = bis.read(buffer);
                        while (i != -1) {
                            os.write(buffer, 0, i);
                            i = bis.read(buffer);
                        }
                        map.put("code", Global.CODE_SUCCESS);
                    } catch (FileNotFoundException e) {
                        logger.warn("[data.api]-[get]-[/api/data/DataBackups]  CODE = " + Global.CODE_DATA_FILE_NOTFOUND_EXCEPTION + ",exception message:" + e.getMessage());
                        map.put("code", Global.CODE_DATA_FILE_NOTFOUND_EXCEPTION);
                    } catch (IOException e) {
                        logger.warn("[data.api]-[get]-[/api/data/DataBackups]  CODE = " + Global.CODE_IO_FlOW + ",exception message:" + e.getMessage());
                        map.put("code", Global.CODE_IO_FlOW);
                        //关闭流
                    } finally {
                        if (bis != null) {
                            try {
                                bis.close();
                            } catch (IOException e) {
                                logger.warn("[data.api]-[get]-[/api/data/DataBackups]  CODE = " + Global.CODE_IO_FlOW + ",exception message:" + e.getMessage());
                                map.put("code", Global.CODE_IO_FlOW);
                            }
                        }
                        if (fis != null) {
                            try {
                                fis.close();
                            } catch (IOException e) {
                                logger.warn("[data.api]-[get]-[/api/data/DataBackups]  CODE = " + Global.CODE_IO_FlOW + ",exception message:" + e.getMessage());
                                map.put("code", Global.CODE_IO_FlOW);
                            }
                        }
                    }
                }
            }
            map.put("code", Global.CODE_SUCCESS);
        }catch (CustomException e){
            map.put("code",e.getCode());
            logger.warn("[data.api]-[post]-[/api/data/DataRecovery]  CODE = " + e.getCode() + ",exception message:" + e.getMessage());
        }
//        return map;
    }
    @ResponseBody
    @PostMapping("/api/data/DataRecovery")
    public HashMap<String, Object> DataRecovery(@RequestParam("file") MultipartFile file,HttpServletRequest request) {
        logger.info("[backup.api]-[get]-[api/backup/DataRecovery] method have been called.");
        HashMap<String, Object> map = new HashMap<>();


        try {
            if (file.isEmpty()) {
                map.put("code", Global.CODE_DATA_FILE_NOTFOUND_EXCEPTION);
                return map;
            }
            String filePath = dataBackupService.getFilePath();
            // 获取文件名
            String fileName = file.getOriginalFilename();
            // 设置文件存储路径
            String path = filePath + fileName;
            File dest = new File(path);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();// 新建文件夹
            }
            file.transferTo(dest);// 文件写入
            dataBackupService.DataRecovery(path);
            map.put("code", Global.CODE_SUCCESS);
        } catch (IllegalStateException e) {
            map.put("code",Global.CODE_DATA_ILLEGAL_STATEEXC_EPTION);
            logger.warn("[data.api]-[post]-[/api/data/DataRecovery]  CODE = " + Global.CODE_DATA_ILLEGAL_STATEEXC_EPTION + ",exception message:" + e.getMessage());
        } catch (IOException e) {
            map.put("code",Global.CODE_IO_FlOW);
            logger.warn("[data.api]-[post]-[/api/data/DataRecovery]  CODE = " + Global.CODE_IO_FlOW + ",exception message:" + e.getMessage());
        } catch (CustomException e) {
            map.put("code", e.getCode());
            logger.warn("[data.api]-[post]-[/api/data/DataRecovery]  CODE = " + e.getCode() + ",exception message:" + e.getMessage());
        }
        return map;
    }

}
