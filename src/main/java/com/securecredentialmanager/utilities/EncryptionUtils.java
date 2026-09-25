package com.securecredentialmanager.utilities;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

public class EncryptionUtils {

    private static final String ALGORITHM = "AES/GCM/NoPadding";
    private static final int GCM_TAG_LENGTH = 128; // in bits
    private static final int IV_LENGTH_BYTES = 12; // 96 bits standard for GCM

    /**
     * Encrypts plain text using AES-GCM encryption.
     *
     * @param plainText The raw password or sensitive data to encrypt.
     * @param secretKey A 32-character (256-bit) key string.
     * @return Base64 encoded string containing IV + encrypted payload.
     * @throws Exception If key size is invalid or encryption fails.
     */
    public static String encryptAES(String plainText, String secretKey) throws Exception {
        if (plainText == null || secretKey == null) {
            throw new IllegalArgumentException("Plain text and secret key must not be null.");
        }

        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        if (keyBytes.length != 32) {
            throw new IllegalArgumentException("Secret key must be exactly 32 bytes (32 characters) for AES-256.");
        }

        // Generate a random Initialization Vector (IV)
        byte[] iv = new byte[IV_LENGTH_BYTES];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);

        // Initialize SecretKey and Cipher
        SecretKey key = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        GCMParameterSpec gcmSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.ENCRYPT_MODE, key, gcmSpec);

        // Encrypt the plain text
        byte[] cipherText = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

        // Combine IV and cipherText into a single array for easier storage
        byte[] combined = new byte[iv.length + cipherText.length];
        System.arraycopy(iv, 0, combined, 0, iv.length);
        System.arraycopy(cipherText, 0, combined, iv.length, cipherText.length);

        // Return Base64 encoded string
        return Base64.getEncoder().encodeToString(combined);
    }

    /**
     * Decrypts a Base64 encoded payload back into plain text using AES-GCM.
     *
     * @param encryptedData Base64 encoded payload containing IV + encrypted payload.
     * @param secretKey     The matching 32-character secret key.
     * @return The original plain text password.
     * @throws Exception If key size is invalid, data is corrupted, or decryption fails.
     */
    public static String decryptAES(String encryptedData, String secretKey) throws Exception {
        if (encryptedData == null || secretKey == null) {
            throw new IllegalArgumentException("Encrypted data and secret key must not be null.");
        }

        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        if (keyBytes.length != 32) {
            throw new IllegalArgumentException("Secret key must be exactly 32 bytes (32 characters) for AES-256.");
        }

        byte[] combined = Base64.getDecoder().decode(encryptedData);

        if (combined.length < IV_LENGTH_BYTES) {
            throw new IllegalArgumentException("Invalid encrypted payload size.");
        }

        // Extract IV and cipherText
        byte[] iv = new byte[IV_LENGTH_BYTES];
        byte[] cipherText = new byte[combined.length - IV_LENGTH_BYTES];

        System.arraycopy(combined, 0, iv, 0, IV_LENGTH_BYTES);
        System.arraycopy(combined, IV_LENGTH_BYTES, cipherText, 0, cipherText.length);

        // Initialize SecretKey and Cipher
        SecretKey key = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        GCMParameterSpec gcmSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.DECRYPT_MODE, key, gcmSpec);

        // Decrypt to original byte array
        byte[] plainTextBytes = cipher.doFinal(cipherText);

        return new String(plainTextBytes, StandardCharsets.UTF_8);
    }
}