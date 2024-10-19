package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.Utility.AlertManager;
import com.example.cab302assessment10b0101.model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * The ProfileController class handles displaying and updating the user's credentials.
 * It retrieves data from the UI, validates it, and stores the information in the database.
 */
public class ProfileController {

    /**
     * TextField for user to input the username.
     */
    @FXML
    public TextField usernameTextField;

    /**
     * TextField for user to input the password.
     */
    public TextField passwordTextField;

    /**
     * Button to initiate the username change process.
     */
    public Button changeUsernameButton;

    /**
     * Button to initiate the password change process.
     */
    public Button changePasswordButton;

    /**
     * Sets the username in the username text field.
     *
     * @param username The username to display in the text field.
     */
    private void setUsernameTextField(String username) {
        usernameTextField.setText(username);
    }

    /**
     * Sets the password in the password text field.
     *
     * @param password The password to display in the text field.
     */
    private void setPasswordTextField(String password) {
        passwordTextField.setText(password);
    }

    /**
     * Populates the username and password fields with the provided values.
     *
     * @param username The username to display.
     * @param password The password to display.
     */
    public void populateFields(String username, String password) {
        setUsernameTextField(username);
        setPasswordTextField(password);
    }

    /**
     * Handles the process of updating the username by opening a modal window
     * for the user to change their username. Reloads the profile after changes.
     */
    public void handleUpdateUsername() {
        try {
            // Load the ChangeUsernamePopup.fxml file for account updating
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cab302assessment10b0101/fxml/ChangeUsernamePopup.fxml"));
            Scene scene = new Scene(loader.load());

            // Display the Pop-up
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Change Username");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(changeUsernameButton.getScene().getWindow());
            dialogStage.setScene(scene);
            dialogStage.showAndWait();
        } catch (IOException e) {
            AlertManager.getInstance().showAlert("Update Error: ", "Failed to update Username.", Alert.AlertType.ERROR);
        }
        reload(); //Reload the profile to reflect any changes
    }

    /**
     * Handles the process of updating the password by opening a modal window
     * for the user to change their password. Reloads the profile after changes.
     */
    public void handleUpdatePassword() {
        try {
            // Load the ChangePasswordPopup.fxml file for account updating
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cab302assessment10b0101/fxml/ChangePasswordPopup.fxml"));
            Scene scene = new Scene(loader.load());

            // Display the Pop-up
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Change Password");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(changePasswordButton.getScene().getWindow());
            dialogStage.setScene(scene);
            dialogStage.showAndWait();
        } catch (IOException e) {
            AlertManager.getInstance().showAlert("Update Error: ", "Failed to update Password.", Alert.AlertType.ERROR);
        }
        reload(); //Reload the profile to reflect any changes
    }

    /**
     * Reloads the profile data by retrieving the current user's information
     * and updating the displayed values in the username and password fields.
     */
    private void reload() {
        int currentUserID = UserManager.getInstance().getCurrentUser().getId();

        // Iterate through all users and find the one matching the current user's ID
        for ( User user : UserDAO.getInstance().getAll() ) {
            if ( user.getId() == currentUserID ) {
                // Update the textFields
                setUsernameTextField(user.getUsername());
                setPasswordTextField(user.getPassword());
                break; //stop search once user is found
            }
        }
    }
}
