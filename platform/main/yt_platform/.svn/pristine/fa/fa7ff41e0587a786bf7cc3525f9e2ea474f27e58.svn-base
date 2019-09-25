package com.san.platform.user.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.san.platform.BaseController;
import com.san.platform.config.MD5;
import com.san.platform.user.User;
import com.san.platform.user.UserService;
import com.san.platform.config.Global;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.*;

/**
 *
 */
@Controller
public class UserController extends BaseController {

    @Reference
    private UserService userService;

    /**
     * 查询tbl_user 列表----方法废弃
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/api/user/queryAll")
    public HashMap<String, Object> queryAll() {
        logger.info("[user.api]-[get]-[/api/user/queryAll] method have been called.");
        List<User> tbl_users = null;
        HashMap<String, Object> map = new HashMap<>();
        try {
            //tbl_users = userService.selectAllUser();
            map.put("results", tbl_users);
            map.put("code", Global.CODE_SUCCESS);
        } catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[user.api]-[get]-[/api/user/queryAll] CODE_DB_QUERY_EXCEPTION exception,sel_all:" + e.getMessage());
        }
        return map;
    }

    @ResponseBody
    @GetMapping(value = "/api/user/queryPage")
    public HashMap<String, Object> queryPage(User user) {
        logger.info("[user.api]-[get]-[/api/user/queryPage] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            map = userService.selectAllUser(user);
            map.put("code", Global.CODE_SUCCESS);
        } catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[user.api]-[get]-[/api/user/queryPage] CODE_DB_QUERY_EXCEPTION exception,sel_all:" + e.getMessage());
        }
        return map;
    }

    /**
     * 保存tbl_user
     *
     * @param obj
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/api/user/createUser")
    public HashMap<String, Object> createUser(@RequestBody User obj) {
        logger.info("[user.api]-[get]-[/api/user/createUser] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        User tbl_user1 = null;
        try {
            //根据当前用户查询是否存在
            User tbl_user = new User();
            tbl_user.setUserName(obj.getUserName());
            tbl_user1 = userService.selectByUserId(tbl_user);
            if (tbl_user1 != null) {
                map.put("code", Global.CODE_USER_NAME_IS_EXIST);
            } else {
                //将获取到的字符串赋值给密码字段----已加密
                obj.setUserPwd(MD5.getMD5Password(obj.getUserPwd()));
                obj.setUserModTime(new Date());
                obj.setUserCreateTime(new Date());
                int result = userService.saveUser(obj);
                if (result != 0) {
                    map.put("code", Global.CODE_SUCCESS); // 添加用户成功
                } else {
                    map.put("code", Global.CODE_DATA_INSERT_EXCEPION); //添加用户失败
                }
            }
        } catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[user.api]-[get]-[/api/user/createUser] CODE_DB_QUERY_EXCEPTION exception,sel_all:" + e.getMessage());
        }
        return map;
    }

    //根据id查询用户信息
    @ResponseBody
    @RequestMapping("/api/user/queryUserById")
    public Map<String, Object> queryUserById(@RequestBody User user) {
        logger.info("[user.api]-[get]-[/api/user/queryUserById] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        User tbl_user = null;
        try {
            //用户的回显
            tbl_user = userService.selectByUserId(user);
            // 用户存入list 可能会出现多条。所以进行判断
            List<User> tbl_users = new ArrayList<>();
            tbl_users.add(tbl_user);
            map.put("code", Global.CODE_SUCCESS);
            map.put("results", tbl_users);
        } catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[user.api]-[get]-[/api/user/queryUserById] CODE_DB_QUERY_EXCEPTION exception,sel_all:" + e.getMessage());
        }
        return map;
    }

    /**
     * 更新用户
     *
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping("/api/user/updateUser")
    public HashMap<String, Object> updateUser(@RequestBody User user) {
        logger.info("[user.api]-[get]-[/api/user/updateUser] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            User tblUser = new User();
            // 获取当前用户信息----判定密码是否有做修改
            tblUser.setUserId(user.getUserId());
            tblUser = userService.selectByUserId(tblUser);
            // 判断修改的是否重名
            User userJudge = userService.queryUserNameByRoleName(user);
            // 获取当前id
            Integer id = user.getUserId();
            // 如果有修改密码，密码信息做加密处理
            if (!tblUser.getUserPwd().equals(user.getUserPwd())) {
                user.setUserPwd(MD5.getMD5Password(user.getUserPwd()));
            }
            // 判断修改成重复的账户名称
            if (userJudge != null) {
                if (id.equals(userJudge.getUserId())) {
                    userService.updateUser(user);
                    map.put("code", Global.CODE_SUCCESS);
                } else {
                    map.put("code", Global.CODE_USER_NOT_ALLOW_REPEAT_EXCEPTION); // 用户不能重复
                }
            }else {
                    userService.updateUser(user);
                    map.put("code", Global.CODE_SUCCESS);
            }
        } catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[user.api]-[get]-[/api/user/updateUser] CODE_DB_QUERY_EXCEPTION exception,sel_all:" + e.getMessage());
        }
        return map;
    }

    /**
     * 删除用户
     *
     * @param userIds
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/api/user/removeUser")
    public HashMap<String, Object> removeUser(@RequestBody List<Integer> userIds) {
        logger.info("[user.api]-[get]-[/api/user/removeUser] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            for (Integer id : userIds) {
                userService.deleteUser(id);
            }
            map.put("code", Global.CODE_SUCCESS);
        } catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[user.api]-[get]-[/api/user/removeUser] CODE_DB_QUERY_EXCEPTION exception,sel_all:" + e.getMessage());
        }
        return map;
    }

    /**
     * 删除用户(单个)
     *
     * @param userId
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/api/user/removeUserSinglo")
    public HashMap<String, Object> removeUserSinglo(@RequestBody User userId) {
        logger.info("[user.api]-[get]-[/api/user/removeUserSinglo] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            userService.deleteUserSinlgo(userId);
            map.put("code", Global.CODE_SUCCESS);
        } catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[user.api]-[get]-[/api/user/removeUserSinglo] CODE_DB_QUERY_EXCEPTION exception,sel_all:" + e.getMessage());
        }
        return map;
    }
}
