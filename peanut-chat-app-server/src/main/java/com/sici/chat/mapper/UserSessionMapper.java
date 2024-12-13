package com.sici.chat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sici.chat.model.chat.session.entity.UserSession;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 20148
* @description 针对表【user_session】的数据库操作Mapper
* @createDate 2024-11-25 18:13:49
* @Entity generator.domain.UserSession
*/
@Mapper
public interface UserSessionMapper extends BaseMapper<UserSession> {

}




