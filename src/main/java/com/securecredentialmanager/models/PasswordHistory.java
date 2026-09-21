package com.securecredentialmanager.models;

import java.time.LocalDateTime;

public class PasswordHistory {
    private long id;
    private int credentialId;
    private String encryptedPassword;
    private LocalDateTime changedAt;

    public PasswordHistory() {
    }

    public PasswordHistory(long id, int credentialId,
                           String encryptedPassword,
                           LocalDateTime changedAt){
        this.id = id;
        this.credentialId = credentialId;
        this.encryptedPassword = encryptedPassword;
        this.changedAt = changedAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(int credentialId) {
        this.credentialId = credentialId;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public LocalDateTime getChangedAt() {
        return changedAt;
    }

    public void setChangedAt(LocalDateTime changedAt) {
        this.changedAt = changedAt;
    }
}
