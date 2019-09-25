package com.san.platform.task.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.san.platform.alert.PersonType;
import com.san.platform.alert.mapper.PersonTypeMapper;
import com.san.platform.config.Global;
import com.san.platform.task.Task;
import com.san.platform.task.TaskService;
import com.san.platform.task.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.HashMap;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private PersonTypeMapper personTypeMapper;

    @Override
    public HashMap<String, Object> queryAllTask(Task task) {
        HashMap<String, Object> hashMap = new HashMap<>();
        // 当前页码 pageNum 与 每页显示条数 pageSize分页
        PageHelper.startPage(task.getPageNum(), Global.PAGESIZE);
        List<Task> taskList = taskMapper.queryAllTask(task);
        // 得到page相关的info
        PageInfo pageInfo = new PageInfo(taskList);
        // 将list与相关的page信息传入map到web端
        hashMap.put("pages",new Integer(pageInfo.getPages()));
        hashMap.put("total",pageInfo.getTotal());
        hashMap.put("results",taskList);
        return hashMap;
    }

    @Override
    public int createTask(Task task) {
        return taskMapper.insertSelective(task);
    }

    @Override
    public int removeTask(Task task) {
        return taskMapper.deleteByPrimaryKey(task);
    }

    @Override
    public List<PersonType> selectPersonType() {
        return personTypeMapper.selectAll();
    }

    /**
     * 更新任务结果数据
     * @param task
     * @return
     */
    @Override
    public int updateTaskInfo (Task task){
        return taskMapper.updateTaskInfo(task);
    }

    /**
     * 根据id查询
     * @param task
     * @return
     */
    @Override
    public Task queryTaskById(Task task){
        return taskMapper.queryTaskById(task);
    }

    /**
     * 根据人员库类型查询任务
     * @param typeId
     * @return
     */
    @Override
    public List<Task> queryTaskByType(Integer typeId){
        return taskMapper.queryTaskByType(typeId);
    }

    /**
     * 根据任务guid查询任务信息
     * @param taskGuid
     * @return
     */
    @Override
    public Task queryTaskByGuid(String taskGuid){
        return taskMapper.queryTaskByGuid(taskGuid);
    }

    @Override
    public void updateTaskStatus(Task task){
        taskMapper.updateByPrimaryKeySelective(task);
    }
}
