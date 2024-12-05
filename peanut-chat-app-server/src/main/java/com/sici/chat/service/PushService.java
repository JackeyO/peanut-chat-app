package com.sici.chat.service;

import com.sici.chat.model.ws.bo.ImMsg;

import java.util.List;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.provider.ws.router.service
 * @author: 20148
 * @description:
 * @create-date: 9/17/2024 5:03 PM
 * @version: 1.0
 */

public interface PushService {
    /**
     * 消息路由到IM示例
     * @param imMsg
     */
    void pushMsg(ImMsg imMsg, Integer receiverId);
    void pushMsg(ImMsg imMsg, List<Integer> receiverIds);
}