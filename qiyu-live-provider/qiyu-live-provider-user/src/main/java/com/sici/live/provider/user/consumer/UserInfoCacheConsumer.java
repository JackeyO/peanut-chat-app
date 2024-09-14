package com.sici.live.provider.user.consumer;

import com.alibaba.fastjson.JSON;
import com.sici.framework.redis.CacheService;
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

@Component
@Slf4j
public class UserInfoCacheConsumer implements MessageListenerConcurrently {
    @Resource
    private CacheService cacheService;
    @Resource
    private UserProviderCacheKeyBuilder userProviderCacheKeyBuilder;

    /**
     * 延迟删除用户信息缓存
     * @param msgs
     * @param consumeConcurrentlyContext
     * @return
     */
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        String userVOStr = new String(msgs.get(0).getBody());
        log.info("用户RPC微服务==>mq==>用户信息缓存删除消费者==>接收到消息,msgStr is {}", userVOStr);
        if (!StringUtils.isEmpty(userVOStr)) {
            UserVO userVO = JSON.parseObject(userVOStr, UserVO.class);

            if (userVO == null || userVO.getUserId() == null) {
                log.error("用户RPC微服务==>mq==>用户信息缓存删除消费者==>用户信息参数为空");
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }

            cacheService.delete(userProviderCacheKeyBuilder.buildUserInfoKey(userVO.getUserId()));
            log.info("用户RPC微服务==>mq==>用户信息缓存删除消费者==>成功消费消息==>延迟删除成功,用户id:{}", userVO.getUserId());
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}