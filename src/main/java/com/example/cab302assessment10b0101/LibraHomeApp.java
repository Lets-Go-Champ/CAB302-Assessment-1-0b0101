package com.example.cab302assessment10b0101;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LibraHomeApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Load image from resources
        Image image = new Image(getClass().getResource("/com/example/cab302assessment10b0101/download.png").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(250); // Set to half the width of the screen
        imageView.setPreserveRatio(true);

        // Login Label
        Label loginLabel = new Label("Login");
        loginLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Username and Password fields with placeholders
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter Username");
        usernameField.setMaxWidth(200);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter Password");
        passwordField.setMaxWidth(200);

        // Creating buttons for login and account creation
        Button loginButton = new Button("Login");
        Button createAccountButton = new Button("Create Account");

        // Styling buttons
        loginButton.setMaxWidth(150);
        createAccountButton.setMaxWidth(150);

        // Add event handler for Create Account button to open pop-up
        createAccountButton.setOnAction(e -> showCreateAccountPopup());

        // Arranging elements in a VBox
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(loginLabel, usernameField, passwordField, loginButton, createAccountButton);

        // HBox to divide the screen into left (image) and right (login)
        HBox hbox = new HBox(10);
        hbox.getChildren().addAll(imageView, vbox);
        hbox.setAlignment(Pos.CENTER);

        // Background gradient
        Stop[] stops = new Stop[]{
                new Stop(0, Color.web("#dfe9f3")),
                new Stop(1, Color.web("#ffffff"))
        };
        LinearGradient macBackground = new LinearGradient(0, 0, 1, 1, true, null, stops);

        hbox.setBackground(new Background(new BackgroundFill(macBackground, CornerRadii.EMPTY, Insets.EMPTY)));

        // Setting up the scene and stage
        Scene scene = new Scene(hbox, 600, 400);
        primaryStage.setTitle("LibraHome - Home Library Catalogue");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to show a pop-up window for account creation
    private void showCreateAccountPopup() {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL); // Block input to other windows

        // Create Account Label
        Label createAccountLabel = new Label("Create Account");
        createAccountLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Fields for creating an account
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter Username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter Password");

        // Button to confirm account creation
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            // Handle logic for account creation
            String username = usernameField.getText();
            String password = passwordField.getText();

            System.out.println("Username: " + username);
            System.out.println("Password: " + password);

            popupStage.close(); // Close the pop-up
        });

        // Button to cancel and close the pop-up without taking any action
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> popupStage.close());

        // HBox to arrange the Submit and Cancel buttons side by side
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(submitButton, cancelButton);

        // VBox to arrange the input fields, Create Account label, and buttons
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(createAccountLabel, usernameField, passwordField, buttonBox);

        Scene popupScene = new Scene(vbox, 300, 200);
        popupStage.setScene(popupScene);
        popupStage.setTitle("Create Account");
        popupStage.showAndWait(); // Wait for the pop-up to be closed before returning to main window
    }

    public static void main(String[] args) {
        launch(args);
    }
}