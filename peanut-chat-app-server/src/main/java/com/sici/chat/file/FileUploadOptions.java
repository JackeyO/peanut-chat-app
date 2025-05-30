package com.sici.chat.file;

import com.qcloud.cos.model.ObjectMetadata;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jackey
 * @description
 * @date 5/27/25 21:04
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadOptions {
    /**
     * 桶
     */
    private String bucketName;

    private ObjectMetadata objectMetadata;
}
