package com.sici.framework.redis.batch;

import java.util.List;

/**
 * @author jackey
 * @description 刷新缓存
 * @date 5/25/25 14:16
 */
public interface RefreshableCache<IN> {
    void refresh(IN req);
    void refresh(List<IN> req);
}