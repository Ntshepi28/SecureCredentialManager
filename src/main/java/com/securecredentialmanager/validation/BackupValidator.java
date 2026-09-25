package com.securecredentialmanager.validation;

import com.securecredentialmanager.enums.BackupStatus;

public final class BackupValidator {

    private static final int MAX_BACKUP_NAME_LENGTH = 255;
    private static final int MAX_BACKUP_PATH_LENGTH = 500;

    private BackupValidator() {
    }

    public static ValidationResult validateBackupName(
            String backupName) {

        if (backupName == null || backupName.isBlank()) {
            return ValidationResult.invalid(
                    "Backup name is required."
            );
        }

        if (backupName.trim().length() > MAX_BACKUP_NAME_LENGTH) {
            return ValidationResult.invalid(
                    "Backup name must not exceed 255 characters."
            );
        }

        return ValidationResult.valid();
    }

    public static ValidationResult validateBackupPath(
            String backupPath) {

        if (backupPath == null || backupPath.isBlank()) {
            return ValidationResult.invalid(
                    "Backup path is required."
            );
        }

        if (backupPath.trim().length() > MAX_BACKUP_PATH_LENGTH) {
            return ValidationResult.invalid(
                    "Backup path must not exceed 500 characters."
            );
        }

        return ValidationResult.valid();
    }

    public static ValidationResult validateBackupSize(
            Integer backupSize) {

        if (backupSize == null) {
            return ValidationResult.invalid(
                    "Backup size is required."
            );
        }

        if (backupSize < 0) {
            return ValidationResult.invalid(
                    "Backup size cannot be negative."
            );
        }

        return ValidationResult.valid();
    }

    public static ValidationResult validateStatus(
            String status) {

        if (status == null || status.isBlank()) {
            return ValidationResult.invalid(
                    "Backup status is required."
            );
        }

        try {
            BackupStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ValidationResult.invalid(
                    "Invalid backup status."
            );
        }

        return ValidationResult.valid();
    }

    public static ValidationResult validateBackup(
            long userId,
            String backupName,
            String backupPath,
            Integer backupSize,
            String status) {

        if (userId <= 0) {
            return ValidationResult.invalid(
                    "Invalid user."
            );
        }

        ValidationResult nameResult =
                validateBackupName(backupName);

        if (!nameResult.isValid()) {
            return nameResult;
        }

        ValidationResult pathResult =
                validateBackupPath(backupPath);

        if (!pathResult.isValid()) {
            return pathResult;
        }

        ValidationResult sizeResult =
                validateBackupSize(backupSize);

        if (!sizeResult.isValid()) {
            return sizeResult;
        }

        ValidationResult statusResult =
                validateStatus(status);

        if (!statusResult.isValid()) {
            return statusResult;
        }

        return ValidationResult.valid();
    }
}