# 🛠️ 管理员用户管理API

## 📋 模块概述

管理员用户管理模块提供用户信息的分页查询功能，支持多种筛选条件，便于管理员对系统用户进行全面管理。

## 🔧 技术栈

- **框架**: Spring Boot 3.5.10
- **ORM**: MyBatis-Plus 3.5.15
- **数据库**: PostgreSQL
- **分页**: MyBatis-Plus Pagination
- **文档**: SpringDoc OpenAPI 3

## 📡 API接口详情

### 🔍 分页获取用户列表

**接口地址**: `GET /api/admin/user/list`

**接口描述**: 管理员分页获取所有用户信息，支持多种筛选条件

#### 请求参数

| 参数名      | 类型      | 必填 | 描述       | 示例                |
|----------|---------|----|----------|-------------------|
| pageNum  | Integer | 是  | 当前页码（≥1） | 1                 |
| pageSize | Integer | 是  | 每页条数（≥1） | 10                |
| username | String  | 否  | 用户名搜索关键词 | admin             |
| email    | String  | 否  | 邮箱搜索关键词  | admin@example.com |
| role     | String  | 否  | 用户角色筛选   | ADMIN             |
| isActive | Boolean | 否  | 是否激活状态筛选 | true              |

#### 请求示例

```bash
curl -X GET "http://localhost:8080/api/admin/user/list?pageNum=1&pageSize=10&isActive=true" \
  -H "uniserv-token: your_token_here"
```

#### 响应格式

```json
{
  "code": 0,
  "message": "操作成功",
  "data": {
    "total": 15,
    "pageNum": 1,
    "pageSize": 10,
    "pages": 2,
    "records": [
      {
        "userId": "04b552f9-8379-4047-9754-9a67c11f39d7",
        "username": "admin",
        "email": "admin@example.com",
        "roles": "ADMIN",
        "isActive": true,
        "lastLogin": "2026-02-07T10:30:00",
        "createdAt": "2026-02-07T10:00:00",
        "updatedAt": "2026-02-07T10:30:00"
      }
    ]
  },
  "timestamp": 1738923456789
}
```

#### 响应字段说明

| 字段        | 类型       | 描述     |
|-----------|----------|--------|
| total     | Long     | 总记录数   |
| pageNum   | Long     | 当前页码   |
| pageSize  | Long     | 每页条数   |
| pages     | Long     | 总页数    |
| records   | Array    | 用户信息列表 |
| userId    | UUID     | 用户ID   |
| username  | String   | 用户名    |
| email     | String   | 邮箱     |
| roles     | String   | 用户角色   |
| isActive  | Boolean  | 是否激活   |
| lastLogin | DateTime | 上次登录时间 |
| createdAt | DateTime | 创建时间   |
| updatedAt | DateTime | 更新时间   |

## 📊 数据模型

### UserPageRequestDto (用户分页查询请求)

继承自 `PageRequestDto`，包含分页基础参数和业务查询字段。

### UserInfoDto (用户信息响应)

用户信息的核心数据传输对象，隐藏敏感信息如密码。

## ⚠️ 错误码说明

| 错误码 | 描述    | 解决方案          |
|-----|-------|---------------|
| 0   | 操作成功  | -             |
| 400 | 参数错误  | 检查请求参数是否符合要求  |
| 401 | 请先登录  | 提供有效的认证令牌     |
| 403 | 无权限访问 | 确认当前用户具有管理员权限 |

## 🔐 权限要求

- 需要管理员角色权限才能访问
- 必须提供有效的认证令牌

## 🔄 使用示例

### JavaScript (axios)

```javascript
const getUsers = async (pageNum = 1, pageSize = 10, filters = {}) => {
    try {
        const response = await axios.get('/api/admin/user/list', {
            params: {
                pageNum,
                pageSize,
                ...filters
            },
            headers: {
                'uniserv-token': localStorage.getItem('token')
            }
        });

        if (response.data.code === 0) {
            console.log('用户列表:', response.data.data);
            return response.data.data;
        }
    } catch (error) {
        console.error('获取用户列表失败:', error);
    }
};

// 使用示例
getUsers(1, 10, {isActive: true, role: 'ADMIN'});
```

### Python (requests)

```python
import requests

def get_users(page_num=1, page_size=10, filters=None):
    if filters is None:
        filters = {}
    
    params = {
        'pageNum': page_num,
        'pageSize': page_size,
        **filters
    }
    
    headers = {
        'uniserv-token': 'your_token_here'
    }
    
    response = requests.get(
        'http://localhost:8080/api/admin/user/list',
        params=params,
        headers=headers
    )
    
    if response.json()['code'] == 0:
        return response.json()['data']
    else:
        raise Exception(response.json()['message'])
```

## 📈 性能优化建议

1. **索引优化**: 确保数据库中对常用查询字段建立索引
2. **缓存策略**: 对频繁查询的结果考虑使用Redis缓存
3. **分页限制**: 建议pageSize不超过100条记录
4. **字段精简**: 只返回必要的字段信息

## 🛡️ 安全注意事项

- 敏感信息（如密码）不会通过API暴露
- 所有请求都需要身份验证
- 支持基于角色的访问控制(RBAC)
- 请求参数会进行严格的输入验证