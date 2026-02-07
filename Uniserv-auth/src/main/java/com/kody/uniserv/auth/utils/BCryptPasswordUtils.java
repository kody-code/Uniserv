package com.kody.uniserv.auth.utils;


import cn.hutool.crypto.digest.BCrypt;

/**
 * <p>
 * BCrypt密码工具类
 * </p>
 *
 * @author kody
 * @since 2026-02-07
 */
public class BCryptPasswordUtils {

    /**
     * <p>
     * 加密密码
     * </p>
     *
     * @param rawPassword 原始密码
     * @return 加密后的密码
     */
    public static String encrypt(String rawPassword) {
        if (rawPassword.isBlank()) {
            throw new IllegalArgumentException("原始密码不能为空");
        }
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }


    /**
     * <p>
     * 验证密码
     * </p>
     *
     * @param rawPassword       原始密码
     * @param encryptedPassword 加密后的密码
     * @return 验证结果
     */
    public static boolean verify(String rawPassword, String encryptedPassword) {
        if (rawPassword.isBlank() || encryptedPassword.isBlank()) {
            return false;
        }
        return BCrypt.checkpw(rawPassword, encryptedPassword);
    }
}
