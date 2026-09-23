package com.securecredentialmanager.ui;

import com.securecredentialmanager.controllers.SettingsController;
import com.securecredentialmanager.models.User;
import com.securecredentialmanager.models.UserSettings;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class SettingsView {

    private final ScreenManager screenManager;
    private final User currentUser;
    private final SettingsController settingsController;

    private final BorderPane root;

    public SettingsView(ScreenManager screenManager, User user) {
        this.screenManager = screenManager;
        this.currentUser = user;
        this.settingsController = new SettingsController();
        root = new BorderPane();
        createView();
    }

    public void createView(){
        root.setPadding(new Insets(20));

        VBox content = new VBox(15);

        Label title = new Label("Settings");

        ComboBox<String> theme = new ComboBox<>();

        theme.getItems().addAll("LIGHT", "DARK");
        theme.setValue("LIGHT");

        Spinner<Integer> autoLock = new Spinner<>(1, 60, 5);
        Spinner<Integer> generatorLength = new Spinner<>(8, 64, 16);

        CheckBox requireMasterPassword = new CheckBox("Require master password");
        requireMasterPassword.setSelected(true);

        Button loadButton = new Button("Load Settings");
        Button saveButton = new Button("save Settings");
        Button backButton = new Button("Back to Dashboard");

        Label message = new Label();

        loadButton.setOnAction(event -> {
            UserSettings settings = settingsController.getSettings(currentUser.getId());

            if (settings == null){
                message.setText("No settings found.");
                return;
            }

            theme.setValue(settings.getTheme());

            autoLock.getValueFactory().setValue(settings.getAutoLockMinutes());

            generatorLength.getValueFactory().setValue(settings.getPasswordGeneratorLength());

            requireMasterPassword.setSelected(settings.isRequireMasterPassword());

            message.setText("Settings loaded.");
        });

        saveButton.setOnAction(event -> {
            UserSettings settings = settingsController.getSettings(currentUser.getId());

            if (settings == null){
                settings = new UserSettings();
                settings.setUserId(currentUser.getId());

                settings.setTheme(theme.getValue());
                settings.setAutoLockMinutes(autoLock.getValue());
                settings.setPasswordGeneratorLength(generatorLength.getValue());
                settings.setRequireMasterPassword(requireMasterPassword.isSelected());

                boolean success = settingsController.saveSettings(settings);
                message.setText(success ? "Settings saved." : "Unable to save settings.");
            } else {
                settings = new UserSettings();
                settings.setUserId(currentUser.getId());

                settings.setTheme(theme.getValue());
                settings.setAutoLockMinutes(autoLock.getValue());
                settings.setPasswordGeneratorLength(generatorLength.getValue());
                settings.setRequireMasterPassword(requireMasterPassword.isSelected());

                boolean success = settingsController.saveSettings(settings);
                message.setText(success ? "Settings saved." : "Unable to save settings.");

            }
        });

        backButton.setOnAction(event -> screenManager.showDashboard(currentUser));

        content.getChildren().addAll(
                title, new Label("Theme"),
                theme, new Label("Auto-lock (minutes"),
                autoLock, new Label("Password generator length"),
                generatorLength, requireMasterPassword,
                loadButton, saveButton,
                message, backButton
        );

        root.setCenter(content);
    }

    public Parent getView(){
        return root;
    }
}
