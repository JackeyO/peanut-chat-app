package com.sici.chat.im.core.server.service;

import com.sici.chat.model.im.bo.ImMsg;

import java.util.List;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.im.core.server.service
 * @author: 20148
 * @description:
 * @create-date: 9/18/2024 5:10 PM
 * @version: 1.0
 */

public interface ImPushService {
    /**
     * 接受router RPC 调用
     * @param userId
     * @param imMsgBody
     */
    void pushMsg(ImMsg imMsg, List<Integer> receiverIds);
    void pushMsg(ImMsg imMsg, Integer receiveId);
}