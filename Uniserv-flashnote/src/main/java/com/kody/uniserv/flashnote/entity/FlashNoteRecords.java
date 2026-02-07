package com.kody.uniserv.flashnote.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 闪记表
 * </p>
 *
 * @author kody
 * @since 2026-02-07
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@TableName("flash_note_records")
public class FlashNoteRecords extends Model<FlashNoteRecords> {

    /**
     * 闪记ID
     */
    @TableId(value = "note_id", type = IdType.AUTO)
    private UUID noteId;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private UUID userId;

    /**
     * 闪记内容
     */
    @TableField("content")
    private String content;

    /**
     * 标签
     */
    @TableField("tags")
    private List<String> tags;

    /**
     * 创建时间
     */
    @TableField("created_at")
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @TableField("updated_at")
    private LocalDateTime updatedAt;

    /**
     * 是否删除
     */
    @TableField("deleted")
    private Boolean deleted;

    /**
     * 删除时间
     */
    @TableField("deleted_at")
    private LocalDateTime deletedAt;

    @Override
    public Serializable pkVal() {
        return this.noteId;
    }
}
