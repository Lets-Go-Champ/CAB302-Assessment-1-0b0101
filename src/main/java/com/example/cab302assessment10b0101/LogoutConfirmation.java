package com.example.cab302assessment10b0101;

import com.example.cab302assessment10b0101.view.LibraHomeApp;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LogoutConfirmation {

    public void show(Stage primaryStage) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);

        VBox vbox = new VBox(20);
        Label label = new Label("Are you sure you want to log out?");
        Button confirmButton = new Button("Sure");
        Button cancelButton = new Button("Cancel");

        confirmButton.setOnAction(e -> {
            // Go back to the login page
            new LibraHomeApp().start(primaryStage);
            popupStage.close();
        });

        cancelButton.setOnAction(e -> popupStage.close());

        vbox.getChildren().addAll(label, confirmButton, cancelButton);
        Scene scene = new Scene(vbox, 300, 150);
        popupStage.setScene(scene);
        popupStage.setTitle("Log Out Confirmation");
        popupStage.showAndWait();
    }
}
