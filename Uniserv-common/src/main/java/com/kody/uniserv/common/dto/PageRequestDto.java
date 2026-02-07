package com.kody.uniserv.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 分页请求DTO基类
 * </p>
 *
 * @author kody
 * @since 2026-02-07
 */
@Data
@Accessors(chain = true)
@Schema(description = "分页请求参数")
public class PageRequestDto {

    /**
     * 当前页码
     */
    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码必须大于0")
    @Schema(description = "当前页码", example = "1")
    private Integer pageNum = 1;

    /**
     * 每页条数
     */
    @NotNull(message = "每页条数不能为空")
    @Min(value = 1, message = "每页条数必须大于0")
    @Schema(description = "每页条数", example = "10")
    private Integer pageSize = 10;
}