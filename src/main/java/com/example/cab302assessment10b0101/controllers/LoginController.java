package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.model.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.List;

/**
 * The LoginController class handles the login functionality of the application.
 * It manages user authentication, input validation, and allows users to create accounts.
 */
public class LoginController {

    //FXML UI elements that are linked to the corresponding elements in the view
    @FXML
    private TextField usernameTextField; //text field for entering a username

    @FXML
    private PasswordField passwordTextField; //text field for entering a password

    @FXML
    private Button createAccountButton; //Button to create an account

    @FXML
    private Button loginButton; //Button to login

    @FXML
    private Label errorLabel; //Label for error messages

    /**
     * This method is automatically called when the FXML file is loaded.
     * It initializes the error label to be hidden and sets up event handlers for buttons.
     */
    @FXML
    private void initialize() {
        errorLabel.setVisible(false); //hides error label
        setupEventHandlers();
    }

    /**
     * Sets up the event handlers for the login and create account buttons.
     */
    private void setupEventHandlers() {
        // Attach event handlers to buttons
        loginButton.setOnAction(this::handleLogin);
        createAccountButton.setOnAction(event -> handleCreateAccount());
    }

    /**
     * Handles the login logic when the 'Login' button is clicked.
     * Validates the user input and checks the credentials against the database.
     *
     * @param event The ActionEvent triggered by the login button click.
     */
    private void handleLogin(ActionEvent event) {
        // Get inputs
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        // Check if fields are empty
        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Login Error", "Please enter both username and password.", AlertType.ERROR);
            return;
        }

        // Validate login credentials
        if (!isValidLogin(username, password)) {
            showAlert("Login Error", "Username and password do not match any existing account.", AlertType.ERROR);
            return;
        }

        //Validate user credentials
        UserDAO userDAO = UserDAO.getInstance();
        User currentUser = userDAO.validateCredentials(username, password);

        //Get user's collections and set current user collections
        List<Collection> userCollections = CollectionDAO.getInstance().getCollectionsByUser(currentUser);
        currentUser.setCollections(FXCollections.observableArrayList(userCollections));

        //if login is successful
        if (currentUser != null) {
            // Clear any previous session data before assigning new user data
            UserManager.getInstance().setCurrentUser(null);  // Clear current user session
            // Set the current user in UserManager
            UserManager.getInstance().setCurrentUser(currentUser);
            System.out.println(currentUser);
            System.out.println(currentUser.getId());

            // Close the login stage and display the main application view
            Stage stage = (Stage) loginButton.getScene().getWindow();
            ViewManager.getInstance().getViewFactory().closeStage(stage);
            ViewManager.getInstance().getViewFactory().getClientScreen();
        } else {
            //show error message if login credentials are invalid
            showAlert("Login Error", "Invalid username or password.", Alert.AlertType.ERROR);
        }
}

    /**
     * Handles the process of opening the account creation window when the create account button is clicked.
     */
    private void handleCreateAccount() {
        try {
            // Load the CreateAccountPopup.fxml file for account creation
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cab302assessment10b0101/fxml/CreateAccountPopup.fxml"));
            Scene scene = new Scene(loader.load());

            // Create a new stage window for the create account form
            Stage dialogStage = new Stage();
            // Set the title of the dialog
            dialogStage.setTitle("Create New Account");
            // Make the dialog modal
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            // Set the owner window for the dialog
            dialogStage.initOwner(createAccountButton.getScene().getWindow());
            // Set the scene (create account form)
            dialogStage.setScene(scene);
            // Show the dialog and wait for it to close
            dialogStage.showAndWait();
        } catch (IOException e) {
            // Debugging Tool
            System.out.println("Error handling Create Account: " + e.getMessage());
            showAlert("Error", "Could not load the create account window.", AlertType.ERROR);
        }
    }

    /**
     * Validates the login credentials by checking if the username and password match a user in the database.
     *
     * @param username The entered username.
     * @param password The entered password.
     * @return True if the credentials are valid, false otherwise.
     */
    private boolean isValidLogin(String username, String password) {
        //check all users in the database for a match
        return UserDAO.getInstance().getAll().stream().anyMatch(user ->
                user.getUsername().equalsIgnoreCase(username) && user.getPassword().equals(password));
    }

    /**
     * Displays an alert dialog with the specified title, message, and alert type.
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

