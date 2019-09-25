package com.san.platform.task.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.san.platform.config.Global;
import com.san.platform.map.Map;
import com.san.platform.task.Task;
import com.san.platform.task.TaskResult;
import com.san.platform.task.TaskResultService;
import com.san.platform.task.mapper.TaskResultMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;

@Service
public class TaskResultServiceImpl implements TaskResultService {

    @Autowired
    private TaskResultMapper resultMapper;

    @Override
    public int createResult(TaskResult result) {
        return resultMapper.insertSelective(result);
    }

    @Override
    public HashMap<String, Object> queryByTaskGuid(Task task) {
        HashMap<String, Object> hashMap = new HashMap<>();
        // 当前页码 pageNum 与 每页显示条数 pageSize分页
        PageHelper.startPage(task.getPageNum(), Global.PAGESIZE);
        List<TaskResult> taskResults = resultMapper.queryByTaskGuid(task.getTaskGuid());
        // 得到page相关的info
        PageInfo pageInfo = new PageInfo(taskResults);
        // 将list与相关的page信息传入map到web端
        hashMap.put("pages",new Integer(pageInfo.getPages()));
        hashMap.put("total",pageInfo.getTotal());
        hashMap.put("results",taskResults);
        return hashMap;
    }
}
