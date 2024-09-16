package com.example.cab302assessment10b0101.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class UserMenuController {

    public Button myBooksBtn;
    public Button addBookBtn;
    public Button addCollectionBtn;
    public Button lendingBtn;
    public Button logoutBtn;


    @FXML
    private Button myBooksButton;

    @FXML
    private Button addBookButton;

    @FXML
    public void initialize() {
        // Add any necessary initialization logic here
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
}