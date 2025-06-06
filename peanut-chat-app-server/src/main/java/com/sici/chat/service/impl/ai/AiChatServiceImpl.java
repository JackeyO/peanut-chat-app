package com.sici.chat.service.impl.ai;

import com.sici.chat.prompt.UserPromptTemplateConstant;
import com.sici.chat.service.ai.AiChatService;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Map;

/**
 * @author jackey
 * @description
 * @date 6/6/25 01:18
 */
@Service
public class AiChatServiceImpl implements AiChatService {
    @Resource
    @Qualifier("volcengineDeepSeekChatClient")
    private ChatClient volcengineDeepSeekChatClient;

    @Override
    public Flux<String> rewriteContent(String content, String prompt) {
        return volcengineDeepSeekChatClient.prompt()
                .user(u -> u.text(UserPromptTemplateConstant.CONTENT_REWRITE_PROMPT)
                        .param("content", content)
                        .param("prompt", prompt))
                .stream()
                .content();
    }
}
