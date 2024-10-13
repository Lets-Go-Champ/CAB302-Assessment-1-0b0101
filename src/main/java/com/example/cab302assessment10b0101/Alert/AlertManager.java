package com.example.cab302assessment10b0101.Alert;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * The AlertManager class is a singleton that handles the display of alert popups.
 * It ensures that the alert functionality is reusable across the entire application.
 */
public class AlertManager {

    // The single instance of AlertManager
    private static AlertManager instance;

    // Private constructor to prevent instantiation
    private AlertManager() {}

    // Get the single instance of AlertManager
    public static AlertManager getInstance() {
        if (instance == null) {
            instance = new AlertManager();
        }
        return instance;
    }

    // Method to show an alert dialog
    public void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Method to show a confirmation dialog and return the user's response (used only when necessary)
    public Optional<ButtonType> showConfirmation(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert.showAndWait();
    }

    // Method to show a blocking modal alert
    public void showModalAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Set modality to block interaction with other windows until the alert is acknowledged
        alert.initModality(javafx.stage.Modality.APPLICATION_MODAL);

        // Show the alert and wait for user response
        alert.showAndWait();
    }
}