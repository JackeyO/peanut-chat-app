package com.sici.chat.canal.sync.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sici.chat.canal.sync.AbstractDataChangeEsHandler;
import com.sici.chat.dao.UserDao;
import com.sici.chat.model.canal.event.DataChangeEvent;
import com.sici.chat.model.user.entity.User;
import com.sici.chat.model.user.es.UserDocument;
import com.sici.chat.repositories.es.user.UserElasticsearchRepository;
import com.sici.chat.util.ConvertBeanUtil;
import com.sici.common.constant.canal.DatabaseConstant;
import jakarta.annotation.Resource;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author jackey
 * @description
 * @date 5/29/25 18:13
 */
public class UserDataChangeEsHandler extends AbstractDataChangeEsHandler<User, UserDocument, Long> {
    @Resource
    private UserDao userDao;
    @Resource
    private UserElasticsearchRepository userElasticsearchRepository;

    @Override
    public UserDocument getDoc(User user) {
        UserDocument userDocument = ConvertBeanUtil.convertSingle(user, UserDocument.class);
        userDocument.setSearchScore(user.calculateEsSearchScore());
        return userDocument;
    }

    @Override
    public ElasticsearchRepository<UserDocument, Long> getElasticsearchRepository() {
        return userElasticsearchRepository;
    }

    @Override
    public IService<User> getIService() {
        return userDao;
    }

    @Override
    public Long convertPrimaryKey(Object primaryKey) {
        return Long.valueOf(String.valueOf(primaryKey));
    }

    @Override
    public String getDatabase() {
        return DatabaseConstant.CHAT_DB_NAME;
    }

    @Override
    public String getTable() {
        return DatabaseConstant.CHAT_USER_TABLE;
    }

    @Override
    public Boolean checkNecessary(User user, DataChangeEvent dataChangeEvent) {
        // 所有用户变更都需要同步到ES
        return Boolean.TRUE;
    }
}
