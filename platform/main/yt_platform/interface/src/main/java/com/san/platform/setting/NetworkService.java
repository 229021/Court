package com.san.platform.setting;

import com.san.platform.Exception.CustomException;
import org.omg.PortableInterceptor.INACTIVE;
import sun.nio.ch.Net;

import java.util.List;


public interface NetworkService {
    /**
     * 查询所有网卡
     * @return
     */
    List<Network> queryAllNetwork();

    /**
     * //删除网卡
     * @return
     */
    int removeNetworkById(Integer netId) throws CustomException;

    /**
     * 添加网卡
     * @param network
     * @return
     */
    int createNetwork(Network network) throws CustomException;

    /**
     * 更新网卡
     * @param network
     * @return
     */
    int updateNetwork(Network network) throws CustomException;


    /**
     * 查询网卡
     * @param network
     * @return
     */
    Network queryNetworkByName(Network network);


    /**
     * 根据id查询wangka
     */
    Network queryNetworkById(Integer netWorkId);


}
