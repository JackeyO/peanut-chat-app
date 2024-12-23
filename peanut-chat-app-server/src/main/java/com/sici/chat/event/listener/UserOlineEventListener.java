package com.sici.chat.event.listener;

import com.sici.chat.builder.cache.RoomOnlineCountCacheKeyBuilder;
import com.sici.chat.dao.RoomMemberDao;
import com.sici.chat.event.UserOnlineEvent;
import com.sici.framework.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @projectName: peanut-chat-app
 * @package: com.sici.chat.event.listener
 * @author: 20148
 * @description: 用户上线事件监听
 * @create-date: 12/23/2024 4:56 PM
 * @version: 1.0
 */

@Component
public class UserOlineEventListener {
    @Resource
    private RoomOnlineCountCacheKeyBuilder roomOnlineCountCacheKeyBuilder;
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Resource
    private RoomMemberDao roomMemberDao;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, classes = UserOnlineEvent.class, fallbackExecution = true)
    public void userOnline(UserOnlineEvent event) {
        Integer userId = event.getUserId();

        // 更新房间在线用户数缓存
        threadPoolTaskExecutor.execute(() -> {
            List<String> roomOnlineCountKeys = roomMemberDao.getRoomsByUserId(userId)
                    .stream()
                    .map(roomId -> roomOnlineCountCacheKeyBuilder.build(roomId))
                    .collect(Collectors.toList());

            RedisUtils.executePipelined((RedisCallback<?>)
                    (redisConnection) -> {
                        roomOnlineCountKeys.forEach(key -> redisConnection.decr(key.getBytes()));
                        return null;
                    });
        });
    }
}
