package com.securecredentialmanager.ui;

import com.securecredentialmanager.controllers.SecurityController;
import com.securecredentialmanager.models.AuditLogs;
import com.securecredentialmanager.models.User;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class SecurityView {

    private final ScreenManager screenManager;
    private final User currentUser;
    private final SecurityController securityController;

    private final BorderPane root;

    public SecurityView(ScreenManager screenManager, User user) {
        this.screenManager = screenManager;
        this.currentUser = user;
        this.securityController = new SecurityController();
        root = new BorderPane();
        createView();
    }

    public void createView(){
        root.setPadding(new Insets(20));

        VBox content = new VBox(15);

        Label title = new Label("Security Center");
        Label activityTitle = new Label("Recent Security Activity");
        Label activity = new Label();

        updateActivity(activity);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter password to check strength");

        Button checkButton = new Button("Check password strength");

        Label strength = new Label();
        Label score = new Label();

        checkButton.setOnAction(event -> {
            String password = passwordField.getText();

            String result = securityController.checkPasswordStrength(password);
            int passwordScore = securityController.getPasswordStrengthScore(password);

            strength.setText("Strength: " + result);
            score.setText("Score: " + passwordScore + " / 6");
        });

        Button backButton = new Button("Back to Dashboard");
        backButton.setOnAction(event -> screenManager.showDashboard(currentUser));

        content.getChildren().addAll(
                title,
                activityTitle,
                activity,
                new Separator(),
                new Label("Password Strength Checker"),
                passwordField,
                checkButton,
                strength,
                score,
                backButton
        );

        root.setCenter(content);
    }

    private void updateActivity(Label label){
        AuditLogs logs = securityController.getLatestActivity(currentUser.getId());

        if (logs == null){
            label.setText("No recent security activity");
            return;
        }

        String description = logs.getDescription();

        if (description == null || description.isBlank()){
            description = logs.getAction();
        }

        label.setText(description);
    }

    public Parent getView(){
        return root;
    }
}
