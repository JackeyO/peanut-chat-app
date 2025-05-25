package com.sici.chat.cache;

import com.sici.chat.adapter.MessageViewAdapter;
import com.sici.chat.builder.cache.RoomMessageCacheKeyBuilder;
import com.sici.chat.dao.MessageDao;
import com.sici.chat.model.chat.message.cache.RoomMessageCacheInfo;
import com.sici.chat.model.chat.message.vo.ChatMessageVo;
import com.sici.chat.util.ChatMessageStringConvertUtil;
import com.sici.framework.redis.RedisUtils;
import com.sici.framework.redis.batch.AbstractRedisStringCache;
import jakarta.annotation.Resource;
import org.assertj.core.util.Lists;
import org.springframework.data.redis.connection.zset.Tuple;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: 20148
 * @description: 房间缓存
 * @create-date: 12/2/2024 3:05 PM
 * @version: 1.0
 */

@Component
public class RoomMessageCache extends AbstractRedisStringCache<Long, RoomMessageCacheInfo> {
    @Resource
    private RoomMessageCacheKeyBuilder roomMessageCacheKeyBuilder;

    @Resource
    private MessageViewAdapter messageViewAdapter;
    @Resource
    private MessageDao messageDao;

    @Override
    public String getKey(Long req) {
        return roomMessageCacheKeyBuilder.build(req);
    }

    @Override
    public long getExpireSeconds() {
        return roomMessageCacheKeyBuilder.getExpireTime().toSeconds();
    }

    @Override
    public Map<Long, RoomMessageCacheInfo> loadFromDb(List<Long> req) {
        List<List<ChatMessageVo>> messages = messageDao.getMessageByRoomId(req)
                .stream()
                .map(messageList -> messageList.stream()
                        .map(message -> messageViewAdapter.adaptChatMessage(message))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());

        Map<Long, RoomMessageCacheInfo> result = req.stream()
                .collect(Collectors.toMap(roomId -> roomId,
                        roomId -> RoomMessageCacheInfo.builder()
                                .messages(messages.get(req.indexOf(roomId)))
                                .build()));
        return result;
    }

    @Override
    public RoomMessageCacheInfo getFromCache(Long req) {
        // 从zSet中获取所有消息
        List<ChatMessageVo> messages = RedisUtils.zAll(getKey(req)).stream()
                .map(ChatMessageStringConvertUtil::convertMessageStringToObject)
                .collect(Collectors.toList());
        return RoomMessageCacheInfo.builder()
                .messages(messages)
                .build();
    }

    @Override
    public List<RoomMessageCacheInfo> getFromCache(List<Long> req) {
        List<String> keys = req.stream().map(this::getKey).collect(Collectors.toList());

        List<RoomMessageCacheInfo> cacheInfos = Lists.newArrayList();

        // 使用redis callback执行批量获取
        RedisUtils.executePipelined(connection -> {
            keys.forEach(key -> {
                List<ChatMessageVo> result = connection.zRange(key.getBytes(), 0, -1)
                        .stream()
                        .map(bytes -> {
                            String messageString = new String(bytes);
                            ChatMessageVo chatMessageVo = ChatMessageStringConvertUtil.convertMessageStringToObject(messageString);
                            return chatMessageVo;
                        })
                        .collect(Collectors.toList());

                cacheInfos.add(RoomMessageCacheInfo.builder()
                        .messages(result)
                        .build());
            });
            return null;
        });
        return cacheInfos;
    }

    // set zSet
    @Override
    public void setToCache(Map<String, RoomMessageCacheInfo> toSet) {
        // 使用redis callback执行批量设置
        RedisUtils.executePipelined(connection -> {
            toSet.forEach((key, value) -> {
                Set<Tuple> messageTupleSet = value.getMessages().stream()
                        .map(message -> Tuple.of(
                                ChatMessageStringConvertUtil.convertMessageObjectToString(message).getBytes(),
                                Double.valueOf(message.getMessage().getSendTime().getTime())
                        ))
                        .collect(Collectors.toSet());
                connection.zAdd(key.getBytes(), messageTupleSet);
            });
            return null;
        });
    }
}