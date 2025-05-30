#!/bin/bash

# Elasticsearch索引创建脚本
# 作者: peanut-chat项目
# 功能: 创建用户索引和房间索引

# 配置ES地址和端口
ES_HOST=${ES_HOST:-localhost}
ES_PORT=${ES_PORT:-9200}
ES_URL="http://${ES_HOST}:${ES_PORT}"

# 脚本目录
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

echo "=========================================="
echo "Elasticsearch 索引创建脚本"
echo "ES地址: ${ES_URL}"
echo "脚本目录: ${SCRIPT_DIR}"
echo "=========================================="

# 检查ES连接
echo "1. 检查Elasticsearch连接..."
if curl -f -s "${ES_URL}/_cluster/health" > /dev/null; then
    echo "✅ Elasticsearch连接成功"
    echo "ES版本信息:"
    curl -s "${ES_URL}" | grep -E '"version"|"number"'
else
    echo "❌ 无法连接到Elasticsearch: ${ES_URL}"
    echo "请检查ES是否启动，地址和端口是否正确"
    exit 1
fi

# 检查插件
echo ""
echo "2. 检查必要的插件..."
PLUGINS=$(curl -s "${ES_URL}/_cat/plugins")
if echo "${PLUGINS}" | grep -q "analysis-ik"; then
    echo "✅ IK分词插件已安装"
else
    echo "⚠️  警告: 未检测到IK分词插件，请安装后再创建索引"
fi

if echo "${PLUGINS}" | grep -q "analysis-pinyin"; then
    echo "✅ 拼音分析插件已安装"
else
    echo "⚠️  警告: 未检测到拼音分析插件，请安装后再创建索引"
fi

# 函数：创建索引
create_index() {
    local index_name=$1
    local json_file=$2
    
    echo ""
    echo "正在创建索引: ${index_name}"
    
    # 检查索引是否已存在
    if curl -f -s "${ES_URL}/${index_name}" > /dev/null; then
        echo "⚠️  索引 ${index_name} 已存在"
        read -p "是否删除现有索引并重新创建？(y/N): " -n 1 -r
        echo
        if [[ $REPLY =~ ^[Yy]$ ]]; then
            echo "删除现有索引: ${index_name}"
            curl -X DELETE "${ES_URL}/${index_name}"
            echo ""
        else
            echo "跳过索引: ${index_name}"
            return 0
        fi
    fi
    
    # 创建索引
    echo "创建索引: ${index_name}"
    response=$(curl -s -w "\n%{http_code}" -X PUT "${ES_URL}/${index_name}" \
        -H 'Content-Type: application/json' \
        -d @"${json_file}")
    
    http_code=$(echo "$response" | tail -n1)
    response_body=$(echo "$response" | head -n -1)
    
    if [ "$http_code" = "200" ] || [ "$http_code" = "201" ]; then
        echo "✅ 索引 ${index_name} 创建成功"
        echo "响应: ${response_body}"
    else
        echo "❌ 索引 ${index_name} 创建失败"
        echo "HTTP状态码: ${http_code}"
        echo "错误响应: ${response_body}"
        return 1
    fi
}

# 创建用户索引
echo ""
echo "3. 创建用户索引..."
create_index "user_index" "${SCRIPT_DIR}/user_index.json"

# 创建房间索引
echo ""
echo "4. 创建房间索引..."
create_index "room_index" "${SCRIPT_DIR}/room_index.json"

# 验证索引创建
echo ""
echo "5. 验证索引创建结果..."
echo "当前所有索引:"
curl -s "${ES_URL}/_cat/indices?v" | grep -E "index|user_index|room_index"

echo ""
echo "=========================================="
echo "索引创建完成!"
echo "=========================================="

# 测试分析器
echo ""
echo "6. 测试分析器功能..."

echo "测试IK分词器:"
curl -X POST "${ES_URL}/_analyze?pretty" \
    -H 'Content-Type: application/json' \
    -d '{
        "analyzer": "ik_max_word",
        "text": "我是中国人"
    }' | head -20

echo ""
echo "测试拼音分析器:"
curl -X POST "${ES_URL}/_analyze?pretty" \
    -H 'Content-Type: application/json' \
    -d '{
        "tokenizer": {
            "type": "pinyin",
            "keep_full_pinyin": true,
            "keep_original": true
        },
        "text": "张三"
    }' | head -20

echo ""
echo "脚本执行完成!" 