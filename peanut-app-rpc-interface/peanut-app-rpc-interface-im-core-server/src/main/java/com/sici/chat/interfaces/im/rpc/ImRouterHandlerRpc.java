package com.sici.chat.interfaces.im.rpc;

import com.sici.chat.model.im.dto.ImMsgBody;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.interfaces.im.rpc
 * @author: 20148
 * @description:
 * @create-date: 9/17/2024 4:54 PM
 * @version: 1.0
 */

@DubboService(timeout = 60000, retries = 0)
public interface ImRouterHandlerRpc {
    /**
     * 发送消息
     * @param objectId
     * @param sengMsg
     */
    void sendMsg(ImMsgBody sengMsg);
}
