package com.sici.chat.provider.im.router.service;

import com.sici.chat.model.im.dto.ImMsgBody;

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
     * 调用IM RPC发消息
     * @param objectId
     * @param imMsg
     */
    void sendMsg(ImMsgBody imMsg);
}
