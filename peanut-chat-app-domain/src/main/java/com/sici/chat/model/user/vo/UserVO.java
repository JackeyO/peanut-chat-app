package com.sici.chat.model.user.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.model.user.entity
 * @author: 20148
 * @description: user实体类
 * @create-date: 9/10/2024 5:50 PM
 * @version: 1.0
 */


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserVO implements Serializable {
    private Integer id;
    /**
     * 用户openId
     */
    private String openId;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 0:男，1：女
     */
    private Integer sex;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 国际啊
     */
    private String country;

    /**
     * 注册时间
     */
    private Date registerTime;

    /**
     * 头像
     */
    private String avatar;
}
