package com.uniserv.cipher.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

/**
 * <p>
 * TOTP 令牌请求参数
 * </p>
 *
 * @author kody
 * @since 2026-01-19
 */
@Data
@Accessors(chain = true)
@Schema(description = "TOTP 令牌请求参数")
public class TotpEntryRequestDto {

    /**
     * 密码条目 ID
     */
    @Schema(description = "密码条目 ID")
    @NotNull(message = "密码条目 ID 不能为空")
    private UUID passwordId;

    /**
     * TOTP 令牌
     */
    @Schema(description = "TOTP 令牌")
    @NotBlank(message = "TOTP 令牌 不能为空")
    private String totpToken;

}
