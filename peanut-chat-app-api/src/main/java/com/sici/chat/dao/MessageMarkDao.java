package com.sici.chat.dao;

import cn.hutool.core.lang.Pair;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sici.chat.mapper.MessageMarkMapper;
import com.sici.chat.model.chat.message.entity.MessageMark;
import com.sici.common.enums.chat.message.MessageMarkActionEnums;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 20148
 * @description 针对表【message_mark】的数据库操作Service实现
 * @createDate 2024-12-03 13:48:19
 */
@Service
public class MessageMarkDao extends ServiceImpl<MessageMarkMapper, MessageMark> {
    public Pair<Long, Long> getLikeAndDislikeCount(Integer id) {
        return Pair.of(getLikeCount(id), getDislikeCount(id));
    }

    public List<Integer> getLikeUser(Integer id) {
        return lambdaQuery().eq(MessageMark::getMsgId, id)
                .eq(MessageMark::getLike, MessageMarkActionEnums.YES.getCode())
                .list().stream()
                .map(MessageMark::getUserId)
                .collect(Collectors.toList());
    }

    public List<Integer> getDisLikeUser(Integer id) {
        return lambdaQuery().eq(MessageMark::getMsgId, id)
                .eq(MessageMark::getDislike, MessageMarkActionEnums.YES.getCode())
                .list().stream()
                .map(MessageMark::getUserId)
                .collect(Collectors.toList());
    }

    public Long getLikeCount(Integer id) {
        Long likes = lambdaQuery().eq(MessageMark::getMsgId, id)
                .eq(MessageMark::getLike, MessageMarkActionEnums.YES.getCode())
                .count();
        return likes;
    }

    public Long getDislikeCount(Integer id) {
        Long dislikes = lambdaQuery().eq(MessageMark::getMsgId, id)
                .eq(MessageMark::getDislike, MessageMarkActionEnums.YES.getCode())
                .count();
        return dislikes;
    }
}




