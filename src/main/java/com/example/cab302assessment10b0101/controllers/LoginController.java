package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.exceptions.ErrorMessage;
import com.example.cab302assessment10b0101.model.User;
import com.example.cab302assessment10b0101.model.UserDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button createAccountButton;

    @FXML
    private Button loginButton;

    @FXML
    private Label errorLabel;

    private UserDAO userDAO = new UserDAO();

    @FXML
    private void initialize() {
        // This method is called automatically after the FXML file has been loaded
        errorLabel.setVisible(false);
        setupEventHandlers();
    }

    private void setupEventHandlers() {
        loginButton.setOnAction(event -> handleLogin());
        createAccountButton.setOnAction(event -> handleCreateAccount());
    }

    private void handleLogin() {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        // Check if fields are empty
        if (username.isEmpty() || password.isEmpty()) {
            ErrorMessage.showError("Login Error", "Please enter both username and password.");
            return;
        }

        // Validate login credentials
        if (!isValidLogin(username, password)) {
            ErrorMessage.showError("Login Error", "Username and password do not match any existing account.");
            return;
        }

        // TODO: Implement actual login logic here
        // For now print to console
        System.out.println("Login attempted with username: " + username);

        // If login is successful, you might redirect to the main application scene
        // If unsuccessful show an error message
    }

    private void handleCreateAccount() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cab302assessment10b0101/fxml/CreateAccountPopup.fxml"));
            Scene scene = new Scene(loader.load());

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Create New Account");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(createAccountButton.getScene().getWindow());
            dialogStage.setScene(scene);

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            ErrorMessage.showError("Error", "Could not load the create account window.");
        }
    }

    private boolean isValidLogin(String username, String password) {
        return userDAO.getAll().stream().anyMatch(user ->
                user.getUsername().equalsIgnoreCase(username) && user.getPassword().equals(password));
    }

    // Method to check if the username already exists in the database
    private boolean isUsernameDuplicate(String username) {
        return userDAO.getAll().stream().anyMatch(user ->
                user.getUsername().equalsIgnoreCase(username));
    }

}

