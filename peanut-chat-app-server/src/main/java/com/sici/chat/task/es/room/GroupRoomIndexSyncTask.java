package com.sici.chat.task.es.room;

import com.sici.chat.es.sync.IndexSyncService;
import com.sici.chat.es.sync.room.GroupRoomIndexSyncService;
import com.sici.chat.task.es.AbstractIndexSyncTask;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * @author jackey
 * @description
 * @date 5/30/25 11:31
 */
@Component
public class GroupRoomIndexSyncTask extends AbstractIndexSyncTask {
    @Resource
    private GroupRoomIndexSyncService groupRoomIndexSyncService;
    @Override
    public IndexSyncService getIndexSyncService() {
        return groupRoomIndexSyncService;
    }

    @Override
    public String getIndexSyncTaskName() {
        return "群聊房间索引同步任务";
    }
}
