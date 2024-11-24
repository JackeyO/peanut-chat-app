package com.sici.chat.im.core.server.comsumer;

import com.sici.common.constant.im.ImConstant;
import com.sici.common.constant.im.ImMqConstant;
import com.sici.chat.im.core.server.service.ImMsgAckService;
import com.sici.chat.im.core.server.service.ImRouterHandlerService;
import com.sici.chat.model.im.dto.ImMsgBody;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

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
@RocketMQMessageListener(consumerGroup = ImMqConstant.IM_CORE_SERVER_MSG_ACK_DELAY_CONSUMER_GROUP,
        topic = ImMqConstant.IM_CORE_SERVER_MSG_ACK_DELAY_TOPIC)
public class ImMsgAckDelayMessageConsumer implements RocketMQListener<ImMsgBody> {
    @Resource
    private ImMsgAckService imMsgAckService;
    @Resource
    private ImRouterHandlerService imRouterHandlerService;

    @Override
    public void onMessage(ImMsgBody imMsgBody) {
        if (imMsgBody == null || imMsgBody.getUserId() == null || imMsgBody.getAppId() == null) {
            log.error("[im-core-server]==>mq==>[IM消息确认--延迟消息-消费者--接收到消息]==>消息参数不合法");
        }
        try {
            handle(imMsgBody);
            log.info("[im-core-server]==>mq==>[IM消息确认--延迟消息-消费者--检查成功], 消息内容:{}", imMsgBody);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("[im-core-server]==>mq==>[IM消息确认--延迟消息-消费者--检查失败], 消息内容:{}", imMsgBody);
        }
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
