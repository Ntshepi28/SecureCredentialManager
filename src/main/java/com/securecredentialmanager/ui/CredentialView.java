package com.securecredentialmanager.ui;

import com.securecredentialmanager.controllers.CredentialController;
import com.securecredentialmanager.models.User;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class CredentialView {

    private final ScreenManager screenManager;
    private final User currentUser;
    private final CredentialController credentialController;

    private final BorderPane root;

    private TextField serviceField;
    private TextField websiteField;
    private TextField usernameField;
    private PasswordField passwordField;
    private TextArea noteField;
    private Label message;

    public CredentialView(
            ScreenManager screenManager,
            User user) {

        this.screenManager = screenManager;
        this.currentUser = user;

        this.credentialController = new CredentialController();

        root = new BorderPane();

        createView();
    }

    public void createView() {
        root.setPadding(new Insets(20));

        Label title = new Label("Credential Manager");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        VBox form = new VBox(10);

        serviceField = new TextField();
        serviceField.setPromptText("Service name");

        websiteField = new TextField();
        websiteField.setPromptText("Website");

        usernameField = new TextField();
        usernameField.setPromptText("Login username");

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        noteField = new TextArea();
        noteField.setPromptText("Notes");
        noteField.setPrefRowCount(3);

        Button saveButton = new Button("Save Credential");
        Button backButton = new Button("Back to dashboard");

        message = new Label();

        saveButton.setOnAction(event -> {
            String serviceName = serviceField.getText().trim();
            String website = websiteField.getText().trim();
            String username = usernameField.getText().trim();
            String password = passwordField.getText();
            String notes = noteField.getText().trim();

            // Single definition of userId
            int userId = (currentUser != null && currentUser.getId() > 0) ? currentUser.getId() : 1;

            if (serviceName.isEmpty() || username.isEmpty() || password.isEmpty()) {
                message.setText("Service name, username, and password are required.");
                message.setStyle("-fx-text-fill: red;");
                return;
            }

            boolean success = credentialController.saveCredential(
                    userId,
                    null, // categoryId
                    serviceName,
                    website,
                    username,
                    password,
                    notes
            );

            if (success) {
                message.setText("Credential saved successfully!");
                message.setStyle("-fx-text-fill: green;");
                clearFormFields();
            } else {
                message.setText("Failed to save credential. Please try again.");
                message.setStyle("-fx-text-fill: red;");
            }
        });

        backButton.setOnAction(event -> screenManager.showDashboard(currentUser));

        form.getChildren().addAll(
                serviceField, websiteField,
                usernameField, passwordField,
                noteField, saveButton,
                message, backButton
        );

        root.setTop(title);
        root.setCenter(form);
    }

    private void clearFormFields() {
        if (serviceField != null) serviceField.clear();
        if (websiteField != null) websiteField.clear();
        if (usernameField != null) usernameField.clear();
        if (passwordField != null) passwordField.clear();
        if (noteField != null) noteField.clear();
    }

    public Parent getView() {
        return root;
    }
}