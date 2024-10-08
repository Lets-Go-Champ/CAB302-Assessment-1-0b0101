package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.Scraper;
import com.example.cab302assessment10b0101.model.*;
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
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import java.io.InputStream;
import java.net.URL;
import java.io.IOException;
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
     * Downloads an image from a given URL using Apache Commons IO and returns it as a byte array.
     *
     * @param imageUrl The URL of the image to download.
     * @return A byte array representing the downloaded image.
     * @throws IOException If an error occurs during the download process.
     */
    private byte[] downloadImage(String imageUrl) throws IOException {
        try (InputStream inputStream = new URL(imageUrl).openStream()) {
            return IOUtils.toByteArray(inputStream); // Download the image and convert it to a byte array
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

        // Get the title and image from the selected search result in searchResultsListView
        int selectedIndex = searchResultsListView.getSelectionModel().getSelectedIndex();
        String titleFromSearch = searchResults.get(selectedIndex).get("title");
        String imageUrlFromSearch = searchResults.get(selectedIndex).get("imageUrl"); // Image URL from scrapeGoogleBooks

        // Run the process for scraping the rest of the book details on a background thread
        new Thread(() -> {
            try {
                // Use scrapeBookDetails to fetch the rest of the information from the selectedBookUrl
                Map<String, String> bookDetails = scraper.scrapeBookDetails(selectedBookUrl);

                // Use the title from the search result, and the rest of the details from scrapeBookDetails
                String title = titleFromSearch;  // Preserving the title from scrapeGoogleBooks
                String isbnStr = bookDetails.get("ISBN");
                String author = bookDetails.get("Author");
                String description = bookDetails.get("Description");

                String publicationDate = bookDetails.get("Publication Date");
                // Handle null or empty publication date
                if (publicationDate == null || publicationDate.isEmpty()) {
                    publicationDate = "Unknown";
                }

                String publisher = bookDetails.get("Publisher");
                String pageCountStr = bookDetails.get("Page Count");
                byte[] imageBytes;

                // Debugging: Print the book details being processed
                System.out.println("Book Details:");
                System.out.println("Title: " + title);
                System.out.println("ISBN: " + isbnStr);
                System.out.println("Author: " + author);
                System.out.println("Description: " + description);
                System.out.println("Publication Date: " + publicationDate);
                System.out.println("Publisher: " + publisher);
                System.out.println("Page Count: " + pageCountStr);
                System.out.println("Image URL: " + imageUrlFromSearch);

                // Handle null values for title
                if (title == null || title.isEmpty()) {
                    Platform.runLater(() -> showAlert("Error", "Failed to retrieve the book title.", AlertType.ERROR));
                    return;
                }

                // Parse ISBN and page count, handle invalid formats
                int isbn = 0; // Default to 0 if parsing fails
                int pages = 0; // Default to 0 if parsing fails

                try {
                    if (isbnStr != null && !isbnStr.isEmpty()) {
                        isbn = Integer.parseInt(isbnStr);  // Now it can be handled as a string
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing ISBN: " + isbnStr);
                }

                try {
                    if (pageCountStr != null && !pageCountStr.isEmpty()) {
                        pages = Integer.parseInt(pageCountStr);
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing page count: " + pageCountStr);
                }

                // Handle image: if imageUrl is not empty, download it; otherwise, use default image
                if (imageUrlFromSearch != null && !imageUrlFromSearch.isEmpty()) {
                    imageBytes = downloadImage(imageUrlFromSearch); // Use a helper method to download the image
                } else {
                    imageBytes = loadDefaultImage(); // Use default image if scraping fails
                }

                // Use "No Description Found" as the default if description is empty
                if (description == null || description.isEmpty()) {
                    description = "No Description Found";
                }

                // Create the book object and insert it into the database
                Book newBook = new Book(
                        selectedCollection.getId(),
                        title,  // Title from search result
                        isbn,
                        author,
                        description,
                        publicationDate,
                        publisher,
                        pages,
                        "", // Notes are empty for now
                        imageBytes,
                        "" // Reading status not set
                );

                BookDAO.getInstance().insert(newBook); // Insert the book into the database

                // Update UI on the main thread
                Platform.runLater(() -> {
                    showAlert("Success", "Book added successfully to the collection.", AlertType.INFORMATION);
                    searchResultsListView.getItems().clear();
                });

            } catch (IOException e) {
                Platform.runLater(() -> showAlert("Error", "Failed to retrieve book details.", AlertType.ERROR));
            } catch (NumberFormatException e) {
                Platform.runLater(() -> showAlert("Error", "Invalid number format in book details.", AlertType.ERROR));
            }
        }).start();
    }


    private byte[] loadDefaultImage() {
        try {
            // Load the default image from resources
            InputStream is = getClass().getResourceAsStream("/com/example/cab302assessment10b0101/images/Default.jpg");

            if (is == null) {
                System.err.println("Default image not found in resources.");
                return null;
            }

            return is.readAllBytes();
        } catch (IOException e) {
            System.err.println("Error loading default image: " + e.getMessage());
            return null; // Return null if there's an error loading the default image
        }
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
