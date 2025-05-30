# Elasticsearch 索引配置

本目录包含花生聊天应用的Elasticsearch索引配置文件和创建脚本。

## 文件说明

- `user_index.json` - 用户索引配置文件
- `room_index.json` - 房间索引配置文件  
- `create_indices.sh` - 完整的索引创建脚本（推荐）
- `create_indices_simple.sh` - 简化版索引创建脚本
- `README.md` - 本说明文档

## 前置条件

### 1. 确保Elasticsearch已启动
```bash
# 检查ES状态
curl -X GET "localhost:9200/_cluster/health?pretty"
```

### 2. 安装必要插件

**安装IK分词插件：**
```bash
# 进入ES安装目录
cd /usr/share/elasticsearch

# 安装IK插件（版本号需与ES版本匹配）
sudo bin/elasticsearch-plugin install https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v8.8.0/elasticsearch-analysis-ik-8.8.0.zip
```

**安装拼音分析插件：**
```bash
# 安装拼音插件
sudo bin/elasticsearch-plugin install https://github.com/medcl/elasticsearch-analysis-pinyin/releases/download/v8.8.0/elasticsearch-analysis-pinyin-8.8.0.zip
```

**重启ES服务：**
```bash
sudo systemctl restart elasticsearch
```

**验证插件安装：**
```bash
curl -X GET "localhost:9200/_cat/plugins?v"
```

## 使用方法

### 方法1: 使用完整脚本（推荐）

```bash
cd peanut-chat-app/es
chmod +x create_indices.sh
./create_indices.sh
```

功能特点：
- ✅ 自动检查ES连接
- ✅ 验证插件安装
- ✅ 检查索引是否已存在
- ✅ 交互式确认重建索引
- ✅ 详细的执行日志
- ✅ 自动测试分析器功能

### 方法2: 使用简化脚本

```bash
cd peanut-chat-app/es
chmod +x create_indices_simple.sh
./create_indices_simple.sh
```

### 方法3: 直接使用curl命令

```bash
cd peanut-chat-app/es

# 创建用户索引
curl -X PUT "localhost:9200/user_index" \
    -H 'Content-Type: application/json' \
    -d @user_index.json

# 创建房间索引
curl -X PUT "localhost:9200/room_index" \
    -H 'Content-Type: application/json' \
    -d @room_index.json
```

### 方法4: 自定义ES地址

如果ES不在本地或使用非默认端口：

```bash
# 设置环境变量
export ES_HOST=your-es-host
export ES_PORT=9200

# 运行脚本
./create_indices.sh
```

或者直接修改curl命令中的地址：
```bash
curl -X PUT "http://your-es-host:9200/user_index" \
    -H 'Content-Type: application/json' \
    -d @user_index.json
```

## 索引配置说明

### 用户索引 (user_index)

**关键字段：**
- `id` - 用户ID (long)
- `openId` - 用户openId (keyword, 精确匹配)
- `nickName` - 用户昵称 (text, 支持IK分词和拼音搜索)
- `city/province` - 地理位置 (text, 支持模糊搜索)
- `searchScore` - 搜索评分 (float)

**分析器配置：**
- `ik_max_word` - IK分词器，索引时最大化分词
- `ik_smart` - IK分词器，搜索时智能分词
- `pinyin_analyzer` - 拼音分析器，支持拼音搜索

### 房间索引 (room_index)

**关键字段：**
- `id` - 房间ID (long)
- `roomName` - 房间名称 (text, 支持IK分词和拼音搜索)
- `description` - 房间描述 (text, 支持全文搜索)
- `memberCount` - 成员数量 (integer)
- `lastActiveTime` - 最后活跃时间 (date)

## 验证索引创建

### 检查索引列表
```bash
curl -X GET "localhost:9200/_cat/indices?v"
```

### 检查索引映射
```bash
# 查看用户索引映射
curl -X GET "localhost:9200/user_index/_mapping?pretty"

# 查看房间索引映射  
curl -X GET "localhost:9200/room_index/_mapping?pretty"
```

### 测试分析器
```bash
# 测试IK分词
curl -X POST "localhost:9200/_analyze?pretty" \
    -H 'Content-Type: application/json' \
    -d '{
        "analyzer": "ik_max_word",
        "text": "我是中国人"
    }'

# 测试拼音分析
curl -X POST "localhost:9200/user_index/_analyze?pretty" \
    -H 'Content-Type: application/json' \
    -d '{
        "field": "nickName.pinyin",
        "text": "张三"
    }'
```

## 删除索引

如果需要重建索引：

```bash
# 删除用户索引
curl -X DELETE "localhost:9200/user_index"

# 删除房间索引
curl -X DELETE "localhost:9200/room_index"
```

## 故障排除

### 常见错误

1. **插件未安装**
   ```
   "failed to find analyzer type [ik_max_word]"
   ```
   解决：安装IK分词插件并重启ES

2. **ES未启动**
   ```
   curl: (7) Failed to connect to localhost port 9200
   ```
   解决：启动Elasticsearch服务

3. **权限问题**
   ```
   Permission denied
   ```
   解决：给脚本添加执行权限 `chmod +x *.sh`

### 调试方法

1. 检查ES日志：
   ```bash
   tail -f /var/log/elasticsearch/elasticsearch.log
   ```

2. 查看集群状态：
   ```bash
   curl -X GET "localhost:9200/_cluster/health?pretty"
   ```

3. 验证插件：
   ```bash
   curl -X GET "localhost:9200/_nodes/plugins?pretty"
   ```

## 注意事项

1. **版本兼容性**：插件版本必须与ES版本完全匹配
2. **数据备份**：重建索引会删除现有数据，请先备份重要数据
3. **生产环境**：在生产环境中谨慎操作，建议先在测试环境验证
4. **性能调优**：根据实际数据量调整分片数和副本数

## 相关文档

- [Elasticsearch官方文档](https://www.elastic.co/guide/en/elasticsearch/reference/current/index.html)
- [IK分词插件](https://github.com/medcl/elasticsearch-analysis-ik)
- [拼音分析插件](https://github.com/medcl/elasticsearch-analysis-pinyin) 