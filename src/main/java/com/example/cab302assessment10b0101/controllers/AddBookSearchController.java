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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.io.IOUtils;

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
                    byte[] imageBytes = (imageUrl != null && !imageUrl.isEmpty()) ? downloadImage(imageUrl) : loadDefaultImage();
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
        // Use the preloaded details to update the UI without making new API calls
        bookTitleLabel.setText("Title: " + bookDetails.get("title"));
        bookAuthorLabel.setText("Author: " + bookDetails.getOrDefault("Author", "No Author"));
        bookIsbnLabel.setText("ISBN: " + bookDetails.getOrDefault("ISBN", "No ISBN"));
        bookPublisherLabel.setText("Publisher: " + bookDetails.getOrDefault("Publisher", "No Publisher"));
        bookPagesLabel.setText("Pages: " + bookDetails.getOrDefault("Page Count", "No Pages"));
        bookPublishedDateLabel.setText("Published Date: " + bookDetails.getOrDefault("Publication Date", "No Date"));
        bookDescriptionLabel.setText("Description: " + bookDetails.getOrDefault("Description", "No Description"));

        // Load the image in a similar way using bookDetails.get("imageUrl")
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

        // Use the preloaded details directly to add the book to the database
        String title = bookDetails.get("title");
        String isbnStr = bookDetails.get("ISBN");
        String author = bookDetails.get("Author");
        String description = bookDetails.get("Description");
        String publicationDate = bookDetails.get("Publication Date");
        String publisher = bookDetails.get("Publisher");
        String pageCountStr = bookDetails.get("Page Count");

        // Handle image download or set default image
        byte[] imageBytes; // Declare the variable here
        String imageUrl = bookDetails.get("imageUrl");

        if (imageUrl != null && !imageUrl.isEmpty()) {
            imageBytes = downloadImage(imageUrl);  // Download the image
        } else {
            imageBytes = loadDefaultImage();  // Load default image if no URL found
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
                Integer.parseInt(pageCountStr),
                "", // Notes
                imageBytes,
                "" // Reading status
        );

        BookDAO.getInstance().insert(newBook); // Insert the book into the database
    }


    /**
     * Resets the book details display and disables navigation buttons.
     */
    private void resetBookDetailsDisplay() {
        bookTitleLabel.setText("Title: ");
        bookAuthorLabel.setText("Author: ");
        bookIsbnLabel.setText("ISBN: ");
        bookPublisherLabel.setText("Publisher: ");
        bookPagesLabel.setText("Pages: ");
        bookPublishedDateLabel.setText("Published Date: ");
        bookDescriptionLabel.setText("Description: ");
        bookImageView.setImage(null);  // Optionally reset the image
        notesTextArea.clear();  // Clear any notes
        prevButton.setDisable(true);  // Disable navigation buttons
        nextButton.setDisable(true);
        addBookButton.setDisable(true);  // Disable add book button until a new search is done
    }


    private byte[] downloadImage(String imageUrl) {
        try (InputStream inputStream = new URL(imageUrl).openStream()) {
            BufferedImage bufferedImage = ImageIO.read(inputStream);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", baos);
            return baos.toByteArray();
        } catch (IOException e) {
            System.err.println("Error downloading image: " + e.getMessage());
            return loadDefaultImage();  // Load the default image if downloading fails
        }
    }

    /**
     * Loads a default image in case downloading the cover image fails.
     */
    private byte[] loadDefaultImage() {
        try {
            InputStream is = getClass().getResourceAsStream("/com/example/cab302assessment10b0101/images/Default.jpg");
            return IOUtils.toByteArray(is);
        } catch (IOException e) {
            System.err.println("Error loading default image: " + e.getMessage());
            return null;
        }
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
