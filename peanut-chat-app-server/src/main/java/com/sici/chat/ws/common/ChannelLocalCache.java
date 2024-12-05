package com.sici.chat.ws.common;

import com.sici.chat.model.ws.bo.WsChannelInfo;
import io.netty.channel.Channel;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser;

import java.util.ArrayList;
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

    private static Map<Channel, WsChannelInfo> ONLINE_CHANNE_INFO_MAP = new ConcurrentHashMap<>();
    private static Map<Integer, CopyOnWriteArrayList<Channel>> ONLINE_CHANNE_MAP = new ConcurrentHashMap<>();

    public static List<Channel> getChannel(Integer userId) {
        return ONLINE_CHANNE_MAP.get(userId);
    }

    public static void addChannel(Integer userId, Channel channel) {
        ONLINE_CHANNE_MAP.computeIfAbsent(userId, key -> new CopyOnWriteArrayList<>())
                .add(channel);
    }

    public static WsChannelInfo getChannelInfo(Channel channel) {
        return ONLINE_CHANNE_INFO_MAP.get(channel);
    }

    public static void addChannelInfo(Channel channel, WsChannelInfo wsChannelInfo) {
        ONLINE_CHANNE_INFO_MAP.put(channel, wsChannelInfo);
    }
    public static void removeChannelInfo(Channel channel) {
        ONLINE_CHANNE_INFO_MAP.remove(channel);
    }
    public static void removeChannel(Channel channel) {
        WsChannelInfo channelInfo = getChannelInfo(channel);
        Integer userId = channelInfo.getUserId();
        if (userId != null) {
            List<Channel> channels = ONLINE_CHANNE_MAP.get(userId);
            channels.removeIf(ch -> Objects.equals(ch, channels));
        }
    }
    public static void removeChannelAndInfo(Channel channel) {
        removeChannel(channel);
        removeChannelInfo(channel);
    }
}
