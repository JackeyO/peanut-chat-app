package com.sici.chat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sici.chat.model.chat.conversation.entity.UserConversation;
import org.apache.ibatis.annotations.Mapper;

/**
* @author jackey
* @description 针对表【user_conversation】的数据库操作Mapper
* @createDate 2025-05-23 15:08:56
* @Entity com.sici.chat.model.chat.conversation.entity.UserConversation
*/
@Mapper
public interface UserConversationMapper extends BaseMapper<UserConversation> {

}




