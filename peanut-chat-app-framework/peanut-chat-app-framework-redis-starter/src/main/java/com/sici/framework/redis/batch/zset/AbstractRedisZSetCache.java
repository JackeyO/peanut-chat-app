package com.sici.framework.redis.batch.zset;

import com.sici.framework.redis.RedisUtils;
import com.sici.framework.redis.batch.AbstractRedisCache;
import org.assertj.core.util.Lists;
import org.springframework.data.redis.connection.zset.Tuple;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.ZSetOperations;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: 20148
 * @description: Redis value为ZSet类型批量缓存
 * @create-date: 11/21/2024 4:26 PM
 */

public abstract class AbstractRedisZSetCache<IN, T, OUT extends RedisZSetCacheInfo<T>> extends AbstractRedisCache<IN, OUT> implements ZSetOperation<IN, T>{
    private Class<OUT> outClass;

    public AbstractRedisZSetCache() {
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
     * 获取zSet item 到Tuple的映射关系
     * @return
     */
    public abstract Function<T, Tuple> getMapping();

    /**
     * 获取zSet item的字符串到对象映射关系
     * @return
     */
    public abstract Function<String, T> getMappingFromString();
    public void extendPack(OUT out, List<T> result) {
        // 默认实现，子类可以覆盖
    }

    private OUT getOutInstance() {
        try {
            Constructor<OUT> constructor = outClass.getConstructor();
            return constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("Failed to create instance of " + outClass.getName(), e);
        }
    }

    @Override
    public OUT getFromCache(IN req) {
        // 从zSet中获取所有消息
        List<T> result = RedisUtils.zAll(getKey(req)).stream()
                .map(getMappingFromString())
                .collect(Collectors.toList());

        OUT out = getOutInstance();
        out.setData(result);
        extendPack(out, result);
        return out;
    }

    @Override
    public List<OUT> getFromCache(List<IN> req) {
        List<String> keys = req.stream().map(this::getKey).collect(Collectors.toList());

        List<OUT> cacheInfos = Lists.newArrayList();

        // 使用redis callback执行批量获取
        RedisUtils.executePipelined(connection -> {
            keys.forEach(key -> {
                List<T> result = connection.zRange(key.getBytes(), 0, -1)
                        .stream()
                        .map(bytes -> {
                            String itemString = new String(bytes);
                            T itemObject = getMappingFromString().apply(itemString);
                            return itemObject;
                        })
                        .collect(Collectors.toList());
                OUT out = getOutInstance();
                out.setData(result);
                extendPack(out, result);
                cacheInfos.add(out);
            });
            return null;
        });
        return cacheInfos;
    }

    // set zSet
    @Override
    public void setToCache(Map<String, OUT> toSet) {
        // 使用redis callback执行批量设置
        RedisUtils.executePipelined(connection -> {
            toSet.forEach((key, value) -> {
                Set<Tuple> itemsTupleSet = value.getData().stream()
                        .map(item -> getMapping().apply(item))
                        .collect(Collectors.toSet());
                connection.zAdd(key.getBytes(), itemsTupleSet);
            });
            return null;
        });
    }


    // ZSet operations

    @Override
    public Boolean add(IN req, T value) {
        if (!super.existsInCache(req)) {
            super.loadFromDbAndSetToCache(Lists.newArrayList(req));
        }
        Tuple tuple = getMapping().apply(value);
        return RedisUtils.zAdd(getKey(req), tuple.getValue(), tuple.getScore());
    }

    @Override
    public Boolean add(IN req, T value, double score) {
        if (!super.existsInCache(req)) {
            super.loadFromDbAndSetToCache(Lists.newArrayList(req));
        }
        return RedisUtils.zAdd(getKey(req), value, score);
    }

    @Override
    public Boolean addIfAbsent(IN req, T value, double score) {
        if (!super.existsInCache(req)) {
            super.loadFromDbAndSetToCache(Lists.newArrayList(req));
        }
        return RedisUtils.zAddIfAbsent(getKey(req), value, score);
    }

    @Override
    public Long remove(IN req, T... values) {
        if (!super.existsInCache(req)) {
            super.loadFromDbAndSetToCache(Lists.newArrayList(req));
        }
        return RedisUtils.zRemove(getKey(req), values);
    }

    @Override
    public Long size(IN req) {
        if (!super.existsInCache(req)) {
            super.loadFromDbAndSetToCache(Lists.newArrayList(req));
        }
        return RedisUtils.zSize(getKey(req));
    }

    @Override
    public Double score(IN req, T o) {
        if (!super.existsInCache(req)) {
            super.loadFromDbAndSetToCache(Lists.newArrayList(req));
        }
        return RedisUtils.zScore(getKey(req), o);
    }

    @Override
    public Set<T> range(IN req, long start, long end) {
        if (!super.existsInCache(req)) {
            super.loadFromDbAndSetToCache(Lists.newArrayList(req));
        }
        return RedisUtils.zRange(getKey(req), start, end).stream()
                .map(getMappingFromString())
                .collect(Collectors.toSet());
    }

    @Override
    public Set<ZSetOperations.TypedTuple<T>> rangeWithScores(IN req, long start, long end) {
        if (!super.existsInCache(req)) {
            super.loadFromDbAndSetToCache(Lists.newArrayList(req));
        }
        return RedisUtils.zRangeWithScores(getKey(req), start, end).stream()
                .map(tuple -> new DefaultTypedTuple<T>(getMappingFromString().apply(tuple.getValue()),
                        tuple.getScore()) {
                })
                .collect(Collectors.toSet());
    }

    @Override
    public Set<T> rangeByScore(IN req, double min, double max) {
        if (!super.existsInCache(req)) {
            super.loadFromDbAndSetToCache(Lists.newArrayList(req));
        }
        return RedisUtils.zRangeByScore(getKey(req), min, max).stream()
                .map(getMappingFromString())
                .collect(Collectors.toSet());
    }

    @Override
    public Set<ZSetOperations.TypedTuple<T>> rangeByScoreWithScores(IN req, double min, double max) {
        if (!super.existsInCache(req)) {
            super.loadFromDbAndSetToCache(Lists.newArrayList(req));
        }
        return RedisUtils.zRangeByScoreWithScores(getKey(req), min, max).stream()
                .map(tuple -> new DefaultTypedTuple<T>(getMappingFromString().apply(tuple.getValue()),
                        tuple.getScore()) {
                })
                .collect(Collectors.toSet());
    }

    @Override
    public Set<T> rangeByScore(IN req, double min, double max, long offset, long count) {
        if (!super.existsInCache(req)) {
            super.loadFromDbAndSetToCache(Lists.newArrayList(req));
        }
        return RedisUtils.zRangeByScore(getKey(req), min, max, offset, count).stream()
                .map(getMappingFromString())
                .collect(Collectors.toSet());
    }

    @Override
    public Set<ZSetOperations.TypedTuple<T>> rangeByScoreWithScores(IN req, double min, double max, long offset, long count) {
        if (!super.existsInCache(req)) {
            super.loadFromDbAndSetToCache(Lists.newArrayList(req));
        }
        return RedisUtils.zRangeByScoreWithScores(getKey(req), min, max, offset, count).stream()
                .map(tuple -> new DefaultTypedTuple<T>(getMappingFromString().apply(tuple.getValue()),
                        tuple.getScore()) {
                })
                .collect(Collectors.toSet());
    }

    @Override
    public Set<T> reverseRange(IN req, long start, long end) {
        if (!super.existsInCache(req)) {
            super.loadFromDbAndSetToCache(Lists.newArrayList(req));
        }
        return RedisUtils.zReverseRange(getKey(req), start, end).stream()
                .map(getMappingFromString())
                .collect(Collectors.toSet());
    }

    @Override
    public Set<ZSetOperations.TypedTuple<T>> reverseRangeWithScores(IN req, long start, long end) {
        if (!super.existsInCache(req)) {
            super.loadFromDbAndSetToCache(Lists.newArrayList(req));
        }
        return RedisUtils.zReverseRangeWithScores(getKey(req), start, end).stream()
                .map(tuple -> new DefaultTypedTuple<T>(getMappingFromString().apply(tuple.getValue()),
                        tuple.getScore()) {
                })
                .collect(Collectors.toSet());
    }

    @Override
    public Set<T> reverseRangeByScore(IN req, double min, double max) {
        if (!super.existsInCache(req)) {
            super.loadFromDbAndSetToCache(Lists.newArrayList(req));
        }
        return RedisUtils.zReverseRangeByScore(getKey(req), min, max).stream()
                .map(getMappingFromString())
                .collect(Collectors.toSet());
    }

    @Override
    public Set<ZSetOperations.TypedTuple<T>> reverseRangeByScoreWithScores(IN req, double min, double max) {
        if (!super.existsInCache(req)) {
            super.loadFromDbAndSetToCache(Lists.newArrayList(req));
        }
        return RedisUtils.zReverseRangeByScoreWithScores(getKey(req), min, max).stream()
                .map(tuple -> new DefaultTypedTuple<T>(getMappingFromString().apply(tuple.getValue()),
                        tuple.getScore()) {
                })
                .collect(Collectors.toSet());
    }

    @Override
    public Set<T> reverseRangeByScore(IN req, double min, double max, long offset, long count) {
        if (!super.existsInCache(req)) {
            super.loadFromDbAndSetToCache(Lists.newArrayList(req));
        }
        return RedisUtils.zReverseRangeByScore(getKey(req), min, max, offset, count).stream()
                .map(getMappingFromString())
                .collect(Collectors.toSet());
    }

    @Override
    public Set<ZSetOperations.TypedTuple<T>> reverseRangeByScoreWithScores(IN req, double min, double max, long offset, long count) {
        if (!super.existsInCache(req)) {
            super.loadFromDbAndSetToCache(Lists.newArrayList(req));
        }
        return RedisUtils.zReverseRangeByScoreWithScores(getKey(req), min, max, offset, count).stream()
                .map(tuple -> new DefaultTypedTuple<T>(getMappingFromString().apply(tuple.getValue()),
                        tuple.getScore()) {
                })
                .collect(Collectors.toSet());
    }

    @Override
    public Long count(IN req, double min, double max) {
        if (!super.existsInCache(req)) {
            super.loadFromDbAndSetToCache(Lists.newArrayList(req));
        }
        return RedisUtils.zCount(getKey(req), min, max);
    }
}