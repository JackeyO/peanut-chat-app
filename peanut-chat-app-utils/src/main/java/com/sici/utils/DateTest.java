package com.sici.utils;

import cn.hutool.core.date.LocalDateTimeUtil;

import java.time.LocalDateTime;

/**
 * @author jackey
 * @description
 * @date 6/1/25 14:33
 */
public class DateTest {
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTimeUtil.now();
        System.out.println(now);
    }
}
