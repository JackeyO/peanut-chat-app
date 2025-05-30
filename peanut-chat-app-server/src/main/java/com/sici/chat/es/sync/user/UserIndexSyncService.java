package com.sici.chat.es.sync.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sici.chat.dao.UserDao;
import com.sici.chat.es.sync.AbstractIndexSyncService;
import com.sici.chat.model.user.entity.User;
import com.sici.chat.model.user.es.UserDocument;
import com.sici.chat.repositories.es.user.UserElasticsearchRepository;
import com.sici.chat.util.ConvertBeanUtil;
import jakarta.annotation.Resource;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

/**
 * @author jackey
 * @description
 * @date 5/28/25 22:46
 */
@Component
public class UserIndexSyncService extends AbstractIndexSyncService<UserDocument, User, Long> {
    @Resource
    private UserDao userDao;
    @Resource
    private UserElasticsearchRepository userElasticsearchRepository;

    @Override
    public String getIndexDescription() {
        return "用户索引";
    }

    @Override
    public IService<User> getIService() {
        return userDao;
    }

    @Override
    public UserDocument convertToDocument(User user) {
        UserDocument userDocument = ConvertBeanUtil.convertSingle(user, UserDocument.class);
        // 设置搜索评分
        userDocument.setSearchScore(user.calculateEsSearchScore());
        return userDocument;
    }

    @Override
    public ElasticsearchRepository<UserDocument, Long> getElasticsearchRepository() {
        return userElasticsearchRepository;
    }

    @Override
    public Boolean checkNecessary(User entity) {
        // 所有用户变更都需要同步到ES
        return Boolean.TRUE;
    }
}
