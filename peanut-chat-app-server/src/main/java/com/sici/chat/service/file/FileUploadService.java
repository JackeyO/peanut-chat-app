package com.sici.chat.service.file;

import com.sici.chat.model.chat.file.dto.FileUploadDto;
import com.sici.chat.model.chat.file.vo.FileUploadVo;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author jackey
 * @description 文件上传
 * @date 5/27/25 15:44
 */
public interface FileUploadService {
    /**
     * 上传文件
     *
     * @param file 文件
     * @return 文件上传结果
     */
    FileUploadVo uploadFile(FileUploadDto fileUploadDto, MultipartFile file);
}
