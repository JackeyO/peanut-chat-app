package com.sici.chat.canal;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.sici.chat.canal.sync.DataChangeHandlerFactory;
import com.sici.chat.config.canal.CanalConfigurationProperties;
import com.sici.chat.model.canal.event.DataChangeEvent;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Canal客户端管理器
 */
//@Component
@Slf4j
public class CanalClientManager implements CommandLineRunner {
    @Resource
    private CanalConfigurationProperties canalConfigurationProperties;
    @Resource
    private DataChangeHandlerFactory dataChangeHandlerFactory;

    private CanalConnector connector;
    private volatile boolean running = false;

    @Override
    public void run(String... args) throws Exception {
        if (!canalConfigurationProperties.getEnabled()) {
            log.info("Canal监听已禁用");
            return;
        }
        
        initCanalConnector();
        startListening();
    }

    /**
     * 初始化Canal连接器
     */
    private void initCanalConnector() {
        try {
            // 创建连接器
            connector = CanalConnectors.newSingleConnector(
                new InetSocketAddress(canalConfigurationProperties.getServerHost(), canalConfigurationProperties.getServerPort()),
                canalConfigurationProperties.getDestination(),
                canalConfigurationProperties.getUsername(),
                canalConfigurationProperties.getPassword()
            );
            
            log.info("Canal连接器初始化成功: {}:{}", 
                    canalConfigurationProperties.getServerHost(), canalConfigurationProperties.getServerPort());
            
        } catch (Exception e) {
            log.error("Canal连接器初始化失败", e);
            throw new RuntimeException("Canal连接器初始化失败", e);
        }
    }

    /**
     * 开始监听
     */
    private void startListening() {
        Thread thread = new Thread(() -> {
            try {
                // 连接Canal服务器
                connector.connect();

                // 会滚到没有ack的地方，下次fetch从没有ack的位置开始
                connector.rollback();

                // 订阅数据库表
                String filter = buildSubscribeFilter();
                connector.subscribe(filter);

                running = true;
                log.info("Canal客户端启动成功，开始监听数据变更: filter={}", filter);

                // 开始消费数据
                while (running) {
                    consumeData();
                }

            } catch (Exception e) {
                log.error("Canal客户端运行异常", e);
            } finally {
                disconnect();
            }
        });

        thread.setName("canal-client-listener-thread");
        thread.setDaemon(true); // 设置为守护线程

        // 启动线程
        thread.start();
    }

    /**
     * 构建订阅过滤器
     */
    private String buildSubscribeFilter() {
        StringBuilder filter = new StringBuilder();
        
        List<CanalConfigurationProperties.TableConfig> tables = canalConfigurationProperties.getTables();
        if (!CollectionUtils.isEmpty(tables)) {
            for (int i = 0; i < tables.size(); i++) {
                CanalConfigurationProperties.TableConfig table = tables.get(i);
                if (table.getEnabled()) {
                    if (i > 0) {
                        filter.append(",");
                    }
                    filter.append(table.getDatabase()).append(".").append(table.getTable());
                }
            }
        }
        
        return filter.toString();
    }

    /**
     * 消费数据
     */
    private void consumeData() {
        try {
            // 获取数据
            Message message = connector.getWithoutAck(canalConfigurationProperties.getBatchSize(), canalConfigurationProperties.getTimeout(), TimeUnit.MILLISECONDS);

            long batchId = message.getId();
            int size = message.getEntries().size();
            
            if (batchId == -1 || size == 0) {
                // 没有数据，继续轮询
                log.info("canal未监听到变更");
                Thread.sleep(1000);
                return;
            }
            
            log.info("接收到Canal消息: batchId={}, size={}", batchId, size);
            
            // 处理数据变更
            processEntries(message.getEntries());
            
            // 确认消费
            connector.ack(batchId);
        } catch (Exception e) {
            log.error("消费Canal数据异常", e);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * 处理数据条目
     */
    private void processEntries(List<CanalEntry.Entry> entries) {
        for (CanalEntry.Entry entry : entries) {
            if (entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONBEGIN || 
                entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONEND) {
                continue;
            }
            
            if (entry.getEntryType() == CanalEntry.EntryType.ROWDATA) {
                processRowDataEntry(entry);
            }
        }
    }

    /**
     * 处理行数据变更
     */
    private void processRowDataEntry(CanalEntry.Entry entry) {
        try {
            CanalEntry.RowChange rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
            CanalEntry.EventType eventType = rowChange.getEventType();
            
            // 只处理INSERT、UPDATE、DELETE事件
            if (eventType != CanalEntry.EventType.INSERT && 
                eventType != CanalEntry.EventType.UPDATE && 
                eventType != CanalEntry.EventType.DELETE) {
                return;
            }
            
            String database = entry.getHeader().getSchemaName();
            String table = entry.getHeader().getTableName();
            
            log.debug("处理数据变更: database={}, table={}, eventType={}", 
                     database, table, eventType);
            
            // 处理每一行数据
            for (CanalEntry.RowData rowData : rowChange.getRowDatasList()) {
                DataChangeEvent event = buildDataChangeEvent(entry, eventType, rowData);
                
                // 分发给对应的处理器
                dataChangeHandlerFactory.handleDataChangeEvent(event);
            }
            
        } catch (Exception e) {
            log.error("处理行数据变更异常: {}", entry, e);
        }
    }

    /**
     * 构建数据变更事件
     */
    private DataChangeEvent buildDataChangeEvent(CanalEntry.Entry entry,
                                                 CanalEntry.EventType eventType,
                                                 CanalEntry.RowData rowData) {
        
        String database = entry.getHeader().getSchemaName();
        String table = entry.getHeader().getTableName();
        
        // 转换事件类型
        DataChangeEvent.EventType changeEventType;
        switch (eventType) {
            case INSERT:
                changeEventType = DataChangeEvent.EventType.INSERT;
                break;
            case UPDATE:
                changeEventType = DataChangeEvent.EventType.UPDATE;
                break;
            case DELETE:
                changeEventType = DataChangeEvent.EventType.DELETE;
                break;
            default:
                throw new IllegalArgumentException("不支持的事件类型: " + eventType);
        }
        
        // 构建变更前后数据
        Map<String, Object> beforeData = null;
        Map<String, Object> afterData = null;
        Object primaryKey = null;
        
        if (rowData.getBeforeColumnsList() != null && !rowData.getBeforeColumnsList().isEmpty()) {
            beforeData = convertColumnsToMap(rowData.getBeforeColumnsList());
            primaryKey = extractPrimaryKey(database, table, beforeData);
        }
        
        if (rowData.getAfterColumnsList() != null && !rowData.getAfterColumnsList().isEmpty()) {
            afterData = convertColumnsToMap(rowData.getAfterColumnsList());
            if (primaryKey == null) {
                primaryKey = extractPrimaryKey(database, table, afterData);
            }
        }
        
        return DataChangeEvent.builder()
                .database(database)
                .table(table)
                .eventType(changeEventType)
                .beforeData(beforeData)
                .afterData(afterData)
                .primaryKey(primaryKey)
                .timestamp(entry.getHeader().getExecuteTime())
                .logPosition(entry.getHeader().getLogfileName() + ":" + entry.getHeader().getLogfileOffset())
                .build();
    }

    /**
     * 转换列数据为Map
     */
    private Map<String, Object> convertColumnsToMap(List<CanalEntry.Column> columns) {
        Map<String, Object> data = new HashMap<>();
        for (CanalEntry.Column column : columns) {
            data.put(column.getName(), column.getValue());
        }
        return data;
    }

    /**
     * 提取主键值
     */
    private Object extractPrimaryKey(String database, String table, Map<String, Object> data) {
        // 根据配置获取主键字段名
        String primaryKeyField = getPrimaryKeyField(database, table);
        return data.get(primaryKeyField);
    }

    /**
     * 获取主键字段名
     */
    private String getPrimaryKeyField(String database, String table) {
        if (canalConfigurationProperties.getTables() != null) {
            return canalConfigurationProperties.getTables().stream()
                    .filter(config -> config.getDatabase().equals(database) && config.getTable().equals(table))
                    .findFirst()
                    .map(CanalConfigurationProperties.TableConfig::getPrimaryKey)
                    .orElse("id");
        }
        return "id";
    }

    /**
     * 断开连接
     */
    private void disconnect() {
        running = false;
        if (connector != null) {
            connector.disconnect();
            log.info("Canal连接已断开");
        }
    }
}