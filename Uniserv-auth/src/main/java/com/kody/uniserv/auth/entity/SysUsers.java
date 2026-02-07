package com.kody.uniserv.auth.entity;

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
 * 用户表
 * </p>
 *
 * @author kody
 * @since 2026-02-07
 */
@Getter
@Setter
@ToString
@TableName("sys_users")
@Accessors(chain = true)
public class SysUsers extends Model<SysUsers> {

    /**
     * 用户ID（UUID）
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private UUID userId;

    /**
     * 用户名（唯一）
     */
    @TableField("username")
    private String username;

    /**
     * 邮箱（唯一）
     */
    @TableField("email")
    private String email;

    /**
     * 密码哈希值
     */
    @TableField("password_hash")
    private String passwordHash;

    /**
     * 用户角色
     */
    @TableField("roles")
    private String roles;

    /**
     * 是否激活（true=是，false=否）
     */
    @TableField("is_active")
    private Boolean isActive;

    /**
     * 上次登录时间
     */
    @TableField("last_login")
    private LocalDateTime lastLogin;

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
        return this.userId;
    }
}
