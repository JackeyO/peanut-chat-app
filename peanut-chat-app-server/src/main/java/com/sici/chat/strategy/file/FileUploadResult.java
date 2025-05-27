package com.sici.chat.strategy.file;

import com.qcloud.cos.model.PutObjectResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jackey
 * @description
 * @date 5/27/25 22:12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadResult {
    private String url;
    private PutObjectResult putObjectResult;
}
