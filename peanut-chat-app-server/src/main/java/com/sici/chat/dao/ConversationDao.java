package com.sici.chat.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sici.chat.mapper.UserConversationMapper;
import com.sici.chat.mapper.UserFriendMapper;
import com.sici.chat.model.chat.conversation.entity.UserConversation;
import com.sici.chat.model.chat.friend.entity.UserFriend;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 20148
 * @description 针对表【user_friend】的数据库操作Service实现
 * @createDate 2024-11-25 18:13:49
 */
@Component
public class ConversationDao extends ServiceImpl<UserConversationMapper, UserConversation> {
    public UserConversation getByConversationId(Long req) {
        return getById(req);
    }
    public List<UserConversation> getByConversationId(List<Long> req) {
        return req.stream()
                .map(this::getByConversationId)
                .collect(Collectors.toList());
    }
}




