package com.sici.framework.redis.batch;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Pair;
import com.sici.common.constant.common.CacheConstant;
import com.sici.framework.redis.RedisUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.framework.utilsredis.batch
 * @author: 20148
 * @description: Redis string Key类型批量缓存
 * @create-date: 11/21/2024 4:26 PM
 * @version: 1.0
 */

public abstract class AbstractRedisCache<IN, OUT> implements BatchCache<IN, OUT>, RefreshableCache<IN> {
    protected Class<OUT> outClass;

    public AbstractRedisCache() {
        ParameterizedType superclass = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.outClass = (Class<OUT>) superclass.getActualTypeArguments()[1];
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

    /**
     * 抽象缓存操作，具体取决于缓存数据类型
     * @param req
     * @return
     */
    public abstract OUT getFromCache(IN req);
    public abstract List<OUT> getFromCache(List<IN> req);
    public abstract void setToCache(Map<String, OUT> toSet);

    public Map<IN, OUT> loadFromDbAndSetToCache(List<IN> req) {
        if (CollectionUtils.isEmpty(req)) return Map.of();
        Map<IN, OUT> fromDbResults = loadFromDb(req);
        // 保存到缓存中
        Map<String, OUT> toSet = fromDbResults.entrySet().stream()
                .map(a -> Pair.of(getKey(a.getKey()), a.getValue()))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));

        setToCache(toSet);
        return fromDbResults;
    }

    /**
     * 删除缓存
     * @param req
     */
    public void deleteFromCache(IN req) {
        RedisUtils.del(getKey(req));
    }
    /**
     * 批量删除缓存
     * @param req
     */
    public void deleteFromCache(List<IN> req) {
        if (CollectionUtil.isEmpty(req)) return ;
        RedisUtils.del(req.stream().map(this::getKey).collect(Collectors.toList()));
    }
    public Boolean existsInCache(IN req) {
        return RedisUtils.hasKey(getKey(req));
    }

    @Override
    public OUT getOne(IN req) {
        return getBatch(List.of(req)).get(req);
    }
    @Override
    public Map<IN, OUT> getBatch(List<IN> req) {
        if (CollectionUtil.isEmpty(req)) return null;
        List<OUT> results = getFromCache(req);

        Map<IN, OUT> finalResults = new HashMap<>(req.size());
        List<IN> needLoadIn = new ArrayList<>();
        for (int i = 0; i < req.size(); i ++) {
            if (results.get(i) == null) {
                needLoadIn.add(req.get(i));
            } else {
                finalResults.put(req.get(i), results.get(i));
            }
        }

        // 缓存中没有的从数据库load
        if (!CollectionUtil.isEmpty(needLoadIn)) {
            Map<IN, OUT> fromDbResults = loadFromDbAndSetToCache(needLoadIn);
            finalResults.putAll(fromDbResults);
        }

        return finalResults;
    }

    @Override
    public void refresh(IN req) {
        deleteFromCache(req);
        // 延时二次删除
        Executor delayedExecutor = CompletableFuture.delayedExecutor(CacheConstant.SECOND_DELETE_INTERVAL, TimeUnit.MILLISECONDS);
        CompletableFuture.runAsync(() -> deleteFromCache(req), delayedExecutor);
    }

    @Override
    public void refresh(List<IN> req) {
        deleteFromCache(req);
        // 延时二次删除
        Executor delayedExecutor = CompletableFuture.delayedExecutor(CacheConstant.SECOND_DELETE_INTERVAL, TimeUnit.MILLISECONDS);
        CompletableFuture.runAsync(() -> deleteFromCache(req), delayedExecutor);
    }
}