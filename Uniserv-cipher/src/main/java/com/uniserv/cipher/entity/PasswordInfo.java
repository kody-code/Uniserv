package com.uniserv.cipher.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 密码管理信息
 * </p>
 *
 * @author kody
 * @since 2026-01-06
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@TableName("password_info")
public class PasswordInfo {

    /**
     * 信息 ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Object userId;

    /**
     * 主密码
     */
    @TableField("master_verify_hash")
    private String masterVerifyHash;

    /**
     * 盐值
     */
    @TableField("verify_salt")
    private String verifySalt;

    /**
     * 版本
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
     * 逻辑删除
     */
    @TableField("deleted")
    @TableLogic(value = "false", delval = "true")
    private Boolean deleted;
}
