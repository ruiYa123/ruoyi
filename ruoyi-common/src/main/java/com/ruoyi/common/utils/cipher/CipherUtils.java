package com.ruoyi.common.utils.cipher;

import com.ruoyi.common.exception.UtilException;
import lombok.Data;

import javax.crypto.SecretKey;

public class CipherUtils {

    @Data
    public static class Envelop {
        private String key;
        private String cipherContent;
        private String signature;
    }

    public Envelop hybridEncrypt(String plainText) {
        try {
            SecretKey aesKey = AESCipher.generateAESKey();
            byte[] bytes = RSACipher.encryptAESKey(aesKey);
            String cipherText = AESCipher.encryptData(plainText, aesKey);
            byte[] signature = RSACipher.signData(cipherText.getBytes());

            Envelop envelop = new Envelop();
            envelop.setKey(RSACipher.encodeToString(bytes));
            envelop.setCipherContent(cipherText);
            envelop.setSignature(RSACipher.encodeToString(signature));
            return envelop;
        } catch (Exception e) {
            throw new UtilException("混合加密失败", e);
        }
    }

    public String hybridDecrypt(Envelop envelop) {
        try {
            // 解码签名
            byte[] signature = RSACipher.decodeToByte(envelop.getSignature());
            // 验证签名
            if (!RSACipher.verifySignature(RSACipher.decodeToByte(envelop.getCipherContent()), signature)) {
                throw new UtilException("签名验证失败");
            }
            // 解密AES密钥
            byte[] encryptedAESKey = RSACipher.decodeToByte(envelop.getKey());
            SecretKey aesKey = RSACipher.decryptAESKey(encryptedAESKey);
            // 解密数据
            return AESCipher.decryptData(envelop.getCipherContent(), aesKey);
        } catch (Exception e) {
            throw new UtilException("混合解密失败", e);
        }
    }
}
