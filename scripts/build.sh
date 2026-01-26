#!/bin/bash

# Uniserv 项目构建脚本
# 用于编译、测试和打包整个项目

set -e  # 遇到错误时退出

# 颜色定义
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo -e "${GREEN}🚀 开始构建 Uniserv 项目${NC}"

# 检查Java版本
echo -e "${YELLOW}🔍 检查Java版本...${NC}"
java_version=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}')
echo "Java版本: $java_version"

if [[ "$java_version" -lt "21" ]]; then
    echo -e "${RED}❌ 错误: 需要Java 21或更高版本${NC}"
    exit 1
fi

# 检查Maven
echo -e "${YELLOW}🔍 检查Maven...${NC}"
if ! command -v mvn &> /dev/null; then
    echo -e "${RED}❌ 错误: Maven未安装${NC}"
    exit 1
fi

echo "Maven版本: $(mvn -version | head -1)"

# 返回项目根目录
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_DIR="$(dirname "$SCRIPT_DIR")"
cd "$PROJECT_DIR"

echo -e "${YELLOW}📂 当前目录: $(pwd)${NC}"

# 清理之前的构建
echo -e "${YELLOW}🧹 清理项目...${NC}"
mvn clean

# 编译项目
echo -e "${YELLOW}🔨 编译项目...${NC}"
mvn compile

# 运行测试
echo -e "${YELLOW}🧪 运行测试...${NC}"
mvn test

# 打包项目
echo -e "${YELLOW}📦 打包项目...${NC}"
mvn package -DskipTests

# 生成覆盖率报告
echo -e "${YELLOW}📊 生成测试覆盖率报告...${NC}"
mvn jacoco:report

echo -e "${GREEN}✅ Uniserv 项目构建完成！${NC}"
echo -e "${GREEN}📁 构建产物位于各模块的 target/ 目录下${NC}"