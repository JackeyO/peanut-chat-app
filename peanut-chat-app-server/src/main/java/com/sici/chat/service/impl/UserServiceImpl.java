package com.sici.chat.service.impl;

import cn.hutool.jwt.JWTUtil;
import com.sici.chat.dao.UserDao;
import com.sici.chat.model.user.entity.User;
import com.sici.chat.service.UserService;
import com.sici.chat.util.JwtUtil;
import io.jsonwebtoken.Jwt;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.swing.text.html.Option;
import java.util.Map;
import java.util.Optional;

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
        String token = JwtUtil.createUserToken(user);
        return token;
    }

    @Override
    public User authorize(String token) {
        Integer userId = JwtUtil.getUidFromToken(token);
        if (userId == null) {
            return null;
        }
        User user = userDao.getById(userId);
        return user;
    }
}