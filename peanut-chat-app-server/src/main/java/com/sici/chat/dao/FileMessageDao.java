package com.sici.chat.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sici.chat.mapper.FileMessageMapper;
import com.sici.chat.model.chat.message.entity.FileMessage;
import org.springframework.stereotype.Component;

/**
* @author 20148
* @description 针对表【file_message】的数据库操作Service实现
* @createDate 2024-11-26 17:27:36
*/
@Component
public class FileMessageDao extends ServiceImpl<FileMessageMapper, FileMessage> {
}