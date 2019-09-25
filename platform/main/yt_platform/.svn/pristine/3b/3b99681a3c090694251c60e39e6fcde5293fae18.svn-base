package com.san.platform.innerlog;

import java.util.HashMap;
import java.util.List;


/**
 * 操作审计模块
 */
public interface InnerlogService {
    /**
     * 查询列表所有信息
     * @return
     */
    HashMap<String,Object> queryAllInnerlog(Innerlog innerlog);
    /**
     * 查询列表信息根据起始结束时间
     */
    HashMap<String,Object> queryInnerlogByTime(Innerlog innerlog);
    /**
     * 创建列表信息
     */
    HashMap<String, Object> createInnerlog(Innerlog innerlog);
    /**
     *查询列表根据起始时间所有信息不带分页
     */
    List<Innerlog> queryAllInnerlogTwo(String start,String end);
}
