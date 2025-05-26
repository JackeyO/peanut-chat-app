package com.sici.framework.redis.batch.zset;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author jackey
 * @description ZSet cache
 * @date 5/26/25 11:01
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RedisZSetCacheInfo<T> {
    private List<T> data;
}
