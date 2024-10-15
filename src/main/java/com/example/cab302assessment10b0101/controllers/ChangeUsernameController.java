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
 * The ChangeUsernameController class handles updating the user's username.
 * It retrieves data from the UI, validates it, and stores the information in the database.
 */
public class ChangeUsernameController {

    @FXML
    private TextField newUsernameTextField;
    public Button updateButton;
    public Button cancelButton;

    // Messages for input validation and notification
    final String noUsernameMessage = "Please enter a new username.";
    final String usernameExistsMessage = "The entered username is assigned to another user.";
    final String successfulUpdateMessage = "Username updated successfully!";

    // The ID of the current user
    final int userID = UserManager.getInstance().getCurrentUser().getId();


    // Updates the username if all fields are valid
    public void handleUpdateUsername() {
        String newUsername = newUsernameTextField.getText();

        // Perform validation
        if ( isUsernameDuplicate(newUsername) ) {
            AlertManager.getInstance().showAlert("Error", usernameExistsMessage, Alert.AlertType.ERROR); return;
        }
        if ( newUsername.isEmpty() ) {
            AlertManager.getInstance().showAlert("Error", noUsernameMessage, Alert.AlertType.ERROR); return;
        }

        // Update the username
        UserDAO.getInstance().updateUsername(newUsername, userID);
        UserManager.getInstance().getCurrentUser().setUsername(newUsername);
        AlertManager.getInstance().showAlert("Success", successfulUpdateMessage, Alert.AlertType.INFORMATION);
        ((Stage) updateButton.getScene().getWindow()).close();
    }

    // Returns to the user profile
    public void handleCancel() { ((Stage) cancelButton.getScene().getWindow()).close(); }

    // Determines if the username already exists in the database
    private boolean isUsernameDuplicate(String username) {
        return UserDAO.getInstance().getAll().stream().anyMatch(user -> user.getUsername().equalsIgnoreCase(username));
    }
}
