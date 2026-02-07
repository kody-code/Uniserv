package com.kody.uniserv.common.constant;

/**
 * <p>
 * 正则常量类
 * </p>
 *
 * @author kody
 * @since 2026-02-07
 */
public class RegexConstants {
    /**
     * 手机号正则
     */
    public static final String PHONE_REGEX = "^1[3-9]\\d{8}$";

    /**
     * 邮箱正则
     */
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
}
