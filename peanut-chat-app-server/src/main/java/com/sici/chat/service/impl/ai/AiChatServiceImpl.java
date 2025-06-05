package com.sici.chat.service.impl.ai;

import com.sici.chat.service.ai.AiChatService;
import com.sici.common.constant.ai.prompt.SystemPromptConstant;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

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
    public Flux<String> rewriteContent(String content) {
        return volcengineDeepSeekChatClient.prompt()
                .system(SystemPromptConstant.CONTENT_REWRITE_PROMPT)
                .user(content)
                .stream()
                .content();
    }
}
