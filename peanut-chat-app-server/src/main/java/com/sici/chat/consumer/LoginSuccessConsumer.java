package com.sici.chat.consumer;

import javax.annotation.Resource;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import com.sici.chat.model.chat.wx.WxLoginSuccessMqDto;
import com.sici.chat.service.ws.WebSocketService;
import com.sici.common.constant.im.ChatMqConstant;

/**
 * @projectName: peanut-chat-app
 * @package: com.sici.chat.consumer
 * @author: 20148
 * @description: 登陆成功消费者
 * @create-date: 12/20/2024 3:52 PM
 * @version: 1.0
 */

@Component
@RocketMQMessageListener(topic = ChatMqConstant.LOGIN_SUCCESS_TOPIC, consumerGroup = ChatMqConstant.LOGIN_SUCCESS_GROUP)
public class LoginSuccessConsumer implements RocketMQListener<WxLoginSuccessMqDto> {
    @Resource
    private WebSocketService webSocketService;
    @Override
    public void onMessage(WxLoginSuccessMqDto wxLoginSuccessMqDto) {
        webSocketService.wxAuthorizeSuccess(wxLoginSuccessMqDto.getLoginCode(), wxLoginSuccessMqDto.getUser());
    }
}
