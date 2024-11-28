package com.sici.chat.im.core.server.comsumer;

import com.sici.chat.model.im.bo.ImMsg;
import com.sici.common.constant.im.ImConstant;
import com.sici.common.constant.im.ImMqConstant;
import com.sici.chat.im.core.server.service.ImMsgAckService;
import com.sici.chat.im.core.server.service.ImPushService;
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
public class ImMsgAckDelayMessageConsumer implements RocketMQListener<ImMsg> {
    @Resource
    private ImMsgAckService imMsgAckService;
    @Resource
    private ImPushService imPushService;

    @Override
    public void onMessage(ImMsg imMsg) {
        if (imMsg == null || imMsg.getToUid() == null) {
            log.error("[im-core-server]==>mq==>[IM消息确认--延迟消息-消费者--接收到消息]==>消息参数不合法");
        }
        try {
            handle(imMsg);
            log.info("[im-core-server]==>mq==>[IM消息确认--延迟消息-消费者--检查成功], 消息内容:{}", imMsg);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("[im-core-server]==>mq==>[IM消息确认--延迟消息-消费者--检查失败], 消息内容:{}", imMsg);
        }
    }

    void handle(ImMsg imMsg) {
        try {
            Integer msgAckTimes = imMsgAckService.getMsgAckTimes(imMsg);
            // 没有ACK并且已重试次数没有达到上限就需要进行再次重试
            if (msgAckTimes != null && msgAckTimes < ImConstant.IM_MSG_RETRY_TIMES) {
                log.info("[im-core-server]==>mq==>[IM消息确认]==>执行消息重发, imMsg:{}, 已重试次数:{}", imMsg, msgAckTimes + 1);
                imPushService.pushMsg(imMsg, imMsg.getToUid());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
