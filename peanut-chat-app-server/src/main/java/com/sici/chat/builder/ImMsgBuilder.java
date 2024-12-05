package com.sici.chat.builder;

import com.sici.chat.model.chat.message.vo.ChatMessageVo;
import com.sici.chat.model.chat.message.vo.MessageVo;
import com.sici.chat.model.ws.bo.ImMsg;

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
}