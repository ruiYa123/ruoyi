package com.ruoyi.common.utils.cipher;

public class SimpleAsymmetricCipher {
    private static final int k = 5;

    // 生成公钥和私钥
    public static int[] generateKeys() {
        int publicKey = 3;
        int privateKey = k * publicKey;
        return new int[]{publicKey, privateKey};
    }

    // 加密
    public static int encrypt(int message, int publicKey) {
        return (message + publicKey) % 256;
    }

    // 解密
    public static int decrypt(int cipherText, int privateKey) {
        return (cipherText - privateKey / k)  % 256;
    }

    public static void main(String[] args) {
        // 生成密钥对
        int[] keys = generateKeys();
        int publicKey = keys[0];
        int privateKey = keys[1];

        System.out.println("Public Key: " + publicKey);
        System.out.println("Private Key: " + privateKey);

        // 明文
        String originalText = "Hello";
        System.out.println("Original: " + originalText);

        // 加密
        StringBuilder encryptedText = new StringBuilder();
        for (char c : originalText.toCharArray()) {
            int cipher = encrypt(c, publicKey);
            encryptedText.append((char) cipher);
        }
        System.out.println("Encrypted: " + encryptedText);

        // 解密
        StringBuilder decryptedText = new StringBuilder();
        for (char c : encryptedText.toString().toCharArray()) {
            int message = decrypt(c, privateKey);
            decryptedText.append((char) message);
        }
        System.out.println("Decrypted: " + decryptedText);
    }
}
