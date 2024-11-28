package com.sici.chat.interfaces.im.router.rpc;

import com.sici.chat.model.im.bo.ImMsg;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;

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
     * 消息路由到IM示例
     * @param imMsg
     */
    void routeMsg(ImMsg imMsg, Integer receiverId);
    void routeMsg(ImMsg imMsg, List<Integer> receiverIds);
}