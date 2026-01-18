package com.uniserv.cipher.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * <p>
 * TOTP令牌表（双因子认证）
 * </p>
 *
 * @author kody
 * @since 2026-01-18
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@TableName("cipher_totp_entry")
public class CipherTotpEntry {

    /**
     * TOTP ID（UUID）
     */
    @TableId(value = "totp_id", type = IdType.AUTO)
    private UUID totpId;

    /**
     * 关联密码条目 ID
     */
    @TableField("password_id")
    private UUID passwordId;

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
}
