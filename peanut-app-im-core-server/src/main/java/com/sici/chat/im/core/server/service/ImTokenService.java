package com.sici.chat.im.core.server.service;

import com.sici.common.result.ResponseResult;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.provider.im.service
 * @author: 20148
 * @description:
 * @create-date: 9/16/2024 5:10 PM
 * @version: 1.0
 */

public interface ImTokenService {
    /**
     * 创建token
     * @param userId
     * @param appId
     * @return
     */
    ResponseResult<String> createImLoginToken(long userId, int appId);

    /**
     * 根据token获取userId
     * @param token
     * @return
     */
    ResponseResult<Long> getUserIdByToken(String token);
}
