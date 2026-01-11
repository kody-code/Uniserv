create type roles as enum ('ADMIN', 'USER');
alter type roles owner to uniserv;

create table users
(
    id            uuid      default uuid_generate_v4() not null
        primary key,
    username      varchar(100)                         not null
        unique,
    email         varchar(255)                         not null
        unique,
    password_hash varchar(255)                         not null,
    roles         roles     default 'USER'::roles      not null,
    created_at    timestamp default CURRENT_TIMESTAMP,
    updated_at    timestamp default CURRENT_TIMESTAMP,
    is_active     boolean   default true,
    last_login    timestamp
);

comment on table users is '用户表';

comment on column users.id is '用户ID';

comment on column users.username is '用户名';

comment on column users.email is '邮箱';

comment on column users.password_hash is '密码';

comment on column users.roles is '角色';

comment on column users.created_at is '创建时间';

comment on column users.updated_at is '更新时间';

comment on column users.is_active is '是否激活';

comment on column users.last_login is '上次登录时间';

alter table users
    owner to uniserv;

