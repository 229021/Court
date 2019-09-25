package com.san.platform.task.mapper;

import com.san.platform.task.Task;
import com.san.platform.track.Track;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;

public interface TaskMapper extends Mapper<Task> {
    /**
     * 分页查询所有任务信息
     * @param task
     * @return
     */
    public List<Task> queryAllTask(Task task);

    /**
     * 更新任务结果数据
     * @param task
     * @return
     */
    public int updateTaskInfo (Task task);

    /**
     * 根据id查询
     * @param task
     * @return
     */
    public Task queryTaskById(Task task);

    /**
     * 根据人员库类型查询任务
     * @param typeId
     * @return
     */
    public List<Task> queryTaskByType(Integer typeId);

    /**
     * 根据guid查询任务信息
     * @param taskGuid
     * @return
     */
    public Task queryTaskByGuid(String taskGuid);
}
