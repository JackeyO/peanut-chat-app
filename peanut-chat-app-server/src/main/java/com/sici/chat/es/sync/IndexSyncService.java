package com.sici.chat.es.sync;

import java.util.Date;
import java.util.concurrent.CompletableFuture;

/**
 * @author jackey
 * @description
 * @date 5/28/25 22:12
 */
public interface IndexSyncService {
    CompletableFuture<Boolean> fullSync();
    CompletableFuture<Boolean> incrementSync(Date lastSyncTime);
}
