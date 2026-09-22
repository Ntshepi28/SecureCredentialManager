package com.securecredentialmanager.controllers;

import com.securecredentialmanager.models.Credential;
import com.securecredentialmanager.services.CredentialService;
import com.securecredentialmanager.services.EncryptionService;

public class CredentialController {

    private final CredentialService credentialService;
    private EncryptionService encryptionService;

    public CredentialController(){
        this.credentialService = new CredentialService();
    }

    public void setEncryptionService(EncryptionService encryptionService){
        this.encryptionService = encryptionService;
    }

    public boolean saveCredential(Credential credential) {

        if (credential == null) {
            return false;
        }

        return credentialService.saveCredential(credential);
    }

    public boolean saveCredential(
            int userId,
            Integer categoryId,
            String serviceName,
            String website,
            String loginUsername,
            String password,
            String notes){

        if (password == null || password.isBlank()){
            return false;
        }

        if (encryptionService == null){
            throw new IllegalStateException("EncryptionService has not been configured");
        }


        String encryptedPassword =
                encryptionService.encrypt(password);

        Credential credential = new Credential();

        credential.setUserId(userId);
        credential.setCategoryId(categoryId);
        credential.setServiceName(serviceName);
        credential.setWebsite(website);
        credential.setLoginUsername(loginUsername);
        credential.setEncryptedPassword(encryptedPassword);
        credential.setNotes(notes);

        return credentialService.saveCredential(credential);
    }

    public Credential findCredential(long id) {

        return credentialService.findCredential(id);
    }

    public Credential findByServiceName(
            int userId,
            String serviceName) {

        return credentialService.findByServiceName(
                userId,
                serviceName
        );
    }

    public String decryptPassword(String encryptedPassword){
        if (encryptedPassword == null){
            return null;
        }

        if (encryptionService == null){
            throw new IllegalStateException("EncryptionService has not been configured.");
        }

        return encryptionService.decrypt(encryptedPassword);
    }

    public boolean deleteCredential(long id) {

        return credentialService.deleteCredential(id);
    }
}
