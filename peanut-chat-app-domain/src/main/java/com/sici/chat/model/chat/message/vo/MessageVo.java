package com.sici.chat.model.chat.message.vo;

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
 * @package: com.sici.chat.model.chat.message.vo
 * @author: 20148
 * @description:
 * @create-date: 11/30/2024 4:16 PM
 * @version: 1.0
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageVo {
    private Long id;
    private Long fromUid;
    private Long roomId;
    private Integer type;
    private String content;
    private Date sendTime;
    private Date updateTime;
    private Integer status;
}
