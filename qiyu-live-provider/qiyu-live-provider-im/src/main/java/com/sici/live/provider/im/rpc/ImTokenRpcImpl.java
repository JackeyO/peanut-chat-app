package com.sici.live.provider.im.rpc;

import com.sici.common.result.ResponseResult;
import com.sici.live.interfaces.im.rpc.ImTokenRpc;
import com.sici.live.provider.im.service.ImTokenService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Component;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.provider.im.rpc
 * @author: 20148
 * @description:
 * @create-date: 9/16/2024 5:09 PM
 * @version: 1.0
 */

@Component
@DubboService(timeout = 60000, retries = 0)
@Slf4j
public class ImTokenRpcImpl implements ImTokenRpc {
    @Resource
    private ImTokenService imTokenService;
    @Override
    public ResponseResult<String> createImLoginToken(long userId, int appId) {
        return imTokenService.createImLoginToken(userId, appId);
    }

    @Override
    public ResponseResult<Long> getUserIdByToken(String token) {
        return imTokenService.getUserIdByToken(token);
    }
}

