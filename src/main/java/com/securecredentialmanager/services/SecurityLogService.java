package com.securecredentialmanager.services;

import com.securecredentialmanager.models.AuditLogs;
import com.securecredentialmanager.repositories.AuditLogsRepository;

public class SecurityLogService {

    private final AuditLogsRepository auditLogsRepository;

    public SecurityLogService(){
        this.auditLogsRepository = new AuditLogsRepository();
    }

    public boolean logAction(int userId, String action, String description, String ipAddress,
                             String deviceName){

        if (userId <= 0 || action == null || action.isBlank()){
            return false;
        }

        AuditLogs auditLogs = new AuditLogs();

        auditLogs.setUserId(userId);
        auditLogs.setAction(action);
        auditLogs.setDescription(description);
        auditLogs.setIpAddress(ipAddress);
        auditLogs.setDeviceName(deviceName);

        return auditLogsRepository.saveAuditLogs(auditLogs);
    }

    public AuditLogs getLatestLog(int userId){

        if (userId <= 0){
            return  null;
        }

        return auditLogsRepository.findLatestByUserId(userId);
    }

    public AuditLogs getLog(long id){
        if (id <= 0){
            return null;
        }

        return auditLogsRepository.findById(id);
    }
}
