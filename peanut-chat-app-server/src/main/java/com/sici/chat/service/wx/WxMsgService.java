package com.sici.chat.service.wx;

import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * @projectName: peanut-chat-app
 * @package: com.sici.chat.service
 * @author: 20148
 * @description: 处理微信的消息
 * @create-date: 12/5/2024 5:36 PM
 * @version: 1.0
 */

public interface WxMsgService {
    WxMpXmlOutMessage scan( WxMpXmlMessage wxMpXmlMessage);
    void authorize(WxOAuth2UserInfo userInfo);
}
