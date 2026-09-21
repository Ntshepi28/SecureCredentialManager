package com.securecredentialmanager.repositories;

import com.securecredentialmanager.models.Credential;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CredentialRepositoryTest {

    private CredentialRepository credentialRepository;

    @BeforeEach
    void setUp() {
        credentialRepository = new CredentialRepository();

        credentialRepository.deleteCredentials(1001); // this clean up test credential before each test
    }

    @Test
    void shouldSaveAndFindCredential(){

        Credential credential = new Credential();

        credential.setUserId(10);
        credential.setCategoryId(1001);
        credential.setServiceName("Test Service");
        credential.setWebsite("https://example.com");
        credential.setLoginUsername("AnikaJs");
        credential.setEncryptedPassword("encrypted-test-password");
        credential.setNotes("Test credential");

        boolean saved = credentialRepository.saveCredential(credential);

        assertTrue(saved, "Failed to save credential - make sure category 1001 exists");

        Credential foundCredential =
                credentialRepository.findByServiceName(10, "Test Service");

        assertNotNull(foundCredential);
        assertEquals(10, foundCredential.getUserId());
        assertEquals(1001, foundCredential.getCategoryId());
        assertEquals("Test Service", foundCredential.getServiceName());
        assertEquals("https://example.com", foundCredential.getWebsite());
        assertEquals("AnikaJs", foundCredential.getLoginUsername());
        assertEquals("encrypted-test-password",
                foundCredential.getEncryptedPassword());
        assertEquals("Test credential", foundCredential.getNotes());
        assertNotNull(foundCredential.getCreatedAt());
        assertNotNull(foundCredential.getUpdatedAt());
    }
}
