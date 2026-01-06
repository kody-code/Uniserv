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
    @Value("${encrypt.pbkdf2.iterations:65536}")
    private int iterations;

    @Value("${encrypt.pbkdf2.key-length:256}")
    private int keyLength;

    @Value("${encrypt.pbkdf2.salt-length:16}")
    private int saltLength;

    /**
     * 生成盐值
     *
     * @return 盐值
     */
    public String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[saltLength];
        random.nextBytes(salt);
        return Hex.encodeHexString(salt);
    }

    /**
     * 获取 AES 密钥
     *
     * @param masterPassword 主密码
     * @param salt           盐值
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
     * 加密密码
     *
     * @param plainPassword  明文密码
     * @param masterPassword 主密码
     * @param salt           盐值
     * @return 密文密码
     * @throws Exception 加密异常
     */
    public String encrypt(String plainPassword, String masterPassword, String salt) throws Exception {
        SecretKey aesKey = getAesKey(masterPassword, salt);
        SecureRandom random = new SecureRandom();
        byte[] iv = new byte[16];
        random.nextBytes(iv);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, aesKey, ivSpec);
        byte[] encrypted = cipher.doFinal(plainPassword.getBytes(StandardCharsets.UTF_8));

        byte[] combined = new byte[iv.length + encrypted.length];
        System.arraycopy(iv, 0, combined, 0, iv.length);
        System.arraycopy(encrypted, 0, combined, iv.length, encrypted.length);
        return Base64.getEncoder().encodeToString(combined);
    }

    /**
     * 解密密码
     *
     * @param encryptedPassword 密文密码
     * @param masterPassword    主密码
     * @param salt              盐值
     * @return 明文密码
     * @throws Exception 解密异常
     */
    public String decrypt(String encryptedPassword, String masterPassword, String salt) throws Exception {
        SecretKey aesKey = getAesKey(masterPassword, salt);
        byte[] combined = Base64.getDecoder().decode(encryptedPassword);

        byte[] iv = new byte[16];
        byte[] encrypted = new byte[combined.length - iv.length];
        System.arraycopy(combined, 0, iv, 0, iv.length);
        System.arraycopy(combined, iv.length, encrypted, 0, encrypted.length);

        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, aesKey, ivSpec);
        byte[] decrypted = cipher.doFinal(encrypted);
        return new String(decrypted, StandardCharsets.UTF_8);
    }

    /**
     * 生成验证哈希
     *
     * @param masterPassword 主密码
     * @param verifySalt     验证盐值
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
        return MessageDigest.isEqual(inputHash.getBytes(), storedVerifyHash.getBytes());
    }
}
