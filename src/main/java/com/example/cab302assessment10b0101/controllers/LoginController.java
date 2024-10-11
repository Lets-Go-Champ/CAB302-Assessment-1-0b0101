package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.model.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.util.List;

/**
 * The LoginController class handles the login functionality of the application.
 * It manages user authentication, input validation, and allows users to create accounts.
 */
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
        errorLabel.setVisible(false);
        setupEventHandlers();
    }

    private void setupEventHandlers() {
        loginButton.setOnAction(this::handleLogin);
        createAccountButton.setOnAction(event -> ViewManager.getInstance().getViewFactory().handleCreateAccount(createAccountButton));
    }

    private void handleLogin(ActionEvent event) {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Login Error", "Please enter both username and password.", Alert.AlertType.ERROR);
            return;
        }

        if (!isValidLogin(username, password)) {
            showAlert("Login Error", "Username and password do not match any existing account.", Alert.AlertType.ERROR);
            return;
        }

        UserDAO userDAO = UserDAO.getInstance();
        User currentUser = userDAO.validateCredentials(username, password);

        List<Collection> userCollections = CollectionDAO.getInstance().getCollectionsByUser(currentUser);
        currentUser.setCollections(FXCollections.observableArrayList(userCollections));

        if (currentUser != null) {
            UserManager.getInstance().setCurrentUser(null);
            UserManager.getInstance().setCurrentUser(currentUser);

            Stage stage = (Stage) loginButton.getScene().getWindow();
            ViewManager.getInstance().getViewFactory().closeStage(stage);
            ViewManager.getInstance().getViewFactory().getClientScreen();
        } else {
            showAlert("Login Error", "Invalid username or password.", Alert.AlertType.ERROR);
        }
    }

    private boolean isValidLogin(String username, String password) {
        return UserDAO.getInstance().getAll().stream().anyMatch(user ->
                user.getUsername().equalsIgnoreCase(username) && user.getPassword().equals(password));
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}