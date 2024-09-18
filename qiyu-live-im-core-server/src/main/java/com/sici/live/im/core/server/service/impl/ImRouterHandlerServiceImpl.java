package com.sici.live.im.core.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.sici.live.im.core.server.common.ChannelHandlerContextCache;
import com.sici.live.im.core.server.common.ImMsgBuilder;
import com.sici.live.im.core.server.service.ImRouterHandlerService;
import com.sici.live.model.im.dto.ImMsgBody;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.im.core.server.service.impl
 * @author: 20148
 * @description:
 * @create-date: 9/18/2024 5:11 PM
 * @version: 1.0
 */

@Service
@Slf4j
public class ImRouterHandlerServiceImpl implements ImRouterHandlerService {
    @Override
    public void onReceive(ImMsgBody imMsgBody) {
        Long userId = imMsgBody.getUserId();
        ChannelHandlerContext ctx = ChannelHandlerContextCache.get(userId);
        if (ctx == null) {
            log.error("[imRouterHandlerServiceImpl]==>没有找到对应的ChannelHandlerContext, imMsgBody:{}", imMsgBody);
        }
        ctx.writeAndFlush(ImMsgBuilder.buildBiz(JSON.toJSONString(imMsgBody)));
    }
}
