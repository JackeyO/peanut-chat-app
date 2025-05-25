package com.sici.framework.redis.batch;

import java.util.List;
import java.util.Map;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.framework.utilsredis.batch
 * @author: 20148
 * @description: 批量缓存
 * @create-date: 11/21/2024 4:23 PM
 * @version: 1.0
 */

public interface BatchCache<IN, OUT> {
    /**
     * 获取一条缓存记录
     * @param req
     * @return
     */
    OUT getOne(IN req);

    /**
     * 获取多个缓存记录
     * @param req
     * @return
     */
    Map<IN, OUT> getBatch(List<IN> req);
}
