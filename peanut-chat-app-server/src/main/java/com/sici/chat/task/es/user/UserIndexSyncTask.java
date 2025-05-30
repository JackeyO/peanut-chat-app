package com.sici.chat.task.es.user;

import com.sici.chat.es.sync.IndexSyncService;
import com.sici.chat.es.sync.user.UserIndexSyncService;
import com.sici.chat.task.es.AbstractIndexSyncTask;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * @author jackey
 * @description
 * @date 5/30/25 11:31
 */
@Component
public class UserIndexSyncTask extends AbstractIndexSyncTask {
    @Resource
    private UserIndexSyncService userIndexSyncService;
    @Override
    public IndexSyncService getIndexSyncService() {
        return userIndexSyncService;
    }

    @Override
    public String getIndexSyncTaskName() {
        return "用户索引同步任务";
    }
}
