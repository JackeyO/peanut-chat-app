package com.sici.chat.service.impl;

import com.sici.chat.adapter.MessageViewAdapter;
import com.sici.chat.builder.ImMsgBuilder;
import com.sici.chat.model.chat.message.bo.aggregate.LoginMessageAggregateParam;
import com.sici.chat.model.chat.message.bo.aggregate.ScanMessageAggregateParam;
import com.sici.chat.model.user.entity.User;
import com.sici.chat.model.ws.bo.ImMsg;
import com.sici.chat.model.ws.bo.WsChannelInfo;
import com.sici.chat.service.UserService;
import com.sici.chat.service.WebSocketService;
import com.sici.chat.ws.common.ChannelLocalCache;
import io.netty.channel.Channel;
import nonapi.io.github.classgraph.scanspec.ScanSpec;
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
    @Resource
    private MessageViewAdapter messageViewAdapter;
    @Resource
    private UserService userService;

    private void sendMsgToChannel(Channel channel, ImMsg imMsg) {
        channel.writeAndFlush(imMsg);
    }

    @Override
    public void sendMsg(ImMsg imMsg, Integer receiverId) {
        // TODO: 使用线程池异步任务来执行此操作  || created by 20148 at 12/4/2024 2:52 PM
        List<Channel> channelsToSend = ChannelLocalCache.getChannel(receiverId);
        channelsToSend.forEach(channel -> sendMsgToChannel(channel, imMsg));
    }

    @Override
    public void sendMsg(ImMsg imMsg, List<Integer> receiverId) {
        // TODO: 使用线程池异步任务来执行此操作  || created by 20148 at 12/4/2024 2:52 PM
        receiverId.forEach(id -> {
            List<Channel> channelsToSend = ChannelLocalCache.getChannel(id);
            channelsToSend.forEach(channel -> sendMsgToChannel(channel, imMsg));
        });
    }

    @Override
    public void offline(Channel channel) {
        ChannelLocalCache.removeOnlineChannelAndInfo(channel);
        // TODO: 发布用户下线事件  || created by 20148 at 12/4/2024 2:50 PM
//        applicationEventPublisher.publishEvent(new );
    }

    @Override
    public Boolean scanSuccess(Integer loginCode) {
        Channel waitLoginChannel = ChannelLocalCache.getWaitLoginChannel(loginCode);
        Boolean success = waitLoginChannel != null;
        sendMsgToChannel(waitLoginChannel,
                ImMsgBuilder.buildScanMessage(messageViewAdapter.adaptScanMessage(new ScanMessageAggregateParam(success))));
        return Boolean.FALSE;
    }

    @Override
    public Boolean loginSuccess(Integer loginCode, User user) {
        String token = userService.createToken(user);
        Channel waitLoginChannel = ChannelLocalCache.getWaitLoginChannel(loginCode);

        Integer userId = user.getId();
        // 把channel以及其用户信息放到在线信息里
        ChannelLocalCache.addOnlineChannelAndInfo(userId, waitLoginChannel, new WsChannelInfo(userId));
        // 从等待登录的channel里删除
        ChannelLocalCache.removeWaitLoginChannel(loginCode);

        sendMsgToChannel(waitLoginChannel,
                ImMsgBuilder.buildLoginMessage(messageViewAdapter.adaptLoginMessage(new LoginMessageAggregateParam(token, user))));
        return Boolean.TRUE;
    }

    @Override
    public void handlerLoginReq() {

    }
}
