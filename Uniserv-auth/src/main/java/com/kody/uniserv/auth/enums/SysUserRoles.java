package com.kody.uniserv.auth.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 系统用户角色枚举类
 * </p>
 *
 * @author kody
 * @since 2026-02-07
 */
@Getter
@AllArgsConstructor
public enum SysUserRoles {

    ADMIN("ADMIN", "管理员"),
    USER("USER", "普通用户");

    private final String role;
    private final String description;

}