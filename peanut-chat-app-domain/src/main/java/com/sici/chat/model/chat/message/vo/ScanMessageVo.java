package com.sici.chat.model.chat.message.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @projectName: peanut-chat-app
 * @package: com.sici.chat.model.chat.message.vo
 * @author: 20148
 * @description: 扫码后消息
 * @create-date: 12/6/2024 2:38 PM
 * @version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScanMessageVo extends CommonMessageVo {
    Boolean scanSuccess;
}