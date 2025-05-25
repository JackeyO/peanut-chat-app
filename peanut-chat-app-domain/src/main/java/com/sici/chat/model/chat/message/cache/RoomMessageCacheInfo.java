package com.sici.chat.model.chat.message.cache;

import com.sici.chat.model.chat.message.vo.ChatMessageVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author jackey
 * @description 房间消息缓存
 * @date 5/25/25 15:05
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomMessageCacheInfo {
    /**
     * 房间消息
     */
    private List<ChatMessageVo> messages;
}