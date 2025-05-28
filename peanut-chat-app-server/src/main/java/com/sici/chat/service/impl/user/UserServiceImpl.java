package com.sici.chat.service.impl.user;

import java.util.Date;

import com.sici.chat.model.user.vo.UserSearchVo;
import org.springframework.stereotype.Service;

import com.sici.chat.dao.UserDao;
import com.sici.chat.model.user.dto.UserProfileDto;
import com.sici.chat.model.user.entity.User;
import com.sici.chat.model.user.vo.UserVO;
import com.sici.chat.service.user.UserService;
import com.sici.chat.util.ConvertBeanUtil;
import com.sici.chat.util.JwtUtil;
import com.sici.common.enums.code.AppHttpCodeEnum;
import com.sici.common.exception.BusinessException;

import jakarta.annotation.Resource;

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
    public Long register(User user) {
        user.setRegisterTime(new Date());
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
        Long userId = JwtUtil.getUidFromToken(token);
        if (userId == null) {
            return null;
        }
        User user = userDao.getById(userId);
        return user;
    }

    @Override
    public UserVO profile(UserProfileDto userProfileDto) {
        User user = userDao.getById(userProfileDto.getUserId());
        if (user == null) {
            throw new BusinessException(AppHttpCodeEnum.USER_NOT_FOUND.getCode(), AppHttpCodeEnum.USER_NOT_FOUND.getErrorMessage());
        }
        UserVO userVO = ConvertBeanUtil.convertSingle(user, UserVO.class);
        return userVO;
    }

    @Override
    public UserSearchVo searchUser(String keyword) {
        // TODO 如果输入的keyword为纯数字，则额外走一遍根据id查询 - Su Xiao Wen - 5/28/25 17:14

        // TODO 定义用户ES索引结构，全量以及增量索引写入。根据ES查用户 - Su Xiao Wen - 5/28/25 17:13

        UserSearchVo userSearchVo = new UserSearchVo();

        return userSearchVo;
    }
}