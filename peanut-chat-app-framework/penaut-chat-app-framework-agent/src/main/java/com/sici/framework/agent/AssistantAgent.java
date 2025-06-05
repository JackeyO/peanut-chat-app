package com.sici.framework.agent;

import com.sici.framework.agent.response.Response;
import com.sici.framework.agent.message.BaseChatMessage;
import com.sici.framework.agent.message.TextMessage;
import lombok.Builder;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.ChatResponse;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Builder
public class AssistantAgent extends BaseChatAgent<TextMessage> {
    private final ChatClient chatClient;
    private final List<Object> tools = new ArrayList<>();

    public AssistantAgent(ChatClient chatClient) {
        this.chatClient = chatClient;
        this.name = "AssistantAgent";
        this.description = "AI assistant powered by Spring AI";
    }

    public AssistantAgent(ChatClient chatClient, String name, String description) {
        this.chatClient = chatClient;
        this.name = name;
        this.description = description;
    }

    public AssistantAgent tools(Object... tools) {
        this.tools.addAll(Arrays.asList(tools));
        return this;
    }

    @Override
    public String getSystemPrompt() {
        return "You are an AI assistant that helps users with their questions and tasks. " +
                "You can use tools to perform actions and provide information. " +
                "Respond in a helpful and concise manner.";
    }

    @Override
    public CompletableFuture<Response<TextMessage>> onMessagesFuture(List<BaseChatMessage> messages) {
        return CompletableFuture.supplyAsync(() -> onMessages(messages));
    }

    @Override
    public Response<TextMessage> onMessages(List<BaseChatMessage> messages) {
        // 构建对话上下文 - 包含完整的会话历史
        List<Message> chatMessages = super.buildChatContext(messages);

        // 构建ChatClient调用 - Spring AI会自动处理工具调用
        ChatClient.ChatClientRequestSpec requestSpec = chatClient.prompt()
                .system(getSystemPrompt())
                .messages(chatMessages);

        // 如果有工具，添加到调用中
        if (!tools.isEmpty()) {
            requestSpec = requestSpec.tools(tools.toArray());
        }

        // 调用AI模型 - Spring AI内部会自动处理tool调用和获取final response
        ChatResponse chatResponse = requestSpec.call().chatResponse();

        String content = chatResponse.getResult().getOutput().getText();

        // 创建响应消息
        TextMessage responseMessage = new TextMessage(content, getName());

        return Response.<TextMessage>builder()
                .chatMessage(responseMessage)
                .usage(extractUsage(chatResponse))
                .build();

    }

    @Override
    public Flux<String> onMessagesStream(List<BaseChatMessage> messages) {
        List<Message> chatMessages = super.buildChatContext(messages);

        ChatClient.ChatClientRequestSpec requestSpec = chatClient.prompt()
                .messages(chatMessages);

        if (!tools.isEmpty()) {
            requestSpec = requestSpec.tools(tools.toArray());
        }

        // 流式调用 - Spring AI会自动处理工具调用
        return requestSpec.stream().content();
    }


    @Override
    public List<Class<? extends BaseChatMessage>> getProducedMessageTypes() {
        return List.of(TextMessage.class);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}