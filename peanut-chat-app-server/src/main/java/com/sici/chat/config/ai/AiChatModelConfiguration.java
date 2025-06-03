package com.sici.chat.config.ai;

import static org.springframework.ai.model.openai.autoconfigure.OpenAIAutoConfigurationUtil.resolveConnectionProperties;

import com.alibaba.fastjson2.JSON;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.ai.chat.observation.ChatModelObservationConvention;
import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.ai.deepseek.DeepSeekChatOptions;
import org.springframework.ai.deepseek.api.DeepSeekApi;
import org.springframework.ai.model.SimpleApiKey;
import org.springframework.ai.model.deepseek.autoconfigure.DeepSeekChatProperties;
import org.springframework.ai.model.deepseek.autoconfigure.DeepSeekConnectionProperties;
import org.springframework.ai.model.openai.autoconfigure.OpenAIAutoConfigurationUtil;
import org.springframework.ai.model.openai.autoconfigure.OpenAiChatProperties;
import org.springframework.ai.model.openai.autoconfigure.OpenAiConnectionProperties;
import org.springframework.ai.model.openai.autoconfigure.OpenAiEmbeddingProperties;
import org.springframework.ai.model.tool.DefaultToolExecutionEligibilityPredicate;
import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.model.tool.ToolExecutionEligibilityPredicate;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author jackey
 * @description
 * @date 5/31/25 21:55
 */
@Configuration
public class AiChatModelConfiguration  {
    /**
     * openai 官方 ChatModel
     * @return
     */
    @Bean("openAiChatModel")
    @Primary
    public OpenAiChatModel openAiChatModel(OpenAiApi openAiApi,
                                           OpenAiConnectionProperties commonProperties,
                                           OpenAiChatProperties chatProperties, ObjectProvider<RestClient.Builder> restClientBuilderProvider,
                                           ObjectProvider<WebClient.Builder> webClientBuilderProvider, ToolCallingManager toolCallingManager,
                                           RetryTemplate retryTemplate, ResponseErrorHandler responseErrorHandler,
                                           ObjectProvider<ObservationRegistry> observationRegistry,
                                           ObjectProvider<ChatModelObservationConvention> observationConvention,
                                           ObjectProvider<ToolExecutionEligibilityPredicate> openAiToolExecutionEligibilityPredicate) {

        var chatModel = OpenAiChatModel.builder()
                .openAiApi(openAiApi)
                .defaultOptions(chatProperties.getOptions())
                .toolCallingManager(toolCallingManager)
                .toolExecutionEligibilityPredicate(
                        openAiToolExecutionEligibilityPredicate.getIfUnique(DefaultToolExecutionEligibilityPredicate::new))
                .retryTemplate(retryTemplate)
                .observationRegistry(observationRegistry.getIfUnique(() -> ObservationRegistry.NOOP))
                .build();

        observationConvention.ifAvailable(chatModel::setObservationConvention);

        return chatModel;
    }

    @Bean("deepSeekChatModel")
    public DeepSeekChatModel deepSeekChatModel(DeepSeekConnectionProperties commonProperties,
                                               DeepSeekChatProperties chatProperties, ObjectProvider<RestClient.Builder> restClientBuilderProvider,
                                               ObjectProvider<WebClient.Builder> webClientBuilderProvider, ToolCallingManager toolCallingManager,
                                               RetryTemplate retryTemplate, ResponseErrorHandler responseErrorHandler,
                                               ObjectProvider<ObservationRegistry> observationRegistry,
                                               ObjectProvider<ChatModelObservationConvention> observationConvention,
                                               ObjectProvider<ToolExecutionEligibilityPredicate> deepseekToolExecutionEligibilityPredicate) {

        var deepSeekApi = deepSeekApi(chatProperties, commonProperties,
                restClientBuilderProvider.getIfAvailable(RestClient::builder),
                webClientBuilderProvider.getIfAvailable(WebClient::builder), responseErrorHandler);

        var chatModel = DeepSeekChatModel.builder()
                .deepSeekApi(deepSeekApi)
                .defaultOptions(chatProperties.getOptions())
                .toolCallingManager(toolCallingManager)
                .toolExecutionEligibilityPredicate(deepseekToolExecutionEligibilityPredicate
                        .getIfUnique(DefaultToolExecutionEligibilityPredicate::new))
                .retryTemplate(retryTemplate)
                .observationRegistry(observationRegistry.getIfUnique(() -> ObservationRegistry.NOOP))
                .build();

        observationConvention.ifAvailable(chatModel::setObservationConvention);

        return chatModel;
    }

    @Bean("volcengineDeepSeekChatModel")
    public DeepSeekChatModel volcengineDeepSeekChatModel(DeepSeekConnectionProperties commonProperties,
                                               DeepSeekChatProperties chatProperties, ObjectProvider<RestClient.Builder> restClientBuilderProvider,
                                               ObjectProvider<WebClient.Builder> webClientBuilderProvider, ToolCallingManager toolCallingManager,
                                               RetryTemplate retryTemplate, ResponseErrorHandler responseErrorHandler,
                                               ObjectProvider<ObservationRegistry> observationRegistry,
                                               ObjectProvider<ChatModelObservationConvention> observationConvention,
                                               ObjectProvider<ToolExecutionEligibilityPredicate> deepseekToolExecutionEligibilityPredicate) {

        var deepSeekApi = volcenginedeepSeekApi(chatProperties, commonProperties,
                restClientBuilderProvider.getIfAvailable(RestClient::builder),
                webClientBuilderProvider.getIfAvailable(WebClient::builder), responseErrorHandler);

        var chatModel = DeepSeekChatModel.builder()
                .deepSeekApi(deepSeekApi)
                .defaultOptions(DeepSeekChatOptions.builder().model("ep-20250227224840-g82tx")
                        .temperature(0.5)
                        .build())
                .toolCallingManager(toolCallingManager)
                .toolExecutionEligibilityPredicate(deepseekToolExecutionEligibilityPredicate
                        .getIfUnique(DefaultToolExecutionEligibilityPredicate::new))
                .retryTemplate(retryTemplate)
                .observationRegistry(observationRegistry.getIfUnique(() -> ObservationRegistry.NOOP))
                .build();

        observationConvention.ifAvailable(chatModel::setObservationConvention);

        return chatModel;
    }

    /**
     * copied from OpenAiChatModelConfiguration
     * more often used for deriving other openai-compatible Api endpoints, such as volcengine DeepSeek. just use mutate()
     */

    @Bean(name = "openAiApi")
    @Primary
    public OpenAiApi openAiApi(OpenAiChatProperties chatProperties, OpenAiConnectionProperties commonProperties,
                               RestClient.Builder restClientBuilder, WebClient.Builder webClientBuilder,
                               ResponseErrorHandler responseErrorHandler) {

        OpenAIAutoConfigurationUtil.ResolvedConnectionProperties resolved = resolveConnectionProperties(
                commonProperties, chatProperties, "chat");

        return OpenAiApi.builder()
                .baseUrl(resolved.baseUrl())
                .apiKey(new SimpleApiKey(resolved.apiKey()))
                .headers(resolved.headers())
                .completionsPath(chatProperties.getCompletionsPath())
                .embeddingsPath(OpenAiEmbeddingProperties.DEFAULT_EMBEDDINGS_PATH)
                .restClientBuilder(restClientBuilder)
                .webClientBuilder(webClientBuilder)
                .responseErrorHandler(responseErrorHandler)
                .build();
    }

    @Bean("deepSeekApi")
    public DeepSeekApi deepSeekApi(DeepSeekChatProperties chatProperties,
                                    DeepSeekConnectionProperties commonProperties, RestClient.Builder restClientBuilder,
                                    WebClient.Builder webClientBuilder, ResponseErrorHandler responseErrorHandler) {

        String resolvedBaseUrl = StringUtils.hasText(chatProperties.getBaseUrl()) ? chatProperties.getBaseUrl()
                : commonProperties.getBaseUrl();
        Assert.hasText(resolvedBaseUrl, "DeepSeek base URL must be set");

        String resolvedApiKey = StringUtils.hasText(chatProperties.getApiKey()) ? chatProperties.getApiKey()
                : commonProperties.getApiKey();
        Assert.hasText(resolvedApiKey, "DeepSeek API key must be set");

        return DeepSeekApi.builder()
                .baseUrl(resolvedBaseUrl)
                .apiKey(new SimpleApiKey(resolvedApiKey))
                .completionsPath(chatProperties.getCompletionsPath())
                .betaPrefixPath(chatProperties.getBetaPrefixPath())
                .restClientBuilder(restClientBuilder)
                .webClientBuilder(webClientBuilder)
                .responseErrorHandler(responseErrorHandler)
                .build();
    }

    @Bean("volcenginedeepSeekApi")
    public DeepSeekApi volcenginedeepSeekApi(DeepSeekChatProperties chatProperties,
                                   DeepSeekConnectionProperties commonProperties, RestClient.Builder restClientBuilder,
                                   WebClient.Builder webClientBuilder, ResponseErrorHandler responseErrorHandler) {

        return DeepSeekApi.builder()
                .baseUrl("https://ark.cn-beijing.volces.com/api/v3")
                .apiKey(new SimpleApiKey("7e43ce30-73bc-442e-b769-b5fe3d5018d6"))
                .completionsPath("/chat/completions")
                .betaPrefixPath("no")
                .restClientBuilder(restClientBuilder)
                .webClientBuilder(webClientBuilder)
                .responseErrorHandler(responseErrorHandler)
                .build();
    }
}
