package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.Scraper;
import com.example.cab302assessment10b0101.model.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;


public class AddBookSearchController {

    @FXML
    private TextField searchTextField;

    @FXML
    private Button searchButton, prevButton, nextButton, addBookButton;

    @FXML
    private ChoiceBox<Collection> collectionChoiceBoxSearch;

    @FXML
    private ImageView bookImageView;

    @FXML
    private Label bookTitleLabel, bookAuthorLabel, bookIsbnLabel, bookPublisherLabel, bookPagesLabel, bookPublishedDateLabel, bookDescriptionLabel;

    @FXML
    private VBox bookDetailsContainer;

    @FXML
    private Label searchResultsLabel;

    @FXML
    private Label addNoteLabel;

    @FXML
    private TextArea notesTextArea;

    private Scraper scraper;

    private List<Map<String, String>> searchResults;

    private Map<String, String> detailedBookDetails;  // Store the detailed book details here

    private int currentIndex = 0;

    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    public void initialize() {
        scraper = new Scraper();

        prevButton.setDisable(true);
        nextButton.setDisable(true);
        addBookButton.setDisable(true);

        populateCollections();
    }

    private void populateCollections() {
        User currentUser = UserManager.getInstance().getCurrentUser();
        List<Collection> collections = CollectionDAO.getInstance().getCollectionsByUser(currentUser);

        ObservableList<Collection> observableCollections = FXCollections.observableArrayList(collections);
        collectionChoiceBoxSearch.setItems(observableCollections);

        if (!observableCollections.isEmpty()) {
            collectionChoiceBoxSearch.getSelectionModel().selectFirst();
        }
    }

    @FXML
    public void handleSearchButton() {
        String query = searchTextField.getText().trim();

        if (query.isEmpty()) {
            showAlert("Error", "Please enter a search query.", Alert.AlertType.ERROR);
            return;
        }

        searchButton.setDisable(true);
        progressIndicator.setVisible(true);

        new Thread(() -> {
            try {
                searchResults = scraper.scrapeGoogleBooks(query);

                Platform.runLater(() -> {
                    if (!searchResults.isEmpty()) {
                        currentIndex = 0;
                        loadPreloadedBookDetails(searchResults.get(currentIndex));  // Load the first book's details

                        // Make the hidden sections visible after search
                        bookDetailsContainer.setVisible(true);
                        searchResultsLabel.setVisible(true);
                        addNoteLabel.setVisible(true);
                        notesTextArea.setVisible(true);
                        prevButton.setVisible(true);
                        nextButton.setVisible(searchResults.size() > 1);  // Only show 'Next' if more than 1 result
                        addBookButton.setVisible(true);
                        showBookDetails();
                        prevButton.setDisable(true);
                        nextButton.setDisable(searchResults.size() <= 1);
                        addBookButton.setDisable(false);

                        progressIndicator.setVisible(false);  // Stop progress indicator

                    } else {
                        showAlert("Error", "No results found.", Alert.AlertType.ERROR);
                        progressIndicator.setVisible(false);  // Stop progress indicator

                    }
                    searchButton.setDisable(false);
                });
            } catch (IOException e) {
                Platform.runLater(() -> showAlert("Error", "Failed to retrieve search results.", Alert.AlertType.ERROR));
                progressIndicator.setVisible(false);  // Stop progress indicator in case of error
            }
            searchButton.setDisable(false);
        }).start();
    }

    private void loadBookDetails(String bookUrl) {
        if (bookUrl == null || bookUrl.isEmpty()) {
            System.err.println("Error: Attempting to load a null or empty book URL.");
            return;
        }

        System.out.println("Loading book details for URL: " + bookUrl);

        new Thread(() -> {
            try {
                Map<String, String> bookDetails = scraper.scrapeBookDetails(bookUrl);

                Platform.runLater(() -> {
                    // Update the UI with the details
                    bookTitleLabel.setText("Title: " + bookDetails.get("title"));
                    bookAuthorLabel.setText("Author: " + bookDetails.getOrDefault("Author", "No Author"));
                    bookIsbnLabel.setText("ISBN: " + bookDetails.getOrDefault("ISBN", "No ISBN"));
                    bookPublisherLabel.setText("Publisher: " + bookDetails.getOrDefault("Publisher", "No Publisher"));
                    bookPagesLabel.setText("Pages: " + bookDetails.getOrDefault("Page Count", "No Pages"));
                    bookPublishedDateLabel.setText("Published Date: " + bookDetails.getOrDefault("Publication Date", "No Date"));
                    bookDescriptionLabel.setText("Description: " + bookDetails.getOrDefault("Description", "No Description"));

                    // Load the image
                    String imageUrl = bookDetails.get("imageUrl");
                    byte[] imageBytes = (imageUrl != null && !imageUrl.isEmpty()) ? scraper.downloadImage(imageUrl) : scraper.loadDefaultImage();
                    if (imageBytes != null) {
                        try (InputStream inputStream = new ByteArrayInputStream(imageBytes)) {
                            bookImageView.setImage(new Image(inputStream));
                        } catch (IOException e) {
                            System.err.println("Error displaying image: " + e.getMessage());
                        }
                    }
                });
            } catch (IOException e) {
                Platform.runLater(() -> showAlert("Error", "Failed to retrieve book details.", Alert.AlertType.ERROR));
            }
        }).start();
    }

    @FXML
    public void handleNextButton() {
        if (currentIndex < searchResults.size() - 1) {
            currentIndex++;
            loadPreloadedBookDetails(searchResults.get(currentIndex));

            // Enable the prev button when we're not on the first book anymore
            prevButton.setDisable(false);

            // Disable the next button if we're on the last book
            nextButton.setDisable(currentIndex == searchResults.size() - 1);
        }
    }

    @FXML
    public void handlePrevButton() {
        if (currentIndex > 0) {
            currentIndex--;
            loadPreloadedBookDetails(searchResults.get(currentIndex));

            // Enable/disable next button appropriately
            nextButton.setDisable(false);  // Re-enable the next button if moving back
            prevButton.setDisable(currentIndex == 0);  // Disable prev button if on the first book
        }
    }

    private void loadPreloadedBookDetails(Map<String, String> bookDetails) {
        // Use the preloaded details to update the UI
        bookTitleLabel.setText("Title: " + bookDetails.get("title"));
        bookAuthorLabel.setText("Author: " + bookDetails.getOrDefault("Author", "No Author"));
        bookIsbnLabel.setText("ISBN: " + bookDetails.getOrDefault("ISBN", "No ISBN"));
        bookPublisherLabel.setText("Publisher: " + bookDetails.getOrDefault("Publisher", "No Publisher"));
        bookPagesLabel.setText("Pages: " + bookDetails.getOrDefault("Page Count", "No Pages"));
        bookPublishedDateLabel.setText("Published Date: " + bookDetails.getOrDefault("Publication Date", "No Date"));
        bookDescriptionLabel.setText("Description: " + bookDetails.getOrDefault("Description", "No Description"));

        // Load the preloaded image bytes from Scraper's imageBytesList
        byte[] imageBytes = scraper.imageBytesList.get(currentIndex);  // Get image bytes using the current index
        if (imageBytes != null) {
            try (InputStream inputStream = new ByteArrayInputStream(imageBytes)) {
                bookImageView.setImage(new Image(inputStream));  // Display cached image
            } catch (IOException e) {
                System.err.println("Error displaying image: " + e.getMessage());
            }
        }
    }

    @FXML
    public void handleAddBookButton() {
        if (searchResults == null || searchResults.isEmpty()) {
            showAlert("Error", "No book selected.", Alert.AlertType.ERROR);
            return;
        }

        Collection selectedCollection = collectionChoiceBoxSearch.getSelectionModel().getSelectedItem();
        if (selectedCollection == null) {
            showAlert("Error", "No collection selected.", Alert.AlertType.ERROR);
            return;
        }

        // Get the preloaded book details
        Map<String, String> bookDetails = searchResults.get(currentIndex);

        String isbnStr = bookDetails.get("ISBN");

        // Fetch all books from the selected collection only
        ObservableList<Book> booksInCollection = BookDAO.getInstance().getAllByCollection(selectedCollection.getId());

        // Check if a book with the same ISBN already exists in the selected collection
        boolean isDuplicate = booksInCollection.stream().anyMatch(book -> book.getISBN().equals(isbnStr));

        if (isDuplicate) {
            showAlert("Error", "A book with the same ISBN already exists in this collection!", Alert.AlertType.ERROR);
            return;  // Stop the process if duplicate is found
        }

        // Use the preloaded details directly to add the book to the database
        String title = bookDetails.get("title");
        String author = bookDetails.get("Author");
        String description = bookDetails.get("Description");
        String publicationDate = bookDetails.get("Publication Date");
        String publisher = bookDetails.get("Publisher");
        String pageCountStr = bookDetails.get("Page Count");

        // Retrieve the preloaded image bytes from the Scraper (instead of downloading it again)
        byte[] imageBytes = scraper.imageBytesList.get(currentIndex);  // Use cached image bytes

        // Create the book object and insert it into the database
        Book newBook = new Book(
                selectedCollection.getId(),
                title,
                isbnStr,
                author,
                description,
                publicationDate,
                publisher,
                Integer.parseInt(pageCountStr),
                "", // Notes
                imageBytes,
                "" // Reading status
        );

        // Insert the book into the database
        BookDAO.getInstance().insert(newBook);

        // Show a success message or reset UI if necessary
        showAlert("Success", "Book added successfully!", Alert.AlertType.INFORMATION);
    }

    private void showBookDetails() {
        // Set all the labels and text area to visible after search
        bookTitleLabel.setVisible(true);
        bookAuthorLabel.setVisible(true);
        bookPublisherLabel.setVisible(true);
        bookIsbnLabel.setVisible(true);
        bookPagesLabel.setVisible(true);
        bookPublishedDateLabel.setVisible(true);
        bookDescriptionLabel.setVisible(true);

        // Make notes section visible
        addNoteLabel.setVisible(true);
        notesTextArea.setVisible(true);
    }

    /**
     * Displays an alert dialog with the provided message.
     */
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
