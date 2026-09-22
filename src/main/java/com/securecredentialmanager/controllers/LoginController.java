package com.securecredentialmanager.controllers;

import com.securecredentialmanager.models.User;
import com.securecredentialmanager.services.AuthenticationService;

public class LoginController {

    private final AuthenticationService authenticationService;
    private User loggedInUser;

    public LoginController(){
        this.authenticationService = new AuthenticationService();
    }

    public boolean login(String username, String password){

        if (username == null || username.isBlank()){
            return false;
        }

        if (password == null || password.isBlank()){
            return false;
        }

        User user = authenticationService.login(username, password);

        if (user == null){
            return false;
        }

        loggedInUser = user;

        return true;
    }

    public User getLoggedInUser(){
        return loggedInUser;
    }

    public boolean isLoggedIn(){
        return loggedInUser != null;
    }

    public void logout(){
        loggedInUser = null;
    }
}
