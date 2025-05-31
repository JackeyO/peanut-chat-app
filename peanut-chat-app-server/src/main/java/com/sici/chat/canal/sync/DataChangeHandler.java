package com.sici.chat.canal.sync;

import com.sici.chat.model.canal.event.DataChangeEvent;

/**
 * @author jackey
 * @description
 * @date 5/29/25 17:43
 */
public interface DataChangeHandler {
    String getSyncType();
    String getDatabase();
    String getTable();
    void handleDataChangeEvent(DataChangeEvent dataChangeEvent);
}
