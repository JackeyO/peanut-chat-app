package com.sici.chat.file;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author jackey
 * @description
 * @date 5/27/25 16:01
 */
public class FileUploadStrategyFactory {
    private FileUploadStrategyFactory() {
        // 私有构造函数，防止实例化
    }

    private static Map<String, AbstractFileUploadStrategy> fileUploadStrategyMap = new HashMap<>();

    public static AbstractFileUploadStrategy getFileUploadStrategy(String uploadType) {
        return fileUploadStrategyMap.get(uploadType);
    }

    public static void registerStrategy(AbstractFileUploadStrategy fileUploadStrategy) {
        if (Objects.nonNull(fileUploadStrategy)) {
            fileUploadStrategyMap.put(fileUploadStrategy.getUploadType(), fileUploadStrategy);
        }
    }
}
