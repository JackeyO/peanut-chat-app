package com.sici.common.log.util;

import com.sici.common.log.constant.LogConstant;
import org.slf4j.Logger;

import java.lang.reflect.Field;

/**
 * @projectName: sici-leadnews
 * @package: com.sici.channel.log.util
 * @author: 20148
 * @description: 获取logger工具类
 * @create-date: 8/8/2024 4:45 PM
 * @version: 1.0
 */

public class LoggerUtil {
    /**
     * 获取某个类的Logger
     * @param clazz
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static Logger getLogger(Class clazz) {
        Logger logger = null;
        try {
            Field loggerField = clazz.getDeclaredField(LogConstant.LOGGER_NAME);
            loggerField.setAccessible(true);
            logger = (Logger) loggerField.get(null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return logger;
    }
}
