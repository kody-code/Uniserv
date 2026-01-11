package com.uniserv.cipher.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 密码解密响应数据
 * </p>
 *
 * @author kody
 * @since 2026-01-11
 */
@Data
@Accessors(chain = true)
@Schema(description = "密码解密响应数据")
public class DecryptPasswordResponseDto {

    @Schema(description = "解密后的密码")
    private String decryptedPassword;
}
