# 💰 Uniserv-ledger 账本管理模块

账本管理模块是Uniserv系统中的财务管理组件，提供个人或团队的收入支出记录、账本分类管理等功能。

## 📋 功能特性

- 📊 **账本管理** - 支持创建和管理多个账本
- 💵 **收支记录** - 记录收入和支出明细
- 🏷️ **分类管理** - 支持收入支出类型分类
- 👤 **账户管理** - 管理不同的支付账户
- 📈 **数据分析** - 提供收支统计和分析
- 🔐 **权限控制** - 基于用户的账本访问控制
- 📅 **时间追踪** - 按时间段统计收支情况

## 🏗️ 技术架构

- **数据持久化**: MyBatis-Plus + PostgreSQL
- **验证框架**: Spring Validation
- **API文档**: Swagger/OpenAPI
- **认证授权**: Sa-Token

## 📁 项目结构

```
src/main/java/com/uniserv/ledger/
├── controller/                         # 控制层
│   ├── LedgerAccountController.java           # 账户控制器
│   ├── LedgerAccountRecordsController.java    # 账户记录控制器
│   ├── LedgerBooksController.java             # 账本控制器
│   └── LedgerExpenseIncomeTypesController.java # 收支类型控制器
├── entity/                             # 实体类
│   ├── LedgerAccount.java                     # 账户实体
│   ├── LedgerAccountRecords.java              # 账户记录实体
│   ├── LedgerBooks.java                       # 账本实体
│   └── LedgerExpenseIncomeTypes.java          # 收支类型实体
├── mapper/                             # 数据访问层
│   ├── LedgerAccountMapper.java               # 账户数据访问接口
│   ├── LedgerAccountRecordsMapper.java        # 账户记录数据访问接口
│   ├── LedgerBooksMapper.java                 # 账本数据访问接口
│   └── LedgerExpenseIncomeTypesMapper.java    # 收支类型数据访问接口
├── service/                            # 业务逻辑层
│   ├── impl/                           # 业务实现
│   │   ├── LedgerAccountRecordsServiceImpl.java    # 账户记录业务实现
│   │   ├── LedgerAccountServiceImpl.java           # 账户业务实现
│   │   ├── LedgerBooksServiceImpl.java             # 账本业务实现
│   │   └── LedgerExpenseIncomeTypesServiceImpl.java # 收支类型业务实现
│   ├── ILedgerAccountRecordsService.java          # 账户记录业务接口
│   ├── ILedgerAccountService.java                 # 账户业务接口
│   ├── ILedgerBooksService.java                   # 账本业务接口
│   └── ILedgerExpenseIncomeTypesService.java      # 收支类型业务接口
└── resources/mapper/                   # MyBatis映射文件
    ├── LedgerAccountMapper.xml                  # 账户映射配置
    ├── LedgerAccountRecordsMapper.xml           # 账户记录映射配置
    ├── LedgerBooksMapper.xml                    # 账本映射配置
    └── LedgerExpenseIncomeTypesMapper.xml       # 收支类型映射配置
```

## 🚀 API接口

### 账本管理

#### 创建账本

- **接口**: `POST /api/ledger/books/create`
- **功能**: 创建新的账本
- **参数**: 账本名称、描述、用户ID

#### 获取账本列表

- **接口**: `GET /api/ledger/books/list`
- **功能**: 获取用户的所有账本
- **参数**: 用户ID

#### 更新账本

- **接口**: `PUT /api/ledger/books/update`
- **功能**: 更新账本信息
- **参数**: 账本ID、新名称、新描述

#### 删除账本

- **接口**: `DELETE /api/ledger/books/delete`
- **功能**: 删除指定账本
- **参数**: 账本ID

### 账户管理

#### 创建账户

- **接口**: `POST /api/ledger/account/create`
- **功能**: 创建新的支付账户
- **参数**: 账户名称、初始余额、账户类型、所属账本ID

#### 获取账户列表

- **接口**: `GET /api/ledger/account/list`
- **功能**: 获取指定账本下的所有账户
- **参数**: 账本ID

#### 更新账户

- **接口**: `PUT /api/ledger/account/update`
- **功能**: 更新账户信息
- **参数**: 账户ID、新名称、新余额

### 收支类型管理

#### 创建收支类型

- **接口**: `POST /api/ledger/types/create`
- **功能**: 创建收入或支出类型
- **参数**: 类型名称、类型（收入/支出）、颜色、图标、所属账本ID

#### 获取收支类型列表

- **接口**: `GET /api/ledger/types/list`
- **功能**: 获取指定账本下的所有收支类型
- **参数**: 账本ID、类型（收入/支出）

#### 更新收支类型

- **接口**: `PUT /api/ledger/types/update`
- **功能**: 更新收支类型信息
- **参数**: 类型ID、新名称、颜色、图标

### 收支记录管理

#### 创建收支记录

- **接口**: `POST /api/ledger/records/create`
- **功能**: 创建收入或支出记录
- **参数**: 金额、账户ID、收支类型ID、备注、发生时间

#### 获取收支记录

- **接口**: `GET /api/ledger/records/list`
- **功能**: 获取指定条件下的收支记录
- **参数**: 账本ID、时间范围、收支类型

#### 更新收支记录

- **接口**: `PUT /api/ledger/records/update`
- **功能**: 更新收支记录信息
- **参数**: 记录ID、新金额、新账户ID、新类型ID、新备注

#### 删除收支记录

- **接口**: `DELETE /api/ledger/records/delete`
- **功能**: 删除指定收支记录
- **参数**: 记录ID

## 📊 数据模型

### 账本 (LedgerBooks)

- 账本ID
- 用户ID
- 账本名称
- 描述
- 创建时间
- 更新时间

### 账户 (LedgerAccount)

- 账户ID
- 账本ID
- 账户名称
- 当前余额
- 账户类型
- 创建时间
- 更新时间

### 收支类型 (LedgerExpenseIncomeTypes)

- 类型ID
- 账本ID
- 类型名称
- 类型（收入/支出）
- 颜色
- 图标
- 创建时间
- 更新时间

### 收支记录 (LedgerAccountRecords)

- 记录ID
- 账户ID
- 收支类型ID
- 金额
- 备注
- 发生时间
- 创建时间
- 更新时间

## 🔐 安全特性

- **用户隔离**: 每个用户的账本数据相互隔离
- **权限控制**: 基于Sa-Token的角色权限验证
- **数据完整性**: 使用数据库约束保证数据一致性
- **逻辑删除**: 重要数据使用逻辑删除而非物理删除

## 🧪 测试覆盖

- 账本创建、查询、更新、删除功能测试
- 账户管理功能测试
- 收支类型管理测试
- 收支记录管理测试
- 数据验证和异常处理测试

## 📚 依赖关系

- **Uniserv-common**: 通用组件依赖
- **Uniserv-auth**: 用户认证依赖
- **MyBatis-Plus**: ORM框架
- **Lombok**: 代码简化工具