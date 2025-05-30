package com.sici.chat.cache.user;

import com.sici.chat.builder.cache.user.UserBaseInfoCacheKeyBuilder;
import com.sici.chat.dao.UserDao;
import com.sici.chat.model.user.entity.User;
import com.sici.framework.redis.batch.string.AbstractRedisStringCache;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author jackey
 * @description
 * @date 5/29/25 20:21
 */
@Component
public class UserBaseInfoCache extends AbstractRedisStringCache<Long, User> {
    @Resource
    private UserBaseInfoCacheKeyBuilder userBaseInfoCacheKeyBuilder;
    @Resource
    private UserDao userDao;

    @Override
    public String getKey(Long req) {
        return userBaseInfoCacheKeyBuilder.build(req);
    }

    @Override
    public long getExpireSeconds() {
        return userBaseInfoCacheKeyBuilder.getExpireTime().getSeconds();
    }

    @Override
    public Map<Long, User> loadFromDb(List<Long> req) {
        return req.stream()
                .map(userId -> userDao.getById(userId))
                .collect(Collectors.toMap(User::getId, user -> user));
    }
}
