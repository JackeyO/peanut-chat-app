package com.sici.live.im.core.server.comsumer;

import com.alibaba.fastjson.JSON;
import com.sici.common.constant.im.ImConstant;
import com.sici.framework.redis.CacheService;
import com.sici.live.im.core.server.redis.key.HeartBeatMessageKeyBuilder;
import com.sici.live.model.im.dto.ImMsgBody;
import com.sici.live.model.user.vo.UserVO;
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
public class ImUserOnlineTimeCacheSaveConsumer implements MessageListenerConcurrently {
    @Resource
    private CacheService cacheService;
    @Resource
    private HeartBeatMessageKeyBuilder heartBeatMessageKeyBuilder;
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        String imMsgBodyStr = new String(msgs.get(0).getBody());
        log.info("[im-core-server]==>mq==>[用户最新在线时间缓存记录-消费者--接收到消息]==>msgStr is {}", imMsgBodyStr);
        if (!StringUtils.isEmpty(imMsgBodyStr)) {
            ImMsgBody imMsgBody = JSON.parseObject(imMsgBodyStr, ImMsgBody.class);
            if (imMsgBody == null || imMsgBody.getUserId() == null || imMsgBody.getAppId() == null) {
                log.error("[im-core-server]==>mq==>[用户最新在线时间缓存记录-消费者--接收到消息]==>消息参数不合法");
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }

            try {
                handle(imMsgBody);
                log.info("[im-core-server]==>mq==>[用户最新在线时间缓存记录-消费者--成功记录], 消息内容:{}", imMsgBody);
            } catch (Exception e) {
                e.printStackTrace();
                log.info("[im-core-server]==>mq==>[用户最新在线时间缓存记录-消费者--记录失败], 消息内容:{}", imMsgBody);
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

    void handle(ImMsgBody imMsgBody) {
        try {
            Long userId = imMsgBody.getUserId();
            Integer appId = imMsgBody.getAppId();

            long currentMillis = System.currentTimeMillis();
            String zSetKey = heartBeatMessageKeyBuilder.buildHearBeatKey(userId, appId);
            cacheService.expire(zSetKey, 5, TimeUnit.MINUTES);
            cacheService.zAdd(zSetKey,
                    JSON.toJSONString(userId), currentMillis);

            // 删除两个心跳间隔外的心跳包记录(考虑到网络延迟)
            cacheService.zRemoveRangeByScore(zSetKey, 0, currentMillis - ImConstant.DEFAULT_HEART_BEAT_INTERVAL * 2);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
