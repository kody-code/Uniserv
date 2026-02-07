package com.kody.uniserv.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

/**
 * <p>
 * 登录响应数据
 * </p>
 *
 * @author kody
 * @since 2026-02-06
 */
@Data
@Accessors(chain = true)
@Schema(description = "登录响应数据")
public class LoginResponseDto {

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
     * 令牌信息
     */
    @Schema(description = "令牌信息", example = "{token_name: xx; token_value: xxx}")
    private TokenInfo tokenInfo;
}
