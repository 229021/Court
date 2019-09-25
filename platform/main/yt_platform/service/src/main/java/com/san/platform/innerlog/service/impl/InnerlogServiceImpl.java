package com.san.platform.innerlog.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.san.platform.config.Global;
import com.san.platform.innerlog.Innerlog;
import com.san.platform.innerlog.InnerlogService;
import com.san.platform.innerlog.mapper.InnerlogMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class InnerlogServiceImpl implements InnerlogService {

    @Autowired
    private InnerlogMapper innerlogMapper;

    /**
     * 查询所有列表信息
     * @return
     */
    @Override
    public HashMap<String, Object> queryAllInnerlog(Innerlog innerlog) {
        HashMap<String, Object> map = new HashMap<>();
        // 当前页码 pageNum 与 每页显示条数 pageSize分页
        PageHelper.startPage(innerlog.getPageNum(), Global.PAGESIZE);
        // 分页查询
        List<Innerlog> innerlogs = innerlogMapper.selectAll();
        // 得到page相关的info
        PageInfo pageInfo = new PageInfo(innerlogs);
        // 将list与相关的page信息传入map到web端
        map.put("pages",new Integer(pageInfo.getPages()));
        map.put("total",pageInfo.getTotal());
        map.put("results",innerlogs);
        return map;
    }

    /**
     * 查询列表信息根据起始结束时间
     */
    @Override
    public HashMap<String, Object> queryInnerlogByTime(Innerlog innerlog) {
        HashMap<String, Object> map = new HashMap<>();
        // 当前页码 pageNum 与 每页显示条数 pageSize分页
        PageHelper.startPage(innerlog.getPageNum(), Global.PAGESIZE);
        // 分页查询
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date start = innerlog.getStartTime();
        Date end = innerlog.getEndTime();
        String startTime = "";
        String endTime = "";
        if (start!=null&&("".equals(start)==false)){
            startTime = simpleDateFormat.format(start);
        }
        if (end!=null&&("".equals(end)==false)){
            endTime = simpleDateFormat.format(end);
        }
        List<Innerlog> innerlogs = innerlogMapper.queryInnerlogByTime(startTime,endTime);
        // 得到page相关的info
        PageInfo pageInfo = new PageInfo(innerlogs);
        // 将list与相关的page信息传入map到web端
        map.put("pages",new Integer(pageInfo.getPages()));
        map.put("total",pageInfo.getTotal());
        map.put("results",innerlogs);
        return map;
    }

    /**
     * 创建列表信息
     * @param innerlog
     * @return
     */
    @Override
    public HashMap<String, Object> createInnerlog(Innerlog innerlog) {
        HashMap<String, Object> map = new HashMap<>();
        int insert = innerlogMapper.insert(innerlog);
        map.put("results",insert);
        return map;
    }

    @Override
    public List<Innerlog> queryAllInnerlogTwo(String start,String end) {

        List<Innerlog> innerlogs = innerlogMapper.queryInnerlogByTime(start,end);
        return innerlogs;
    }

}
