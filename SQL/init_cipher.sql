create table password_info
(
    id                 serial
        constraint password_info_pk
            primary key,
    user_id            uuid                    not null
        constraint password_info_users_id_fk
            references users,
    master_verify_hash varchar(255)            not null,
    verify_salt        varchar(255)            not null,
    version            integer   default 1     not null,
    created_at         timestamp default CURRENT_TIMESTAMP,
    updated_at         timestamp default CURRENT_TIMESTAMP,
    deleted            boolean   default false not null
);

comment on table password_info is '密码管理信息';

comment on column password_info.id is '信息 ID';

comment on column password_info.user_id is '用户ID';

comment on column password_info.master_verify_hash is '主密码';

comment on column password_info.verify_salt is '盐值';

comment on column password_info.version is '版本';

comment on column password_info.created_at is '创建时间';

comment on column password_info.updated_at is '更新时间';

comment on column password_info.deleted is '逻辑删除';

alter table password_info
    owner to uniserv;

create table password_entry
(
    id           uuid         default uuid_generate_v4()    not null
        constraint password_entry_pk
            primary key,
    website      varchar(255)                               not null,
    account      varchar(255)                               not null,
    email        varchar(255),
    password     varchar(255)                               not null,
    url          varchar(255),
    deleted      boolean      default false                 not null,
    user_id      uuid                                       not null
        constraint password_entry_users_id_fk
            references users,
    notes        text,
    created_at   timestamp    default CURRENT_TIMESTAMP,
    updated_at   timestamp    default CURRENT_TIMESTAMP,
    encrypt_salt varchar(255) default ''::character varying not null
);

comment on table password_entry is '密码条目表';

comment on column password_entry.id is '密码条目 ID';

comment on column password_entry.website is '网站 APP';

comment on column password_entry.account is '账号';

comment on column password_entry.email is '邮箱';

comment on column password_entry.password is '密码';

comment on column password_entry.url is '网站地址';

comment on column password_entry.deleted is '逻辑删除';

comment on column password_entry.user_id is '用户 id';

comment on column password_entry.notes is '备注';

comment on column password_entry.created_at is '创建时间';

comment on column password_entry.updated_at is '更新时间';

alter table password_entry
    owner to uniserv;

create index password_entry_user_id_index
    on password_entry (user_id);

create table totp_entry
(
    id            uuid      default gen_random_uuid() not null
        constraint totp_entry_pk
            primary key,
    totp_token    varchar(255)                        not null,
    recovery_code text,
    deleted       boolean   default false             not null,
    password_id   uuid
        constraint totp_entry_password_entry_id_fk
            references password_entry,
    created_at    timestamp default CURRENT_TIMESTAMP,
    updated_at    timestamp default CURRENT_TIMESTAMP
);

comment on table totp_entry is 'Totp 表';

comment on column totp_entry.id is 'totp Id';

comment on column totp_entry.totp_token is 'TOTP令牌';

comment on column totp_entry.deleted is '逻辑删除';

comment on column totp_entry.password_id is '密码条目 ID';

comment on column totp_entry.created_at is '创建时间';

comment on column totp_entry.updated_at is '更新时间';

alter table totp_entry
    owner to uniserv;

