package com.sici.framework.agent.message;

import com.alibaba.fastjson2.JSON;
import org.springframework.ai.chat.messages.UserMessage;

public class StructuredMessage<T> extends BaseChatMessage {
    private T content;
    private String formatString;
    
    @Override
    public String toText() {
        return JSON.toJSONString(content);
    }

    @Override
    public UserMessage toModelMessage() {
        return new UserMessage(toText());
    }

    @Override
    public String toModelText() {
        return toText();
    }

    @Override
    public String getType() {
        return "StructuredMessage";
    }
}