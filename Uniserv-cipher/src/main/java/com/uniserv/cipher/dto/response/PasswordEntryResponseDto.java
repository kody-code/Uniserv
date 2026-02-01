package com.uniserv.cipher.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

/**
 * <p>
 * 密码条响应数据
 * </p>
 *
 * @author kody
 * @since 2026-01-18
 */
@Data
@Accessors(chain = true)
@Schema(description = "密码条响应数据")
public class PasswordEntryResponseDto {

    /**
     * 密码条目 ID
     */
    @Schema(description = "密码条目 ID")
    private UUID id;

    /**
     * 网站
     */
    @Schema(description = "网站")
    private String website;

    /**
     * 账号
     */
    @Schema(description = "账号")
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
    private String password;

    /**
     * 网站地址
     */
    @Schema(description = "网站地址")
    private String url;

    @Schema(description = "备注")
    private String notes;
}
