package com.sici.live.provider.im.service.impl;

import com.mchange.v1.cachedstore.CachedStoreError;
import com.mysql.cj.util.TimeUtil;
import com.sici.common.result.ResponseResult;
import com.sici.framework.redis.CacheService;
import com.sici.live.provider.im.redis.key.ImProviderCacheKeyBuilder;
import com.sici.live.provider.im.service.ImTokenService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.provider.im.service.impl
 * @author: 20148
 * @description:
 * @create-date: 9/16/2024 5:10 PM
 * @version: 1.0
 */

@Service
@Slf4j
public class ImTokenServiceImpl implements ImTokenService {
    @Resource
    private CacheService cacheService;
    @Resource
    private ImProviderCacheKeyBuilder imProviderCacheKeyBuilder;
    @Override
    public ResponseResult<String> createImLoginToken(long userId, int appId) {
        String token = UUID.randomUUID() + ":" + appId;
        cacheService.setEx(imProviderCacheKeyBuilder.buildImLoginTokenKey(token), String.valueOf(userId), 5, TimeUnit.MINUTES);
        return ResponseResult.okResult(token);
    }

    @Override
    public ResponseResult<Long> getUserIdByToken(String token) {
        String userId = cacheService.get(imProviderCacheKeyBuilder.buildImLoginTokenKey(token));
        return ResponseResult.okResult(userId == null ? null : Long.valueOf(userId));
    }
}
