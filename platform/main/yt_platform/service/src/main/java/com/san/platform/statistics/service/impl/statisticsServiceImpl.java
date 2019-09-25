package com.san.platform.statistics.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.san.platform.statistics.StatisticsService;
import com.san.platform.statistics.mapper.StatisticsMapper;
import com.san.platform.visitor.Visitor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class statisticsServiceImpl implements StatisticsService {

    @Autowired
    private StatisticsMapper statisticsMapper;

    @Override
    public List<Visitor> queryEnter() {
        return statisticsMapper.queryEnter();
    }

    @Override
    public List<Visitor> queryLeave() {
        return statisticsMapper.queryLeave();
    }
}
