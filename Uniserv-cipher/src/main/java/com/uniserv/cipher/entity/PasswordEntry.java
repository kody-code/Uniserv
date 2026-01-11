package com.uniserv.cipher.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * <p>
 * 密码条目表
 * </p>
 *
 * @author kody
 * @since 2026-01-06
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@TableName("password_entry")
public class PasswordEntry {

    /**
     * 密码条目 ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private UUID id;

    /**
     * 网站 APP
     */
    @TableField("website")
    private String website;

    /**
     * 账号
     */
    @TableField("account")
    private String account;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 网站地址
     */
    @TableField("url")
    private String url;

    /**
     * 逻辑删除
     */
    @TableField("deleted")
    @TableLogic(value = "false", delval = "true")
    private Boolean deleted;

    /**
     * 用户 id
     */
    @TableField("user_id")
    private UUID userId;

    /**
     * 备注
     */
    @TableField("notes")
    private String notes;

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
}
