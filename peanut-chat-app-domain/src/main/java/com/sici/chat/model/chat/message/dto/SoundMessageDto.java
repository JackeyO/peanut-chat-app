package com.sici.chat.model.chat.message.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.chat.model.chat.message.dto
 * @author: 20148
 * @description:
 * @create-date: 11/27/2024 5:48 PM
 * @version: 1.0
 */

@Data
@AllArgsConstructor
public class SoundMessageDto extends BaseFileMessageDto {
    /**
     * 总时长(以s为单位)
     */
    private Long time;
}