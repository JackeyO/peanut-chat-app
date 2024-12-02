package com.sici.common.constant.im;

/**
 * @projectName_ qiyu-live-app
 * @package_ com.sici.common.constant.im
 * @author_ 20148
 * @description_
 * @create-date_ 9/18/2024 4_20 PM
 * @version_ 1.0
 */

public class ImMqConstant {
    // 用户最新在线时间缓存记录消费者组
    public static final String IM_CORE_SERVER_USER_ONLINE_CACHE_SAVE_CONSUMER_GROUP = "im-core-server_user-online-cache-save_consumer-group";
    // 用户最新在线时间缓存记录-mq-主题
    public static final String IM_CORE_SERVER_USER_ONLINE_CACHE_SAVE_TOPIC = "im-core-server_user-online-cache-save_topic";


    // 用户最新在线时间缓存删除消费者组
    public static final String IM_CORE_SERVER_USER_ONLINE_CACHE_DELETE_CONSUMER_GROUP = "im-core-server_user-online-cache-delete_consumer-group";
    // 用户最新在线时间缓存删除-mq-主题
    public static final String IM_CORE_SERVER_USER_ONLINE_CACHE_DELETE_TOPIC = "im-core-server_user-online-cache-delete_topic";

    // 用户所连接IM服务器地址缓存记录--消费者组
    public static final String IM_CORE_SERVER_USER_IM_SERVER_ADDRESS_CACHE_SAVE_CONSUMER_GROUP = "im-core-server_user-im-server-address-cache-save_consumer-group";
    // 用户所连接IM服务器地缓存记录址-mq-主题
    public static final String IM_CORE_SERVER_USER_IM_SERVER_ADDRESS_CACHE_SAVE_TOPIC = "im-core-server_user-im-server-address-cache-save_topic";

    // 用户所连接IM服务器地址缓存删除--消费者组
    public static final String IM_CORE_SERVER_USER_IM_SERVER_ADDRESS_CACHE_DELETE_CONSUMER_GROUP = "im-core-server_user-im-server-address-cache-delete_consumer-group";
    // 用户所连接IM服务器地址缓存删除-mq-主题
    public static final String IM_CORE_SERVER_USER_IM_SERVER_ADDRESS_CACHE_DELETE_TOPIC = "im-core-server_user-im-server-address-cache-delete_topic";
    public static final String IM_CORE_SERVER_MESSAGE_RECEIVE_CONSUMER_GROUP = "im-core-server_message-receive_consumer-group";
    public static final String IM_CORE_SERVER_MESSAGE_RECEIVE_TOPIC = "im-core-server_message-receive_topic";
    public static final String IM_CORE_SERVER_MSG_ACK_DELAY_TOPIC = "im-core-server_msg-ack_topic";
    public static final String IM_CORE_SERVER_MSG_ACK_DELAY_CONSUMER_GROUP = "im-core-server_msg-ack_consumer-group";
}
