package com.uniserv.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

/**
 * <p>
 * 删除用户参数
 * </p>
 *
 * @author kody
 * @since 2026-01-18
 */
@Data
@Accessors(chain = true)
public class DelUserRequestDto {

    @NotBlank(message = "用户 ID 不能为空")
    @Schema(description = "用户 ID", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID userId;
}
