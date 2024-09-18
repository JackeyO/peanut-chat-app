package com.sici.live.im.core.server.common.util;

import com.alibaba.fastjson.JSON;
import com.sici.common.constant.im.ImMqConstant;
import com.sici.live.model.im.dto.ImMsgBody;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.stereotype.Component;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.im.core.server.common.util
 * @author: 20148
 * @description:
 * @create-date: 9/18/2024 4:41 PM
 * @version: 1.0
 */

@Component
@Slf4j
public class ImCacheUtil {
    @Resource
    private MQProducer mqProducer;

    private void sendMessage(String topic, String data) {
        Message message = new Message();
        message.setTopic(topic);
        message.setBody(data.getBytes());
        try {
            SendResult send = mqProducer.send(message);
            if (send.getSendStatus().equals(SendStatus.SEND_OK)) {
                log.info("[im-cache-util]==>发送消息到topic:{}成功,消息内容:{}",
                        topic,
                        message);
            } else {
                log.error("[im-cache-util]==>发送消息到topic:{}失败,消息内容:{}",
                        topic,
                        message);
            }
        } catch (Exception e) {
            log.error("[im-cache-util]==>发送消息到topic:{}失败,消息内容:{}",
                    topic,
                    message);
        }
    }
    public void recordImOnlineTime(Long userId, Integer appId) {
        sendMessage(ImMqConstant.IM_CORE_SERVER_USER_ONLINE_CACHE_SAVE_TOPIC,
                JSON.toJSONString(ImMsgBody.builder()
                        .userId(userId)
                        .appId(appId)
                        .build()));
    }
    public void recordImBindAddressOfUserId(Long userId, Integer appId) {
        sendMessage(ImMqConstant.IM_CORE_SERVER_USER_IM_SERVER_ADDRESS_CACHE_SAVE_TOPIC,
                JSON.toJSONString(ImMsgBody.builder()
                        .userId(userId)
                        .appId(appId)
                        .build()));
    }

    public void removeImOnlineTime(Long userId, Integer appId) {
        sendMessage(ImMqConstant.IM_CORE_SERVER_USER_ONLINE_CACHE_DELETE_TOPIC,
                JSON.toJSONString(ImMsgBody.builder()
                        .userId(userId)
                        .appId(appId)
                        .build()));
    }
    public void removeImBindAddressOfUserId(Long userId, Integer appId) {
        sendMessage(ImMqConstant.IM_CORE_SERVER_USER_IM_SERVER_ADDRESS_CACHE_DELETE_TOPIC,
                JSON.toJSONString(ImMsgBody.builder()
                        .userId(userId)
                        .appId(appId)
                        .build()));
    }
}
