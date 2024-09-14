package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.exceptions.ErrorMessage;
import com.example.cab302assessment10b0101.model.User;
import com.example.cab302assessment10b0101.model.UserDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateAccountController {

    // Declare FXML fields and buttons
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Button createButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Label messageLabel;

    private UserDAO userDAO = new UserDAO();

    @FXML
    private void handleCreate() {
        // Get inputs
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Check if any of the fields are empty
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            ErrorMessage.showError("Input Error", "All fields must be filled.");
        } else if (!password.equals(confirmPassword)) {
            ErrorMessage.showError("Input Error", "Passwords do not match.");
        } else if (isUsernameDuplicate(username)) {
            ErrorMessage.showError("Warning", "Username already in use.");
        } else {
            // If everything is good, create a new user and insert it into the database
            User newUser = new User(username, password);
            userDAO.insert(newUser);
            System.out.println("User created: " + newUser);
            ((Stage) createButton.getScene().getWindow()).close();
        }
    }

    @FXML
    private void handleCancel() {
        // Close the window when the cancel button is clicked
        ((Stage) cancelButton.getScene().getWindow()).close();
    }

    private boolean isUsernameDuplicate(String username) {
        // Check if the username already exists in the database
        return userDAO.getAll().stream().anyMatch(user ->
                user.getUsername().equalsIgnoreCase(username));
    }
}