# 🏃‍♂️ Uniserv-app 主应用模块

主应用模块是Uniserv系统的启动入口，负责整合各个功能模块，提供统一的服务入口。

## 📋 功能特性

- 🚀 **应用启动** - 统一的应用程序启动入口
- 🧩 **模块整合** - 整合认证、密码管理、账本管理等模块
- 📡 **服务暴露** - 通过REST API暴露系统功能
- 📋 **配置管理** - 集中管理应用配置
- 🔧 **环境配置** - 支持开发、测试、生产等多环境配置
- 📊 **监控集成** - 集成Spring Boot Actuator监控
- 🔄 **自动配置** - 自动扫描和配置组件

## 🏗️ 技术架构

- **基础框架**: Spring Boot 3.5.10
- **Web框架**: Spring MVC
- **数据访问**: MyBatis-Plus
- **监控**: Spring Boot Actuator
- **日志**: Logback
- **配置**: Spring Profile

## 📁 项目结构

```
src/main/
├── java/com/uniserv/app/
│   └── UniservApplication.java      # 应用启动类
└── resources/
    ├── application-dev.yml          # 开发环境配置
    ├── application.yml              # 主配置文件
    └── logback-spring.xml           # 日志配置
```

## 🚀 启动配置

### 主启动类

`UniservApplication.java` 是整个系统的启动入口，包含以下配置：

- **Spring Boot应用**: 使用`@SpringBootApplication`注解
- **组件扫描**: 使用`@ComponentScan("com.uniserv")`扫描所有组件
- **Mapper扫描**: 使用`@MapperScan("com.uniserv.**.mapper")`扫描所有Mapper
- **Sa-Token配置**: 启动时输出Sa-Token配置信息

### 配置文件

#### application.yml - 主配置文件

- **应用名称**: Uniserv
- **激活配置**: 开发环境(dev)
- **包含配置**: common, auth, cipher, ledger
- **MyBatis-Plus配置**:
    - 映射文件路径: classpath*:/mapper/**/*.xml
    - 下划线转驼峰: 启用
    - 类型处理器包: com.uniserv.common.handler
    - 逻辑删除配置: 使用deleted字段

#### application-dev.yml - 开发环境配置

- **数据源配置**:
    - 驱动: PostgreSQL Driver
    - URL: 从环境变量`DB_URL`获取
    - 用户名: 从环境变量`DB_USER`获取
    - 密码: 从环境变量`DB_PASSWORD`获取

#### logback-spring.xml - 日志配置

- **开发环境**:
    - 日志格式: 精简格式（仅显示时分秒）
    - 日志级别: DEBUG
    - 包含TraceId
- **生产环境**:
    - 日志格式: 完整格式（包含日期、进程ID等）
    - 日志级别: INFO
    - 包含TraceId
- **输出方式**:
    - 控制台输出: 带彩色编码
    - 文件输出: 按时间和大小分割
    - 错误日志: 单独输出到错误日志文件

## 🧩 模块依赖

### 核心依赖

- **Uniserv-common**: 通用组件模块
    - 提供公共配置、工具类、异常处理
    - 统一的API响应格式
    - Sa-Token和Swagger配置
- **Uniserv-auth**: 认证模块
    - 用户注册、登录功能
    - 权限验证和角色管理
    - 用户信息管理
- **Uniserv-cipher**: 密码管理模块
    - 安全的密码存储和管理
    - 加密解密功能
    - TOTP双因子认证
- **Uniserv-ledger**: 账本管理模块
    - 账本、账户、收支记录管理
    - 财务数据统计功能
    - 收支分类管理

### 依赖管理

所有依赖都在父POM中统一管理，确保版本一致性。

## 🔧 环境配置

### 开发环境 (dev)

- **数据源**: 从环境变量加载数据库配置
- **日志级别**: DEBUG
- **日志格式**: 精简格式
- **配置文件**: 加载common, auth, cipher, ledger配置

### 生产环境 (prod)

- **数据源**: 从配置中心或环境变量加载
- **日志级别**: INFO
- **日志格式**: 完整格式
- **监控**: 启用Actuator端点

## 📡 API端点

应用启动后提供以下API端点：

- **认证API**: `/api/auth/**` - 用户认证相关接口
- **密码管理API**: `/api/cipher/**` - 密码管理相关接口
- **账本管理API**: `/api/ledger/**` - 账本管理相关接口
- **文档API**: `/swagger-ui/**`, `/v3/api-docs/**` - API文档相关接口
- **监控API**: `/actuator/**` - 应用监控相关接口

## 🧪 启动验证

应用启动时会执行以下验证：

- **Sa-Token配置**: 输出Sa-Token配置信息
- **数据库连接**: 验证数据库连接正常
- **组件扫描**: 确保所有组件正确扫描和注入
- **配置加载**: 验证配置文件正确加载

## 🚀 部署方式

### 本地运行

```bash
mvn spring-boot:run
```

### 打包运行

```bash
# 打包
mvn clean package

# 运行
java -jar Uniserv-app-*.jar --spring.profiles.active=dev
```

### Docker部署

```bash
# 构建镜像
docker build -t uniserv-app .

# 运行容器
docker run -d -p 8080:8080 \
  -e DB_URL=jdbc:postgresql://host:port/dbname \
  -e DB_USER=username \
  -e DB_PASSWORD=password \
  -e SPRING_PROFILES_ACTIVE=prod \
  uniserv-app
```

## 🔍 监控与运维

### Actuator端点

- **健康检查**: `/actuator/health`
- **指标**: `/actuator/metrics`
- **配置信息**: `/actuator/configprops`
- **环境信息**: `/actuator/env`

### 日志管理

- **日志轮转**: 按大小和时间自动轮转
- **历史保留**: 保留30天日志历史
- **错误分离**: 错误日志单独存储
- **TraceId**: 每个请求都有唯一的追踪ID

## 📚 依赖关系

- **Spring Boot Starter Web**: Web功能支持
- **Spring Boot Starter Actuator**: 应用监控
- **MyBatis-Plus**: ORM框架
- **PostgreSQL Driver**: 数据库驱动
- **Lombok**: 代码简化工具
- **各功能模块**: Uniserv-common, Uniserv-auth, Uniserv-cipher, Uniserv-ledger