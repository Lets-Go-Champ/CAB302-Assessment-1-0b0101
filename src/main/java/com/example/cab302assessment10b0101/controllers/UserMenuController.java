package com.example.cab302assessment10b0101.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class UserMenuController {

    @FXML
    private Button myBooksButton;

    @FXML
    private Button addBookButton;

    @FXML
    private Button logoutButton;

    @FXML
    public void initialize() {
        // Add any necessary initialization logic here
        logoutButton.setOnAction(event -> handleLogoutAction());
    }

    @FXML
    public void handleMyBooksButtonAction() throws IOException {
        switchScene("/com/example/cab302assessment10b0101/fxml/MyBooks.fxml", myBooksButton);
    }

    @FXML
    public void handleAddBookButtonAction() throws IOException {
        switchScene("/com/example/cab302assessment10b0101/fxml/AddBookManually.fxml", addBookButton);
    }

    private void switchScene(String fxmlPath, Node eventSource) throws IOException {
        // Get the resource using getClassLoader to ensure the path is found
        URL resource = getClass().getResource(fxmlPath);

        // Check if the resource is null (meaning it could not be found)
        if (resource == null) {
            throw new IOException("FXML file not found at: " + fxmlPath);
        }

        FXMLLoader loader = new FXMLLoader(resource);
        Parent root = loader.load();  // Ensure the FXML file is loaded correctly
        Stage stage = (Stage) eventSource.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void handleLogoutAction() {
        showLogoutConfirmation();
    }

    private void showLogoutConfirmation() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cab302assessment10b0101/fxml/LogoutConfirmation.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Logout Confirmation");
            stage.initModality(Modality.APPLICATION_MODAL); // This makes the pop-up modal
            stage.showAndWait(); // This waits for the pop-up to close before returning to the main window
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}