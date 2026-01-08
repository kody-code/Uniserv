# Uniserv-generator 模块

⚙️ **Uniserv 代码生成模块** - 基于MyBatis-Plus的自动化代码生成工具

## 📋 概述

Uniserv-generator 是 Uniserv 项目的代码生成模块，基于 MyBatis-Plus Generator 和 Freemarker
模板引擎构建，用于自动生成实体类、数据访问层、服务层和控制器等基础代码。

## 🚀 功能特性

- ✅ **自动化代码生成** - 根据数据库表结构自动生成代码
- ✅ **多层代码生成** - 支持实体类、Mapper、Service、Controller等多层代码生成
- ✅ **Lombok集成** - 自动生成Lombok注解简化代码
- ✅ **REST风格支持** - 生成REST风格的控制器
- ✅ **链式调用支持** - 支持链式调用模式
- ✅ **字段注解支持** - 为表字段添加注解

## 🏗️ 技术架构

### 核心依赖

- **MyBatis-Plus Generator** - 代码生成核心引擎
- **Freemarker** - 模板引擎
- **Uniserv-common** - 公共组件模块

## 📁 目录结构

```
Uniserv-generator/
├── src/main/java/com/uniserv/generator/
│   └── CodeGenerator.java         # 代码生成器主类
├── pom.xml                        # Maven 项目配置
└── README.md                      # 模块说明文档
```

## ⚙️ 配置说明

### 代码生成器配置

[**CodeGenerator.java**](/src/main/java/com/uniserv/generator/CodeGenerator.java) 包含以下配置：

- **输出目录**:
    - Java文件: `/Uniserv-auth/src/main/java`
    - XML文件: `/Uniserv-auth/src/main/resources/mapper`

- **数据库配置**:
    - 通过环境变量获取数据库连接信息
    - `DB_URL`: 数据库连接URL
    - `DB_USER`: 数据库用户名
    - `DB_PASSWORD`: 数据库密码

- **包配置**:
    - 父包名: `com.uniserv.auth`
    - 实体类包: `entity`
    - Mapper包: `mapper`
    - Service包: `service`
    - ServiceImpl包: `service.impl`
    - XML包: `mapper.xml`

- **策略配置**:
    - 包含表: `users`
    - 启用Lombok
    - 启用链式模型
    - 启用表字段注解
    - 禁用序列化ID
    - 启用连字符风格
    - 启用REST风格

## 🔧 使用方法

### 环境变量设置

在运行代码生成器之前，需要设置以下环境变量：

```bash
export DB_URL=jdbc:postgresql://localhost:5432/uniserv_db
export DB_USER=your_username
export DB_PASSWORD=your_password
```

## 📦 依赖关系

Uniserv-generator 模块依赖于:

- **MyBatis-Plus Generator**: 提供代码生成核心功能
- **Freemarker**: 提供模板引擎功能

## 🎯 生成内容

代码生成器将为指定的数据库表生成以下内容：

- **实体类** - 带有Lombok注解的实体类
- **Mapper接口** - 数据访问层接口
- **Service接口** - 业务逻辑层接口
- **Service实现类** - 业务逻辑层实现
- **Controller类** - REST控制器
- **Mapper XML文件** - SQL映射文件

## 🛠️ 生成示例

对于`users`表，代码生成器将生成:

- `com.uniserv.auth.entity.Users` - 用户实体类
- `com.uniserv.auth.mapper.UsersMapper` - 用户数据访问接口
- `com.uniserv.auth.service.IUsersService` - 用户服务接口
- `com.uniserv.auth.service.impl.UsersServiceImpl` - 用户服务实现
- `com.uniserv.auth.controller.UsersController` - 用户控制器
- `UsersMapper.xml` - 用户SQL映射文件

## 📝 注意事项

- 生成的代码将输出到指定目录，请确保目录存在且有写入权限
- 数据库连接信息通过环境变量配置，请确保环境变量已正确设置
- 当前配置仅生成`users`表的代码，如需生成其他表的代码，请修改`addInclude`方法
- 生成的代码可能需要根据具体业务需求进行调整和扩展