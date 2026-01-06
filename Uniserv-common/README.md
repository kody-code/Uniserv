# Uniserv-common 模块

📦 **Uniserv 通用组件模块** - 提供项目共享的配置、枚举、异常处理、结果封装和工具类

## 📋 概述

Uniserv-common 是 Uniserv 项目的核心通用组件模块，提供项目中各个模块共享的通用功能，包括配置类、枚举定义、异常处理、结果封装和各种工具类。

## 🚀 功能特性

- ✅ **Sa-Token 配置** - 提供统一的权限验证配置
- ✅ **Swagger 配置** - 提供统一的API文档配置
- ✅ **统一结果封装** - 标准化API响应格式
- ✅ **全局异常处理** - 统一处理各类异常情况
- ✅ **密码加密工具** - 提供BCrypt和PBKDF2密码加密
- ✅ **UUID类型处理器** - 为PostgreSQL提供UUID支持

## 🏗️ 技术架构

### 核心依赖

- **Spring Boot** - 企业级应用开发框架
- **Sa-Token** - 轻量级 Java 权限认证框架
- **Swagger/OpenAPI** - API 文档自动生成
- **MyBatis-Plus** - 持久层框架增强
- **Hutool** - Java 工具集
- **Commons Codec** - 编码解码工具

## 📁 目录结构

```
Uniserv-common/
├── src/main/java/com/uniserv/common/
│   ├── config/                    # 配置类
│   │   ├── SaTokenConfigure.java  # Sa-Token 权限配置
│   │   └── SwaggerConfig.java     # Swagger API 文档配置
│   ├── enums/                     # 枚举类
│   │   └── ResultCode.java        # 响应码枚举
│   ├── exception/                 # 异常类
│   │   ├── BusinessException.java  # 业务异常
│   │   └── GlobalExceptionHandler.java  # 全局异常处理器
│   ├── handler/                   # 类型处理器
│   │   └── PostgresUUIDTypeHandler.java  # PostgreSQL UUID 类型处理器
│   ├── result/                    # 结果封装类
│   │   └── Result.java            # 统一结果返回类
│   └── utils/                     # 工具类
│       ├── BCryptPasswordUtils.java   # BCrypt 密码工具
│       └── PasswordEncryptUtil.java   # PBKDF2 密码加密工具
├── src/test/java/com/uniserv/common/
│   ├── result/                    # Result类测试
│   │   └── ResultTest.java        # 统一结果返回类测试
│   └── utils/                     # 工具类测试
│       ├── BCryptPasswordUtilsTest.java   # BCrypt 密码工具测试
│       └── PasswordEncryptUtilTest.java   # PBKDF2 密码加密工具测试
├── pom.xml                        # Maven 项目配置
└── README.md                      # 模块说明文档
```

## ⚙️ 配置说明

### Sa-Token 配置

[**SaTokenConfigure.java**](src/main/java/com/uniserv/common/config/SaTokenConfigure.java) 提供了全局的权限验证规则：

- 配置了无需权限验证的接口路径（如登录、注册、API文档等）
- 实现了管理员权限验证规则
- 使用拦截器实现注解式鉴权功能

### Swagger 配置

[**SwaggerConfig.java**](src/main/java/com/uniserv/common/config/SwaggerConfig.java) 提供了统一的API文档配置：

- 配置了API文档标题、版本和描述
- 设置了统一的认证令牌参数
- 提供了联系人信息

## 📊 枚举定义

### 响应码枚举

[**ResultCode.java**](src/main/java/com/uniserv/common/enums/ResultCode.java) 定义了系统的标准响应码：

| 枚举             | 状态码  | 描述   |
|----------------|------|------|
| SUCCESS        | 0    | 操作成功 |
| NOT_LOGIN      | 401  | 未登录  |
| SYSTEM_ERROR   | 500  | 系统异常 |
| BUSINESS_ERROR | 1000 | 业务异常 |
| AUTH_ERROR     | 2000 | 认证失败 |
| PARAM_ERROR    | 3000 | 参数错误 |

## 🛡️ 异常处理

### 全局异常处理

[**GlobalExceptionHandler.java**](src/main/java/com/uniserv/common/exception/GlobalExceptionHandler.java) 提供了统一的异常处理机制：

- **业务异常处理** - 处理自定义业务异常
- **登录异常处理** - 处理未登录异常
- **角色异常处理** - 处理角色验证异常
- **参数校验异常处理** - 处理RequestBody和RequestParam参数校验异常
- **系统异常处理** - 兜底处理系统异常

### 业务异常

[**BusinessException.java**](src/main/java/com/uniserv/common/exception/BusinessException.java) 是自定义业务异常类，支持自定义错误码和消息。

## 📤 结果封装

### 统一结果返回

[**Result.java**](src/main/java/com/uniserv/common/result/Result.java) 提供了标准化的API响应格式：

```json
{
  "code": 0,
  "message": "操作成功",
  "data": {}
}
```

## 🔐 安全工具

### BCrypt 密码工具

[**BCryptPasswordUtils.java**](src/main/java/com/uniserv/common/utils/BCryptPasswordUtils.java) 提供了BCrypt密码加密功能：

- `encrypt(String rawPassword)` - 对原始密码进行加密
- `verify(String rawPassword, String encryptedPassword)` - 验证密码是否匹配

### PBKDF2 密码加密工具

[**PasswordEncryptUtil.java**](src/main/java/com/uniserv/common/utils/PasswordEncryptUtil.java) 提供了基于PBKDF2的密码加密功能：

- `generateSalt()` - 生成随机盐值
- `encrypt(String plainPassword, String masterPassword, String salt)` - 加密密码
- `decrypt(String encryptedPassword, String masterPassword, String salt)` - 解密密码
- `generateVerifyHash(String masterPassword, String verifySalt)` - 生成验证哈希
- `verifyMasterPassword(String inputMasterPwd, String storedVerifyHash, String verifySalt)` - 验证主密码

## 🗄️ 类型处理器

### PostgreSQL UUID 处理器

[**PostgresUUIDTypeHandler.java**](src/main/java/com/uniserv/common/handler/PostgresUUIDTypeHandler.java)
为MyBatis提供了PostgreSQL的UUID类型支持，包括：

- 设置参数时的UUID处理
- 查询结果中UUID的获取
- 存储过程调用时的UUID处理

## 📦 依赖关系

Uniserv-common 模块依赖于:

- **Hutool** - 提供丰富的Java工具集
- **Commons Codec** - 提供编码解码功能

## 🔗 模块间关系

Uniserv-common 作为通用组件模块，被以下模块依赖:

- **Uniserv-auth** - 认证授权模块
- **Uniserv-app** - 应用主模块
- **Uniserv-generator** - 代码生成模块

## 📖 使用说明

### 在其他模块中使用

在其他模块的 pom.xml 文件中添加依赖：

```xml

<dependency>
    <groupId>com.uniserv</groupId>
    <artifactId>Uniserv-common</artifactId>
    <version>0.0.1</version>
</dependency>
```

### 统一结果返回示例

```java
class DemoController {
    public Result<?> demo() {
        // 成功返回
        Result.success(data);

        // 成功返回带消息
        Result.success("操作成功", data);

        // 错误返回
        Result.error(1000, "业务异常");
    }
}

```

### 异常处理示例

```java
// 抛出业务异常
throw new BusinessException(ResultCode.PARAM_ERROR);

// 或自定义异常
throw new

BusinessException(3000,"参数错误");
```

## 🛠️ 配置参数

PasswordEncryptUtil 支持以下可配置参数（通过Spring配置文件）：

- `encrypt.pbkdf2.iterations` - PBKDF2迭代次数（默认：65536）
- `encrypt.pbkdf2.key-length` - 密钥长度（默认：256）
- `encrypt.pbkdf2.salt-length` - 盐值长度（默认：16）

## 🧪 单元测试

Uniserv-common 模块包含了全面的单元测试，确保各个组件的稳定性和可靠性：

### 测试覆盖

- ✅ **Result 类测试** - [ResultTest.java](src/test/java/com/uniserv/common/result/ResultTest.java)
  包含10个测试用例，验证统一结果返回类的各种使用场景
    - 成功返回（带数据/不带数据）
    - 错误返回（带状态码和消息）
    - 泛型类型正确性
    - 字段可访问性
    - 序列化支持

- ✅ **BCrypt 密码工具测试
  ** - [BCryptPasswordUtilsTest.java](src/test/java/com/uniserv/common/utils/BCryptPasswordUtilsTest.java)
  包含3个测试用例，验证密码加密和验证功能
    - 正常密码加密测试
    - 空密码异常处理
    - 密码验证功能

- ✅ **PBKDF2 密码加密工具测试
  ** - [PasswordEncryptUtilTest.java](src/test/java/com/uniserv/common/utils/PasswordEncryptUtilTest.java)
  包含11个测试用例，验证加密解密功能
    - 盐值生成测试
    - 加密和解密功能测试
    - 验证哈希生成和验证
    - 错误处理（错误密码、错误盐值）
    - 空值处理
    - 数据完整性验证

### 运行测试

在项目根目录下执行以下命令运行所有单元测试：

```bash
mvn test
```
