package com.kody.uniserv.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 登录请求参数
 * </p>
 *
 * @author kody
 * @since 2026-02-06
 */
@Data
@Accessors(chain = true)
@Schema(description = "登录请求参数")
public class LoginRequestDto {

    /**
     * 用户名
     */
    @NotBlank(message = "用户不能为空")
    @Schema(description = "账号", example = "admin/admin@example.com")
    private String account;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Schema(description = "密码", example = "123456")
    private String password;
}
