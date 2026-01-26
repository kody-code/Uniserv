package com.uniserv.common.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>
 * BCrypt 密码工具测试类
 * </p>
 *
 * @author kody
 * @since 2026-01-26
 */
@DisplayName("BCrypt 密码工具测试类")
public class BCryptPasswordUtilsTest {

    @Test
    @DisplayName("加密密码功能测试")
    public void testEncryptPassword() {
        // 准备测试数据
        String rawPassword = "testPassword123";

        // 执行加密操作
        String encryptedPassword = BCryptPasswordUtils.encrypt(rawPassword);

        // 验证加密结果
        assertNotNull(encryptedPassword, "加密后的密码不应为空");
        assertNotEquals(rawPassword, encryptedPassword, "加密后的密码应与原密码不同");
        assertTrue(encryptedPassword.startsWith("$2a$"), "加密后的密码应符合 BCrypt 格式");
    }

    @Test
    @DisplayName("密码验证功能测试 - 正确密码")
    public void testVerifyCorrectPassword() {
        // 准备测试数据：先加密一个密码
        String rawPassword = "testPassword123";
        String encryptedPassword = BCryptPasswordUtils.encrypt(rawPassword);

        // 验证正确的密码是否能够成功验证
        assertTrue(BCryptPasswordUtils.verify(rawPassword, encryptedPassword),
                "正确的密码应该验证通过");
    }

    @Test
    @DisplayName("密码验证功能测试 - 错误密码")
    public void testVerifyIncorrectPassword() {
        // 准备测试数据
        String rawPassword = "testPassword123";
        String wrongPassword = "wrongPassword";
        String encryptedPassword = BCryptPasswordUtils.encrypt(rawPassword);

        // 验证错误的密码是否能够被拒绝
        assertFalse(BCryptPasswordUtils.verify(wrongPassword, encryptedPassword),
                "错误的密码应该验证失败");
    }

    @Test
    @DisplayName("空密码处理测试")
    public void testEmptyPasswordHandling() {
        // 测试加密空密码时抛出异常的情况
        assertThrows(IllegalArgumentException.class, () -> BCryptPasswordUtils.encrypt(""), "空密码应该抛出 IllegalArgumentException");

        assertThrows(IllegalArgumentException.class, () -> BCryptPasswordUtils.encrypt("   "), "空白字符串应该抛出 IllegalArgumentException");

        // 测试验证空密码返回 false 的情况
        assertFalse(BCryptPasswordUtils.verify("", "anyEncryptedPassword"));
        assertFalse(BCryptPasswordUtils.verify("validPassword", ""));
        assertFalse(BCryptPasswordUtils.verify("   ", "anyEncryptedPassword"));
        assertFalse(BCryptPasswordUtils.verify("validPassword", "   "));
    }

    @Test
    @DisplayName("相同原密码生成不同哈希值测试")
    public void testDifferentHashForSamePassword() {
        // 准备测试数据
        String rawPassword = "testPassword123";

        // 对同一个密码进行两次加密
        String hash1 = BCryptPasswordUtils.encrypt(rawPassword);
        String hash2 = BCryptPasswordUtils.encrypt(rawPassword);

        // 验证BCrypt的盐值机制：即使原密码相同，生成的哈希也不同
        assertNotEquals(hash1, hash2, "每次加密相同的密码都应该产生不同的哈希值（由于盐值不同）");

        // 但两个哈希值都应能被原密码正确验证
        assertTrue(BCryptPasswordUtils.verify(rawPassword, hash1));
        assertTrue(BCryptPasswordUtils.verify(rawPassword, hash2));
    }

    @Test
    @DisplayName("特殊字符密码测试")
    public void testSpecialCharacterPasswords() {
        // 定义包含各种特殊字符的密码数组用于测试
        String[] passwords = {
                "password!@#",          // 包含常见特殊字符
                "test123密码",          // 包含中文字符
                "p@ssw0rd$%^",         // 包含符号和数字
                "aA1!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~", // 包含所有特殊字符
                "password\nwith\tspaces" // 包含换行符和制表符
        };

        // 遍历所有特殊字符密码进行测试
        for (String password : passwords) {
            String encrypted = BCryptPasswordUtils.encrypt(password);
            assertTrue(BCryptPasswordUtils.verify(password, encrypted),
                    "包含特殊字符的密码应该能够正确验证");

            // 确保其他密码不能通过验证（防止误报）
            assertFalse(BCryptPasswordUtils.verify("differentPassword", encrypted),
                    "不同的密码不应该验证通过");
        }
    }
}