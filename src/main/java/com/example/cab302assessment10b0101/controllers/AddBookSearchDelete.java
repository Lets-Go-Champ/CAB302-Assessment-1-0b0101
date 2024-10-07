package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.Scraper;
import com.example.cab302assessment10b0101.model.Collection;
import com.example.cab302assessment10b0101.model.CollectionDAO;
import com.example.cab302assessment10b0101.model.User;
import com.example.cab302assessment10b0101.model.UserManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * The AddBookSearchDelete controller handles the logic for the search functionality
 * and retrieving detailed book information.
 */
public class AddBookSearchDelete {

    @FXML
    private TextField searchTextField; // Search bar for entering book title

    @FXML
    private Button searchButton; // Button to trigger book search

    @FXML
    private ListView<String> searchResultsListView; // ListView to display search results

    @FXML
    private Button addBookButton; // Button to add book

    @FXML
    private ChoiceBox<Collection> collectionChoiceBoxSearch; // ChoiceBox to select collection

    private Scraper scraper;

    private String selectedBookUrl; // Store the URL of the selected book

    private List<Map<String, String>> searchResults; // Store search results with titles and URLs

    /**
     * Initializes the controller and sets up the scraper.
     */
    @FXML
    public void initialize() {
        scraper = new Scraper();

        // Initially disable the "Add Book" button
        addBookButton.setDisable(true);

        // Load the user's collections into the ChoiceBox
        populateCollections();

        // Listen for changes in the ListView selection
        searchResultsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Enable the "Add Book" button when a book is selected
            int selectedIndex = searchResultsListView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0 && selectedIndex < searchResults.size()) {
                // Get the correct URL from the search results
                selectedBookUrl = searchResults.get(selectedIndex).get("url");
                addBookButton.setDisable(false);
            }
        });
    }

    /**
     * Populates the collection ChoiceBox with the user's collections.
     */
    private void populateCollections() {
        User currentUser = UserManager.getInstance().getCurrentUser();
        List<Collection> collections = CollectionDAO.getInstance().getCollectionsByUser(currentUser);

        // Convert the list of collections to an observable list
        ObservableList<Collection> observableCollections = FXCollections.observableArrayList(collections);

        // Set the items in the ChoiceBox
        collectionChoiceBoxSearch.setItems(observableCollections);

        // Select the first collection by default
        if (!observableCollections.isEmpty()) {
            collectionChoiceBoxSearch.getSelectionModel().selectFirst();
        }
    }

    /**
     * This method is triggered when the "Search" button is pressed.
     * It scrapes Google Books and displays the top 5 search results in the ListView.
     */
    @FXML
    public void handleSearchButton() {
        String query = searchTextField.getText().trim();

        if (query.isEmpty()) {
            showAlert("Error", "Please enter a search query.", AlertType.ERROR);
            return;
        }

        searchButton.setDisable(true);
        searchResultsListView.getItems().clear();

        // Run the scraping process on a background thread
        new Thread(() -> {
            try {
                searchResults = scraper.scrapeGoogleBooks(query);

                Platform.runLater(() -> {
                    searchResults.forEach(book -> searchResultsListView.getItems().add(book.get("title")));
                    searchButton.setDisable(false);

                    if (searchResults.isEmpty()) {
                        searchResultsListView.getItems().add("No results found.");
                    }
                });

            } catch (IOException e) {
                Platform.runLater(() -> {
                    showAlert("Error", "Failed to retrieve search results.", AlertType.ERROR);
                    searchButton.setDisable(false);
                });
            }
        }).start();
    }

    /**
     * This method is triggered when the "Add Book" button is pressed.
     * It scrapes detailed book information and prints it to the terminal.
     */
    @FXML
    public void handleAddBookButton() {
        if (selectedBookUrl == null || selectedBookUrl.isEmpty()) {
            showAlert("Error", "No book selected.", AlertType.ERROR);
            return;
        }

        Collection selectedCollection = collectionChoiceBoxSearch.getSelectionModel().getSelectedItem();
        if (selectedCollection == null) {
            showAlert("Error", "No collection selected.", AlertType.ERROR);
            return;
        }

        System.out.println("Add Book button pressed for collection: " + selectedCollection.getCollectionName());

        // Run the scraping process for book details on a background thread
        new Thread(() -> {
            try {
                Map<String, String> bookDetails = scraper.scrapeBookDetails(selectedBookUrl);

                // Print detailed book information to the terminal
                bookDetails.forEach((key, value) -> System.out.println(key + ": " + value));

            } catch (IOException e) {
                Platform.runLater(() -> showAlert("Error", "Failed to retrieve book details.", AlertType.ERROR));
            }
        }).start();
    }

    /**
     * Displays an alert dialog with the provided message.
     *
     * @param title     The title of the alert.
     * @param message   The message of the alert.
     * @param alertType The type of alert.
     */
    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
