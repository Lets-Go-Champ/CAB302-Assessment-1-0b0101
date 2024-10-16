package com.example.cab302assessment10b0101.Utility;

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

    /**
     * Displays an alert dialog with the specified title and message.
     *
     * @param title     The title of the alert dialog.
     * @param message   The message content to be displayed in the alert.
     * @param alertType The type of alert to be displayed (e.g., INFORMATION, WARNING).
     */
    public void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Displays a confirmation dialog with the specified title and message.
     * This dialog prompts the user to confirm an action and returns the user's response.
     *
     * @param title   The title of the confirmation dialog.
     * @param message The message content to be displayed in the confirmation dialog.
     * @return An Optional<ButtonType> representing the user's choice (OK or CANCEL).
     */
    public Optional<ButtonType> showConfirmation(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert.showAndWait();
    }

    /**
     * Displays a blocking modal alert that requires the user to acknowledge it
     * before interacting with other windows in the application.
     *
     * @param title     The title of the modal alert dialog.
     * @param message   The message content to be displayed in the modal alert.
     * @param alertType The type of alert to be displayed (e.g., ERROR, WARNING).
     */
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