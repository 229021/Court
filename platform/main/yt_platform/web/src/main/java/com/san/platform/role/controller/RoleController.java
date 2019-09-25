package com.san.platform.role.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.san.platform.BaseController;
import com.san.platform.config.Global;
import com.san.platform.role.Role;
import com.san.platform.role.RoleService;
import com.san.platform.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class RoleController extends BaseController {
    @Reference
    private RoleService roleService;
    @ResponseBody
    @GetMapping  (value = "/api/role/queryPage")
    public HashMap<String,Object> queryPage (Role role) {
        logger.info("[role.api]-[get]-[/api/role/queryPage] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            map = roleService.queryAllRole(role);
            System.out.println("mmmmmmmmmmmmm"+map);
            map.put("code", Global.CODE_SUCCESS);
        } catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[role.api]-[get]-[/api/role/queryPage] CODE_DB_QUERY_EXCEPTION exception,sel_all:" + e.getMessage());
        }
        return map;
    }

    /**
     *查询全部角色不用分页
     * @return
     */
    @ResponseBody
    @GetMapping  (value = "/api/role/queryRole")
    public HashMap<String,Object> queryRole () {
        logger.info("[role.api]-[get]-[/api/role/queryPage] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            List<Role> roles = roleService.queryAllRole();
            map.put("results",roles);
            map.put("code", Global.CODE_SUCCESS);
        } catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[role.api]-[get]-[/api/role/queryPage] CODE_DB_QUERY_EXCEPTION exception,sel_all:" + e.getMessage());
        }
        return map;
    }
    /**
     * 增加角色
     */
    @ResponseBody
    @RequestMapping(value = "/api/role/createRoleByRole")
    public HashMap<String,Object> createRoleByRole (@RequestBody Role role) {
        logger.info("[role.api]-[get]-[/api/role/createRoleByRole] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            int roleByRole = roleService.createRoleByRole(role);
            System.out.println("mmmmmmmmmmmmm"+roleByRole);
            map.put("code", Global.CODE_SUCCESS);
        } catch (Exception e) {
            map.put("code", Global.CODE_DATA_INSERT_EXCEPION);
            logger.info("[role.api]-[get]-[/api/role/createRoleByRole] CODE_DATA_INSERT_EXCEPION exception,sel_all:" + e.getMessage());
        }
        return map;
    }
    /**
     * 修改角色
     */
    @ResponseBody
    @RequestMapping(value = "/api/role/updateRoleByRole")
    public HashMap<String,Object> updateRoleByRole (@RequestBody Role role) {
        logger.info("[role.api]-[get]-[/api/role/updateRoleByRole] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            int roleByRole = roleService.updateRoleByRole(role);
            System.out.println("mmmmmmmmmmmmm"+roleByRole);
            map.put("code", Global.CODE_SUCCESS);
        } catch (Exception e) {
            map.put("code", Global.CODE_DATA_UPDATE_EXCEPION);
            logger.info("[role.api]-[get]-[/api/role/updateRoleByRole] CODE_DATA_UPDATE_EXCEPION exception,sel_all:" + e.getMessage());
        }
        return map;
    }
    /**
     * 删除角色
     */
    @ResponseBody
    @RequestMapping(value = "/api/role/removeRoleByRole")
    public HashMap<String,Object> removeRoleByRole (@RequestBody Role role) {
        logger.info("[role.api]-[get]-[/api/role/removeRoleByRole] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            int roleByRole = roleService.removeRoleByRole(role);
            if (roleByRole>0){
                System.out.println("mmmmmmmmmmmmm"+roleByRole);
                map.put("code", Global.CODE_SUCCESS);
            }else{
                map.put("code", Global.CODE_ROLE_IS_DELETED_EXCEPTION);
            }
        } catch (Exception e) {
            map.put("code", Global.CODE_ROLE_IS_DELETED_EXCEPTION);
            logger.info("[role.api]-[get]-[/api/role/removeRoleByRole] CODE_ROLE_IS_DELETED_EXCEPTION exception,sel_all:" + e.getMessage());
        }
        return map;
    }
    //根据id查询用户信息
    @ResponseBody
    @RequestMapping("/api/role/queryRoleByRoleID")
    public Map<String, Object> queryRoleByRoleID(@RequestBody Role role) {
        logger.info("[role.api]-[get]-[/api/role/queryRoleByRoleID] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            //用户的回显
            Role role1 = roleService.queryRoleByRoleID(role);
            map.put("code", Global.CODE_SUCCESS);
            map.put("results", role1);
        } catch (Exception e) {
            map.put("code", Global.CODE_DB_QUERY_EXCEPTION);
            logger.info("[role.api]-[get]-[/api/role/queryRoleByRoleID] CODE_DB_QUERY_EXCEPTION exception,sel_all:" + e.getMessage());
        }
        return map;
    }
}
