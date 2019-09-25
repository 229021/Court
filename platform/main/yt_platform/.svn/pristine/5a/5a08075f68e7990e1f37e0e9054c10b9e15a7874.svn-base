package com.san.platform.alert.mapper;

import com.san.platform.alert.Alert;
import com.san.platform.alert.AlertList;
import com.san.platform.alert.PersonType;
import com.san.platform.personnel.Personnel;
import com.san.platform.region.Region;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author: Zhouzhongqing
 * @Date: 2019/4/26 11:01
 * @Description: 告警列表Mapper
 */
@Repository
public interface AlertListMapper extends Mapper<AlertList> {

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/5/7 16:26p
     * @Description: 查询告警列表信息 + 分页
     */
    public List<AlertList> queryAlertListPage(AlertList alertList);


    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/5/20 13:46
     * @Description: 导出全部
     */

    List<AlertList> queryAlertList(AlertList alertList);

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/5/20 13:44
     * @Description: 导出选中
     */

    AlertList queryAlertLists(@Param(value = "alertId") Integer alertIds);

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/5/20 13:44
     * @Description: 修改人员类型
     */

    public void updatePersonnelTypes(PersonType personnel);

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/5/25 12:54
     * @Description: 按身份证查询
     */
    Personnel queryPersonnelByNumber(String string);

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/5/25 12:54
     * @Description: 查告警列表
     */

    List<AlertList> selectAlertList();

    //    Integer selectRegion(String deviceIP);
    Region selectRegion(String deviceIP);

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/6/3 12:57
     * @Description: 添加告警列表
     */

    public void createAlertList(AlertList alertList);



}
