package com.sici.live.im.core.server.common;

import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.im.core.server.common
 * @author: 20148
 * @description:
 * @create-date: 9/16/2024 8:12 PM
 * @version: 1.0
 */

public class ChannelHandlerContextCache {
    private static Map<Long, ChannelHandlerContext> channelHandlerContextCacheMap = new HashMap<>();

    public static ChannelHandlerContext get(Long userId) {
        return channelHandlerContextCacheMap.get(userId);
    }

    public static void put(Long userId, ChannelHandlerContext channelHandlerContext) {
        channelHandlerContextCacheMap.put(userId, channelHandlerContext);
    }

    public static void remove(Long userId) {
        channelHandlerContextCacheMap.remove(userId);
    }
}
