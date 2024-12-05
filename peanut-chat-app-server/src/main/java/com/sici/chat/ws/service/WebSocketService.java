package com.sici.chat.ws.service;

import com.sici.chat.model.ws.bo.ImMsg;
import io.netty.channel.Channel;

import java.util.List;

/**
 * @projectName: peanut-chat-app
 * @package: com.sici.chat.ws.service
 * @author: 20148
 * @description: ws相关接口
 * @create-date: 12/4/2024 11:28 AM
 * @version: 1.0
 */

public interface WebSocketService {
    void sendMsg(ImMsg imMsg, Integer receiverId);
    void sendMsg(ImMsg imMsg, List<Integer> receiverId);

    /**
     * 用户下线
     * @param channel
     */
    void offline(Channel channel);

}