package com.sici.chat.util;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.sici.common.enums.code.AppHttpCodeEnum;
import com.sici.common.exception.BusinessException;

/**
 * 断言工具类
 */
public class AssertUtil {

    /**
     * 统一的异常抛出方法
     *
     * @param code    错误码
     * @param message 错误消息
     */
    private static void throwException(Integer code, String message) {
        throw new BusinessException(code, message);
    }

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
            throwException(code, message);
        }
    }

    /**
     * 断言条件为真，如果不为真则抛出异常
     *
     * @param condition 条件
     * @param codeEnum  错误码枚举
     */
    public static void isTrue(boolean condition, AppHttpCodeEnum codeEnum) {
        isTrue(condition, codeEnum.getCode(), codeEnum.getErrorMessage());
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
            throwException(code, message);
        }
    }

    /**
     * 断言条件为假，如果不为假则抛出异常
     *
     * @param condition 条件
     * @param codeEnum  错误码枚举
     */
    public static void isFalse(boolean condition, AppHttpCodeEnum codeEnum) {
        isFalse(condition, codeEnum.getCode(), codeEnum.getErrorMessage());
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
            throwException(code, message);
        }
    }

    /**
     * 断言对象不为空，如果为空则抛出异常
     *
     * @param object   对象
     * @param codeEnum 错误码枚举
     */
    public static void notNull(Object object, AppHttpCodeEnum codeEnum) {
        notNull(object, codeEnum.getCode(), codeEnum.getErrorMessage());
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
            throwException(code, message);
        }
    }

    /**
     * 断言字符串不为空，如果为空则抛出异常
     *
     * @param text     字符串
     * @param codeEnum 错误码枚举
     */
    public static void hasText(String text, AppHttpCodeEnum codeEnum) {
        hasText(text, codeEnum.getCode(), codeEnum.getErrorMessage());
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
            throwException(code, message);
        }
    }

    /**
     * 断言集合不为空，如果为空则抛出异常
     *
     * @param collection 集合
     * @param codeEnum   错误码枚举
     */
    public static void notEmpty(Collection<?> collection, AppHttpCodeEnum codeEnum) {
        notEmpty(collection, codeEnum.getCode(), codeEnum.getErrorMessage());
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
            throwException(code, message);
        }
    }

    /**
     * 断言Map不为空，如果为空则抛出异常
     *
     * @param map      Map
     * @param codeEnum 错误码枚举
     */
    public static void notEmpty(Map<?, ?> map, AppHttpCodeEnum codeEnum) {
        notEmpty(map, codeEnum.getCode(), codeEnum.getErrorMessage());
    }

    /**
     * 断言字符串不为空白，如果为空白则抛出异常
     *
     * @param content 字符串内容
     * @param message 错误消息
     */
    public static void notBlank(String content, String message) {
        notBlank(content, AppHttpCodeEnum.FAIL.getCode(), message);
    }

    /**
     * 断言字符串不为空白，如果为空白则抛出异常
     *
     * @param content 字符串内容
     * @param code    错误码
     * @param message 错误消息
     */
    public static void notBlank(String content, Integer code, String message) {
        if (!StringUtils.hasText(content)) {
            throwException(code, message);
        }
    }

    /**
     * 断言字符串不为空白，如果为空白则抛出异常
     *
     * @param content  字符串内容
     * @param codeEnum 错误码枚举
     */
    public static void notBlank(String content, AppHttpCodeEnum codeEnum) {
        notBlank(content, codeEnum.getCode(), codeEnum.getErrorMessage());
    }
}