package com.san.platform.hikface.init;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.config.annotation.Reference;
import com.san.platform.hikface.HikfaceService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

/**
 * 启动登陆脸谱
 */
@Service
public class InitHikFace implements ApplicationRunner {

    @Value("${face.ip}")
    String serverIp;   // 脸谱ip地址
    @Value("${face.port}")
    int serverPort;     // ISAPI通信端口
    @Value("${face.user}")
    String userName;   // 用户名
    @Value("${face.pass}")
    String userPwd;    // 密码

    @Reference
    HikfaceService hikFaceService;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        hikFaceService.login(serverIp,serverPort,userName,userPwd);
    }
}
