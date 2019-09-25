package com.san.platform.setting.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.san.platform.Exception.CustomException;
import com.san.platform.setting.Network;
import com.san.platform.setting.NetworkService;
import com.san.platform.setting.mapper.NetworkMapper;
import com.san.platform.setting.service.util.SystemNetworkFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NetworkServiceImpl implements NetworkService {

    @Autowired
    private NetworkMapper networkMapper;


    /**
     * 查询所有网卡
     *
     * @return
     */
    @Override
    public List<Network> queryAllNetwork() {
        return networkMapper.selectAll();
    }


    /**
     * 根据id删除网卡
     *
     * @param
     * @return
     */
    @Override
    public int removeNetworkById(Integer netId) {
         return networkMapper.deleteByPrimaryKey(netId);
    }

    /**
     * 创建网卡
     *
     * @param network
     * @return
     */
    @Override
    public int createNetwork(Network network) throws CustomException {
        int i = 0;
        if (SystemNetworkFile.checkNetwork(network)) {
            try {
                SystemNetworkFile.updateLinuxNetwork(network);
                i = networkMapper.insertSelective(network);
                return i;
            }catch (CustomException e){

            }

        }
        return i;
    }


    /**
     * 更新网卡
     *
     * @param network
     * @return
     */

    @Override
    @Transactional(rollbackFor = CustomException.class)
    public int updateNetwork(Network network) throws CustomException {
        int i = networkMapper.updateByPrimaryKeySelective(network);
        SystemNetworkFile.updateLinuxNetwork(network);
        return i;
    }


    @Override
    public Network queryNetworkByName(Network network) {
        return networkMapper.selectOne(network);
    }

    @Override
    public Network queryNetworkById(Integer netWorkId) {
        return networkMapper.selectByPrimaryKey(netWorkId);
    }
}
