package com.securecredentialmanager.controllers;

import com.securecredentialmanager.models.AuditLogs;
import com.securecredentialmanager.models.BackupHistory;
import com.securecredentialmanager.models.User;
import com.securecredentialmanager.services.DashboardService;

import javax.print.DocFlavor;

public class DashboardController {

    private final DashboardService dashboardService;
    private User currentUser;

    public DashboardController(){
        this.dashboardService = new DashboardService();
    }

    public void setCurrentUser(User user){
        this.currentUser = user;
    }

    public User getCurrentUser(){
        return currentUser;
    }

    public String getWelcomeMessage(){

        if (currentUser == null){
            return "Welcome";
        }

        return dashboardService.getWelcomeMessage(currentUser.getUsername());
    }

    public int getCredentialCount(){

        if (currentUser == null){
            return 0;
        }

        return dashboardService.getCredentialCount(currentUser.getId());
    }

    public int getCategoryCount(){

        if (currentUser == null){
           return 0;
        }

        return dashboardService.getCategoryCount(currentUser.getId());
    }

    public AuditLogs getRecentActivity(){

        if (currentUser == null){
            return null;
        }

        return dashboardService.getRecentActivity(currentUser.getId());
    }

    public String getRecentActivityAction(){

        if (currentUser == null){
            return "NONE";
        }

        return dashboardService.getRecentActivityAction(currentUser.getId());
    }

    public String getSecurityStatus(){
        return dashboardService.getSecurityStatus(currentUser);
    }

    public int getFailedLoginAttempts(){
        return dashboardService.getFailedLoginAttempts(currentUser);
    }

    public String getAccountStatus(){
        return dashboardService.getAccountStatus(currentUser);
    }

    public BackupHistory getLatestBackup(){

        if (currentUser == null){
            return null;
        }

        return dashboardService.getLatestBackup(currentUser.getId());
    }

    public String getBackupStatus(){
        if (currentUser == null){
            return "NO BACKUP";
        }

        return dashboardService.getBackupStatus(currentUser.getId());
    }

    public String getBackupName(){
        if (currentUser == null){
            return "No backup available";
        }

        return dashboardService.getBackupInformation(currentUser.getId());
    }

    public int getBackupSize(){
        if (currentUser == null){
            return 0;
        }

        return dashboardService.getBackupSize(currentUser.getId());
    }

    public String getDashboardSummary(){
        return dashboardService.getDashboardSummary(currentUser);
    }
}
