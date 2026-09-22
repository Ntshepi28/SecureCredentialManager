package com.securecredentialmanager.services;

import com.securecredentialmanager.models.AuditLogs;
import com.securecredentialmanager.models.BackupHistory;
import com.securecredentialmanager.models.User;
import com.securecredentialmanager.models.UserSettings;
import com.securecredentialmanager.repositories.*;
import com.sun.jdi.event.StepEvent;

public class DashboardService {

    private final UserRepository userRepository;
    private final CredentialRepository credentialRepository;
    private final CategoryRepository categoryRepository;
    private final AuditLogsRepository auditLogsRepository;
    private final BackupHistoryRepository backupHistoryRepository;
    private final UserSettingsRepository userSettingsRepository;
    private final PasswordStrengthService passwordStrengthService;

    public DashboardService(){
        this.userRepository = new UserRepository();
        this.credentialRepository = new CredentialRepository();
        this.categoryRepository = new CategoryRepository();
        this.auditLogsRepository = new AuditLogsRepository();
        this.backupHistoryRepository = new BackupHistoryRepository();
        this.userSettingsRepository = new UserSettingsRepository();
        this.passwordStrengthService = new PasswordStrengthService();
    }

    public User getUser(String username){
        if (username == null || username.isBlank()){
            return null;
        }

        return userRepository.findByUsername(username);
    }

    public String getWelcomeMessage(String username){
        User user = getUser(username);

        if (user == null){
            return null;
        }

        return "Welcome, " + user.getUsername();
    }

    public boolean isAccountActive(String username){
        User user = getUser(username);

        return user != null
                && "ACTIVE".equals(user.getAccountStatus());
    }

    public int getCredentialCount(int userId){
        if (userId <= 0){
            return 0;
        }

        return credentialRepository.countByUserId(userId);
    }

    public int getCategoryCount(int userId) {

        if (userId <= 0) {
            return 0;
        }

        return categoryRepository.countByUserId(userId);
    }
}
