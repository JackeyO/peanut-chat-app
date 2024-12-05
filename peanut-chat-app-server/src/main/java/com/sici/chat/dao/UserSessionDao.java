package com.sici.chat.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sici.chat.mapper.UserSessionMapper;
import com.sici.chat.model.chat.session.entity.UserSession;
import org.springframework.stereotype.Component;

/**
 * @author 20148
 * @description 针对表【user_session】的数据库操作Service实现
 * @createDate 2024-11-25 18:13:49
 */
@Component
public class UserSessionDao extends ServiceImpl<UserSessionMapper, UserSession> {
}