-- ===================== 表结构 =====================
-- 记录表
CREATE TABLE IF NOT EXISTS flash_note_records
(
    note_id    uuid      DEFAULT uuid_generate_v4() NOT NULL PRIMARY KEY,
    user_id    uuid                                 NOT NULL,
    content    text                                 NOT NULL,
    tags       varchar(255)[],
    created_at timestamp DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp DEFAULT CURRENT_TIMESTAMP,
    deleted    boolean   DEFAULT FALSE,
    deleted_at timestamp
);

-- ===================== 约束 =====================

-- ===================== 表触发器 =====================
-- updated_at 触发器
CREATE TRIGGER trigger_flash_note_records_updated_at
    BEFORE UPDATE
    ON flash_note_records
    FOR EACH ROW
EXECUTE FUNCTION update_updated_at();
-- set_deleted_at 触发器
CREATE TRIGGER trigger_flash_note_records_deleted_at
    BEFORE UPDATE
    ON flash_note_records
    FOR EACH ROW
EXECUTE FUNCTION set_deleted_at();

-- ===================== 索引优化 =====================
CREATE INDEX IF NOT EXISTS idx_flash_note_records_user_id ON flash_note_records (user_id);
CREATE INDEX IF NOT EXISTS idx_flash_note_records_deleted ON flash_note_records (deleted);
CREATE INDEX IF NOT EXISTS idx_flash_note_records_tags ON flash_note_records USING GIN (tags);
-- 全文索引
CREATE INDEX IF NOT EXISTS idx_flash_note_records_content ON flash_note_records USING GIN (to_tsvector('english', content));

-- ===================== 表注释 =====================
COMMENT ON TABLE flash_note_records IS '闪记表';
COMMENT ON COLUMN flash_note_records.note_id IS '闪记ID';
COMMENT ON COLUMN flash_note_records.user_id IS '用户ID';
COMMENT ON COLUMN flash_note_records.content IS '闪记内容';
COMMENT ON COLUMN flash_note_records.tags IS '标签';
COMMENT ON COLUMN flash_note_records.created_at IS '创建时间';
COMMENT ON COLUMN flash_note_records.updated_at IS '更新时间';
COMMENT ON COLUMN flash_note_records.deleted IS '是否删除';
COMMENT ON COLUMN flash_note_records.deleted_at IS '删除时间';
