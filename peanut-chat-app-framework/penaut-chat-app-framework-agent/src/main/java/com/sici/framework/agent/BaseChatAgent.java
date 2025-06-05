package com.sici.framework.agent;

import com.sici.framework.agent.message.BaseChatMessage;
import com.sici.framework.agent.response.Response;
import com.sici.framework.agent.usage.RequestUsage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.ChatResponse;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

// 聊天Agent抽象
public abstract class BaseChatAgent<T extends BaseChatMessage> implements Agent {
    protected String name;
    protected String description;

    public abstract String getSystemPrompt();

    // 支持的消息类型
    public abstract List<Class<? extends BaseChatMessage>> getProducedMessageTypes();

    // 处理消息的核心方法
    public abstract CompletableFuture<Response<T>> onMessagesFuture(List<BaseChatMessage> messages);

    public abstract Response<T> onMessages(List<BaseChatMessage> messages);

    // 流式处理
    public abstract Flux<String> onMessagesStream(List<BaseChatMessage> messages);

    protected List<Message> buildChatContext(List<BaseChatMessage> messages) {
        return messages.stream()
                .map(BaseChatMessage::toModelMessage)
                .collect(Collectors.toList());
    }

    protected RequestUsage extractUsage(ChatResponse response) {
        if (response.getMetadata() != null) {
            Usage usage = response.getMetadata().getUsage();
            if (usage != null) {
                return RequestUsage.builder()
                        .promptTokens(usage.getPromptTokens())
                        .completionTokens(usage.getCompletionTokens())
                        .build();
            }
        }
        return null;
    }

}