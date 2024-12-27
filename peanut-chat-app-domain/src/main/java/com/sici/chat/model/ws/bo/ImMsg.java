package com.sici.chat.model.ws.bo;

import com.sici.chat.model.chat.message.vo.CommonMessageVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.ws.core.server.channel
 * @author: 20148
 * @description: IM消息体
 * @create-date: 9/16/2024 2:57 PM
 * @version: 1.0
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImMsg<T extends CommonMessageVo> implements Serializable {
    /**
     * 消息类型
     */
    Integer type;
    /**
     * 消息数据部分
     */
    T data;
}