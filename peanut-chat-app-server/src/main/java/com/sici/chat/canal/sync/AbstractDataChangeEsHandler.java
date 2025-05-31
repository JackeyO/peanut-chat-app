package com.sici.chat.canal.sync;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sici.chat.model.canal.event.DataChangeEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.io.Serializable;

/**
 * @author jackey
 * @description
 * @date 5/29/25 17:45
 */
@Slf4j
public abstract class AbstractDataChangeEsHandler<T, D, ID extends Serializable> extends AbstractDataChangeHandler<T, ID> {
    public abstract D getDoc(T t);
    public abstract ElasticsearchRepository<D, ID> getElasticsearchRepository();

    /**
     * 仅仅更新实体，如果设计其他信息更新，子类重写该方法。
     */
    @Override
    public void handlerInsertDataChangeEvent(DataChangeEvent dataChangeEvent) {
        ID primaryKey = convertPrimaryKey(dataChangeEvent.getPrimaryKey());

        IService<T> iService = getIService();
        T t = iService.getById(primaryKey);

        if (!checkNecessary(t, dataChangeEvent)) {
            return ;
        }

        ElasticsearchRepository<D, ID> elasticsearchRepository = getElasticsearchRepository();

        // 保存到ES
        elasticsearchRepository.save(getDoc(t));

        log.info("{}.{} 插入数据{}变更事件处理完成，主键: {}", getDatabase(), getTable(), getSyncType(), primaryKey);
    }

    @Override
    public void handlerDeleteDataChangeEvent(DataChangeEvent dataChangeEvent) {
        ID primaryKey = convertPrimaryKey(dataChangeEvent.getPrimaryKey());
        IService<T> iService = getIService();
        T t = iService.getById(primaryKey);

        if (!checkNecessary(t, dataChangeEvent)) {
            return ;
        }
        ElasticsearchRepository<D, ID> elasticsearchRepository = getElasticsearchRepository();
        // 删除索引数据
        elasticsearchRepository.deleteById(primaryKey);

        log.info("{}.{} 删除数据{}变更事件处理完成，主键: {}", getDatabase(), getTable(), getSyncType(), primaryKey);
    }

    @Override
    public void handlerUpdateDataChangeEvent(DataChangeEvent dataChangeEvent) {
        ID primaryKey = convertPrimaryKey(dataChangeEvent.getPrimaryKey());

        IService<T> iService = getIService();
        T t = iService.getById(primaryKey);

        if (!checkNecessary(t, dataChangeEvent)) {
            return ;
        }

        ElasticsearchRepository<D, ID> elasticsearchRepository = getElasticsearchRepository();

        // 保存到ES
        elasticsearchRepository.save(getDoc(t));

        log.info("{}.{} 更新数据{}变更事件处理完成，主键: {}", getDatabase(), getTable(), getSyncType(), primaryKey);
    }

    @Override
    public String getSyncType() {
        return "ES";
    }
}
