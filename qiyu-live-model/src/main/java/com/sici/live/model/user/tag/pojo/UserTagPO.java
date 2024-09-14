package com.sici.live.model.user.tag.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.model.user.tag.pojo
 * @author: 20148
 * @description: 用户标签实体类
 * @create-date: 9/14/2024 8:36 PM
 * @version: 1.0
 */

@TableName("t_user_tag")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserTagPO {
    @TableId(type = IdType.INPUT)
    private Long userId;
    @TableField(value = "tag_info_01")
    private Long tagInfo01;
    @TableField(value = "tag_info_02")
    private Long tagInfo02;
    @TableField(value = "tag_info_03")
    private Long tagInfo03;
    private Date createTime;
    private Date updateTime;
}
