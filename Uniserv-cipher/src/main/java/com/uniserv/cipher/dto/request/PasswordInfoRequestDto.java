package com.uniserv.cipher.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 初始化密码管理信息请求参数
 * </p>
 *
 * @author kody
 * @since 2026-01-07
 */
@Data
@Accessors(chain = true)
@Schema(description = "初始化密码管理信息请求参数")
public class PasswordInfoRequestDto {

    /**
     * 主密码
     */
    @NotBlank(message = "主密码不能为空")
    @Schema(description = "主密码", example = "12345678")
    private String masterPassword;

}