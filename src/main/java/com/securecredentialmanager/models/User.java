package com.securecredentialmanager.models;

import java.time.LocalDateTime;

public class User {

    private int Id;
    private String Username;
    private String Email;
    private String PasswordHash;
    private String accountStatus;
    private int failedLoginAttempts;
    private LocalDateTime lastLogin;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public User(int id, String username, String email, String passwordHash, String accountStatus, int failedLoginAttempts,
    LocalDateTime lastLogin, LocalDateTime createdAt, LocalDateTime updatedAt) {
        Id = id;
        Username = username;
        Email = email;
        PasswordHash = passwordHash;
        accountStatus = accountStatus;
        failedLoginAttempts = failedLoginAttempts;
        lastLogin = lastLogin;
        createdAt = createdAt;
        updatedAt = updatedAt;
    }

    public User(String username, String email, String passwordHash) {
        Username = username;
        Email = email;
        PasswordHash = passwordHash;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        this.Username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getPasswordHash() {
        return PasswordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.PasswordHash = passwordHash;
    }

    public String getAccountStatus(){
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public int getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    public void setFailedLoginAttempts(int failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
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
