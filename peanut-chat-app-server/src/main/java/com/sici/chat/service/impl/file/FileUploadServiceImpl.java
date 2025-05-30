package com.sici.chat.service.impl.file;

import com.sici.chat.model.chat.file.dto.FileUploadDto;
import com.sici.chat.model.chat.file.vo.FileUploadVo;
import com.sici.chat.service.file.FileUploadService;
import com.sici.chat.file.AbstractFileUploadStrategy;
import com.sici.chat.file.FileUploadResult;
import com.sici.chat.file.FileUploadStrategyFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author jackey
 * @description 文件上传
 * @date 5/27/25 15:45
 */
@Service
@Slf4j
public class FileUploadServiceImpl implements FileUploadService {
    @Override
    public FileUploadVo uploadFile(FileUploadDto fileUploadDto, MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        long size = file.getSize();

        AbstractFileUploadStrategy fileUploadStrategy = FileUploadStrategyFactory.getFileUploadStrategy(fileUploadDto.getUploadType());

        FileUploadResult fileUploadResult = fileUploadStrategy.uploadFile(file, fileUploadDto.getParams());

        FileUploadVo fileUploadVo = FileUploadVo.builder()
                .fileType(suffix)
                .fileSize(size)
                .fileName(originalFilename)
                .fileUrl(fileUploadResult.getUrl())
                .build();
        return fileUploadVo;
    }
}
