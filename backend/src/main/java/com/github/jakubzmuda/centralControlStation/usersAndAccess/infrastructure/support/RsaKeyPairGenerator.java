package com.github.jakubzmuda.centralControlStation.usersAndAccess.infrastructure.support;

import java.security.*;
import java.util.Base64;

public class RsaKeyPairGenerator {
        public static void main(String[] args) throws Exception {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048); // Key size
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();

            System.out.println("Private Key: " + Base64.getEncoder().encodeToString(privateKey.getEncoded()));
            System.out.println("Public Key: " + Base64.getEncoder().encodeToString(publicKey.getEncoded()));
        }
}
