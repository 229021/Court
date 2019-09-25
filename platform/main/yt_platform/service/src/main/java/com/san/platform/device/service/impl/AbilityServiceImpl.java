package com.san.platform.device.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.san.platform.config.Global;
import com.san.platform.device.Ability;
import com.san.platform.device.AbilityService;
import com.san.platform.device.mapper.AbilityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: zsa
 * @Date: 2019/4/29 11:22
 * @Description: 实现类
 */
@Service
public class AbilityServiceImpl implements AbilityService {

    @Autowired
    private AbilityMapper abilityMapper;

    @Override
    public HashMap<String, Object> queryAllAbility() {
        HashMap<String, Object> map = new HashMap<>();
        List<Ability> abilities = abilityMapper.selectAll();
        map.put("results", abilities);
        return map;
    }

    @Override
    public HashMap<String, Object> queryAbilityPage(Ability ability) {
        HashMap<String, Object> map = new HashMap<>();
        // 当前页码 pageNum 与 每页显示条数 pageSize分页
        PageHelper.startPage(ability.getPageNum(), Global.PAGESIZE);
        // 分页查询
        List<Ability> abilities = abilityMapper.queryAllAbility(ability);
        // 得到page相关的info
        PageInfo pageInfo = new PageInfo(abilities);
        // 将list与相关的page信息传入map到web端
        map.put("pages",new Integer(pageInfo.getPages()));
        map.put("total",pageInfo.getTotal());
        map.put("results", abilities);
        return map;
    }

    @Override
    public Ability queryAbilityById(Ability ability) {
        return abilityMapper.selectByPrimaryKey(ability);
    }

    @Override
    public int removeAbility(Integer actId) {
        return abilityMapper.deleteByPrimaryKey(actId);
    }

    @Override
    public int createAbility(Ability ability) {
        return abilityMapper.insertSelective(ability);
    }

    @Override
    public int updateAbility(Ability ability) {
        return abilityMapper.updateByPrimaryKeySelective(ability);
    }
}
