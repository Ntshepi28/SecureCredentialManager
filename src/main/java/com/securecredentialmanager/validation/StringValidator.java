package com.securecredentialmanager.validation;

public final class StringValidator {

    private StringValidator() {
    }

    public static boolean isNullOrBlank(String value) {
        return value == null || value.isBlank();
    }

    public static boolean isNotNullOrBlank(String value) {
        return !isNullOrBlank(value);
    }

    public static boolean hasMinimumLength(String value, int minimumLength) {
        if (value == null) {
            return false;
        }

        return value.length() >= minimumLength;
    }

    public static boolean hasMaximumLength(String value, int maximumLength) {
        if (value == null) {
            return false;
        }

        return value.length() <= maximumLength;
    }

    public static boolean hasLengthBetween(
            String value,
            int minimumLength,
            int maximumLength) {

        if (value == null) {
            return false;
        }

        int length = value.length();

        return length >= minimumLength && length <= maximumLength;
    }

    public static boolean containsWhitespace(String value) {
        return value != null && value.matches(".*\\s.*");
    }

    public static boolean containsOnlyLetters(String value) {
        return value != null && value.matches("[a-zA-Z]+");
    }

    public static boolean containsOnlyNumbers(String value) {
        return value != null && value.matches("\\d+");
    }

    public static String trim(String value) {
        return value == null ? null : value.trim();
    }
}