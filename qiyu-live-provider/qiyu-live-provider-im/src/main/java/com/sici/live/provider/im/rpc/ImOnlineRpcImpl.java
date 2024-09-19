package com.sici.live.provider.im.rpc;

import com.sici.common.result.ResponseResult;
import com.sici.live.interfaces.im.rpc.ImOnlineRpc;
import com.sici.live.provider.im.service.ImOnlineService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Component;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.provider.im.rpc
 * @author: 20148
 * @description: IM判断用户在线
 * @create-date: 9/19/2024 2:17 PM
 * @version: 1.0
 */

@DubboService(timeout = 60000, retries = 0)
@Component
@Slf4j
public class ImOnlineRpcImpl implements ImOnlineRpc {
    @Resource
    private ImOnlineService imOnlineService;

    @Override
    public ResponseResult<Boolean> isOnline(Long userId, Integer appId) {
        return imOnlineService.isOnline(userId, appId);
    }
}

