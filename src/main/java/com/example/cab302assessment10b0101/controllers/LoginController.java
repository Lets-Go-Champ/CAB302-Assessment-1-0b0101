package com.example.cab302assessment10b0101.controllers;

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

        if (username.isEmpty() || password.isEmpty()) {
            //showError("Username and password cannot be empty.");
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cab302assessment10b0101/CreateAccountPopup.fxml"));
            Scene scene = new Scene(loader.load());

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Create New Account");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(createAccountButton.getScene().getWindow());
            dialogStage.setScene(scene);

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            //showError("Could not load create account window.");
        }
    }
    }
/*
    private void showError(String message) {
        errorLabel.setText("Error: " + message);
        errorLabel.setVisible(true);
    }
    /*
 */
