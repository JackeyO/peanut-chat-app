package com.sici.chat.consumer;

import javax.annotation.Resource;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import com.sici.chat.model.chat.wx.WxScanSuccessMqDto;
import com.sici.chat.service.ws.WebSocketService;
import com.sici.common.constant.im.ChatMqConstant;

/**
 * @projectName: peanut-chat-app
 * @package: com.sici.chat.consumer
 * @author: 20148
 * @description: 扫码成功消费者
 * @create-date: 12/20/2024 3:48 PM
 * @version: 1.0
 */

@Component
@RocketMQMessageListener(topic = ChatMqConstant.WX_SCAN_SUCCESS_TOPIC, consumerGroup = ChatMqConstant.WX_SCAN_SUCCESS_GROUP)
public class ScanSuccessConsumer implements RocketMQListener<WxScanSuccessMqDto> {
    @Resource
    private WebSocketService webSocketService;
    @Override
    public void onMessage(WxScanSuccessMqDto wxScanSuccessMqDto) {
        webSocketService.wxScanSuccess(wxScanSuccessMqDto.getLoginCode());
    }
}
