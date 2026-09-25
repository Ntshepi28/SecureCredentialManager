package com.securecredentialmanager.validation;

public final class PasswordValidator {

    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MAX_PASSWORD_LENGTH = 255;

    private PasswordValidator() {
    }

    public static boolean isValid(String password) {

        return validate(password).isValid();
    }

    public static ValidationResult validate(String password) {

        if (password == null || password.isEmpty()) {
            return ValidationResult.invalid(
                    "Password is required."
            );
        }

        if (password.length() < MIN_PASSWORD_LENGTH) {
            return ValidationResult.invalid(
                    "Password must be at least 8 characters long."
            );
        }

        if (password.length() > MAX_PASSWORD_LENGTH) {
            return ValidationResult.invalid(
                    "Password must not exceed 255 characters."
            );
        }

        if (!containsUppercase(password)) {
            return ValidationResult.invalid(
                    "Password must contain at least one uppercase letter."
            );
        }

        if (!containsLowercase(password)) {
            return ValidationResult.invalid(
                    "Password must contain at least one lowercase letter."
            );
        }

        if (!containsNumber(password)) {
            return ValidationResult.invalid(
                    "Password must contain at least one number."
            );
        }

        if (!containsSpecialCharacter(password)) {
            return ValidationResult.invalid(
                    "Password must contain at least one special character."
            );
        }

        return ValidationResult.valid();
    }

    public static boolean containsUppercase(String password) {
        return password != null && password.matches(".*[A-Z].*");
    }

    public static boolean containsLowercase(String password) {
        return password != null && password.matches(".*[a-z].*");
    }

    public static boolean containsNumber(String password) {
        return password != null && password.matches(".*[0-9].*");
    }

    public static boolean containsSpecialCharacter(String password) {
        return password != null &&
                password.matches(".*[^a-zA-Z0-9].*");
    }

    public static boolean passwordsMatch(
            String password,
            String confirmation) {

        if (password == null || confirmation == null) {
            return false;
        }

        return password.equals(confirmation);
    }

    public static ValidationResult validateConfirmation(
            String password,
            String confirmation) {

        if (confirmation == null || confirmation.isEmpty()) {
            return ValidationResult.invalid(
                    "Please confirm your password."
            );
        }

        if (!passwordsMatch(password, confirmation)) {
            return ValidationResult.invalid(
                    "Passwords do not match."
            );
        }

        return ValidationResult.valid();
    }

    public static int getMinimumLength() {
        return MIN_PASSWORD_LENGTH;
    }

    public static int getMaximumLength() {
        return MAX_PASSWORD_LENGTH;
    }
}