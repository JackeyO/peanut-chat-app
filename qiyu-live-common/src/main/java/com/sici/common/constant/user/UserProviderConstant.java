package com.sici.common.constant.user;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.common.constant.user
 * @author: 20148
 * @description: user provider RPC服务相关常量
 * @create-date: 9/11/2024 4:58 PM
 * @version: 1.0
 */

public class UserProviderConstant {
    // 用户表片键取模值
    public static final int USER_TABLE_KEY_MOD = 100;
    // 用户信息缓存过期时间
    public static final int USER_INFO_CACHE_EXPIRE = 60;


    // 用户缓存信息删除相关主题名
    public static final String USER_INFO_CACHE_DELETE_TOPIC = "user-info-cache-delete";
    // 用户标签缓存删除主题名
    public static final String USER_TAG_CACHE_DELETE_TOPIC = "user-tag-cache-delete";


    // 用户信息缓存消费者组
    public static final String USER_INFO_CONSUMER_GROUP = "qiyu-live-user-info";
    public static final String USER_TAG_CONSUMER_GROUP = "qiyu-live-user-tag";
}
