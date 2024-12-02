package com.sici.chat.im.core.server.comsumer;

import com.sici.common.constant.im.ImConstant;
import com.sici.common.constant.im.ImMqConstant;
import com.sici.framework.redis.RedisUtils;
import com.sici.chat.im.core.server.common.ChannelHandlerContextCache;
import com.sici.chat.model.im.dto.ImMsgBody;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

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
@RocketMQMessageListener(consumerGroup = ImMqConstant.IM_CORE_SERVER_USER_IM_SERVER_ADDRESS_CACHE_SAVE_CONSUMER_GROUP,
        topic = ImMqConstant.IM_CORE_SERVER_USER_IM_SERVER_ADDRESS_CACHE_SAVE_TOPIC)
public class ImUserImServerAddressCacheSaveConsumer implements RocketMQListener<ImMsgBody> {
    @Override
    public void onMessage(ImMsgBody imMsgBody) {
        if (imMsgBody == null || imMsgBody.getUserId() == null || imMsgBody.getAppId() == null) {
            log.error("[im-core-server]==>mq==>[用户连接IM服务器地址缓存记录-消费者--接收到消息]==>消息参数不合法");
        }
        try {
            handle(imMsgBody);
            log.info("[im-core-server]==>mq==>[用户连接IM服务器地址缓存记录-消费者--记录成功], 消息内容:{}", imMsgBody);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("[im-core-server]==>mq==>[用户连接IM服务器地址缓存记录-消费者--记录失败], 消息内容:{}", imMsgBody);
        }
    }

    void handle(ImMsgBody imMsgBody) {
        try {
            Long userId = imMsgBody.getUserId();
            Integer appId = imMsgBody.getAppId();

            RedisUtils.set(ImConstant.IM_CORE_SERVER_ADDRESS_CACHE_KEY_PREFIX + appId + ":" + userId,
                    ChannelHandlerContextCache.getServerAddress(),
                    5,
                    TimeUnit.MINUTES
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
