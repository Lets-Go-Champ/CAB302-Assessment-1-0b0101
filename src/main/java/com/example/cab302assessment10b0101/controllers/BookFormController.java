package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.model.*;
import com.example.cab302assessment10b0101.views.MenuOptions;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

/**
 * Singleton class to manage common functionalities across multiple book-related controllers.
 */
public class BookFormController {

    // Singleton instance of BookFormController
    private static BookFormController instance;

    /**
     * Returns the Singleton instance of BookFormController.
     *
     * @return The single instance of BookFormController.
     */
    public static synchronized BookFormController getInstance() {
        if (instance == null) {
            instance = new BookFormController();
        }
        return instance;
    }

    /**
     * Populates the collection dropdown with the current user's collections.
     */
    public void populateCollections(ChoiceBox<Collection> collectionChoiceBox) {
        User currentUser = UserManager.getInstance().getCurrentUser();
        ObservableList<Collection> collections = FXCollections.observableArrayList(
                CollectionDAO.getInstance().getCollectionsByUser(currentUser)
        );
        collectionChoiceBox.setItems(collections);
        if (!collections.isEmpty()) {
            collectionChoiceBox.getSelectionModel().selectFirst();
        }
    }

    /**
     * Clears the input fields provided as arguments.
     *
     * @param fields A variable number of TextField instances to clear.
     */
    public void clearFields(TextField... fields) {
        for (TextField field : fields) {
            field.clear();
        }
    }

    /**
     * Handles the action for adding a new collection by navigating to the Add Collection menu.
     *
     * @param addCollectionLink The Hyperlink used to trigger this action.
     */
    public void handleAddCollectionLink(Hyperlink addCollectionLink) {
        addCollectionLink.setOnAction(event ->
                ViewManager.getInstance().getViewFactory().getUserSelectedMenuItem().set(MenuOptions.ADDCOLLECTION)
        );
    }

    /**
     * Populates the reading status ChoiceBox with standard options.
     *
     * @param readingStatusChoiceBox The ChoiceBox to populate with reading statuses.
     */
    public void populateReadingStatus(ChoiceBox<String> readingStatusChoiceBox) {
        ObservableList<String> readingStatusOptions = FXCollections.observableArrayList(
                "Unread", "Reading", "Read"
        );
        readingStatusChoiceBox.setItems(readingStatusOptions);
    }

    /**
     * Sets up bindings to manage the visibility and layout of components dynamically.
     *
     * @param emptyStateView The VBox representing the empty state view.
     * @param addBookForm The VBox containing the add book form.
     * @param collectionChoiceBox The ChoiceBox used for selecting collections.
     */
    public void setupBindings(VBox emptyStateView, VBox addBookForm, ChoiceBox<Collection> collectionChoiceBox) {
        // Bind the visibility of the empty state view to whether the collectionChoiceBox has items
        emptyStateView.visibleProperty().bind(Bindings.createBooleanBinding(
                () -> collectionChoiceBox.getItems().isEmpty(),
                collectionChoiceBox.getItems()
        ));
        emptyStateView.managedProperty().bind(emptyStateView.visibleProperty());

        // Bind the visibility of the addBookForm to whether the collectionChoiceBox has items
        addBookForm.visibleProperty().bind(Bindings.createBooleanBinding(
                () -> !collectionChoiceBox.getItems().isEmpty(),
                collectionChoiceBox.getItems()
        ));
        addBookForm.managedProperty().bind(addBookForm.visibleProperty());
    }

    /**
     * Sets up event handlers for buttons such as adding a book or uploading an image.
     *
     * @param addImageButton The Button to upload a book cover image.
     * @param addBookButton The Button to add a new book.
     * @param imageUploadHandler The event handler for the image upload action.
     * @param addBookHandler The event handler for the add book action.
     */
    public void setupEventHandlers(Button addImageButton, Button addBookButton,
                                   Runnable imageUploadHandler, Runnable addBookHandler) {
        addImageButton.setOnAction(e -> imageUploadHandler.run());
        addBookButton.setOnAction(e -> addBookHandler.run());
    }

    /**
     * Checks if a collection is selected in the provided ChoiceBox.
     *
     * @param collectionChoiceBox The ChoiceBox containing collections.
     * @return True if a collection is selected, otherwise false.
     */
    public boolean collectionSelected(ChoiceBox<Collection> collectionChoiceBox) {
        return collectionChoiceBox.getSelectionModel().getSelectedItem() == null;
    }
}