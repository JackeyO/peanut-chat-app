package com.sici.chat.im.core.server.handler.impl;

import com.sici.common.enums.im.ImMsgCodeEnums;
import com.sici.chat.im.core.server.common.ImMsg;
import com.sici.chat.im.core.server.handler.AbstractMessageHandler;
import com.sici.chat.im.core.server.handler.ImHandlerFactory;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.im.core.server.handler.impl
 * @author: 20148
 * @description:
 * @create-date: 9/16/2024 3:28 PM
 * @version: 1.0
 */

@Component
public class ImMessageHandlerFactoryImpl implements ImHandlerFactory, InitializingBean {
    private static Map<Integer, AbstractMessageHandler> imMessageHandlers = new HashMap<>();
    @Resource
    private ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() {
        initializeMessageHandlers();
    }

    private void initializeMessageHandlers() {
        imMessageHandlers.put(ImMsgCodeEnums.IM_MSG_LOGIN.getCode(), applicationContext.getBean(LoginMessageHandler.class));
        imMessageHandlers.put(ImMsgCodeEnums.IM_MSG_LOGOUT.getCode(), applicationContext.getBean(LogoutMessageHandler.class));
        imMessageHandlers.put(ImMsgCodeEnums.IM_MSG_BIZ.getCode(), applicationContext.getBean(BizMessageHandler.class));
        imMessageHandlers.put(ImMsgCodeEnums.IM_MSG_HEARTBEAT.getCode(), applicationContext.getBean(HeartBeatMessageHandler.class));
        imMessageHandlers.put(ImMsgCodeEnums.IM_MSG_ACK.getCode(), applicationContext.getBean(ImMessageAckHandler.class));
    }

    @Override
    public void doMessageHandle(ChannelHandlerContext ctx, ImMsg imMsg) {
        AbstractMessageHandler messageHandler = imMessageHandlers.get(imMsg.getCode());
        if (messageHandler == null) {
            throw new IllegalArgumentException("non-supported message type, found non message handler to handle, message : " + imMsg);
        }
        // handle this message
        messageHandler.handle(ctx, imMsg);
    }
}
