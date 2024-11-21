package com.github.jakubzmuda.centralControlStation.support;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class TokenSigner {
    public static String signToken(String identifier, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(identifier.getBytes("UTF-8"));

        byte[] signedBytes = signature.sign();
        return Base64.getEncoder().encodeToString(signedBytes);
    }

    public static void main(String[] args) throws Exception {
        String privateKeyBase64 = "YOUR_PRIVATE_KEY_IN_BASE64";
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyBase64);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));

        String token = signToken("krokiet", privateKey);
        System.out.println("Signed Token: " + token);
    }
}