package com.san.platform.map;

import java.util.HashMap;
import java.util.List;

public interface MapService {
    /**
     * 查询所有地图信息----搜索查询
     *
     * @param map
     * @return
     */
    public HashMap<String, Object> queryMapPage(Map map);

    /**
     * 根据iD查询map信息
     *
     * @param map
     * @return
     */
    public Map queryMapById(Map map);


    /**
     * @param map
     * @return
     */
    public Map queryMapOne(Map map);

    /**
     * 相同map名的有多少条
     *
     * @param map
     * @return
     */
    public int queryMapCount(Map map);

    /**
     * 删除map信息
     *
     * @param map
     */
    public int removeDevice(Map map);

    /**
     * 添加map信息
     *
     * @param map
     */
    public int createMap(Map map);

    /**
     * 修改map信息
     *
     * @param map
     */
    public int updateMap(Map map);

    /**
     * 查询地图所有
     *
     * @return
     */
    public List<Map> queryAllMap(Map map);

    /**
     * 根据iD查询List信息
     *
     * @param map
     * @return
     */
    public List<Map> queryMapBySelectId(Map map);
}
