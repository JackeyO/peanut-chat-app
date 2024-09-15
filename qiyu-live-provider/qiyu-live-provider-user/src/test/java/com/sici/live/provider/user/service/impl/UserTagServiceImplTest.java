package com.sici.live.provider.user.service.impl;

import com.sici.common.enums.user.tag.UserTagEnums;
import com.sici.common.result.ResponseResult;
import com.sici.live.provider.user.service.UserTagService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class UserTagServiceImplTest {

    @Resource
    private UserTagService userTagService;
    @Test
    public void setTag() {
//        ResponseResult<Boolean> result = userTagService.setTag(0L, UserTagEnums.IS_RIH_UESR);
//        ResponseResult<Boolean> result1 = userTagService.setTag(0L, UserTagEnums.IS_RIH_UESR);
//        System.out.println(result1.getData());
//        log.info("" + userTagService.containTag(0L, UserTagEnums.IS_RIH_UESR));

//        userTagService.setTag(0L, UserTagEnums.IS_VIP_USER);
//        log.info("" + userTagService.containTag(0L, UserTagEnums.IS_VIP_USER));

//        userTagService.setTag(0L, UserTagEnums.IS_OLD_USER);
//        log.info("" + userTagService.containTag(0L, UserTagEnums.IS_OLD_USER));
    }

    @Test
    public void cancelTag() {
        userTagService.cancelTag(0L, UserTagEnums.IS_VIP_USER);
    }

    @Test
    public void containTag() {
        System.out.println(userTagService.containTag(0L, UserTagEnums.IS_RIH_UESR).getData());
        System.out.println(userTagService.containTag(0L, UserTagEnums.IS_VIP_USER).getData());
        System.out.println(userTagService.containTag(0L, UserTagEnums.IS_OLD_USER).getData());
    }
}