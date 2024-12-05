package com.sici.chat.ws.common;

import io.netty.util.AttributeKey;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.ws.core.server.common
 * @author: 20148
 * @description:
 * @create-date: 9/16/2024 8:23 PM
 * @version: 1.0
 */

public class ChannelAttr {
    public static AttributeKey<Integer> USER_ID = AttributeKey.valueOf("userId");
    public static AttributeKey<Integer> IP = AttributeKey.valueOf("ip");
    public static AttributeKey<Integer> TOKEN = AttributeKey.valueOf("token");
}