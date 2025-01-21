package com.ruoyi.common.encrypt;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;

public class SM4Encryption {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static String encrypt(String plainText, byte[] key) throws Exception {
        Cipher cipher = Cipher.getInstance("SM4/ECB/PKCS5Padding", "BC");

        SecretKeySpec keySpec = new SecretKeySpec(key, "SM4");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);

        byte[] encryptedData = cipher.doFinal(plainText.getBytes());

        return Hex.toHexString(encryptedData);
    }

    public static String decrypt(String encryptedText, byte[] key) throws Exception {
        Cipher cipher = Cipher.getInstance("SM4/ECB/PKCS5Padding", "BC");

        SecretKeySpec keySpec = new SecretKeySpec(key, "SM4");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);

        byte[] decryptedData = cipher.doFinal(Hex.decode(encryptedText));

        return new String(decryptedData);
    }

    public static void main(String[] args) {
        try {
            byte[] key = Hex.decode("0123456789ABCDEFFEDCBA9876543210"); // 16字节密钥

            String plainText = "这是一个测试消息";
            System.out.println("原始文本: " + plainText);

            String encryptedText = encrypt(plainText, key);
            System.out.println("加密后的文本: " + encryptedText);

            String decryptedText = decrypt(encryptedText, key);
            System.out.println("解密后的文本: " + decryptedText);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
