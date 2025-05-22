package com.sici.chat.util;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.sici.chat.exception.BusinessException;

/**
 * 断言工具类
 */
public class AssertUtil {

    /**
     * 断言条件为真，如果不为真则抛出异常
     *
     * @param condition 条件
     * @param message   错误消息
     */
    public static void isTrue(boolean condition, String message) {
        isTrue(condition, 500, message);
    }

    /**
     * 断言条件为真，如果不为真则抛出异常
     *
     * @param condition 条件
     * @param code      错误码
     * @param message   错误消息
     */
    public static void isTrue(boolean condition, Integer code, String message) {
        if (!condition) {
            throw new BusinessException(code, message);
        }
    }

    /**
     * 断言条件为假，如果不为假则抛出异常
     *
     * @param condition 条件
     * @param message   错误消息
     */
    public static void isFalse(boolean condition, String message) {
        isFalse(condition, 500, message);
    }

    /**
     * 断言条件为假，如果不为假则抛出异常
     *
     * @param condition 条件
     * @param code      错误码
     * @param message   错误消息
     */
    public static void isFalse(boolean condition, Integer code, String message) {
        if (condition) {
            throw new BusinessException(code, message);
        }
    }

    /**
     * 断言对象不为空，如果为空则抛出异常
     *
     * @param object  对象
     * @param message 错误消息
     */
    public static void notNull(Object object, String message) {
        notNull(object, 500, message);
    }

    /**
     * 断言对象不为空，如果为空则抛出异常
     *
     * @param object  对象
     * @param code    错误码
     * @param message 错误消息
     */
    public static void notNull(Object object, Integer code, String message) {
        if (Objects.isNull(object)) {
            throw new BusinessException(code, message);
        }
    }

    /**
     * 断言字符串不为空，如果为空则抛出异常
     *
     * @param text    字符串
     * @param message 错误消息
     */
    public static void hasText(String text, String message) {
        hasText(text, 500, message);
    }

    /**
     * 断言字符串不为空，如果为空则抛出异常
     *
     * @param text    字符串
     * @param code    错误码
     * @param message 错误消息
     */
    public static void hasText(String text, Integer code, String message) {
        if (!StringUtils.hasText(text)) {
            throw new BusinessException(code, message);
        }
    }

    /**
     * 断言集合不为空，如果为空则抛出异常
     *
     * @param collection 集合
     * @param message    错误消息
     */
    public static void notEmpty(Collection<?> collection, String message) {
        notEmpty(collection, 500, message);
    }

    /**
     * 断言集合不为空，如果为空则抛出异常
     *
     * @param collection 集合
     * @param code       错误码
     * @param message    错误消息
     */
    public static void notEmpty(Collection<?> collection, Integer code, String message) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BusinessException(code, message);
        }
    }

    /**
     * 断言Map不为空，如果为空则抛出异常
     *
     * @param map     Map
     * @param message 错误消息
     */
    public static void notEmpty(Map<?, ?> map, String message) {
        notEmpty(map, 500, message);
    }

    /**
     * 断言Map不为空，如果为空则抛出异常
     *
     * @param map     Map
     * @param code    错误码
     * @param message 错误消息
     */
    public static void notEmpty(Map<?, ?> map, Integer code, String message) {
        if (CollectionUtils.isEmpty(map)) {
            throw new BusinessException(code, message);
        }
    }
} 