package com.sici.framework.redis.batch.string;

/**
 * @author jackey
 * @description
 * @date 5/28/25 16:05
 */
public interface ValueOperation<IN, V> {
    void set(IN key, V value);
}
