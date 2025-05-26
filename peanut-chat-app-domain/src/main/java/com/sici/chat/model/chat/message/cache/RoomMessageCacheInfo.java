package com.sici.chat.model.chat.message.cache;

import com.sici.chat.model.chat.message.vo.ChatMessageVo;
import com.sici.framework.redis.batch.zset.RedisZSetCacheInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jackey
 * @description 房间消息缓存
 * @date 5/25/25 15:05
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomMessageCacheInfo extends RedisZSetCacheInfo<ChatMessageVo> {
}