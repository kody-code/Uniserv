package com.kody.uniserv.auth.dto.request;

import com.kody.uniserv.common.constant.RegexConstants;
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
 * @since 2026-02-06
 */
@Data
@Accessors(chain = true)
@Schema(description = "注册参数")
public class RegisterRequestDto {

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    @Schema(description = "用户名", example = "admin")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Schema(description = "密码", example = "123456")
    private String password;

    /**
     * 邮箱
     */
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确", regexp = RegexConstants.EMAIL_REGEX)
    @Schema(description = "邮箱", example = "admin@example.com")
    private String email;
}