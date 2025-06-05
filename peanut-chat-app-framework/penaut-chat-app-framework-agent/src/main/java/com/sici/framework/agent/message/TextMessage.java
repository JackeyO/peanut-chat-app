package com.sici.framework.agent.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.ai.chat.messages.UserMessage;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TextMessage extends BaseChatMessage {
    private String content;

    public TextMessage(String content, String source) {
        super(source);
        this.content = content;
    }
    @Override
    public String getType() { return "TextMessage"; }
    
    @Override
    public String toText() { return content; }
    
    @Override
    public UserMessage toModelMessage() {
        return new UserMessage(content);
    }
    
    @Override
    public String toModelText() { return content; }
}