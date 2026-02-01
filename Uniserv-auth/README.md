# 🔐 Uniserv-auth 用户认证模块

用户认证模块是Uniserv系统的核心安全组件，提供用户注册、登录、权限验证等功能。

## 📋 功能特性

- 👤 **用户注册** - 支持用户名和邮箱注册
- 🔐 **用户登录** - 提供安全的用户身份验证
- 👥 **角色管理** - 支持ADMIN和USER两种角色
- 🔑 **权限控制** - 基于Sa-Token的权限验证
- 🛡️ **安全加密** - 使用BCrypt加密用户密码
- 📨 **邮箱验证** - 支持邮箱格式验证

## 🏗️ 技术架构

- **认证框架**: Sa-Token
- **密码加密**: BCrypt
- **验证框架**: Spring Validation
- **数据持久化**: MyBatis-Plus + PostgreSQL

## 📁 项目结构

```
src/main/java/com/uniserv/auth/
├── config/                 # 配置类
│   └── StpInterfaceImpl.java   # Sa-Token权限接口实现
├── constant/               # 常量定义
│   └── ValidateConstant.java   # 验证常量
├── controller/             # 控制层
│   └── AuthController.java     # 认证控制器
├── dto/                    # 数据传输对象
│   ├── request/            # 请求DTO
│   │   ├── LoginRequestDto.java      # 登录请求
│   │   └── RegisterRequestDto.java   # 注册请求
│   └── response/           # 响应DTO
│       ├── LoginResponseDto.java     # 登录响应
│       ├── RegisterResponseDto.java  # 注册响应
│       └── TokenInfo.java            # 令牌信息
├── entity/                 # 实体类
│   └── SysUsers.java       # 系统用户实体
├── enums/                  # 枚举类
│   └── SysUserRoles.java   # 用户角色枚举
├── mapper/                 # 数据访问层
│   └── SysUsersMapper.java # 用户数据访问接口
├── service/                # 业务逻辑层
│   ├── impl/               # 业务实现
│   │   └── SysUsersServiceImpl.java  # 用户业务实现
│   └── ISysUsersService.java         # 用户业务接口
└── resources/
    ├── mapper/             # MyBatis映射文件
    │   └── SysUsersMapper.xml        # 用户映射配置
    └── application-auth.yml          # 认证模块配置
```

## 🚀 API接口

### 用户注册

- **接口**: `POST /api/auth/register`
- **功能**: 新用户注册
- **参数**: 用户名、密码、邮箱

### 用户登录

- **接口**: `POST /api/auth/login`
- **功能**: 用户身份验证
- **返回**: 用户信息和认证令牌

### 用户登出

- **接口**: `POST /api/auth/logout`
- **功能**: 用户退出登录

## 🔐 安全特性

- **密码加密**: 所有用户密码均使用BCrypt算法加密存储
- **会话管理**: 基于Sa-Token的分布式会话管理
- **权限验证**: 支持细粒度的权限控制
- **防暴力破解**: 可扩展的登录失败限制机制

## 🛠️ 配置说明

### Sa-Token配置

- **Token名称**: uniserv-token
- **有效期**: 7天
- **活跃期**: 2天
- **并发登录**: 支持同一账号多地登录
- **Token风格**: UUID格式

## 🧪 测试覆盖

- 用户注册/登录功能测试
- 权限验证逻辑测试
- 密码加密/验证测试
- 异常处理测试

## 📚 依赖关系

- **Uniserv-common**: 通用组件依赖
- **Spring Boot Web**: Web功能支持
- **MyBatis-Plus**: ORM框架
- **Sa-Token**: 认证授权框架
- **Lombok**: 代码简化工具