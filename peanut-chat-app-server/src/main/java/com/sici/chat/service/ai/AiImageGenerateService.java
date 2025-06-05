package com.sici.chat.service.ai;

import com.sici.chat.model.chat.ai.vo.image.AiImageGenerateVo;

/**
 * @author jackey
 * @description
 * @date 6/5/25 21:50
 */
public interface AiImageGenerateService {
    /**
     * Generates an image based on the provided prompt.
     *
     * @param prompt the text prompt to generate the image from
     * @return the URL of the generated image
     */
    AiImageGenerateVo generateImage(String prompt);
}