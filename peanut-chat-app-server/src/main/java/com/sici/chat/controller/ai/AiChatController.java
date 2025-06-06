package com.sici.chat.controller.ai;

import com.sici.chat.service.impl.ai.AiChatServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author jackey
 * @description
 * @date 6/6/25 01:23
 */
@RestController
@RequestMapping("/peanut/ai/chat")
public class AiChatController {
    @Resource
    private AiChatServiceImpl aiChatService;

    @GetMapping("rewrite")
    public Flux<String> rewriteContent(String content, String prompt) {
        return aiChatService.rewriteContent(content, prompt);
    }
}
