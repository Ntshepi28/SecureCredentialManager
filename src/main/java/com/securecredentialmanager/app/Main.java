package com.securecredentialmanager.app;

import com.securecredentialmanager.database.DatabaseConnection;

public class Main {
    public static void main(String[] args) {
        DatabaseConnection.getConnection();
    }
}
