package com.sici.framework.agent.response;

import com.sici.framework.agent.BaseChatAgent;
import com.sici.framework.agent.message.BaseChatMessage;
import com.sici.framework.agent.usage.RequestUsage;
import lombok.Builder;
import lombok.Data;

/**
 * 表示Agent处理消息后的响应结果
 */
@Data
@Builder
public class Response<T extends BaseChatMessage> {
    private T chatMessage;
//    private List<BaseAgentEvent> innerMessages;
    private RequestUsage usage;

    @Override
    public String toString() {
        return "Response{" +
                "chatMessage=" + chatMessage +
//                ", innerMessages=" + innerMessages +
                ", usage=" + usage +
                '}';
    }
}