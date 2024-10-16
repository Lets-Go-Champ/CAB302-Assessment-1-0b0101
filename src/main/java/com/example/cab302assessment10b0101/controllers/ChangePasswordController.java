package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.Utility.AlertManager;
import com.example.cab302assessment10b0101.model.UserDAO;
import com.example.cab302assessment10b0101.model.UserManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * The ChangePasswordController class handles the functionality for updating
 * the user's password. It retrieves user input from the UI, validates the
 * password according to specific criteria, and stores the new password in
 * the database.
 */
public class ChangePasswordController {

    @FXML
    private TextField newPasswordTextField; //Input field for the new password
    public Button updateButton; //Button to trigger password update
    public Button cancelButton; //Button to cancel the update process

    // The ID of the current user
    final int userID = UserManager.getInstance().getCurrentUser().getId();


    /**
     * Handles the action of updating the user's password. Validates the input
     * password to ensure it meets complexity requirements. If valid, updates
     * the password in the database and displays a success alert. Closes the
     * current window upon successful update.
     */
    public void handleUpdatePassword() {
        String newPassword = newPasswordTextField.getText();

        // Password must be at least 6 characters long, contain one uppercase letter, one number, and one special character
        String passwordPattern = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[A-Za-z0-9!@#$%^&*]{6,}$";

        // Perform validation
        if (newPassword.isEmpty()) {
            AlertManager.getInstance().showAlert("Error", "Password cannot be empty.", Alert.AlertType.ERROR);
            return;
        }

        // Check if the new password meets the validation criteria
        else if (!newPassword.matches(passwordPattern)) {
            AlertManager.getInstance().showAlert("Invalid Password", "Password must be at least 6 characters long, contain one uppercase letter, one number, and one special character.", Alert.AlertType.ERROR);
            return;
        }

        // If validation passes, update the password
        UserDAO.getInstance().updatePassword(newPassword,userID);
        AlertManager.getInstance().showAlert("Success", "Password successfully updated!", Alert.AlertType.INFORMATION);

        // Close the current window
        ((Stage) updateButton.getScene().getWindow()).close();
    }

    /**
     * Handles the action of cancelling the password change. Closes the current
     * window without saving any changes to the password.
     */
    public void handleCancel() {
        ((Stage) cancelButton.getScene().getWindow()).close();
    }
}
