package com.san.platform.user;
import com.san.platform.limit.Limit;

import java.util.HashMap;
import java.util.List;

/**
 * 用户模块interface
 */
public interface UserService {

    /**
     * 查询所有用户
     * @return
     */
    HashMap<String,Object> selectAllUser(User user);

    /**
     *
     * @param user
     * @return
     */
    public User queryUserNameByRoleName (User user);

    /**
     * 添加用户信息
     * @param user
     * @return
     */
    int saveUser(User user);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    int updateUser(User user);

    /**
     * 根据ID查询用户信息
     * @param user
     * @return
     */
    User selectByUserId(User user);

    /**
     * 根据ID删除用户
     * @param userId
     * @return
     */
    int deleteUser(Integer userId);

    /**
     * 根据ID删除用户(单个)
     * @param userId
     * @return
     */
    int deleteUserSinlgo(User userId);

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
     * 查询总条数
     * @return
     */
    public int selectUserCount () ;
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
     * 更新用户信息通过 User
     */
    public int updateUserPwdByUser(User user);

    /**
     *更新登录错误次数
     */

    int updateLoginErrNum(User user);
    /**
     * 锁定时间加长
     */
    int updateLockTimeByUser(User user);

    /**
     *查询权限菜单
     */
    List<Limit> queryLimit(User user);
}
