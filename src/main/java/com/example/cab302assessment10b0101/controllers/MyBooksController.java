package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.Utility.AlertManager;
import com.example.cab302assessment10b0101.model.*;
import com.example.cab302assessment10b0101.views.MenuOptions;
import javafx.application.Platform;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;


/**
 * The MyBooksController class displays the user's book collections
 * and their associated books. It allows the user to switch between collections and view details
 * about specific books by clicking on them.
 */
public class MyBooksController implements Initializable {

    // FXML fields for linking the UI elements in the view
    @FXML
    private ChoiceBox<Collection> collectionsChoiceBox; //Choice box for selection collection

    @FXML
    private GridPane bookContainer; // Grid layout for displaying books

    @FXML
    private TextField searchTextField; //Text field for search queries

    @FXML
    private ComboBox<String> filterComboBox; //ComboBox for sorting options


    /**
     * This method is called automatically after the FXML file has been loaded.
     * It initializes the controller, populates the collection dropdown, and loads the books
     * for the default collection if available.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object is not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateCollections();
        setupEventHandlers();

        //Load books for the default collection if available
        Collection defaultCollection = collectionsChoiceBox.getValue();
        if (defaultCollection != null) {
            loadBooks(defaultCollection);
        }

        // Set up search functionality
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterBooks(newValue.trim());
        });

        // Add sorting options to the ComboBox
        filterComboBox.getItems().addAll("Title", "Author", "Publication Date");

        // Set the default value with "Sort by"
        filterComboBox.setButtonCell(createCustomButtonCell("Sort by"));
        filterComboBox.setPromptText("Sort by");

        // Update the ComboBox items to display text
        filterComboBox.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null); // Show null text for empty items
                        } else {
                            setText(item); // Show the selected item text
                        }
                    }
                };
            }
        });

        // Add listener to handle selection and sorting
        filterComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                switch (newValue) {
                    case "Title":
                        sortBooksByTitle(); // Sort books by title
                        break;
                    case "Author":
                        sortBooksByAuthor(); // Sort books by author
                        break;
                    case "Publication Date":
                        sortBooksByPublicationDate(); // Sort books by publication date
                        break;
                }
            }
        });
    }

    /**
     * Creates a ListCell with the specified text for the ComboBox.
     *
     * @param text The text to display when no item is selected.
     * @return A custom ListCell with the specified text.
     */
    private ListCell<String> createCustomButtonCell(String text) {
        return new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(text); // Show "Sort by" by default
                } else {
                    setText(item); // Show the selected item text
                }
            }
        };
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
        if (selectedCollection == null) return; // No collection selected, exit

        User currentUser = UserManager.getInstance().getCurrentUser();
        String collectionName = selectedCollection.getCollectionName();
        int collectionId = CollectionDAO.getInstance().getCollectionsIDByUserAndCollectionName(currentUser, collectionName);

        // Get all books from the collection using the retrieved collectionId
        ObservableList<Book> allBooks = BookDAO.getInstance().getAllByCollection(collectionId);

        // Sort by title
        FXCollections.sort(allBooks, (b1, b2) -> b1.getTitle().compareToIgnoreCase(b2.getTitle()));

        // Update the book grid with sorted books
        updateBookGrid(allBooks);
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
        if (selectedCollection == null) return; // No collection selected, exit

        User currentUser = UserManager.getInstance().getCurrentUser();
        String collectionName = selectedCollection.getCollectionName();
        int collectionId = CollectionDAO.getInstance().getCollectionsIDByUserAndCollectionName(currentUser, collectionName);

        // Get all books from the collection using the retrieved collectionId
        ObservableList<Book> books = BookDAO.getInstance().getAllByCollection(collectionId);

        // Sort by author
        FXCollections.sort(books, (b1, b2) -> b1.getAuthor().compareToIgnoreCase(b2.getAuthor()));

        // Update the book grid with sorted books
        updateBookGrid(books);
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
        if (selectedCollection == null) return; // No collection selected, exit

        User currentUser = UserManager.getInstance().getCurrentUser();
        String collectionName = selectedCollection.getCollectionName();
        int collectionId = CollectionDAO.getInstance().getCollectionsIDByUserAndCollectionName(currentUser, collectionName);

        // Get all books from the collection using the retrieved collectionId
        ObservableList<Book> books = BookDAO.getInstance().getAllByCollection(collectionId);

        // Sort by publication date, handling null values
        FXCollections.sort(books, (b1, b2) -> {
            LocalDate date1 = b1.getPublicationDateAsLocalDate();
            LocalDate date2 = b2.getPublicationDateAsLocalDate();

            // Handle null values (null dates are pushed to the end)
            if (date1 == null && date2 == null) return 0;  // Both dates are null
            if (date1 == null) return 1;  // Push nulls to the end
            if (date2 == null) return -1; // Push nulls to the end

            // Compare actual dates
            return date1.compareTo(date2);
        });

        // Update the book grid with sorted books
        updateBookGrid(books);
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
                AlertManager.getInstance().showAlert("Update Error: ", "Failed to update the Book grid", Alert.AlertType.ERROR);
            }
        });
    }

    /**
     * Populates the collection choice box with the user's collections.
     * It sets the first collection as the default selection if available.
     */
    private void populateCollections() {
        User currentUser = UserManager.getInstance().getCurrentUser();
        collectionsChoiceBox.getItems().clear();

        ObservableList<Collection> collections = currentUser.getCollections();
        collectionsChoiceBox.setItems(collections);

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
        if (selectedCollection == null) return;

        User currentUser = UserManager.getInstance().getCurrentUser();
        String collectionName = selectedCollection.getCollectionName();
        int collectionId = CollectionDAO.getInstance().getCollectionsIDByUserAndCollectionName(currentUser, collectionName);

        ObservableList<Book> allBooks = BookDAO.getInstance().getAllByCollection(collectionId);

        if (query == null || query.trim().isEmpty()) {
            // Show all books if the search field is empty
            updateBookGrid(allBooks);
        } else {
            // Split the search query into words
            String[] searchTerms = query.toLowerCase().split("\\s+");

            // Filter the books by checking if any term matches title, author, etc.
            ObservableList<Book> filteredBooks = FXCollections.observableArrayList(
                    allBooks.stream()
                            .filter(book -> Arrays.stream(searchTerms)
                                    .allMatch(term ->
                                            book.getTitle().toLowerCase().contains(term) ||
                                                    book.getAuthor().toLowerCase().contains(term) ||
                                                    book.getPublisher().toLowerCase().contains(term) ||
                                                    String.valueOf(book.getISBN()).contains(term) ||
                                                    book.getPublicationDate().toLowerCase().contains(term))
                            )
                            .collect(Collectors.toList())
            );

            // Display the filtered books
            updateBookGrid(filteredBooks);
        }
    }
}

