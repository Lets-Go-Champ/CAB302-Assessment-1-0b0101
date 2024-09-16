package com.example.cab302assessment10b0101.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
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
        closeCurrentWindow();
        showLogoutSuccess();
    }

    private void closeCurrentWindow() {
        Stage stage = (Stage) yesButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleCancel() {
        // Close the current stage (logout confirmation pop-up)
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();

        // Load the home page in the current stage
        loadHomePage(stage);
    }

    private void showLogoutSuccess() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cab302assessment10b0101/fxml/LogoutSuccess.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Logout Success");
            stage.initModality(Modality.APPLICATION_MODAL); // This makes the pop-up modal
            stage.showAndWait(); // This waits for the pop-up to close before returning to the main window
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadHomePage(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cab302assessment10b0101/fxml/UserMenu.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root)); // Set the scene on the current stage
            stage.setTitle("My Books");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

