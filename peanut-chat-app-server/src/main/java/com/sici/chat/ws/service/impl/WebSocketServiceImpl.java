package com.sici.chat.ws.service.impl;

import com.sici.chat.ws.common.ChannelLocalCache;
import com.sici.chat.ws.service.WebSocketService;
import com.sici.chat.model.ws.bo.ImMsg;
import io.netty.channel.Channel;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @projectName: peanut-chat-app
 * @package: com.sici.chat.ws.service.impl
 * @author: 20148
 * @description: ws
 * @create-date: 12/4/2024 11:34 AM
 * @version: 1.0
 */

@Service
public class WebSocketServiceImpl implements WebSocketService {
    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void sendMsg(ImMsg imMsg, Integer receiverId) {
        // TODO: 使用线程池异步任务来执行此操作  || created by 20148 at 12/4/2024 2:52 PM
        List<Channel> channelsToSend = ChannelLocalCache.getChannel(receiverId);
        channelsToSend.forEach(channel -> channel.writeAndFlush(imMsg));
    }

    @Override
    public void sendMsg(ImMsg imMsg, List<Integer> receiverId) {
        // TODO: 使用线程池异步任务来执行此操作  || created by 20148 at 12/4/2024 2:52 PM
        receiverId.forEach(id -> {
            List<Channel> channelsToSend = ChannelLocalCache.getChannel(id);
            channelsToSend.forEach(channel -> channel.writeAndFlush(imMsg));
        });
    }

    @Override
    public void offline(Channel channel) {
        ChannelLocalCache.removeChannelAndInfo(channel);
        // TODO: 发布用户下线事件  || created by 20148 at 12/4/2024 2:50 PM
//        applicationEventPublisher.publishEvent(new );
    }
}
