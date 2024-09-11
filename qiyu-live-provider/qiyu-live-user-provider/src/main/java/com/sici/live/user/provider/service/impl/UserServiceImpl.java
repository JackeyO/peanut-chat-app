package com.sici.live.user.provider.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sici.common.constant.user.UserProviderConstant;
import com.sici.framework.redis.CacheService;
import com.sici.framework.redis.key.RedisKeyEntry;
import com.sici.live.model.user.pojo.UserPO;
import com.sici.live.user.provider.mapper.UserMapper;
import com.sici.live.user.provider.redis.key.UserProviderCacheKeyBuilder;
import com.sici.live.user.provider.service.UserService;
import com.sici.redis.RedisExpireTimeUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.user.provider.service.impl
 * @author: 20148
 * @description:
 * @create-date: 9/10/2024 6:43 PM
 * @version: 1.0
 */

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserPO> implements UserService {
    @Resource
    private CacheService cacheService;
    @Resource
    private UserProviderCacheKeyBuilder userProviderCacheKeyBuilder;
    @Resource
    private UserMapper userMapper;

    @Override
    public UserPO getUser(Long userId) {
        if (userId == null) {
            return null;
        }
        String user = cacheService.get(userProviderCacheKeyBuilder.buildUserInfoKey(userId));
        UserPO userPO = null;
        if (!StringUtils.isEmpty(user)) {
            userPO = JSON.parseObject(user, UserPO.class);
        }
        if (userPO == null) {
            userPO = getById(userId);
            // 存入缓存
            if (userPO != null) {
                cacheService.setEx(userProviderCacheKeyBuilder.buildUserInfoKey(userId),
                        JSON.toJSONString(userPO), UserProviderConstant.USER_INFO_CACHE_EXPIRE, TimeUnit.MINUTES);
            }
        }
        return userPO;
    }

    @Override
    public List<UserPO> getUsers(List<Long> userBatchIds) {
        List<UserPO> userPOS = new ArrayList<>();
        if (CollectionUtils.isEmpty(userBatchIds)) {
            return userPOS;
        }

        // 获取key
        List<String> keys = userProviderCacheKeyBuilder.buildUserInfoKey(userBatchIds);

        // 从redis中查询数据
        Map<Long, Boolean> inRedis = new HashMap<>();
        List<String> users = cacheService.multiGet(keys);
        users.stream()
                .filter(user -> !StringUtils.isEmpty(user))
                .map(user -> JSON.parseObject(user, UserPO.class))
                .forEach(userPO -> {
                    userPOS.add(userPO);
                    inRedis.put(userPO.getUserId(), true);
                });

        // 将没有从redis中查到的userId先分组再从数据库中查询
        Map<Long, List<Long>> notInRedisGroupedIds = userBatchIds.stream()
                .parallel()
                .filter(userId -> !inRedis.containsKey(userId))
                .collect(Collectors.groupingBy(userId -> userId % UserProviderConstant.USER_TABLE_KEY_MOD));

        notInRedisGroupedIds.values()
                .stream()
                .forEach(userIds -> {
                    List<UserPO> userPOSTmp = userMapper.selectBatchIds(userIds)
                                    .stream().filter(userPO -> userPO != null).collect(Collectors.toList());
                    userPOS.addAll(userPOSTmp);

                    Map<RedisKeyEntry, String> userPosMap = userPOSTmp.stream()
                            .collect(Collectors.toMap(userPO ->
                                            RedisKeyEntry.builder()
                                                    .value(userProviderCacheKeyBuilder.buildUserInfoKey(userPO.getUserId()))
                                                    .expire(RedisExpireTimeUtil.createRandomExpireTime())
                                                    .timeUnit(TimeUnit.MINUTES).build()
                                    ,
                                    userPO -> JSON.toJSONString(userPO)));
                    // 保存到redis
                    cacheService.multiSetByPipeLine(userPosMap);
                });

        return userPOS;
    }
}
