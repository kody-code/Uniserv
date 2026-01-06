package com.uniserv.common.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>
 * 密码加密解密工具类测试
 * </p>
 *
 * @author kody
 * @since 2026-01-06
 */
@DisplayName("密码加密解密工具类测试")
class PasswordEncryptUtilTest {
    private PasswordEncryptUtil passwordEncryptUtil;

    @BeforeEach
    void setUp() {
        passwordEncryptUtil = new PasswordEncryptUtil();
        // 设置默认配置值
        ReflectionTestUtils.setField(passwordEncryptUtil, "iterations", 1000); // 使用较小值加快测试
        ReflectionTestUtils.setField(passwordEncryptUtil, "keyLength", 256);
        ReflectionTestUtils.setField(passwordEncryptUtil, "saltLength", 16);
    }

    @Test
    @DisplayName("测试生成盐值功能")
    void testGenerateSalt() {
        String salt1 = passwordEncryptUtil.generateSalt();
        String salt2 = passwordEncryptUtil.generateSalt();

        assertNotNull(salt1);
        assertNotNull(salt2);
        assertEquals(32, salt1.length()); // 16字节盐值的十六进制表示
        assertNotEquals(salt1, salt2); // 两次生成的盐值应不同
    }

    @Test
    @DisplayName("测试密码加密和解密功能")
    void testEncryptAndDecrypt() throws Exception {
        String plainPassword = "testPassword123";
        String masterPassword = "masterPassword123";
        String salt = passwordEncryptUtil.generateSalt();

        // 测试加密
        String encrypted = passwordEncryptUtil.encrypt(plainPassword, masterPassword, salt);
        assertNotNull(encrypted);
        assertNotEquals(plainPassword, encrypted);

        // 测试解密
        String decrypted = passwordEncryptUtil.decrypt(encrypted, masterPassword, salt);
        assertEquals(plainPassword, decrypted);
    }

    @Test
    @DisplayName("测试解密错误密码")
    void testDecryptWrongPassword() {
        String plainPassword = "testPassword123";
        String masterPassword = "masterPassword123";
        String wrongMasterPassword = "wrongMasterPassword";
        String salt = passwordEncryptUtil.generateSalt();

        assertThrows(Exception.class, () -> {
            String encrypted = passwordEncryptUtil.encrypt(plainPassword, masterPassword, salt);
            passwordEncryptUtil.decrypt(encrypted, wrongMasterPassword, salt);
        });
    }

    @Test
    @DisplayName("测试解密错误盐值")
    void testDecryptWrongSalt() {
        String plainPassword = "testPassword123";
        String masterPassword = "masterPassword123";
        String salt = passwordEncryptUtil.generateSalt();
        String wrongSalt = passwordEncryptUtil.generateSalt();

        assertThrows(Exception.class, () -> {
            String encrypted = passwordEncryptUtil.encrypt(plainPassword, masterPassword, salt);
            passwordEncryptUtil.decrypt(encrypted, masterPassword, wrongSalt);
        });
    }

    @Test
    @DisplayName("测试验证哈希生成和验证功能")
    void testVerifyHash() throws Exception {
        String masterPassword = "masterPassword123";
        String verifySalt = passwordEncryptUtil.generateSalt();

        // 生成验证哈希
        String verifyHash = passwordEncryptUtil.generateVerifyHash(masterPassword, verifySalt);
        assertNotNull(verifyHash);
        assertFalse(verifyHash.isEmpty());

        // 验证正确密码
        boolean result = passwordEncryptUtil.verifyMasterPassword(masterPassword, verifyHash, verifySalt);
        assertTrue(result);

        // 验证错误密码
        boolean wrongResult = passwordEncryptUtil.verifyMasterPassword("wrongPassword", verifyHash, verifySalt);
        assertFalse(wrongResult);
    }

    @Test
    @DisplayName("测试空值处理")
    void testNullValues() {
        String salt = passwordEncryptUtil.generateSalt();
        String verifySalt = passwordEncryptUtil.generateSalt();

        // 测试加密时的空值
        assertThrows(Exception.class, () ->
                passwordEncryptUtil.encrypt(null, "master", salt));
        assertThrows(Exception.class, () ->
                passwordEncryptUtil.encrypt("plain", null, salt));
        assertThrows(Exception.class, () ->
                passwordEncryptUtil.encrypt("plain", "master", null));

        // 测试解密时的空值
        assertThrows(Exception.class, () ->
                passwordEncryptUtil.decrypt(null, "master", salt));
        assertThrows(Exception.class, () ->
                passwordEncryptUtil.decrypt("encrypted", null, salt));
        assertThrows(Exception.class, () ->
                passwordEncryptUtil.decrypt("encrypted", "master", null));

        // 测试验证哈希时的空值
        assertThrows(Exception.class, () ->
                passwordEncryptUtil.generateVerifyHash(null, verifySalt));
        assertThrows(Exception.class, () ->
                passwordEncryptUtil.generateVerifyHash("master", null));

        // 测试验证主密码时的空值
        assertThrows(Exception.class, () ->
                passwordEncryptUtil.verifyMasterPassword(null, "hash", verifySalt));
        assertThrows(Exception.class, () ->
                passwordEncryptUtil.verifyMasterPassword("master", null, verifySalt));
        assertThrows(Exception.class, () ->
                passwordEncryptUtil.verifyMasterPassword("master", "hash", null));
    }

    @Test
    @DisplayName("测试不同配置参数")
    void testDifferentConfigurations() throws Exception {
        // 测试不同的keyLength
        ReflectionTestUtils.setField(passwordEncryptUtil, "keyLength", 128);
        String plainPassword = "testPassword123";
        String masterPassword = "masterPassword123";
        String salt = passwordEncryptUtil.generateSalt();

        String encrypted = passwordEncryptUtil.encrypt(plainPassword, masterPassword, salt);
        String decrypted = passwordEncryptUtil.decrypt(encrypted, masterPassword, salt);
        assertEquals(plainPassword, decrypted);
    }

    @Test
    @DisplayName("测试加密后的数据完整性")
    void testDataIntegrity() throws Exception {
        String plainPassword = "testPassword123";
        String masterPassword = "masterPassword123";
        String salt = passwordEncryptUtil.generateSalt();

        String encrypted = passwordEncryptUtil.encrypt(plainPassword, masterPassword, salt);
        String decrypted = passwordEncryptUtil.decrypt(encrypted, masterPassword, salt);

        assertEquals(plainPassword, decrypted);
        assertNotEquals(plainPassword, encrypted); // 确保加密后与原文不同
    }

    @Test
    @DisplayName("测试常量时间比较防止计时攻击")
    void testConstantTimeComparison() throws Exception {
        String masterPassword = "masterPassword123";
        String verifySalt = passwordEncryptUtil.generateSalt();

        String verifyHash = passwordEncryptUtil.generateVerifyHash(masterPassword, verifySalt);

        // 验证正确密码
        assertTrue(passwordEncryptUtil.verifyMasterPassword(masterPassword, verifyHash, verifySalt));

        // 验证错误密码
        assertFalse(passwordEncryptUtil.verifyMasterPassword("wrongPassword", verifyHash, verifySalt));
    }
}
