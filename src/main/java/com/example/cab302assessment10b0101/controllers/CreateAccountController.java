package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.Utility.AlertManager;
import com.example.cab302assessment10b0101.model.User;
import com.example.cab302assessment10b0101.model.UserDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * The CreateAccountController class handles the logic for creating a new user account.
 * It validates the input from the user (username, password, and confirmation), ensures that the username is unique,
 * and inserts a new user into the database if all conditions are met.
 */
public class CreateAccountController {

    //FXML UI elements that are linked to the corresponding elements in the view
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

    /**
     * Handles the account creation logic when the 'Create' button is clicked.
     * This method performs validation on the input fields and creates a new account if validation passes.
     */
    @FXML
    private void handleCreate() {
        // Retrieves the input values from the text fields
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Password must be at least 6 characters long, contain one uppercase letter, one number, and one special character
        String passwordPattern = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[A-Za-z0-9!@#$%^&*]{6,}$";

        // Check if any of the fields are empty
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            AlertManager.getInstance().showAlert("Input Error", "All fields must be filled.", Alert.AlertType.ERROR);
        }
        // Check password validity
        else if (!password.matches(passwordPattern)) {
            AlertManager.getInstance().showAlert("Invalid Password", "Password must be at least 6 characters long, contain one uppercase letter, one number, and one special character", Alert.AlertType.ERROR);
        }
        // Check if password and confirm password match
        else if (!password.equals(confirmPassword)) {
            AlertManager.getInstance().showAlert("Input Error", "Passwords do not match.", Alert.AlertType.ERROR);
        }
        // Check if the username is already in use
        else if (isUsernameDuplicate(username)) {
            AlertManager.getInstance().showAlert("Warning", "Username already in use.", Alert.AlertType.WARNING);
        }
        // If all validations pass, create the new user and insert it into the database
        else {
            User newUser = new User(username, password);
            UserDAO.getInstance().insert(newUser);

            // Close the current window after account creation
            ((Stage) createButton.getScene().getWindow()).close();
        }
    }

    /**
     * Handles the cancel operation. Closes the current window when the 'Cancel' button is clicked.
     */
    @FXML
    private void handleCancel() {
        ((Stage) cancelButton.getScene().getWindow()).close();
    }

    /**
     * Checks if the given username already exists in the database.
     *
     * @param username The username to check for duplication.
     * @return True if the username is already in use, false otherwise.
     */
    private boolean isUsernameDuplicate(String username) {
        return UserDAO.getInstance().getAll().stream().anyMatch(user ->
                user.getUsername().equalsIgnoreCase(username));
    }

}