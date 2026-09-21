package com.securecredentialmanager.services;

import com.securecredentialmanager.models.User;
import com.securecredentialmanager.repositories.UserRepository;
import org.mindrot.jbcrypt.BCrypt;

public class AuthenticationService {
    private static final int MAX_LOGIN_ATTEMPTS = 5;

    private final UserRepository userRepository;

    public AuthenticationService(){
        this.userRepository = new UserRepository();
    }

    public boolean register(String username, String email, String password){
        if (username == null || username.isBlank()
             || email == null || email.isBlank()
             || password == null || password.isBlank()){
            return false;
        }

        if (userRepository.findByUsername(username) != null){
            return false;
        }

        if (userRepository.findByEmail(email) != null){
            return false;
        }

        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPasswordHash(passwordHash);
        user.setAccountStatus("ACTIVE");
        user.setFailedLoginAttempts(0);

        return userRepository.saveUser(user);
    }

    public User login(String username, String password){
        User user = userRepository.findByUsername(username);
        if (user == null){
            return null;
        }

        if (!"ACTIVE".equals(user.getAccountStatus())){
            return null;
        }

        if (!BCrypt.checkpw(password, user.getPasswordHash())){
            int failedAttempts = user.getFailedLoginAttempts() + 1;

            if (failedAttempts >= MAX_LOGIN_ATTEMPTS){
                userRepository.updateLoginSecurity(username, failedAttempts, "LOCKED");
            } else {
                userRepository.updateLoginSecurity(username, failedAttempts, "ACTIVE");
            }

            return null;
        }

        userRepository.updateLoginSecurity(username, 0, "ACTIVE");

        return user;

    }
}
