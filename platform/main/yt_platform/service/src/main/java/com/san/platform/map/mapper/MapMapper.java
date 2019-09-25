package com.san.platform.map.mapper;

import com.san.platform.map.Map;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface MapMapper extends Mapper<Map> {

    public List<Map> queryAllMap(Map map);

    public List<Map> queryMapBySelectId(Map map);
}
