package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.model.UserDAO;
import com.example.cab302assessment10b0101.model.UserManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ChangeUsernameController {

    @FXML
    private TextField newUsernameTextField;

    final private String originalUsername = UserManager.getInstance().getCurrentUser().getUsername();

    public void handleUpdateUsername() {
        String newUsername = newUsernameTextField.getText();

        // TODO verify that the username does not already exist
        if ( newUsername != null ) {
            System.out.println(originalUsername);
            System.out.println(newUsername);
            UserDAO.getInstance().updateUsername(newUsername, originalUsername);
        }
        // else { showAlert.. }
    }

    public void handleCancel() {}
}
