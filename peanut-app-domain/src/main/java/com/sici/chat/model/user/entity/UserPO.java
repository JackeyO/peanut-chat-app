package com.sici.chat.model.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.model.user.entity
 * @author: 20148
 * @description: user实体类
 * @create-date: 9/10/2024 5:50 PM
 * @version: 1.0
 */


@TableName("t_user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPO implements Serializable {
    @TableId(type = IdType.INPUT)
    private Long userId;
    private String nickName;
    private String trueName;
    private String avatar;
    private Integer sex;
    private Integer workCity;
    private Integer bornCity;
    private Date bornDate;
    private Date createTime;
    private Date updateTime;
}
