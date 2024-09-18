package com.sici.live.im.core.server.handler.impl;

import com.sici.common.constant.im.ImConstant;
import com.sici.common.constant.im.ImMqConstant;
import com.sici.live.im.core.server.common.ImMsg;
import com.sici.live.im.core.server.common.util.ImContextUtil;
import com.sici.live.im.core.server.handler.AbstractMessageHandler;
import io.netty.channel.ChannelHandlerContext;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.stereotype.Component;

import static com.sici.common.constant.im.ImConstant.IM_BIZ_MESSAGE_TOPIC;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.im.core.server.handler.impl
 * @author: 20148
 * @description: 业务消息处理器
 * @create-date: 9/16/2024 3:24 PM
 * @version: 1.0
 */

@Component
@Slf4j
public class BizMessageHandler implements AbstractMessageHandler {
    @Resource
    private MQProducer mqProducer;

    @Override
    public void handle(ChannelHandlerContext ctx, ImMsg imMsg) {
        Long userId = ImContextUtil.getUserId(ctx);
        Integer appId = ImContextUtil.getAppId(ctx);

        if (userId == null || appId == null) {
            log.error("[BizMessageHandler]==>imMsg error:{}", imMsg);
            ctx.close();
            throw new IllegalArgumentException("[BizMessageHandler]==>imMsg error:" + imMsg);
        }

        byte[] body = imMsg.getBody();
        if (body == null || body.length == 0) {
            log.error("[BizMessageHandler]==>body error");
            return ;
        }

        Message message = new Message();
        message.setTopic(ImMqConstant.IM_CORE_SERVER_MESSAGE_RECEIVE_TOPIC);
        message.setBody(body);

        try {
            SendResult sendResult = mqProducer.send(message);
            log.info("[BizMessageHandler]==>[message send status]==>", sendResult.getSendStatus());
        } catch (Exception e) {
            log.error("[BizMessageHandler]==>[message send error]");
        }
    }
}
