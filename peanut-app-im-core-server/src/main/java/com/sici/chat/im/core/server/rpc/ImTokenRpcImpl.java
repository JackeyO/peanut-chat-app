package com.sici.chat.im.core.server.rpc;

import com.sici.common.log.annotation.EnableInvokeLog;
import com.sici.common.result.ResponseResult;
import com.sici.chat.im.core.server.service.ImTokenService;
import com.sici.chat.interfaces.im.rpc.ImTokenRpc;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

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
    @EnableInvokeLog(description = "[IM RPC]==>[创建IM登录token]")
    public ResponseResult<String> createImLoginToken(long userId, int appId) {
        return imTokenService.createImLoginToken(userId, appId);
    }

    @Override
    @EnableInvokeLog(description = "[IM RPC]==>[根据token获取用户ID]")
    public ResponseResult<Long> getUserIdByToken(String token) {
        return imTokenService.getUserIdByToken(token);
    }
}

