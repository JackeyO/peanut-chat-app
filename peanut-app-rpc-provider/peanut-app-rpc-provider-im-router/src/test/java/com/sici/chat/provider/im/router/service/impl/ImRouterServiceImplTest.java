package com.sici.chat.provider.im.router.service.impl;

import com.sici.chat.provider.im.router.service.ImRouterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ImRouterServiceImplTest {
    @Resource
    private ImRouterService imRouterService;
    @Test
    public void sendMsg() {
//        imRouterService.pushMsg( );
    }
}