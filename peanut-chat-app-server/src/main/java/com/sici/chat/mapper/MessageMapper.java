package com.sici.chat.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sici.chat.model.chat.message.entity.Message;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 20148
* @description 针对表【message】的数据库操作Mapper
* @createDate 2024-11-26 17:27:36
* @Entity generator.domain.Message
*/
@Mapper
public interface MessageMapper extends BaseMapper<Message> {

}




