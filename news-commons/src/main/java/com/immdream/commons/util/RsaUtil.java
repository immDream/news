package com.immdream.commons.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.codec.binary.Base64;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * RSA 工具类，公钥私钥生成，加密解密
 * <p>
 * news com.immdream.commons.util
 *
 * @author immDream
 * @date 2023/04/12/9:31
 * @since 1.8
 */
public class RsaUtil {
    private static final String SRC = "1a2b3c";

    /**
     * 生成 RSA 公私钥对
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static RsaKeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
        String publicKeyString = Base64.encodeBase64String(rsaPublicKey.getEncoded());
        String privateKeyString = Base64.encodeBase64String(rsaPrivateKey.getEncoded());
        return new RsaKeyPair(publicKeyString, privateKeyString);
    }
    /**
     * RSA 公私钥对
     */
    @Data
    @AllArgsConstructor
    public static class RsaKeyPair {
        private final String publicKey;
        private final String privateKey;
    }
}
