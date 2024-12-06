package com.sici.chat.builder;

import com.sici.chat.model.chat.message.vo.ChatMessageVo;
import com.sici.chat.model.chat.message.vo.LoginMessageVo;
import com.sici.chat.model.chat.message.vo.MessageVo;
import com.sici.chat.model.chat.message.vo.ScanMessageVo;
import com.sici.chat.model.ws.bo.ImMsg;
import com.sici.common.enums.chat.message.MessageTypeEnum;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.common.builder
 * @author: 20148
 * @description: 构建ImMsg
 * @create-date: 11/30/2024 4:09 PM
 * @version: 1.0
 */

public class ImMsgBuilder {
    public static ImMsg<ChatMessageVo> buildChatMessage(ChatMessageVo messageVo) {
        ImMsg<ChatMessageVo> imMsg = new ImMsg<>();
        MessageVo message = messageVo.getMessage();
        imMsg.setType(message.getType());
        imMsg.setMsgId(message.getId());
        imMsg.setData(messageVo);
        return imMsg;
    }

    public static ImMsg<ScanMessageVo> buildScanMessage(ScanMessageVo scanMessageVo) {
        ImMsg<ScanMessageVo> imMsg = new ImMsg<>();
        imMsg.setType(MessageTypeEnum.SCAN_SUCCESS.getType());
        imMsg.setData(scanMessageVo);
        return imMsg;
    }

    public static ImMsg buildLoginMessage(LoginMessageVo loginMessageVo) {
        ImMsg<LoginMessageVo> imMsg = new ImMsg<>();
        imMsg.setType(MessageTypeEnum.LOGIN_SUCCESS.getType());
        imMsg.setData(loginMessageVo);
        return null;
    }
}