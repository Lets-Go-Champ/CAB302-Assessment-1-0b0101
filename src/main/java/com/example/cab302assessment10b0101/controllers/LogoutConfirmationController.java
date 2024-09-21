package com.example.cab302assessment10b0101.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;

/**
 * Controller class for handling the logout confirmation dialog in the application.
 * This class manages the interaction of the "Yes" and "Cancel" buttons when the
 * user attempts to log out, as well as loading relevant scenes.
 */
public class LogoutConfirmationController {

    /**
     * Handles the action when the "Yes" button is clicked.
     * Closes the current confirmation window and shows a logout success message.
     */
    @FXML
    private Button yesButton; // Button to confirm logout action
    @FXML
    private Button cancelButton; // Button to cancel logout and return to the previous screen
    @FXML
    private Label messageLabel; // Label to display the logout confirmation message

    /**
     * Handles the action when the "Yes" button is clicked.
     * Closes the current confirmation window and shows a logout success message.
     */
    @FXML
    private void handleYes() {
        closeCurrentWindow();
        showLogoutSuccess();
    }

    /**
     * Closes the current window (logout confirmation pop-up).
     * This method is used by both the "Yes" and "Cancel" button handlers.
     */
    private void closeCurrentWindow() {
        Stage stage = (Stage) yesButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Handles the action when the "Cancel" button is clicked.
     * Closes the logout confirmation window and reloads the home page.
     */
    @FXML
    private void handleCancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
        // Load the home page in the current stage
        loadHomePage(stage);
    }

    /**
     * Displays the logout success pop-up in a modal window.
     * This pop-up is shown when the user confirms logout by clicking the "Yes" button.
     */
    private void showLogoutSuccess() {
        try {
            // Loads the LogoutSuccess.fxml file to display the success pop-up
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cab302assessment10b0101/fxml/LogoutSuccess.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Logout Success");
            stage.initModality(Modality.APPLICATION_MODAL); // This makes the pop-up modal
            stage.showAndWait(); // This waits for the pop-up to close before returning to the main window
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the home page into the provided stage. This method is called when the
     * user cancels the logout process and wants to return to the home screen.
     *
     * @param stage The current stage where the home page will be loaded.
     */
    private void loadHomePage(Stage stage) {
        try {
            // Loads the UserMenu.fxml file to display the main home screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cab302assessment10b0101/fxml/UserMenu.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.setTitle("My Books");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

