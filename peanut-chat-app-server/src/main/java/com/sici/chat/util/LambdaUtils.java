package com.sici.chat.util;

import com.baomidou.mybatisplus.core.toolkit.support.LambdaMeta;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import lombok.SneakyThrows;
import org.apache.ibatis.reflection.property.PropertyNamer;

import java.lang.reflect.Field;

/**
 * @projectName: peanut-chat-app
 * @package: com.sici.chat.util
 * @author: 20148
 * @description: lambda工具类
 * @create-date: 12/11/2024 3:31 PM
 * @version: 1.0
 */

public class LambdaUtils {
    @SneakyThrows
    public static Class<?> getReturnType(SFunction func) {
        LambdaMeta lambdaMeta = com.baomidou.mybatisplus.core.toolkit.LambdaUtils.extract(func);
        Class<?> instantiatedClass = lambdaMeta.getInstantiatedClass();
        String methodName = lambdaMeta.getImplMethodName();

        String fieldName = PropertyNamer.methodToProperty(methodName);
        Field field = instantiatedClass.getField(fieldName);
        field.setAccessible(true);
        return field.getType();
    }
}
