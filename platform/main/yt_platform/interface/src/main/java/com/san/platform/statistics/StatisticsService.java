package com.san.platform.statistics;

import com.san.platform.visitor.Visitor;

import java.util.List;

public interface StatisticsService {

    public List<Visitor> queryEnter();

    public List<Visitor> queryLeave();


}
