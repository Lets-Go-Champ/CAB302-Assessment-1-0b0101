package com.example.cab302assessment10b0101.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ProfileController {

    @FXML
    public TextField usernameTextField;
    public TextField passwordTextField;

    private void setUsernameTextField(String username) { usernameTextField.setText(username); }
    private void setPasswordTextField(String password) { passwordTextField.setText(password); }


    public void populateFields(String username, String password) {
        setUsernameTextField(username);
        setPasswordTextField(password);
    }

    public void handleUpdateUsername() {
    }

    public void handleUpdatePassword() {
    }
}
