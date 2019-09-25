package com.san.platform.visitor;

import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;

public interface VisitorService {

    /**
     * 查询来访人员   分页
     */
    HashMap<String, Object> queryVisitor(Visitor visitor);

    /**
     * 根据主键更新照片
     * @param visitor
     * @return
     */
    void updateVisitor(Visitor visitor);


    /**
     * 查询来访人员   大屏
     */
    List<Visitor> queryVisitorDisplay(Visitor visitor);

    /**
     * 根据访客id查询访客 白鹏
     * 参数 主键id
     */
    Visitor queryVisitorByID(Visitor visitor);

    /**
     * 查询来访人员
     */
    List<Visitor> queryVisitors(Visitor visitor);

    /**
     * 删除来访人员
     */

    int removeVisitor(List<Integer> visitIds);

    /**
     * 创建来访人员
     *
     * @param visitor
     * @return
     */
    int createVisitor(Visitor visitor);


    /**
     * 根据id集合查询Visitor
     *
     * @param visitIds
     * @return
     */
    List<Visitor> queryVisitor(List<Integer> visitIds);


    /**
     * 查询所有
     */
    List<Visitor> queryAllVisitor();


    /**
     * 更新访客事由
     * @param visitor
     * @return
     */
    int updateVisitCause(Visitor visitor);
    /**
     * 查询visitor根据身份证当天
     */
    Visitor queryVisitorByIDNoToday(String idnumber);
    /**
     * 查询visitor根据身份证历史
     */
    Visitor queryVisitorByIDNo(String idnumber);

    //查询当天的进入访客的id
     Visitor queryVisitorIdByIDNoToday(String idnumber);

    //设置离场时间为null 第二次进场用
    int updateVistorLeaveTime(Integer id);
    /**
     * 查询今日访客--依据事由
     * @param visitor
     * @return
     */
    HashMap<String, Object>queryVisitorInfo(Visitor visitor);

    /**
     * 未离场人员
     * @return
     */
    HashMap<String, Object>queryVisitorLeave(Visitor visitor);

    /**
     * 查询访客信息-isToday=yes 今日来访 isToday=no 历史来访
     * @param visitor
     * @return
     */
    List<Visitor>queryVistorByDate (Visitor visitor);

    /**
     * 根据月份查询历史来访数据
     * @param visitor
     * @return
     */
    List<Visitor> queryOldDateByMonth (Visitor visitor);

    /**
     * 根据id查询访客
     * @param visitor
     * @return
     */
    Visitor queryVisitorById (Visitor visitor) ;



}
