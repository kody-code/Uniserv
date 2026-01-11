package com.uniserv.common.utils;

import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

/**
 * <p>
 * 密码加密解密工具类
 * </p>
 *
 * @author kody
 * @since 2026-01-06
 */
@Component
public class PasswordEncryptUtil {
    // IV向量固定长度（AES/CBC要求16字节）
    private static final int IV_LENGTH = 16;
    @Value("${encrypt.pbkdf2.iterations:65536}")
    private int iterations;
    @Value("${encrypt.pbkdf2.key-length:256}")
    private int keyLength;
    // 密码专属盐值长度（原盐值长度作为基础配置）
    @Value("${encrypt.pbkdf2.salt-length:16}")
    private int saltLength;

    /**
     * 生成盐值（通用）
     *
     * @return 盐值（16进制字符串）
     */
    public String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[saltLength];
        random.nextBytes(salt);
        return Hex.encodeHexString(salt);
    }

    /**
     * 获取 AES 密钥（基于主密码+密码专属盐值）
     *
     * @param masterPassword 主密码
     * @param salt           密码专属盐值（16进制字符串）
     * @return 密钥
     * @throws Exception 密钥生成异常
     */
    private SecretKey getAesKey(String masterPassword, String salt) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(masterPassword.toCharArray(), Hex.decodeHex(salt), iterations, keyLength);
        SecretKey tmp = factory.generateSecret(spec);
        return new SecretKeySpec(tmp.getEncoded(), "AES");
    }

    /**
     * 加密密码（自动生成每条密码的专属盐值，无需外部传入）
     *
     * @param plainPassword  明文密码
     * @param masterPassword 主密码
     * @return 密文（格式：Base64(密码专属盐值 + IV向量 + 加密内容)）
     * @throws Exception 加密异常
     */
    public String encrypt(String plainPassword, String masterPassword) throws Exception {
        // 1. 为当前密码生成专属盐值（每条密码独立）
        String passwordSalt = generateSalt();
        SecretKey aesKey = getAesKey(masterPassword, passwordSalt);

        // 2. 生成随机IV向量
        SecureRandom random = new SecureRandom();
        byte[] iv = new byte[IV_LENGTH];
        random.nextBytes(iv);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        // 3. AES/CBC加密
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, aesKey, ivSpec);
        byte[] encrypted = cipher.doFinal(plainPassword.getBytes(StandardCharsets.UTF_8));

        // 4. 拼接：密码专属盐值（字节） + IV向量 + 加密内容
        byte[] saltBytes = Hex.decodeHex(passwordSalt);
        byte[] combined = new byte[saltBytes.length + iv.length + encrypted.length];
        System.arraycopy(saltBytes, 0, combined, 0, saltBytes.length);
        System.arraycopy(iv, 0, combined, saltBytes.length, iv.length);
        System.arraycopy(encrypted, 0, combined, saltBytes.length + iv.length, encrypted.length);

        // 5. Base64编码返回
        return Base64.getEncoder().encodeToString(combined);
    }

    /**
     * 解密密码（自动从密文中提取密码专属盐值，无需外部传入）
     *
     * @param encryptedPassword 密文（格式：Base64(密码专属盐值 + IV向量 + 加密内容)）
     * @param masterPassword    主密码
     * @return 明文密码
     * @throws Exception 解密异常
     */
    public String decrypt(String encryptedPassword, String masterPassword) throws Exception {
        // 1. 解码Base64
        byte[] combined = Base64.getDecoder().decode(encryptedPassword);

        // 2. 拆分：密码专属盐值（前saltLength字节） + IV向量（接下来16字节） + 加密内容（剩余）
        byte[] saltBytes = new byte[saltLength];
        byte[] iv = new byte[IV_LENGTH];
        byte[] encrypted = new byte[combined.length - saltLength - IV_LENGTH];

        System.arraycopy(combined, 0, saltBytes, 0, saltLength);
        System.arraycopy(combined, saltLength, iv, 0, IV_LENGTH);
        System.arraycopy(combined, saltLength + IV_LENGTH, encrypted, 0, encrypted.length);

        // 3. 还原密码专属盐值（16进制字符串）
        String passwordSalt = Hex.encodeHexString(saltBytes);
        SecretKey aesKey = getAesKey(masterPassword, passwordSalt);

        // 4. AES/CBC解密
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, aesKey, ivSpec);
        byte[] decrypted = cipher.doFinal(encrypted);

        return new String(decrypted, StandardCharsets.UTF_8);
    }

    /**
     * 生成验证哈希（主密码验证用）
     *
     * @param masterPassword 主密码
     * @param verifySalt     验证盐值（独立于密码盐值）
     * @return 验证哈希
     * @throws Exception 验证哈希生成异常
     */
    public String generateVerifyHash(String masterPassword, String verifySalt) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        // 验证哈希用更长的迭代次数，提升安全性
        KeySpec spec = new PBEKeySpec(masterPassword.toCharArray(), Hex.decodeHex(verifySalt), iterations * 2, 512);
        SecretKey tmp = factory.generateSecret(spec);
        return Hex.encodeHexString(tmp.getEncoded());
    }

    /**
     * 验证主密码
     *
     * @param inputMasterPwd   输入的主密码
     * @param storedVerifyHash 存储的验证哈希
     * @param verifySalt       验证盐值
     * @return 验证结果
     * @throws Exception 验证异常
     */
    public boolean verifyMasterPassword(String inputMasterPwd, String storedVerifyHash, String verifySalt) throws Exception {
        String inputHash = generateVerifyHash(inputMasterPwd, verifySalt);
        // 常量时间比较，防止计时攻击
        return MessageDigest.isEqual(inputHash.getBytes(StandardCharsets.UTF_8), storedVerifyHash.getBytes(StandardCharsets.UTF_8));
    }
}
