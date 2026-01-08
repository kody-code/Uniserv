# 🎯 Uniserv-app - 核心应用模块

Uniserv-app 是 Uniserv 项目的主应用模块，负责整个系统的启动和核心服务的集成。该模块整合了认证、数据访问和其他公共组件，是整个系统的入口点。

## 📋 概述

- **模块名称**: Uniserv-app
- **版本**: 0.0.1
- **功能**: 系统主入口，集成认证、数据访问等服务
- **角色**: 作为主应用模块启动整个 Uniserv 系统

## 🛠️ 技术栈

- **Spring Boot**: 3.5.9 (主框架)
- **MyBatis-Plus**: 3.5.15 (持久层框架)
- **Sa-Token**: 1.44.0 (权限认证框架)
- **PostgreSQL**: 数据库驱动
- **Lombok**: 代码简化工具
- **SLF4J/Logback**: 日志框架
- **SpringDoc OpenAPI**: API 文档生成

## 🏗️ 项目结构

```
Uniserv-app/
├── src/main
│   ├── java/com/uniserv/app
│   │   └── StudyApplication.java          # 应用启动类
│   └── resources
│       ├── application.yml               # 主配置文件
│       ├── application-dev.yml           # 开发环境配置
│       └── logback-spring.xml            # 日志配置
└── pom.xml                               # Maven 依赖配置
```

## 🚀 快速开始

### 环境要求

- Java 21+
- PostgreSQL 数据库

### 配置

默认开发环境配置：

- 数据库连接: `jdbc:postgresql://localhost:5432/xxx`
- 用户名: `xxxx`
- 密码: `xxxx`

### 启动

```bash
# 使用 Maven 启动
mvn spring-boot:run

# 或者打包后运行
mvn package
java -jar target/Uniserv-app-0.0.1.jar
```

## ⚙️ 配置说明

### Sa-Token 配置

- Token 名称: `uniserv-token`
- 超时时间: 2592000 秒 (30 天)
- 并发登录: 启用
- Token 风格: UUID
- 活跃超时: 604800 秒 (7 天)

### MyBatis-Plus 配置

- Mapper XML 位置: `classpath:mapper/**/*.xml`
- 下划线转驼峰: 启用
- SQL 日志输出: 控制台输出

### 加密配置

- 加密算法: PBKDF2
- 迭代次数: 65536
- 密钥长度: 256 位
- 盐值长度: 16 位

## 🔧 依赖模块

- [Uniserv-common](Uniserv-common/README.md) - 公共组件模块
- [Uniserv-auth](Uniserv-auth/README.md) - 认证授权模块

## 📁 包扫描配置

- Mapper 扫描: `com.uniserv.*.mapper`
- 组件扫描: `com.uniserv.common`, `com.uniserv.auth`

## 🧪 测试

项目使用 Spring Boot Test 进行单元测试和集成测试。