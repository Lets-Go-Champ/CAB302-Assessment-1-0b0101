package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.Utility.AlertManager;
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

    // FXML UI elements bound to corresponding elements in the view
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

    /**
     * Initializes the controller when the view is loaded. Hides the error label
     * and sets up event handlers for the login and account creation buttons.
     */
    @FXML
    private void initialize() {
        errorLabel.setVisible(false);
        setupEventHandlers();
    }

    /**
     * Configures event handlers for the login and account creation buttons.
     * The login button triggers the login process, and the create account button
     * opens the account creation screen.
     */
    private void setupEventHandlers() {
        loginButton.setOnAction(this::handleLogin);
        createAccountButton.setOnAction(event -> ViewManager.getInstance().getViewFactory().handleCreateAccount(createAccountButton));
    }

    /**
     * Handles the login process when the login button is clicked. Validates the input,
     * authenticates the user, and loads the user's collections if login is successful.
     *
     * @param event The ActionEvent triggered by the login button.
     */
    private void handleLogin(ActionEvent event) {
        // Retrieve input from the username and password fields
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        // Check if either username or password is empty
        if (username.isEmpty() || password.isEmpty()) {
            AlertManager.getInstance().showAlert("Login Error", "Please enter both username and password.", Alert.AlertType.ERROR);
            return;
        }

        // Get the user from the database based on the provided credentials
        UserDAO userDAO = UserDAO.getInstance();
        User currentUser = userDAO.validateCredentials(username, password);

        // Check if the user exists (i.e., username and password match exactly)
        if (currentUser == null) {
            // Display an error if login failed
            AlertManager.getInstance().showAlert("Login Error", "Invalid username or password.", Alert.AlertType.ERROR);
            return;
        }

        // Load the collections associated with the current user
        List<Collection> userCollections = CollectionDAO.getInstance().getCollectionsByUser(currentUser);
        currentUser.setCollections(FXCollections.observableArrayList(userCollections));

        // Set the current user in the UserManager
        UserManager.getInstance().setCurrentUser(currentUser);

        // Close the login stage and open the main application window
        Stage stage = (Stage) loginButton.getScene().getWindow();
        ViewManager.getInstance().getViewFactory().closeStage(stage);
        ViewManager.getInstance().getViewFactory().getClientScreen();
    }
}