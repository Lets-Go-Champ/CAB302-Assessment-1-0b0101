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
                        loadBookDetails(searchResults.get(currentIndex).get("url"));
                        prevButton.setDisable(true);
                        nextButton.setDisable(searchResults.size() <= 1);
                        addBookButton.setDisable(false);
                    } else {
                        showAlert("Error", "No results found.", Alert.AlertType.ERROR);
                    }
                    searchButton.setDisable(false);
                });
            } catch (IOException e) {
                Platform.runLater(() -> showAlert("Error", "Failed to retrieve search results.", Alert.AlertType.ERROR));
                searchButton.setDisable(false);
            }
        }).start();
    }

    private void loadBookDetails(String bookUrl) {
        new Thread(() -> {
            try {
                // Fetch the full book details from the URL
                Map<String, String> bookDetails = scraper.scrapeBookDetails(bookUrl);

                // Get the title from the search result (already scraped in the search method)
                String titleFromSearch = searchResults.get(currentIndex).get("title");  // Get the title from search results

                Platform.runLater(() -> {
                    // Use title from search results for consistency
                    bookTitleLabel.setText("Title: " + titleFromSearch);

                    bookAuthorLabel.setText("Author: " + bookDetails.getOrDefault("Author", "No Author"));
                    bookIsbnLabel.setText("ISBN: " + bookDetails.getOrDefault("ISBN", "No ISBN"));
                    bookPublisherLabel.setText("Publisher: " + bookDetails.getOrDefault("Publisher", "No Publisher"));
                    bookPagesLabel.setText("Pages: " + bookDetails.getOrDefault("Page Count", "No Pages"));
                    bookPublishedDateLabel.setText("Published Date: " + bookDetails.getOrDefault("Publication Date", "No Date"));
                    bookDescriptionLabel.setText("Description: " + bookDetails.getOrDefault("Description", "No Description"));

                    // Load the book cover image
                    String imageUrl = bookDetails.get("imageUrl");
                    byte[] imageBytes = (imageUrl != null && !imageUrl.isEmpty()) ? downloadImage(imageUrl) : loadDefaultImage();

                    // Convert the byte[] to an InputStream and load the image in the ImageView
                    if (imageBytes != null) {
                        try (InputStream inputStream = new ByteArrayInputStream(imageBytes)) {
                            bookImageView.setImage(new Image(inputStream));
                        } catch (IOException e) {
                            System.err.println("Error displaying image: " + e.getMessage());
                        }
                    }
                    // Stop the progress indicator after the UI is updated
                    progressIndicator.setVisible(false);
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
            new Thread(() -> {
                loadBookDetails(searchResults.get(currentIndex).get("url"));

                Platform.runLater(() -> {
                    prevButton.setDisable(currentIndex == 0);
                    nextButton.setDisable(currentIndex == searchResults.size() - 1);

                    // Hide the progress indicator when done
                    progressIndicator.setVisible(false);
                });
            }).start();
        }
    }

    @FXML
    public void handlePrevButton() {
        if (currentIndex > 0) {
            currentIndex--;

            new Thread(() -> {
                loadBookDetails(searchResults.get(currentIndex).get("url"));

                Platform.runLater(() -> {
                    prevButton.setDisable(currentIndex == 0);
                    nextButton.setDisable(currentIndex == searchResults.size() - 1);

                    // Hide the progress indicator when done
                    progressIndicator.setVisible(false);
                });
            }).start();
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

        // Disable the Add Book button and show progress
        addBookButton.setDisable(true);
        progressIndicator.setVisible(true);

        // Get the selected book details
        Map<String, String> bookDetails = searchResults.get(currentIndex);

        // Retrieve the title from the search results (already scraped)
        String titleFromSearch = bookDetails.get("title");

        new Thread(() -> {
            try {
                // Use scrapeBookDetails to fetch the rest of the information from the selectedBookUrl
                Map<String, String> fullBookDetails = scraper.scrapeBookDetails(bookDetails.get("url"));

                // Use the title from the search result, and the rest of the details from scrapeBookDetails
                String title = titleFromSearch;  // We use the title from the initial search
                String isbnStr = fullBookDetails.get("ISBN");
                String author = fullBookDetails.get("Author");
                String description = fullBookDetails.get("Description");
                String publicationDate = fullBookDetails.get("Publication Date");
                String publisher = fullBookDetails.get("Publisher");
                String pageCountStr = fullBookDetails.get("Page Count");

                // Image URL retrieved from the Scraper class
                String imageUrl = fullBookDetails.get("imageUrl");
                byte[] imageBytes;

                // Download the image from Open Library API or set a default image
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    imageBytes = downloadImage(imageUrl);
                } else {
                    imageBytes = loadDefaultImage();
                }

                // Parse page count
                int pages = 0;
                if (pageCountStr != null && !pageCountStr.isEmpty()) {
                    pages = Integer.parseInt(pageCountStr);
                }

                // Use "No Description Found" as the default if description is empty
                if (description == null || description.isEmpty()) {
                    description = "No Description Found";
                }

                // Create the book object and insert it into the database
                Book newBook = new Book(
                        selectedCollection.getId(),
                        title,  // Use the title from the search
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
                    showAlert("Success", "Book added successfully to the collection.", Alert.AlertType.INFORMATION);

                    // Reset the fields after successful addition
                    resetBookDetailsDisplay();

                    addBookButton.setDisable(false);
                    progressIndicator.setVisible(false);
                });

            } catch (IOException e) {
                Platform.runLater(() -> showAlert("Error", "Failed to retrieve book details.", Alert.AlertType.ERROR));
                addBookButton.setDisable(false);
                progressIndicator.setVisible(false);
            } catch (NumberFormatException e) {
                Platform.runLater(() -> showAlert("Error", "Invalid number format in book details.", Alert.AlertType.ERROR));
                addBookButton.setDisable(false);
                progressIndicator.setVisible(false);
            }
        }).start();
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
