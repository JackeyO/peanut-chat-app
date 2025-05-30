#!/bin/bash

# 简单的索引创建脚本 - 直接使用curl命令
# ES地址配置
ES_URL="http://localhost:9200"

echo "创建Elasticsearch索引..."
echo "ES地址: ${ES_URL}"

# 获取脚本所在目录
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

echo ""
echo "1. 创建用户索引 (user_index)..."
curl -X PUT "${ES_URL}/user_index" \
    -H 'Content-Type: application/json' \
    -d @"${SCRIPT_DIR}/user_index.json"

echo ""
echo ""
echo "2. 创建房间索引 (room_index)..."
curl -X PUT "${ES_URL}/room_index" \
    -H 'Content-Type: application/json' \
    -d @"${SCRIPT_DIR}/room_index.json"

echo ""
echo ""
echo "3. 查看创建的索引..."
curl -X GET "${ES_URL}/_cat/indices?v"

echo ""
echo "索引创建完成!" 