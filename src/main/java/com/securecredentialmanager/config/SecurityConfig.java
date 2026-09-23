package com.securecredentialmanager.config;

public final class SecurityConfig {

    private static final int DEFAULT_MAX_LOGIN_ATTEMPTS = 5;
    private static final int DEFAULT_AUTO_LOCK_MINUTES = 5;
    private static final int DEFAULT_PASSWORD_GENERATOR_LENGTH = 16;
    private static final int MIN_PASSWORD_GENERATOR_LENGTH = 8;
    private static final int MAX_PASSWORD_GENERATOR_LENGTH = 64;

    private SecurityConfig() {
    }

    public static int getMaxLoginAttempts() {
        return getIntegerEnvironmentValue(
                "SCM_MAX_LOGIN_ATTEMPTS",
                DEFAULT_MAX_LOGIN_ATTEMPTS
        );
    }

    public static int getDefaultAutoLockMinutes() {
        return getIntegerEnvironmentValue(
                "SCM_AUTO_LOCK_MINUTES",
                DEFAULT_AUTO_LOCK_MINUTES
        );
    }

    public static int getDefaultPasswordGeneratorLength() {
        return getIntegerEnvironmentValue(
                "SCM_PASSWORD_GENERATOR_LENGTH",
                DEFAULT_PASSWORD_GENERATOR_LENGTH
        );
    }

    public static int getMinPasswordGeneratorLength() {
        return MIN_PASSWORD_GENERATOR_LENGTH;
    }

    public static int getMaxPasswordGeneratorLength() {
        return MAX_PASSWORD_GENERATOR_LENGTH;
    }

    private static int getIntegerEnvironmentValue(
            String variableName,
            int defaultValue) {

        String value = System.getenv(variableName);

        if (value == null || value.isBlank()) {
            return defaultValue;
        }

        try {
            int result = Integer.parseInt(value);

            if (result <= 0) {
                return defaultValue;
            }

            return result;

        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}