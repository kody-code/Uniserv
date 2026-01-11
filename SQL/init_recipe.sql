create type difficulty_type as enum ('EASY', 'MEDIUM', 'HARD');
alter type difficulty_type owner to uniserv;
comment on type difficulty_type is '菜谱难度';

create table recipes
(
    recipe_id     serial primary key,
    recipe_name   varchar(255) not null,
    cooking_steps text         not null,
    cooking_time  int          not null,
    difficulty    difficulty_type,
    created_at    timestamp default CURRENT_TIMESTAMP
);
comment on table recipes is '菜谱表';
comment on column recipes.recipe_id is '菜谱ID';
comment on column recipes.recipe_name is '菜谱名称';
comment on column recipes.cooking_steps is '烹饪步骤';
comment on column recipes.cooking_time is '烹饪时间';
comment on column recipes.difficulty is '难度';
comment on column recipes.created_at is '创建时间';
CREATE INDEX IF NOT EXISTS idx_recipes_name ON recipes (recipe_name);
alter table password_info
    owner to uniserv;

create table ingredients
(
    ingredient_id   serial primary key,
    ingredient_name varchar(255) not null unique,
    unit            varchar(255)
);
comment on table ingredients is '食材表';
comment on column ingredients.ingredient_id is '食材ID';
comment on column ingredients.ingredient_name is '食材名称';
comment on column ingredients.unit is '食材单位';
CREATE INDEX IF NOT EXISTS idx_ingredients_name ON ingredients (ingredient_name);
alter table password_info
    owner to uniserv;

create table recipe_ingredient_relation
(
    relation_id   SERIAL PRIMARY KEY,
    recipe_id     INT          NOT NULL,
    ingredient_id INT          NOT NULL,
    amount        varchar(255) NOT NULL,
    -- 外键约束：关联菜谱表，保证数据完整性（删除菜谱时可选择CASCADE/RESTRICT）
    CONSTRAINT fk_relation_recipe FOREIGN KEY (recipe_id) REFERENCES recipes (recipe_id) ON DELETE RESTRICT,
    -- 外键约束：关联食材表，保证数据完整性
    CONSTRAINT fk_relation_ingredient FOREIGN KEY (ingredient_id) REFERENCES ingredients (ingredient_id) ON DELETE RESTRICT,
    -- 可选：唯一约束，避免同一菜谱对同一食材重复关联
    CONSTRAINT uk_recipe_ingredient UNIQUE (recipe_id, ingredient_id)
);
comment on table recipe_ingredient_relation is '菜谱食材关系表';
comment on column recipe_ingredient_relation.relation_id is '关系ID';
comment on column recipe_ingredient_relation.recipe_id is '菜谱ID';
comment on column recipe_ingredient_relation.ingredient_id is '食材ID';
comment on column recipe_ingredient_relation.amount is '食材数量';
CREATE INDEX IF NOT EXISTS idx_relation_recipe_ingredient ON recipe_ingredient_relation (recipe_id, ingredient_id);
alter table password_info
    owner to uniserv;