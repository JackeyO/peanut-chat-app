package com.sici.live.im.core.server.common.util;

import com.sici.live.im.core.server.common.ImContextAttr;
import io.netty.channel.ChannelHandlerContext;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.im.core.server.common.util
 * @author: 20148
 * @description:
 * @create-date: 9/16/2024 8:27 PM
 * @version: 1.0
 */

public class ImContextUtil {

    public static Long getUserId(ChannelHandlerContext ctx) {
        return ctx.attr(ImContextAttr.USER_ID).get();
    }

    public static Integer getAppId(ChannelHandlerContext ctx) {
        return ctx.attr(ImContextAttr.APP_ID).get();
    }


    public static void setUserId(ChannelHandlerContext ctx, Long userId) {
        ctx.attr(ImContextAttr.USER_ID).set(userId);
    }

    public static void setAppId(ChannelHandlerContext ctx, Integer appId) {
        ctx.attr(ImContextAttr.APP_ID).set(appId);
    }
}
