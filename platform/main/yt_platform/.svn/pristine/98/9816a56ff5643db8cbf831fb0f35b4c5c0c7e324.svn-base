package com.san.platform.option;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: Zhouzhongqing
 * @Date: 2019/4/18 10:43
 * @Description: 选项管理模块InterFace
*/

public interface OptionService {

    /* **
     * @Description: 查询全部选项
     * @Author: Zhouzhongqing
     * @Date: 2019/4/18 10:49
     * @Param: []
     * @Return: java.util.List
     * @Exception:
     */
    HashMap<String,Object> selectOptionPage(Option option);

    /* **
     * @Description: 增加选项
     * @Author: Zhouzhongqing
     * @Date: 2019/4/18 13:21
     * @Param: [option]
     * @Return: int
     * @Exception:
     */
    int saveOption(Option option);

    /* **
     * @Description: 根据ID删除选项 (单删)
     * @Author: Zhouzhongqing
     * @Date: 2019/4/18 14:24
     * @Param: [optionId]
     * @Return: int
     * @Exception:
     */
    int deleteByOptionId(Option optionId);

    /* **
     * @Description: 删除选项信息
     * @Author: Zhouzhongqing
     * @Date: 2019/4/18 18:44
     * @Param: [option]
     * @Return: int
     * @Exception:
     */
    int deleteOption(Integer optionId);

    /* **
     * @Description: 修改选项
     * @Author: Zhouzhongqing
     * @Date: 2019/4/18 17:41
     * @Param: [optionId]
     * @Return: int
     * @Exception:
     */
    int updateOption(Option option);

    /* **
     * @Description: 根据ID查询选项信息
     * @Author: Zhouzhongqing
     * @Date: 2019/4/18 18:00
     * @Param: [optionId]
     * @Return: com.san.platform.option.Option
     * @Exception:
     */
    Option selectByOptionId(Option option);

    /**
     * 查询所有事由
     * @return
     */
    List<Option> queryAllOption();

}
