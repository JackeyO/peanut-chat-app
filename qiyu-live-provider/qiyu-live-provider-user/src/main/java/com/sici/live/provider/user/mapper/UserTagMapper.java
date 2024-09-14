package com.sici.live.provider.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sici.live.model.user.tag.pojo.UserTagPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.provider.user.mapper
 * @author: 20148
 * @description:
 * @create-date: 9/14/2024 8:39 PM
 * @version: 1.0
 */

@Mapper
public interface UserTagMapper extends BaseMapper<UserTagPO> {
    @Update("update t_user_tag set ${fieldName}=${fieldName} | #{tag} where user_id=#{userId}")
    int setTag(Long userId, String fieldName, long tag);

    @Update("update t_user_tag set ${fieldName}=${fieldName} &~ #{tag} where user_id=#{userId}")
    int cancelTag(Long userId, String fieldName, long tag);
}
