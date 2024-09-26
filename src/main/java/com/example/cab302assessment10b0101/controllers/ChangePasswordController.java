package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.model.UserManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ChangePasswordController {

    @FXML
    private TextField newPasswordTextField;
    public Button updateButton;
    public Button cancelButton;

    // Error messages for input validation
    final String noPasswordMessage = "Please enter a new password.";
    final String successfulUpdateMessage = "Password updated successfully!";

    // The original username for the current user
    final private String originalPassword = UserManager.getInstance().getCurrentUser().getPassword();

    /**
     * Updates the password if all fields are valid
     */
    public void handleUpdatePassword() {
        String newPassword = newPasswordTextField.getText();

        // Perform validation
        if ( newPassword.isEmpty() ) { showAlert("Error", noPasswordMessage, Alert.AlertType.ERROR); return; }

        //UserDAO.getInstance().updatePassword(newPassword, originalPassword);
        showAlert("Success", successfulUpdateMessage, Alert.AlertType.INFORMATION);
        ((Stage) updateButton.getScene().getWindow()).close();
    }

    // Returns to the user profile
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
