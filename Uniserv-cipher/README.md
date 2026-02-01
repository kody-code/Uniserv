# 🗝️ Uniserv-cipher 密码管理模块

密码管理模块是Uniserv系统的重要组成部分，提供安全的密码存储、加密解密以及双因子认证功能。

## 📋 功能特性

- 🔐 **安全加密** - 使用AES/CBC算法加密存储密码
- 🗝️ **主密码管理** - 通过主密码保护所有子密码
- 🔄 **加密解密** - 支持密码的加密存储和安全解密
- 🛡️ **主密码验证** - 通过哈希验证主密码正确性
- 🔢 **TOTP双因子认证** - 支持基于时间的一次性密码
- 📝 **密码信息管理** - 支持网站、账号、邮箱、备注等详细信息
- 🗂️ **密码条目分类** - 按网站分类管理多个密码条目

## 🏗️ 技术架构

- **加密算法**: AES/CBC + PBKDF2 + SHA256
- **双因子认证**: Google Authenticator
- **数据持久化**: MyBatis-Plus + PostgreSQL
- **验证框架**: Spring Validation
- **序列化**: Jackson

## 📁 项目结构

```
src/main/java/com/uniserv/cipher/
├── controller/             # 控制层
│   ├── CipherPasswordEntryController.java    # 密码条目控制器
│   ├── CipherPasswordInfoController.java     # 密码信息控制器
│   └── CipherTotpEntryController.java        # TOTP条目控制器
├── dto/                    # 数据传输对象
│   ├── request/            # 请求DTO
│   │   ├── DecryptPasswordRequestDto.java    # 解密密码请求
│   │   ├── PasswordEntryRequestDto.java      # 密码条目请求
│   │   ├── PasswordInfoRequestDto.java       # 密码信息请求
│   │   └── TotpEntryRequestDto.java          # TOTP条目请求
│   └── response/           # 响应DTO
│       ├── DecryptPasswordResponseDto.java   # 解密密码响应
│       ├── PasswordEntryResponseDto.java     # 密码条目响应
│       └── TotpEntryResponseDto.java         # TOTP条目响应
├── entity/                 # 实体类
│   ├── CipherPasswordEntry.java              # 密码条目实体
│   └── CipherPasswordInfo.java               # 密码信息实体
├── mapper/                 # 数据访问层
│   ├── CipherPasswordEntryMapper.java        # 密码条目数据访问接口
│   └── CipherPasswordInfoMapper.java         # 密码信息数据访问接口
├── service/                # 业务逻辑层
│   ├── impl/               # 业务实现
│   │   ├── CipherPasswordEntryServiceImpl.java   # 密码条目业务实现
│   │   └── CipherPasswordInfoServiceImpl.java    # 密码信息业务实现
│   ├── ICipherPasswordEntryService.java          # 密码条目业务接口
│   └── ICipherPasswordInfoService.java           # 密码信息业务接口
├── utils/                  # 工具类
│   ├── PasswordEncryptUtil.java              # 密码加密工具
│   └── TotpGeneratorUtil.java                # TOTP生成工具
└── resources/
    ├── mapper/             # MyBatis映射文件
    │   ├── CipherPasswordEntryMapper.xml     # 密码条目映射配置
    │   └── CipherPasswordInfoMapper.xml      # 密码信息映射配置
    └── application-cipher.yml                # 密码模块配置
```

## 🚀 API接口

### 密码信息管理

#### 初始化密码信息

- **接口**: `GET /api/cipher/password/info/init`
- **功能**: 初始化密码管理信息，设置主密码
- **参数**: 主密码

#### 验证主密码

- **接口**: `GET /api/cipher/password/info/check`
- **功能**: 验证主密码是否正确
- **参数**: 主密码

#### 更新密码信息

- **接口**: `POST /api/cipher/password/info/update`
- **功能**: 更新主密码
- **参数**: 旧主密码、新主密码

#### 删除密码信息

- **接口**: `DELETE /api/cipher/password/info/delete`
- **功能**: 删除密码管理信息

### 密码条目管理

#### 添加密码条目

- **接口**: `POST /api/cipher/password/entry/add`
- **功能**: 添加新的密码条目
- **参数**: 主密码、网站名称、账号、密码、邮箱、网址、备注

#### 获取所有密码条目

- **接口**: `GET /api/cipher/password/entry/getAll`
- **功能**: 获取所有密码条目（需提供主密码）
- **参数**: 主密码

#### 解密密码

- **接口**: `POST /api/cipher/password/entry/decrypt`
- **功能**: 解密特定密码
- **参数**: 加密密码、主密码

#### 删除密码条目

- **接口**: `DELETE /api/cipher/password/entry/delete`
- **功能**: 删除指定密码条目
- **参数**: 条目ID

### TOTP管理

#### 获取TOTP令牌

- **接口**: `POST /api/cipher/totp/get`
- **功能**: 获取TOTP验证码
- **参数**: 密码ID

#### 添加TOTP令牌

- **接口**: `POST /api/cipher/totp/add`
- **功能**: 为密码条目添加TOTP令牌
- **参数**: 密码ID、TOTP令牌

#### 更新TOTP令牌

- **接口**: `POST /api/cipher/totp/update`
- **功能**: 更新TOTP令牌
- **参数**: 密码ID、TOTP令牌

#### 删除TOTP令牌

- **接口**: `DELETE /api/cipher/totp/delete`
- **功能**: 删除TOTP令牌
- **参数**: 密码ID

## 🔐 安全特性

### 加密机制

- **主密码验证**: 使用PBKDF2 + SHA256 + 盐值进行哈希验证
- **密码加密**: 使用AES/CBC模式加密存储密码
- **独立盐值**: 每个密码条目使用独立的盐值
- **随机IV**: 每次加密使用随机IV向量
- **常量时间比较**: 防止计时攻击

### 配置参数

- **PBKDF2迭代次数**: 65536（可配置）
- **密钥长度**: 256位（可配置）
- **盐值长度**: 16字节（可配置）

## 🛠️ 配置说明

### 加密配置

在 `application-cipher.yml` 中可配置加密参数：

```yaml
encrypt:
  pbkdf2:
    iterations: 65536    # 迭代次数，影响安全性
    key-length: 256      # 密钥长度
    salt-length: 16      # 盐值长度
```

## 🧪 测试覆盖

- 密码加密/解密功能测试
- 主密码验证逻辑测试
- TOTP生成验证测试
- 密码条目管理测试
- 异常处理测试

## 📚 依赖关系

- **Uniserv-common**: 通用组件依赖
- **Uniserv-auth**: 用户认证依赖
- **GoogleAuth**: TOTP双因子认证
- **MyBatis-Plus**: ORM框架
- **Lombok**: 代码简化工具