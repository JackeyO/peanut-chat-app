package com.sici.live.user.provider.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sici.live.model.user.pojo.UserPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @projectName: qiyu-live-app
 * @package: com.sici.live.user.provider.mapper
 * @author: 20148
 * @description:
 * @create-date: 9/10/2024 6:43 PM
 * @version: 1.0
 */

@Mapper
public interface UserMapper extends BaseMapper<UserPO> {
}
