package com.sici.chat.im.core.server.service;


import com.sici.chat.model.im.bo.ImMsg;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.im.core.server.service
 * @author: 20148
 * @description: Im Ack
 * @create-date: 9/19/2024 2:34 PM
 * @version: 1.0
 */

public interface ImMsgAckService {
    void doMsgAck(ImMsg imMsg);

    void recordMsgAck(ImMsg imMsg);

    Integer getMsgAckTimes(ImMsg imMsg);

    void sendDelayMessage(ImMsg imMsg, int delay);
}
