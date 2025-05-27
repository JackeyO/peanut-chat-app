package com.sici.chat.strategy.file;

import com.sici.chat.util.AssertUtil;
import com.sici.common.constant.cos.ChatCosConstant;
import com.sici.common.enums.code.AppHttpCodeEnum;
import com.sici.common.enums.file.FileUploadTypeEnum;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * @author jackey
 * @description 聊天文件上传策略
 * @date 5/27/25 20:29
 */
@Component
public class ChatFileUploadStrategy extends AbstractFileUploadStrategy{
    @Override
    String getUploadType() {
        return FileUploadTypeEnum.CHAT_FILE.getType();
    }

    @Override
    public String getFinalFileKey(MultipartFile file, Map<String, Object> params) {
        Long roomId = Long.valueOf(params.get(ChatCosConstant.CHAT_FILE_ROOM_ID_ATTRIBUTE).toString());

        // get now from Calendar
        String datePath = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String fileType = super.getFileType(file);

        return String.format("chat/room_%d/%s/%s/%s", roomId, fileType, datePath, file.getOriginalFilename());
    }

    @Override
    public void validateFile(MultipartFile file, Map<String, Object> params) {
        // 聊天文件验证逻辑：大小、格式等
        AssertUtil.isTrue(file.getSize() <= 10 * 1024 * 1024,
                AppHttpCodeEnum.PARAM_INVALID.getCode(), "文件大小不能超过10MB");


        // 验证roomId参数
        Long roomId = Long.valueOf(params.get(ChatCosConstant.CHAT_FILE_ROOM_ID_ATTRIBUTE).toString());
        AssertUtil.notNull(roomId, AppHttpCodeEnum.CHAT_FILE_NEED_ROOM_ID);
    }

    @Override
    public FileUploadOptions getFileUploadOptions(MultipartFile file, Map<String, Object> params) {
        FileUploadOptions fileUploadOptions = new FileUploadOptions();
        fileUploadOptions.setBucketName(ChatCosConstant.CHAT_FILE_BUCKET_NAME);
        return fileUploadOptions;
    }
}

