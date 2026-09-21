package com.securecredentialmanager.services;

import com.securecredentialmanager.models.Credential;
import com.securecredentialmanager.repositories.CredentialRepository;

public class CredentialService {

    private final CredentialRepository credentialRepository;

    public CredentialService(){
        this.credentialRepository = new CredentialRepository();
    }

    public boolean saveCredential(Credential credential){
        if (credential == null){
            return false;
        }

        if (credential.getUserId() <= 0){
            return false;
        }

        if (credential.getServiceName() == null
               || credential.getServiceName().isBlank()){
            return false;
        }

        if (credential.getLoginUsername() == null
              || credential.getLoginUsername().isBlank()){
            return false;
        }

        if (credential.getEncryptedPassword() == null
               || credential.getEncryptedPassword().isBlank()){
            return false;
        }

        return credentialRepository.saveCredential(credential);
    }

    public Credential findCredential(long id){
        if (id <= 0){
            return null;
        }

        return credentialRepository.findById((int) id);
    }

    public Credential findByServiceName(int userId, String serviceName){
        if (userId <= 0 || serviceName == null || serviceName.isBlank()){
            return null;
        }

        return credentialRepository.findByServiceName(userId, serviceName);
    }

    public boolean deleteCredential(long id){
        if (id <= 0){
            return false;
        }

        return credentialRepository.deleteCredentials((int) id);
    }
}
