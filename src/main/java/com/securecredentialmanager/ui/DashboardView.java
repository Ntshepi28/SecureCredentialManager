package com.securecredentialmanager.ui;

import com.securecredentialmanager.controllers.DashboardController;
import com.securecredentialmanager.models.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;

public class DashboardView {

    private final ScreenManager screenManager;
    private final DashboardController dashboardController;
    private final User currentUser;

    public DashboardView(ScreenManager screenManager, User currentUser) {
        this.screenManager = screenManager;
        this.currentUser = currentUser;
        this.dashboardController = new DashboardController();
        this.dashboardController.setCurrentUser(currentUser);
    }

    public Parent getView() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        // 1. Left Sidebar Navigation
        root.setLeft(createSidebar());

        // 2. Center Branding (Logo & Title) + Activity
        root.setCenter(createCenterSection());

        // 3. Right Stats / Metrics Column
        root.setRight(createRightStatsSection());

        return root;
    }

    private VBox createSidebar() {
        VBox sidebar = new VBox(12);
        sidebar.setPadding(new Insets(15));
        sidebar.setPrefWidth(200);
        sidebar.setStyle("-fx-background-color: #f8fafc; -fx-background-radius: 8px; -fx-border-color: #e2e8f0; -fx-border-radius: 8px;");

        Label appHeader = new Label("SCM");
        appHeader.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #1e293b;");

        Button btnDashboard = new Button("Dashboard");
        btnDashboard.setMaxWidth(Double.MAX_VALUE);
        btnDashboard.setDisable(true); // Currently on Dashboard

        Button btnCredentials = new Button("Credentials");
        btnCredentials.setMaxWidth(Double.MAX_VALUE);
        btnCredentials.setOnAction(e -> screenManager.showCredential());

        Button btnCategories = new Button("Categories");
        btnCategories.setMaxWidth(Double.MAX_VALUE);
        btnCategories.setOnAction(e -> screenManager.showCategory());

        Button btnSecurity = new Button("Security");
        btnSecurity.setMaxWidth(Double.MAX_VALUE);
        btnSecurity.setOnAction(e -> screenManager.showSecurity());

        Button btnBackups = new Button("Backups");
        btnBackups.setMaxWidth(Double.MAX_VALUE);
        btnBackups.setOnAction(e -> screenManager.showBackup());

        Button btnSettings = new Button("Settings");
        btnSettings.setMaxWidth(Double.MAX_VALUE);
        btnSettings.setOnAction(e -> screenManager.showSettings());

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        Button btnLogout = new Button("Logout");
        btnLogout.setMaxWidth(Double.MAX_VALUE);
        btnLogout.setStyle("-fx-background-color: #ef4444; -fx-text-fill: white; -fx-font-weight: bold;");
        btnLogout.setOnAction(e -> screenManager.logout());

        sidebar.getChildren().addAll(
                appHeader,
                btnDashboard,
                btnCredentials,
                btnCategories,
                btnSecurity,
                btnBackups,
                btnSettings,
                spacer,
                btnLogout
        );

        return sidebar;
    }

    private VBox createCenterSection() {
        VBox centerBox = new VBox(25);
        centerBox.setPadding(new Insets(10, 30, 10, 30));
        centerBox.setAlignment(Pos.CENTER);

        // Center Logo Branding Container
        VBox brandingBox = new VBox();
        brandingBox.setAlignment(Pos.CENTER);

        try {
            var imageStream = getClass().getResourceAsStream("/images/shield.png");

            if (imageStream == null) {
                imageStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("images/shield.png");
            }

            if (imageStream != null) {
                Image logoImage = new Image(imageStream);
                ImageView imageView = new ImageView(logoImage);

                // Scaled up for better visibility
                imageView.setFitWidth(260);
                imageView.setPreserveRatio(true);
                imageView.setSmooth(true);

                brandingBox.getChildren().add(imageView);
            } else {
                throw new Exception("Image asset shield.png was not found on the classpath!");
            }

        } catch (Exception e) {
            System.err.println("Could not load branding image: " + e.getMessage());

            Label fallbackLabel = new Label("Secure Credential Manager");
            fallbackLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #ffffff;");
            brandingBox.getChildren().add(fallbackLabel);
        }

        // Recent Activity Card
        VBox activityCard = createCard("Recent Activity");
        activityCard.setMaxWidth(500); // Keeps the activity card neatly sized

        Label activityLabel = new Label(dashboardController.getRecentActivityDescription());
        activityLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #475569;");
        activityCard.getChildren().add(activityLabel);

        centerBox.getChildren().addAll(brandingBox, activityCard);

        return centerBox;
    }
    private VBox createRightStatsSection() {
        VBox rightBox = new VBox(15);
        rightBox.setPadding(new Insets(10));
        rightBox.setPrefWidth(260);

        // Credential Count Card
        VBox cardCredentials = createCard("Credential Count");
        Label lblCredCount = new Label(String.valueOf(dashboardController.getCredentialCount()));
        lblCredCount.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #2563eb;");
        cardCredentials.getChildren().add(lblCredCount);

        // Category Count Card
        VBox cardCategories = createCard("Category Count");
        Label lblCatCount = new Label(String.valueOf(dashboardController.getCategoryCount()));
        lblCatCount.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #059669;");
        cardCategories.getChildren().add(lblCatCount);

        // Latest Backup Card
        VBox cardBackup = createCard("Latest Backup");
        Label lblBackupStatus = new Label(dashboardController.getBackupStatus());
        lblBackupStatus.setStyle("-fx-font-size: 14px; -fx-text-fill: #64748b;");
        cardBackup.getChildren().add(lblBackupStatus);

        rightBox.getChildren().addAll(cardCredentials, cardCategories, cardBackup);

        return rightBox;
    }

    private VBox createCard(String title) {
        VBox card = new VBox(8);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 8px; -fx-border-color: #cbd5e1; -fx-border-radius: 8px;");

        Label cardTitle = new Label(title);
        cardTitle.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #334155;");

        card.getChildren().add(cardTitle);
        return card;
    }
}