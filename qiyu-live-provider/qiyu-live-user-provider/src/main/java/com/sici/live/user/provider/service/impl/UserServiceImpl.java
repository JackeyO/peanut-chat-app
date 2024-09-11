package com.sici.live.user.provider.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sici.live.model.user.pojo.UserPO;
import com.sici.live.user.provider.mapper.UserMapper;
import com.sici.live.user.provider.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.user.provider.service.impl
 * @author: 20148
 * @description:
 * @create-date: 9/10/2024 6:43 PM
 * @version: 1.0
 */

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserPO> implements UserService {
}
