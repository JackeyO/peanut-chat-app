package com.sici.common.constant.im;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.common.constant.im
 * @author: 20148
 * @description:
 * @create-date: 9/16/2024 3:06 PM
 * @version: 1.0
 */

public class ImConstant {
    // 默认魔法值
    public static final short DEFAULT_MAGIC = 18673;
    // 默认心跳间隔
    public static final int DEFAULT_HEART_BEAT_INTERVAL = 30000;
    // im 业务消息主题
    public static final String IM_BIZ_MESSAGE_TOPIC = "im";


    // dubbo对外暴露的ip
    public static final String DUBBO_REGISTRY_IP_ENV_NAME = "DUBBO_REGISTRY_IP";
    // dubbo对外暴露的端口
    public static final String DUBBO_REGISTRY_PORT_ENV_NAME = "DUBBO_REGISTRY_PORT";

    // im服务对外部暴露的服务地址--缓存key前缀
    public static final String IM_CORE_SERVER_ADDRESS_CACHE_KEY_PREFIX = "im-core-server:bind:address:";


    // IM 消息重试次数上限
    public static final int IM_MSG_RETRY_TIMES = 3;
}