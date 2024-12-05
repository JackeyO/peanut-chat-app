package com.sici.chat.wechat;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class WechatServiceTest {
    @Resource
    private WechatService wechatService;
    @Test
    void createTicket() throws WxErrorException {
        WxMpQrCodeTicket ticket = wechatService.createTicket(100);
    }
}