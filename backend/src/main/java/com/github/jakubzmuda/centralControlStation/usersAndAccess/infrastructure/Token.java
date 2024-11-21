package com.github.jakubzmuda.centralControlStation.usersAndAccess.infrastructure;


import com.github.jakubzmuda.centralControlStation.core.application.BadRequestException;

import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class Token {

    private static final String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAndcsaiIypEz3FmjfEVPGsNkDspqUUanEj2NMWQqAbrVn0Fh/YDGGoMFCVkGeoaf3WLlPLfyu1I8N5KE3ypZ36gj8dF6pAxyJPHb5DQ2wPTthwGPdER0ZPpo02JQhJurJVD9vFnh0dZ7+22bwxGaJKmIP7zGMPFV3ydVViIxRlHi//pFo1AUCYv5sgl8hJXCzJUl7gtDJReDeoxWVlDU249X+7GytxpDnfkmy2Z8JBStKlJ6S/vTF/IEPrnvwmzB7eVVfneXfoHMeyBKBUDgYVqDdTv3/gTQlsuXwNUf3vXx7Gtt67rp7gUwLuI5HplmXetLq0sZv5mMBglJHlz9tgwIDAQAB";
    private final String encodedHeader;
    private final String encodedPayload;
    private final String encodedSignature;


    public Token(String value) {
        String[] tokenParts = value.split("\\.");

        if (tokenParts.length != 3) {
            throw new BadRequestException("Invalid token format");
        }

        this.encodedHeader = tokenParts[0];
        this.encodedPayload = tokenParts[1];
        this.encodedSignature = tokenParts[2];
    }

    public static Token of(String value) {
        return new Token(value);
    }

    public boolean isValid() {
        try {
            byte[] publicKeyBytes = Base64.getDecoder().decode(publicKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyBytes));

            String headerAndPayload = encodedHeader + "." + encodedPayload;

            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initVerify(publicKey);
            signature.update(headerAndPayload.getBytes("UTF-8"));

            byte[] signedBytes = Base64.getUrlDecoder().decode(encodedSignature);
            return signature.verify(signedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Could not verify token signature", e);
        }
    }

    public TokenPayload getPayload() {
        try {
            return TokenPayload.of(new String(Base64.getUrlDecoder().decode(encodedPayload), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new BadRequestException("Could not decode token payload");
        }
    }

    record TokenPayload(String user) {
        public static TokenPayload of(String payload) {
            return new TokenPayload(payload);
        }
    }
}

