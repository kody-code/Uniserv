package com.uniserv.common.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>
 * BCrypt 密码工具类测试
 * </p>
 *
 * @author kody
 * @since 2026-01-06
 */
@DisplayName("BCrypt 密码工具类测试")
class BCryptPasswordUtilsTest {

    @Test
    @DisplayName("测试正常密码加密")
    void testEncryptNormalPassword() {
        String rawPassword = "test123456";
        String encrypted = BCryptPasswordUtils.encrypt(rawPassword);

        assertNotNull(encrypted);
        assertNotNull(rawPassword, encrypted);
        assertTrue(encrypted.startsWith("$2a$"));
    }

    @Test
    @DisplayName("测试空密码异常")
    void testEncryptEmptyPassword() {
        assertThrows(IllegalArgumentException.class,
                () -> BCryptPasswordUtils.encrypt(""));
        assertThrows(IllegalArgumentException.class,
                () -> BCryptPasswordUtils.encrypt("   "));
        assertThrows(NullPointerException.class,
                () -> BCryptPasswordUtils.encrypt(null));
    }

    @Test
    @DisplayName("测试密码验证功能")
    void testVerifyPassword() {
        String rawPassword = "test123";
        String encrypted = BCryptPasswordUtils.encrypt(rawPassword);

        assertTrue(BCryptPasswordUtils.verify(rawPassword, encrypted));
        assertFalse(BCryptPasswordUtils.verify("wrong", encrypted));
    }
}
