package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.Alert.AlertManager;
import com.example.cab302assessment10b0101.model.*;
import com.example.cab302assessment10b0101.views.MenuOptions;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The AddCollectionController class handles the addition of new collections
 * in the application. It manages user input from the UI and interacts with the
 * data model to save a new collection to the database.
 */
public class AddCollectionController implements Initializable {

    //FXML UI elements that are linked to the corresponding elements in the view
    @FXML
    private TextField CollectionNameTextField; //Input field for the collection name

    @FXML
    private TextField DescriptionTextField; //Input field for the collection description

    @FXML
    private Button saveBtn; //Button to save/add the collection

    @FXML
    private Button cancelBtn; //Button to cancel


    /**
     * Initializes the controller when the view is loaded. Sets event handlers for the
     * save and cancel buttons.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        //Set the action for the save button to call saveCollection() method
        saveBtn.setOnAction(e -> saveCollection());

        //Set the action for the cancel button (currently does nothing)
        cancelBtn.setOnAction(e -> {
            // Switch back to the "My Books" page
            ViewManager.getInstance().getViewFactory().getUserSelectedMenuItem().set(MenuOptions.MYBOOKS);
        });

    }

    /**
     * Handles the saving of a new collection. Validates the input, creates a new collection,
     * adds it to the database, and updates the current user's collection list.
     */
    private void saveCollection() {
        //Get the input from the text fields and trim extra spaces
        String collectionName = CollectionNameTextField.getText().trim();
        String collectionDescription = DescriptionTextField.getText().trim();

        //Check if the collection name is empty and if it is show error
        if (collectionName.isEmpty()) {
            AlertManager.getInstance().showAlert("Error", "Collection name cannot be empty.", Alert.AlertType.INFORMATION);
            return;
        }

        //Get the current user's ID
        int currentUserId = UserManager.getInstance().getCurrentUser().getId();

        //Create a new collection object with the current user ID, collection name, and description
        Collection newCollection = new Collection(currentUserId, collectionName, collectionDescription.isEmpty() ? "" : collectionDescription);

        //Insert the new collection into the database
        CollectionDAO.getInstance().insert(newCollection);

        //Add the new collection to the current user's collection list
        UserManager.getInstance().getCurrentUser().addCollection(newCollection);

        // Clear input fields and show success alert
        clearFields();
        AlertManager.getInstance().showAlert("Success", "Collection saved successfully!", Alert.AlertType.INFORMATION);
    }

    /**
     * Clears the input fields after the collection has been saved or cancelled.
     */
    private void clearFields() {
        CollectionNameTextField.clear();
        DescriptionTextField.clear();
    }
}
