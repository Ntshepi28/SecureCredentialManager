package com.securecredentialmanager.services;

import com.securecredentialmanager.models.AuditLogs;
import com.securecredentialmanager.models.BackupHistory;
import com.securecredentialmanager.models.User;
import com.securecredentialmanager.models.UserSettings;
import com.securecredentialmanager.repositories.AuditLogsRepository;
import com.securecredentialmanager.repositories.BackupHistoryRepository;
import com.securecredentialmanager.repositories.CredentialRepository;
import com.securecredentialmanager.repositories.UserRepository;
import com.securecredentialmanager.repositories.UserSettingsRepository;

public class DashboardService {

    private final UserRepository userRepository;
    private final CredentialRepository credentialRepository;
    private final AuditLogsRepository auditLogsRepository;
    private final BackupHistoryRepository backupHistoryRepository;
    private final UserSettingsRepository userSettingsRepository;
    private final PasswordStrengthService passwordStrengthService;

    public DashboardService(){
        this.userRepository = new UserRepository();
        this.credentialRepository = new CredentialRepository();
        this.auditLogsRepository = new AuditLogsRepository();
        this.backupHistoryRepository = new BackupHistoryRepository();
        this.userSettingsRepository = new UserSettingsRepository();
        this.passwordStrengthService = new PasswordStrengthService();
    }
}
