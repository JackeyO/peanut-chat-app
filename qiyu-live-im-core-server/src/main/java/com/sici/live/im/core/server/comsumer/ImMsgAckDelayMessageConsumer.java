package com.sici.live.im.core.server.comsumer;

import com.alibaba.fastjson.JSON;
import com.sici.common.constant.im.ImConstant;
import com.sici.framework.redis.CacheService;
import com.sici.live.im.core.server.redis.key.HeartBeatMessageKeyBuilder;
import com.sici.live.im.core.server.service.ImMsgAckService;
import com.sici.live.im.core.server.service.ImRouterHandlerService;
import com.sici.live.model.im.dto.ImMsgBody;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.im.core.server.comsumer
 * @author: 20148
 * @description:
 * @create-date: 9/18/2024 4:06 PM
 * @version: 1.0
 */

@Component
@Slf4j
public class ImMsgAckDelayMessageConsumer implements MessageListenerConcurrently {
    @Resource
    private CacheService cacheService;
    @Resource
    private ImMsgAckService imMsgAckService;
    @Resource
    private ImRouterHandlerService imRouterHandlerService;
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        String imMsgBodyStr = new String(msgs.get(0).getBody());
        log.info("[im-core-server]==>mq==>[IM消息确认--延迟消息-消费者--接收到消息]==>msgStr is {}", imMsgBodyStr);
        if (!StringUtils.isEmpty(imMsgBodyStr)) {
            ImMsgBody imMsgBody = JSON.parseObject(imMsgBodyStr, ImMsgBody.class);
            if (imMsgBody == null || imMsgBody.getUserId() == null || imMsgBody.getAppId() == null) {
                log.error("[im-core-server]==>mq==>[IM消息确认--延迟消息-消费者--接收到消息]==>消息参数不合法");
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }

            try {
                handle(imMsgBody);
                log.info("[im-core-server]==>mq==>[IM消息确认--延迟消息-消费者--检查成功], 消息内容:{}", imMsgBody);
            } catch (Exception e) {
                e.printStackTrace();
                log.info("[im-core-server]==>mq==>[IM消息确认--延迟消息-消费者--检查失败], 消息内容:{}", imMsgBody);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

    void handle(ImMsgBody imMsgBody) {
        try {
            Integer msgAckTimes = imMsgAckService.getMsgAckTimes(imMsgBody);
            // 没有ACK并且已重试次数没有达到上限就需要进行再次重试
            if (msgAckTimes != null && msgAckTimes < ImConstant.IM_MSG_RETRY_TIMES) {
                log.info("[im-core-server]==>mq==>[IM消息确认]==>执行消息重发, imMsgBody:{}, 已重试次数:{}", imMsgBody, msgAckTimes + 1);
                imRouterHandlerService.onReceive(imMsgBody);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
