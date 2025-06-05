package com.sici.chat.service.ai.dashscope;

import com.alibaba.dashscope.aigc.imagesynthesis.ImageSynthesis;
import com.alibaba.dashscope.aigc.imagesynthesis.ImageSynthesisParam;
import com.alibaba.dashscope.aigc.imagesynthesis.ImageSynthesisResult;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.sici.chat.config.dashscope.properties.DashScopeImageProperties;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author jackey
 * @description
 * @date 6/5/25 22:27
 */
@Service
public class DashScopeImageService {
    @Resource
    private DashScopeImageProperties dashScopeImageProperties;

    public ImageSynthesisResult generateImage(String prompt) {
        return generateImage(prompt, dashScopeImageProperties.getModel());
    }

    public ImageSynthesisResult generateImage(String prompt, String model) {
        String taskId = createAsyncTask(prompt, "1024*1024", model);
        return waitAsyncTask(taskId);
    }

    private String createAsyncTask(String prompt) {
        return createAsyncTask(prompt, "1024*1024", "wanx2.1-t2i-turbo");
    }

    /**
     * 创建异步任务
     *
     * @return taskId
     */
    private String createAsyncTask(String prompt, String size, String model) {
        ImageSynthesisParam param =
                ImageSynthesisParam.builder()
                        .apiKey(dashScopeImageProperties.getApiKey())
                        .model(model)
                        .prompt(prompt)
                        .n(1)
                        .size(size)
                        .build();

        ImageSynthesis imageSynthesis = new ImageSynthesis();
        ImageSynthesisResult result = null;
        try {
            result = imageSynthesis.asyncCall(param);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        String taskId = result.getOutput().getTaskId();
        return taskId;
    }


    /**
     * 等待异步任务结束
     *
     * @param taskId 任务id
     */
    private ImageSynthesisResult waitAsyncTask(String taskId) {
        ImageSynthesis imageSynthesis = new ImageSynthesis();
        ImageSynthesisResult result = null;
        try {
            //如果已经在环境变量中设置了 DASHSCOPE_API_KEY，wait()方法可将apiKey设置为null
            result = imageSynthesis.wait(taskId, null);
        } catch (ApiException | NoApiKeyException e) {
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }
}
