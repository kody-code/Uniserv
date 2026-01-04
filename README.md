# 🚀 Uniserv - 个人服务后端系统

Uniserv 是一个功能全面的个人服务后端系统，采用现代化的技术栈构建，旨在提供一个统一的后端服务解决方案。系统集成了用户认证、权限管理、数据库操作等核心功能。

## 🛠️ 技术栈

- **Java**: Java 21
- **Spring Boot**: 3.5.9
- **MyBatis-Plus**: 3.5.15 (持久层框架)
- **Sa-Token**: 1.44.0 (权限认证框架)
- **PostgreSQL**: 数据库
- **Lombok**: 代码简化工具
- **Hutool**: Java工具库
- **Freemarker**: 代码生成器模板引擎

## ✨ 功能特性

### 👤 1. 用户管理

- 用户注册与登录
- 用户信息管理
- 密码加密存储（BCrypt）

### 🔐 2. 权限认证

- 基于 Sa-Token 的会话管理
- 统一的登录验证机制
- 管理员权限控制
- 令牌管理（UUID风格，有效期30天）

### 🧑‍💻 3. 代码生成

- 集成 MyBatis-Plus 代码生成器
- 支持自动生成 Entity、Mapper、Service 等代码
- 可配置生成策略

### 📝 4. 日志管理

- 完整的日志记录系统
- 分级日志输出
- 日志文件按时间和大小滚动

### 🛡️ 5. 异常处理

- 全局异常处理机制
- 统一的响应格式
- 业务异常和系统异常区分

## 📁 项目结构

```
src/
├── main/
│   ├── java/com/uniserv/
│   │   ├── auth/                 # 认证模块
│   │   │   ├── controller/       # 控制器层
│   │   │   ├── entity/           # 实体类
│   │   │   ├── mapper/           # 数据访问层
│   │   │   └── service/          # 业务逻辑层
│   │   └── common/               # 公共模块
│   │       ├── config/           # 配置类
│   │       ├── enums/            # 枚举类
│   │       ├── exception/        # 异常处理
│   │       ├── utils/            # 工具类
│   │       └── vo/               # 视图对象
│   └── resources/                # 配置文件
└── test/                         # 测试代码
```

## ⚙️ 配置说明

### 环境变量配置

系统需要配置以下环境变量：

- `DB_URL`: 数据库连接URL
- `DB_USER`: 数据库用户名
- `DB_PASSWORD`: 数据库密码

### Sa-Token 配置

在 `application.yml` 中配置了 Sa-Token 的相关参数：

```yaml
sa-token:
  token-name: uniserv-token    # 令牌名称
  timeout: 2592000             # 会话有效期（30天）
  is-concurrent: true          # 允许并发登录
  is-share: false              # 不共享token
  token-style: uuid            # 令牌风格
  is-log: true                 # 启用日志
  active-timeout: 604800       # 活跃超时时间（7天）
```

## 🔒 安全机制

### 权限验证规则

1. **登录验证**: 除登录接口外，所有路径都需要登录验证
2. **管理员权限**: 访问 `/api/admin/**` 路径需要具备 admin 权限

### 密码安全

- 使用 BCrypt 算法进行密码加密
- 提供密码加密和验证工具类

## 🛠️ 代码生成器

系统集成了 MyBatis-Plus 代码生成器，可以通过 [CodeGenerator](src/main/java/com/uniserv/CodeGenerator.java) 类快速生成基础的
CRUD 代码。

## 📤 API 响应格式

所有 API 接口返回统一格式的数据：

```json
{
  "code": 0,
  "message": "操作成功",
  "data": {}
}
```

## 🚀 开发环境搭建

1. 安装 Java 21
2. 安装 Maven
3. 安装 PostgreSQL
4. 配置环境变量（DB_URL, DB_USER, DB_PASSWORD）
5. 运行 `mvn clean install`
6. 运行 `mvn spring-boot:run`

## 📦 部署

### 打包

```bash
mvn clean package
```

### 运行

```bash
java -jar target/Uniserv-0.0.1.jar
```

## 📄 许可证

本项目采用 [MIT 许可证](LICENSE)

## 🤝 贡献

如果您希望为本项目做出贡献，请 fork 项目并提交 Pull Request。