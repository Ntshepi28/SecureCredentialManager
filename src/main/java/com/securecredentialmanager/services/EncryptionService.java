package com.securecredentialmanager.services;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class EncryptionService {

    private static final String ALGORITHM = "AES";

    private final SecretKeySpec secretKey;

    public EncryptionService(String secrete){
        byte[] key = secrete.getBytes(StandardCharsets.UTF_8);

        if (key.length != 16 && key.length != 24
                 && key.length != 32){
            throw new IllegalArgumentException("Encryption key must be 16, 24, or 32 bytes");
        }

        this.secretKey = new SecretKeySpec(key, ALGORITHM);
    }

    public String encrypt(String plainText){

        if (plainText == null){
            return null;
        }

        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e){
            throw new RuntimeException("Unable to encrypt data", e);
        }
    }
}
