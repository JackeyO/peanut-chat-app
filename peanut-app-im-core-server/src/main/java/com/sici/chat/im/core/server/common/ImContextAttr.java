package com.sici.chat.im.core.server.common;

import io.netty.util.AttributeKey;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.im.core.server.common
 * @author: 20148
 * @description:
 * @create-date: 9/16/2024 8:23 PM
 * @version: 1.0
 */

public class ImContextAttr {
    public static AttributeKey<Long> USER_ID = AttributeKey.valueOf("userId");
    public static AttributeKey<Integer> APP_ID = AttributeKey.valueOf("appId");
}
