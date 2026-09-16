package com.securecredentialmanager.models;

import java.time.LocalDateTime;

public class Category {

    private long id;
    private long userId;
    private String name;
    private LocalDateTime createdAt;

    public Category() {
    }

    public Category(long id, long userId, String name, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}