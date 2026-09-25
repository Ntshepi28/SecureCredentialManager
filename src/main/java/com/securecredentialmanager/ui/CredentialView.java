package com.securecredentialmanager.ui;

import com.securecredentialmanager.controllers.CredentialController;
import com.securecredentialmanager.models.Credential;
import com.securecredentialmanager.models.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.*;

import java.util.List;

public class CredentialView {

    private final ScreenManager screenManager;
    private final User currentUser;
    private final CredentialController credentialController;

    private final BorderPane root;

    // Form inputs
    private TextField serviceField;
    private TextField websiteField;
    private TextField usernameField;
    private PasswordField passwordField;
    private TextArea noteField;
    private Label message;

    // Table controls
    private TableView<Credential> table;
    private ObservableList<Credential> credentialList;

    public CredentialView(ScreenManager screenManager, User user) {
        this.screenManager = screenManager;
        this.currentUser = user;
        this.credentialController = new CredentialController();

        root = new BorderPane();
        createView();
    }

    public void createView() {
        root.setPadding(new Insets(20));

        Label title = new Label("Credential Manager");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // -------------------------------------------------------------
        // LEFT PANE: Form for Adding Credentials
        // -------------------------------------------------------------
        VBox form = new VBox(10);
        form.setPrefWidth(280);
        form.setPadding(new Insets(10));

        Label formTitle = new Label("Add New Credential");
        formTitle.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        serviceField = new TextField();
        serviceField.setPromptText("Service name (e.g. GitHub)");

        websiteField = new TextField();
        websiteField.setPromptText("Website URL");

        usernameField = new TextField();
        usernameField.setPromptText("Login Username/Email");

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        noteField = new TextArea();
        noteField.setPromptText("Notes");
        noteField.setPrefRowCount(3);

        Button saveButton = new Button("Save Credential");
        Button backButton = new Button("Back to Dashboard");

        message = new Label();

        saveButton.setOnAction(event -> handleSave());
        backButton.setOnAction(event -> screenManager.showDashboard(currentUser));

        form.getChildren().addAll(
                formTitle,
                serviceField, websiteField,
                usernameField, passwordField,
                noteField, saveButton,
                message, backButton
        );

        // -------------------------------------------------------------
        // RIGHT PANE: TableView Displaying Saved Credentials
        // -------------------------------------------------------------
        table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        TableColumn<Credential, String> serviceCol = new TableColumn<>("Service");
        serviceCol.setCellValueFactory(new PropertyValueFactory<>("serviceName"));

        TableColumn<Credential, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("loginUsername"));

        TableColumn<Credential, String> websiteCol = new TableColumn<>("Website");
        websiteCol.setCellValueFactory(new PropertyValueFactory<>("website"));

        // Masked Password Column
        TableColumn<Credential, String> passCol = new TableColumn<>("Password");
        passCol.setCellValueFactory(cell -> new SimpleStringProperty("••••••••"));

        // Actions Column (Copy & Delete)
        TableColumn<Credential, Void> actionCol = new TableColumn<>("Actions");
        actionCol.setCellFactory(param -> new TableCell<>() {
            private final Button copyBtn = new Button("Copy");
            private final Button deleteBtn = new Button("Delete");
            private final HBox container = new HBox(5, copyBtn, deleteBtn);

            {
                copyBtn.setStyle("-fx-font-size: 11px;");
                deleteBtn.setStyle("-fx-font-size: 11px; -fx-text-fill: red;");

                // Copy Decrypted Password
                copyBtn.setOnAction(e -> {
                    Credential credential = getTableView().getItems().get(getIndex());
                    String decrypted = credentialController.decryptPassword(credential.getEncryptedPassword());
                    if (decrypted != null) {
                        Clipboard clipboard = Clipboard.getSystemClipboard();
                        ClipboardContent content = new ClipboardContent();
                        content.putString(decrypted);
                        clipboard.setContent(content);
                        message.setText("Password copied to clipboard!");
                        message.setStyle("-fx-text-fill: green;");
                    } else {
                        message.setText("Failed to decrypt password.");
                        message.setStyle("-fx-text-fill: red;");
                    }
                });

                // Delete Credential
                deleteBtn.setOnAction(e -> {
                    Credential credential = getTableView().getItems().get(getIndex());
                    boolean deleted = credentialController.deleteCredential(credential.getId());
                    if (deleted) {
                        message.setText("Credential deleted.");
                        message.setStyle("-fx-text-fill: green;");
                        refreshTable();
                    } else {
                        message.setText("Failed to delete credential.");
                        message.setStyle("-fx-text-fill: red;");
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : container);
            }
        });

        table.getColumns().addAll(serviceCol, usernameCol, websiteCol, passCol, actionCol);

        // Layout arrangement
        SplitPane splitPane = new SplitPane();
        splitPane.getItems().addAll(form, table);
        splitPane.setDividerPositions(0.35);

        root.setTop(title);
        BorderPane.setMargin(title, new Insets(0, 0, 10, 0));
        root.setCenter(splitPane);

        // Load initial data into table
        refreshTable();
    }

    private void handleSave() {
        String serviceName = serviceField.getText().trim();
        String website = websiteField.getText().trim();
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        String notes = noteField.getText().trim();

        int userId = (currentUser != null && currentUser.getId() > 0) ? currentUser.getId() : 1;

        if (serviceName.isEmpty() || username.isEmpty() || password.isEmpty()) {
            message.setText("Service, username, and password are required.");
            message.setStyle("-fx-text-fill: red;");
            return;
        }

        boolean success = credentialController.saveCredential(
                userId, null, serviceName, website, username, password, notes
        );

        if (success) {
            message.setText("Saved successfully!");
            message.setStyle("-fx-text-fill: green;");
            clearFormFields();
            refreshTable();
        } else {
            message.setText("Failed to save. Check database connection.");
            message.setStyle("-fx-text-fill: red;");
        }
    }

    private void refreshTable() {
        int userId = (currentUser != null && currentUser.getId() > 0) ? currentUser.getId() : 1;
        List<Credential> list = credentialController.getCredentialsForUser(userId);
        credentialList = FXCollections.observableArrayList(list);
        table.setItems(credentialList);
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