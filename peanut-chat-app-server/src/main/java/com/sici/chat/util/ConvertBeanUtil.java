package com.sici.chat.util;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;


/**
 * @author jackey
 * @description Bean转换工具类，用于对象间属性拷贝和类型转换
 * @date 5/23/25 16:05
 */
public class ConvertBeanUtil {

    /**
     * 单个对象转换
     * @param source 源对象
     * @param targetClass 目标类
     * @param <S> 源类型
     * @param <T> 目标类型
     * @return 转换后的对象
     */
    public static <S, T> T convertSingle(S source, Class<T> targetClass) {
        if (source == null) {
            return null;
        }

        T target;
        try {
            target = targetClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, target);
            return target;
        } catch (Exception e) {
            throw new RuntimeException("Convert error", e);
        }
    }

    /**
     * 转换对象列表
     * @param sourceList 源对象列表
     * @param targetClass 目标类
     * @param <S> 源类型
     * @param <T> 目标类型
     * @return 转换后的对象列表
     */
    public static <S, T> List<T> convertList(List<S> sourceList, Class<T> targetClass) {
        if (sourceList == null || sourceList.isEmpty()) {
            return Collections.emptyList();
        }

        return sourceList.stream()
                .map(source -> convertSingle(source, targetClass))
                .collect(Collectors.toList());
    }

    /**
     * 转换对象列表，使用自定义供应商创建目标对象
     * @param sourceList 源对象列表
     * @param targetSupplier 目标对象供应商
     * @param <S> 源类型
     * @param <T> 目标类型
     * @return 转换后的对象列表
     */
    public static <S, T> List<T> convertList(List<S> sourceList, Supplier<T> targetSupplier) {
        if (sourceList == null || sourceList.isEmpty()) {
            return Collections.emptyList();
        }

        List<T> targetList = new ArrayList<>(sourceList.size());
        for (S source : sourceList) {
            if (source == null) {
                continue;
            }
            T target = targetSupplier.get();
            BeanUtils.copyProperties(source, target);
            targetList.add(target);
        }
        return targetList;
    }

    /**
     * 转换对象并应用后处理函数
     * @param source 源对象
     * @param targetClass 目标类
     * @param afterProcess 后处理函数
     * @param <S> 源类型
     * @param <T> 目标类型
     * @return 转换后的对象
     */
    public static <S, T> T convertWithCallback(S source, Class<T> targetClass, java.util.function.BiConsumer<S, T> afterProcess) {
        if (source == null) {
            return null;
        }

        T target = convertSingle(source, targetClass);
        if (afterProcess != null) {
            afterProcess.accept(source, target);
        }
        return target;
    }

    /**
     * 转换对象列表并应用后处理函数
     * @param sourceList 源对象列表
     * @param targetClass 目标类
     * @param afterProcess 后处理函数
     * @param <S> 源类型
     * @param <T> 目标类型
     * @return 转换后的对象列表
     */
    public static <S, T> List<T> convertListWithCallback(List<S> sourceList, Class<T> targetClass, java.util.function.BiConsumer<S, T> afterProcess) {
        if (sourceList == null || sourceList.isEmpty()) {
            return Collections.emptyList();
        }

        List<T> targetList = new ArrayList<>(sourceList.size());
        for (S source : sourceList) {
            if (source == null) {
                continue;
            }
            T target = convertSingle(source, targetClass);
            if (afterProcess != null) {
                afterProcess.accept(source, target);
            }
            targetList.add(target);
        }
        return targetList;
    }
}
