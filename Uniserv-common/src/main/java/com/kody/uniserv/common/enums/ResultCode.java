package com.kody.uniserv.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 响应结果枚举类
 * </p>
 *
 * @author kody
 * @since 2026-02-07
 */
@Getter
@AllArgsConstructor
public enum ResultCode {

    // ====================== 全局通用 ======================
    SUCCESS(0, "操作成功"),
    PARAM_ERROR(400, "参数错误"),
    UNAUTHORIZED(401, "请先登录"),
    FORBIDDEN(403, "无权限访问"),
    SERVER_ERROR(500, "服务器异常"),
    DATA_NOT_EXIST(100, "数据不存在"),
    DATA_EXIST(101, "数据已存在"),

    // ====================== AUTH 1000 ======================
    USER_NOT_FOUND(1001, "用户不存在"),
    ACCOUNT_PASSWORD_ERROR(1002, "账户或密码错误"),
    USER_DISABLE(1003, "用户被禁用"),
    USER_NOT_AUTHORIZED(1004, "用户未授权"),
    USER_ALREADY_EXIST(1005, "用户已存在"),

    // ====================== ADMIN 2000 ======================
    ROLE_NOT_EXIST(2001, "角色不存在"),

    // ====================== PASSWORD 3000 ======================
    PWD_ITEM_NOT_EXIST(3001, "密码条目不存在"),

    // ====================== BOOKKEEPING 4000 ======================
    BOOK_NOT_EXIST(4001, "账单不存在"),

    // ====================== FLASH NOTE 5000 ======================
    FLASH_NOTE_NOT_EXIST(5001, "闪记不存在");

    private final int code;
    private final String message;
}
