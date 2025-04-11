package com.ruoyi.common.utils.cipher;

import com.ruoyi.common.exception.UtilException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class AESCipher {

    // 生成 AES 密钥（256位）
    public static SecretKey generateAESKey()  {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            return keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new UtilException("未找到AES加密算法", e);
        }

    }

    public static String secretKeyToString(SecretKey secretKey) {
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    public static SecretKey stringToSecretKey(String secretKeyString) {
        byte[] keyBytes = Base64.getDecoder().decode(secretKeyString);
        return new SecretKeySpec(keyBytes, "AES");
    }

    // 使用 AES 加密数据（CBC模式，带随机IV）
    public static String encryptData(String plainText, SecretKey aesKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] ivBytes = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(ivBytes); // 生成随机IV
        IvParameterSpec iv = new IvParameterSpec(ivBytes);
        cipher.init(Cipher.ENCRYPT_MODE, aesKey, iv);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        // 将 IV 和加密数据拼接，便于传输
        byte[] combined = new byte[ivBytes.length + encryptedBytes.length];
        System.arraycopy(ivBytes, 0, combined, 0, ivBytes.length);
        System.arraycopy(encryptedBytes, 0, combined, ivBytes.length, encryptedBytes.length);
        return Base64.getEncoder().encodeToString(combined);
    }

    // 使用 AES 解密数据
    public static String decryptData(String encryptedText, SecretKey aesKey) throws Exception {
        byte[] combined = Base64.getDecoder().decode(encryptedText);
        byte[] ivBytes = new byte[16];
        byte[] encryptedBytes = new byte[combined.length - ivBytes.length];
        System.arraycopy(combined, 0, ivBytes, 0, ivBytes.length);
        System.arraycopy(combined, ivBytes.length, encryptedBytes, 0, encryptedBytes.length);
        IvParameterSpec iv = new IvParameterSpec(ivBytes);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, aesKey, iv);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes);
    }

}
