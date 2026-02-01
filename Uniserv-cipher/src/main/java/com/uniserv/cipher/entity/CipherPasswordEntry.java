package com.uniserv.cipher.entity;

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
import java.util.UUID;

/**
 * <p>
 * 密码条目表（存储各平台账号密码）
 * </p>
 *
 * @author kody
 * @since 2026-01-31
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@TableName("cipher_password_entry")
public class CipherPasswordEntry extends Model<CipherPasswordEntry> {

    /**
     * 密码条目ID（UUID）
     */
    @TableId(value = "password_id", type = IdType.AUTO)
    private UUID passwordId;

    /**
     * 关联用户ID
     */
    @TableField("user_id")
    private UUID userId;

    /**
     * 网站/APP名称
     */
    @TableField("website")
    private String website;

    /**
     * 平台账号
     */
    @TableField("account")
    private String account;

    /**
     * 关联邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 平台密码（加密存储）
     */
    @TableField("password")
    private String password;

    /**
     * 网站地址
     */
    @TableField("url")
    private String url;

    /**
     * TOTP令牌
     */
    @TableField("totp_token")
    private String totpToken;

    /**
     * 恢复码
     */
    @TableField("recovery_code")
    private String recoveryCode;

    /**
     * 备注信息
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

    /**
     * 逻辑删除（false=未删除，true=已删除）
     */
    @TableField("deleted")
    private Boolean deleted;

    /**
     * 逻辑删除时间
     */
    @TableField("deleted_at")
    private LocalDateTime deletedAt;

    @Override
    public Serializable pkVal() {
        return this.passwordId;
    }
}
