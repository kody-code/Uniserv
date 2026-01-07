# Uniserv-cipher 模块

🔐 **Uniserv 密码管理模块** - 提供安全的密码存储、管理和访问功能

## 📋 概述

Uniserv-cipher 是 Uniserv 项目的密码管理模块，专注于安全的密码存储、管理和访问功能。该模块旨在提供一个安全的密码管理系统，可能包含TOTP（基于时间的一次性密码）等功能。

## ✅ 已完成的功能

- 📦 **模块结构** - 基础模块框架已建立
- 🔄 **模块集成** - 已集成到主项目构建系统中
- 📚 **依赖管理** - 集成Uniserv-common公共组件
- 🗄️ **数据映射器** - 包含PasswordEntry、PasswordInfo和TotpEntry的MyBatis映射器结构

## 🔄 待完成的功能

- 🛠️ **密码条目管理** - PasswordEntry实体和相关服务的实现
- 🛠️ **密码信息管理** - PasswordInfo实体和相关服务的实现
- 🛠️ **TOTP功能** - TotpEntry实体和相关服务的实现
- 🛠️ **数据访问层** - 完整的DAO/Repository层实现
- 🛠️ **业务逻辑层** - 完整的Service层实现
- 🛠️ **控制器层** - REST API控制器实现
- 🛠️ **安全功能** - 加密/解密功能实现
- 🛠️ **用户界面** - 密码管理前端界面
- 🛠️ **数据模型** - 完整的数据库表结构设计

## 🏗️ 技术架构

### 核心依赖

- **Spring Boot** - 企业级应用开发框架
- **MyBatis-Plus** - 持久层框架增强
- **Uniserv-common** - 通用组件模块（结果封装、异常处理、配置等）

## 📁 目录结构

```
Uniserv-cipher/
├── src/main/java/com/uniserv/cipher/
│   ├── controller/              # 控制器层
│   ├── entity/                  # 实体类
│   ├── mapper/                  # 数据访问层
│   │   ├── PasswordEntryMapper.java    # 密码条目数据访问接口
│   │   ├── PasswordInfoMapper.java     # 密码信息数据访问接口
│   │   └── TotpEntryMapper.java        # TOTP条目数据访问接口
│   ├── service/                 # 业务逻辑层
│   └── dto/                     # 数据传输对象
├── src/main/resources/mapper/   # MyBatis 映射文件
│   ├── PasswordEntryMapper.xml  # 密码条目SQL映射
│   ├── PasswordInfoMapper.xml   # 密码信息SQL映射
│   └── TotpEntryMapper.xml      # TOTP条目SQL映射
├── pom.xml                      # Maven 项目配置
└── README.md                    # 模块说明文档
```

## 📦 依赖关系

Uniserv-cipher 模块依赖于:

- **Uniserv-common**: 提供公共组件，如结果封装、异常处理、配置等

## 🔗 模块间关系

Uniserv-cipher 作为密码管理模块，可能与以下模块交互:

- **Uniserv-auth** - 用户认证模块（用于身份验证）
- **Uniserv-common** - 通用组件模块（提供基础功能）

## 🚀 预期功能

Uniserv-cipher 模块预期将提供以下功能:

- 🔐 **安全密码存储** - 使用加密算法安全存储密码
- 📝 **密码条目管理** - 创建、编辑、删除密码条目
- 🕐 **TOTP支持** - 支持基于时间的一次性密码功能
- 👥 **用户权限管理** - 控制对密码条目的访问权限
- 🗄️ **数据同步** - 支持密码数据的备份与同步
- 🔍 **密码检索** - 快速搜索和访问密码条目
- 🛡️ **安全审计** - 记录密码访问和修改日志

## 📝 备注

> ⚠️ **注意**: 此模块目前处于开发阶段，具体功能实现将在后续版本中完成。