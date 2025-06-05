package com.sici.framework.agent.context;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 消息处理的上下文信息
 */
@Data
@Builder
public class MessageContext {
    private String messageId;
    private LocalDateTime timestamp;
    private Map<String, Object> attributes;
    private String senderName;
    private String recipientName;
    
    public MessageContext() {
        this.timestamp = LocalDateTime.now();
        this.attributes = new HashMap<>();
    }
    
    public MessageContext(String messageId) {
        this();
        this.messageId = messageId;
    }
    
    // 便捷方法
    public void putAttribute(String key, Object value) {
        attributes.put(key, value);
    }
    
    public <T> T getAttribute(String key, Class<T> type) {
        Object value = attributes.get(key);
        if (value != null && type.isInstance(value)) {
            return type.cast(value);
        }
        return null;
    }
    
    public Object getAttribute(String key) {
        return attributes.get(key);
    }
    
    public boolean hasAttribute(String key) {
        return attributes.containsKey(key);
    }
}