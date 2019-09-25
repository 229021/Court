package com.san.platform.option.service.impl;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.san.platform.config.Global;
import com.san.platform.option.Option;
import com.san.platform.option.mapper.OptionMapper;
import com.san.platform.option.OptionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Author: Zhouzhongqing
 * @Date: 2019/4/18 11:22
 * @Description: 实现类
*/

@Service
public class OptionServiceImpl implements OptionService {

    @Autowired
    private OptionMapper optionMapper;

    /* **
     * @Description: 实现查询全部service
     * @Author: Zhouzhongqing
     * @Date: 2019/4/18 11:16
     * @Param: []
     * @Return: java.util.List<com.san.platform.option.Option>
     * @Exception:
     */
    @Override
    public HashMap<String,Object> selectOptionPage(Option option){
        HashMap<String,Object> map = new HashMap<>();
        // 当前页码 pageNum 与 每页显示条数  pageSize分页
        PageHelper.startPage(option.getPageNum(),Global.PAGESIZE);
        // 分页查询
        List<Option> options = optionMapper.selectOptionPage(option);
        // 得到page相关的info
        PageInfo pageInfo = new PageInfo(options);
        // 将List 与相关的page信息传入map到web端
        map.put("pages",new Integer(pageInfo.getPages()));
        map.put("total",pageInfo.getTotal());
        map.put("results",options);
        return map;
    }

    /* **
     * @Description: 实现增加选项
     * @Author: Zhouzhongqing
     * @Date: 2019/4/18 13:29
     * @Param: [option]
     * @Return: int
     * @Exception:
     */
    @Override
    public int saveOption(Option option) {
        return optionMapper.insertSelective(option);
    }


    /* **
     * @Description:实现根据ID删除选项
     * @Author: Zhouzhongqing
     * @Date: 2019/4/18 14:27
     * @Param: [optionId]
     * @Return: int
     * @Exception:
     */
    @Override
    public int deleteByOptionId(Option optionId) {
        return optionMapper.deleteByPrimaryKey(optionId);
    }

    /* **
     * @Description: 删除选项
     * @Author: Zhouzhongqing
     * @Date: 2019/4/19 9:38
     * @Param: [optionId]
     * @Return: int
     * @Exception:
     */
    @Override
    public int deleteOption(Integer optionId) {
        return optionMapper.deleteByPrimaryKey(optionId);
    }

    /* **
     * @Description: 修改选项
     * @Author: Zhouzhongqing
     * @Date: 2019/4/18 17:43
     * @Param: [optionId]
     * @Return: int
     * @Exception:
     */
    @Override
    public int updateOption(Option option) {
        return optionMapper.updateByPrimaryKeySelective(option);
    }

    /* **
     * @Description: 根据ID查询选项信息
     * @Author: Zhouzhongqing
     * @Date: 2019/4/18 18:01
     * @Param: [optionId]
     * @Return: com.san.platform.option.Option
     * @Exception:
     */
    @Override
    public Option selectByOptionId(Option option) {
//        return optionMapper.selectByPrimaryKey(option);
        return optionMapper.selectOne(option);
    }

    @Override
    public List<Option> queryAllOption() {
        return optionMapper.selectAll();
    }


}
