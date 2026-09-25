package com.securecredentialmanager.validation;

public final class UserValidator {

    private static final int MIN_USERNAME_LENGTH = 3;
    private static final int MAX_USERNAME_LENGTH = 50;

    private UserValidator() {
    }

    public static ValidationResult validateUsername(String username) {

        if (username == null || username.isBlank()) {
            return ValidationResult.invalid(
                    "Username is required."
            );
        }

        String trimmedUsername = username.trim();

        if (trimmedUsername.length() < MIN_USERNAME_LENGTH) {
            return ValidationResult.invalid(
                    "Username must be at least 3 characters long."
            );
        }

        if (trimmedUsername.length() > MAX_USERNAME_LENGTH) {
            return ValidationResult.invalid(
                    "Username must not exceed 50 characters."
            );
        }

        if (!trimmedUsername.matches("[A-Za-z0-9_.-]+")) {
            return ValidationResult.invalid(
                    "Username may only contain letters, numbers, " +
                            "underscores, dots and hyphens."
            );
        }

        return ValidationResult.valid();
    }

    public static ValidationResult validateEmail(String email) {
        return EmailValidator.validate(email);
    }

    public static ValidationResult validatePassword(String password) {
        return PasswordValidator.validate(password);
    }

    public static ValidationResult validateRegistration(
            String username,
            String email,
            String password,
            String confirmation) {

        ValidationResult usernameResult =
                validateUsername(username);

        if (!usernameResult.isValid()) {
            return usernameResult;
        }

        ValidationResult emailResult =
                validateEmail(email);

        if (!emailResult.isValid()) {
            return emailResult;
        }

        ValidationResult passwordResult =
                validatePassword(password);

        if (!passwordResult.isValid()) {
            return passwordResult;
        }

        ValidationResult confirmationResult =
                PasswordValidator.validateConfirmation(
                        password,
                        confirmation
                );

        if (!confirmationResult.isValid()) {
            return confirmationResult;
        }

        return ValidationResult.valid();
    }
}