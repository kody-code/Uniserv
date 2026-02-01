# 🧩 Uniserv-common 通用组件模块

通用组件模块是Uniserv系统的基础支撑模块，提供公共配置、工具类、异常处理等通用功能。

## 📋 功能特性

- ⚙️ **公共配置** - 统一的系统配置管理
- 🛡️ **认证配置** - Sa-Token权限认证配置
- 📝 **API文档** - Swagger/OpenAPI配置
- 🔄 **链路追踪** - 分布式链路追踪配置
- 🛠️ **工具类** - 常用工具类集合
- ❗ **异常处理** - 全局异常处理机制
- 📊 **枚举定义** - 统一的状态码和枚举定义
- 🔧 **处理器** - 自定义类型处理器

## 🏗️ 技术架构

- **Web框架**: Spring MVC
- **API文档**: SpringDoc OpenAPI
- **认证框架**: Sa-Token
- **工具库**: Hutool、Commons Codec
- **日志框架**: SLF4J + Logback

## 📁 项目结构

```
src/main/java/com/uniserv/common/
├── config/                   # 配置类
│   ├── SaTokenConfigure.java     # Sa-Token配置
│   ├── SwaggerConfig.java        # Swagger配置
│   └── TraceIdConfig.java        # 链路追踪配置
├── enums/                    # 枚举类
│   └── ResultCode.java           # 响应结果枚举
├── exception/                # 异常处理
│   ├── BusinessException.java     # 业务异常类
│   └── GlobalExceptionHandler.java # 全局异常处理器
├── handler/                  # 处理器
│   ├── PostgresUUIDTypeHandler.java # PostgreSQL UUID类型处理器
│   └── TraceIdInterceptor.java     # 链路追踪拦截器
└── utils/                    # 工具类
    ├── BCryptPasswordUtils.java    # BCrypt密码工具
    ├── ResultUtils.java            # 统一结果工具
    └── TraceIdUtils.java           # 链路追踪工具
```

## 🛠️ 核心功能

### Sa-Token配置

- **登录验证**: 全局登录验证，无需在每个方法上添加注解
- **路径排除**: 自动排除认证相关的登录、注册等接口
- **角色验证**: 支持管理员权限验证
- **拦截器**: 配置Sa-Token拦截器，开启注解式鉴权

### Swagger配置

- **API文档**: 自动生成RESTful API文档
- **分组管理**: 按模块分组展示API接口
- **认证配置**: 集成token认证，方便接口调试
- **文档信息**: 包含项目基本信息和联系人信息

### 链路追踪配置

- **TraceId生成**: 自动为每个请求生成唯一追踪ID
- **跨服务透传**: 支持TraceId在服务间传递
- **日志集成**: 将TraceId集成到日志系统中
- **响应头**: 将TraceId写入响应头便于前端获取

### 公共工具类

- **密码工具**: BCrypt密码加密和验证工具
- **结果工具**: 统一的API响应结果封装
- **追踪工具**: 链路追踪ID管理工具
- **UUID处理器**: PostgreSQL的UUID类型处理

## 📊 枚举定义

### ResultCode - 响应结果枚举

- **SUCCESS**: 操作成功 (0)
- **NOT_LOGIN**: 未登录 (401)
- **SYSTEM_ERROR**: 系统异常 (500)
- **BUSINESS_ERROR**: 业务异常 (1000)
- **OPERATION_ERROR**: 操作失败 (2000)
- **NOT_FOUND_ERROR**: 未找到 (3000)
- **AUTH_ERROR**: 认证失败 (2000)
- **PARAM_ERROR**: 参数错误 (3000)

## ❗ 异常处理

### BusinessException - 业务异常

- **自定义错误码**: 支持自定义错误码和消息
- **枚举构造**: 支持通过ResultCode枚举构造异常

### GlobalExceptionHandler - 全局异常处理器

- **业务异常**: 处理自定义业务异常
- **认证异常**: 处理未登录和角色异常
- **参数校验**: 处理RequestBody和RequestParam参数校验异常
- **系统异常**: 处理兜底的系统异常
- **日志记录**: 自动记录异常日志

## 🔧 处理器

### PostgresUUIDTypeHandler

- **UUID处理**: 专门处理PostgreSQL的UUID类型
- **类型映射**: 自动映射Java UUID和PostgreSQL UUID
- **多种操作**: 支持设置参数、获取结果等操作

### TraceIdInterceptor

- **请求拦截**: 拦截所有HTTP请求
- **ID生成**: 生成或获取TraceId
- **上下文管理**: 将TraceId放入MDC上下文
- **响应传递**: 将TraceId写入响应头

## 🧪 测试覆盖

src/test/java/com/uniserv/common/ - 包含完整的单元测试：

- **异常测试**: BusinessException和GlobalExceptionHandler的全面测试
- **工具测试**: BCryptPasswordUtils、ResultUtils、TraceIdUtils的功能测试
- **边界测试**: 各种异常情况和边界条件的测试
- **性能测试**: 关键功能的性能验证

## 📚 依赖关系

- **Hutool**: 提供丰富的Java工具类
- **Commons Codec**: 编码解码工具
- **Spring Boot**: Web和配置支持
- **Lombok**: 代码简化工具
- **SLF4J**: 日志门面

## 🔗 模块依赖

其他模块通过依赖Uniserv-common获得：

- 统一的异常处理机制
- 公共工具类
- 认证配置
- API文档配置
- 链路追踪能力