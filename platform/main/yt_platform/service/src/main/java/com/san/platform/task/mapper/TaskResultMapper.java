package com.san.platform.task.mapper;

import com.san.platform.task.Task;
import com.san.platform.task.TaskResult;
import tk.mybatis.mapper.common.Mapper;

import java.util.HashMap;
import java.util.List;

/**
 * 名单下发结果
 */
public interface TaskResultMapper extends Mapper<TaskResult> {

    /**
     * 根据名单下发任务查询 结果信息
     * @param taskGuid
     * @return
     */
    public List<TaskResult> queryByTaskGuid(String taskGuid);
}
