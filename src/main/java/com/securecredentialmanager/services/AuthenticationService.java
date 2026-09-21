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
}
