package com.sici.live.interfaces.im.router.rpc;

import org.apache.dubbo.config.annotation.DubboService;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.interfaces.im.router.rpc
 * @author: 20148
 * @description:
 * @create-date: 9/17/2024 4:56 PM
 * @version: 1.0
 */

@DubboService(timeout = 60000, retries = 0)
public interface ImRouterRpc {
    /**
     * 向im发送消息
     * @param objectId
     * @param imMsg
     */
    void sendMsg(Long objectId, String imMsg);
}
