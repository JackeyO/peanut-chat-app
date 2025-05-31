package com.sici.chat.config.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jackey
 * @description
 * @date 5/31/25 21:34
 */
@Configuration
public class AiChatClientConfiguration {
    @Bean
    public ChatClient openAiChatClient(@Qualifier("openAiChatModel") OpenAiChatModel openAiChatModel) {
        return ChatClient.create(openAiChatModel);
    }

    @Bean
    public ChatClient deepseekChatClient(DeepSeekChatModel deepSeekChatModel) {
        return ChatClient.create(deepSeekChatModel);
    }

    @Bean
    public ChatClient volcengineDeepSeekChatClient(@Qualifier("volcengineDeepSeekChatModel") OpenAiChatModel volcengineDeepSeekChatModel) {
        return ChatClient.create(volcengineDeepSeekChatModel);
    }
}
