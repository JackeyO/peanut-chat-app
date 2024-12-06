package com.sici.chat.service.impl;

import com.sici.chat.dao.UserDao;
import com.sici.chat.model.user.entity.User;
import com.sici.chat.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 20148
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2024-12-05 17:27:51
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;
    @Override
    public Integer register(User user) {
        userDao.save(user);
        return user.getId();
    }

    @Override
    public String createToken(User user) {
        return null;
    }
}