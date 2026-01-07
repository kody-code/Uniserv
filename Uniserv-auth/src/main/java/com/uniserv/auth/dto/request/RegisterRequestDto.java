package com.uniserv.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * </p>
 *
 * @author kody
 * @since 2026-01-07
 */
@Data
@Accessors(chain = true)
@Schema(description = "注册参数")
public class RegisterRequestDto {

    @NotBlank(message = "用户名不能为空")
    @Schema(description = "用户名", example = "admin")
    private String username;

    @NotBlank(message = "邮箱不能为空")
    @Schema(description = "邮箱", example = "admin@example.com")
    private String email;

    @NotBlank(message = "密码不能为空")
    @Schema(description = "密码", example = "123456")
    private String password;

}