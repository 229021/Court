package com.san.platform.config;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class PictureUtil {

@GetMapping(value = "/file")
public String file() {
    return "file";
}

    @PostMapping(value = "/fileUpload1")
    public String fileUpload(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request) {
        if (file.isEmpty()) {
            System.out.println("文件为空");
        }
        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        File directory = new File("");// 参数为空
        String courseFile = null;
        try {
            courseFile = directory.getCanonicalPath();
            courseFile = courseFile.replaceAll("\\\\","//");
            courseFile = courseFile.replaceAll("main//","");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String peojectName = request.getContextPath();//项目名
       // E://YtWeb//platform//yt_platform/static//img//pic//
        String basePath = request.getScheme()+"://" +request.getServerName()+":"+request.getServerPort()+request.getContextPath()  +"/";
        String filePath = courseFile + peojectName + "//static//img//pic//"; // 上传后的路径
        // String filePath ="E://YtWeb//platform//yt_platform//static//static//img//pic//"; // 上传后的路径
      /*  String filePath = "C://Users//xiaoz//Desktop//platform//yt_platform//static//img//pic//";*/ // 上传后的路径
        fileName = UUID.randomUUID() + suffixName; // 新文件名
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String filename = "/static//img//pic//" + fileName;
        return filename;
    }

    @RequestMapping("/test")
    public String test2(){
        return "test";
    }
}
