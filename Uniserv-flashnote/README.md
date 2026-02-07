# 📝 Uniserv-flashnote

## 📋 模块概述

闪记管理模块，提供轻量级笔记记录和管理功能。

## ✅ 已完成功能

- [x] 闪记实体建模 (`FlashNoteRecords`)
- [x] Mapper接口定义
- [x] Service接口及实现
- [x] MyBatis-Plus基础CRUD支持
- [x] PostgreSQL数组类型支持(tags字段)

## 🔄 待完成功能

- [ ] 闪记创建接口
- [ ] 闪记查询接口(分页/搜索)
- [ ] 闪记更新接口
- [ ] 闪记删除接口
- [ ] 标签管理功能
- [ ] 内容搜索功能
- [ ] 数据统计分析

## 🛠️ 核心组件

### 实体类设计

```java
FlashNoteRecords {
    UUID noteId;        // 闪记ID
    UUID userId;        // 用户ID
    String content;     // 闪记内容
    List<String> tags;  // 标签列表
    LocalDateTime createdAt;  // 创建时间
    LocalDateTime updatedAt;  // 更新时间
    Boolean deleted;    // 逻辑删除标识
}
```

### 技术特性

- **UUID主键**: 使用UUID作为主键，保证全局唯一性
- **数组类型**: PostgreSQL数组类型存储标签
- **逻辑删除**: 支持软删除机制
- **时间追踪**: 自动维护创建和更新时间

## 🔧 技术栈

- Spring Boot 3.5
- MyBatis-Plus 3.5
- PostgreSQL (数组类型支持)
- Lombok

## 📊 数据库表结构

### flash_note_records (闪记表)

```sql
CREATE TABLE flash_note_records (
    note_id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    user_id UUID NOT NULL,
    content TEXT NOT NULL,
    tags TEXT[],              -- PostgreSQL数组类型
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    deleted_at TIMESTAMP
);
```

## 📈 功能规划

### MVP版本

- 基础CRUD操作
- 简单的内容管理
- 基本标签功能

### 进阶功能

- 全文搜索
- 标签云统计
- 按时间轴查看
- 数据导出功能

### 高级特性

- Markdown编辑器支持
- 图片附件上传
- 协作分享功能
- 模板管理

## 🎯 使用场景

- 快速记录想法和灵感
- 会议要点整理
- 学习笔记管理
- 任务备忘录
- 日常随笔记录

## 🔐 权限设计

- 用户只能查看和管理自己的闪记
- 管理员可查看所有用户的公开闪记
- 支持闪记设为私有/公开状态

## 📊 API接口规划

```
POST   /api/flashnote/create     # 创建闪记
GET    /api/flashnote/list       # 查询闪记列表(分页)
GET    /api/flashnote/{id}       # 获取单个闪记详情
PUT    /api/flashnote/{id}       # 更新闪记
DELETE /api/flashnote/{id}       # 删除闪记
GET    /api/flashnote/tags       # 获取标签列表
```

## ⚡ 性能优化方向

- 内容分页加载
- 标签索引优化
- 全文搜索引擎集成
- 缓存热点数据