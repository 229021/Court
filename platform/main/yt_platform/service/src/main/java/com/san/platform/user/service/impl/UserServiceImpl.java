package com.san.platform.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.san.platform.config.Global;
import com.san.platform.limit.Limit;
import com.san.platform.user.User;
import com.san.platform.user.UserService;
import com.san.platform.user.mapper.UserMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;

/**
 * 用户模块 实现类
 */
@Service
public class UserServiceImpl  implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public HashMap<String,Object> selectAllUser(User user) {
        HashMap<String, Object> map = new HashMap<>();
        // 当前页码 pageNum 与 每页显示条数 pageSize分页
        PageHelper.startPage(user.getPageNum(), Global.PAGESIZE);
        // 分页查询
        List<User> users = userMapper.selectAll();
        // 得到page相关的info
        PageInfo pageInfo = new PageInfo(users);
        // 将list与相关的page信息传入map到web端
        map.put("pages",new Integer(pageInfo.getPages()));
        map.put("total",pageInfo.getTotal());
        map.put("results",users);
        return map;

    }

    @Override
    public User queryUserNameByRoleName(User user) {
        return userMapper.queryUserNameByRoleName(user);
    }

    @Override
    public int saveUser(User user) {
        return userMapper.insertSelective(user);
    }

    @Override
    public int updateUser(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public User selectByUserId(User user) {
        return userMapper.selectOne(user);

    }

    @Override
    public int deleteUser(Integer userId) {
        return userMapper.deleteByPrimaryKey(userId);
    }

    @Override
    public int deleteUserSinlgo(User userId) {
        return userMapper.deleteByPrimaryKey(userId);
    }

    @Override
    public List<User> selectAllUserName(String tbl_user) {
        return userMapper.selectAllUserName(tbl_user);
    }

    @Override
    public List<User> selectUserPage(HashMap map) { return userMapper.selectUserPage(map); }

    @Override
    public int selectUserCount() {
        return userMapper.selectUserCount();
    }

    @Override
    public int queryUserNameByName(User user) {
        return userMapper.queryUserNameByName(user);
    }

    @Override
    public int queryUserModTimeByName(User user) {
        return userMapper.queryUserModTimeByName(user);
    }

    @Override
    public int queryLockTimeByName(User user) {
        return userMapper.queryLockTimeByName(user);
    }

    @Override
    public int queryUserValidation(User user) {
        return userMapper.queryUserValidation(user);
    }

    @Override
    public User querySafeQuestion(User user) {
        return userMapper.querySafeQuestion(user);
    }

    @Override
    public int updateUserPwdByUser(User user) {
        return userMapper.updatePasswordByName(user);
    }

    @Override
    public int updateLoginErrNum(User user) {
        return userMapper.updateLoginErrNum(user);
    }


    /**
     * 锁定时间加长
     */
    @Override
    public int updateLockTimeByUser(User user) {
        return userMapper.updateLockTimeByUser(user);
    }

    /**
     * 查询权限菜单
     * @param user
     * @return
     */
    @Override
    public List<Limit> queryLimit(User user) {
        return userMapper.queryLimit(user);
    }


}
