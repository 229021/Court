package com.san.platform.control.mapper;

import com.san.platform.control.Control;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 通用mapper接口
 */
public interface ControlMapper extends Mapper<Control> {

    // 移动时实时更新XY轴
    public int updateControl(Control control);

    // 查询所有
    public List<Control> queryControl(Control control);

    // 根据Mapid查询在哪张地图上
    public List<Control> queryControlMapId(Integer conMapId);

    // 查询去出Mapid的
    public List<Control> queryControlNotconMapId(Integer MapId);

    // 根据Id查询数据有此条没 没有则进行添加
    public List<Control> queryControlId(Control control);

    // 根据设备表新增之后获取id再新增到control表中
    public int createControl(Control control);

    //根据MapId删除
    public int removeControlByMapId(Control control);

    // 根据conDeviceId删除设备信息
    public int removeDeviceconDeviceId (Control control);

}
