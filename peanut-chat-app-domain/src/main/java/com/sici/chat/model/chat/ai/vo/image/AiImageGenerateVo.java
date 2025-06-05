package com.sici.chat.model.chat.ai.vo.image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jackey
 * @description
 * @date 6/5/25 21:52
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AiImageGenerateVo {
    private String size;
    private String url;
}