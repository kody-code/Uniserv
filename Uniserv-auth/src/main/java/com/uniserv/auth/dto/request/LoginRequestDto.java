package com.uniserv.auth.dto.request;

import com.uniserv.auth.constant.ValidateConstant;

import io.micrometer.core.instrument.config.validate.Validated.Valid;
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
 * @since 2026-01-26
 */
@Data
@Accessors(chain = true)
@Schema(description = "登录请求参数")
public class LoginRequestDto {

    /**
     * 用户名
     */
    @NotBlank(message = ValidateConstant.USER_NAME_NOT_NULL)
    @Schema(description = "账号", example = "admin/admin@example.com")
    private String account;

    /**
     * 密码
     */
    @NotBlank(message = ValidateConstant.PASSWORD_NOT_NULL)
    @Schema(description = "密码", example = "123456")
    private String password;
}
