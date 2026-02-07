-- 启用UUID扩展
CREATE
EXTENSION IF NOT EXISTS "uuid-ossp";

-- ===================== 通用触发器函数（全局复用） =====================
-- 自动更新updated_at字段的触发器函数
CREATE
OR REPLACE FUNCTION update_updated_at()
    RETURNS TRIGGER
AS
$$
BEGIN
    NEW.updated_at
= CURRENT_TIMESTAMP;
RETURN NEW;
END;
$$
LANGUAGE plpgsql;

-- 自动更新deleted_at字段的触发器函数
CREATE
OR REPLACE FUNCTION set_deleted_at()
    RETURNS TRIGGER
AS
$$
BEGIN
    NEW.deleted_at
= CURRENT_TIMESTAMP;
RETURN NEW;
END;
$$
LANGUAGE plpgsql;

