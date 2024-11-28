package com.sici.chat.interfaces.im.rpc;

import com.sici.chat.model.im.bo.ImMsg;
import com.sici.chat.model.im.dto.ImMsgBody;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.interfaces.im.rpc
 * @author: 20148
 * @description:
 * @create-date: 9/17/2024 4:54 PM
 * @version: 1.0
 */

@DubboService(timeout = 60000, retries = 0)
public interface ImPushMsgRpc {
    /**
     * 发送消息
     * @param objectId
     * @param sengMsg
     */
    void pushMsg(ImMsg imMsg, Integer receiverId);
    void pushMsg(ImMsg imMsg, List<Integer> receiverIds);
}