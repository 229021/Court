package com.san.platform.role.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.san.platform.config.Global;
import com.san.platform.role.Role;
import com.san.platform.role.RoleService;
import com.san.platform.role.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Override
    public HashMap<String,Object> queryAllRole(Role role) {
        HashMap<String, Object> map = new HashMap<>();
        // 当前页码 pageNum 与 每页显示条数 pageSize分页
        PageHelper.startPage(role.getPageNum(), Global.PAGESIZE);
        // 分页查询
        List<Role> roles = roleMapper.selectAll();
        // 得到page相关的info
        PageInfo pageInfo = new PageInfo(roles);
        // 将list与相关的page信息传入map到web端
        map.put("pages",new Integer(pageInfo.getPages()));
        map.put("total",pageInfo.getTotal());
        map.put("results",roles);
        return map;
    }

    @Override
    public List<Role> queryAllRole() {
        return roleMapper.selectAll();
    }

    @Override
    public int createRoleByRole(Role role) {
        return roleMapper.insertSelective(role);
    }

    @Override
    public int updateRoleByRole(Role role) {
        return roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public int removeRoleByRole(Role role) {
        return roleMapper.deleteByPrimaryKey(role);
    }

    @Override
    public Role queryRoleByRoleID(Role role) {
        return roleMapper.selectByPrimaryKey(role);
    }
}
