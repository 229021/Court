package com.san.platform.option.mapper;

import com.san.platform.option.Option;

import org.apache.ibatis.annotations.Select;
import org.apache.zookeeper.Op;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.HashMap;
import java.util.List;

/**
 * @Author: Zhouzhongqing
 * @Date: 2019/4/18 11:27
 * @Description: 选项模块mapper接口
*/

@Repository
public interface OptionMapper extends Mapper<Option> {

    /* **
     * @Description: 分页查询显示所有
     * @Author: Zhouzhongqing
     * @Date: 2019/4/20 18:07
     * @Param: [map]
     * @Return: java.util.List<com.san.platform.option.Option>
     * @Exception:
     */
    List<Option> selectOptionPage(Option option);


}
