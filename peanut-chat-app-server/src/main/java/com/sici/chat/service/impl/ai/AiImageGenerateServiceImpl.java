package com.sici.chat.service.impl.ai;

import com.alibaba.dashscope.aigc.imagesynthesis.ImageSynthesisResult;
import com.sici.chat.model.chat.ai.vo.AiImageGenerateVo;
import com.sici.chat.service.ai.AiImageGenerateService;
import com.sici.chat.service.ai.dashscope.DashScopeImageService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author jackey
 * @description
 * @date 6/5/25 21:50
 */
@Service
public class AiImageGenerateServiceImpl implements AiImageGenerateService {
    @Resource
    private DashScopeImageService dashScopeImageService;

    @Override
    public AiImageGenerateVo generateImage(String prompt) {
        ImageSynthesisResult imageSynthesisResult = dashScopeImageService.generateImage(prompt);

        Map<String, String> resultMap = imageSynthesisResult.getOutput().getResults().get(0);
        AiImageGenerateVo aiImageGenerateVo = new AiImageGenerateVo();
        aiImageGenerateVo.setUrl(resultMap.get("url"));
        aiImageGenerateVo.setSize("1024*1024");
        return aiImageGenerateVo;
    }
}
