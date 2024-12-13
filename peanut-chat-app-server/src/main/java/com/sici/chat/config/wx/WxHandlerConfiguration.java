package com.sici.chat.config.wx;

import com.sici.chat.handler.wx.msg.handler.LogHandler;
import com.sici.chat.handler.wx.msg.handler.MsgHandler;
import com.sici.chat.handler.wx.msg.handler.ScanHandler;
import com.sici.chat.handler.wx.msg.handler.SubscribeHandler;
import lombok.AllArgsConstructor;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static me.chanjar.weixin.common.api.WxConsts.EventType.SUBSCRIBE;
import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType.EVENT;

/**
 * @projectName: peanut-chat-app
 * @package: com.sici.chat.config.wx
 * @author: 20148
 * @description: 微信消息处理器配置
 * @create-date: 12/12/2024 5:42 PM
 * @version: 1.0
 */

@Configuration
@AllArgsConstructor
public class WxHandlerConfiguration {
    private final LogHandler logHandler;
    private final SubscribeHandler subscribeHandler;
    private final ScanHandler scanHandler;
    private final MsgHandler msgHandler;

    @Bean
    public WxMpMessageRouter messageRouter(WxMpService wxMpService) {
        final WxMpMessageRouter newRouter = new WxMpMessageRouter(wxMpService);

        // 记录所有事件的日志 （异步执行）
        newRouter.rule().handler(this.logHandler).next();

        // 关注事件
        newRouter.rule().async(false).msgType(EVENT).event(SUBSCRIBE).handler(this.subscribeHandler).end();

        // 扫码事件
        newRouter.rule().async(false).msgType(EVENT).event(WxConsts.EventType.SCAN).handler(this.scanHandler).end();

        // 默认
        newRouter.rule().async(false).handler(this.msgHandler).end();

        return newRouter;
    }
}
