package com.securecredentialmanager.models;

import java.time.LocalDateTime;

public class Sessions {

    private long id;
    private int userId;
    private String sessionToken;
    private String ipAddress;
    private String deviceName;
    private LocalDateTime loginTime;
    private LocalDateTime lastActivity;
    private LocalDateTime expiresAt;
    private boolean active;

    public Sessions(){
    }

    public Sessions(long id, int userId, String sessionToken, String ipAddress, String deviceName,
                    LocalDateTime loginTime, LocalDateTime lastActivity, LocalDateTime expiresAt,
                    boolean active){

        this.id = id;
        this.userId = userId;
        this.sessionToken = sessionToken;
        this.ipAddress = ipAddress;
        this.deviceName = deviceName;
        this.loginTime = loginTime;
        this.lastActivity = lastActivity;
        this.expiresAt = expiresAt;
        this.active = active;
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

    public String getSessionToken(){
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
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

    public LocalDateTime getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(LocalDateTime loginTime) {
        this.loginTime = loginTime;
    }

    public LocalDateTime getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(LocalDateTime lastActivity) {
        this.lastActivity = lastActivity;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
