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
import java.net.URL;
import javafx.scene.image.Image;
import org.apache.commons.io.output.ByteArrayOutputStream;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

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
     * Downloads an image from a given URL and returns it as a byte array.
     * If the image is too small (e.g., below a certain width or height threshold),
     * or if an error occurs during the download process, the default image is returned instead.
     *
     * @param imageUrl The URL of the image to download.
     * @return A byte array representing the downloaded image or the default image if the downloaded image is too small or an error occurs.
     * @throws IOException If an error occurs during the download process.
     */
    private byte[] downloadImage(String imageUrl) throws IOException {
        try (InputStream inputStream = new URL(imageUrl).openStream()) {
            // Read the image from the InputStream
            BufferedImage bufferedImage = ImageIO.read(inputStream);

            // Check if the image is too small (due to API size bug)
            if (bufferedImage != null && (bufferedImage.getWidth() < 50 || bufferedImage.getHeight() < 50)) {
                System.out.println("Image too small, using default image.");
                return loadDefaultImage();  // Load and return the default image
            }

            // If the image is large enough, return its byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", baos);
            return baos.toByteArray();
        } catch (IOException e) {
            System.err.println("Error downloading image: " + e.getMessage());
            return loadDefaultImage();  // Use default image if there's an error
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

        // Get the title from the selected search result in searchResultsListView
        int selectedIndex = searchResultsListView.getSelectionModel().getSelectedIndex();
        String titleFromSearch = searchResults.get(selectedIndex).get("title");

        // Run the process for scraping the rest of the book details on a background thread
        new Thread(() -> {
            try {
                // Use scrapeBookDetails to fetch the rest of the information from the selectedBookUrl
                Map<String, String> bookDetails = scraper.scrapeBookDetails(selectedBookUrl);

                // Use the title from the search result, and the rest of the details from scrapeBookDetails
                String title = titleFromSearch;
                String isbnStr = bookDetails.get("ISBN");
                String author = bookDetails.get("Author");
                String description = bookDetails.get("Description");
                String publicationDate = bookDetails.get("Publication Date");
                String publisher = bookDetails.get("Publisher");
                String pageCountStr = bookDetails.get("Page Count");

                // Image URL retrieved from the Scraper class
                String imageUrl = bookDetails.get("imageUrl");
                byte[] imageBytes;

                // Download the image from Open Library API or set a default image
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    imageBytes = downloadImage(imageUrl);
                } else {
                    imageBytes = loadDefaultImage();
                }

                // Parse page count
                int pages = 0;

                try {
                    if (pageCountStr != null && !pageCountStr.isEmpty()) {
                        pages = Integer.parseInt(pageCountStr);
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing page count: " + pageCountStr);
                }

                // Use "No Description Found" as the default if description is empty
                if (description == null || description.isEmpty()) {
                    description = "No Description Found";
                }

                // Create the book object and insert it into the database
                Book newBook = new Book(
                        selectedCollection.getId(),
                        title,
                        isbnStr,
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

    /**
     * Loads a default image in case downloading the cover image fails.
     *
     * @return A byte array representing the default image.
     */
    private byte[] loadDefaultImage() {
        try {
            InputStream is = getClass().getResourceAsStream("/com/example/cab302assessment10b0101/images/Default.jpg");
            if (is == null) {
                System.err.println("Default image not found in resources.");
                return null;
            }
            return IOUtils.toByteArray(is);
        } catch (IOException e) {
            System.err.println("Error loading default image: " + e.getMessage());
            return null;
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
