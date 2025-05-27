package com.sici.chat.model.chat.file.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jackey
 * @description
 * @date 5/27/25 15:42
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadVo {
    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件访问地址
     */
    private String fileUrl;
}
