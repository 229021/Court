package com.san.platform.task;

import java.util.HashMap;

public interface TaskResultService {

    /**
     * 名单下发结果入库
     * @param result
     * @return
     */
    public int createResult (TaskResult result);


    /**
     * 根据名单下发任务查询 结果信息
     * @return
     */
    public HashMap<String, Object> queryByTaskGuid(Task task);

}
