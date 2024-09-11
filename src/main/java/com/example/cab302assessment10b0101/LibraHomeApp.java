package com.example.cab302assessment10b0101;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;

public class LibraHomeApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Load image from resources
        Image image = new Image(getClass().getResource("/com/example/cab302assessment10b0101/download.png").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(250); // Set to half the width of the screen
        imageView.setPreserveRatio(true);

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

        // Arranging elements in a VBox
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(usernameField, passwordField, loginButton, createAccountButton);

        // HBox to divide the screen into left (image) and right (login)
        HBox hbox = new HBox(10);
        hbox.getChildren().addAll(imageView, vbox);
        hbox.setAlignment(Pos.CENTER);

        // macOS-like background
        Stop[] stops = new Stop[] {
                new Stop(0, Color.web("#dfe9f3")),
                new Stop(1, Color.web("#ffffff"))
        };
        LinearGradient macBackground = new LinearGradient(0, 0, 1, 1, true, null, stops);

        hbox.setBackground(new Background(new BackgroundFill(macBackground, CornerRadii.EMPTY, Insets.EMPTY)));

        // Setting up the scene and stage
        Scene loginScene = new Scene(hbox, 600, 400);
        primaryStage.setTitle("LibraHome - Home Library Catalogue");
        primaryStage.setScene(loginScene);
        primaryStage.show();

        // Button Actions: Link the buttons to new pages
        loginButton.setOnAction(e -> showLoggedInPage(primaryStage));
        createAccountButton.setOnAction(e -> showCreateAccountPage(primaryStage));
    }

    // Add the Logged-in Page
    public void showLoggedInPage(Stage primaryStage) {
        // Logo on top-left
        Image logo = new Image(getClass().getResource("/com/example/cab302assessment10b0101/logo.png").toExternalForm());
        ImageView logoView = new ImageView(logo);
        logoView.setFitWidth(100);
        logoView.setPreserveRatio(true);

        // Buttons
        Button myBooksButton = new Button("My Books");
        Button addCollectionButton = new Button("Add Collection");
        Button addBookButton = new Button("Add Book");
        Button lendingButton = new Button("Lending");
        Button logoutButton = new Button("Logout");

        // Styling buttons
        myBooksButton.setMaxWidth(150);
        addCollectionButton.setMaxWidth(150);
        addBookButton.setMaxWidth(150);
        lendingButton.setMaxWidth(150);
        logoutButton.setMaxWidth(150);

        // VBox for buttons
        VBox buttonBox = new VBox(15);
        buttonBox.setAlignment(Pos.TOP_LEFT);
        buttonBox.getChildren().addAll(logoView, myBooksButton, addCollectionButton, addBookButton, lendingButton, logoutButton);
        buttonBox.setPadding(new Insets(20));

        // Create a border layout
        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(buttonBox);

        // Set up the scene and stage
        Scene loggedInScene = new Scene(borderPane, 600, 400);
        primaryStage.setScene(loggedInScene);
        primaryStage.setTitle("LibraHome - Dashboard");
        primaryStage.show();

        // Logout button action to go back to login page
        logoutButton.setOnAction(event -> start(primaryStage)); // Call the start method to show login page
    }

    // Add the Create Account Page
    public void showCreateAccountPage(Stage primaryStage) {
        // Username field
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter Username");
        usernameField.setMaxWidth(200);

        // Password field
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter Password");
        passwordField.setMaxWidth(200);

        // Buttons for saving and cancelling
        Button saveButton = new Button("Save");
        Button cancelButton = new Button("Cancel");

        // Styling buttons
        saveButton.setMaxWidth(100);
        cancelButton.setMaxWidth(100);

        // VBox for form layout
        VBox formBox = new VBox(10);
        formBox.setAlignment(Pos.CENTER);
        formBox.getChildren().addAll(usernameField, passwordField, saveButton, cancelButton);
        formBox.setPadding(new Insets(20));

        // BorderPane for centering the form
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(formBox);

        // Set up the scene and stage
        Scene createAccountScene = new Scene(borderPane, 600, 400);
        primaryStage.setScene(createAccountScene);
        primaryStage.setTitle("LibraHome - Create Account");
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}

