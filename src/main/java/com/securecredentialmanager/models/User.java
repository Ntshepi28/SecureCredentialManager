package com.securecredentialmanager.models;

public class User {

    private int Id;
    private String Username;
    private String Email;
    private String PasswordHash;

    public User(int id, String username, String email, String passwordHash) {
        Id = id;
        Username = username;
        Email = email;
        PasswordHash = passwordHash;
    }

    public User(String username, String email, String passwordHash) {
        Username = username;
        Email = email;
        PasswordHash = passwordHash;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        this.Username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getPasswordHash() {
        return PasswordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.PasswordHash = passwordHash;
    }
}
