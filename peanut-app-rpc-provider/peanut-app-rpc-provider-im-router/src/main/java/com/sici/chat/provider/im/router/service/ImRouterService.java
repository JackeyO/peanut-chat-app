package com.sici.chat.provider.im.router.service;

import com.sici.chat.model.im.bo.ImMsg;
import com.sici.chat.model.im.dto.ImMsgBody;

import java.util.List;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.provider.im.router.service
 * @author: 20148
 * @description:
 * @create-date: 9/17/2024 5:03 PM
 * @version: 1.0
 */

public interface ImRouterService {
    /**
     * 消息路由到IM示例
     * @param imMsg
     */
    void routeMsg(ImMsg imMsg, Integer receiverId);
    void routeMsg(ImMsg imMsg, List<Integer> receiverIds);
}
