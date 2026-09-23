package com.securecredentialmanager.ui;

import com.securecredentialmanager.controllers.CategoryController;
import com.securecredentialmanager.models.User;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class CategoryView {

    private final ScreenManager screenManager;
    private final User currentUser;
    private final CategoryController categoryController;

    private final BorderPane root;

    public CategoryView(
            ScreenManager screenManager,
            User user) {

        this.screenManager = screenManager;
        this.currentUser = user;

        this.categoryController =
                new CategoryController();

        root = new BorderPane();

        createView();
    }

    public void createView(){
        root.setPadding(new Insets(20));

        VBox content = new VBox(15);

        Label title = new Label("Categories");
        Label count = new Label("Categories: " + categoryController.getCategoryCount(currentUser.getId()));

        TextField nameField = new TextField();

        nameField.setPromptText("Category name");

        Button createButton = new Button("Create Category");
        Button backButton = new Button("Back to Dashboard");

        Label message = new Label();

        createButton.setOnAction(event -> {
            String name = nameField.getText();

            if (name == null || name.isBlank()) {
                message.setText("Category name is required.");
                return;
            }

            long categoryId = System.currentTimeMillis();

            boolean success = categoryController.createCategory(categoryId, currentUser.getId(),
                    name);

            if (success){
                message.setText("Category created successfully.");

                nameField.clear();
                count.setText("Categories: " + categoryController.getCategoryCount(currentUser.getId()));
            } else {
                message.setText("Unable to create category.");
            }
        });

        backButton.setOnAction(event -> screenManager.showDashboard(currentUser));

        content.getChildren().addAll(
                title, count,
                nameField, createButton,
                message, backButton
        );

        root.setCenter(content);
    }

    public Parent getView(){
        return root;
    }
}
