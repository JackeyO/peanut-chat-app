package com.sici.chat.service.impl.wx;

import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sici.chat.builder.cache.UserOpenIdBindLoginCodeKeyBuilder;
import com.sici.chat.builder.user.UserInfoBuilder;
import com.sici.chat.builder.wx.TextBuilder;
import com.sici.chat.dao.UserDao;
import com.sici.chat.model.chat.wx.WxLoginSuccessMqDto;
import com.sici.chat.model.chat.wx.WxScanSuccessMqDto;
import com.sici.chat.model.user.entity.User;
import com.sici.chat.service.user.UserService;
import com.sici.chat.service.wx.WxMsgService;
import com.sici.common.constant.im.ChatMqConstant;
import com.sici.framework.redis.RedisUtils;
import com.sici.qiyu.live.framework.rmq.config.MQProducer;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * @projectName: peanut-chat-app
 * @package: com.sici.chat.service.impl
 * @author: 20148
 * @description: 处理微信的消息
 * @create-date: 12/5/2024 5:36 PM
 * @version: 1.0
 */

@Slf4j
@Service
public class WxMsgServiceImpl implements WxMsgService {
    private static final String URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
    @Value("${peanut.wx.mp.callback}")
    private String callback;
    @Resource
    private WxMpService wxMpService;
    @Resource
    private MQProducer mqProducer;
    @Resource
    private UserDao userDao;
    @Resource
    private UserService userService;
    @Resource
    private UserOpenIdBindLoginCodeKeyBuilder userOpenIdBindLoginCodeRedisRedisKeyBuilder;

    @Override
    public WxMpXmlOutMessage scan(WxMpXmlMessage wxMpXmlMessage) {
        // 之前生成的登陆码
        Integer loginCode = Integer.valueOf(wxMpXmlMessage.getEventKey());

        // 检查是否注册过，如果没有注册过就注册
        String openId = wxMpXmlMessage.getFromUser();
        User user = userDao.getByOpenId(openId);
        if (user == null) {
            user = User.builder()
                    .openId(openId)
                    .build();
            userService.register(user);
        }
        log.info("扫码成功, 登录码:{}, 用户信息:{}", loginCode, user);

        // 记录openId和loginCode对应关系
        String key = userOpenIdBindLoginCodeRedisRedisKeyBuilder.build(openId);
        RedisUtils.set(key, loginCode, 1, TimeUnit.HOURS);

        // 发送mq消息，通知前端扫码成功
        mqProducer.sendMsg(ChatMqConstant.WX_SCAN_SUCCESS_TOPIC, WxScanSuccessMqDto.builder().loginCode(loginCode).build());

        // 生成授权连接
        String skipUrl = String.format(URL, wxMpService.getWxMpConfigStorage().getAppId(), URLEncoder.encode(callback + "/wx/portal/public/callBack"));
        WxMpXmlOutMessage.TEXT().build();

        return new TextBuilder().build("请点击链接授权：<a href=\"" + skipUrl + "\">登录</a>", wxMpXmlMessage, wxMpService);
    }

    @Override
    public void authorize(WxOAuth2UserInfo userInfo) {
        User userRegistered = userDao.getByOpenId(userInfo.getOpenid());
        User user = UserInfoBuilder.buildFromWxAuthInfo(userInfo);
        BeanUtil.copyProperties(user, userRegistered, CopyOptions.create().setIgnoreNullValue(true));

        // 更新用户信息
        userDao.updateById(userRegistered);
        log.info("授权成功，用户信息:{}", userRegistered);

        // 获取扫码成功后保存的openid对应的登陆码
        Integer loginCode = RedisUtils.get(userOpenIdBindLoginCodeRedisRedisKeyBuilder.build(userInfo.getOpenid()), Integer.class);
        // 发送登录成功消息,告知前端登陆成功，并且更新ws相关信息
        mqProducer.sendMsg(ChatMqConstant.LOGIN_SUCCESS_TOPIC, WxLoginSuccessMqDto.builder().loginCode(loginCode).user(userRegistered).build());
    }
}