-- ===================== 枚举 =====================
CREATE TYPE sys_user_roles AS ENUM ('ADMIN', 'USER');
ALTER TYPE sys_user_roles OWNER TO uniserv;
COMMENT
    ON type sys_user_roles IS '用户角色（ADMIN=管理员，USER=普通用户）';

-- ===================== 表结构 =====================
-- 用户表
CREATE TABLE IF NOT EXISTS sys_users
(
    user_id
                  uuid
                                 DEFAULT
                                     uuid_generate_v4
                                     (
                                     )                          NOT NULL PRIMARY KEY,
    username      varchar(20)                                   NOT NULL UNIQUE,
    email         varchar(255)                                  NOT NULL UNIQUE,
    password_hash varchar(255)                                  NOT NULL,
    roles         sys_user_roles DEFAULT 'USER'::sys_user_roles NOT NULL,
    is_active     boolean        DEFAULT TRUE,
    last_login    timestamp,
    created_at    timestamp      DEFAULT CURRENT_TIMESTAMP,
    updated_at    timestamp      DEFAULT CURRENT_TIMESTAMP,
    deleted       boolean        DEFAULT FALSE,
    deleted_at    timestamp
);

-- 操作日志表
CREATE TABLE IF NOT EXISTS sys_operate_logs
(
    log_id
               uuid
                         DEFAULT
                             uuid_generate_v4
                             (
                             ) NOT NULL PRIMARY KEY,
    user_id    uuid            NOT NULL,
    operation  text            NOT NULL,
    ip         varchar(20)     NOT NULL,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp DEFAULT CURRENT_TIMESTAMP,
    deleted    boolean   DEFAULT FALSE,
    deleted_at timestamp
);

-- ===================== 约束 =====================
-- 用户表
-- 邮箱格式校验约束：正则拦截无效邮箱，避免脏数据
ALTER TABLE sys_users
    ADD CONSTRAINT chk_valid_email CHECK (email ~* '^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+[.][A-Za-z]{2,}$');

-- ===================== 表触发器 =====================
-- 用户表
-- updated_at 触发器
CREATE TRIGGER trigger_sys_users_updated_at
    BEFORE UPDATE
    ON sys_users
    FOR EACH ROW
EXECUTE FUNCTION update_updated_at();
-- set_deleted_at 触发器
CREATE TRIGGER trigger_sys_users_deleted_at
    BEFORE UPDATE
    ON sys_users
    FOR EACH ROW
EXECUTE FUNCTION set_deleted_at();

-- 操作日志表
-- updated_at 触发器
CREATE TRIGGER trigger_sys_operate_logs_updated_at
    BEFORE UPDATE
    ON sys_operate_logs
    FOR EACH ROW
EXECUTE FUNCTION update_updated_at();
-- set_deleted_at 触发器
CREATE TRIGGER trigger_sys_operate_logs_deleted_at
    BEFORE UPDATE
    ON sys_operate_logs
    FOR EACH ROW
EXECUTE FUNCTION set_deleted_at();

-- ===================== 索引优化 =====================
CREATE INDEX IF NOT EXISTS idx_sys_users_user_id ON sys_users (user_id);
CREATE INDEX IF NOT EXISTS idx_sys_users_user_id_active ON sys_users (user_id, is_active);
-- -------------------- 用户模块索引优化 --------------------
-- 用户名/邮箱查询索引（登录、用户检索高频场景）
CREATE INDEX IF NOT EXISTS idx_sys_users_username ON sys_users (username)
    WHERE
        is_active = TRUE;

CREATE INDEX IF NOT EXISTS idx_sys_users_email ON sys_users (email)
    WHERE
        is_active = TRUE;

-- 最后登录时间索引（用户活跃度分析）
CREATE INDEX IF NOT EXISTS idx_sys_users_last_login ON sys_users (last_login);

-- ===================== 表注释 =====================
-- 用户表
COMMENT
    ON TABLE sys_users IS '用户表';

COMMENT
    ON COLUMN sys_users.user_id IS '用户ID（UUID）';

COMMENT
    ON COLUMN sys_users.username IS '用户名（唯一）';

COMMENT
    ON COLUMN sys_users.email IS '邮箱（唯一）';

COMMENT
    ON COLUMN sys_users.password_hash IS '密码哈希值';

COMMENT
    ON COLUMN sys_users.roles IS '用户角色';

COMMENT
    ON COLUMN sys_users.created_at IS '创建时间';

COMMENT
    ON COLUMN sys_users.updated_at IS '更新时间';

COMMENT
    ON COLUMN sys_users.is_active IS '是否激活（true=是，false=否）';

COMMENT
    ON COLUMN sys_users.last_login IS '上次登录时间';

COMMENT
    ON COLUMN sys_users.deleted IS '逻辑删除（false=未删除，true=已删除）';

COMMENT
    ON COLUMN sys_users.deleted_at IS '逻辑删除时间';

-- 操作日志表
COMMENT
    ON TABLE sys_operate_logs IS '操作日志表';
COMMENT
    ON COLUMN sys_operate_logs.log_id IS '日志ID（UUID）';
COMMENT
    ON COLUMN sys_operate_logs.user_id IS '用户ID';
COMMENT
    ON COLUMN sys_operate_logs.operation IS '操作内容';
COMMENT
    ON COLUMN sys_operate_logs.ip IS '操作IP';
COMMENT
    ON COLUMN sys_operate_logs.created_at IS '创建时间';
COMMENT
    ON COLUMN sys_operate_logs.updated_at IS '更新时间';
COMMENT
    ON COLUMN sys_operate_logs.deleted IS '逻辑删除（false=未删除，true=已删除）';
COMMENT
    ON COLUMN sys_operate_logs.deleted_at IS '逻辑删除时间';