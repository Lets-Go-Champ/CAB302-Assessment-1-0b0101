package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.model.User;
import com.example.cab302assessment10b0101.model.UserDAO;
import com.example.cab302assessment10b0101.model.UserManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;

import com.example.cab302assessment10b0101.model.ViewManager;

public class LoginController {

    // Declare FXML fields and buttons
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
    private ImageView loginImageView;


    // Declare DAO for interacting with User Database
    //private UserDAO userDAO = new UserDAO();

    @FXML
    private void initialize() {
        // This method is called automatically after the FXML file has been loaded
        // Not 100% what errorLabel is for in the current implementation, Error Message fills this role decently as it is
        errorLabel.setVisible(false);
//        Image image = new Image(getClass().getResourceAsStream("/com/example/cab302assessment10b0101/download.png"));
//        loginImageView.setImage(image);
        // Sets up event handlers for the buttons
        setupEventHandlers();
    }

    private void setupEventHandlers() {
        // Attach event handlers to buttons
        loginButton.setOnAction(this::handleLogin);
        createAccountButton.setOnAction(event -> handleCreateAccount());
    }

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

        UserDAO userDAO = UserDAO.getInstance();
        User currentUser = userDAO.validateCredentials(username, password);

        if (currentUser != null) {
            // Set the logged-in user in UserManager
            UserManager.getInstance().setCurrentUser(currentUser);

            // Close the login stage and open the main application
            Stage stage = (Stage) loginButton.getScene().getWindow();
            ViewManager.getInstance().getViewFactory().closeStage(stage);
            ViewManager.getInstance().getViewFactory().getClientScreen();
        } else {
            showAlert("Login Error", "Invalid username or password.", Alert.AlertType.ERROR);
        }
        // If login is successful, load MyBooks.fxml and display it

        /*try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cab302assessment10b0101/fxml/Client.fxml"));
            Scene myBooksScene = new Scene(loader.load());

            // Get the stage from the event source (login button) and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(myBooksScene);
            stage.setTitle("My Books");
            stage.show();

        } catch (IOException e) {
            // Debugging Tool
            e.printStackTrace();
            showAlert("Error", "Could not load MyBooks page.", AlertType.ERROR);
        }
    }*/
}
    private void handleCreateAccount() {
        try {
            // Load the CreateAccountPopup.fxml file for account creation
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cab302assessment10b0101/fxml/CreateAccountPopup.fxml"));
            Scene scene = new Scene(loader.load());

            // Create a new dialog window for the create account form
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
            e.printStackTrace();
            showAlert("Error", "Could not load the create account window.", AlertType.ERROR);
        }
    }

    private boolean isValidLogin(String username, String password) {
        // Validate login credentials by checking if the username and password match any user in the database
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

