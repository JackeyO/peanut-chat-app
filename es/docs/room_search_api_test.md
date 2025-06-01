# 房间搜索API测试文档

## 快速开始

### 1. 导入示例数据
```bash
# 给脚本执行权限
chmod +x src/main/resources/scripts/import_room_data.sh

# 执行导入（确保Elasticsearch已启动）
./src/main/resources/scripts/import_room_data.sh localhost 9200
```

### 2. 基础测试
```bash
# 检查数据是否导入成功
curl "http://localhost:9200/room_index/_count"
```

## API测试用例

### 🔍 智能综合搜索

#### 测试1: 搜索"技术"相关房间
```bash
GET /api/room/search/intelligent?keyword=技术&page=0&size=5
```
**预期结果**: 返回包含"技术交流群"、"前端开发交流"、"Java后端开发"等技术相关房间

#### 测试2: 搜索"Python"
```bash
GET /api/room/search/intelligent?keyword=Python&page=0&size=10
```
**预期结果**: 返回"Python编程学习"、"数据科学研究"等相关房间，支持高亮显示

#### 测试3: 搜索房间ID
```bash
GET /api/room/search/intelligent?keyword=1&page=0&size=5
```
**预期结果**: 优先返回ID为1的"技术交流群"

### 📛 房间名称搜索

#### 测试4: 精确名称搜索
```bash
GET /api/room/search/name?roomName=游戏爱好者联盟&page=0&size=5
```

#### 测试5: 拼音搜索
```bash
GET /api/room/search/name?roomName=jishu&page=0&size=5
```
**预期结果**: 能够搜索到"技术交流群"（如果配置了拼音分析器）

### 🔥 热门房间搜索

#### 测试6: 搜索热门房间
```bash
GET /api/room/search/hot?minMemberCount=100&page=0&size=10
```
**预期结果**: 返回成员数≥100的房间，按搜索评分和成员数排序

#### 测试7: 搜索超级热门房间
```bash
GET /api/room/search/hot?minMemberCount=200&page=0&size=5
```
**预期结果**: 返回"美食分享天地"等大型房间

### ⚡ 活跃房间搜索

#### 测试8: 搜索活跃房间
```bash
GET /api/room/search/active?page=0&size=10
```
**预期结果**: 返回最近24小时内有活动的房间

### 🏷️ 按类型搜索

#### 测试9: 搜索多人群聊
```bash
GET /api/room/search/type/0?page=0&size=10
```
**预期结果**: 返回type=0的群聊房间

#### 测试10: 搜索1对1聊天
```bash
GET /api/room/search/type/1?page=0&size=10
```
**预期结果**: 返回"李小明的聊天室"、"王大华的对话"等私聊房间

### 🔍 高级搜索

#### 测试11: 多条件搜索
```bash
GET /api/room/search/advanced?keyword=开发&type=0&minMemberCount=50&page=0&size=5
```
**预期结果**: 返回包含"开发"关键词、类型为群聊、成员数≥50的房间

#### 测试12: 搜索编程相关大群
```bash
GET /api/room/search/advanced?keyword=编程&type=0&minMemberCount=100&page=0&size=10
```

### 🎯 相似房间推荐

#### 测试13: 基于技术群推荐相似房间
```bash
GET /api/room/search/1/similar?type=0&memberCount=156&searchScore=9.5&page=0&size=5
```
**预期结果**: 推荐与"技术交流群"相似的房间

### 🆕 新建房间搜索

#### 测试14: 搜索最近7天的新房间
```bash
GET /api/room/search/recent?page=0&size=10
```

### 📭 空房间搜索

#### 测试15: 搜索小房间
```bash
GET /api/room/search/empty?maxMemberCount=30&page=0&size=10
```
**预期结果**: 返回成员数≤30的房间，如"新手村"、"Bitcoin讨论组"等

### 📊 成员数量范围搜索

#### 测试16: 搜索中型房间
```bash
GET /api/room/search/members/range?minCount=50&maxCount=150&page=0&size=10
```
**预期结果**: 返回成员数在50-150之间的房间

### 🔍 模糊搜索（容错）

#### 测试17: 拼写错误容错
```bash
GET /api/room/search/fuzzy?keyword=jisue&page=0&size=5
```
**预期结果**: 能容错搜索到"技术"相关房间

#### 测试18: 英文容错
```bash
GET /api/room/search/fuzzy?keyword=pythom&page=0&size=5
```
**预期结果**: 能搜索到"Python"相关房间

### 📝 描述搜索

#### 测试19: 搜索特定描述内容
```bash
GET /api/room/search/description?content=机器学习&page=0&size=5
```
**预期结果**: 返回"深度学习研究室"、"数据科学研究"等相关房间

#### 测试20: 搜索技术栈
```bash
GET /api/room/search/description?content=Spring&page=0&size=5
```

### 💡 搜索建议

#### 测试21: 获取房间名称建议
```bash
GET /api/room/search/suggestions?prefix=技
```
**预期结果**: 返回以"技"开头的房间名称建议

#### 测试22: 英文前缀建议
```bash
GET /api/room/search/suggestions?prefix=Py
```

### 🤖 智能推荐

#### 测试23: 个性化房间推荐
```bash
GET /api/room/search/recommend?userId=123&limit=15
```
**预期结果**: 返回热门、活跃、新建房间的智能组合推荐

### 📈 统计信息

#### 测试24: 获取房间统计
```bash
GET /api/room/search/statistics
```
**预期结果**: 返回房间类型分布和成员数量统计信息

### 🔄 组合搜索

#### 测试25: 关键词+类型组合搜索
```bash
GET /api/room/search/combo?keyword=学习&type=0&page=0&size=5
```
**预期结果**: 返回包含"学习"关键词的群聊房间

## 测试验证要点

### ✅ 功能验证
1. **搜索准确性**: 关键词匹配是否准确
2. **排序合理性**: 结果按评分、成员数、活跃度合理排序
3. **高亮显示**: 搜索结果是否正确高亮关键词
4. **分页功能**: 分页参数是否正常工作
5. **过滤条件**: deleted=false的房间被正确过滤

### ✅ 性能验证
1. **响应时间**: 搜索请求响应时间是否合理
2. **并发支持**: 多用户同时搜索的性能表现
3. **索引效率**: 大数据量下的搜索效率

### ✅ 边界测试
1. **空关键词搜索**: 处理空字符串或null值
2. **特殊字符**: 包含特殊字符的搜索
3. **超长关键词**: 过长搜索关键词的处理
4. **无结果搜索**: 搜索不存在内容的处理

## 常见问题排查

### 问题1: 搜索无结果
```bash
# 检查索引是否存在
curl "http://localhost:9200/room_index"

# 检查数据是否导入
curl "http://localhost:9200/room_index/_count"

# 查看索引映射
curl "http://localhost:9200/room_index/_mapping"
```

### 问题2: 拼音搜索不生效
- 确认Elasticsearch已安装拼音分析器插件
- 检查索引映射中的拼音字段配置

### 问题3: 高亮不显示
- 确认查询使用了@Highlight注解
- 检查前端是否正确解析highlight字段

### 问题4: 排序不合理
- 检查searchScore字段的值是否合理
- 确认排序字段的映射类型正确

## 扩展测试

### 压力测试
```bash
# 使用Apache Bench进行压力测试
ab -n 1000 -c 10 "http://localhost:8080/api/room/search/intelligent?keyword=技术"
```

### 搜索分析
```bash
# 查看搜索查询的执行计划
curl -X GET "http://localhost:9200/room_index/_search?explain=true" \
  -H "Content-Type: application/json" \
  -d '{"query": {"match": {"roomName": "技术"}}}'
```

---

**建议**: 在实际使用中，根据用户搜索行为和业务需求，持续优化搜索算法和权重配置。