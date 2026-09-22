package com.securecredentialmanager.controllers;

import com.securecredentialmanager.services.AuthenticationService;

public class RegistrationController {

    private final AuthenticationService authenticationService;

    public RegistrationController(){
        this.authenticationService = new AuthenticationService();
    }

    public boolean register(String username, String email, String password){
        if (username == null || username.isBlank()){
            return false;
        }

        if (email == null || email.isBlank()){
            return false;
        }

        if (password == null || password.isBlank()){
            return false;
        }

        return authenticationService.register(username, email, password);
    }

    public boolean usernameExists(String username){

        if (username == null || username.isBlank()){
            return false;
        }

        return authenticationService.usernameExists(username);
    }

    public boolean emailExists(String email){

        if (email == null || email.isBlank()){
            return false;
        }

        return authenticationService.emailExists(email);
    }
}
