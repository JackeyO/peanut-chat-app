package com.sici.bean;

import org.springframework.beans.BeanUtils;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.bean
 * @author: 20148
 * @description: bean类型转换
 * @create-date: 9/11/2024 4:35 PM
 * @version: 1.0
 */

public class ConvertBeanUtil {
    /**
     * 转换一个bean
     * @param source
     * @param clazz
     * @return
     */
    public static <S, T> T convertSingle(S source, Class<T> clazz) {
        T target = null;
        try {
            Constructor<T> constructor = clazz.getConstructor();
            target = constructor.newInstance();
            BeanUtils.copyProperties(source, target);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return target;
    }


    public static <S, T, U> List<T> convertCollection(List<S> source, Class<T> clazz) {
        List<T> target = new ArrayList<>();
        try {
            for (S s : source) {
                target.add(convertSingle(s, clazz));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return target;
    }
}
