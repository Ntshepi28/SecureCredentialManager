package com.securecredentialmanager.ui;

import com.securecredentialmanager.controllers.BackupController;
import com.securecredentialmanager.models.BackupHistory;
import com.securecredentialmanager.models.User;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.File;

public class BackupView {

    private final ScreenManager screenManager;
    private final User currentUser;
    private final BackupController backupController;

    private final BorderPane root;

    public BackupView(ScreenManager screenManager, User user) {
        this.screenManager = screenManager;
        this.currentUser = user;
        this.backupController = new BackupController();
        root = new BorderPane();
        createView();
    }

    public void createView(){
        root.setPadding(new Insets(20));

        VBox content = new VBox(15);

        Label title = new Label("Backup Management");
        Label latestBackup = new Label();

        updateLatestBackup(latestBackup);


        TextField backupName = new TextField();
        backupName.setPromptText("Backup name");

        TextField backupPath = new TextField();
        backupPath.setPromptText("Backup path");

        ComboBox<String> status = new ComboBox<>();
        status.getItems().addAll("SUCCESS", "FAILED");
        status.setValue("SUCCESS");

        Button saveButton = new Button("Record Backup");
        Button refreshButton = new Button("Refresh");
        Button backButton = new Button("Back to Dashboard");

        Label message = new Label();

        saveButton.setOnAction(event -> {

            File backupFile = new File(backupPath.getText());

            if (!backupFile.exists()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Backup Error");
                alert.setHeaderText(null);
                alert.setContentText("Backup file does not exist.");
                alert.showAndWait();
                return;
            }
            int backupSize = (int) backupFile.length();

            boolean success =
                    backupController.saveBackup(
                            currentUser.getId(),
                            backupName.getText(),
                            backupPath.getText(),
                            backupSize,
                            status.getValue()
                    );

            if (success) {
                message.setText("Backup record saved.");
                updateLatestBackup(latestBackup);
                backupName.clear();
                backupPath.clear();

            } else {
                message.setText("Unable to save backup record.");
            }
        });

        refreshButton.setOnAction(event -> updateLatestBackup(latestBackup));
        backButton.setOnAction(event -> screenManager.showDashboard(currentUser));

        content.getChildren().addAll(
                title, latestBackup,
                backupName, backupPath,
                status, saveButton,
                refreshButton, message,
                backButton
        );

        root.setCenter(content);
    }

    private void updateLatestBackup(Label label) {
        BackupHistory backup = backupController.getLatestBackup(
                        currentUser.getId());

        if (backup == null) {
            label.setText("Latest backup: None");
            return;
        }
        label.setText("Latest backup: " + backup.getBackupName()
                        + " - " + backup.getStatus()
        );
    }

    public Parent getView(){
        return root;
    }

}
