package com.uniserv.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 响应结果枚举类
 * </p>
 *
 * @author kody
 * @since 2026-01-26
 */
@Getter
@AllArgsConstructor
public enum ResultCode {
    // 成功
    SUCCESS(0, "操作成功"),
    NOT_LOGIN(401, "未登录"),

    // 系统错误
    SYSTEM_ERROR(500, "系统异常"),

    // 业务错误
    BUSINESS_ERROR(1000, "业务异常"),
    OPERATION_ERROR(2000, "操作失败"),
    NOT_FOUND_ERROR(3000, "未找到"),

    // 认证错误
    AUTH_ERROR(2000, "认证失败"),

    // 参数错误
    PARAM_ERROR(3000, "参数错误");

    private final int code;
    private final String message;
}
