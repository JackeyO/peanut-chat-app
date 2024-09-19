package com.sici.live.provider.im.service;

import com.sici.common.result.ResponseResult;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.provider.im.service
 * @author: 20148
 * @description:
 * @create-date: 9/19/2024 2:18 PM
 * @version: 1.0
 */

public interface ImOnlineService {
    ResponseResult<Boolean> isOnline(Long userId, Integer appId);
}
