package com.securecredentialmanager.services;

public class PasswordStrengthService {

    public String checkStrength(String password){
        if (password == null || password.isEmpty()){
            return "EMPTY";
        }

        int score = calculateScore(password);

        if (score <= 2){
            return "WEAK";
        }

        if (score <= 6){
            return "MEDIUM";
        }

        return "STRONG";
    }

    public int calculateScore(String password){

        if (password == null || password.isEmpty()){
            return 0;
        }

        int score = 0;

        if (password.length() >= 8){
            score++;
        }

        if (password.length() >= 12){
            score++;
        }

        if (containsLowercase(password)){
            score++;
        }

        if (containsUppercase(password)){
            score++;
        }

        if (containsNumber(password)) {
            score++;
        }

        if (containsSpecialCharacter(password)) {
            score++;
        }

        return score;
    }

    private boolean containsLowercase(String password){
        return password.matches(".*[a-z].*");
    }

    private boolean containsUppercase(String password){
        return password.matches("\".*[A-Z].*\"");
    }

    private boolean containsNumber(String password){
        return password.matches(".*[0-9].*");
    }

    private boolean containsSpecialCharacter(String password){
        return password.matches(".*[^a-zA-Z0-9].*");
    }
}
