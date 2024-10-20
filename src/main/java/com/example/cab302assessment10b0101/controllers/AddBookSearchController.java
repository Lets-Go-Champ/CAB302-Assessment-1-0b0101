package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.Utility.AlertManager;
import com.example.cab302assessment10b0101.Utility.Scraper;
import com.example.cab302assessment10b0101.model.*;
import com.example.cab302assessment10b0101.views.MenuOptions;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Controller class responsible for handling book search, display, and addition to collections.
 * It interacts with the UI, processes user inputs, and updates the display dynamically.
 */
public class AddBookSearchController {

    // FXML elements bound to the UI components in the view
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
    private VBox emptyStateView;
    @FXML
    private VBox addBookForm;
    @FXML
    private Hyperlink addCollectionLink;
    @FXML
    private TextArea notesTextArea;

    // Scraper for fetching book data
    private Scraper scraper;

    // Stores the search results
    private List<Map<String, String>> searchResults;

    // Current index in the search results
    private int currentIndex = 0;

    // Loading symbol used to indicate progress
    @FXML
    private ProgressIndicator progressIndicator;

    /**
     * Navigates to the "Add Collection" page when the link is clicked.
     */
    @FXML
    private void handleAddCollectionLink() {
        // Navigate to the add collection page
        ViewManager.getInstance().getViewFactory().getUserSelectedMenuItem().set(MenuOptions.ADDCOLLECTION);
    }

    /**
     * Initializes the controller by setting up necessary components and event handlers.
     */
    @FXML
    public void initialize() {
        scraper = new Scraper();
        populateCollections();
        setupEventHandler();
        setupBindings();
    }

    /**
     * Populates the collection choice box with the current user's collections.
     */
    private void populateCollections() {
        User currentUser = UserManager.getInstance().getCurrentUser();
        ObservableList<Collection> collections = currentUser.getCollections();

        collectionChoiceBoxSearch.setItems(null);  // Clear old items
        collectionChoiceBoxSearch.setItems(collections);  // Populate with updated collections


        if (!collections.isEmpty()) {
            collectionChoiceBoxSearch.getSelectionModel().selectFirst();  // Select the first one by default
        }
    }

    /**
     * Configures the event handler for the "Add Collection" link.
     */
    private void setupEventHandler() {
        // Set the action for the hyperlink to navigate to the "Add Collection" page
        addCollectionLink.setOnAction(event -> handleAddCollectionLink());
    }

    /**
     * Configures bindings to dynamically show or hide UI elements based on collection availability.
     */
    private void setupBindings() {
        // Bind the visibility of the emptyStateView to whether the collectionChoiceBox has items
        emptyStateView.visibleProperty().bind(Bindings.createBooleanBinding(
                () -> collectionChoiceBoxSearch.getItems().isEmpty(),
                collectionChoiceBoxSearch.getItems()
        ));
        emptyStateView.managedProperty().bind(emptyStateView.visibleProperty());

        // Bind the visibility of the addBookForm to whether the collectionChoiceBox has items
        addBookForm.visibleProperty().bind(Bindings.createBooleanBinding(
                () -> !collectionChoiceBoxSearch.getItems().isEmpty(),
                collectionChoiceBoxSearch.getItems()
        ));
        addBookForm.managedProperty().bind(addBookForm.visibleProperty());
    }

    /**
     * Handles the search operation, querying Google Books using the Scraper.
     */
    @FXML
    public void handleSearchButton() {
        String query = searchTextField.getText().trim();

        if (query.isEmpty()) {
            AlertManager.getInstance().showAlert("Error", "Please enter a search query.", Alert.AlertType.ERROR);
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
                        AlertManager.getInstance().showAlert("Error", "No results found.", Alert.AlertType.ERROR);
                        progressIndicator.setVisible(false);  // Stop progress indicator
                    }
                    searchButton.setDisable(false);
                });
            } catch (IOException e) {
                Platform.runLater(() -> AlertManager.getInstance().showAlert("Error", "Failed to retrieve search results. Please try again.", Alert.AlertType.ERROR));
                progressIndicator.setVisible(false);  // Stop progress indicator in case of error
            }
            searchButton.setDisable(false);
        }).start();
    }

    /**
     * Handles navigation to the next search result in Webscraping.
     */
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

    /**
     * Handles navigation to the previous search result in Webscraping.
     */
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

    /**
     * Loads preloaded book details into the UI components.
     *
     * @param bookDetails A map containing the details of the book to be displayed.
     */
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
        if ( imageBytes != null ) {
            try ( InputStream inputStream = new ByteArrayInputStream(imageBytes) ) {
                bookImageView.setImage(new Image(inputStream));  // Display cached image
            } catch ( IOException e ) {
                AlertManager.getInstance().showAlert("Error: Image Load Failed", "Failed to display the image.", Alert.AlertType.ERROR);
            }
        }
    }

    /**
     * Checks if a collection is selected in the dropdown.
     * @return True if a collection is selected, otherwise false.
     */
    private boolean collectionSelected() {
        return collectionChoiceBoxSearch.getSelectionModel().getSelectedItem() != null;
    }

    /**
     * Handles the event when the user clicks the "Add Book" button.
     * Validates the selection and adds the book to the chosen collection if it doesn't already exist.
     */
    @FXML
    public void handleAddBookButton() {

        // Get the selected collection's name (if any)
        String collectionName;
        if ( collectionSelected() ) { collectionName = collectionChoiceBoxSearch.getSelectionModel().getSelectedItem().getCollectionName(); }
        else { AlertManager.getInstance().showAlert("Error: No Collection", "Please select a collection.", Alert.AlertType.ERROR); return; }

        // Get the selected collection's ID
        int collectionId = CollectionDAO.getInstance().getCollectionsIDByUserAndCollectionName(UserManager.getInstance().getCurrentUser(), collectionName);

        if (searchResults == null || searchResults.isEmpty()) {
            AlertManager.getInstance().showAlert("Error", "No book selected.", Alert.AlertType.ERROR);
            return;
        }

        Collection selectedCollection = collectionChoiceBoxSearch.getSelectionModel().getSelectedItem();
        if (selectedCollection == null) {
            AlertManager.getInstance().showAlert("Error", "No collection selected.", Alert.AlertType.ERROR);
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
            AlertManager.getInstance().showAlert("Error", "A Book with this name already exists in this collection.", Alert.AlertType.ERROR);
            return;  // Stop the process if duplicate is found
        }

        // Use the preloaded details directly to add the book to the database
        String title = bookDetails.get("title");
        String author = bookDetails.get("Author");
        String description = bookDetails.get("Description");
        String publicationDate = bookDetails.get("Publication Date");
        String publisher = bookDetails.get("Publisher");
        String pageCountStr = bookDetails.get("Page Count").trim().replace(",", "");         // Trim the input to remove whitespace and remove commas from page number if any

        // Retrieve the preloaded image bytes from the Scraper (instead of downloading it again)
        byte[] imageBytes = scraper.imageBytesList.get(currentIndex);  // Use cached image bytes

        // Retrieve notes from the TextArea
        String notes = notesTextArea.getText().trim();  // Get the entered notes

        // Create the book object and insert it into the database
        Book newBook = new Book(
                collectionId,
                title,
                isbnStr,
                author,
                description,
                publicationDate,
                publisher,
                Integer.parseInt(pageCountStr),
                notes,
                imageBytes,
                "" // Reading status
        );

        // Insert the book into the database
        BookDAO.getInstance().insert(newBook);

        // Show a success message or reset UI if necessary
        AlertManager.getInstance().showAlert("Success", "Book added Successfully!", Alert.AlertType.INFORMATION);
    }

    /**
     * Displays the book details section after a successful search.
     * Makes relevant UI components visible to the user.
     */
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
}
