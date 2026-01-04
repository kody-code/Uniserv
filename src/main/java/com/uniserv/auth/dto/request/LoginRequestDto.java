package com.uniserv.auth.dto.request;

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
 * @since 2026-01-04
 */
@Data
@Accessors(chain = true)
@Schema(description = "登录请求参数")
public class LoginRequestDto {

    @NotBlank(message = "用户名不能为空")
    @Schema(description = "用户名", example = "admin")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Schema(description = "密码", example = "123456")
    private String password;
}
