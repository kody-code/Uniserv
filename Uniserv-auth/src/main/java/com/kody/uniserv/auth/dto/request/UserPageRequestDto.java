package com.kody.uniserv.auth.dto.request;

import com.kody.uniserv.common.dto.PageRequestDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户分页查询请求DTO
 * </p>
 *
 * @author kody
 * @since 2026-02-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Schema(description = "用户分页查询请求参数")
public class UserPageRequestDto extends PageRequestDto {

    /**
     * 用户名搜索关键词
     */
    @Schema(description = "用户名搜索关键词", example = "admin")
    private String username;

    /**
     * 邮箱搜索关键词
     */
    @Schema(description = "邮箱搜索关键词", example = "admin@example.com")
    private String email;

    /**
     * 用户角色筛选
     */
    @Schema(description = "用户角色筛选", example = "ADMIN")
    private String role;

    /**
     * 是否激活状态筛选
     */
    @Schema(description = "是否激活状态筛选", example = "true")
    private Boolean isActive;
}