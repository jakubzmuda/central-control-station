package com.github.jakubzmuda.centralControlStation.support;

import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class TokenVerifier {
    public static boolean verifyToken(String identifier, String signedToken, PublicKey publicKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);
        signature.update(identifier.getBytes("UTF-8"));

        byte[] signedBytes = Base64.getDecoder().decode(signedToken);
        return signature.verify(signedBytes);
    }

    public static void main(String[] args) throws Exception {
        String publicKeyBase64 = "YOUR_PUBLIC_KEY_IN_BASE64";
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyBase64);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyBytes));

        String identifier = "krokiet";
        String signedToken = "SIGNATURE_FROM_SIGN_TOKEN_METHOD";

        boolean isValid = verifyToken(identifier, signedToken, publicKey);
        System.out.println("Is the token valid? " + isValid);
    }
}
