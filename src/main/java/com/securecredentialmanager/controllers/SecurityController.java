package com.securecredentialmanager.controllers;

import com.securecredentialmanager.models.AuditLogs;
import com.securecredentialmanager.services.PasswordStrengthService;
import com.securecredentialmanager.services.SecurityLogService;


public class SecurityController {

    private final SecurityLogService securityLogService;
    private final PasswordStrengthService passwordStrengthService;

    public SecurityController() {
        this.securityLogService = new SecurityLogService();
        this.passwordStrengthService = new PasswordStrengthService();
    }

    public boolean logAction(int userId, String action, String description, String ipAddress,
                             String deviceName){

        return securityLogService.logAction(userId, action, description, ipAddress, deviceName);
    }

    public AuditLogs getLatestActivity(int userId){
        if (userId <= 0){
            return null;
        }

        return securityLogService.getLatestLog(userId);
    }

    public AuditLogs getSecurityLog(long id){
        if (id <= 0){
            return null;
        }

        return securityLogService.getLog(id);
    }

    public String checkPasswordStrength(String password){
        return passwordStrengthService.checkStrength(password);
    }

    public int getPasswordStrengthScore(String password){
        return passwordStrengthService.calculateScore(password);
    }
}
