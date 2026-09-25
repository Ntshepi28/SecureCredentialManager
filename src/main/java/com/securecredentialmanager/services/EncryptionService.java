package com.securecredentialmanager.services;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

public class EncryptionService {

    private static final String ALGORITHM = "AES/GCM/NoPadding";

    private static final int GCM_TAG_LENGTH = 128;
    private static final int IV_LENGTH = 12;

    private final SecretKeySpec secretKey;
    private final SecureRandom secureRandom;

    /**
     * Default no-argument constructor.
     * Checks for SCM_ENCRYPTION_KEY in environment variables; falls back to an exact 32-byte dev key if missing.
     */
    public EncryptionService() {
        this(resolveSecretKey());
    }

    /**
     * Parameterized constructor that takes an explicit key string.
     */
    public EncryptionService(String secret) {

        if (secret == null || secret.isBlank()) {
            throw new IllegalArgumentException(
                    "Encryption key cannot be null or empty"
            );
        }

        byte[] key = secret.getBytes(StandardCharsets.UTF_8);

        if (key.length != 16
                && key.length != 24
                && key.length != 32) {

            throw new IllegalArgumentException(
                    "Encryption key must be 16, 24, or 32 bytes"
            );
        }

        this.secretKey = new SecretKeySpec(key, "AES");
        this.secureRandom = new SecureRandom();
    }

    /**
     * Helper method to resolve the secret key from environment variables or fallback.
     */
    private static String resolveSecretKey() {
        String envKey = System.getenv("SCM_ENCRYPTION_KEY");
        if (envKey == null || envKey.isBlank()) {
            System.out.println("WARNING: SCM_ENCRYPTION_KEY missing. Using local dev fallback key.");
            // Must be EXACTLY 32 characters (32 bytes for AES-256)
            return "12345678901234567890123456789012";
        }
        return envKey;
    }

    // ==========================================
    // ENCRYPT
    // ==========================================

    public String encrypt(String plainText) {

        if (plainText == null) {
            return null;
        }

        try {

            // Generate a new random IV for every encryption
            byte[] iv = new byte[IV_LENGTH];
            secureRandom.nextBytes(iv);

            Cipher cipher = Cipher.getInstance(ALGORITHM);

            GCMParameterSpec gcmParameterSpec =
                    new GCMParameterSpec(
                            GCM_TAG_LENGTH,
                            iv
                    );

            cipher.init(
                    Cipher.ENCRYPT_MODE,
                    secretKey,
                    gcmParameterSpec
            );

            byte[] encryptedData =
                    cipher.doFinal(
                            plainText.getBytes(StandardCharsets.UTF_8)
                    );

            /*
             * Store IV together with encrypted data.
             *
             * Format: Base64(IV + encrypted data + authentication tag)
             */

            byte[] combined =
                    new byte[iv.length + encryptedData.length];

            System.arraycopy(
                    iv,
                    0,
                    combined,
                    0,
                    iv.length
            );

            System.arraycopy(
                    encryptedData,
                    0,
                    combined,
                    iv.length,
                    encryptedData.length
            );

            return Base64.getEncoder()
                    .encodeToString(combined);

        } catch (Exception e) {

            throw new RuntimeException(
                    "Unable to encrypt data",
                    e
            );
        }
    }

    // ==========================================
    // DECRYPT
    // ==========================================

    public String decrypt(String encryptedText) {

        if (encryptedText == null) {
            return null;
        }

        try {

            byte[] combined =
                    Base64.getDecoder()
                            .decode(encryptedText);

            if (combined.length <= IV_LENGTH) {
                throw new IllegalArgumentException(
                        "Invalid encrypted data"
                );
            }

            // Extract IV
            byte[] iv =
                    new byte[IV_LENGTH];

            System.arraycopy(
                    combined,
                    0,
                    iv,
                    0,
                    IV_LENGTH
            );

            // Extract encrypted data + authentication tag
            byte[] encryptedData =
                    new byte[combined.length - IV_LENGTH];

            System.arraycopy(
                    combined,
                    IV_LENGTH,
                    encryptedData,
                    0,
                    encryptedData.length
            );

            Cipher cipher =
                    Cipher.getInstance(ALGORITHM);

            GCMParameterSpec gcmParameterSpec =
                    new GCMParameterSpec(
                            GCM_TAG_LENGTH,
                            iv
                    );

            cipher.init(
                    Cipher.DECRYPT_MODE,
                    secretKey,
                    gcmParameterSpec
            );

            byte[] decryptedData =
                    cipher.doFinal(encryptedData);

            return new String(
                    decryptedData,
                    StandardCharsets.UTF_8
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "Unable to decrypt data",
                    e
            );
        }
    }
}