package com.san.platform.statistics.mapper;

import com.san.platform.visitor.Visitor;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface StatisticsMapper extends Mapper<Visitor> {
    
    public List<Visitor> queryEnter();

    public List<Visitor> queryLeave();
}
