package com.sici.chat.service.conversation;

import com.sici.chat.model.chat.conversation.vo.ConversationVO;
import com.sici.chat.model.chat.cursor.dto.CursorPageDto;
import com.sici.chat.model.chat.cursor.vo.CursorPageVo;

/**
 * @author jackey
 * @description 会话业务
 * @date 5/24/25 16:03
 */
public interface ConversationService {
    CursorPageVo<ConversationVO> getConversationCursorPageList(Long userId, CursorPageDto cursorPageDto);
}
