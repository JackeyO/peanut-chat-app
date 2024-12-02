package com.sici.framework.redis.batch;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Pair;
import com.sici.framework.redis.RedisUtils;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.framework.utilsredis.batch
 * @author: 20148
 * @description: Redis string Key类型批量缓存
 * @create-date: 11/21/2024 4:26 PM
 * @version: 1.0
 */

public abstract class AbstractRedisStringCache<IN, OUT> implements BatchCache<IN, OUT> {
    private Class<OUT> outClass;

    public AbstractRedisStringCache () {
        ParameterizedType superclass = (ParameterizedType) this.getClass().getGenericSuperclass();
        outClass = (Class<OUT>) superclass.getActualTypeArguments()[1];
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

    @Override
    public OUT getOne(IN req) {
        return RedisUtils.get(getKey(req), outClass);
    }

    @Override
    public Map<IN, OUT> getBatch(List<IN> req) {
        if (CollectionUtil.isEmpty(req)) return null;
        List<String> keys = req.stream().map(this::getKey).collect(Collectors.toList());
        List<OUT> results = RedisUtils.mget(keys, outClass);

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
            Map<IN, OUT> fromDbResults = loadFromDb(needLoadIn);
            finalResults.putAll(fromDbResults);

            // 保存到缓存中
            Map<String, OUT> toSet = fromDbResults.entrySet().stream()
                    .map(a -> Pair.of(getKey(a.getKey()), a.getValue()))
                    .collect(Collectors.toMap(Pair::getKey, Pair::getValue));

            RedisUtils.mset(toSet, getExpireSeconds());
        }

        return finalResults;
    }

    @Override
    public void deleteOne(IN req) {
        RedisUtils.del(getKey(req));
    }

    @Override
    public void deleteBatch(List<IN> req) {
        if (CollectionUtil.isEmpty(req)) return ;
        RedisUtils.del(req.stream().map(this::getKey).collect(Collectors.toList()));
    }
}