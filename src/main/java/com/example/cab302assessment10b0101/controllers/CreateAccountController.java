package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.model.User;
import com.example.cab302assessment10b0101.model.UserDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

/**
 * The CreateAccountController class handles the logic for creating a new user account.
 * It validates the input from the user (username, password, and confirmation), ensures that the username is unique,
 * and inserts a new user into the database if all conditions are met.
 */
public class CreateAccountController {

    //FXML UI elements that are linked to the corresponding elements in the view
    @FXML
    private TextField usernameField; //Field for entering a username

    @FXML
    private PasswordField passwordField; //Field for entering a password

    @FXML
    private PasswordField confirmPasswordField; //Field for confirming the password

    @FXML
    private Button createButton; //Button to create an account

    @FXML
    private Button cancelButton; //Button to cancel

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

        // Check if any of the fields are empty
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert("Input Error", "All fields must be filled.", AlertType.ERROR);
        }

        // Check password and confirm password fields match
        else if (!password.equals(confirmPassword)) {
            showAlert("Input Error", "Passwords do not match.", AlertType.ERROR);
        }

        //Check username is not already in the system
        else if (isUsernameDuplicate(username)) {
            showAlert("Warning", "Username already in use.", AlertType.WARNING);
        }
        //If all values are valid, craete a new user and insert into the database
        else {
            //Create new user with input username and password
            User newUser = new User(username, password);

            //Insert new user into the database
            UserDAO.getInstance().insert(newUser);
            //System.out.println("User created: " + newUser);

            //close stage window
            ((Stage) createButton.getScene().getWindow()).close();
        }
    }

    /**
     * Handles the cancel operation. Closes the current window when the 'Cancel' button is clicked.
     */
    @FXML
    private void handleCancel() {
        // Close the window when the cancel button is clicked
        ((Stage) cancelButton.getScene().getWindow()).close();
    }

    /**
     * Checks if the given username already exists in the database.
     *
     * @param username The username to check for duplication.
     * @return True if the username is already in use, false otherwise.
     */
    private boolean isUsernameDuplicate(String username) {
        // Check if the username already exists in the database
        return UserDAO.getInstance().getAll().stream().anyMatch(user ->
                user.getUsername().equalsIgnoreCase(username));
    }

    /**
     * Displays an alert dialog with a given title, message, and alert type.
     * This is used to provide meaningful error messages to the user.
     *
     * @param title     The title of the alert dialog.
     * @param message   The message to display in the alert dialog.
     * @param alertType The type of alert.
     */
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}