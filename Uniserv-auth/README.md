# 🔐 Uniserv-auth

## 📋 模块概述

认证授权模块，提供用户管理、登录注册、权限控制等核心功能。

## ✅ 已完成功能

- [x] 用户实体建模 (`SysUsers`)
- [x] 用户角色枚举 (`SysUserRoles`)
- [x] BCrypt密码加密工具
- [x] 用户登录/注册/登出接口
- [x] Sa-Token集成配置
- [x] 用户分页查询功能
- [x] 角色权限管理
- [x] 登录时间更新机制

## 🔄 待完成功能

- [ ] 多因素认证(MFA)
- [ ] OAuth2集成
- [ ] 用户信息修改接口
- [ ] 密码重置功能
- [ ] 用户状态管理(启用/禁用)

## 🛠️ 核心组件

### 用户管理

- **实体类**: `SysUsers` - 用户基本信息
- **枚举类**: `SysUserRoles` - 管理员/普通用户
- **服务层**: `ISysUsersService` 及其实现

### 认证功能

- 登录验证(username/email + password)
- 注册功能(自动分配角色)
- 登出机制
- Session管理

### 安全特性

- BCrypt密码加密存储
- Sa-Token权限框架集成
- 角色权限验证
- 登录状态追踪

### 分页查询

```java
// 支持多种筛选条件
UserPageRequestDto {
    String username;    // 用户名模糊搜索
    String email;       // 邮箱模糊搜索  
    String role;        // 角色精确筛选
    Boolean isActive;   // 激活状态筛选
}
```

## 🔧 技术栈

- Spring Boot 3.5
- MyBatis-Plus 3.5
- PostgreSQL
- Sa-Token 1.44.0
- Hutool Crypto
- BCrypt

## 📊 数据库表结构

### sys_users (用户表)

```sql
CREATE TABLE sys_users (
    user_id UUID PRIMARY KEY,
    username VARCHAR(20) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    roles sys_user_roles DEFAULT 'USER',
    is_active BOOLEAN DEFAULT TRUE,
    last_login TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE
);
```

## 🔐 权限说明

- **ADMIN**: 管理员角色，可访问所有接口
- **USER**: 普通用户角色，仅可访问基础功能

## 📈 API接口

主要接口路径: `/api/auth/**`

- POST `/api/auth/login` - 用户登录
- POST `/api/auth/register` - 用户注册
- POST `/api/auth/logout` - 用户登出
- GET `/api/admin/user/list` - 管理员用户列表(分页)