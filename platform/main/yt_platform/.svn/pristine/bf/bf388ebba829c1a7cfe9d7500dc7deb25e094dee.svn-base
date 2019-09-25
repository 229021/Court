package com.san.platform.visitor.mapper;

import com.san.platform.visitor.Visitor;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface VisitorMapper extends Mapper<Visitor> {

    // 模糊查询字段    事由   查询今日
    /**
     * 查询今日来访人员
     */
    List<Visitor> queryVisitor(Visitor visitor);
    //查询visitor根据身份证当天
    Visitor queryVisitorByIDNoToday(String idnumber);
    //查询visitor根据身份证当天历史
    Visitor queryVisitorByIDNo(String idnumber);
    //查询当天的进入访客的id
    Visitor queryVisitorIdByIDNoToday(String idnumber);
    //设置离场时间为null 第二次进场用
    int updateVistorLeaveTime(Integer id);

    /**
     * 查询今日访客二级页面
     * @param visitor
     * @return
     */
    List<Visitor> queryVisitorInfo(Visitor visitor);

    /**
     * 未离场人员
     * @return
     */
    List<Visitor> queryVisitorLeave();

    /**
     * 根据月份查询历史来访数据
     * @param visitor
     * @return
     */
    List<Visitor> queryOldDateByMonth (Visitor visitor);
    /**
     * 根据主键更新照片
     * @param visitor
     * @return
     */
    void updateVisitor(Visitor visitor);
}
