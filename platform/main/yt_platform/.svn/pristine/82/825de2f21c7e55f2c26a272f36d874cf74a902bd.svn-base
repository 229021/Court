package com.san.platform.alert;

import com.san.platform.personnel.Personnel;
import com.san.platform.region.Region;
import com.san.platform.visitor.Visitor;

import java.util.HashMap;
import java.util.List;

/**
 * @Author: Zhouzhongqing
 * @Date: 2019/4/26 10:50
 * @Description: 告警列表接口
 */

public interface AlertListService {

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/5/5 12:58
     * @Description: 查询告警列表全部 + 分页
     */
    HashMap<String, Object> queryAlertListPage(AlertList alertList);

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/5/5 13:00
     * @Description: 保存其他人像库
     */
    List<AlertList> queryAlertListById(List<Integer> alertIds);

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/5/18 14:01
     * @Description: 导出选中
     */
    List<AlertList> queryAlertLists(List<Integer> alertIds);

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/5/5 12:58
     * @Description: 查询告警列表全部  导出全部
     */
//    List<AlertList> queryAlertList(AlertList alertList);
    List<AlertList> queryAlertList();

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/4/26 10:50
     * @Description: 添加告警列表
     */
    public int createAlertList(AlertList alertList);

    /**
     * @param alertId
     * @Author: Zhouzhongqing
     * @Date: 2019/4/26 10:53
     * @Description: 删除告警列表信息（多个）
     */

    public int removeAlertList(Integer alertId);

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/4/26 10:54
     * @Description: 删除告警列表（单个）
     */

    public int removeByAlertListId(AlertList alertId);

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/5/23 12:40
     * @Description: 保存到其他人像库
     */

    public void updatePersonnelTypes(PersonType personnel);


    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/5/25 17:22
     * @Description: 根据身份证查询人像库对象
     */
    public Personnel queryPersonnelByNumber(String string);

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/5/25 10:49
     * @Description: 查询
     */
    List<AlertList> selectAll();


    // 根据球机IP 查询所在区域
    Region selectRegionById(String deviceIP);

    // 告警弹窗接口
    public void alarmwindow(String deviceIP, String number, String small, String big);

    /**
     * 根据人员类型查询库名字，库类型
     * @param getbyid
     * @return
     */
    public PersonType Getbyid(Integer getbyid);

}
