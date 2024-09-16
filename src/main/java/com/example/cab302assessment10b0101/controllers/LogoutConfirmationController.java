package com.example.cab302assessment10b0101.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class LogoutConfirmationController {

    @FXML
    private Button yesButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label messageLabel;

    @FXML
    private void handleYes() {
        // Close the current stage (logout confirmation pop-up)
        Stage stage = (Stage) yesButton.getScene().getWindow();
        stage.close();

        // Load the login window
        loadLoginWindow();
    }

    @FXML
    private void handleCancel() {
        // Close the current stage (logout confirmation pop-up)
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();

        // Return to the home page
        loadHomePage();
    }

    private void loadLoginWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cab302assessment10b0101/fxml/Login.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadHomePage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cab302assessment10b0101/fxml/MyBooks.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("My Books");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

