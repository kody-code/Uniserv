package com.uniserv.auth.dot.response;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
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
 * @since 2026-01-07
 */
@Data
@Accessors(chain = true)
@Schema(description = "登录响应数据")
public class LoginResponseDto {

    /**
     * 用户 ID
     */
    @JsonProperty("user_id")
    @Schema(description = "用户 ID", example = "123e4567-e89b-12d3-a456-426614174000")
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
     * 登录信息
     */
    @JsonProperty("token_info")
    @Schema(description = "登录信息")
    private SaTokenInfo tokenInfo;
}