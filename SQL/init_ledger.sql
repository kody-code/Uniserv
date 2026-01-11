create type ledger_type as enum ('EXPENSE', 'INCOME');
alter type ledger_type owner to uniserv;
comment on type ledger_type is '收入支出类型';

create table expense_income_types
(
    types_id      serial
        constraint expense_income_types_pk
            primary key,
    type_name     varchar(50)             not null,
    type_category ledger_type             not null,
    created_at    timestamp default CURRENT_TIMESTAMP,
    deleted       boolean   default false not null
);
comment on table expense_income_types is '收入支出类型表';
comment on column expense_income_types.types_id is '类型ID';
comment on column expense_income_types.type_name is '类型名称';
comment on column expense_income_types.type_category is '类型类别';
comment on column expense_income_types.created_at is '创建时间';
comment on column expense_income_types.deleted is '逻辑删除';
create index idx_expense_income_types_deleted on expense_income_types (deleted);
alter table expense_income_types
    owner to uniserv;

-- 插入默认支出分类（EXPENSE）
INSERT INTO expense_income_types (type_name, type_category, deleted)
VALUES ('餐饮', 'EXPENSE', false),
       ('交通', 'EXPENSE', false),
       ('住房', 'EXPENSE', false), -- 房租、房贷、物业费等
       ('购物', 'EXPENSE', false),
       ('娱乐', 'EXPENSE', false),
       ('通讯', 'EXPENSE', false), -- 手机话费、宽带费等
       ('医疗', 'EXPENSE', false),
       ('教育', 'EXPENSE', false),
       ('人情', 'EXPENSE', false),
       ('其他支出', 'EXPENSE', false);

-- 插入默认收入分类（INCOME）
INSERT INTO expense_income_types (type_name, type_category, deleted)
VALUES ('工资', 'INCOME', false),
       ('奖金', 'INCOME', false),
       ('兼职', 'INCOME', false),
       ('投资收益', 'INCOME', false),
       ('转账收款', 'INCOME', false),
       ('其他收入', 'INCOME', false);

create table account_books
(
    account_id      serial
        constraint account_books_pk
            primary key,
    account_name    varchar(100)                 not null,
    account_balance decimal(10, 2) default 0.00  not null,
    account_status  boolean        default true,
    created_at      timestamp      default CURRENT_TIMESTAMP,
    deleted         boolean        default false not null
);
comment on table account_books is '账户表';
comment on column account_books.account_id is '账户ID';
comment on column account_books.account_name is '账户名称';
comment on column account_books.account_balance is '账户余额';
comment on column account_books.account_status is '账户状态';
comment on column account_books.created_at is '创建时间';
comment on column account_books.deleted is '逻辑删除';
create index idx_account_books_deleted_status on account_books (deleted, account_status);
alter table account_books
    owner to uniserv;

create table account_records
(
    record_id   serial
        constraint account_records_pk
            primary key,
    type_id     integer        not null
        constraint account_records_fk_types
            references expense_income_types (types_id)
            on delete restrict,
    account_id  integer        not null
        constraint account_records_fk_accounts
            references account_books (account_id)
            on delete restrict,
    amount      decimal(10, 2) not null,
    remark      text,
    record_time timestamp default CURRENT_TIMESTAMP,
    create_time timestamp default CURRENT_TIMESTAMP,
    deleted     boolean   default false
);
comment on table account_records is '账户记录表';
comment on column account_records.record_id is '记录ID';
comment on column account_records.type_id is '类型ID';
comment on column account_records.account_id is '账户ID';
comment on column account_records.amount is '金额';
comment on column account_records.remark is '备注';
comment on column account_records.record_time is '交易发生时间';
comment on column account_records.create_time is '记账录入时间';
create index idx_account_records_type_id on account_records (type_id, deleted);
create index idx_account_records_account_id on account_records (account_id, deleted);
create index idx_account_records_record_time on account_records (record_time, deleted);
alter table account_records
    owner to uniserv;


-- 创建函数：根据记账记录的变动，更新对应账户余额
CREATE OR REPLACE FUNCTION update_account_balance()
    RETURNS TRIGGER AS
$$
DECLARE
    old_amount DECIMAL(10, 2); -- 旧记录金额（更新/删除时使用）
    new_amount DECIMAL(10, 2); -- 新记录金额（插入/更新时使用）
BEGIN
    -- 场景1：新增记账记录（INSERT）
    IF (TG_OP = 'INSERT') THEN
        new_amount := NEW.amount;
        -- 更新账户余额：累加新记录金额
        UPDATE account_books
        SET account_balance = account_balance + new_amount
        WHERE account_id = NEW.account_id
          AND deleted = false;
        RETURN NEW;
    END IF;

    -- 场景2：更新记账记录（UPDATE，仅处理金额、账户ID、删除状态变动）
    IF (TG_OP = 'UPDATE') THEN
        -- 旧记录有效（未删除），先扣除旧金额
        IF (OLD.deleted = false) THEN
            old_amount := OLD.amount;
            UPDATE account_books
            SET account_balance = account_balance - old_amount
            WHERE account_id = OLD.account_id
              AND deleted = false;
        END IF;
        -- 新记录有效（未删除），再累加新金额
        IF (NEW.deleted = false) THEN
            new_amount := NEW.amount;
            UPDATE account_books
            SET account_balance = account_balance + new_amount
            WHERE account_id = NEW.account_id
              AND deleted = false;
        END IF;
        RETURN NEW;
    END IF;

    -- 场景3：逻辑删除记账记录（UPDATE，仅deleted字段从false改为true）
    -- （已包含在上述UPDATE逻辑中，无需额外处理）

    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

-- 创建触发器：在account_records插入/更新后，执行余额更新函数
CREATE TRIGGER trigger_account_records_balance
    AFTER INSERT OR UPDATE
    ON account_records
    FOR EACH ROW -- 每一行记录变动都触发（行级触发器）
EXECUTE FUNCTION update_account_balance();

