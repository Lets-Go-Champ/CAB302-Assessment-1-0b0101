package com.example.cab302assessment10b0101.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Controller class for handling the "Logout Success" confirmation screen.
 * This class manages the interaction with the "OK" button to close the
 * success message and exit the application.
 */
public class LogoutSuccessController {

    @FXML
    // Button to confirm and close the logout success message
    private Button okButton;

    // FXMLLoader instance to load the FXML file for the logout success screen
    FXMLLoader loader = new FXMLLoader(getClass().getResource("com/example/cab302assessment10b0101/fxml/LogoutSuccess.fxml"));

    /**
     * Handles the action when the "OK" button is clicked.
     * Closes the current window and exits the application.
     */
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

