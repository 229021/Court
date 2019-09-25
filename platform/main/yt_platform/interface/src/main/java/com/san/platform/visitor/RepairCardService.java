package com.san.platform.visitor;

public interface RepairCardService {

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/6/11 13:31
     * @Description: 将需要补卡的人员 放到临时表里
     */

    public int createRepairCard(RepairCard repairCard);

    /**
     * @Author: Zhouzhongqing
     * @Date: 2019/6/12 14:21
     * @Description: 验证失败
    */

    public int deleteRepairCard(RepairCard repairCard);

}
