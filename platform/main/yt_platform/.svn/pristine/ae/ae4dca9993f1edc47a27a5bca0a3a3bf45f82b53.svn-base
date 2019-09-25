package com.san.platform.region.mapper;

import com.san.platform.region.Region;
import tk.mybatis.mapper.common.Mapper;

import java.util.HashMap;
import java.util.List;

public interface RegionMapper extends Mapper<Region> {

    public List<Region> queryRegionParentId (Region region);

    //根据id查询区域信息
    public Region queryReginById(Region regionId);

    /**
     * 根据上级id查询上级区域
     */
    public List<Region> queryReginByParid(Integer regionId);

    //删除rid此节点
    public int removeRegin(Object regionId);

    //删除pid此节点
    public int removeReginPid(Object regionParentId);

     //新增区域信息
    public int createRegion(Region region);

    //更新当前区域信息
    public int updateRegin(Region region);

    //查询所有
    public List<Region> queryAllRegion();

    // 查询出所有pid带有父节点的
    public List<Region> queryReginByPid(Integer regionParentId);

    //根据name查询
    public List<Region> queryReginByname(String regionName);

    //更新
    public int updateReginonId(String regionSon,Integer regionId);

    //模糊查询
    public List<Region> querySonByLike (String regionId);
}
