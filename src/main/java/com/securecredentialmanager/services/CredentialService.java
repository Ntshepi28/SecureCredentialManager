package com.securecredentialmanager.services;

import com.securecredentialmanager.models.Credential;
import com.securecredentialmanager.repositories.CredentialRepository;

public class CredentialService {

    private final CredentialRepository credentialRepository;
    private final EncryptionService encryptionService;

    public CredentialService() {
        this.credentialRepository = new CredentialRepository();
        this.encryptionService = new EncryptionService();
    }

    /**
     * Checks if encryption service is properly configured and usable.
     */
    public boolean isEncryptionConfigured() {
        return encryptionService != null;
    }

    /**
     * Saves a credential, encrypting the raw password using EncryptionService.
     */
    public boolean saveCredential(Credential credential, String rawPassword) {
        if (credential == null) {
            System.err.println("Save failed: Credential object is null.");
            return false;
        }

        if (credential.getUserId() <= 0) {
            System.err.println("Save failed: Invalid user ID.");
            return false;
        }

        if (credential.getServiceName() == null || credential.getServiceName().isBlank()) {
            System.err.println("Save failed: Service name is required.");
            return false;
        }

        if (credential.getLoginUsername() == null || credential.getLoginUsername().isBlank()) {
            System.err.println("Save failed: Login username is required.");
            return false;
        }

        if (rawPassword == null || rawPassword.isBlank()) {
            System.err.println("Save failed: Password cannot be empty.");
            return false;
        }

        try {
            // Encrypt using EncryptionService
            String encryptedPassword = encryptionService.encrypt(rawPassword);
            credential.setEncryptedPassword(encryptedPassword);

            // Persist using repository
            return credentialRepository.saveCredential(credential);

        } catch (Exception e) {
            System.err.println("Error encrypting or saving credential: " + e.getMessage());
            return false;
        }
    }

    /**
     * Overloaded save method for credentials that already have an encrypted password attached.
     */
    public boolean saveCredential(Credential credential) {
        if (credential == null || credential.getUserId() <= 0) {
            return false;
        }

        if (credential.getServiceName() == null || credential.getServiceName().isBlank()) {
            return false;
        }

        if (credential.getLoginUsername() == null || credential.getLoginUsername().isBlank()) {
            return false;
        }

        if (credential.getEncryptedPassword() == null || credential.getEncryptedPassword().isBlank()) {
            System.err.println("Save failed: Encrypted password is missing.");
            return false;
        }

        return credentialRepository.saveCredential(credential);
    }

    public Credential findCredential(long id) {
        if (id <= 0) {
            return null;
        }

        return credentialRepository.findById((int) id);
    }

    public Credential findByServiceName(int userId, String serviceName) {
        if (userId <= 0 || serviceName == null || serviceName.isBlank()) {
            return null;
        }

        return credentialRepository.findByServiceName(userId, serviceName);
    }

    public boolean deleteCredential(long id) {
        if (id <= 0) {
            return false;
        }

        return credentialRepository.deleteCredentials((int) id);
    }
}