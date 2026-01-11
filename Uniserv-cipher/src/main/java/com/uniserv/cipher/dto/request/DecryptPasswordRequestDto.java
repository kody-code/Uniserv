package com.uniserv.cipher.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 密码解密请求参数
 * </p>
 *
 * @author kody
 * @since 2026-01-11
 */
@Data
@Accessors(chain = true)
@Schema(description = "密码解密请求参数")
public class DecryptPasswordRequestDto {

    /**
     * 密文
     */
    @Schema(description = "密文")
    @NotBlank(message = "密文不能为空")
    private String encryptedPassword;

    /**
     * 主密码
     */
    @Schema(description = "主密码")
    @NotBlank(message = "主密码不能为空")
    private String masterPassword;
}
