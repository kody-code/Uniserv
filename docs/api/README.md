# 📘 UniServ API 文档

欢迎使用 UniServ 系统 API 文档！本文档包含了所有可用的 API 接口说明和技术细节。

## 📚 文档目录

### 🔐 认证模块

- [认证接口文档](auth-api.md) - 用户登录、注册、登出等相关接口

### 🔧 管理员模块

- [管理员用户管理API](admin-user-api.md) - 用户信息分页查询接口 ✨ *新增*

### 🔐 密码管理模块

- [密码管理接口文档](cipher-api.md) - 密码条目管理相关接口

### 📖 账本模块

- [账本接口文档](ledger-api.md) - 账单管理相关接口

## 🚀 快速开始

### 环境准备

- JDK 25+
- PostgreSQL 数据库
- Redis (可选)

### 基础配置

1. 配置数据库连接信息
2. 设置环境变量：
   ```bash
   export DB_URL=jdbc:postgresql://localhost:5432/uniserv
   export DB_USER=your_username
   export DB_PASSWORD=your_password
   ```

### 启动服务

```bash
./mvnw spring-boot:run
```

服务启动后，可通过以下地址访问：

- API文档: http://localhost:8080/swagger-ui.html
- 健康检查: http://localhost:8080/actuator/health

## 🔑 认证机制

系统采用 Sa-Token 进行身份认证和权限管理：

1. **登录获取Token**: 调用 `/api/auth/login` 接口
2. **携带Token**: 在请求头中添加 `uniserv-token: your_token`
3. **权限验证**: 系统自动验证Token有效性和用户权限

## 📊 通用响应格式

所有API接口均返回统一的JSON格式：

```json
{
  "code": 0,
  "message": "操作成功",
  "data": {},
  "timestamp": 1738923456789
}
```

### 响应码说明

| 状态码 | 说明      |
|-----|---------|
| 0   | 操作成功    |
| 400 | 参数错误    |
| 401 | 未认证     |
| 403 | 无权限     |
| 500 | 服务器内部错误 |

## 🛠️ 开发工具

### API测试

推荐使用以下工具进行API测试：

- **Postman**: 功能强大的API测试工具
- **Insomnia**: 轻量级API客户端
- **curl**: 命令行HTTP客户端

### 代码生成

项目包含代码生成器，可快速生成CRUD代码：

```bash
cd Uniserv-generator
./mvnw exec:java -Dexec.mainClass="com.kody.uniserv.generator.CodeGenerator"
```

## 📞 技术支持

如有问题，请联系：

- 邮箱: zhaojiew.wang@gmail.com
- GitHub: https://github.com/your-repo

## 📝 版本历史

### v1.0.0 (2026-02-07)

- 🎉 初始版本发布
- ✨ 实现基础认证功能
- ✨ 新增管理员用户分页查询接口
- 🛠️ 集成MyBatis-Plus分页功能
- 📚 完善API文档体系

---
*文档最后更新时间: 2026-02-07*