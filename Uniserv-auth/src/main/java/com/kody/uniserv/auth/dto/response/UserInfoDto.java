package com.kody.uniserv.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * <p>
 * 用户信息响应DTO
 * </p>
 *
 * @author kody
 * @since 2026-02-07
 */
@Data
@Accessors(chain = true)
@Schema(description = "用户信息")
public class UserInfoDto {

    /**
     * 用户ID
     */
    @Schema(description = "用户ID", example = "04b552f9-8379-4047-9754-9a67c11f39d7")
    private UUID userId;

    /**
     * 用户名
     */
    @Schema(description = "用户名", example = "admin")
    private String username;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱", example = "admin@example.com")
    private String email;

    /**
     * 用户角色
     */
    @Schema(description = "用户角色", example = "ADMIN")
    private String roles;

    /**
     * 是否激活
     */
    @Schema(description = "是否激活", example = "true")
    private Boolean isActive;

    /**
     * 上次登录时间
     */
    @Schema(description = "上次登录时间", example = "2026-02-07T10:30:00")
    private LocalDateTime lastLogin;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间", example = "2026-02-07T10:00:00")
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间", example = "2026-02-07T10:30:00")
    private LocalDateTime updatedAt;
}