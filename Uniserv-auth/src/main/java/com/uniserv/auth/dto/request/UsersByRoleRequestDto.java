package com.uniserv.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户角色请求参数
 * </p>
 *
 * @author kody
 * @since 2026-01-18
 */
@Data
@Accessors(chain = true)
@Schema(description = "用户角色请求参数")
public class UsersByRoleRequestDto {

    /**
     * 角色
     */
    @Schema(description = "角色")
    @NotBlank(message = "角色不能为空")
    private String role;
}
