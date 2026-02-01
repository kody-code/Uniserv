package com.uniserv.cipher.utils;

import com.warrenstrange.googleauth.GoogleAuthenticator;

/**
 * <p>
 * TOTP 生成工具类
 * </p>
 *
 * @author kody
 * @since 2026-02-01
 */
public class TotpGeneratorUtil {

    // 核心TOTP生成器（默认配置）
    private static final GoogleAuthenticator DEFAULT_GA = new GoogleAuthenticator();

    /**
     * 生成 TOTP 验证码
     *
     * @param secret 密钥
     * @return TOTP 验证码
     */
    public static int generateTotpCode(String secret) {
        return DEFAULT_GA.getTotpPassword(secret);
    }
}
