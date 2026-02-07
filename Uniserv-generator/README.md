# 🛠️ Uniserv-generator

## 📋 模块概述

代码生成器模块，基于MyBatis-Plus Generator提供快速代码生成功能。

## ✅ 已完成功能

- [x] MyBatis-Plus代码生成器集成
- [x] PostgreSQL数据库支持
- [x] Freemarker模板引擎配置
- [x] 基础代码生成配置
- [x] Lombok和链式编程支持
- [x] ActiveRecord模式支持

## 🔄 待完成功能

- [ ] 自定义模板扩展
- [ ] 生成代码质量检查
- [ ] 批量表生成支持
- [ ] 生成历史记录管理
- [ ] 图形化界面配置

## 🛠️ 核心功能

### 代码生成能力

- Entity实体类生成
- Mapper接口及XML生成
- Service接口及实现类生成
- Controller控制器生成(RESTful风格)
- DTO数据传输对象生成

### 数据库支持

- PostgreSQL类型转换
- 关键字处理
- 自定义查询支持

### 生成特性

- Lombok注解支持
- 链式编程模式
- Swagger文档注解
- Validation参数校验注解

## 🔧 技术栈

- MyBatis-Plus Generator 3.5
- Freemarker 2.3
- PostgreSQL JDBC
- Spring Boot 3.5

## 📊 使用说明

### 运行方式

```bash
cd Uniserv-generator
./mvnw exec:java -Dexec.mainClass="com.kody.uniserv.generator.CodeGenerator"
```

### 配置要求

需要设置以下环境变量：

```bash
export DB_URL=jdbc:postgresql://localhost:5432/uniserv
export DB_USER=your_username
export DB_PASSWORD=your_password
```

### 交互流程

1. 输入生成代码的目标路径
2. 输入包名(如: flashnote)
3. 输入表名(all表示所有表，或逗号分隔的表名列表)
4. 自动生成相应代码

## ⚙️ 生成配置

### 全局配置

```java
.author("kody")           // 作者
.commentDate("yyyy-MM-dd") // 注释日期格式
```

### 策略配置

```java
.enableLombok()          // 启用Lombok
.enableChainModel()      // 启用链式编程
.idType(IdType.AUTO)     // ID生成策略
.enableTableFieldAnnotation() // 启用字段注解
.enableActiveRecord()    // 启用ActiveRecord模式
```

### 包配置

- entity: 实体类包
- mapper: Mapper接口包
- service: Service接口包
- serviceImpl: Service实现类包
- xml: XML映射文件

## 📁 生成文件结构

```
com.kody.uniserv.{module}
├── entity/
│   └── {EntityName}.java
├── mapper/
│   ├── {EntityName}Mapper.java
│   └── xml/{EntityName}Mapper.xml
├── service/
│   ├── I{EntityName}Service.java
│   └── impl/{EntityName}ServiceImpl.java
```

## 🎯 适用场景

- 新业务模块快速搭建
- 数据库表结构变更后的代码同步
- 标准化代码结构维护
- 减少重复性编码工作

## ⚠️ 注意事项

1. 生成前请确保数据库连接正常
2. 建议先在测试环境验证生成效果
3. 生成的代码可能需要手动调整业务逻辑
4. 注意备份重要代码避免被覆盖