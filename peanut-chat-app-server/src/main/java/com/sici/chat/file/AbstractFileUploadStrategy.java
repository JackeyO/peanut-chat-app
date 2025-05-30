package com.sici.chat.file;

import com.qcloud.cos.model.PutObjectResult;
import com.sici.chat.util.CosUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * @author jackey
 * @description
 * @date 5/27/25 16:00
 */
@Slf4j
public abstract class AbstractFileUploadStrategy {
    public AbstractFileUploadStrategy() {
        // 注册到工厂中
        FileUploadStrategyFactory.registerStrategy(this);
    }

    abstract String getUploadType();

    public abstract void validateFile(MultipartFile file, Map<String, Object> params);
    public abstract String getFinalFileKey(MultipartFile file, Map<String, Object> params);
    public abstract FileUploadOptions getFileUploadOptions(MultipartFile file, Map<String, Object> params);

    protected String getFileType(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType.startsWith("image/")) return "images";
        if (contentType.startsWith("video/")) return "videos";
        return "files";
    }

    public FileUploadResult uploadFile(MultipartFile file, Map<String, Object> params) {
        // 验证文件
        validateFile(file, params);
        // 获取最终的文件key
        String finalFileKey = getFinalFileKey(file, params);
        // 获取上传选项
        FileUploadOptions fileUploadOptions = getFileUploadOptions(file, params);

        try {
            PutObjectResult putObjectResult = CosUtil.putObject(fileUploadOptions.getBucketName(), finalFileKey, file);
            log.info("upload file successfully!, uploadType:{}, file params:{}", getUploadType(), params);
            return FileUploadResult.builder()
                    .url(finalFileKey)
                    .putObjectResult(putObjectResult)
                    .build();
        } catch (IOException e) {
            log.error("上传文件到COS失败，key: {}, bucket: {}", finalFileKey, fileUploadOptions.getBucketName(), e);
            throw new RuntimeException(e);
        }
    }
}
