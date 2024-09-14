package com.sici.live.provider.user.redis.key;

import com.sici.framework.redis.key.RedisKeyBuilder;
import com.sici.framework.redis.key.RedisKeyLoadMatch;
import com.sici.live.model.user.pojo.UserPO;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@Configurable
@Component
@Conditional(RedisKeyLoadMatch.class)
public class UserProviderCacheKeyBuilder extends RedisKeyBuilder {
    private static String USER_INFO_KEY = "userInfo";

    public String buildUserInfoKey(Long userId) {
        return super.getPrefix() + USER_INFO_KEY +
                super.getSplitItem() + userId;
    }
    public List<String> buildUserInfoKey(List<Long> userId) {
        List<String> keys = new ArrayList<>();
        userId.forEach(id -> keys.add(buildUserInfoKey(id)));
        return keys;
    }
}