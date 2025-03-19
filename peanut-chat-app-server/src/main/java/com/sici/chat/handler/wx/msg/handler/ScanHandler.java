package com.sici.chat.handler.wx.msg.handler;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.sici.chat.service.wx.WxMsgService;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

@Component
public class ScanHandler extends AbstractHandler {
    @Resource
    private WxMsgService wxMsgService;
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMpXmlMessage, Map<String, Object> map,
                                    WxMpService wxMpService, WxSessionManager wxSessionManager) throws WxErrorException {
        // 扫码事件处理
        return wxMsgService.scan(wxMpXmlMessage);
    }
}