package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.model.UserDAO;
import com.example.cab302assessment10b0101.model.UserManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ChangeUsernameController {

    @FXML
    private TextField newUsernameTextField;
    public Button updateButton;
    public Button cancelButton;

    //Error messages for input validation
    final String noUsernameMessage = "Please enter a new username.";
    final String successfulUpdateMessage = "Username updated successfully!";

    //The original username for the current user
    final private String originalUsername = UserManager.getInstance().getCurrentUser().getUsername();




    public void handleUpdateUsername() {
        String newUsername = newUsernameTextField.getText();

        // TODO verify that the username does not already exist
        if ( !newUsername.isEmpty() ) {
            UserDAO.getInstance().updateUsername(newUsername, originalUsername);
            showAlert("Success", successfulUpdateMessage, Alert.AlertType.INFORMATION);
            ((Stage) updateButton.getScene().getWindow()).close();
        }
        else { showAlert("Error", noUsernameMessage, Alert.AlertType.ERROR); }
    }

    public void handleCancel() { ((Stage) cancelButton.getScene().getWindow()).close(); }


    /**
     * Shows an alert dialog with the specified type, title, and message.
     * @param title     The title of the alert dialog.
     * @param message   The message content of the alert dialog.
     * @param alertType The type of alert.
     */
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
