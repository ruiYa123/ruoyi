package com.ruoyi.common.utils.cipher;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSACipher {

    private static String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlY0fUT+KSmuMOu+ssp/yCARg8KZvgpZtMWrJYom74uIRFNNrwOsTKQ2+6HSJziAIVFYrjLRiPEJWMzrOBw1LFYG9uEEwxT08H/nUO9Jr0hRWodViDQDUCVcx2LWDP+/W5HDXZ7Ck+Gkt3UW0u/QJ95yO3fy54iE/44DhffbEaHFpjMTLGssWnI8cfMDfmsTznE7cUTRSD2Az3vqYr7En9Tl1JW5IZoV383nRZoETGcBc6wTor1gZfDMLPdnr3WuZ+47zLm82PQjCIs3weRdYAaXtz9NCUzOoek9oNk6XCKzk3ulsEDdjZ09Q7Qi1FRrIUOzKLBBjGroTbZcH5zAj7wIDAQAB";

    private static String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCVjR9RP4pKa4w676yyn/IIBGDwpm+Clm0xasliibvi4hEU02vA6xMpDb7odInOIAhUViuMtGI8QlYzOs4HDUsVgb24QTDFPTwf+dQ70mvSFFah1WINANQJVzHYtYM/79bkcNdnsKT4aS3dRbS79An3nI7d/LniIT/jgOF99sRocWmMxMsayxacjxx8wN+axPOcTtxRNFIPYDPe+pivsSf1OXUlbkhmhXfzedFmgRMZwFzrBOivWBl8Mws92evda5n7jvMubzY9CMIizfB5F1gBpe3P00JTM6h6T2g2TpcIrOTe6WwQN2NnT1DtCLUVGshQ7MosEGMauhNtlwfnMCPvAgMBAAECggEARq1hshe55w7pwIa50tdVXqWXHNRDme82nqatkyStx314fiOm5o8SSJIm+Zvt7pzj4tuwjDhJvtnI/EAb02hcZkchHt2sS2LzsdbzjuwUqb2T7bq3MpcEHn/3Xkf2Rfa209Jicq4gYQfwNYU2c6dTOJLkUBQa0zxMgdvMdaf0TbkmlDS8KvJjs//+e/gar5DYWSA2SboWyDpAY4D4pn2SPDEjpvx50vfCj1ZWdl/wDdEUFwdewhiuXpeQR0MYF6lELAZ1B4GB/6Dd7G57LRu8mf5xq31DQfTVRWNERe5aT+oKzTaWc6ltg9ggkT5AYQO4tK1tMq+y0tEIDST+skpT/QKBgQC9mn6y1owhCH8djI3fpVvBDwOqCohMiV2Lz3H5OvrJ7VHoW2R7Yr3VhFIlSqQwPoWIeyJs7pFhh5klPILPXOWe+DF9n6n24cwBxgAE0GpPce6OryqYWhAZhSw8Z+tERWF/Y8bGuAsyP7aDI0CN3achSKnm37vgb6DH6w3ZPc5CLQKBgQDJ7AoCsk/GJOhlfuFA9/1OSnSB7zAZMUgRffGqoYvSzV63tMyeKKBS9Rz0KBNmsyId+CG/ntfZUQVwy4q3sObiqj4Lzm81dA0Xu8qFx2QOeCBgwrLIa83S31ykqlM0KYONygq3WCpUsZHqVoSsaUmCcCTRxC3hy3A5JZlv/0v8CwKBgFPVKKoIROMiL5owGFbHdV9SNSuNBqDYp7z2lw08r8BypyGWr0IC8a6jl/KXIyuVqVMkJp+FiFBYNTWKoMfw6jI0ySUjYHIheiNamY2HXUoNNQ5vkuTUBYRTJrD6vcJs7uP1iOE6ZPDtzNQE1TkkxQ3pY/4J9LcShEOAwZnJQxZtAoGAI0ft29jCHZH3VTydKRfrl5Fk2Ast8UjuGbhS0IiXgMbHhPu/QOdCVBxl6bdS5H/qNyhS5UNg3aTNeY7m9v+e5/3tB20Te1iCrSHv2f4j2m9c4OtEB8bmWLEUY+Uq0pNqXAzwYGa0RsYcA8EIP4PKB1OG62wGxEwimKmcLLiD008CgYEAoIwerdTZ5H8PkiZjI7Ps62gvqguexw/w2ZNXhPLiMgDLM85gMCPyTE9DdooyjsWxZIUGlXvZBqu1tdZaIsUoF7MPe6Y/beIxg+dNPlOk+RheTY6euqFRtD9+7ppOwut0d70SOKrcl+ez2FdKy4bNVzk1sj2Sbpx1ch30eOaufRo=\n";


    public static PublicKey stringToPublicKey(String publicKeyString) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(publicKeyString);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(spec);
    }


    public static PrivateKey stringToPrivateKey(String privateKeyString) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(privateKeyString);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(spec);
    }

    public static String encodeToString(byte[] keyBytes) {
        return Base64.getEncoder().encodeToString(keyBytes);
    }

    public static byte[] decodeToByte(String base64EncodedString) {
        return Base64.getDecoder().decode(base64EncodedString);
    }

    public static byte[] encryptAESKey(SecretKey aesKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, stringToPublicKey(publicKey));
        return cipher.doFinal(aesKey.getEncoded());
    }

    // 使用 RSA 私钥解密 AES 密钥
    public static SecretKey decryptAESKey(byte[] encryptedAESKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, stringToPrivateKey(privateKey));
        byte[] decryptedKeyBytes = cipher.doFinal(encryptedAESKey);
        return new SecretKeySpec(decryptedKeyBytes, "AES");
    }

    // 使用私钥生成签名
    public static byte[] signData(byte[] data) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(stringToPrivateKey(privateKey));
        signature.update(data);
        return signature.sign();
    }

    // 使用公钥验证签名
    public static boolean verifySignature(byte[] data, byte[] signatureBytes) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(stringToPublicKey(publicKey));
        signature.update(data);
        return signature.verify(signatureBytes);
    }


    // 生成 RSA 密钥对（2048位）
    public static KeyPair generateRSAKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }

    public static String publicKeyToString(PublicKey publicKey) {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    public static String privateKeyToString(PrivateKey privateKey) {
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }

}
