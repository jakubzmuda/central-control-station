package com.github.jakubzmuda.centralControlStation.usersAndAccess.infrastructure.support;

import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class TokenVerifier {

    public static void main(String[] args) throws Exception {
        String publicKeyBase64 = "YOUR_PUBLIC_KEY_IN_BASE64";
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyBase64);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyBytes));

        String token = "HEADER.PAYLOAD.SIGNATURE";

        if (verifyToken(token, publicKey)) {
            String payload = getPayload(token);
            System.out.println("Token is valid. Payload: " + payload);
        } else {
            System.out.println("Invalid token.");
        }
    }

    public static boolean verifyToken(String token, PublicKey publicKey) throws Exception {
        // Split the token into parts
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid token format");
        }

        String headerAndPayload = parts[0] + "." + parts[1];
        String signatureString = parts[2];

        // Verify the signature
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);
        signature.update(headerAndPayload.getBytes("UTF-8"));

        byte[] signedBytes = Base64.getUrlDecoder().decode(signatureString);
        return signature.verify(signedBytes);
    }

    public static String getPayload(String token) throws Exception {
        // Extract and decode the payload
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid token format");
        }

        String encodedPayload = parts[1];
        return new String(Base64.getUrlDecoder().decode(encodedPayload), "UTF-8");
    }
}
