package com.san.platform.personnel;

import com.san.platform.alert.PersonType;

import java.util.HashMap;
import java.util.List;

public interface PersonnelService {

    /**
     * 律师库查询
     *
     * @param personnel
     * @return
     */
    public HashMap<String, Object> queryPersonnel(Personnel personnel);

    /*public List<Personnel> queryPersonnel(Personnel personnel);*/

    /**
     * 判断用户是否存在
     *
     * @param personnel
     * @return
     */

    public Personnel queryPersonnelByNumber(Personnel personnel);

    public Personnel queryPersonnelByNumberTongYong(Personnel personnel);

    /**
     * 查询今日所有人员信息
     *
     * @param perType
     * @return
     */
    public List<Personnel> queryPersonnels(Personnel perType);

    /**
     * 查询当前页
     *
     * @param perIds
     * @return
     */
    public List<Personnel> queryPersonnelPage(List<Integer> perIds, Integer perType);

    /**
     * 用于页面重置--测试接口
     *
     * @param personnel
     * @return
     */
    public List<Personnel> queryPersonnelReset(Personnel personnel);

    /**
     * 闹访人查询
     *
     * @param personnel
     * @return
     */
    public HashMap<String, Object> queryTroublePersonnel(Personnel personnel);

    public Personnel selectTroublePersonnel(Personnel personnel);
    /**
     * 失信人查询
     *
     * @param personnel
     * @return
     */
    public HashMap<String, Object> queryLowCreditPersonnel(Personnel personnel);

    /**
     * 特殊人查询
     *
     * @param personnel
     * @return
     */
    public HashMap<String, Object> querySpecialPersonnel(Personnel personnel);

    /**
     * 内部人员查询
     *
     * @param personnel
     * @return
     */
    public HashMap<String, Object> queryStaffPersonnel(Personnel personnel);

    /**
     * 自定义人员查询
     *
     * @param personnel
     * @return
     */
    public HashMap<String, Object> queryCustomizePersonnel(Personnel personnel);


    /**
     * 修改律师库
     *
     * @param personnel
     */
    public void updatePersonnel(Personnel personnel);

    /**
     * 添加律师
     *
     * @param personnel
     */
    public void createPersonnel(Personnel personnel);

    /**
     * 删除律师
     *
     * @param perId
     */
    public void removePersonnel(Integer perId);

    /**
     * 删除律师（通用mapper）
     */
    public void removePersonnelTongYong(Integer perIds);


    /**
     * 编辑详情
     *
     * @param personnel
     * @return
     */
    public Personnel queryPersonnelById(Personnel personnel);

    /* *//**
     * 根据人员类型放置不同的人员库--更改人员状态
     * @param perId
     *//*
    public void updatePersonnelType(Integer perType , Integer perId);*/

    /**
     * 根据人员类型放置不同的人员库--更改人员状态
     *
     * @param personnel
     */
    public void updatePersonnelType(Personnel personnel);

    public void importPersonnel(Personnel personnel);
    /* public void updatePersonnelTypeTongYong(Personnel personnel);*/

    /**
     * @param picturePath
     */
    public void savePicturePath(PicturePath picturePath);

    /**
     * 查询图片路径
     *
     * @param
     */
    public List<String> queryPicPath();

    /**
     * 查询图片路径--personnel表
     *
     * @param
     */
    public List<String> queryPersonnelByTypes(List<Integer> perId, Integer perType);

    /**
     * 查询当前库全部图片
     *
     * @return
     */
    public List<String> queryPersonnelByTypeAll(Integer perType);


    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/6/14 10:01
     * @Description: 查询自定义人像库
     */
    public List<PersonType> queryCustomize(Integer typeSign);

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/6/14 10:29
     * @Description: 添加自定义人像库
     */
    public int createCustomize(PersonType personType);

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/6/14 14:40
     * @Description: 修改自定义人像库
     */
    public int updateCustomize(PersonType personType);

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/6/14 14:41
     * @Description: 删除自定义人像库
     */
    public int deleteCustomizeById(PersonType personType);

    // 删除多个自定义人像库
    public int deleteCustomizeByIds(Integer personTypeId);

    // 按typeId 查找自定义人像库
    public PersonType queryPersonTypeById(PersonType personType);


    /**
     * 根据人员类型查询所有的人员信息
     * @param type
     * @return
     */
    public List<Personnel> queryAllByType (Integer type);

    /**
     * 根据证件号查询人员数据
     * @param perNumber
     * @return
     */
    public int queryCountByNumber(String perNumber);
}
