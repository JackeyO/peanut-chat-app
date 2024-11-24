package com.sici.chat.interfaces.im.rpc;

import com.sici.common.result.ResponseResult;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.interfaces.im.rpc
 * @author: 20148
 * @description:
 * @create-date: 9/16/2024 5:07 PM
 * @version: 1.0
 */

@DubboService(timeout = 60000, retries = 0)
public interface ImTokenRpc {
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
