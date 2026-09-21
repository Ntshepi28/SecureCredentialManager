package com.securecredentialmanager.models;

import java.time.LocalDateTime;

public class AuditLogs {

    private long id;
    private int userId;
    private String action;
    private String description;
    private String ipAddress;
    private String deviceName;
    private LocalDateTime createdAt;

    public AuditLogs(){
    }

    public AuditLogs(long id, int userId, String action, String description, String ipAddress,
                     String deviceName, LocalDateTime createdAt){
        this.id = id;
        this.userId = userId;
        this.action = action;
        this.description = description;
        this.ipAddress = ipAddress;
        this.deviceName = deviceName;
        this.createdAt = createdAt;
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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
