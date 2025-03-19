package com.sici.chat.service.ws;

import java.util.List;

import com.sici.chat.model.ws.bo.ImMsg;

/**
 * 消息推送服务
 */
public interface PushService {
    /**
     * 推送消息给单个接收者
     *
     * @param imMsg      消息内容
     * @param receiverId 接收者ID
     */
    void pushMsg(ImMsg imMsg, Integer receiverId);

    /**
     * 推送消息给多个接收者
     *
     * @param imMsg       消息内容
     * @param receiverIds 接收者ID列表
     */
    void pushMsg(ImMsg imMsg, List<Integer> receiverIds);
} 