package com.sici.live.provider.im.service.impl;

import com.sici.common.constant.im.ImConstant;
import com.sici.common.result.ResponseResult;
import com.sici.framework.redis.CacheService;
import com.sici.live.provider.im.service.ImOnlineService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.provider.im.service.impl
 * @author: 20148
 * @description:
 * @create-date: 9/19/2024 2:20 PM
 * @version: 1.0
 */

@Service
@Slf4j
public class ImOnlineServiceImpl implements ImOnlineService {
    @Resource
    private CacheService cacheService;
    @Override
    public ResponseResult<Boolean> isOnline(Long userId, Integer appId) {
        Boolean exists = cacheService.exists(ImConstant.IM_CORE_SERVER_ADDRESS_CACHE_KEY_PREFIX + appId + ":" + userId);
        return ResponseResult.okResult(exists);
    }
}