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

public class ProfileController {

    @FXML
    public TextField usernameTextField;
    public TextField passwordTextField;
    public Button changeUsernameButton;

    private void setUsernameTextField(String username) { usernameTextField.setText(username); }
    private void setPasswordTextField(String password) { passwordTextField.setText(password); }


    public void populateFields(String username, String password) {
        setUsernameTextField(username);
        setPasswordTextField(password);
    }

    public void handleUpdateUsername() {
        try {
            // Load the ChangeUsernamePopup.fxml file for account creation
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
        // showAlert
    }

    public void handleUpdatePassword() {
    }

    /**
     * Refreshes the values displayed in the textFields
     */
    private void reload() {
        // Update the Instance to reflect new user credentials
        int currentUserID = UserManager.getInstance().getCurrentUser().getId();
        for ( User user : UserDAO.getInstance().getAll() ) {
            if ( user.getId() == currentUserID ) {
                UserManager.getInstance().setCurrentUser(user);
            }
        }
        setUsernameTextField(UserManager.getInstance().getCurrentUser().getUsername());
        setPasswordTextField(UserManager.getInstance().getCurrentUser().getPassword());
    }
}
