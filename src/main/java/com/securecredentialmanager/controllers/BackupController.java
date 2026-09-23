package com.securecredentialmanager.controllers;

import com.securecredentialmanager.models.BackupHistory;
import com.securecredentialmanager.repositories.BackupHistoryRepository;

public class BackupController {

    private final BackupHistoryRepository backupHistoryRepository;

    public BackupController(){
        this.backupHistoryRepository = new BackupHistoryRepository();
    }

    public boolean saveBackup(int userId, String backupName, String backupPath,
                              int backupSize, String status){

        if (userId <= 0){
            return false;
        }

        if (backupPath == null || backupPath.isBlank()){
            return false;
        }

        BackupHistory backup = new BackupHistory();

        backup.setUserId(userId);
        backup.setBackupName(backupName);
        backup.setBackupPath(backupPath);
        backup.setBackupSize(backupSize);
        backup.setStatus(status);

        return backupHistoryRepository.saveBackup(backup);
    }

    public BackupHistory getBackup(long id){

        if (id <= 0){
            return null;
        }

        return backupHistoryRepository.findById(id);
    }

    public BackupHistory getLatestBackup(int userId){
        if (userId <= 0){
            return null;
        }

        return backupHistoryRepository.findLatestByUserId(userId);
    }

    public boolean deleteBackup(long id){
        if (id <= 0){
            return false;
        }

        return backupHistoryRepository.deleteBackup(id);
    }
}
