package com.san.platform.alert;

import java.util.HashMap;
import java.util.List;

/**
 * @Author: Zhouzhongqing
 * @Date: 2019/4/22 14:41
 * @Description: 告警模块接口
 */

public interface AlertService {

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/4/22 14:41
     * @Description: 查询所有告警
     */
    HashMap<String, Object> queryAllAlert(Alert alert);

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/4/22 18:33
     * @Description: 查询告警(根据ID)
     */
    Alert selectByAlertId(Alert alert);

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/4/22 18:35
     * @Description: 删除告警(多个)
     */
    int removeAlert(Integer alertId);

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/4/22 18:37
     * @Description: 删除告警(根据ID)
     */
    int removeByAlertId(Alert alertId);

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/4/22 18:40
     * @Description: 添加告警
     */

    int createAlert(Alert alert);

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/4/22 18:41
     * @Description: 修改告警
     */
    int updateAlert(Alert alert);
//    int updateAlert(Integer alertId);

    List<PersonType> selectPersonType();

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/5/25 17:24
     * @Description: 查询所有告警规则
     */
    List<Alert> selectAll();

}
