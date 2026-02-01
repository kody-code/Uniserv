package com.uniserv.cipher.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 密码条目请求参数
 * </p>
 *
 * @author kody
 * @since 2026-01-18
 */
@Data
@Accessors(chain = true)
@Schema(description = "密码条目请求参数")
public class PasswordEntryRequestDto {

    /**
     * 主密码
     */
    @NotBlank(message = "主密码不能为空")
    @Schema(description = "主密码", example = "12345678")
    private String masterPassword;

    /**
     * 网站名称
     */
    @Schema(description = "网站名称")
    @NotBlank(message = "网站名称不能为空")
    private String website;

    /**
     * 账号
     */
    @Schema(description = "账号")
    @NotBlank(message = "账号不能为空")
    private String account;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    private String email;

    /**
     * 密码
     */
    @Schema(description = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 网站地址
     */
    @Schema(description = "网站地址")
    private String url;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String notes;

}
