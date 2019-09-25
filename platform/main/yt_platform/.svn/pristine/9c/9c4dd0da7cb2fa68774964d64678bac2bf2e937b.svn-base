package com.san.platform.alert.mapper;

import com.san.platform.alert.Alert;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Zhouzhongqing
 * @Date: 2019/4/22 14:48
 * @Description: 告警模块Mapper接口
 */

@Repository
public interface AlertMapper extends Mapper<Alert> {

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/4/24 14:57
     * @Description: 查询所有告警信息（按出创建时间降序）
     */
    public List<Alert> queryAlertPage(Alert alert);

}
