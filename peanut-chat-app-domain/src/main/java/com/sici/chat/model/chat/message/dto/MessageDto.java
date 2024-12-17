package com.sici.chat.model.chat.message.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.sici.chat.model.chat.message.bo.MessageExtra;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
@NoArgsConstructor
public class MessageDto {
    private Integer fromUid;
    private Integer roomId;
    private Integer type;
    private Date sendTime;
    private Date updateTime;
    private Integer status;
    private String extra;
}
