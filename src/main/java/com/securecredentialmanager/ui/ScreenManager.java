package com.securecredentialmanager.ui;

import com.securecredentialmanager.models.User;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScreenManager {

    private final Stage stage;
    private User currentUser;

    public ScreenManager(Stage stage) {
        this.stage = stage;
        this.stage.setTitle("Secure Credential Manager");
        this.stage.setMinWidth(900);
        this.stage.setMinHeight(600);
    }

    public void showLogin() {
        LoginView loginView = new LoginView(this);
        Scene scene = new Scene(loginView.getView(), 900, 600);

        applyStylesheet(scene);

        stage.setScene(scene);
        stage.show();
    }

    public void showRegister() {
        RegisterView registerView = new RegisterView(this);
        Scene scene = new Scene(registerView.getView(), 900, 600);

        applyStylesheet(scene);

        stage.setScene(scene);
        stage.show();
    }

    public void showDashboard(User user) {
        this.currentUser = user;

        DashboardView dashboardView = new DashboardView(this, user);
        Scene scene = new Scene(dashboardView.getView(), 1200, 750);

        applyStylesheet(scene);

        stage.setScene(scene);
        stage.show();
    }

    public void showCredential() {
        if (currentUser == null) {
            showLogin();
            return;
        }

        CredentialView credentialView = new CredentialView(this, currentUser);
        Scene scene = new Scene(credentialView.getView(), 1200, 750);

        applyStylesheet(scene);

        stage.setScene(scene);
        stage.show();
    }

    public void showCategory() {
        if (currentUser == null) {
            showLogin();
            return;
        }

        CategoryView categoryView = new CategoryView(this, currentUser);
        Scene scene = new Scene(categoryView.getView(), 1000, 700);

        applyStylesheet(scene);

        stage.setScene(scene);
        stage.show();
    }

    public void showSettings() {
        if (currentUser == null) {
            showLogin();
            return;
        }

        SettingsView settingsView = new SettingsView(this, currentUser);
        Scene scene = new Scene(settingsView.getView(), 1000, 700);

        applyStylesheet(scene);

        stage.setScene(scene);
        stage.show();
    }

    public void showBackup() {
        if (currentUser == null) {
            showLogin();
            return;
        }

        BackupView backupView = new BackupView(this, currentUser);
        Scene scene = new Scene(backupView.getView(), 1000, 700);

        applyStylesheet(scene);

        stage.setScene(scene);
        stage.show();
    }

    public void showSecurity() {
        if (currentUser == null) {
            showLogin();
            return;
        }

        SecurityView securityView = new SecurityView(this, currentUser);
        Scene scene = new Scene(securityView.getView(), 1000, 700);

        applyStylesheet(scene);

        stage.setScene(scene);
        stage.show();
    }

    public void logout() {
        this.currentUser = null;
        showLogin();
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public Stage getStage() {
        return stage;
    }

    private void applyStylesheet(Scene scene) {
        try {
            var cssResource = getClass().getResource("/css/app.css");
            if (cssResource != null) {
                scene.getStylesheets().add(cssResource.toExternalForm());
            } else {
                System.err.println("CSS file not found at /css/app.css. Running with default UI styling.");
            }
        } catch (Exception e) {
            System.err.println("Error loading stylesheet: " + e.getMessage());
        }
    }
}