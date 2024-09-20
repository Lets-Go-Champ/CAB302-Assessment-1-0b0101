package com.example.cab302assessment10b0101.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LogoutSuccessController {

    @FXML
    private Button okButton;

    FXMLLoader loader = new FXMLLoader(getClass().getResource("com/example/cab302assessment10b0101/fxml/LogoutSuccess.fxml"));

    //Show log out confirmation
    @FXML
    private void handleOkAction() {
        if (okButton == null) {
            System.out.println("okButton is null");
        }
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
        // This makes the pop-up modal
        Platform.exit();
    }
}

