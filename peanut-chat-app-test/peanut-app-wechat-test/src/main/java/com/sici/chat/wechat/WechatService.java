package com.sici.chat.wechat;

import jakarta.annotation.Resource;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * @projectName: peanut-chat-app
 * @package: com.sici.chat.wechat
 * @author: 20148
 * @description:
 * @create-date: 12/4/2024 4:29 PM
 * @version: 1.0
 */

@Service
public class WechatService {
    @Resource
    private WxMpService wxMpService;
    private static final Duration EXPIRE_TIME = Duration.ofHours(1);
    public WxMpQrCodeTicket createTicket(Integer code) throws WxErrorException {
        WxMpQrCodeTicket wxMpQrCodeTicket = wxMpService.getQrcodeService().qrCodeCreateTmpTicket(code, (int) EXPIRE_TIME.getSeconds());
        return wxMpQrCodeTicket;
    }
}
