package com.securecredentialmanager.config;

public final class EncryptionConfig {

    private static final String ENVIRONMENT_VARIABLE =
            "SCM_ENCRYPTION_KEY";

    private EncryptionConfig() {
    }

    public static String getEncryptionKey() {

        String key = System.getenv(ENVIRONMENT_VARIABLE);

        if (key == null || key.isBlank()) {
            throw new IllegalStateException(
                    "Encryption key is not configured. " +
                            "Set the SCM_ENCRYPTION_KEY environment variable."
            );
        }

        validateKey(key);

        return key;
    }

    private static void validateKey(String key) {

        int length = key.getBytes(java.nio.charset.StandardCharsets.UTF_8).length;

        if (length != 16 && length != 24 && length != 32) {
            throw new IllegalStateException(
                    "Encryption key must be exactly 16, 24, or 32 bytes."
            );
        }
    }
}