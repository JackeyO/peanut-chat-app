package com.sici.framework.redis.batch.string;

import cn.hutool.core.collection.CollectionUtil;
import com.sici.framework.redis.RedisUtils;
import com.sici.framework.redis.batch.AbstractRedisCache;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.framework.utilsredis.batch
 * @author: 20148
 * @description: Redis value 为String类型的批量缓存
 * @create-date: 11/21/2024 4:26 PM
 * @version: 1.0
 */

public abstract class AbstractRedisStringCache<IN, OUT> extends AbstractRedisCache<IN, OUT> {
    public AbstractRedisStringCache () {
    }
    /**
     * 由子类各种应用类型缓存来决定key的生成规则
     * @param req
     * @return
     */
    public abstract String getKey(IN req);

    /**
     * 由子类决定各类key的存活时间
     * @return
     */
    public abstract long getExpireSeconds();

    /**
     * 从数据库里加载数据(同样，由子类决定加载规则)
     * @param req
     * @return
     */
    public abstract Map<IN, OUT> loadFromDb(List<IN> req);

    public OUT getFromCache(IN req) {
        return RedisUtils.get(getKey(req), super.outClass);
    }
    public List<OUT> getFromCache(List<IN> req) {
        List<String> keys = req.stream()
                .map(this::getKey)
                .collect(Collectors.toList());
        return RedisUtils.mget(keys, super.outClass);
    }
    public void setToCache(Map<String, OUT> toSet) {
        if (CollectionUtil.isEmpty(toSet)) return;
        RedisUtils.mset(toSet, getExpireSeconds());
    }
}