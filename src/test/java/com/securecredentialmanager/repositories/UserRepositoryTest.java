package com.securecredentialmanager.repositories;

import com.securecredentialmanager.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserRepositoryTest {

    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepository();
        // Clean up test records before each test to keep tests isolated
        userRepository.deleteByUsername("Jackson");
        userRepository.deleteByUsername("jackson");
    }

    @Test
    void shouldSaveAndFindUser() {
        User user = new User();
        user.setUsername("Jackson");
        user.setEmail("jackson@gmail.com");
        user.setPasswordHash("test-password-hash");
        user.setAccountStatus("ACTIVE");
        user.setFailedLoginAttempts(0);

        boolean saved = userRepository.saveUser(user);
        assertTrue(saved);

        User foundUser = userRepository.findByUsername("Jackson");

        assertNotNull(foundUser);
        assertEquals("Jackson", foundUser.getUsername());
        assertEquals("jackson@gmail.com", foundUser.getEmail());
        assertEquals("test-password-hash", foundUser.getPasswordHash());
        assertEquals("ACTIVE", foundUser.getAccountStatus());
        assertEquals(0, foundUser.getFailedLoginAttempts());
    }

    @Test
    void shouldFindUserByEmail() {
        User user = new User();
        user.setUsername("Jackson");
        user.setEmail("jackson@gmail.com");
        user.setPasswordHash("test-password-hash");
        user.setAccountStatus("ACTIVE");
        user.setFailedLoginAttempts(0);

        userRepository.saveUser(user);

        User foundUser = userRepository.findByEmail("jackson@gmail.com");

        assertNotNull(foundUser);
        assertEquals("Jackson", foundUser.getUsername());
        assertEquals("jackson@gmail.com", foundUser.getEmail());
    }

    @Test
    void shouldFailToSaveDuplicateUsername() {
        User originalUser = new User();
        originalUser.setUsername("jackson");
        originalUser.setEmail("jackson1@gmail.com");
        originalUser.setPasswordHash("hash1");
        originalUser.setAccountStatus("ACTIVE");
        originalUser.setFailedLoginAttempts(0);
        userRepository.saveUser(originalUser);

        User duplicateUser = new User();
        duplicateUser.setUsername("jackson");
        duplicateUser.setEmail("jackson2@gmail.com");
        duplicateUser.setPasswordHash("hash2");
        duplicateUser.setAccountStatus("ACTIVE");
        duplicateUser.setFailedLoginAttempts(0);

        boolean saved = userRepository.saveUser(duplicateUser);

        assertFalse(saved, "Saving a duplicate username should fail at the database level");
    }

    @Test
    void shouldUpdateLoginSecurity() {
        User user = new User();
        user.setUsername("Jackson");
        user.setEmail("jackson@gmail.com");
        user.setPasswordHash("test-password-hash");
        user.setAccountStatus("ACTIVE");
        user.setFailedLoginAttempts(0);
        userRepository.saveUser(user);

        boolean updated = userRepository.updateLoginSecurity("Jackson", 3, "LOCKED");
        assertTrue(updated);

        User updatedUser = userRepository.findByUsername("Jackson");
        assertNotNull(updatedUser);
        assertEquals(3, updatedUser.getFailedLoginAttempts());
        assertEquals("LOCKED", updatedUser.getAccountStatus());
    }

    @Test
    void shouldReturnNullWhenUserNotFound() {
        User foundUser = userRepository.findByUsername("non_existent_user");
        assertNull(foundUser);
    }
}