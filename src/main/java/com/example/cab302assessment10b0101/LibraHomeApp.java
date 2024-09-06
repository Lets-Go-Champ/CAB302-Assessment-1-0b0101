package com.example.cab302assessment10b0101;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class LibraHomeApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Creating a simple label for now
        Label label = new Label("Welcome to LibraHome!");
        StackPane root = new StackPane(label);

        // Setting up the main stage
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("LibraHome - Home Library Catalogue");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

