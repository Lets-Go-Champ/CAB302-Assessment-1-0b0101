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
                detailedBookDetails = scraper.scrapeBookDetails(bookUrl);  // Save the detailed info

                Platform.runLater(() -> {
                    bookTitleLabel.setText("Title: " + detailedBookDetails.getOrDefault("title", "No Title"));
                    bookAuthorLabel.setText("Author: " + detailedBookDetails.getOrDefault("Author", "No Author"));
                    bookIsbnLabel.setText("ISBN: " + detailedBookDetails.getOrDefault("ISBN", "No ISBN"));
                    bookPublisherLabel.setText("Publisher: " + detailedBookDetails.getOrDefault("Publisher", "No Publisher"));
                    bookPagesLabel.setText("Pages: " + detailedBookDetails.getOrDefault("Page Count", "No Pages"));
                    bookPublishedDateLabel.setText("Published Date: " + detailedBookDetails.getOrDefault("Publication Date", "No Date"));
                    bookDescriptionLabel.setText("Description: " + detailedBookDetails.getOrDefault("Description", "No Description"));

                    String imageUrl = detailedBookDetails.get("imageUrl");
                    if (imageUrl != null && !imageUrl.isEmpty()) {
                        try {
                            bookImageView.setImage(new Image(imageUrl, true));
                        } catch (Exception e) {
                            bookImageView.setImage(new Image(getClass().getResourceAsStream("/com/example/cab302assessment10b0101/images/Default.jpg")));
                        }
                    } else {
                        bookImageView.setImage(new Image(getClass().getResourceAsStream("/com/example/cab302assessment10b0101/images/Default.jpg")));
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
            loadBookDetails(searchResults.get(currentIndex).get("url"));
            prevButton.setDisable(false);
            if (currentIndex == searchResults.size() - 1) {
                nextButton.setDisable(true);
            }
        }
    }

    @FXML
    public void handlePrevButton() {
        if (currentIndex > 0) {
            currentIndex--;
            loadBookDetails(searchResults.get(currentIndex).get("url"));
            nextButton.setDisable(false);
            if (currentIndex == 0) {
                prevButton.setDisable(true);
            }
        }
    }

    @FXML
    public void handleAddBookButton() {
        if (detailedBookDetails == null) {
            showAlert("Error", "No detailed book information available.", Alert.AlertType.ERROR);
            return;
        }

        Collection selectedCollection = collectionChoiceBoxSearch.getSelectionModel().getSelectedItem();

        if (selectedCollection == null) {
            showAlert("Error", "No collection selected.", Alert.AlertType.ERROR);
            return;
        }

        // Use the detailedBookDetails map for submission
        String title = detailedBookDetails.get("title");
        String isbn = detailedBookDetails.get("ISBN");
        String author = detailedBookDetails.get("Author");
        String description = detailedBookDetails.get("Description");
        String publicationDate = detailedBookDetails.get("Publication Date");
        String publisher = detailedBookDetails.get("Publisher");
        int pages = Integer.parseInt(detailedBookDetails.getOrDefault("Page Count", "0"));
        String notes = notesTextArea.getText();

        String imageUrl = detailedBookDetails.get("imageUrl");
        byte[] imageBytes = (imageUrl != null && !imageUrl.isEmpty()) ? downloadImage(imageUrl) : loadDefaultImage();

        Book newBook = new Book(
                selectedCollection.getId(),
                title,
                isbn,
                author,
                description,
                publicationDate,
                publisher,
                pages,
                notes,
                imageBytes,
                ""
        );

        BookDAO.getInstance().insert(newBook);

        showAlert("Success", "Book added successfully to the collection.", Alert.AlertType.INFORMATION);
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
