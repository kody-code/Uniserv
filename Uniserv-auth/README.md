# Uniserv-auth 模块

🔐 **Uniserv 认证与授权模块** - 提供用户认证、登录、注册及权限管理功能

## 📋 概述

Uniserv-auth 是 Uniserv 项目的认证与授权模块，负责处理用户身份验证、登录、注册以及权限管理。该模块基于 Sa-Token
实现了安全的用户认证机制，结合 MyBatis-Plus 进行数据库操作。

## 🚀 功能特性

- ✅ **用户登录** - 安全的用户身份验证
- ✅ **用户注册** - 支持新用户注册功能
- ✅ **权限管理** - 基于角色的访问控制（RBAC）
- ✅ **密码加密** - 使用 BCrypt 加密算法
- ✅ **自动角色分配** - 首个用户自动成为管理员

## 🏗️ 技术架构

### 核心依赖

- **Spring Boot** - 企业级应用开发框架
- **Sa-Token** - 轻量级 Java 权限认证框架
- **MyBatis-Plus** - 持久层框架增强
- **Swagger/OpenAPI** - API 文档自动生成
- **BCrypt** - 密码加密算法

### 模块依赖

- **Uniserv-common** - 公共组件模块（异常处理、结果封装等）

## 📁 目录结构

```
Uniserv-auth/
├── src/main/java/com/uniserv/auth/
│   ├── config/           # 配置类
│   │   └── StpInterfaceImpl.java  # Sa-Token 权限接口实现
│   ├── controller/       # 控制器层
│   │   └── AuthController.java    # 认证相关API接口
│   ├── dto/              # 数据传输对象
│   │   ├── request/      # 请求参数
│   │   │   ├── LoginRequestDto.java      # 登录请求参数
│   │   │   └── RegisterRequestDto.java   # 注册请求参数
│   │   └── response/     # 响应参数
│   │       └── LoginResponseDto.java     # 登录响应参数
│   ├── entity/           # 实体类
│   │   └── Users.java    # 用户实体
│   ├── mapper/           # 数据访问层
│   │   └── UsersMapper.java     # 用户数据访问接口
│   └── service/          # 业务逻辑层
│       ├── impl/         # 业务实现类
│       │   └── UsersServiceImpl.java  # 用户服务实现
│       └── IUsersService.java         # 用户服务接口
├── src/main/resources/mapper/    # MyBatis 映射文件
│   └── UsersMapper.xml   # 用户SQL映射
├── pom.xml              # Maven 项目配置
└── README.md            # 模块说明文档
```

## 🛠️ API 接口

### 认证接口

#### 用户登录

- **POST** `/api/auth/login`
- **功能**: 用户身份验证并返回登录令牌
- **请求参数**:
  ```json
  {
    "username": "用户名",
    "password": "密码"
  }
  ```
- **响应示例**:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": {
      "userId": "123e4567-e89b-12d3-a456-426614174000",
      "username": "admin",
      "email": "admin@example.com",
      "tokenInfo": {
        "tokenName": "satoken",
        "tokenValue": "token值",
        "timeout": 2592000,
        "isLogin": true,
        "loginId": "用户ID"
      }
    }
  }
  ```

#### 用户注册

- **POST** `/api/auth/register`
- **功能**: 新用户注册
- **请求参数**:
  ```json
  {
    "username": "用户名",
    "email": "邮箱",
    "password": "密码"
  }
  ```
- **响应示例**:
  ```json
  {
    "code": 200,
    "message": "success",
    "data": "注册成功"
  }
  ```

## 🔐 权限系统

### 角色管理

- **管理员 (ADMIN)**: 首个注册用户自动获得管理员权限
- **普通用户 (USER)**: 后续注册用户默认为普通用户

### 权限验证

- 基于 Sa-Token 的权限验证机制
- 自定义权限接口实现 [StpInterfaceImpl](src/main/java/com/uniserv/auth/config/StpInterfaceImpl.java)

## 🗄️ 数据库设计

### 用户表 (users)

| 字段名           | 类型        | 描述     |
|---------------|-----------|--------|
| id            | UUID      | 用户唯一标识 |
| username      | VARCHAR   | 用户名    |
| email         | VARCHAR   | 邮箱     |
| password_hash | VARCHAR   | 加密后的密码 |
| roles         | VARCHAR   | 用户角色   |
| created_at    | TIMESTAMP | 创建时间   |
| updated_at    | TIMESTAMP | 更新时间   |
| is_active     | BOOLEAN   | 是否激活   |
| last_login    | TIMESTAMP | 上次登录时间 |

## 🛡️ 安全特性

- **密码加密**: 使用 BCrypt 算法对用户密码进行加密存储
- **会话管理**: 基于 Sa-Token 的安全会话管理
- **参数验证**: 使用 Jakarta Validation 进行请求参数校验
- **异常处理**: 统一的异常处理机制

## 📦 依赖关系

Uniserv-auth 模块依赖于:

- **Uniserv-common**: 提供公共组件，如结果封装、异常处理、配置等

## 🧪 使用示例

### 用户注册

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "newuser",
    "email": "newuser@example.com",
    "password": "password123"
  }'
```

### 用户登录

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "password123"
  }'
```

## 📖 重要类说明

- **[AuthController](src/main/java/com/uniserv/auth/controller/AuthController.java)**: 提供认证相关的 REST API
- **[UsersServiceImpl](src/main/java/com/uniserv/auth/service/impl/UsersServiceImpl.java)**: 实现用户认证、注册等业务逻辑
- **[Users](src/main/java/com/uniserv/auth/entity/Users.java)**: 用户实体类，映射数据库 users 表
- **[StpInterfaceImpl](src/main/java/com/uniserv/auth/config/StpInterfaceImpl.java)**: Sa-Token 权限接口实现，处理角色权限验证