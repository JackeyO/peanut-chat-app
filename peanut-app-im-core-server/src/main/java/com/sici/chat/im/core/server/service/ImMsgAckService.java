package com.sici.chat.im.core.server.service;

import com.sici.chat.model.im.dto.ImMsgBody;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.im.core.server.service
 * @author: 20148
 * @description: Im Ack
 * @create-date: 9/19/2024 2:34 PM
 * @version: 1.0
 */

public interface ImMsgAckService {
    void doMsgAck(ImMsgBody imMsgBody);

    void sendDelayMessage(ImMsgBody imMsgBody);

    void recordMsgAck(ImMsgBody imMsgBody);

    Integer getMsgAckTimes(ImMsgBody imMsgBody);
}
