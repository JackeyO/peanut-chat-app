package com.sici.chat.model.chat.cursor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @projectName: peanut-chat-app
 * @package: com.sici.chat.model.chat.cursor
 * @author: 20148
 * @description: 游标分页请求
 * @create-date: 12/11/2024 2:50 PM
 * @version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CursorPageDto {
    private int pageSize = 10;
    /**
     * 游标，第一次请求游标为空，然后游标返回前端，后面每次请求带上游标
     */
    private String cursor;
}