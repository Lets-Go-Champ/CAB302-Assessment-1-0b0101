package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.model.UserDAO;
import com.example.cab302assessment10b0101.model.UserManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ChangeUsernameController {

    @FXML
    private TextField newUsernameTextField;
    public Button updateButton;
    public Button cancelButton;

    final private String originalUsername = UserManager.getInstance().getCurrentUser().getUsername();

    public void handleUpdateUsername() {
        String newUsername = newUsernameTextField.getText();

        // TODO verify that the username does not already exist
        if ( newUsername != null ) {
            UserDAO.getInstance().updateUsername(newUsername, originalUsername);
            ((Stage) updateButton.getScene().getWindow()).close();
        }
        // else { showAlert.. }
    }

    public void handleCancel() { ((Stage) cancelButton.getScene().getWindow()).close(); }
}
