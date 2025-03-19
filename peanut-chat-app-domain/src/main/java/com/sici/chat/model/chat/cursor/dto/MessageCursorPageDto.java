package com.sici.chat.model.chat.cursor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageCursorPageDto extends CursorPageDto {
    private Integer roomId;
}
