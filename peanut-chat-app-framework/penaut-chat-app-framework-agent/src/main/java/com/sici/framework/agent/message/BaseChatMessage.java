package com.sici.framework.agent.message;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.ai.chat.messages.UserMessage;

import java.time.LocalDateTime;
import java.util.Map;

// 聊天消息基类
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseChatMessage extends BaseMessage {
    private String source;
    private LocalDateTime timestamp;
    private Map<String, Object> metadata;

    public BaseChatMessage(String source) {
        this.source = source;
        this.timestamp = LocalDateTime.now();
    }
    public abstract UserMessage toModelMessage();
    public abstract String toModelText();
}