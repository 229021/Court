package com.san.platform.innerlog.mapper;

import com.san.platform.innerlog.Innerlog;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface InnerlogMapper extends Mapper<Innerlog> {
    /**
     * 查询列表信息根据起始结束时间
     */
    List<Innerlog> queryInnerlogByTime(@Param("startTime")String startTime,@Param("endTime")String endTime);
}
