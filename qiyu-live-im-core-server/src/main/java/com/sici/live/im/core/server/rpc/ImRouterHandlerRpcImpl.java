package com.sici.live.im.core.server.rpc;

import com.sici.live.interfaces.im.rpc.ImRouterHandlerRpc;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Component;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.im.core.server.rpc
 * @author: 20148
 * @description:
 * @create-date: 9/17/2024 4:55 PM
 * @version: 1.0
 */

@DubboService(timeout = 60000, retries = 0)
@Component
@Slf4j
public class ImRouterHandlerRpcImpl implements ImRouterHandlerRpc {
    @Override
    public void sendMsg(Long objectId, String imMsg) {
        log.info("this is im core server, receive msg:{}, to:{}", imMsg, objectId);
    }
}
