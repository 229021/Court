package com.san.platform.control.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.san.platform.control.Control;
import com.san.platform.control.ControlService;
import com.san.platform.control.mapper.ControlMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 实现类
 */
@Service
public class ControlServiceImpl implements ControlService {

    @Autowired
    private ControlMapper controlmapper;

    // 更新
    @Override
    public int updateControl(Control control) {
        return controlmapper.updateControl(control);
    }

    // 查询所有
    @Override
    public List queryControl(Control control) {
        return controlmapper.queryControl(control);
    }

    @Override
    public List<Control> queryControlMapId(Integer conMapId) {
        return controlmapper.queryControlMapId(conMapId);
    }

    @Override
    public List<Control> queryControlNotconMapId(Integer mapId) {
        return controlmapper.queryControlNotconMapId(mapId);
    }

    @Override
    public List<Control> queryControlId(Control control) {
        return controlmapper.queryControlId(control);
    }

    @Override
    public int createControl(Control control) {
        return controlmapper.createControl(control);
    }

    @Override
    public int removeControlByMapId(Control control) {
        return controlmapper.removeControlByMapId(control);
    }

    @Override
    public int removeDeviceconDeviceId(Control control) {
        return controlmapper.removeDeviceconDeviceId(control);
    }
}
