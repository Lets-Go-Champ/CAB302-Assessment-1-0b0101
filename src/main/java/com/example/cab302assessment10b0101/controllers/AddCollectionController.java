package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.model.Collection;
import com.example.cab302assessment10b0101.model.CollectionDAO;
import com.example.cab302assessment10b0101.model.UserManager;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class AddCollectionController implements Initializable {

    @FXML
    private TextField CollectionNameTextField;

    @FXML
    private TextField DescriptionTextField;

    @FXML
    private Button saveBtn;

    @FXML
    private Button cancelBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        saveBtn.setOnAction(e -> saveCollection());
        cancelBtn.setOnAction(e -> {
        });
    }

    private void saveCollection() {
        String collectionName = CollectionNameTextField.getText().trim();
        String collectionDescription = DescriptionTextField.getText().trim();

        if (collectionName.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Collection name cannot be empty.");
            return;
        }

        // Create a new collection and insert it into the database
        System.out.println("\nCreating Collection...");
        int currentUserId = UserManager.getInstance().getCurrentUser().getId();
        System.out.println("Current User ID = " + currentUserId + "\n");
        Collection newCollection = new Collection(currentUserId, collectionName, collectionDescription.isEmpty() ? "" : collectionDescription);
        System.out.println("New collection = " + newCollection);

        CollectionDAO.getInstance().insert(newCollection);

        ObservableList<Collection> collections = UserManager.getInstance().getCurrentUser().getCollections();
        collections.forEach(c -> System.out.println("Collection: " + c.getCollectionName()));

        UserManager.getInstance().getCurrentUser().addCollection(newCollection);

        // Clear fields and show success message
        clearFields();
        showAlert(Alert.AlertType.INFORMATION, "Success", "Collection saved successfully!");
    }

    private void clearFields() {
        CollectionNameTextField.clear();
        DescriptionTextField.clear();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}