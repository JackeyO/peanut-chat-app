package com.sici.chat.model.chat.message.bo.aggregate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @projectName: peanut-chat-app
 * @package: com.sici.chat.model.chat.message.bo.aggregate
 * @author: 20148
 * @description: 扫码消息聚合参数
 * @create-date: 12/6/2024 4:10 PM
 * @version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScanMessageAggregateParam {
    /**
     * 是否扫码成功
     */
    private Boolean success;
}
