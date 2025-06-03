package com.sici.chat.cmd;

import com.sici.chat.agent.tools.DateTimeTools;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author jackey
 * @description
 * @date 5/31/25 22:54
 */
@Component
@Slf4j
public class AiModelCreateTest implements CommandLineRunner {
    @Resource
    @Qualifier("openAiChatClient")
    private ChatClient openAiChatClient;
    @Resource
    @Qualifier("volcengineDeepSeekChatClient")
    private ChatClient volcengineDeepSeekChatClient;
    @Resource
    @Qualifier("deepseekChatClient")
    private ChatClient deepseekChatClient;

    @Override
    public void run(String... args) throws Exception {
//        String response = openAiChatClient
//                .prompt("Can you set an alarm 10 minutes from now?")
//                .tools(new DateTimeTools())
//                .call()
//                .content();
//        log.info("OpenAI Chat Response: {}", response);

        String content = volcengineDeepSeekChatClient.prompt()
                .user("hello")
                .call()
                .content();

        log.info("Volcengine DeepSeek Chat Response: {}", content);

//        String content = deepseekChatClient.prompt()
//                .user("Hello")
//                .call()
//                .content();
//
//        log.info("DeepSeek Chat Response: {}", content);
    }
}
