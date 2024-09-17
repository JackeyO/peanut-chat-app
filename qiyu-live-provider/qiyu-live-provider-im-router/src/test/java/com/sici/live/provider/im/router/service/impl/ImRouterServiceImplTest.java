package com.sici.live.provider.im.router.service.impl;

import com.sici.live.provider.im.router.service.ImRouterService;
import jakarta.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ImRouterServiceImplTest {
    @Resource
    private ImRouterService imRouterService;
    @Test
    public void sendMsg() {
        imRouterService.sendMsg(1234L, "hello, send message from imRouter to imRouterHandler");
    }
}