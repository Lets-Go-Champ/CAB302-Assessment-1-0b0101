package com.example.cab302assessment10b0101.view;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LendingPage {
    public void show(Stage primaryStage) {
        VBox vbox = new VBox(20);
        Label label = new Label("Lending");
        vbox.getChildren().add(label);

        Scene scene = new Scene(vbox, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Lending");
    }
}
