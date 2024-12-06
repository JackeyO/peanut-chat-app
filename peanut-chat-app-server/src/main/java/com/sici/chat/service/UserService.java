package com.sici.chat.service;


import com.sici.chat.model.user.entity.User;

/**
* @author 20148
* @description 针对表【user】的数据库操作Service
* @createDate 2024-12-05 17:27:51
*/
public interface UserService {

    Integer register(User user);

    String createToken(User user);
}
