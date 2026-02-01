-- 启用UUID扩展
CREATE
EXTENSION IF NOT EXISTS "uuid-ossp";

-- ===================== 枚举 =====================
-- 用户角色枚举
CREATE TYPE sys_user_roles AS enum (
    'ADMIN',
    'USER'
    );

ALTER TYPE sys_user_roles OWNER TO uniserv;

COMMENT
ON type sys_user_roles IS '用户角色（ADMIN=管理员，USER=普通用户）';

-- 账本类型枚举：区分账本用途（个人/家庭/办公/共享）
CREATE TYPE ledger_book_type AS ENUM (
    'PERSONAL',
    'FAMILY',
    'OFFICE',
    'SHARE',
    'OTHER'
    );

ALTER TYPE ledger_book_type OWNER TO uniserv;

COMMENT
ON type ledger_book_type IS '账本类型（个人/家庭/办公/共享/其他）';

-- 账本交易类型枚举：限定交易类型（收入/支出/转账）
CREATE TYPE ledger_record_type AS ENUM (
    'INCOME',
    'EXPENSE',
    'TRANSFER'
    );

ALTER TYPE ledger_record_type OWNER TO uniserv;

COMMENT
ON type ledger_record_type IS '交易类型（收入/支出/转账）';

-- 账本账户属性枚举：区分普通账户（余额非负）/信用卡账户（可负）
CREATE TYPE ledger_account_attribute AS ENUM (
    'NORMAL',
    'CREDIT'
    );

ALTER TYPE ledger_account_attribute OWNER TO uniserv;

COMMENT
ON type ledger_account_attribute IS '账户属性（普通/信用卡）';

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

-- ===================== 表结构 =====================
-- 用户表
CREATE TABLE sys_users
(
    user_id       uuid           DEFAULT uuid_generate_v4() NOT NULL PRIMARY KEY,
    username      varchar(20)                               NOT NULL UNIQUE,
    email         varchar(255)                              NOT NULL UNIQUE,
    password_hash varchar(255)                              NOT NULL,
    roles         sys_user_roles DEFAULT 'USER'::sys_user_roles NOT NULL,
    is_active     boolean        DEFAULT TRUE,
    last_login    timestamp,
    created_at    timestamp      DEFAULT CURRENT_TIMESTAMP,
    updated_at    timestamp      DEFAULT CURRENT_TIMESTAMP,
    deleted       boolean        DEFAULT FALSE,
    deleted_at    timestamp
);

-- 密码信息表
CREATE TABLE cipher_password_info
(
    info_id            uuid      DEFAULT uuid_generate_v4() NOT NULL
        CONSTRAINT cipher_password_info_pk PRIMARY KEY,
    user_id            uuid                                 NOT NULL
        CONSTRAINT cipher_password_info_sys_users_id_fk REFERENCES sys_users (user_id) ON DELETE CASCADE, -- 优化：级联删除
    master_verify_hash varchar(255)                         NOT NULL,
    verify_salt        varchar(255)                         NOT NULL,
    version            integer   DEFAULT 1                  NOT NULL,
    created_at         timestamp DEFAULT CURRENT_TIMESTAMP,
    updated_at         timestamp DEFAULT CURRENT_TIMESTAMP,
    deleted            boolean   DEFAULT FALSE              NOT NULL,
    deleted_at         timestamp
);

-- 密码条目表
CREATE TABLE cipher_password_entry
(
    password_id   uuid      DEFAULT uuid_generate_v4() NOT NULL
        CONSTRAINT cipher_password_entry_pk PRIMARY KEY,
    user_id       uuid                                 NOT NULL
        CONSTRAINT cipher_password_entry_sys_users_id_fk REFERENCES sys_users (user_id) ON DELETE CASCADE,
    website       varchar(50)                          NOT NULL,
    account       varchar(50)                          NOT NULL,
    email         varchar(255),
    PASSWORD      varchar(255)                         NOT NULL,
    url           varchar(100),
    totp_token    varchar(255),
    recovery_code text,
    notes         text,
    created_at    timestamp DEFAULT CURRENT_TIMESTAMP,
    updated_at    timestamp DEFAULT CURRENT_TIMESTAMP,
    deleted       boolean   DEFAULT FALSE              NOT NULL,
    deleted_at    timestamp
);

-- 账本账户表
CREATE TABLE ledger_books
(
    book_id     uuid PRIMARY KEY          DEFAULT uuid_generate_v4(),   -- 账本ID
    user_id     uuid             NOT NULL,                              -- 所属用户ID
    book_name   varchar(50)      NOT NULL,                              -- 账本名称
    book_type   ledger_book_type NOT NULL DEFAULT 'PERSONAL',           -- 账本类型
    currency    varchar(3)       NOT NULL DEFAULT 'CNY',                -- 币种：遵循ISO 4217（CNY/USD/EUR）
    description varchar(200)              DEFAULT '' NOT NULL,          -- 账本描述
    icon        varchar(30)               DEFAULT 'icon-book' NOT NULL, -- 展示图标：前端使用
    status      boolean          NOT NULL DEFAULT TRUE,                 -- 账本状态：true=启用，false=禁用
    created_at  timestamptz      NOT NULL DEFAULT CURRENT_TIMESTAMP,    -- 创建时间
    updated_at  timestamptz      NOT NULL DEFAULT CURRENT_TIMESTAMP,    -- 更新时间
    deleted     boolean          NOT NULL DEFAULT FALSE,                -- 软删除标记
    deleted_at  timestamptz                                             -- 软删除时间
);

-- 账本账户表
CREATE TABLE ledger_account
(
    account_id        uuid PRIMARY KEY                  DEFAULT uuid_generate_v4(),     -- 账户ID
    book_id           uuid                     NOT NULL,                                -- 所属账本ID
    account_name      varchar(50)              NOT NULL,                                -- 账户名称：如"招商银行储蓄卡/招行信用卡"
    account_type      varchar(20)              NOT NULL,                                -- 账户类型：CASH/BANK/CREDIT_CARD
    account_attribute ledger_account_attribute NOT NULL DEFAULT 'NORMAL',               -- 账户属性：NORMAL/CREDIT
    account_balance   numeric(15, 2)           NOT NULL DEFAULT 0.00,                   -- 账户余额：保留2位小数
    currency          varchar(3)               NOT NULL DEFAULT 'CNY',                  -- 账户币种：与账本一致
    icon              varchar(30)                       DEFAULT 'icon-wallet' NOT NULL, -- 展示图标：前端使用
    sort_order        integer                  NOT NULL DEFAULT 0,                      -- 排序值：前端展示顺序
    created_at        timestamptz              NOT NULL DEFAULT CURRENT_TIMESTAMP,      -- 创建时间
    updated_at        timestamptz              NOT NULL DEFAULT CURRENT_TIMESTAMP,      -- 更新时间
    deleted           boolean                  NOT NULL DEFAULT FALSE,                  -- 软删除标记
    deleted_at        timestamptz,                                                      -- 软删除时间
    -- 条件约束：普通账户余额≥0，信用账户无限制（可负，如信用卡欠款）
    CHECK ((account_attribute = 'NORMAL' AND account_balance >= 0) OR (account_attribute = 'CREDIT'))
);

-- 收支类型表
CREATE TABLE ledger_expense_income_types
(
    types_id      uuid        DEFAULT uuid_generate_v4() NOT NULL
        CONSTRAINT ledger_expense_income_types_pk PRIMARY KEY,
    type_name     varchar(50)                            NOT NULL,
    type_category ledger_record_type                     NOT NULL,
    icon          varchar(50) DEFAULT 'default-icon'     NOT NULL,
    created_at    timestamp   DEFAULT CURRENT_TIMESTAMP,
    updated_at    timestamp   DEFAULT CURRENT_TIMESTAMP,
    deleted       boolean     DEFAULT FALSE              NOT NULL,
    deleted_at    timestamp
);

-- 收支记录表
CREATE TABLE ledger_account_records
(
    record_id   uuid                        DEFAULT uuid_generate_v4() NOT NULL, -- 交易记录ID
    account_id  uuid               NOT NULL,                                     -- 所属账户ID
    book_id     uuid               NOT NULL,                                     -- 所属账本ID（冗余存储，提升查询效率）
    amount      numeric(15, 2)     NOT NULL,                                     -- 交易金额：收入=正，支出=负，转账=双向等额
    record_type ledger_record_type NOT NULL,                                     -- 交易类型：INCOME/EXPENSE/TRANSFER
    type_id     uuid               NOT NULL,                                     -- 关联收支类型表ID
    type_name   varchar(50)                 DEFAULT '' NOT NULL,                 -- 交易分类：如"餐饮美食/工资收入"
    description varchar(200)                DEFAULT '' NOT NULL,                 -- 交易备注
    occurred_at timestamptz        NOT NULL,                                     -- 交易发生时间
    created_at  timestamptz        NOT NULL DEFAULT CURRENT_TIMESTAMP,           -- 记录创建时间
    updated_at  timestamptz        NOT NULL DEFAULT CURRENT_TIMESTAMP,           -- 记录更新时间
    deleted     boolean            NOT NULL DEFAULT FALSE,                       -- 软删除标记
    deleted_at  timestamptz,                                                     -- 软删除时间
    PRIMARY KEY (record_id, occurred_at),                                        -- 主键包含分区键
    -- 约束：交易金额不可为0（避免无效交易）
    CHECK (amount <> 0),
    -- 新增约束：保证type_id关联收支类型表
    CONSTRAINT fk_records_type FOREIGN KEY (type_id) REFERENCES ledger_expense_income_types (types_id)
) PARTITION BY RANGE (occurred_at);

-- ===================== 约束 =====================
-- 用户表
-- 邮箱格式校验约束：正则拦截无效邮箱，避免脏数据
ALTER TABLE sys_users
    ADD CONSTRAINT chk_valid_email CHECK (email ~* '^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+[.][A-Za-z]{2,}$');

-- 密码条目表
-- 外键约束1：关联用户表，用户删除时级联删除密码条目
ALTER TABLE cipher_password_entry
    ADD CONSTRAINT fk_cipher_entry_user FOREIGN KEY (user_id) REFERENCES sys_users (user_id) ON DELETE CASCADE;

-- URL格式校验约束：仅允许空值或合法http/https网址
ALTER TABLE cipher_password_entry
    ADD CONSTRAINT chk_valid_url CHECK (url IS NULL OR url ~* '^https?://[A-Za-z0-9.-]+[.][A-Za-z]{2,}(/.*)?$');

-- 账本表
-- 外键约束1：关联用户表，用户删除时级联删除账本
ALTER TABLE ledger_account
    ADD CONSTRAINT fk_ledger_account_book FOREIGN KEY (book_id) REFERENCES ledger_books (book_id) ON DELETE CASCADE;

-- 外键约束2：关联用户表，用户删除时级联删除账本
ALTER TABLE ledger_books
    ADD CONSTRAINT fk_ledger_books_user FOREIGN KEY (user_id) REFERENCES sys_users (user_id) ON DELETE CASCADE;

-- 收支记录表
-- 外键约束1：关联密码条目表，密码条目删除时级联删除收支记录
ALTER TABLE ledger_account_records
    ADD CONSTRAINT fk_records_account FOREIGN KEY (account_id) REFERENCES ledger_account (account_id) ON DELETE CASCADE;

-- 外键约束2：关联账本表，账本删除时级联删除收支记录
ALTER TABLE ledger_account_records
    ADD CONSTRAINT fk_records_book FOREIGN KEY (book_id) REFERENCES ledger_books (book_id) ON DELETE CASCADE;

-- ===================== 函数 =====================
-- 创建自动创建分区的函数（按月分区）
CREATE
OR REPLACE FUNCTION auto_create_ledger_partition()
    RETURNS TRIGGER
AS
$$
DECLARE
partition_name  text;
    -- 分区表名，如ledger_account_records_202401
    partition_start
timestamptz;
    -- 分区起始时间（当月1日）
    partition_end
timestamptz;
    -- 分区结束时间（下月1日）
BEGIN
    -- 拼接分区表名：主表名_年份月份（如ledger_account_records_202401）
    partition_name
:= 'ledger_account_records_' || TO_CHAR(NEW.occurred_at, 'YYYYMM');
    -- 计算分区的时间范围（带时区，匹配occurred_at类型）
    partition_start
:= DATE_TRUNC('month', NEW.occurred_at)::timestamptz;
    partition_end
:= DATE_TRUNC('month', NEW.occurred_at) + INTERVAL '1 month';
    -- 检查分区是否存在，不存在则创建
    IF
NOT EXISTS (SELECT 1
                   FROM pg_tables
                   WHERE tablename = partition_name
                     AND schemaname = 'public') THEN
        -- 创建子分区
        EXECUTE format('CREATE TABLE %I PARTITION OF ledger_account_records FOR VALUES FROM (%L) TO (%L)',
                       partition_name, partition_start, partition_end);
        -- 可选：为子分区创建索引（优化查询性能）
EXECUTE format('CREATE INDEX idx_%I_occurred_at ON %I (occurred_at)', partition_name, partition_name);
EXECUTE format('CREATE INDEX idx_%I_account_id ON %I (account_id)', partition_name, partition_name);
RAISE
NOTICE 'Created new partition: %', partition_name;
        -- 打印日志，方便调试
END IF;
RETURN NEW;
END;
$$
LANGUAGE plpgsql;

-- 更新账户余额函数
CREATE
OR REPLACE FUNCTION update_account_balance()
    RETURNS TRIGGER
AS
$$
DECLARE
old_amount numeric(15, 2);
    new_amount
numeric(15, 2);
BEGIN
    -- ===================== INSERT =====================
    IF
(TG_OP = 'INSERT') THEN
        IF (NEW.deleted = FALSE) THEN
            -- 1. 校验账户有效性 + 加行锁（核心修正：SELECT FOR UPDATE）
            IF NOT EXISTS (SELECT 1
                           FROM ledger_account
                           WHERE account_id = NEW.account_id
                             AND deleted = FALSE
                               FOR UPDATE -- 锁定该行，避免并发修改
            ) THEN
                RAISE EXCEPTION 'Account % is invalid or deleted', NEW.account_id;
END IF;
            new_amount
:= NEW.amount;
            -- 2. 执行余额更新（移除错误的 FOR UPDATE）
UPDATE
    ledger_account
SET account_balance = account_balance + new_amount,
    updated_at      = CURRENT_TIMESTAMP
WHERE account_id = NEW.account_id
  AND deleted = FALSE;
END IF;
RETURN NEW;
END IF;
    -- ===================== UPDATE =====================
    IF
(TG_OP = 'UPDATE') THEN
        IF (OLD.amount <> NEW.amount OR OLD.account_id <> NEW.account_id OR OLD.deleted <> NEW.deleted) THEN
            -- 1. 扣旧记录（旧有效）
            IF (OLD.deleted = FALSE) THEN
                -- 锁定旧账户行
                PERFORM
                    1
                FROM ledger_account
                WHERE account_id = OLD.account_id
                  AND deleted = FALSE
                    FOR
UPDATE;
old_amount
:= OLD.amount;
UPDATE
    ledger_account
SET account_balance = account_balance - old_amount,
    updated_at      = CURRENT_TIMESTAMP
WHERE account_id = OLD.account_id
  AND deleted = FALSE;
END IF;
            -- 2. 加新记录（新有效）
            IF
(NEW.deleted = FALSE) THEN
                -- 校验新账户有效性 + 加锁
                IF NOT EXISTS (SELECT 1
                               FROM ledger_account
                               WHERE account_id = NEW.account_id
                                 AND deleted = FALSE
                                   FOR UPDATE) THEN
                    RAISE EXCEPTION 'Account % is invalid or deleted', NEW.account_id;
END IF;
                new_amount
:= NEW.amount;
UPDATE
    ledger_account
SET account_balance = account_balance + new_amount,
    updated_at      = CURRENT_TIMESTAMP
WHERE account_id = NEW.account_id
  AND deleted = FALSE;
END IF;
END IF;
RETURN NEW;
END IF;
    -- ===================== DELETE（物理删除） =====================
    IF
(TG_OP = 'DELETE') THEN
        IF (OLD.deleted = FALSE) THEN
            -- 锁定账户行
            PERFORM
                1
            FROM ledger_account
            WHERE account_id = OLD.account_id
              AND deleted = FALSE
                FOR
UPDATE;
old_amount
:= OLD.amount;
UPDATE
    ledger_account
SET account_balance = account_balance - old_amount,
    updated_at      = CURRENT_TIMESTAMP
WHERE account_id = OLD.account_id
  AND deleted = FALSE;
END IF;
RETURN OLD;
END IF;
RETURN NULL;
END;
$$
LANGUAGE plpgsql;

-- ===================== 表触发器 =====================
-- updated_at 触发器
-- 用户表
CREATE TRIGGER trigger_sys_users_updated_at
    BEFORE UPDATE
    ON sys_users
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at();

-- 密码信息表
CREATE TRIGGER trigger_cipher_password_info_updated_at
    BEFORE UPDATE
    ON cipher_password_info
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at();

-- 密码条目表
CREATE TRIGGER trigger_cipher_password_entry_updated_at
    BEFORE UPDATE
    ON cipher_password_entry
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at();

-- 账本表
CREATE TRIGGER trigger_ledger_books_updated_at
    BEFORE UPDATE
    ON ledger_books
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at();

-- 账户表
CREATE TRIGGER trigger_ledger_account_updated_at
    BEFORE UPDATE
    ON ledger_account
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at();

-- 收支类型表
CREATE TRIGGER trigger_ledger_expense_income_types_updated_at
    BEFORE UPDATE
    ON ledger_expense_income_types
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at();

-- 收支记录表
CREATE TRIGGER trigger_ledger_account_records_updated_at
    BEFORE UPDATE
    ON ledger_account_records
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at();

-- set_deleted_at 触发器
-- 用户表
CREATE TRIGGER trigger_sys_users_deleted_at
    BEFORE UPDATE
    ON sys_users
    FOR EACH ROW
    EXECUTE FUNCTION set_deleted_at();

-- 密码信息表
CREATE TRIGGER trigger_cipher_password_info_deleted_at
    BEFORE UPDATE
    ON cipher_password_info
    FOR EACH ROW
    EXECUTE FUNCTION set_deleted_at();

-- 密码条目表
CREATE TRIGGER trigger_cipher_password_entry_deleted_at
    BEFORE UPDATE
    ON cipher_password_entry
    FOR EACH ROW
    EXECUTE FUNCTION set_deleted_at();

-- 账本表
CREATE TRIGGER trigger_ledger_books_deleted_at
    BEFORE UPDATE
    ON ledger_books
    FOR EACH ROW
    EXECUTE FUNCTION set_deleted_at();

-- 账户表
CREATE TRIGGER trigger_ledger_account_deleted_at
    BEFORE UPDATE
    ON ledger_account
    FOR EACH ROW
    EXECUTE FUNCTION set_deleted_at();

-- 收支类型表
CREATE TRIGGER trigger_ledger_expense_income_types_deleted_at
    BEFORE UPDATE
    ON ledger_expense_income_types
    FOR EACH ROW
    EXECUTE FUNCTION set_deleted_at();

-- 收支记录表
CREATE TRIGGER trigger_ledger_account_records_deleted_at
    BEFORE UPDATE
    ON ledger_account_records
    FOR EACH ROW
    EXECUTE FUNCTION set_deleted_at();

-- ===================== 函数触发器 =====================
-- 创建触发器：插入数据前触发分区检查/创建
CREATE TRIGGER trg_auto_create_ledger_partition
    BEFORE INSERT
    ON ledger_account_records
    FOR EACH ROW
    EXECUTE FUNCTION auto_create_ledger_partition();

-- 创建触发器：插入/更新数据后触发余额更新
CREATE TRIGGER trigger_ledger_account_records_after_insert_update_balance
    AFTER INSERT OR
UPDATE
    ON ledger_account_records
    FOR EACH ROW
    EXECUTE FUNCTION update_account_balance();

-- ===================== 索引优化 =====================
-- 密码条目表索引（优化：包含逻辑删除，提升查询效率）
DROP INDEX IF EXISTS password_entry_user_id_index;

CREATE INDEX idx_password_entry_user_deleted ON cipher_password_entry (user_id, deleted);

CREATE INDEX idx_password_entry_user_created ON cipher_password_entry (user_id, created_at) WHERE
        deleted = FALSE;

-- UUID字段索引优化（提升查询效率）
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

-- 密码管理信息表索引（用户维度查询）
CREATE INDEX IF NOT EXISTS idx_cipher_password_info_user ON cipher_password_info (user_id)
    WHERE
    deleted = FALSE;

-- 密码版本号索引（密码更新/验证场景）
CREATE INDEX IF NOT EXISTS idx_cipher_password_info_version ON cipher_password_info (user_id, version)
    WHERE
    deleted = FALSE;

-- 密码条目表补充索引（高频查询维度）
-- 按网站+账户检索（用户快速查找特定平台密码）
CREATE INDEX IF NOT EXISTS idx_cipher_password_entry_website_account ON cipher_password_entry (website, account)
    WHERE
    deleted = FALSE;

-- 按创建时间排序（用户查看最新添加的密码）
CREATE INDEX IF NOT EXISTS idx_cipher_password_entry_created_at ON cipher_password_entry (user_id, created_at DESC)
    WHERE
    deleted = FALSE;

-- -------------------- 账本模块索引优化 --------------------
-- 账本表补充索引
-- 用户+状态索引（查询用户启用/禁用的账本）
CREATE INDEX IF NOT EXISTS idx_ledger_books_user_status ON ledger_books (user_id, status)
    WHERE
    deleted = FALSE;

-- 账本类型索引（按类型筛选账本）
CREATE INDEX IF NOT EXISTS idx_ledger_books_book_type ON ledger_books (user_id, book_type)
    WHERE
    deleted = FALSE;

-- 软删除时间索引（数据清理/恢复场景）
CREATE INDEX IF NOT EXISTS idx_ledger_books_deleted_at ON ledger_books (deleted_at)
    WHERE
    deleted = TRUE;

-- 账本表索引
CREATE UNIQUE INDEX idx_ledger_books_user_book_name_active ON ledger_books (user_id, book_name) WHERE
        deleted = FALSE;

-- 账户表补充索引
-- 账本+状态索引（查询账本下的有效账户）
CREATE INDEX IF NOT EXISTS idx_ledger_account_book_deleted ON ledger_account (book_id)
    WHERE
    deleted = FALSE;

-- 账户类型+属性索引（按类型/属性筛选账户）
CREATE INDEX IF NOT EXISTS idx_ledger_account_type_attribute ON ledger_account (account_type, account_attribute)
    WHERE
    deleted = FALSE;

-- 余额索引（资产统计/异常排查）
CREATE INDEX IF NOT EXISTS idx_ledger_account_balance ON ledger_account (account_balance)
    WHERE
    deleted = FALSE;

-- 排序值索引（前端展示排序）
CREATE INDEX IF NOT EXISTS idx_ledger_account_sort_order ON ledger_account (book_id, sort_order)
    WHERE
    deleted = FALSE;

-- 收支类型表补充索引
-- 类型类别+名称索引（快速查找收入/支出分类）
CREATE INDEX IF NOT EXISTS idx_ledger_expense_income_types_category_name ON ledger_expense_income_types (type_category, type_name)
    WHERE
    deleted = FALSE;

-- 交易记录表补充索引（核心高频查询）
-- 账户+时间范围索引（查询账户某时间段的交易）
CREATE INDEX IF NOT EXISTS idx_ledger_records_account_occurred_at ON ledger_account_records (account_id, occurred_at)
    WHERE
    deleted = FALSE;

-- 账本+交易类型+时间索引（账本维度统计收入/支出）
CREATE INDEX IF NOT EXISTS idx_ledger_records_book_type_time ON ledger_account_records (book_id, record_type, occurred_at)
    WHERE
    deleted = FALSE;

-- 交易类型+分类ID索引（按类型+分类筛选交易）
CREATE INDEX IF NOT EXISTS idx_ledger_records_type_category ON ledger_account_records (record_type, type_id)
    WHERE
    deleted = FALSE;

-- 软删除标记索引（已删除交易的查询/恢复）
CREATE INDEX IF NOT EXISTS idx_ledger_records_deleted ON ledger_account_records (deleted, deleted_at);

-- 收支类型表软删除索引（分类管理场景）
CREATE INDEX IF NOT EXISTS idx_ledger_expense_income_types_deleted ON ledger_expense_income_types (deleted);

-- ===================== 初始化数据 =====================
INSERT INTO ledger_expense_income_types (type_name, type_category, icon, deleted)
VALUES ('餐饮', 'EXPENSE', 'icon-food', false),
       ('交通', 'EXPENSE', 'icon-transport', false),
       ('住房', 'EXPENSE', 'icon-housing', false),
       ('购物', 'EXPENSE', 'icon-shopping', false),
       ('娱乐', 'EXPENSE', 'icon-entertainment', false),
       ('通讯', 'EXPENSE', 'icon-communication', false),
       ('医疗', 'EXPENSE', 'icon-medical', false),
       ('教育', 'EXPENSE', 'icon-education', false),
       ('人情', 'EXPENSE', 'icon-personal', false),
       ('其他支出', 'EXPENSE', 'icon-other-expense', false),
       ('工资', 'INCOME', 'icon-salary', false),
       ('奖金', 'INCOME', 'icon-bonus', false),
       ('兼职', 'INCOME', 'icon-part-time', false),
       ('投资收益', 'INCOME', 'icon-investment', false),
       ('转账收款', 'INCOME', 'icon-transfer-in', false),
       ('其他收入', 'INCOME', 'icon-other-income', false);

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

-- 密码信息表
COMMENT
ON TABLE cipher_password_info IS '密码管理信息表（存储主密码相关）';

COMMENT
ON COLUMN cipher_password_info.info_id IS '信息ID（UUID）';

COMMENT
ON COLUMN cipher_password_info.user_id IS '关联用户ID';

COMMENT
ON COLUMN cipher_password_info.master_verify_hash IS '主密码验证哈希';

COMMENT
ON COLUMN cipher_password_info.verify_salt IS '盐值（用于主密码加密）';

COMMENT
ON COLUMN cipher_password_info.version IS '密码版本号';

COMMENT
ON COLUMN cipher_password_info.created_at IS '创建时间';

COMMENT
ON COLUMN cipher_password_info.updated_at IS '更新时间';

COMMENT
ON COLUMN cipher_password_info.deleted IS '逻辑删除（false=未删除，true=已删除）';

COMMENT
ON COLUMN cipher_password_info.deleted_at IS '逻辑删除时间';

-- 密码条目表
COMMENT
ON TABLE cipher_password_entry IS '密码条目表（存储各平台账号密码）';

COMMENT
ON COLUMN cipher_password_entry.password_id IS '密码条目ID（UUID）';

COMMENT
ON COLUMN cipher_password_entry.user_id IS '关联用户ID';

COMMENT
ON COLUMN cipher_password_entry.website IS '网站/APP名称';

COMMENT
ON COLUMN cipher_password_entry.account IS '平台账号';

COMMENT
ON COLUMN cipher_password_entry.email IS '关联邮箱';

COMMENT
ON COLUMN cipher_password_entry.password IS '平台密码（加密存储）';

COMMENT
ON COLUMN cipher_password_entry.url IS '网站地址';

COMMENT
ON COLUMN cipher_password_entry.totp_token IS 'TOTP令牌';

COMMENT
ON COLUMN cipher_password_entry.recovery_code IS '恢复码';

COMMENT
ON COLUMN cipher_password_entry.notes IS '备注信息';

COMMENT
ON COLUMN cipher_password_entry.created_at IS '创建时间';

COMMENT
ON COLUMN cipher_password_entry.updated_at IS '更新时间';

COMMENT
ON COLUMN cipher_password_entry.deleted IS '逻辑删除（false=未删除，true=已删除）';

COMMENT
ON COLUMN cipher_password_entry.deleted_at IS '逻辑删除时间';

-- 账本表
COMMENT
ON TABLE ledger_books IS '账本表，支持个人/家庭/办公/共享等多种类型，多币种配置';

COMMENT
ON COLUMN ledger_books.book_id IS '账本唯一标识，UUID格式，主键';

COMMENT
ON COLUMN ledger_books.user_id IS '所属用户ID，关联sys_users表user_id字段，不可为NULL';

COMMENT
ON COLUMN ledger_books.book_name IS '账本名称，用户自定义，同一用户下未删除的账本名称不可重复';

COMMENT
ON COLUMN ledger_books.book_type IS '账本类型，枚举类型：PERSONAL(个人)、FAMILY(家庭)、OFFICE(办公)、SHARE(共享)、OTHER(其他)，默认PERSONAL';

COMMENT
ON COLUMN ledger_books.currency IS '账本币种，遵循ISO 4217标准：CNY(人民币)、USD(美元)、EUR(欧元)等，默认CNY';

COMMENT
ON COLUMN ledger_books.description IS '账本描述信息，用户自定义，说明账本用途，默认空字符串';

COMMENT
ON COLUMN ledger_books.icon IS '账本展示图标，前端图标类名：如icon-book(书本)、icon-wallet(钱包)，默认icon-book';

COMMENT
ON COLUMN ledger_books.status IS '账本状态，true=启用（可新增交易），false=禁用（仅可查看），默认true';

COMMENT
ON COLUMN ledger_books.created_at IS '账本创建时间，自动填充当前时间，带时区';

COMMENT
ON COLUMN ledger_books.updated_at IS '账本最后更新时间，带时区，通过触发器自动刷新';

COMMENT
ON COLUMN ledger_books.deleted IS '软删除标记，true=已删除，false=正常使用，默认false';

COMMENT
ON COLUMN ledger_books.deleted_at IS '软删除时间，仅当deleted=true时有值，带时区，通过触发器自动设置';

-- 账户表
COMMENT
ON TABLE ledger_account IS '账本下的账户表，记录各类账户信息与余额，支持普通账户与信用账户';

COMMENT
ON COLUMN ledger_account.account_id IS '账户唯一标识，UUID格式，主键';

COMMENT
ON COLUMN ledger_account.book_id IS '所属账本ID，关联ledger_books表book_id字段，不可为NULL';

COMMENT
ON COLUMN ledger_account.account_name IS '账户名称，用户自定义：如"招商银行储蓄卡"、"招行信用卡"，不可为NULL';

COMMENT
ON COLUMN ledger_account.account_type IS '账户类型，字符串标识：CASH(现金)、BANK(银行卡)、CREDIT_CARD(信用卡)、INVESTMENT(投资)等';

COMMENT
ON COLUMN ledger_account.account_attribute IS '账户属性，枚举类型：NORMAL(普通账户，余额不可为负)、CREDIT(信用账户，余额可负)，默认NORMAL';

COMMENT
ON COLUMN ledger_account.account_balance IS '账户余额，数值类型保留2位小数，普通账户≥0，信用账户可负，默认0.00';

COMMENT
ON COLUMN ledger_account.currency IS '账户币种，遵循ISO 4217标准，需与所属账本币种一致，默认CNY';

COMMENT
ON COLUMN ledger_account.icon IS '账户展示图标，前端图标类名：如icon-wallet(钱包)、icon-card(银行卡)，默认icon-wallet';

COMMENT
ON COLUMN ledger_account.sort_order IS '排序值，整数类型，数值越小前端展示越靠前，默认0';

COMMENT
ON COLUMN ledger_account.created_at IS '账户创建时间，自动填充当前时间，带时区';

COMMENT
ON COLUMN ledger_account.updated_at IS '账户最后更新时间，带时区，通过触发器自动刷新（含余额更新）';

COMMENT
ON COLUMN ledger_account.deleted IS '软删除标记，true=已删除，false=正常使用，默认false';

COMMENT
ON COLUMN ledger_account.deleted_at IS '软删除时间，仅当deleted=true时有值，带时区，通过触发器自动设置';

-- 收支类型表
COMMENT
ON TABLE ledger_expense_income_types IS '收入支出类型表（基础分类）';

COMMENT
ON COLUMN ledger_expense_income_types.types_id IS '类型ID（UUID）';

COMMENT
ON COLUMN ledger_expense_income_types.type_name IS '类型名称（如餐饮、工资）';

COMMENT
ON COLUMN ledger_expense_income_types.type_category IS '类型类别（EXPENSE=支出，INCOME=收入）';

COMMENT
ON COLUMN ledger_expense_income_types.icon IS '图标名称';

COMMENT
ON COLUMN ledger_expense_income_types.created_at IS '创建时间';

COMMENT
ON COLUMN ledger_expense_income_types.updated_at IS '更新时间';

COMMENT
ON COLUMN ledger_expense_income_types.deleted IS '逻辑删除（false=未删除，true=已删除）';

COMMENT
ON COLUMN ledger_expense_income_types.deleted_at IS '逻辑删除时间';

-- 账户交易记录表
COMMENT
ON TABLE ledger_account_records IS '账户交易记录表，记录收入、支出、转账等交易行为，按时间分区存储';

COMMENT
ON COLUMN ledger_account_records.record_id IS '交易记录唯一标识，UUID格式，主键';

COMMENT
ON COLUMN ledger_account_records.account_id IS '所属账户ID，关联ledger_account表account_id字段，不可为NULL';

COMMENT
ON COLUMN ledger_account_records.book_id IS '所属账本ID，关联ledger_books表book_id字段，冗余存储提升查询效率，不可为NULL';

COMMENT
ON COLUMN ledger_account_records.amount IS '交易金额，数值类型保留2位小数：收入=正数，支出=负数，转账需关联两个账户生成等额正负记录，不可为0';

COMMENT
ON COLUMN ledger_account_records.record_type IS '交易类型，枚举类型：INCOME(收入)、EXPENSE(支出)、TRANSFER(转账)，不可为NULL';

COMMENT
ON COLUMN ledger_account_records.type_id IS '关联收支类型表的ID，不可为NULL';

COMMENT
ON COLUMN ledger_account_records.type_name IS '交易分类名称，用户自定义：如"餐饮美食"、"工资收入"、"房租支出"，默认空字符串';

COMMENT
ON COLUMN ledger_account_records.description IS '交易备注信息，用户自定义补充说明，默认空字符串';

COMMENT
ON COLUMN ledger_account_records.occurred_at IS '交易发生时间，可自定义（如补录历史交易），带时区，用于分区表拆分，不可为NULL';

COMMENT
ON COLUMN ledger_account_records.created_at IS '交易记录创建时间，自动填充当前时间，带时区，不可手动修改';

COMMENT
ON COLUMN ledger_account_records.updated_at IS '交易记录最后更新时间，带时区，通过触发器自动刷新';

COMMENT
ON COLUMN ledger_account_records.deleted IS '软删除标记，true=已删除，false=正常使用，默认false';

COMMENT
ON COLUMN ledger_account_records.deleted_at IS '软删除时间，仅当deleted=true时有值，带时区，通过触发器自动设置';

