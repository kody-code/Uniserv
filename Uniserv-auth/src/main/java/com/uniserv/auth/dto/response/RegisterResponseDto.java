package com.uniserv.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 注册响应数据
 * </p>
 *
 * @author kody
 * @since 2026-01-18
 */
@Data
@Accessors(chain = true)
@Schema(description = "注册响应数据")
public class RegisterResponseDto {

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String username;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    private String email;
}
