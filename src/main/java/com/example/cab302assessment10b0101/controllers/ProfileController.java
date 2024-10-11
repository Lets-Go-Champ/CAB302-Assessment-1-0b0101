package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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

    @FXML
    public TextField usernameTextField;
    public TextField passwordTextField;
    public Button changeUsernameButton;
    public Button changePasswordButton;

    // Updates the displayed username and password
    private void setUsernameTextField(String username) { usernameTextField.setText(username); }
    private void setPasswordTextField(String password) { passwordTextField.setText(password); }

    // Populates the textFields
    public void populateFields(String username, String password) {
        setUsernameTextField(username);
        setPasswordTextField(password);
    }

    // Switches views to changeUsername. Updates the username and instance
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
        } catch (IOException e) { e.printStackTrace(); }
        reload();
    }

    // Switches views to changePassword. Updates the password and instance
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
        } catch (IOException e) { e.printStackTrace(); }
        reload();
    }

    // Updates the Instance and refreshes the values displayed in the textFields
    private void reload() {

        int currentUserID = UserManager.getInstance().getCurrentUser().getId();
        for ( User user : UserDAO.getInstance().getAll() ) {
            if ( user.getId() == currentUserID ) {

                // Update the textFields
                setUsernameTextField(user.getUsername());
                setPasswordTextField(user.getPassword());
            }
        }
    }
}
