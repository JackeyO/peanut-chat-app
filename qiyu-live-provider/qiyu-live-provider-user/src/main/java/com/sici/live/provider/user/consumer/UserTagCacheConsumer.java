package com.sici.live.provider.user.consumer;

import com.alibaba.fastjson.JSON;
import com.sici.framework.redis.CacheService;
import com.sici.live.model.user.tag.vo.UserTagVO;
import com.sici.live.model.user.vo.UserVO;
import com.sici.live.provider.user.redis.key.UserProviderCacheKeyBuilder;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.provider.user.consumer
 * @author: 20148
 * @description: 清理用户标签缓存
 * @create-date: 9/15/2024 3:29 PM
 * @version: 1.0
 */

@Component
@Slf4j
public class UserTagCacheConsumer implements MessageListenerConcurrently {
    @Resource
    private CacheService cacheService;
    @Resource
    private UserProviderCacheKeyBuilder userProviderCacheKeyBuilder;
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        String userTagVOStr = new String(msgs.get(0).getBody());
        log.info("用户RPC微服务==>mq==>用户标签信息缓存删除消费者==>接收到消息,msgStr is {}", userTagVOStr);
        if (!StringUtils.isEmpty(userTagVOStr)) {
            UserTagVO userTagVO = JSON.parseObject(userTagVOStr, UserTagVO.class);

            if (userTagVO == null || userTagVO.getUserId() == null) {
                log.error("用户RPC微服务==>mq==>用户标签信息缓存删除消费者==>用户标签信息参数为空");
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }

            cacheService.delete(userProviderCacheKeyBuilder.buildUserTakKey(userTagVO.getUserId()));
            log.info("用户RPC微服务==>mq==>用户标签信息缓存删除消费者==>成功消费消息==>延迟删除成功,用户id:{}", userTagVO.getUserId());
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
