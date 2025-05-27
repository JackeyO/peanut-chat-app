package com.sici.common.enums.file;

/**
 * @author jackey
 * @description
 * @date 5/27/25 20:34
 */
public enum FileUploadTypeEnum {
    /**
     * 聊天文件（音频，视频，普通文件）
     */
    CHAT_FILE("chat_file", "聊天文件（音频，视频，普通文件）");

    private final String type;
    private final String description;

    FileUploadTypeEnum(String type, String description) {
        this.type = type;
        this.description = description;
    }
    public String getType() {
        return type;
    }
    public String getDescription() {
        return description;
    }
}