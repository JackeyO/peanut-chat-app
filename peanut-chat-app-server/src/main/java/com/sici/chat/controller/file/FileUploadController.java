package com.sici.chat.controller.file;

import com.alibaba.fastjson.JSON;
import com.sici.chat.model.chat.file.dto.FileUploadDto;
import com.sici.chat.model.chat.file.vo.FileUploadVo;
import com.sici.chat.service.file.FileUploadService;
import com.sici.common.result.ResponseResult;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author jackey
 * @description 文件上传
 * @date 5/27/25 15:40
 */
@RestController
@Slf4j
public class FileUploadController {
    @Resource
    private FileUploadService fileUploadService;
    @PostMapping("/file/upload")
    public ResponseResult<FileUploadVo> upload(@RequestParam("file") MultipartFile file,
                                               @RequestParam("fileUploadDto") String fileUploadDtoJSON) {
        FileUploadDto fileUploadDto = JSON.parseObject(fileUploadDtoJSON, FileUploadDto.class);
        FileUploadVo fileUploadVo = fileUploadService.uploadFile(fileUploadDto, file);

        return ResponseResult.okResult(fileUploadVo);
    }
}
