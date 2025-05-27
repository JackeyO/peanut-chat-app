package com.sici.chat.model.chat.file.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author jackey
 * @description
 * @date 5/27/25 16:07
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadDto {
    private String fileName;
    private String uploadType;
    private Map<String, Object> params;
}
