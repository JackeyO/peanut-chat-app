package com.sici.chat.ws.handler;


import com.sici.chat.cache.conversation.ConversationActivityCache;
import com.sici.chat.config.thread.ThreadPoolConfiguration;
import com.sici.chat.model.chat.conversation.cache.ConversationActivityCacheInfo;
import com.sici.chat.model.chat.ws.WsReadEventDto;
import com.sici.chat.model.ws.bo.ImMsgReq;
import com.sici.common.enums.chat.message.MessageReqTypeEnum;
import io.netty.channel.ChannelHandlerContext;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * @author: jackey
 * @description: read事件消息处理器
 * @create-date: 9/16/2024 3:24 PM
 */


@Component
@Slf4j
public class WsConversationReadMessageHandler extends AbstractWsMessageHandler<WsReadEventDto> {
    @Resource
    private ConversationActivityCache conversationActivityCache;

    @Resource
    @Qualifier(ThreadPoolConfiguration.CHAT_PUBLIC_EXECUTOR)
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public MessageReqTypeEnum getSupportedMessageType() {
        return MessageReqTypeEnum.READ_EVENT_REQUEST;
    }

    /**
     * 处理客户端登录请求
     * @param ctx
     * @param msgReq
     */
    @Override
    public void handle(ChannelHandlerContext ctx, ImMsgReq<WsReadEventDto> msgReq) {
        // 处理read事件, 异步方式执行
        CompletableFuture.runAsync(() -> {
            WsReadEventDto wsReadEventDto = msgReq.getData();

            Long conversationId = wsReadEventDto.getConversationId();
            Long readTime = wsReadEventDto.getReadTime();

            // 更新会话的最后活动时间
            ConversationActivityCacheInfo conversationActivityCacheInfo = new ConversationActivityCacheInfo();
            conversationActivityCacheInfo.setLastActivityTime(readTime);
            conversationActivityCache.set(conversationId, conversationActivityCacheInfo);

            log.info("update conversation activity cache, conversationId: {}, readTime: {}", conversationId, readTime);
        }, threadPoolTaskExecutor);
    }
}