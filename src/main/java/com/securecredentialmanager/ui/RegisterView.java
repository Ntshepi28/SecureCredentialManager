package com.securecredentialmanager.ui;

import com.securecredentialmanager.controllers.RegistrationController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class RegisterView {

    private final ScreenManager screenManager;
    private final RegistrationController registrationController;

    private final VBox root;

    public RegisterView(ScreenManager screenManager) {

        this.screenManager = screenManager;
        this.registrationController = new RegistrationController();

        root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));

        createView();
    }

    public void createView(){
        Label title = new Label("Create Your Account");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setMaxWidth(300);

        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setMaxWidth(300);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(300);

        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm Password");
        confirmPasswordField.setMaxWidth(300);

        Label message = new Label();

        Button registerButton = new Button("Create Account");
        registerButton.setMaxWidth(300);

        Button backButton = new Button("Back to Login");
        backButton.setMaxWidth(300);

        registerButton.setOnAction(event -> {
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            if (!password.equals(confirmPassword)){
                message.setText("Passwords do not match.");
                return;
            }

            if (registrationController.usernameExists(username)){
                message.setText("Username already exists.");
                return;
            }

            if (registrationController.emailExists(email)){
                message.setText("Email already exists.");
                return;
            }

            boolean success = registrationController.register(username, email, password);

            if (success){
                message.setText("Account created successfully.");

                usernameField.clear();
                emailField.clear();
                passwordField.clear();
                confirmPasswordField.clear();
            } else {
                message.setText("Unable to create account.");
            }
        });

        backButton.setOnAction(event -> screenManager.showLogin());

        root.getChildren().addAll(title, usernameField, emailField,
                passwordField, confirmPasswordField, registerButton,
                backButton, message);
    }

    public Parent getView(){
        return root;
    }
}
