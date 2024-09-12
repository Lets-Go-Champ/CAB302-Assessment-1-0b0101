package com.example.cab302assessment10b0101.view;

import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.stage.Stage;

public class AddBookPage {
    public void show(Stage primaryStage) {
        // Load logo image
        Image logoImage = new Image(getClass().getResource("/com/example/cab302assessment10b0101/logo.png").toExternalForm());
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setFitWidth(150);
        logoImageView.setPreserveRatio(true);

        // Create navigation buttons
        Button myBooksButton = new Button("My Books");
        Button addCollectionButton = new Button("Add Collection");
        Button lendingButton = new Button("Lending");

        // Styling navigation buttons
        myBooksButton.setMaxWidth(Double.MAX_VALUE);
        addCollectionButton.setMaxWidth(Double.MAX_VALUE);
        lendingButton.setMaxWidth(Double.MAX_VALUE);

        // Arrange buttons in a VBox
        VBox navVBox = new VBox(10);
        navVBox.setPadding(new Insets(20));
        navVBox.getChildren().addAll(logoImageView, myBooksButton, addCollectionButton,lendingButton);
        navVBox.setAlignment(Pos.TOP_LEFT);

        // Create content for the home page
        Label homeLabel = new Label("Welcome to LibraHome!");
        homeLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        VBox homeContentVBox = new VBox(20);
        homeContentVBox.setPadding(new Insets(20));
        homeContentVBox.setAlignment(Pos.CENTER);
        homeContentVBox.getChildren().add(homeLabel);

        // Create main layout for home page
        HBox mainHBox = new HBox(10);
        mainHBox.getChildren().addAll(navVBox, homeContentVBox);
        mainHBox.setAlignment(Pos.CENTER);

        // Background gradient for home page
        Stop[] stops = new Stop[]{
                new Stop(0, Color.web("#dfe9f3")),
                new Stop(1, Color.web("#ffffff"))
        };
        LinearGradient macBackground = new LinearGradient(0, 0, 1, 1, true, null, stops);
        mainHBox.setBackground(new Background(new BackgroundFill(macBackground, CornerRadii.EMPTY, Insets.EMPTY)));

        // Setting up the scene and stage for home page
        Scene homeScene = new Scene(mainHBox, 600, 400);
        primaryStage.setScene(homeScene);
        primaryStage.setTitle("LibraHome - AddBook Page");
    }

}

