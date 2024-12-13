package com.sici.chat.model.chat.cursor.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @projectName: peanut-chat-app
 * @package: com.sici.chat.model.chat.cursor.vo
 * @author: 20148
 * @description: 游标分页返回内容
 * @create-date: 12/11/2024 2:54 PM
 * @version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CursorPageVo<T> {
    private Integer current;
    private Integer pageSize;
    private Integer recordSize;
    private String cursor;
    private List<T> records;
    private Boolean isLast;
}