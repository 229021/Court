package com.san.platform.setting.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.san.platform.Exception.CustomException;
import com.san.platform.config.Global;
import com.san.platform.setting.DataBackupService;
import com.san.platform.setting.service.util.DataInputAndExport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;

@Service
public class DataBackupServiceImpl implements DataBackupService {

    @Value("${sql.ip}")
    private String sqlIp;
    @Value("${sql.user}")
    private String sqlUser;
    @Value("${sql.password}")
    private String sqlPwd;
    @Value("${sql.databases}")
    private String sqlDatabases;
    @Value("${filePath}")
    private String filePath;

    @Override
    public String getFilePath() {
        return filePath;
    }

    @Override
    public String DataBackups() throws CustomException {

        String path = DataInputAndExport.backup(filePath, sqlIp, sqlUser, sqlPwd, sqlDatabases);

        return path;
    }

    @Override
    public String DataRecovery(String path) throws CustomException {
        DataInputAndExport.restore(path,sqlIp,sqlUser,sqlPwd);
        return "success";
    }

}
