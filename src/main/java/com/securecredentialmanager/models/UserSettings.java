package com.securecredentialmanager.models;

import java.time.LocalDateTime;

public class UserSettings {

    private long id;
    private int userId;
    private String theme;
    private int autoLockMinutes;
    private int passwordGeneratorLength;
    private boolean requireMasterPassword;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserSettings(){
    }

    public UserSettings(long id, int userId, String theme, int autoLockMinutes,
                        int passwordGeneratorLength, boolean requireMasterPassword,
                        LocalDateTime createdAt, LocalDateTime updatedAt){

        this.id = id;
        this.userId = userId;
        this.theme = theme;
        this.autoLockMinutes = autoLockMinutes;
        this.passwordGeneratorLength = autoLockMinutes;
        this.requireMasterPassword = requireMasterPassword;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public int getAutoLockMinutes() {
        return autoLockMinutes;
    }

    public void setAutoLockMinutes(int autoLockMinutes) {
        this.autoLockMinutes = autoLockMinutes;
    }

    public int getPasswordGeneratorLength() {
        return passwordGeneratorLength;
    }

    public void setPasswordGeneratorLength(int passwordGeneratorLength) {
        this.passwordGeneratorLength = passwordGeneratorLength;
    }

    public boolean isRequireMasterPassword() {
        return requireMasterPassword;
    }

    public void setRequireMasterPassword(boolean requireMasterPassword) {
        this.requireMasterPassword = requireMasterPassword;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
