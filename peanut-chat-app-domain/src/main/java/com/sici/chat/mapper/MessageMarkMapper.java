package com.sici.chat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sici.chat.model.chat.message.entity.MessageMark;
import org.apache.ibatis.annotations.Mapper;

/**
* @author jackey
* @description 针对表【message_mark】的数据库操作Mapper
* @createDate 2025-05-23 15:08:56
* @Entity com.sici.chat.model.chat.message.entity.MessageMark
*/
@Mapper
public interface MessageMarkMapper extends BaseMapper<MessageMark> {

}




