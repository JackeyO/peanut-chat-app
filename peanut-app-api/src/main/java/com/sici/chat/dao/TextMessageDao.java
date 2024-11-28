package com.sici.chat.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sici.chat.model.chat.message.entity.TextMessage;
import com.sici.chat.mapper.TextMessageMapper;
import org.springframework.stereotype.Component;

/**
* @author 20148
* @description 针对表【text_message】的数据库操作Service实现
* @createDate 2024-11-26 17:27:36
*/
@Component
public class TextMessageDao extends ServiceImpl<TextMessageMapper, TextMessage> {
}




