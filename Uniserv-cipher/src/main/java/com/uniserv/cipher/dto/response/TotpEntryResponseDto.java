package com.uniserv.cipher.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

/**
 * <p>
 * TOTP 令牌响应数据
 * </p>
 *
 * @author kody
 * @since 2026-01-19
 */
@Data
@Accessors(chain = true)
@Schema(description = "TOTP 令牌响应数据")
public class TotpEntryResponseDto {

    /**
     * 密码条目 ID
     */
    @Schema(description = "密码条目 ID")
    private UUID passwordId;

    /**
     * TOTP 验证码
     */
    @Schema(description = "TOTP 验证码")
    private int totp;
}
