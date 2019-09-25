package com.san.platform.alert.service.impl;
import com.github.pagehelper.PageInfo;
import com.san.platform.alert.Alert;
import com.san.platform.alert.AlertService;
import com.alibaba.dubbo.config.annotation.Service;
import com.san.platform.alert.PersonType;
import com.san.platform.alert.mapper.AlertMapper;
import com.san.platform.alert.mapper.PersonTypeMapper;
import com.san.platform.config.Global;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.HashMap;
import java.util.List;

import com.github.pagehelper.PageHelper;


/**
 * @Author: Zhouzhongqing
 * @Date: 2019/4/22 14:59
 * @Description: 告警模块  实现类
 */

@Service
public class AlertServiceImpl implements AlertService {

    @Autowired
    private AlertMapper alertMapper;
    @Autowired
    private PersonTypeMapper personTypeMapper;

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/4/22 15:02
     * @Description: 查询所有告警
     */
    @Override
    public HashMap<String, Object> queryAllAlert(Alert alert) {
        HashMap<String, Object> map = new HashMap<>();
        //当前页 pageNum 与 显示条数 pageSize分页
        PageHelper.startPage(alert.getPageNum(), Global.PAGESIZE);
        //查询所有
        List<Alert> list = alertMapper.queryAlertPage(alert);
        PageInfo pageInfo = new PageInfo(list);
        //将List与相关的page信息传入map
        map.put("pages", new Integer(pageInfo.getPages())); // 页
        map.put("total", pageInfo.getTotal()); // 总的
        map.put("results", list); // 结果
        return map;
    }

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/4/22 18:47
     * @Description: 查询告警（根据ID）
     */
    @Override
    public Alert selectByAlertId(Alert alert) {
//        return alertMapper.selectByPrimaryKey(alert);
        return alertMapper.selectOne(alert);
    }

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/4/22 18:47
     * @Description: 删除告警（多个）
     */
    @Override
    public int removeAlert(Integer alertId) {
        return alertMapper.deleteByPrimaryKey(alertId);
    }

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/4/22 18:48
     * @Description: 删除告警（单个）
     */
    @Override
    public int removeByAlertId(Alert alertId) {
        return alertMapper.deleteByPrimaryKey(alertId);
    }

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/4/22 18:48
     * @Description: 增加告警
     */
    @Override
    public int createAlert(Alert alert) {
        return alertMapper.insertSelective(alert);
    }

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/4/22 18:49
     * @Description: 修改告警
     */
    @Override
    public int updateAlert(Alert alert) {
        return alertMapper.updateByPrimaryKeySelective(alert);
    }


    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/6/10 16:49
     * @Description: 查询人员类型
    */
    @Override
    public List<PersonType> selectPersonType() {
        return personTypeMapper.selectAll();
    }

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/5/25 17:27
     * @Description: 查询告警规则
     */
    @Override
    public List<Alert> selectAll() {
        return alertMapper.selectAll();
    }
}
