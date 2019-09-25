package com.san.platform.network.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.san.platform.BaseController;
import com.san.platform.Exception.CustomException;
import com.san.platform.config.Global;
import com.san.platform.setting.Network;
import com.san.platform.setting.NetworkService;

import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
public class NetworkController extends BaseController {


    @Reference
    private NetworkService networkService;

    /**
     * 查询所有网卡
     *
     * @return
     */

    @GetMapping("/api/network/queryAllNetwork")
    public HashMap<String, Object> queryAllNetwork() {
        logger.info("[network.api]-[get]-[/api/network/queryAllNetwork] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            List<Network> networks = networkService.queryAllNetwork();
            map.put("results", networks);
            map.put("code", Global.CODE_SUCCESS);
        } catch (Exception e) {
            logger.warn("[network.api]-[get]-[/api/network/queryAllNetwork]  CODE = "+Global.CODE_DB_QUERY_EXCEPTION +",exception message:"+ e.getMessage());
            map.put("code", Global.CODE_NETWORK_QUERY_EXCEPTION);
            //e.printStackTrace();
        }
        return map;
    }

    /**
     * 根据id 查询网卡
     */


    @GetMapping("/api/network/queryNetworkById")
    public HashMap<String, Object> queryNetworkById(Integer netId) {
        logger.info("[network.api]-[get]-[/api/network/selectNetwork] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        System.out.println(netId);
        try {
            Network re_network = networkService.queryNetworkById(netId);
            System.out.println(re_network.toString());
            map.put("results", re_network);
            map.put("code", 200);
        } catch (Exception e) {
            logger.warn("[network.api]-[get]-[/api/network/selectNetwork]  CODE = "+Global.CODE_DB_QUERY_EXCEPTION +",exception message:"+ e.getMessage());
            map.put("code", Global.CODE_NETWORK_QUERY_EXCEPTION);
            //e.printStackTrace();
        }
        return map;
    }


    /**
     * 更新网卡
     *
     * @param network
     * @return
     */
    @PutMapping("/api/network/updateNetwork")
    public HashMap<String, Object> updateNetwork(@RequestBody Network network) {
        logger.info("[network.api]-[put]-[/api/network/updateNetwork] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            network.setNetUpdateTime(new Date());
            networkService.updateNetwork(network);
            map.put("code", Global.CODE_SUCCESS);
        } catch (CustomException e) {
            logger.warn("[network.api]-[put]-[/api/network/updateNetwork] CODE = "+e.getCode() +",exception message:" + e.getMessage());
            map.put("code",e.getCode());
        } catch (Exception e) {
            logger.warn("[network.api]-[put]-[/api/network/updateNetwork]  CODE = "+Global.CODE_DB_QUERY_EXCEPTION +",exception message:"+ e.getMessage());
            map.put("code", Global.CODE_NETWORK_UPDATE_EXCEPTION);
            //e.printStackTrace();
        }
        return map;
    }

    /**
     * 根据ID 删除网卡
     *
     * @param
     * @return map
     */
    @DeleteMapping("/api/network/removeNetworkById")
    public HashMap<String, Object> removeNetworkById(Integer netId) {
        System.out.println(netId);
        logger.info("[network.api]-[delete]-[/api/network/deleteNetworkById] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        try {
            networkService.removeNetworkById(netId);
            map.put("code", Global.CODE_SUCCESS);
        }  catch (Exception e) {
            logger.warn("[network.api]-[delete]-[/api/network/deleteNetworkById]  CODE = "+Global.CODE_DB_QUERY_EXCEPTION +",exception message:"+ e.getMessage());
            map.put("code", Global.CODE_NETWORK_REMOVE_EXCEPTION);
            //e.printStackTrace();
        }
        return map;
    }

    /**
     * 创建网卡
     *
     * @param network
     * @return
     */
    @PostMapping("/api/network/createNetwork")
    public HashMap<String, Object> createNetwork(@RequestBody Network network) {
        logger.info("[network.api]-[post]-[/api/network/createNetwork] method have been called.");
        HashMap<String, Object> map = new HashMap<>();
        Network temp = new Network();
        temp.setNetName(network.getNetName());
        try {
            if (networkService.queryNetworkByName(temp) == null) {
                network.setNetUpdateTime(new Date());
                networkService.createNetwork(network);
                map.put("code", Global.CODE_SUCCESS);
            } else {
                map.put("code", Global.CODE_NETWORK_IS_EXIST);
            }
        }catch (CustomException e){
            map.put("code",e.getCode());
            logger.warn("[network.api]-[post]-[/api/network/createNetwork]  CODE = "+e.getCode() +",exception message:"+ e.getMessage());
            // e.printStackTrace();
        }
        catch (Exception e) {
            logger.warn("[network.api]-[post]-[/api/network/createNetwork]  CODE = "+Global.CODE_DB_QUERY_EXCEPTION +",exception message:"+ e.getMessage());
            map.put("code", Global.CODE_NETWORK_CREATE_EXCEPTION);
            // e.printStackTrace();
        }
        return map;
    }
}
