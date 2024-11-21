package com.github.jakubzmuda.centralControlStation.usersAndAccess.infrastructure.support;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class TokenSigner {
    public static String signToken(String user, PrivateKey privateKey) throws Exception {
        // Create JWT header
        ObjectMapper objectMapper = new ObjectMapper();
        String header = objectMapper.writeValueAsString(new Header("RS256", "JWT"));
        String encodedHeader = Base64.getUrlEncoder().withoutPadding().encodeToString(header.getBytes("UTF-8"));

        // Create JWT payload
        String payload = objectMapper.writeValueAsString(new JwtTokenPayload(user));
        String encodedPayload = Base64.getUrlEncoder().withoutPadding().encodeToString(payload.getBytes("UTF-8"));

        // Concatenate header and payload
        String headerAndPayload = encodedHeader + "." + encodedPayload;

        // Sign the header and payload
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(headerAndPayload.getBytes("UTF-8"));

        byte[] signedBytes = signature.sign();
        String encodedSignature = Base64.getUrlEncoder().withoutPadding().encodeToString(signedBytes);

        // Return the full JWT
        return headerAndPayload + "." + encodedSignature;
    }

    public static void main(String[] args) throws Exception {
        String privateKeyBase64 = "";
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyBase64);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));

        String user = "krokiet";
        String token = signToken(user, privateKey);
        System.out.println("Signed Token - " + user + ": " + token);
    }


    record Header(String algorithm, String type) {
    }
}
