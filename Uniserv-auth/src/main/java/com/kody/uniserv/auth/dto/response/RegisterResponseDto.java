package com.kody.uniserv.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 注册结果
 * </p>
 *
 * @author kody
 * @since 2026-02-06
 */
@Data
@Accessors(chain = true)
@Schema(description = "注册结果")
public class RegisterResponseDto {

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
}