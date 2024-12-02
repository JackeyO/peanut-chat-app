package com.sici.chat.im.core.server.service.impl;

import com.sici.chat.im.core.server.common.ChannelHandlerContextCache;
import com.sici.chat.model.im.bo.ImMsg;
import com.sici.chat.im.core.server.service.ImMsgAckService;
import com.sici.chat.im.core.server.service.ImPushService;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
public class ImPushServiceImpl implements ImPushService {
    @Resource
    private ImMsgAckService imMsgAckService;
    @Override
    public void pushMsg(ImMsg imMsg, List<Integer> receiverIds) {
        receiverIds.forEach(receiverId -> {
            pushMsg(imMsg, receiverId);
        });
    }
    @Override
    public void pushMsg(ImMsg imMsg, Integer receiveId) {
        imMsg.setToUid(receiveId);
        ChannelHandlerContext ctx = ChannelHandlerContextCache.get(receiveId);
        if (ctx == null) {
            log.error("[imRouterHandlerServiceImpl]==>没有找到对应的ChannelHandlerContext, imMsgBody:{}", imMsg);
        }
        // 发送消息到客户端后，需要记录当前重试次数,初始为0,最多重试3次
        imMsgAckService.recordMsgAck(imMsg);
        ctx.writeAndFlush(imMsg);
        imMsgAckService.sendDelayMessage(imMsg, 2);
    }
}