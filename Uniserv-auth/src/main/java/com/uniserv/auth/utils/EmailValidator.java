package com.uniserv.auth.utils;

import java.util.regex.Pattern;

import com.uniserv.auth.constant.ValidateConstant;

/**
 * <p>
 * 邮箱校验工具类
 * </p>
 * 
 * @author kody
 * @since 2026-01-29
 */
public class EmailValidator {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(ValidateConstant.EMAIL_REGEX,
            Pattern.CASE_INSENSITIVE);

    /**
     * 健壮版邮箱校验（含空值、空白字符判断）
     * 
     * @param email 待校验的邮箱地址
     * @return 合法返回true，否则false
     */
    public static boolean isValidEmailWithNullCheck(String email) {
        // 先判断空值、空白字符（如""、" "）
        if (email == null || email.isBlank()) {
            return false;
        }
        // 再做正则匹配
        return EMAIL_PATTERN.matcher(email).matches();
    }

}
