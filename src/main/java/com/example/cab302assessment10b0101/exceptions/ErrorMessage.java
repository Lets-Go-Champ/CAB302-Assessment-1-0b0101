package com.example.cab302assessment10b0101.exceptions;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ErrorMessage {

    // Method to display the error dialog with a specific message
    public static void showError(String title, String message) {
        Stage warningStage = new Stage();
        warningStage.initModality(Modality.APPLICATION_MODAL);
        warningStage.setTitle(title);

        // Message label
        Label messageLabel = new Label(message);
        messageLabel.setWrapText(true);
        messageLabel.setStyle("-fx-font-size: 14px;");

        // OK button to close the dialog
        Button okButton = new Button("OK");
        okButton.setOnAction(e -> warningStage.close());

        // Layout for the pop-up
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(messageLabel, okButton);

        Scene scene = new Scene(vbox);
        warningStage.setScene(scene);

        // Adjust the dialog size based on the message length
        warningStage.sizeToScene();
        warningStage.showAndWait();
    }
}
