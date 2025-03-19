package com.sici.chat.builder.user;

import com.sici.chat.model.user.entity.User;

import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;

/**
 * @projectName: peanut-chat-app
 * @package: com.sici.chat.builder.user
 * @author: 20148
 * @description:
 * @create-date: 12/5/2024 6:06 PM
 * @version: 1.0
 */

public class UserInfoBuilder {
    public static User buildFromWxAuthInfo(WxOAuth2UserInfo userInfo) {
        return User.builder()
                .openId(userInfo.getOpenid())
                .nickName(userInfo.getNickname())
                .sex(userInfo.getSex())
                .country(userInfo.getCountry())
                .province(userInfo.getProvince())
                .city(userInfo.getCity())
                .avatar(userInfo.getHeadImgUrl())
                .build();
    }
}
