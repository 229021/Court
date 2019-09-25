package com.san.platform.user.mapper;

import com.san.platform.limit.Limit;
import com.san.platform.user.User;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.HashMap;
import java.util.List;

/**
 * 用户模块mapper接口
 */
@Repository
public interface UserMapper extends Mapper<User> {

    /**
     * 查询部分字段--用于保存时的验证
     * @return
     */
    public List<User> selectAllUserName(String tbl_user);

    /**
     * 查询所有用户分页
     * @paramstart
     * @paramlen
     * @return
     */
    public List<User> selectUserPage (HashMap map);

    /**
     *
     * @param user
     * @return
     */
    public User queryUserNameByRoleName (User user);

    /**
     * 查询所有条数
     */
    public int selectUserCount ();
    /**
     * 验证用户名是否存在
     * 存在返回1
     * 不存在返回0
     * 白鹏
     */
    public int queryUserNameByName(User user);
    /**
     * 用户登录验证
     * 查询密码是否失效
     */
    public int queryUserModTimeByName(User user);
    /**
     * 用户是否处于锁定状态
     */
    public int queryLockTimeByName(User user);
    /**
     * 用户密码验证
     */
    public int queryUserValidation(User user);
    /**
     * 密码失效后重新设置密码
     */
    public User querySafeQuestion(User user);
    /**
     * 更新密码
     */
     int updatePasswordByName(User user);

    /**
     * 更新错误数
     */
    int updateLoginErrNum(User user);
    /**
     * 锁定时间加长
     */
    int updateLockTimeByUser(User user);

    /**
     * 查询权限菜单
     * @param user
     * @return
     */
    List<Limit> queryLimit(User user);
}
