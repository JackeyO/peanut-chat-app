package com.sici.chat.service.ai;

import reactor.core.publisher.Flux;

/**
 * @author jackey
 * @description
 * @date 6/5/25 21:50
 */
public interface AiChatService {
    Flux<String> rewriteContent(String content);
}