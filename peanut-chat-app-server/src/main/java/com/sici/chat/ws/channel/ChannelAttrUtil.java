package com.sici.chat.ws.channel;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.ws.core.server.channel.util
 * @author: 20148
 * @description:
 * @create-date: 9/16/2024 8:27 PM
 * @version: 1.0
 */

public class ChannelAttrUtil {
    public static <T> T getAttr(Channel channel, AttributeKey<T> attributeKey) {
        return channel.attr(attributeKey).get();
    }
    public static <T> void setUserId(Channel channel, AttributeKey<T> attributeKey, T data) {
        channel.attr(attributeKey).set(data);
    }
}
