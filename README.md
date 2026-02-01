# 🏛️ Uniserv 统一服务系统

一个基于 Spring Boot 的综合性服务管理系统，采用模块化架构设计，提供用户认证、密码管理、账本管理等多种功能。

## 🚀 项目概述

Uniserv 是一个企业级服务管理系统，旨在提供一套完整的解决方案，包括用户认证、密码管理、财务账本等功能。该项目采用了现代化的技术栈和最佳实践，确保系统的可扩展性、安全性和易维护性。

## 📋 功能特性

- 🔐 **用户认证模块** - 提供用户注册、登录、权限管理功能
- 🗝️ **密码管理模块** - 安全的密码存储和管理，支持TOTP双因子认证
- 💰 **账本管理模块** - 个人或团队财务账本管理
- 🔄 **模块化架构** - 清晰的模块划分，便于扩展和维护
- 📊 **API文档** - 集成Swagger，提供完整的API文档
- 🔍 **链路追踪** - 支持分布式链路追踪，便于问题排查
- 🛡️ **安全防护** - 基于Sa-Token的身份认证和权限控制

## 🏗️ 技术架构

### 核心技术栈

- **基础框架**: Spring Boot 3.5.10
- **数据库**: PostgreSQL
- **ORM框架**: MyBatis-Plus
- **认证授权**: Sa-Token
- **API文档**: SpringDoc OpenAPI
- **模板引擎**: Freemarker
- **构建工具**: Maven

### 项目模块

```
Uniserv/
├── Uniserv-app/           # 主应用入口
├── Uniserv-auth/          # 用户认证模块
├── Uniserv-cipher/        # 密码管理模块
├── Uniserv-common/        # 通用组件模块
├── Uniserv-generator/     # 代码生成模块
└── Uniserv-ledger/        # 账本管理模块
```

#### 模块详情

- **[Uniserv-app](Uniserv-app/README.md)**: 项目的主应用程序模块，负责整合其他模块
- **[Uniserv-auth](Uniserv-auth/README.md)**: 用户认证模块，提供用户注册、登录、权限验证功能
- **[Uniserv-cipher](Uniserv-cipher/README.md)**: 密码管理模块，提供安全的密码存储、加密解密、TOTP双因子认证
- **[Uniserv-common](Uniserv-common/README.md)**: 通用组件模块，包含公共配置、工具类、异常处理等
- **[Uniserv-generator](Uniserv-generator/README.md)**: 代码生成模块，基于MyBatis-Plus的代码生成器
- **[Uniserv-ledger](Uniserv-ledger/README.md)**: 账本管理模块，提供财务账本管理功能

## 🛠️ 快速开始

### 环境要求

- Java 25+
- Maven 3.6+
- PostgreSQL 12+

### 本地部署

1. **克隆项目**

   ```bash
   git clone https://github.com/kody-code/Uniserv
   cd Uniserv
   ```

2. **配置数据库**

   在 `application-dev.yml` 中配置数据库连接：

   ```yaml
   spring:
     datasource:
       driver-class-name: org.postgresql.Driver
       url: jdbc:postgresql://localhost:5432/uniserv_db
       username: your_username
       password: your_password
   ```

3. **设置环境变量**

   ```bash
   export DB_URL=jdbc:postgresql://localhost:5432/uniserv_db
   export DB_USER=your_username
   export DB_PASSWORD=your_password
   ```

4. **编译项目**

   ```bash
   mvn clean install
   ```

5. **运行项目**

   ```bash
   cd Uniserv-app
   mvn spring-boot:run
   ```

### Docker部署（可选）

```bash
# 构建Docker镜像
docker build -t uniserv .

# 运行容器
docker run -d -p 8080:8080 \
  -e DB_URL=jdbc:postgresql://host:port/dbname \
  -e DB_USER=username \
  -e DB_PASSWORD=password \
  uniserv
```

## 🔐 认证与授权

项目采用Sa-Token进行身份认证和权限控制：

- **登录验证**: 所有需要认证的接口会自动验证token
- **角色权限**: 支持ADMIN和USER角色
- **Token配置**: 默认有效期7天，活跃期2天

## 📚 API文档

项目集成了SpringDoc OpenAPI，提供完整的REST API文档：

- **接口文档地址**: `http://localhost:8080/swagger-ui/index.html`
- **API文档地址**: `http://localhost:8080/v3/api-docs`

## 🔧 代码生成

项目内置代码生成器，位于`Uniserv-generator`模块，支持：

- 基于数据库表自动生成Entity、Mapper、Service、Controller等代码
- 使用Freemarker模板引擎，支持自定义模板
- 支持PostgreSQL数据库

## 📊 日志管理

- **日志格式**: 支持开发环境精简格式和生产环境完整格式
- **链路追踪**: 通过TraceId关联请求链路
- **日志分割**: 按时间和大小自动分割日志文件
- **日志级别**: 支持动态调整日志级别

## 🔒 安全特性

- **密码加密**: 使用BCrypt对用户密码进行加密存储
- **主密码保护**: 密码管理模块使用AES/CBC加密算法
- **双因子认证**: 支持TOTP双因子认证
- **输入验证**: 完整的参数校验和防注入措施
- **权限控制**: 基于角色的访问控制（RBAC）

## 🧪 测试覆盖

项目包含完整的单元测试：

- **业务逻辑测试**: 核心业务逻辑的单元测试
- **工具类测试**: 通用工具类的功能验证
- **异常处理测试**: 各种异常场景的处理验证

## 🚀 部署建议

### 生产环境配置

- 使用反向代理（如Nginx）提供HTTPS访问
- 配置Redis集群用于Sa-Token分布式认证
- 配置PostgreSQL主从复制保证高可用
- 启用防火墙限制不必要的端口访问

### 性能优化

- 配置Redis缓存减少数据库压力
- 合理设置数据库连接池参数
- 使用CDN加速静态资源访问
- 启用GZIP压缩减少网络传输

## 🤝 贡献指南

1. Fork 项目
2. 创建功能分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 创建 Pull Request

## 📄 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情

## 📞 支持

如果您有任何问题，请联系：

- 邮箱: zhaojiew.wang@gmail.com
- 项目主页: [https://github.com/kody-code/Uniserv](https://github.com/kody-code/Uniserv)

---
Made with ❤️ by Kody