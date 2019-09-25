package com.san.platform.region.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.san.platform.region.Region;
import com.san.platform.region.RegionService;
import com.san.platform.region.mapper.RegionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.HashMap;
import java.util.List;

@Service
public class RegionServiceImpl implements RegionService{

    @Autowired
    private RegionMapper regionMapper;

    @Override
    public List<Region> queryAllRegion(){
        return regionMapper.selectAll();
    }
    @Override
    public List<Region> queryRegionParentId (Region region){
        return regionMapper.queryRegionParentId(region);
    }

    @Override
    public Region queryReginById(Region regionId) {
        return regionMapper.queryReginById(regionId);
    }

    @Override
    public List<Region> queryReginByParid(Integer regionId) {
        return regionMapper.queryReginByParid(regionId);
    }

    @Override
    public List<Region> queryReginByPid(Integer regionParentId) {
        return regionMapper.queryReginByPid(regionParentId);
    }

    @Override
    public int updateReginonId(String regionSon, Integer regionId) {
        return regionMapper.updateReginonId(regionSon,regionId);
    }

    @Override
    public List<Region> querySonByLike(String regionId) {
        return regionMapper.querySonByLike(regionId);
    }


    @Override
    public Region queryReginOne(Region region) {
        return regionMapper.selectOne(region);
    }

    @Override
    public int removeRegin(Object regionId) {
        return regionMapper.removeRegin(regionId);
    }

    @Override
    public int removeReginPid(Integer regionParentId) {
        return regionMapper.removeReginPid(regionParentId);
    }

    @Override
    public int createRegion(Region region) {
        return regionMapper.createRegion(region);
    }

    @Override
    public List<Region> queryReginByname(String regionName) {
        return regionMapper.queryReginByname(regionName);
    }


    @Override
    public int updateRegin(Region region) {
        return regionMapper.updateRegin(region);
    }
}
