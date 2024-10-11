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
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * The MyBooksController class displays the user's book collections
 * and their associated books. It allows the user to switch between collections and view details
 * about specific books by clicking on them.
 */
public class MyBooksController implements Initializable {

    // FXML fields for linking the UI elements in the view
    @FXML
    private ChoiceBox<Collection> collectionsChoiceBox;

    @FXML
    private GridPane bookContainer; // Grid layout for displaying books

    @FXML
    private TextField searchTextField;

    @FXML
    private ChoiceBox<String> filterChoiceBox; //Dropdown for selecting a filter

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

        // Set up search functionality
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterBooks(newValue);  // Call method to filter books based on search query
        });
        // Add sorting options to the ChoiceBox
        filterChoiceBox.setItems(FXCollections.observableArrayList("Title", "Author", "Publication Date"));

        // Set the default sorting option to "Title"
        filterChoiceBox.getSelectionModel().select("Title");

        // Handle sorting when an option is selected
        filterChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                switch (newValue) {
                    case "Title":
                        sortBooksByTitle();
                        break;
                    case "Author":
                        sortBooksByAuthor();
                        break;
                    case "Publication Date":
                        sortBooksByPublicationDate();
                        break;
                }
            }
        });
    }

    /**
     * Sorts the books by title in the currently selected collection and updates the book grid.
     * <p>
     * This method retrieves all books from the selected collection, sorts them by title in alphabetical order
     * (ignoring case), and then updates the book grid to reflect the sorted books.
     * </p>
     */
    private void sortBooksByTitle() {
        Collection selectedCollection = collectionsChoiceBox.getSelectionModel().getSelectedItem();
        if (selectedCollection != null) {
            // Get all books from the collection
            ObservableList<Book> books = BookDAO.getInstance().getAllByCollection(selectedCollection.getId());

            // Sort by title
            FXCollections.sort(books, (b1, b2) -> b1.getTitle().compareToIgnoreCase(b2.getTitle()));

            // Update the book grid with sorted books
            updateBookGrid(books);
        }
    }
    /**
     * Sorts the books by author in the currently selected collection and updates the book grid.
     * <p>
     * This method retrieves all books from the selected collection, sorts them by author name in alphabetical order
     * (ignoring case), and then updates the book grid to reflect the sorted books.
     * </p>
     */
    private void sortBooksByAuthor() {
        Collection selectedCollection = collectionsChoiceBox.getSelectionModel().getSelectedItem();
        if (selectedCollection != null) {
            // Get all books from the collection
            ObservableList<Book> books = BookDAO.getInstance().getAllByCollection(selectedCollection.getId());

            // Sort by author
            FXCollections.sort(books, (b1, b2) -> b1.getAuthor().compareToIgnoreCase(b2.getAuthor()));

            // Update the book grid with sorted books
            updateBookGrid(books);
        }
    }
    /**
     * Sorts the books by publication date in the currently selected collection and updates the book grid.
     * <p>
     * This method retrieves all books from the selected collection, sorts them by publication date,
     * and then updates the book grid to reflect the sorted books.
     * </p>
     */
    private void sortBooksByPublicationDate() {
        Collection selectedCollection = collectionsChoiceBox.getSelectionModel().getSelectedItem();
        if (selectedCollection != null) {
            // Get all books from the collection
            ObservableList<Book> books = BookDAO.getInstance().getAllByCollection(selectedCollection.getId());

            // Sort by publication date
            FXCollections.sort(books, (b1, b2) -> b1.getPublicationDate().compareTo(b2.getPublicationDate()));

            // Update the book grid with sorted books
            updateBookGrid(books);
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
        // Set flag for Book Detail page
        ViewManager.getInstance().getViewFactory().getUserSelectedBook().set(book);
        ViewManager.getInstance().getViewFactory().getUserSelectedMenuItem().set(MenuOptions.BOOKDETAILS);
    }

    /**
     * Reloads the books for the currently selected collection in the choice box.
     */
    public void reloadBooksForSelectedCollection() {
        Collection selectedCollection = collectionsChoiceBox.getSelectionModel().getSelectedItem();
        if (selectedCollection != null) {
            // Force reloading the books for the selected collection
            loadBooks(selectedCollection);
        } else {
            System.out.println("No collection selected.");
        }
    }

    /**
     * Loads the books for a specific collection from the database and updates the book grid UI.
     *
     * @param collection The collection for which the books should be loaded.
     */
    private void loadBooks(Collection collection) {
        // Get current User
        User currentUser = UserManager.getInstance().getCurrentUser();

        // Get Collection Name and ID
        String collectionName = collection.getCollectionName();
        int collectionId = CollectionDAO.getInstance().getCollectionsIDByUserAndCollectionName(currentUser, collectionName);

        // Get the books from the database for the selected collection
        ObservableList<Book> books = BookDAO.getInstance().getAllByCollection(collectionId);

        // Update grid
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

            // Track rows and columns
            int columns = 0;
            int rows = 1;
            int maxColumns = 4;

            // Clear existing content
            bookContainer.getChildren().clear();

            try {
                // Create book UI for each book
                for (Book book : books) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/com/example/cab302assessment10b0101/fxml/Book.fxml"));
                    VBox bookBox = fxmlLoader.load();
                    BookController bookController = fxmlLoader.getController();
                    bookController.setData(book);

                    // Add a mouse click event for each book being clicked
                    bookBox.setOnMouseClicked(event -> handleBookClick(book));

                    // Add book to current row and column
                    bookContainer.add(bookBox, columns, rows);
                    GridPane.setMargin(bookBox, new Insets(10));

                    // Update rows and columns
                    columns++;
                    if (columns == maxColumns) {
                        columns = 0;
                        rows++;
                    }
                }
            } catch (IOException e) {
                System.out.println("Error updating book grid: " + e.getMessage());
            }
        });
    }

    /**
     * Populates the collection choice box with the user's collections.
     * It sets the first collection as the default selection if available.
     */
    private void populateCollections() {
        User currentUser = UserManager.getInstance().getCurrentUser();
        // Clear existing items in case switching accounts
        collectionsChoiceBox.getItems().clear();

        ObservableList<Collection> collections = currentUser.getCollections();
        collectionsChoiceBox.setItems(collections);

        // Optionally set a default value
        if (!collections.isEmpty()) {
            collectionsChoiceBox.getSelectionModel().selectFirst();
        }
    }

    /**
     * Filters the books based on the search query entered by the user.
     *
     * @param query The search query entered by the user.
     */
    private void filterBooks(String query) {
        Collection selectedCollection = collectionsChoiceBox.getSelectionModel().getSelectedItem();
        // Exit if no collection is selected
        if (selectedCollection == null) return;

        // Get the current user
        User currentUser = UserManager.getInstance().getCurrentUser();

        // Retrieve the collection ID from the CollectionDAO based on the user and collection name
        String collectionName = selectedCollection.getCollectionName();
        int collectionId = CollectionDAO.getInstance().getCollectionsIDByUserAndCollectionName(currentUser, collectionName);

        // Fetch all books by the collection ID
        ObservableList<Book> allBooks = BookDAO.getInstance().getAllByCollection(collectionId);

        if (query == null || query.trim().isEmpty()) {
            // If the search field is empty, show all books
            updateBookGrid(allBooks);
        } else {
            // Filter books by title (ignoring case)
            ObservableList<Book> filteredBooks = FXCollections.observableArrayList(
                    allBooks.stream()
                            .filter(book -> book.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                                    book.getAuthor().toLowerCase().contains(query.toLowerCase()) ||
                                    book.getPublisher().toLowerCase().contains(query.toLowerCase()) ||
                                    String.valueOf(book.getISBN()).contains(query) ||
                                    book.getPublicationDate().toLowerCase().contains(query.toLowerCase()))
                            .collect(Collectors.toList())
            );
            // Display the filtered books
            updateBookGrid(filteredBooks);
        }
    }


}

