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
 * 密码管理信息表（存储主密码相关）
 * </p>
 *
 * @author kody
 * @since 2026-01-31
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@TableName("cipher_password_info")
public class CipherPasswordInfo extends Model<CipherPasswordInfo> {

    /**
     * 信息ID（UUID）
     */
    @TableId(value = "info_id", type = IdType.AUTO)
    private UUID infoId;

    /**
     * 关联用户ID
     */
    @TableField("user_id")
    private UUID userId;

    /**
     * 主密码验证哈希
     */
    @TableField("master_verify_hash")
    private String masterVerifyHash;

    /**
     * 盐值（用于主密码加密）
     */
    @TableField("verify_salt")
    private String verifySalt;

    /**
     * 密码版本号
     */
    @TableField("version")
    private Integer version;

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
        return this.infoId;
    }
}
