package com.sici.chat.cache.room;

import com.alibaba.fastjson2.JSON;
import com.sici.chat.adapter.MessageViewAdapter;
import com.sici.chat.builder.cache.room.RoomMessageCacheKeyBuilder;
import com.sici.chat.dao.MessageDao;
import com.sici.chat.model.chat.message.cache.RoomMessageCacheInfo;
import com.sici.chat.model.chat.message.vo.ChatMessageVo;
import com.sici.chat.util.ChatMessageStringConvertUtil;
import com.sici.framework.redis.batch.zset.AbstractRedisZSetCache;
import jakarta.annotation.Resource;
import org.springframework.data.redis.connection.zset.Tuple;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: 20148
 * @description: 房间缓存
 * @create-date: 12/2/2024 3:05 PM
 * @version: 1.0
 */

@Component
public class RoomMessageCache extends AbstractRedisZSetCache<Long, ChatMessageVo, RoomMessageCacheInfo> {
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
    public Function<ChatMessageVo, Tuple> getMapping() {
        return (message) -> {
            if (message == null) {
                return null;
            }
            return Tuple.of(JSON.toJSONString(message).getBytes(), (double) message.getMessage().getSendTime().getTime());
        };
    }

    @Override
    public Function<String, ChatMessageVo> getMappingFromString() {
        return (messageStr) -> {
            if (!StringUtils.hasText(messageStr)) {
                return null;
            }
            return ChatMessageStringConvertUtil.convertMessageStringToObject(messageStr);
        };
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
                        roomId -> {
                            RoomMessageCacheInfo cacheInfo = new RoomMessageCacheInfo();
                            List<ChatMessageVo> chatMessageVos = messages.get(req.indexOf(roomId));
                            cacheInfo.setData(chatMessageVos);
                            return cacheInfo;
                        }));
        return result;
    }
}