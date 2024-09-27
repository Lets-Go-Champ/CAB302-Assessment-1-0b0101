package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.model.*;
import com.example.cab302assessment10b0101.views.MenuOptions;
import javafx.application.Platform;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The MyBooksController class displays the user's book collections
 * and their associated books. It allows the user to switch between collections and view details
 * about specific books by clicking on them.
 */
public class MyBooksController implements Initializable {

    //FXML fields for linking the UI elements in the view
    @FXML
    private ChoiceBox<Collection> collectionsChoiceBox; //Dropdown for selecting a collection

    @FXML
    private GridPane bookContainer; //Grid layout for displaying books

    /**
     * This method is called automatically after the FXML file has been loaded.
     * It initializes the controller, populates the collection dropdown, and loads the books
     * for the default collection if available.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateCollections();
        setupEventHandlers();

        Collection defaultCollection = collectionsChoiceBox.getValue();
        if (defaultCollection != null) {
            loadBooks(defaultCollection);
        }
    }

    /**
     * Sets up event handlers for handling user interactions with the UI.
     * Specifically, it listens for changes in the selected collection in the choice box.
     */
    private void setupEventHandlers() {
        reloadBooksForSelectedCollection();
        collectionsChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                //System.out.println(newValue); //debugging tool
                loadBooks(newValue);
            }
        });
    }

    /**
     * Handles the event when a user clicks on a specific book in the grid.
     * It sets the clicked book as the user-selected book and updates user selected menu item to the book details page.
     *
     * @param book The book that was clicked.
     */
    private void handleBookClick(Book book){
        //System.out.println("Book clicked: " + book.getTitle() + " | Thread: " + Thread.currentThread().getName());  // Ensu
        ViewManager.getInstance().getViewFactory().getUserSelectedBook().set(book); // set flag for Book Detail page
        ViewManager.getInstance().getViewFactory().getUserSelectedMenuItem().set(MenuOptions.BOOKDETAILS); // set flag for Book Detail page
        //System.out.println("Book " + book + "was clicked");
    }

    /**
     * Reloads the books for the currently selected collection in the choice box.
     */
    public void reloadBooksForSelectedCollection() {
        Collection selectedCollection = collectionsChoiceBox.getSelectionModel().getSelectedItem();
        if (selectedCollection != null) {
            //System.out.println("Reloading books for collection: " + selectedCollection.getCollectionName());
            loadBooks(selectedCollection); // Force reloading the books for the selected collection
        } else {
            //System.out.println("No collection selected.");
        }
    }

    /**
     * Loads the books for a specific collection from the database and updates the book grid UI.
     *
     * @param collection The collection for which the books should be loaded.
     */
    private void loadBooks(Collection collection) {
        //System.out.println("Loading books for collection: " + collection.getCollectionName());
        //System.out.println("which has an ID of: " + collection.getId());
        //get current user
        User currentUser = UserManager.getInstance().getCurrentUser();

        //get collection name and ID
        String collectionName = collection.getCollectionName();
        int collectionId = CollectionDAO.getInstance().getCollectionsIDByUserAndCollectionName(currentUser, collectionName);

        //System.out.println("passing: " + collection);
        //Get the books from the database for the selected collection
        ObservableList<Book> books = BookDAO.getInstance().getAllByCollection(collectionId);
        //System.out.println(books);

        //update grid
        updateBookGrid(books);
    }

    /**
     * Updates the book grid in the UI with the books for the selected collection.
     * This method runs on the JavaFX Application Thread to update the UI.
     *
     * @param books The list of books to display in the grid.
     */
    private void updateBookGrid(ObservableList<Book> books) {
        Platform.runLater(() -> {
            //System.out.println("Updating book grid with " + books.size() + " books.");

            //track rows and columns
            int columns = 0;
            int rows = 1;
            int maxColumns = 4;

            bookContainer.getChildren().clear(); // Clear existing content

            try {
                //create book UI for each book
                for (Book book : books) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/com/example/cab302assessment10b0101/fxml/Book.fxml"));
                    VBox bookBox = fxmlLoader.load();
                    BookController bookController = fxmlLoader.getController();
                    bookController.setData(book);

                    //Add a mouse click event for each book being clicked
                    bookBox.setOnMouseClicked(event -> handleBookClick(book));

                    //add book to current row and column
                    bookContainer.add(bookBox, columns, rows);
                    GridPane.setMargin(bookBox, new Insets(10));

                    //update rows and columns
                    columns++;
                    if (columns == maxColumns) {
                        columns = 0;  // Reset to the first column
                        rows++;       // Move to the next row
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Populates the collection choice box with the user's collections.
     * It sets the first collection as the default selection if available.
     */
    private void populateCollections() {
        User currentUser = UserManager.getInstance().getCurrentUser();
        // Debugging
        System.out.println("Current User when populating Collections: " + UserManager.getInstance().getCurrentUser().getUsername());
        // Clear existing items in case switching accounts
        collectionsChoiceBox.getItems().clear();

        ObservableList<Collection> collections = currentUser.getCollections();
        collectionsChoiceBox.setItems(collections);

        // Optionally set a default value
        if (!collections.isEmpty()) {
            collectionsChoiceBox.getSelectionModel().selectFirst();
        }
    }
}

