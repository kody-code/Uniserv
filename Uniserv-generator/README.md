# 🛠️ Uniserv-generator 代码生成模块

代码生成模块是Uniserv系统的开发辅助工具，基于MyBatis-Plus Generator提供快速的代码生成功能。

## 📋 功能特性

- 🚀 **快速生成** - 基于数据库表快速生成全套代码
- 🧱 **完整组件** - 生成Entity、Mapper、Service、Controller等组件
- 🎨 **模板定制** - 支持Freemarker模板自定义
- 🗄️ **数据库支持** - 支持PostgreSQL数据库
- 📁 **目录管理** - 智能生成到指定目录结构
- ⚙️ **配置灵活** - 支持多种生成策略和配置选项
- 🔧 **可扩展性** - 支持自定义模板和生成规则

## 🏗️ 技术架构

- **代码生成器**: MyBatis-Plus Generator
- **模板引擎**: Freemarker
- **数据库适配**: PostgreSQL Query/TypeConvert
- **关键词处理**: PostgreSQL关键词处理器
- **构建工具**: Maven

## 📁 项目结构

```
src/main/java/com/uniserv/generator/
└── CodeGenerator.java      # 代码生成器主类

pom.xml                     # Maven配置文件
```

## 🚀 使用方法

### 环境准备

在使用代码生成器前，请确保设置了以下环境变量：

```bash
export DB_URL=jdbc:postgresql://localhost:5432/database_name
export DB_USER=username
export DB_PASSWORD=password
```

### 运行代码生成器

1. **启动生成器**: 运行`CodeGenerator.main()`方法
2. **选择路径**: 输入代码生成的目标路径
3. **输入包名**: 输入要生成的包名
4. **选择表**: 输入要生成代码的表名（多个表用逗号分隔），输入"all"生成所有表

### 生成内容

代码生成器将自动生成以下内容：

- **Entity**: 实体类，使用Lombok注解简化代码
- **Mapper**: 数据访问接口
- **Service**: 业务逻辑接口
- **ServiceImpl**: 业务逻辑实现类
- **Controller**: REST控制器
- **Mapper XML**: MyBatis映射文件

### 生成特性

- **Lombok支持**: 自动添加Lombok注解
- **链式调用**: 支持链式调用模型
- **主键策略**: 自动配置主键生成策略
- **字段注解**: 自动添加字段注解
- **活动记录**: 支持活动记录模式
- **驼峰转换**: 自动进行下划线转驼峰

## ⚙️ 配置说明

### 数据库配置

- **驱动**: PostgreSQL JDBC Driver
- **查询器**: PostgreSqlQuery
- **类型转换**: PostgreSqlTypeConvert
- **关键词处理**: PostgreSqlKeyWordsHandler

### 代码生成配置

- **作者**: kody
- **输出目录**: `/src/main/java` 和 `/src/main/resources/mapper`
- **日期格式**: yyyy-MM-dd
- **ID类型**: AUTO（自增长）

### 生成策略

- **命名策略**: 驼峰命名转换
- **表包含**: 支持指定特定表或全部表
- **注解启用**: 启用表字段注解
- **链式模型**: 启用链式setter方法

## 🛠️ 扩展定制

### 自定义模板

可通过替换Freemarker模板来自定义生成的代码格式：

1. 在`/src/main/resources/templates`目录下放置自定义模板
2. 模板文件命名遵循MyBatis-Plus Generator规范
3. 在生成器中指定模板路径

### 自定义配置

可在`CodeGenerator`类中修改以下配置：

- 输出目录路径
- 作者名称
- 命名策略
- 注释日期格式
- 代码生成策略

## 🧪 测试覆盖

虽然代码生成模块主要为开发工具，但其核心功能经过验证：

- 数据库连接测试
- 表信息查询测试
- 代码生成路径测试
- 文件输出验证

## 📚 依赖关系

- **MyBatis-Plus Generator**: 核心代码生成框架
- **Freemarker**: 模板引擎
- **PostgreSQL Driver**: 数据库驱动
- **Uniserv-common**: 通用组件依赖
- **Lombok**: 代码简化工具

## 🚀 最佳实践

1. **数据库设计优先**: 在生成代码前，先完成数据库表结构设计
2. **命名规范**: 遵循数据库命名规范，便于生成清晰的代码
3. **权限确认**: 确认数据库用户有足够的读取表结构权限
4. **备份重要代码**: 生成代码前备份已有代码，避免覆盖
5. **生成后审查**: 生成代码后进行审查和必要的修改

## ⚠️ 注意事项

- 生成器仅用于初始开发阶段，不应用于生产环境直接修改
- 生成的代码可能需要根据业务需求进行调整
- 请确保数据库连接信息正确且安全
- 生成的代码应遵循项目编码规范