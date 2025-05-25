package com.sici.chat.model.chat.conversation.cache;

import com.sici.chat.model.chat.conversation.entity.UserConversation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jackey
 * @description 对话缓存信息
 * @date 5/25/25 12:48
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConversationBaseCacheInfo {
    private UserConversation conversation;
}
