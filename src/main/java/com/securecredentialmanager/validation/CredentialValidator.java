package com.securecredentialmanager.validation;

public final class CredentialValidator {

    private static final int MAX_SERVICE_NAME_LENGTH = 100;
    private static final int MAX_WEBSITE_LENGTH = 255;
    private static final int MAX_USERNAME_LENGTH = 100;

    private CredentialValidator() {
    }

    public static ValidationResult validateServiceName(
            String serviceName) {

        if (serviceName == null || serviceName.isBlank()) {
            return ValidationResult.invalid(
                    "Service name is required."
            );
        }

        if (serviceName.trim().length() > MAX_SERVICE_NAME_LENGTH) {
            return ValidationResult.invalid(
                    "Service name must not exceed 100 characters."
            );
        }

        return ValidationResult.valid();
    }

    public static ValidationResult validateWebsite(
            String website) {

        if (website == null || website.isBlank()) {
            return ValidationResult.valid();
        }

        if (website.trim().length() > MAX_WEBSITE_LENGTH) {
            return ValidationResult.invalid(
                    "Website must not exceed 255 characters."
            );
        }

        return ValidationResult.valid();
    }

    public static ValidationResult validateLoginUsername(
            String loginUsername) {

        if (loginUsername == null || loginUsername.isBlank()) {
            return ValidationResult.invalid(
                    "Login username is required."
            );
        }

        if (loginUsername.trim().length() > MAX_USERNAME_LENGTH) {
            return ValidationResult.invalid(
                    "Login username must not exceed 100 characters."
            );
        }

        return ValidationResult.valid();
    }

    public static ValidationResult validatePassword(
            String password) {

        if (password == null || password.isEmpty()) {
            return ValidationResult.invalid(
                    "Credential password is required."
            );
        }

        return ValidationResult.valid();
    }

    public static ValidationResult validateNotes(
            String notes) {

        if (notes == null || notes.isBlank()) {
            return ValidationResult.valid();
        }

        return ValidationResult.valid();
    }

    public static ValidationResult validateCredential(
            int userId,
            String serviceName,
            String website,
            String loginUsername,
            String password,
            String notes) {

        if (userId <= 0) {
            return ValidationResult.invalid(
                    "Invalid user."
            );
        }

        ValidationResult serviceResult =
                validateServiceName(serviceName);

        if (!serviceResult.isValid()) {
            return serviceResult;
        }

        ValidationResult websiteResult =
                validateWebsite(website);

        if (!websiteResult.isValid()) {
            return websiteResult;
        }

        ValidationResult usernameResult =
                validateLoginUsername(loginUsername);

        if (!usernameResult.isValid()) {
            return usernameResult;
        }

        ValidationResult passwordResult =
                validatePassword(password);

        if (!passwordResult.isValid()) {
            return passwordResult;
        }

        ValidationResult notesResult =
                validateNotes(notes);

        if (!notesResult.isValid()) {
            return notesResult;
        }

        return ValidationResult.valid();
    }
}
