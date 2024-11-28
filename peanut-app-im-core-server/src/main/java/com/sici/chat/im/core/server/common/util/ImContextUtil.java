package com.sici.chat.im.core.server.common.util;

import com.sici.chat.im.core.server.common.ImContextAttr;
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
    public static Integer getUserId(ChannelHandlerContext ctx) {
        return ctx.attr(ImContextAttr.USER_ID).get();
    }

    public static void setUserId(ChannelHandlerContext ctx, Integer userId) {
        ctx.attr(ImContextAttr.USER_ID).set(userId);
    }
}
