package com.san.platform.user.logincontroller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.san.platform.BaseController;
import com.san.platform.config.Global;
import com.san.platform.config.MD5;
import com.san.platform.limit.Limit;
import com.san.platform.user.User;
import com.san.platform.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 用户登录验证
 */
@Controller
public class UserLogin extends BaseController {

    @Reference
    private UserService userService;
    //登录验证
    @ResponseBody
    @RequestMapping(value = "/api/user/userlogin")
    public HashMap<String, Object> queryAll(@RequestBody User user) {
        logger.info("[userlogin.api]-[get]-[/api/user/userlogin] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            User tbl_user = new User();
            tbl_user.setUserName(user.getUserName());
            tbl_user = userService.selectByUserId(tbl_user);
            int i1=0,i2=1,i3 = 0 ;
            //用户名是否存在
            if (tbl_user != null) {
                i1 = 1;
                //用户密码是否过期
                if (tbl_user.getChangePwdTime() != null && !tbl_user.getChangePwdTime().equals("")){
                    i2 = userService.queryUserModTimeByName(user);
                }
                //用户是否为锁定状态
                if (tbl_user.getLockTime() != null && !tbl_user.getLockTime().equals("")) {
                    i3 = userService.queryLockTimeByName(user);
                }
                if (i2<1){
                    map.put("code",Global.CODE_USER_CHANGE_PASS);
                }else if (i3>0){
                    map.put("code",Global.CODE_USER_IS_LOCK);
                }else {
                    //账号密码验证
                    user.setUserPwd(MD5.getMD5Password(user.getUserPwd()));
                    int i = userService.queryUserValidation(user);
                    if (i==1){
                        //登录成功
                        map.put("code", Global.CODE_SUCCESS);
                        map.put("userName",tbl_user.getRelName());
                        user.setLoginErrNum(0);
                        userService.updateLoginErrNum(user);
                        //左侧菜单栏
                        List<Limit> limit = userService.queryLimit(user);
                        map.put("limit", limit);
                    }else{
                        map.put("code", Global.CODE_USER_PASS_NOT_WRONG );
                        //User user1 = userService.querySafeQuestion(user);
                        //获取当前测试次数
                        Integer loginErrNum = tbl_user.getLoginErrNum();
                        loginErrNum = loginErrNum + 1 ;
                        //剩余错误次数返回
                        map.put("errorNum",Global.CODE_USER_LOGNUM - loginErrNum);
                        tbl_user.setLoginErrNum(loginErrNum);
                        //更新错误次数
                        userService.updateLoginErrNum(tbl_user);
                        if(loginErrNum>=5){
                            long currentTime = System.currentTimeMillis() + 5 * 60 * 1000;
                            Date date = new Date(currentTime);
                            user.setLockTime(date);
                            user.setLoginErrNum(0);
                            userService.updateLockTimeByUser(user);
                            userService.updateLoginErrNum(user);
                        }
                    }
                }
            }else {
                map.put("code",Global.CODE_USER_NAME_NOT_EXIST);
            }
        } catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[user.api]-[get]-[/api/user/userlogin] CODE_DB_QUERY_EXCEPTION exception,sel_all:" + e.getMessage());
        }
        return map;
    }
    //忘记密码获取问题和答案 参数userName
    @ResponseBody
    @RequestMapping(value = "/api/user/querySafeQuestion")
    public HashMap<String, Object> querySafeQuestion(@RequestBody User user) {
        logger.info("[userlogin.api]-[get]-[/api/user/querySafeQuestion] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            User user1 = userService.querySafeQuestion(user);
            if (user1 != null) {
                String safeQuestion = user1.getSafeQuestion();//问题
                String safeAnswer = user1.getSafeAnswer();//正确答案

                map.put("safeQuestion", safeQuestion);
                map.put("safeAnswer", safeAnswer);
                map.put("code", Global.CODE_SUCCESS);
            } else {
                map.put("code", Global.CODE_USER_NAME_NOT_EXIST);
            }
        } catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[userlogin.api]-[get]-[/api/user/querySafeQuestion] CODE_DB_QUERY_EXCEPTION exception,sel_all:" + e.getMessage());
        }
        return map;
    }
    //修改密码
    @ResponseBody
    @RequestMapping(value = "/api/user/updateUserPwdByUser")
    public HashMap<String, Object> updateUserPwdByUser(@RequestBody User user) {
        logger.info("[userlogin.api]-[get]-[/api/user/updateUserPwdByUser] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            // 密码加密处理
            user.setUserPwd(MD5.getMD5Password(user.getUserPwd()));
            int i = userService.updateUserPwdByUser(user);
            if (i == 1) {
                map.put("code", Global.CODE_USER_CHANGE_PASS_SUCCESS);//密码更新成功
            } else {
                map.put("code", Global.CODE_DATA_INSERT_EXCEPION);//密码更新失败
            }
        } catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);

            logger.info("[user.api]-[get]-[/api/user/updateUserPwdByUser] CODE_DB_QUERY_EXCEPTION exception" + e.getMessage());
        }
        return map;
    }
    @ResponseBody
    @RequestMapping(value = "/api/user/updatedByUser")
    public HashMap<String, Object> updatedByUser(@RequestBody User user) {
        logger.info("[userlogin.api]-[get]-[/api/user/updateUserPwdByUser] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            // 密码加密处理

            List<Limit> limit = userService.queryLimit(user);
            map.put("code", Global.CODE_SUCCESS);
            map.put("limit", limit);
        } catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);

            logger.info("[user.api]-[get]-[/api/user/updateUserPwdByUser] CODE_DB_QUERY_EXCEPTION exception" + e.getMessage());
        }
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/api/user/queryLoginName")
    public HashMap<String, Object> queryLoginName(@RequestBody User user) {
        logger.info("[user.api]-[get]-[/api/user/queryLoginName] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            // 密码加密处理

            List<Limit> limit = userService.queryLimit(user);
            map.put("code", Global.CODE_SUCCESS);
            map.put("limit", limit);
        } catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[user.api]-[get]-[/api/user/queryLoginName] CODE_DB_QUERY_EXCEPTION exception" + e.getMessage());
        }
        return map;
    }

}
