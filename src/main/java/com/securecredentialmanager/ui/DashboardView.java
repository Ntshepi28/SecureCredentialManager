package com.securecredentialmanager.ui;

import com.securecredentialmanager.controllers.DashboardController;
import com.securecredentialmanager.models.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class DashboardView {


    private final ScreenManager screenManager;
    private final DashboardController dashboardController;

    private final BorderPane root;

    public DashboardView(
            ScreenManager screenManager,
            User user) {

        this.screenManager = screenManager;

        this.dashboardController =
                new DashboardController();

        this.dashboardController.setCurrentUser(user);

        root = new BorderPane();

        createView();
    }

    public void createView(){
        root.setPadding(new Insets(20));

        VBox sidebar = createSidebar();
        VBox dashboard = createDashboard();

        root.setLeft(sidebar);
        root.setCenter(dashboard);
    }

    private VBox createSidebar(){
        VBox sidebar = new VBox(12);

        sidebar.setPadding(new Insets(20));
        sidebar.setPrefWidth(200);

        Label title = new Label("SCM");
        Label subtitle = new Label("Secure Credential Manager");

        Button dashboardButton = new Button("Dashboard");
        Button credentialButton = new Button("Credentials");
        Button categoriesButton = new Button("Categories");
        Button securityButton = new Button("Security");
        Button backupButton = new Button("Backups");
        Button settingsButton = new Button("Settings");
        Button logoutButton = new Button("Logout");

        dashboardButton.setMaxWidth(Double.MAX_VALUE);
        credentialButton.setMaxWidth(Double.MAX_VALUE);
        categoriesButton.setMaxWidth(Double.MAX_VALUE);
        securityButton.setMaxWidth(Double.MAX_VALUE);
        backupButton.setMaxWidth(Double.MAX_VALUE);
        settingsButton.setMaxWidth(Double.MAX_VALUE);
        logoutButton.setMaxWidth(Double.MAX_VALUE);

        dashboardButton.setOnAction(event -> screenManager.showDashboard(
                dashboardController.getCurrentUser()
        ));

        credentialButton.setOnAction(event -> screenManager.showCredential());
        categoriesButton.setOnAction(event -> screenManager.showCategory());
        securityButton.setOnAction(event -> screenManager.showSecurity());
        backupButton.setOnAction(event -> screenManager.showBackup());
        settingsButton.setOnAction(event -> screenManager.showSettings());
        logoutButton.setOnAction(event -> screenManager.logout());

        Region spacer = new Region();

        VBox.setVgrow(spacer, Priority.ALWAYS);

        sidebar.getChildren().addAll(title, subtitle, new Separator(),
                dashboardButton, credentialButton, categoriesButton,
                securityButton, backupButton, settingsButton,
                spacer, logoutButton);

        return sidebar;
    }

    private VBox createDashboard(){
        VBox dashboard = new VBox(20);
        dashboard.setPadding(new Insets(20));

        Label welcome = new Label(dashboardController.getWelcomeMessage());
        Label heading = new Label("Dashboard");

        GridPane cards = new GridPane();
        cards.setHgap(15);
        cards.setVgap(15);

        VBox credentialCard = createCard("Credentials", String.valueOf(dashboardController.
                getCredentialCount()));

        VBox categoryCard = createCard("Categories", String.valueOf(dashboardController.
                getCategoryCount()));

        VBox securityCard = createCard("Security Status", dashboardController.
                getSecurityStatus());

        VBox backupCard = createCard("Latest Backup", dashboardController.getBackupStatus());

        cards.add(credentialCard, 0, 0);
        cards.add(categoryCard, 1, 1);
        cards.add(securityCard, 0, 1);
        cards.add(backupCard, 1, 1);

        VBox activityBox = new VBox(10);
        activityBox.setPadding(new Insets(20));
        activityBox.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID,
                new CornerRadii(5), BorderWidths.DEFAULT)));


        Label activityTitle = new Label("Recent Activity");
        Label activity = new Label(dashboardController.getRecentActivityDescription);

        activityBox.getChildren().addAll(activityTitle, activity);

        return dashboard;
    }

    private VBox createCard(String title, String value){
        VBox card = new VBox(10);

        card.setPadding(new Insets(20));
        card.setPrefWidth(250);
        card.setPrefHeight(130);
        card.setAlignment(Pos.CENTER);

        Label titleLabel = new Label(title);

        Label valueLabel = new Label(value);

        card.getChildren().addAll(titleLabel, valueLabel);

        return card;
    }

    public Parent getView(){
        return root;
    }
}
