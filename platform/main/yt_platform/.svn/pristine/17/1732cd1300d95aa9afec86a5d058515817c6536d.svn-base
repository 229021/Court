package com.san.platform.map.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.san.platform.config.Global;
import com.san.platform.map.Map;
import com.san.platform.map.MapService;
import com.san.platform.map.mapper.MapMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;

@Service
public class MapServiceImpl implements MapService {

    @Autowired
    private MapMapper mapMapper;
    @Override
    public HashMap<String, Object> queryMapPage(Map map) {
        HashMap<String, Object> hashMap = new HashMap<>();
        // 当前页码 pageNum 与 每页显示条数 pageSize分页
        PageHelper.startPage(map.getPageNum(), Global.PAGESIZE);
        List<Map> mapList = mapMapper.queryAllMap(map);
        // 得到page相关的info
        PageInfo pageInfo = new PageInfo(mapList);
        // 将list与相关的page信息传入map到web端
        hashMap.put("pages",new Integer(pageInfo.getPages()));
        hashMap.put("total",pageInfo.getTotal());
        hashMap.put("results",mapList);
        return hashMap;
    }

    @Override
    public Map queryMapById(Map map) {
        return mapMapper.selectByPrimaryKey(map);
    }

    @Override
    public Map queryMapOne(Map map) {
        return mapMapper.selectOne(map);
    }

    @Override
    public int queryMapCount(Map map) {
        return mapMapper.selectCount(map);
    }

    @Override
    public int removeDevice(Map map) {
        return mapMapper.deleteByPrimaryKey(map);
    }

    @Override
    public int createMap(Map map) {
        return mapMapper.insertSelective(map);
    }

    @Override
    public int updateMap(Map map) {
        return mapMapper.updateByPrimaryKeySelective(map);
    }

    @Override
    public List<Map> queryAllMap(Map map) {
        return mapMapper.queryAllMap(map);
    }

    @Override
    public List<Map> queryMapBySelectId(Map map) {
        return mapMapper.queryMapBySelectId(map);
    }
}
