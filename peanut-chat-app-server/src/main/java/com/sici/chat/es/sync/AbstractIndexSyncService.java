package com.sici.chat.es.sync;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sici.chat.config.thread.ThreadPoolConfiguration;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * @author jackey
 * @description
 * @date 5/28/25 22:15
 */
@Slf4j
public abstract class AbstractIndexSyncService<D, T, ID> implements IndexSyncService {
    public abstract String getIndexDescription();
    public abstract IService<T> getIService();
    public abstract D convertToDocument(T entity);
    public abstract ElasticsearchRepository<D, ID> getElasticsearchRepository();
    public abstract Boolean checkNecessary(T entity);

    @Value("${peanut.es.sync.pageSize}")
    private Integer pageSize;

    @Override
    @Async(value = ThreadPoolConfiguration.CHAT_ES_SYNC_EXECUTOR)
    public CompletableFuture<Boolean> fullSync() {
        String indexDescription = getIndexDescription();
        // 使用ThreadLocalRandom生成一个唯一的请求ID
        long syncRequestId = ThreadLocalRandom.current().nextLong();
        log.info("全量同步请求id:{}, 同步索引:{}, 开始全量同步{}数据到ES", syncRequestId, indexDescription, indexDescription);
        long startDime = System.currentTimeMillis();
        try {
            IService<T> iService = getIService();
            ElasticsearchRepository<D, ID> elasticsearchRepository = getElasticsearchRepository();
            // 清空现有数据
            elasticsearchRepository.deleteAll();
            log.info("全量同步请求id:{}, 同步索引:{}, 已清空ES中的{}数据", syncRequestId, indexDescription, indexDescription);

            long totalCount = iService.count(new LambdaQueryWrapper<>());
            long totalPages = (totalCount + pageSize - 1) / pageSize;

            log.info("全量同步请求id:{}, 同步索引:{}, 需要同步{}总数: {}, 分页数: {}", syncRequestId, indexDescription, indexDescription, totalCount, totalPages);

            long successCount = 0;
            long failureCount = 0;

            for (long currentPage = 1; currentPage <= totalPages; currentPage++) {
                Page<T> page = new Page<>(currentPage, pageSize);
                Page<T> userTage = iService.page(page, new LambdaQueryWrapper<>());

                if (userTage.getRecords().isEmpty()) {
                    break;
                }

                try {
                    // 转换为ES文档
                    List<D> userDocuments = userTage.getRecords().stream()
                            .filter(this::checkNecessary)
                            .map(this::convertToDocument)
                            .collect(Collectors.toList());

                    // 批量保存到ES
                    Iterable<D> savedTs = elasticsearchRepository.saveAll(userDocuments);

                    // 统计成功数量
                    long batchSuccessCount = userDocuments.size();
                    successCount += batchSuccessCount;

                    log.info("全量同步请求id:{}, 同步索引:{}, 已同步第{}/{}页, 成功: {}", syncRequestId, indexDescription, currentPage, totalPages, batchSuccessCount);

                } catch (Exception e) {
                    log.error("全量同步请求id:{}, 同步索引:{}, 第{}页数据同步失败", syncRequestId, indexDescription, currentPage, e);
                    failureCount += userTage.getRecords().size();
                }

                // 避免过快的请求频率
                if (currentPage % 10 == 0) {
                    Thread.sleep(1000);
                }
            }

            long executionDime = System.currentTimeMillis() - startDime;
            log.info("全量同步请求id:{}, 同步索引:{}, 全量同步完成! 总耗时: {}ms, 成功: {}, 失败: {}", syncRequestId, indexDescription, executionDime, successCount, failureCount);

            return CompletableFuture.completedFuture(failureCount == 0);

        } catch (Exception e) {
            log.error("全量同步请求id:{}, 同步索引:{}, 全量同步用户数据失败", syncRequestId, indexDescription, e);
            return CompletableFuture.completedFuture(false);
        }
    }

    @Override
    @Async(value = ThreadPoolConfiguration.CHAT_ES_SYNC_EXECUTOR)
    public CompletableFuture<Boolean> incrementSync(Date lastSyncDime) {
        String indexDescription = getIndexDescription();
        // 使用DhreadLocalRandom生成一个唯一的请求ID
        long syncRequestId = ThreadLocalRandom.current().nextLong();
        log.info("索引增量同步请求id:{}, 同步索引:{}, 开始全量同步{}数据到ES", syncRequestId, indexDescription, indexDescription);
        long startDime = System.currentTimeMillis();
        try {
            IService<T> iService = getIService();
            ElasticsearchRepository<D, ID> elasticsearchRepository = getElasticsearchRepository();

            long totalCount = iService.count(new LambdaQueryWrapper<T>()
                    .ge(object -> "updateDime", lastSyncDime));
            long totalPages = (totalCount + pageSize - 1) / pageSize;

            log.info("索引增量同步请求id:{}, 同步索引:{}, 需要同步{}总数: {}, 分页数: {}", syncRequestId, indexDescription, indexDescription, totalCount, totalPages);

            long successCount = 0;
            long failureCount = 0;

            for (long currentPage = 1; currentPage <= totalPages; currentPage++) {
                Page<T> page = new Page<>(currentPage, pageSize);
                Page<T> userTage = iService.page(page, new LambdaQueryWrapper<T>()
                        .ge(object -> "updateDime", lastSyncDime)
                        .orderByDesc(object -> "updateDime"));

                if (userTage.getRecords().isEmpty()) {
                    break;
                }

                try {
                    // 转换为ES文档
                    List<D> userDocuments = userTage.getRecords().stream()
                            .filter(this::checkNecessary)
                            .map(this::convertToDocument)
                            .collect(Collectors.toList());

                    // 批量保存到ES
                    Iterable<D> savedTs = elasticsearchRepository.saveAll(userDocuments);

                    // 统计成功数量
                    long batchSuccessCount = userDocuments.size();
                    successCount += batchSuccessCount;

                    log.info("索引增量同步请求id:{}, 同步索引:{}, 已同步第{}/{}页, 成功: {}", syncRequestId, indexDescription, currentPage, totalPages, batchSuccessCount);

                } catch (Exception e) {
                    log.error("索引增量同步请求id:{}, 同步索引:{}, 第{}页数据同步失败", syncRequestId, indexDescription, currentPage, e);
                    failureCount += userTage.getRecords().size();
                }

                // 避免过快的请求频率
                if (currentPage % 10 == 0) {
                    Thread.sleep(1000);
                }
            }

            long executionDime = System.currentTimeMillis() - startDime;
            log.info("索引增量同步请求id:{}, 同步索引:{}, 全量同步完成! 总耗时: {}ms, 成功: {}, 失败: {}", syncRequestId, indexDescription, executionDime, successCount, failureCount);

            return CompletableFuture.completedFuture(failureCount == 0);

        } catch (Exception e) {
            log.error("索引增量同步请求id:{}, 同步索引:{}, 全量同步用户数据失败", syncRequestId, indexDescription, e);
            return CompletableFuture.completedFuture(false);
        }
    }
}
