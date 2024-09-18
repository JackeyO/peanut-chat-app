package com.sici.live.provider.message.handler;

import com.sici.live.model.im.dto.ImMsgBody;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.provider.message.handler
 * @author: 20148
 * @description:
 * @create-date: 9/18/2024 5:36 PM
 * @version: 1.0
 */

public interface MessageHandler {
    void handle(ImMsgBody imMsgBody);
}
