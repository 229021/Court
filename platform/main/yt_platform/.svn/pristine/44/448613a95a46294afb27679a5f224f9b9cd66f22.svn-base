package com.san.platform.visitor.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.san.platform.config.DateUtil;
import com.san.platform.config.Global;
import com.san.platform.visitor.Visitor;
import com.san.platform.visitor.VisitorService;
import com.san.platform.visitor.mapper.VisitorMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


@Service
public class VisitorServiceImpl implements VisitorService {
    @Autowired
    private VisitorMapper visitorMapper;

    /**
     * 查询来访人员 分页
     * @param visitor
     * @return
     */

    @Override
    public HashMap<String, Object> queryVisitor(Visitor visitor) {



        HashMap<String, Object> map = new HashMap<>();

        // 当前页码 pageNum 与 每页显示条数 pageSize分页
        PageHelper.startPage(visitor.getPageNum(), Global.PAGESIZE);
        // 分页查询
        List<Visitor> visitors = visitorMapper.queryVisitor(visitor);
        PageInfo pageInfo = new PageInfo(visitors);
        // 将list与相关的page信息传入map到web端
        map.put("pages", new Integer(pageInfo.getPages()));
        map.put("total", pageInfo.getTotal());
        map.put("results", visitors);
        return map;
    }

    /**
     * 根据主键更新照片
     * @param visitor
     * @return
     */
    @Override
    public void updateVisitor(Visitor visitor) {
        visitorMapper.updateVisitor(visitor);
    }

    @Override
    public  List<Visitor> queryVisitorDisplay(Visitor visitor) {
        PageHelper.startPage(visitor.getPageNum(), Global.PAGESIZE);
        return visitorMapper.queryVisitor(visitor);
    }

    /**
     * 根据访客id查询访客 白鹏
     * 参数 主键id
     */
    @Override
    public  Visitor queryVisitorByID(Visitor visitor) {
        return visitorMapper.selectByPrimaryKey(visitor);
    }


    /**
     * 查询来访人员
     * @param visitor
     * @return
     */
    @Override
    public List<Visitor> queryVisitors(Visitor visitor) {
        return visitorMapper.queryVisitor(visitor);
    }

    /**
     * 删除来访人员
     * @param visitIds
     * @return
     */
    @Override
    public int removeVisitor(List<Integer> visitIds) {
        int sum = 0;

        for (Integer visitId : visitIds) {
            sum += visitorMapper.deleteByPrimaryKey(visitId);
        }

        return sum;
    }

    /**
     * 添加来访人员
     * @param visitor
     * @return
     */
    @Override
    public int createVisitor(Visitor visitor) {
        return visitorMapper.insertSelective(visitor);
    }

    /**
     * 根据id集合 查询来访人员
     * @param visitIds
     * @return
     */
    @Override
    public List<Visitor> queryVisitor(List<Integer> visitIds) {
        ArrayList<Visitor> visitors = new ArrayList<>();

        for (Integer id : visitIds) {
            visitors.add(visitorMapper.selectByPrimaryKey(id));
        }

        return visitors;
    }


    /**
     * 查询所有
     * @return
     */
    @Override
    public List<Visitor> queryAllVisitor() {
        return visitorMapper.selectAll();
    }

    @Override
    public int updateVisitCause(Visitor visitor) {
        return visitorMapper.updateByPrimaryKeySelective(visitor);
    }
    //查询visitor根据身份证
    @Override
    public Visitor queryVisitorByIDNoToday(String idnumber) {
        return visitorMapper.queryVisitorByIDNoToday(idnumber);
    }

    @Override
    public Visitor queryVisitorByIDNo(String idnumber) {
        return visitorMapper.queryVisitorByIDNo(idnumber);
    }
    //查询当天的进入访客的id
    @Override
    public Visitor queryVisitorIdByIDNoToday(String idnumber) {
        return visitorMapper.queryVisitorIdByIDNoToday(idnumber);
    }

    @Override
    public int updateVistorLeaveTime(Integer id) {
        return visitorMapper.updateVistorLeaveTime(id);
    }

    /**
     * 查询今日访客--二级页面
     * @param visitor
     * @return
     */
    @Override
    public HashMap<String, Object> queryVisitorInfo(Visitor visitor) {
        HashMap<String, Object> map = new HashMap<>();

        // 当前页码 pageNum 与 每页显示条数 pageSize分页
        PageHelper.startPage(visitor.getPageNum(), Global.PAGESIZE);
        // 分页查询
        List<Visitor> visitors = visitorMapper.queryVisitorInfo(visitor);
        PageInfo pageInfo = new PageInfo(visitors);
        // 将list与相关的page信息传入map到web端
        map.put("pages", new Integer(pageInfo.getPages()));
        map.put("total", pageInfo.getTotal());
        map.put("results", visitors);
        return map;
    }

    @Override
    public HashMap<String, Object> queryVisitorLeave(Visitor visitor) {
        HashMap<String, Object> map = new HashMap<>();

        // 当前页码 pageNum 与 每页显示条数 pageSize分页
        PageHelper.startPage(visitor.getPageNum(), Global.PAGESIZE);
        // 分页查询
        List<Visitor> visitors = visitorMapper.queryVisitorLeave();

        PageInfo pageInfo = new PageInfo(visitors);
        // 将list与相关的page信息传入map到web端
        map.put("pages", new Integer(pageInfo.getPages()));
        map.put("total", pageInfo.getTotal());
        map.put("results", visitors);
        return map;
    }

    /**
     * 查询访客信息-isToday=yes 今日来访 isToday=no 历史来访
     * @param visitor
     * @return
     */
    @Override
    public List<Visitor>queryVistorByDate (Visitor visitor){
        return visitorMapper.queryVisitor(visitor);
    }

    /**
     * 根据月份查询历史来访数据
     * @param visitor
     * @return
     */
    @Override
    public List<Visitor> queryOldDateByMonth (Visitor visitor){
        return visitorMapper.queryOldDateByMonth(visitor);
    }

    /**
     * 根据id查询访客
     * @param visitor
     * @return
     */
    @Override
    public Visitor queryVisitorById (Visitor visitor) {
        return visitorMapper.selectByPrimaryKey(visitor);
    }


}

