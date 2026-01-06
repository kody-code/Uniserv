package com.uniserv.cipher.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * Totp 表
 * </p>
 *
 * @author kody
 * @since 2026-01-06
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@TableName("totp_entry")
public class TotpEntry {

    /**
     * totp Id
     */
    @TableId("id")
    private Object id;

    /**
     * TOTP令牌
     */
    @TableField("totp_token")
    private String totpToken;

    @TableField("recovery_code")
    private String recoveryCode;

    /**
     * 逻辑删除
     */
    @TableField("deleted")
    private Boolean deleted;

    /**
     * 密码条目 ID
     */
    @TableField("password_id")
    private Object passwordId;

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
