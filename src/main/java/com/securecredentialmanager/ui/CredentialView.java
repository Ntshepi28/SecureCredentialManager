package com.securecredentialmanager.ui;

import com.securecredentialmanager.controllers.CredentialController;
import com.securecredentialmanager.models.Credential;
import com.securecredentialmanager.models.User;
import com.securecredentialmanager.services.EncryptionService;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class CredentialView {

    private final ScreenManager screenManager;
    private final User currentUser;
    private final CredentialController credentialController;

    private final BorderPane root;

    public CredentialView(
            ScreenManager screenManager,
            User user) {

        this.screenManager = screenManager;
        this.currentUser = user;

        this.credentialController =
                new CredentialController();

        root = new BorderPane();

        createView();
    }

    public void createView(){
        root.setPadding(new Insets(20));

        Label title = new Label("Credential Manager");

        VBox form = new VBox(10);

        TextField serviceField = new TextField();
        serviceField.setPromptText("Service name");

        TextField websiteField = new TextField();
        websiteField.setPromptText("Website");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Login username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        TextArea noteField = new TextArea();
        noteField.setPromptText("Notes");

        Button saveButton = new Button("Save Credential");
        Button backButton = new Button("Back to dashboard");

        Label message = new Label();

        saveButton.setOnAction(event -> {message.setText("Encryption configuration is required before saving credentials.");
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

    public Parent getView(){
        return root;
    }
}
