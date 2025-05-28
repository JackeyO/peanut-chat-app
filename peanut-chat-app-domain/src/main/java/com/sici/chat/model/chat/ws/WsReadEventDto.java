package com.sici.chat.model.chat.ws;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jackey
 * @description
 * @date 5/28/25 12:11
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WsReadEventDto {
    /**
     * 对话id
     */
    private Long conversationId;

    /**
     * read时间
     */
    private Long readTime;
}
