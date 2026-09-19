package com.securecredentialmanager.models;

import java.time.LocalDateTime;

public class Credential {

    private int id;
    private int userId;
    private Integer categoryId;
    private String serviceName;
    private String website;
    private String loginUsername;
    private String encryptedPassword;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Credential() {
    }

    public Credential(int id, int userId, Integer categoryId,
                      String serviceName, String website,
                      String loginUsername, String encryptedPassword,
                      String notes, LocalDateTime createdAt,
                      LocalDateTime updatedAt) {

        this.id = id;
        this.userId = userId;
        this.categoryId = categoryId;
        this.serviceName = serviceName;
        this.website = website;
        this.loginUsername = loginUsername;
        this.encryptedPassword = encryptedPassword;
        this.notes = notes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLoginUsername() {
        return loginUsername;
    }

    public void setLoginUsername(String loginUsername) {
        this.loginUsername = loginUsername;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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