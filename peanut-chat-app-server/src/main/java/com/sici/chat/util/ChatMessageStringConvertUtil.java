package com.sici.chat.util;

import com.alibaba.fastjson2.JSON;
import com.sici.chat.model.chat.message.vo.ChatMessageVo;
import com.sici.chat.model.chat.message.vo.ImageMessageVo;
import com.sici.chat.model.chat.message.vo.NormalFileMessageVo;
import com.sici.chat.model.chat.message.vo.SoundMessageVo;
import com.sici.chat.model.chat.message.vo.VideoMessageVo;
import com.sici.common.enums.chat.message.MessageRespTypeEnum;

/**
 * @author jackey
 * @description 消息string转对象
 * @date 5/25/25 15:54
 */
public class ChatMessageStringConvertUtil {
    private ChatMessageStringConvertUtil() {
        // 私有构造函数，防止实例化
    }

    /**
     * 将消息字符串转换为 ChatMessageVo 对象。
     *
     * @param message 消息字符串
     * @return 转换后的对象
     */
    public static ChatMessageVo convertMessageStringToObject(String message) {
        ChatMessageVo chatMessageVo = JSON.parseObject(message, ChatMessageVo.class);
        Integer type = chatMessageVo.getMessage().getType();
        if (type == null) {
            throw new IllegalArgumentException("消息类型不能为空");
        }
        if (type.equals(MessageRespTypeEnum.TEXT.getType())) {
            return chatMessageVo;
        }
        if (type.equals(MessageRespTypeEnum.IMAGE.getType())) {
            return convertImageMessageStringToObject(message);
        }
        if (type.equals(MessageRespTypeEnum.AUDIO.getType())) {
            return convertAudioMessageStringToObject(message);
        }
        if (type.equals(MessageRespTypeEnum.VIDEO.getType())) {
            return convertVideoMessageStringToObject(message);
        }
        if (type.equals(MessageRespTypeEnum.NORMAL_FILE.getType())) {
            return convertNormalFileMessageStringToObject(message);
        }
        throw new IllegalArgumentException("<UNK> message type");
    }

    /**
     * 转图片消息对象
     */
    private static ImageMessageVo convertImageMessageStringToObject(String message) {
        return JSON.parseObject(message, ImageMessageVo.class);
    }

    /**
     * 转音频消息对象
     */
    private static SoundMessageVo convertAudioMessageStringToObject(String message) {
        return JSON.parseObject(message, SoundMessageVo.class);
    }

    /**
     * 转视频消息对象
     */
    private static VideoMessageVo convertVideoMessageStringToObject(String message) {
        return JSON.parseObject(message, VideoMessageVo.class);
    }


    /**
     * 转普通文件消息对象
     */
    private static NormalFileMessageVo convertNormalFileMessageStringToObject(String message) {
        return JSON.parseObject(message, NormalFileMessageVo.class);
    }

    public static String convertMessageObjectToString(ChatMessageVo chatMessageVo) {
        return JSON.toJSONString(chatMessageVo);
    }
}
