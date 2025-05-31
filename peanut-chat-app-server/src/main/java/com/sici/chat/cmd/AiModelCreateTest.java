package com.sici.chat.cmd;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author jackey
 * @description
 * @date 5/31/25 22:54
 */
@Component
@Slf4j
public class AiModelCreateTest implements CommandLineRunner {
    @Resource
    private OpenAiChatModel openAiChatModel;
    @Override
    public void run(String... args) throws Exception {

//        log.info("<UNK>");
    }
}
