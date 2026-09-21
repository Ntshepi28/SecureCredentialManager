package com.securecredentialmanager.models;

import java.time.LocalDateTime;

public class BackupHistory {

    private long id;
    private int userId;
    private String backupName;
    private String backupPath;
    private int backupSize;
    private String status;
    private LocalDateTime createdAt;

    public BackupHistory(){
    }

    public BackupHistory(long id, int userId, String backupName, String backupPath, int backupSize,
                         String status, LocalDateTime createdAt){

        this.id = id;
        this.userId = userId;
        this.backupName = backupName;
        this.backupPath = backupPath;
        this.backupSize = backupSize;
        this.status = status;
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

    public String getBackupName() {
        return backupName;
    }

    public void setBackupName(String backupName) {
        this.backupName = backupName;
    }

    public String getBackupPath() {
        return backupPath;
    }

    public void setBackupPath(String backupPath) {
        this.backupPath = backupPath;
    }

    public int getBackupSize() {
        return backupSize;
    }

    public void setBackupSize(int backupSize) {
        this.backupSize = backupSize;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
