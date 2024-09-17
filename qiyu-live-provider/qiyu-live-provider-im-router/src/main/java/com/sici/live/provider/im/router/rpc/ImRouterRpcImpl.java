package com.sici.live.provider.im.router.rpc;

import com.sici.common.log.annotation.EnableInvokeLog;
import com.sici.live.interfaces.im.router.rpc.ImRouterRpc;
import com.sici.live.interfaces.im.rpc.ImRouterHandlerRpc;
import com.sici.live.provider.im.router.service.ImRouterService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Component;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.provider.im.router.rpc
 * @author: 20148
 * @description:
 * @create-date: 9/17/2024 4:58 PM
 * @version: 1.0
 */

@DubboService(timeout = 60000, retries = 0)
@Component
@Slf4j
public class ImRouterRpcImpl implements ImRouterRpc {
    @Resource
    private ImRouterService imRouterService;
    @Override
    @EnableInvokeLog(description = "[ImRouterRpcImpl]==>调用ImRouterHandlerRpc")
    public void sendMsg(Long objectId, String imMsg) {
        imRouterService.sendMsg(objectId, imMsg);
    }
}

