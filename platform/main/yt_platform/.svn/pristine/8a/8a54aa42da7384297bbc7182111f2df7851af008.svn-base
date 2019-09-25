package com.san.platform.region;

import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.HashMap;
import java.util.List;

public interface RegionService {

    /**
     * 查询所有区域信息
     * @return
     */
    public List<Region> queryAllRegion();

    public List<Region> queryRegionParentId (Region region);
    /**
     * 根据id信息查询上级id
     * @param
     * @return
     */
    public Region queryReginById(Region regionId);

    // 重复查询上级id
    public List<Region> queryReginByParid(Integer regionId);

    // 查询出所有pid带有父节点的
    public List<Region> queryReginByPid(Integer regionParentId);

    //更新
    public int updateReginonId(String regionSon,Integer regionId);

    //模糊查询
    public List<Region> querySonByLike (String regionId);

    /**
     * 查询唯一的区域信息
     * @param region
     * @return
     */
    public Region queryReginOne(Region region);

    /**
     * 移除当前区域信息
     * @param
     * @return
     */
    public int removeRegin(Object regionId);

    /**
     * 移除当前pid区域信息
     * @param
     * @return
     */
    public int removeReginPid(Integer regionParentId);

    /**
     * 创建区域信息
     * @param region
     * @return
     */
    public int createRegion(Region region);

    //根据name查询出
    public List<Region> queryReginByname(String regionName);

    /**
     * 更新当前区域信息
     * @param region
     * @return
     */
    public int updateRegin(Region region);
}
