package com.securecredentialmanager.validation;

import java.util.regex.Pattern;

public final class EmailValidator {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile(
                    "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
            );

    private static final int MAX_EMAIL_LENGTH = 100;

    private EmailValidator() {
    }

    public static boolean isValid(String email) {

        if (email == null || email.isBlank()) {
            return false;
        }

        String trimmedEmail = email.trim();

        if (trimmedEmail.length() > MAX_EMAIL_LENGTH) {
            return false;
        }

        return EMAIL_PATTERN.matcher(trimmedEmail).matches();
    }

    public static ValidationResult validate(String email) {

        if (email == null || email.isBlank()) {
            return ValidationResult.invalid(
                    "Email address is required."
            );
        }

        String trimmedEmail = email.trim();

        if (trimmedEmail.length() > MAX_EMAIL_LENGTH) {
            return ValidationResult.invalid(
                    "Email address must not exceed 100 characters."
            );
        }

        if (!EMAIL_PATTERN.matcher(trimmedEmail).matches()) {
            return ValidationResult.invalid(
                    "Please enter a valid email address."
            );
        }

        return ValidationResult.valid();
    }
}