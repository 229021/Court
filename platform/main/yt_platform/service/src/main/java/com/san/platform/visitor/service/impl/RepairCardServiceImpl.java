package com.san.platform.visitor.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.san.platform.visitor.RepairCard;
import com.san.platform.visitor.RepairCardService;
import com.san.platform.visitor.mapper.RepairCardMapper;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class RepairCardServiceImpl implements RepairCardService {

    @Autowired
    private RepairCardMapper repairCardMapper;


    @Override
    public int createRepairCard(RepairCard repairCard) {
        return repairCardMapper.insertSelective(repairCard);
    }


    @Override
    public int deleteRepairCard(RepairCard repairCard) {
//        return repairCardMapper.deleteByPrimaryKey(repairCard);
        return  repairCardMapper.delete(repairCard);
    }


}
