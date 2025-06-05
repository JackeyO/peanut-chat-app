package com.sici.chat.model.chat.ai.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jackey
 * @description
 * @date 6/6/25 01:15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AiContentRewriteVo {
    private String originalContent;
    private String rewrittenContent;
    private String rewriteModelName;
}
