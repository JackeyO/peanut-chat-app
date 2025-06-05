package com.sici.framework.agent;

import com.sici.framework.agent.context.MessageContext;
import com.sici.framework.agent.message.BaseChatMessage;
import com.sici.framework.agent.response.Response;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author jackey
 * @description
 * @date 6/4/25 20:31
 */
public interface Agent {
    String getName();
    String getDescription();
}