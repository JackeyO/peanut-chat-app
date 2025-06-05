package com.sici.chat.config.ai;

import com.sici.chat.agent.tools.DateTimeTools;
import com.sici.framework.agent.AssistantAgent;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * @author jackey
 * @description
 * @date 6/5/25 18:54
 */
@Configuration
public class AiAgentConfiguration {
    @Bean
    public AssistantAgent assistantAgent(ChatClient openAiChatClient) {
        return new AssistantAgent(openAiChatClient).tools(new DateTimeTools());
    }
}
