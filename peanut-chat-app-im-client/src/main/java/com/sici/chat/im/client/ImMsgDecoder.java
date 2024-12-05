package com.sici.chat.im.client;

import com.sici.common.constant.im.ImConstant;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.ws.core.server.common
 * @author: 20148
 * @description: IM消息解码器
 * @create-date: 9/16/2024 3:03 PM
 * @version: 1.0
 */

@Component
public class ImMsgDecoder extends ByteToMessageDecoder {
    private final int BASE_LEN = 2 + 4 + 4;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // byteBuf magic校验
        if (in.readableBytes() > BASE_LEN) {
            // check magic
            short magic = in.readShort();
            if (magic != ImConstant.DEFAULT_MAGIC) {
                ctx.close();
                return;
            }
            // code
            int code = in.readInt();
            // len
            int len = in.readInt();
            if (in.readableBytes() != len) {
                ctx.close();
                return;
            }
            // body
            byte[] body = new byte[len];
            in.readBytes(body);
            ImMsg imMsg = ImMsgBuilder.build(magic, code, len, body);
            out.add(imMsg);
        }
    }
}
