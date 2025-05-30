package com.sici.chat.task.es;

import java.util.Date;

/**
 * @author jackey
 * @description
 * @date 5/30/25 11:17
 */
public interface IndexSyncTask {
    void fullSync();
    void incrementSync();
}
