package com.san.platform.task;

import com.san.platform.alert.PersonType;

import java.util.HashMap;
import java.util.List;

public interface TaskService {

    /**
     * 查询所有名单任务
     * @return
     */
    public HashMap<String, Object> queryAllTask(Task task);

    /**
     * 添加名单任务
     * @param task
     * @return
     */
    public int createTask (Task task);

    /**
     * 删除 名单任务
     * @param task
     * @return
     */
    public int removeTask (Task task);

    public List<PersonType> selectPersonType();

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
     * 根据guid查询task信息
     * @param taskGuid
     * @return
     */
    public Task queryTaskByGuid(String taskGuid);

    /**
     * 更新任务状态信息
     * @param task
     */
    public void updateTaskStatus(Task task);
}
