package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.Alert.AlertManager;
import com.example.cab302assessment10b0101.model.UserDAO;
import com.example.cab302assessment10b0101.model.UserManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * The ChangePasswordController class handles updating the user's password.
 * It retrieves data from the UI, validates it, and stores the information in the database.
 */
public class ChangePasswordController {

    @FXML
    private TextField newPasswordTextField;
    public Button updateButton;
    public Button cancelButton;

    // Messages for input validation and notification
    final String noPasswordMessage = "Please enter a new password.";
    final String successfulUpdateMessage = "Password updated successfully!";

    // The ID of the current user
    final int userID = UserManager.getInstance().getCurrentUser().getId();


    // Updates the password if all fields are valid
    public void handleUpdatePassword() {
        String newPassword = newPasswordTextField.getText();

        // Password pattern: at least 6 characters long, one uppercase letter, one lowercase letter
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z]).{6,}$";

        // Perform validation
        if (newPassword.isEmpty()) {
            AlertManager.getInstance().showAlert("Error", "Password cannot be empty.", Alert.AlertType.ERROR);
            return;
        }

        // Check if the new password meets the validation criteria
        else if (!newPassword.matches(passwordPattern)) {
            AlertManager.getInstance().showAlert("Invalid Password", "Password must be at least 6 characters long, contain one uppercase letter, and one lowercase letter.", Alert.AlertType.ERROR);
            return;
        }

        // If validation passes, update the password
        UserDAO.getInstance().updatePassword(newPassword,userID);
        AlertManager.getInstance().showAlert("Success", "Password successfully updated!", Alert.AlertType.INFORMATION);

        // Close the current window
        ((Stage) updateButton.getScene().getWindow()).close();
    }

    // Returns to the user profile
    public void handleCancel() {
        ((Stage) cancelButton.getScene().getWindow()).close();
    }
}
