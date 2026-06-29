package com.securecredentialmanager.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import com.securecredentialmanager.database.DatabaseConnection;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage){
        Label label = new Label("Secure Credential Manager");

        StackPane root = new StackPane();
        root.getChildren().add(label);

        Scene scene = new Scene(root, 400, 300);

        primaryStage.setTitle("SCM - Login System Test");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        DatabaseConnection.getConnection();
        launch(args);
    }
}
