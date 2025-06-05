package com.sici.framework.agent.usage;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jackey
 * @description
 * @date 6/5/25 18:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestUsage {
    @JSONField(name = "prompt_tokens")
    private Integer promptTokens;
    @JSONField(name = "completion_tokens")
    private Integer completionTokens;
}
