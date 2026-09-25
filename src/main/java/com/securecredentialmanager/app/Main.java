package com.securecredentialmanager.app;

import com.securecredentialmanager.database.DatabaseConnection;
import com.securecredentialmanager.ui.ScreenManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Initialize the ScreenManager with the JavaFX primary stage
        ScreenManager screenManager = new ScreenManager(primaryStage);

        // Launch directly into the Login View
        screenManager.showLogin();
    }

    public static void main(String[] args) {
        // Test/Verify DB connection on startup
        DatabaseConnection.getConnection();

        // Launch JavaFX application
        launch(args);
    }
}