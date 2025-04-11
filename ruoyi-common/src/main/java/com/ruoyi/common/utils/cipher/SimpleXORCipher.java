package com.ruoyi.common.utils.cipher;

public class SimpleXORCipher {
    private static final String SYMMETRIC_KEY = "wonderful";

    public static String encrypt(String data, String key) {
        StringBuilder encrypted = new StringBuilder();
        for (int i = 0; i < data.length(); i++) {
            char c = (char) (data.charAt(i) ^ key.charAt(i % key.length()));
            encrypted.append(c);
        }
        return encrypted.toString();
    }

    public static String decrypt(String encryptedData, String key) {
        return encrypt(encryptedData, key);
    }


    public static void main(String[] args) {
        String originalText = "this is a text needs to be encrypted";
        System.out.println("Original: " + originalText);

        // 使用对称密钥加密
        String encryptedText = encrypt(originalText, SYMMETRIC_KEY);
        System.out.println("Encrypted: " + encryptedText);

        // 解密
        String decryptedText = decrypt(encryptedText, SYMMETRIC_KEY);
        System.out.println("Decrypted: " + decryptedText);

    }
}
