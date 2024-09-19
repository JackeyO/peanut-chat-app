package com.sici.live.interfaces.im.rpc;

import com.sici.common.result.ResponseResult;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.interfaces.im.rpc
 * @author: 20148
 * @description: IM判断用户是否在线
 * @create-date: 9/19/2024 2:16 PM
 * @version: 1.0
 */

@DubboService(timeout = 60000, retries = 0)
public interface ImOnlineRpc {
    /**
     * 判断用户是否在线
     * @param userId
     * @param appId
     * @return
     */
    ResponseResult<Boolean> isOnline(Long userId, Integer appId);
}