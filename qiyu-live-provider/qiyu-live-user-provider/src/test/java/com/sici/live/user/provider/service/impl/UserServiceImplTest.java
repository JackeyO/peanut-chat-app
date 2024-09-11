package com.sici.live.user.provider.service.impl;

import com.sici.common.result.ResponseResult;
import com.sici.live.model.user.pojo.UserPO;
import com.sici.live.model.user.vo.UserVO;
import com.sici.live.user.provider.service.UserService;
import jakarta.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceImplTest {
    @Resource
    private UserService userService;
    @Test
    public void getUser() {
        ResponseResult<UserVO> user = userService.getUser(0L);
    }

    @Test
    public void getUsers() {
        ArrayList<Long> list = new ArrayList<>();
        for (int i = 0; i < 3; i ++) {
            list.add((long) i);
        }

        ResponseResult<List<UserVO>> users = userService.getUsers(list);
    }
}