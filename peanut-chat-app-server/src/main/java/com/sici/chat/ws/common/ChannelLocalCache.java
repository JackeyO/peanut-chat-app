package com.sici.chat.ws.common;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.sici.chat.model.ws.bo.WsChannelInfo;
import com.sici.chat.ws.common.util.ChannelAttrUtil;
import io.netty.channel.Channel;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.ws.core.server.common
 * @author: 20148
 * @description:
 * @create-date: 9/16/2024 8:12 PM
 * @version: 1.0
 */

public class ChannelLocalCache {
    /**
     * 当前im服务对外暴露的地址
     */
    private static String SERVER_ADDRESS = "";

    public static String getServerAddress() {
        return SERVER_ADDRESS;
    }

    public static void setServerAddress(String serverAddress) {
        SERVER_ADDRESS = serverAddress;
    }

    private static final Duration EXPIRE_TIME = Duration.ofHours(1);
    private static final Long MAX_MUM_SIZE = 10000L;
    /**
     * 登陆码和Channel对应关系
     */
    public static final Cache<Integer, Channel> WAIT_LOGIN_CHANNEL_MAP = Caffeine.newBuilder()
            .expireAfterWrite(EXPIRE_TIME)
            .maximumSize(MAX_MUM_SIZE)
            .build();

    private static Map<Channel, WsChannelInfo> ONLINE_CHANNE_INFO_MAP = new ConcurrentHashMap<>();
    /**
     * 用户id和Channel对应关系
     */
    private static Map<Integer, CopyOnWriteArrayList<Channel>> ONLINE_CHANNE_MAP = new ConcurrentHashMap<>();

    public static List<Channel> getChannel(Integer userId) {
        return ONLINE_CHANNE_MAP.get(userId);
    }

    public static void addOnlineChannel(Integer userId, Channel channel) {
        ChannelAttrUtil.setUserId(channel, ChannelAttr.USER_ID, userId);
        ONLINE_CHANNE_MAP.computeIfAbsent(userId, key -> new CopyOnWriteArrayList<>())
                .add(channel);
    }

    public static WsChannelInfo getOnlineChannelInfo(Channel channel) {
        return ONLINE_CHANNE_INFO_MAP.get(channel);
    }

    public static void addOnlineChannelInfo(Channel channel, WsChannelInfo wsChannelInfo) {
        ONLINE_CHANNE_INFO_MAP.put(channel, wsChannelInfo);
    }
    public static void addOnlineChannelAndInfo(Integer userId, Channel channel, WsChannelInfo wsChannelInfo) {
        addOnlineChannel(userId, channel);
        addOnlineChannelInfo(channel, wsChannelInfo);
    }
    public static void removeOnlineChannelInfo(Channel channel) {
        ONLINE_CHANNE_INFO_MAP.remove(channel);
    }
    public static void removeOnlineChannel(Channel channel) {
        WsChannelInfo channelInfo = getOnlineChannelInfo(channel);
        Integer userId = channelInfo.getUserId();
        if (userId != null) {
            List<Channel> channels = ONLINE_CHANNE_MAP.get(userId);
            channels.removeIf(ch -> Objects.equals(ch, channels));
        }
    }
    public static void removeOnlineChannelAndInfo(Channel channel) {
        removeOnlineChannel(channel);
        removeOnlineChannelInfo(channel);
    }

    public static Channel getWaitLoginChannel(Integer loginCode) {
        return WAIT_LOGIN_CHANNEL_MAP.get(loginCode, key -> null);
    }
    public static void addWaitLoginChannel(Integer loginCode, Channel channel) {
        WAIT_LOGIN_CHANNEL_MAP.put(loginCode, channel);
    }
    public static void removeWaitLoginChannel(Integer loginCode) {
        WAIT_LOGIN_CHANNEL_MAP.invalidate(loginCode);
    }
}
