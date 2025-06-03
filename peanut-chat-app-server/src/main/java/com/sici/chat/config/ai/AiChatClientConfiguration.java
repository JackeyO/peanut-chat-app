package com.sici.chat.config.ai;

import com.sici.chat.agent.tools.DateTimeTools;
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
        return ChatClient.builder(openAiChatModel)
                .defaultTools(new DateTimeTools())
                .build();
    }

    @Bean
    public ChatClient deepseekChatClient(DeepSeekChatModel deepSeekChatModel) {
        return ChatClient.builder(deepSeekChatModel)
                .defaultTools(new DateTimeTools())
                .build();
    }

    @Bean
    public ChatClient volcengineDeepSeekChatClient(@Qualifier("volcengineDeepSeekChatModel") DeepSeekChatModel volcengineDeepSeekChatModel) {
        return ChatClient.builder(volcengineDeepSeekChatModel)
                .defaultTools(new DateTimeTools())
                .build();
    }
}
