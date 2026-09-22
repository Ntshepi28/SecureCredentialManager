package com.securecredentialmanager.services;

import java.security.SecureRandom;

public class PasswordGeneratorService {

    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBERS = "0123456789";
    private static final String SPECIAL =  "!@#$%^&*()-_=+";

    private final SecureRandom random;

    public PasswordGeneratorService(){
        this.random = new SecureRandom();
    }

    public String generatePassword(int length){

        if (length < 8){
            throw new IllegalArgumentException("Password length must be at least 8");
        }

        String characters = LOWERCASE + UPPERCASE + NUMBERS + SPECIAL;

        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++){
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }

        return password.toString();
    }

}
