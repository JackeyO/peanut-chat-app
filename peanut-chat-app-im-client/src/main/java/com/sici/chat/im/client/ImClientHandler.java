package com.sici.chat.im.client;

import com.alibaba.fastjson.JSON;
import com.sici.common.enums.im.ImMsgTypeEnums;
import com.sici.chat.model.ws.dto.ImMsgBody;
import com.sici.chat.model.ws.dto.ImMsgDto;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.ws.core.server
 * @author: 20148
 * @description:
 * @create-date: 9/16/2024 3:47 PM
 * @version: 1.0
 */

@Slf4j
public class ImClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ImMsg imMsg = (ImMsg) msg;

        byte[] body = imMsg.getBody();

        // 如果是业务消息，发送ACK确认
        if (imMsg.getCode() == ImMsgTypeEnums.IM_MSG_BIZ.getCode()) {
            ImMsgBody imMsgBody = JSON.parseObject(new String(body), ImMsgBody.class);

            imMsgBody.setData(JSON.toJSONString(
                    ImMsgDto.builder()
                            .content("ACK")
                            .build()
            ));

            ImMsg imMsgAck = ImMsgBuilder.buildAck(JSON.toJSONString(
                    imMsgBody
            ));

            ctx.writeAndFlush(imMsgAck);
        }
        log.info("[server response]: " + imMsg);
    }
}
