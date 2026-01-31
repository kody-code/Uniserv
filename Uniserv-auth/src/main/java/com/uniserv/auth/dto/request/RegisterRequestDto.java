package com.uniserv.auth.dto.request;

import com.uniserv.auth.constant.ValidateConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 注册参数
 * </p>
 *
 * @author kody
 * @since 2026-01-26
 */
@Data
@Accessors(chain = true)
@Schema(description = "注册参数")
public class RegisterRequestDto {

    /**
     * 用户名
     */
    @NotBlank(message = ValidateConstant.USER_NAME_NOT_NULL)
    @Schema(description = "用户名", example = "admin")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = ValidateConstant.PASSWORD_NOT_NULL)
    @Schema(description = "密码", example = "123456")
    private String password;

    /**
     * 邮箱
     */
    @NotBlank(message = ValidateConstant.EMAIL_NOT_NULL)
    @Email(message = ValidateConstant.EMAIL_NOT_LEGAL, regexp = ValidateConstant.EMAIL_REGEX)
    @Schema(description = "邮箱", example = "admin@example.com")
    private String email;
}
