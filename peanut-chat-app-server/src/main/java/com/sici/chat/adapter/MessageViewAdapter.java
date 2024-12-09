package com.sici.chat.adapter;

import com.sici.chat.aggregator.*;
import com.sici.chat.model.chat.message.bo.aggregate.LoginMessageAggregateParam;
import com.sici.chat.model.chat.message.bo.aggregate.LoginQrCodeMessageAggregateParam;
import com.sici.chat.model.chat.message.bo.aggregate.ScanMessageAggregateParam;
import com.sici.chat.model.chat.message.entity.Message;
import com.sici.chat.model.chat.message.vo.*;
import com.sici.common.enums.chat.message.MessageRespTypeEnum;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.chat.adapter
 * @author: 20148
 * @description: 消息展示适配器(用于生成针对各类消息的展示物料)
 * @create-date: 12/2/2024 3:49 PM
 * @version: 1.0
 */

@Component
public class MessageViewAdapter {
    /**
     * 适配消息信息与接收者无关
     * @param message
     * @return
     */
    public ChatMessageVo adaptChatMessage(Message message) {
        return ((ChatMessageAggregator) MessageAggregatorFactory.getMessageAggregator(message.getType()))
                .aggregateAll(message);
    }

    /**
     * 需要根据接收者来适配消息信息
     *
     * @param message
     * @param receiverId
     * @return
     */
    public ChatMessageVo adaptChatMessageRelationToReceiver(Message message, Integer receiverId) {
        return ((ChatMessageAggregator) MessageAggregatorFactory.getMessageAggregator(message.getType()))
                .aggregateAllRelationToReceiver(message, receiverId);
    }

    /**
     * 需要根据接收者来适配消息信息
     * @param message
     * @param receiverIds
     * @return
     */
    public Map<Integer, CommonMessageVo> adaptChatMessageRelationToReceiver(Message message, List<Integer> receiverIds) {
        return ((ChatMessageAggregator) MessageAggregatorFactory.getMessageAggregator(message.getType()))
                .aggregateAllRelationToReceiver(message, receiverIds);
    }

    public LoginMessageVo adaptLoginMessage(LoginMessageAggregateParam loginMessageAggregateParam) {
        return ((LoginMessageAggregator) MessageAggregatorFactory.getMessageAggregator(MessageRespTypeEnum.LOGIN_SUCCESS.getType()))
                .aggregateAll(loginMessageAggregateParam);
    }

    public ScanMessageVo adaptScanMessage(ScanMessageAggregateParam scanMessageAggregateParam) {
        return ((ScanMessageAggregator) MessageAggregatorFactory.getMessageAggregator(MessageRespTypeEnum.SCAN_SUCCESS.getType()))
                .aggregateAll(scanMessageAggregateParam);
    }

    public LoginQrCodeMessageVo adaptLoginQrCodeMessage(LoginQrCodeMessageAggregateParam loginQrCodeMessageAggregateParam) {
        return ((LoginQrCodeMessageAggregator) MessageAggregatorFactory.getMessageAggregator(MessageRespTypeEnum.LOGIN_QR_CODE.getType()))
                .aggregateAll(loginQrCodeMessageAggregateParam);
    }
}