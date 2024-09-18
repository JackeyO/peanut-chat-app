package com.sici.live.im.core.server.service;

import com.sici.live.model.im.dto.ImMsgBody;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.im.core.server.service
 * @author: 20148
 * @description:
 * @create-date: 9/18/2024 5:10 PM
 * @version: 1.0
 */

public interface ImRouterHandlerService {
    /**
     * 接受router RPC 调用
     * @param userId
     * @param imMsgBody
     */
    void onReceive(ImMsgBody imMsgBody);
}
