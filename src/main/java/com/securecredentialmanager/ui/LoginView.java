package com.securecredentialmanager.ui;

import com.securecredentialmanager.controllers.LoginController;
import com.securecredentialmanager.models.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class LoginView {

    private final ScreenManager screenManager;
    private final LoginController loginController;

    private final VBox root;

    public LoginView(ScreenManager screenManager){
        this.screenManager = screenManager;
        this.loginController = new LoginController();
        root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));

        createView();
    }

    public void createView(){
        Label title = new Label("Secure Credential Manager");

        Label subtitle = new Label("Sign to access your credentials");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setMaxWidth(300);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(300);

        Label message = new Label();

        Button loginButton = new Button("Login");
        loginButton.setMaxWidth(300);

        Button registerButton = new Button("Create Account");
        registerButton.setMaxWidth(300);

        loginButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            boolean success = loginController.login(username, password);

            if (success){
                User user = loginController.getLoggedInUser();
                screenManager.showDashboard(user);
            } else {
                message.setText("Invalid username or password.");
            }
        });

        registerButton.setOnAction(event -> {
            screenManager.showRegister();
        });

        root.getChildren().addAll(
                title, subtitle,
                usernameField, passwordField,
                loginButton, registerButton,
                message
        );
    }

    public Parent getView(){
        return root;
    }
}
