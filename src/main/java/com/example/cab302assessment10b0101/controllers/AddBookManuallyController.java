package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.Utility.AlertManager;
import com.example.cab302assessment10b0101.Utility.BookValidation;
import com.example.cab302assessment10b0101.model.*;
import com.example.cab302assessment10b0101.model.BookDAO;
import com.example.cab302assessment10b0101.views.MenuOptions;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;


/**
 * The AddBookManuallyController class handles the manual addition of a book in the system.
 * It retrieves data from the UI, validates it, and stores the book information in the database.
 */
public class AddBookManuallyController extends BookForm implements Initializable {

    // FXML UI components used for adding book details
    @FXML
    private ChoiceBox<Collection> collectionChoiceBox; // Dropdown for choosing a collection
    @FXML
    private TextField isbnTextField; // Input field for ISBN
    @FXML
    private TextField titleTextField; // Input field for book title
    @FXML
    private TextField authorTextField; // Input field for book author
    @FXML
    private TextField descriptionTextField; // Input field for book description
    @FXML
    private TextField publisherTextField; // Input field for publisher
    @FXML
    private DatePicker dateDatePicker; // Date picker for publication date
    @FXML
    private TextField pagesTextField; // Input field for page count
    @FXML
    private TextField notesTextField; // Input field for user notes
    @FXML
    private Button addBookButton; // Button to add the book
    @FXML
    private Button addImageButton; // Button to upload a book cover image
    @FXML
    private Image image; // Image field for storing the uploaded book cover image
    @FXML
    private ChoiceBox<String> readingStatusChoiceBox; // Dropdown for selecting reading status
    @FXML
    private ProgressIndicator progressIndicator; // ProgressIndicator when uploading an image
    @FXML
    private VBox emptyStateView; // If there are no collections
    @FXML
    private VBox addBookForm; // Content to display if there are collections
    @FXML
    private Hyperlink addCollectionLink; // Hyperlink to add a new collection


    /**
     * Initializes the controller, setting up event handlers and populating the collections and reading lists.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the resource is not specified.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupEventHandlers();
        populateCollections();
        populateReadingStatus();
        setupBindings();
    }

    /**
     * Sets up the event handlers for the buttons on the page.
     */
    private void setupEventHandlers() {
        addImageButton.setOnAction(e -> handleUploadImage()); // Handles image upload
        addBookButton.setOnAction(event -> handleAddBook()); // Handles adding the book
        addCollectionLink.setOnAction(event -> handleAddCollectionLink()); // Handles clicking on add collection link
    }

    /**
     * Handles the image upload process by showing a progress indicator during the upload.
     * <p>This method shows a visual indication (progress indicator) to the user while the image is being uploaded.
     * After the upload process completes, the progress indicator is hidden.</p>
     */
    private void handleUploadImage() {
        progressIndicator.setVisible(true);
        image = uploadImage();
        progressIndicator.setVisible(false);
    }

    /**
     * Handles functionality for when the "Add Book" button is clicked.
     * <p>If validation passes, the book is added to the database. If validation fails, appropriate
     * error messages are shown to the user.</p>
     */
    @FXML
    private void handleAddBook() {

        // Get the selected collection's name (if any)
        String collectionName;
        if ( collectionSelected() ) { collectionName = collectionChoiceBox.getSelectionModel().getSelectedItem().getCollectionName(); }
        else { AlertManager.getInstance().showAlert("Error: No Collection", "Please select a collection.", Alert.AlertType.ERROR); return; }

        // Get the selected collection's ID
        int collectionId = CollectionDAO.getInstance().getCollectionsIDByUserAndCollectionName(UserManager.getInstance().getCurrentUser(), collectionName);

        // Get the remaining input values from the fields
        String title = titleTextField.getText();
        String isbn = isbnTextField.getText();
        String author = authorTextField.getText();
        String description = descriptionTextField.getText();
        String publisher = publisherTextField.getText();
        String pages = pagesTextField.getText();
        String notes = notesTextField.getText();
        String readingStatus = readingStatusChoiceBox.getSelectionModel().getSelectedItem();

        // Ensure that a publication date is selected
        if (dateDatePicker.getValue() == null) {
            AlertManager.getInstance().showAlert("Error: No Date", "Please enter a publication date.", AlertType.ERROR);
            return;
        }

        // Extract date components from dateDatePicker
        String formattedDate = getFormattedDate();

        // Ensure image has been uploaded
        if ( image == null ) { AlertManager.getInstance().showAlert("Error: No image", "Please select a cover image.", Alert.AlertType.ERROR); return; }

        // Validate remaining input fields
        if ( BookValidation.getInstance().validFields(title, true, isbn, author, description, publisher, pages, notes, readingStatus) ) {

            // Save the book to the database
            saveBook(collectionId, title, isbn, author, description, publisher, formattedDate, pages, notes, readingStatus);
            AlertManager.getInstance().showAlert("Success", "Book has been added successfully!", AlertType.INFORMATION);
        }
    }

    /**
     * Retrieves the formatted publication date from the date picker and returns it as a String.
     * The date is formatted in the format "YYYY-MM-DD".
     *
     * @return A String representing the formatted publication date.
     *         The date is in the format "YYYY-MM-DD", where YYYY is the year,
     *         MM is the month (1-12), and DD is the day of the month (1-31).
     *         If the date picker does not have a value selected, this method may throw a NullPointerException.
     */
    private String getFormattedDate() throws NullPointerException {
        LocalDate publicationDate = dateDatePicker.getValue();
        String publicationDay = String.valueOf(publicationDate.getDayOfMonth());
        String publicationMonth = String.valueOf(publicationDate.getMonthValue());
        String publicationYear = String.valueOf(publicationDate.getYear());

        // Format the publication Date as a String (YYYY-MM-DD)
        return publicationYear + "-" + publicationMonth + "-" + publicationDay;
    }

    /**
     * Populates the reading status choice box with options: Unread, Reading, Read.
     */
    private void populateReadingStatus() {
        ObservableList<String> readingStatusOptions = FXCollections.observableArrayList("Unread", "Reading", "Read");
        readingStatusChoiceBox.setItems(readingStatusOptions);
    }

    /**
     * Handles the action for adding a new collection via a hyperlink click.
     */
    private void handleAddCollectionLink(){
        ViewManager.getInstance().getViewFactory().getUserSelectedMenuItem().set(MenuOptions.ADDCOLLECTION);
    }

    /**
     * Populates the collection dropdown with the current user's collections.
     */
    private void populateCollections() {
        User currentUser = UserManager.getInstance().getCurrentUser();
        ObservableList<Collection> collections = currentUser.getCollections();
        collectionChoiceBox.setItems(collections);

        // Populate the choice box with a default selection if collections exist
        if ( !collections.isEmpty() ) {
            collectionChoiceBox.getSelectionModel().selectFirst(); // Set default selection
        }
    }

    /**
     * Sets up bindings for the UI components to display the correct state based on collections.
     */
    private void setupBindings() {
        // Bind the visibility of the empty state view to whether the collectionChoiceBox has items
        emptyStateView.visibleProperty().bind(Bindings.createBooleanBinding(
                () -> collectionChoiceBox.getItems().isEmpty(),
                collectionChoiceBox.getItems()
        ));
        emptyStateView.managedProperty().bind(emptyStateView.visibleProperty());

        // Bind the visibility of the addBookForm to whether the collectionChoiceBox has items
        addBookForm.visibleProperty().bind(Bindings.createBooleanBinding(
                () -> !collectionChoiceBox.getItems().isEmpty(),
                collectionChoiceBox.getItems()
        ));
        addBookForm.managedProperty().bind(addBookForm.visibleProperty());
    }

    /**
     * Checks if a collection is selected in the dropdown.
     * @return True if a collection is selected, otherwise false.
     */
    private boolean collectionSelected() { return collectionChoiceBox.getSelectionModel().getSelectedItem() != null; }

    /**
     * Save the book to the database
     * @param title The title of the book
     * @param isbn The isbn of the book (ID)
     * @param author The author of the book
     * @param description The book's description
     * @param publisher The book's publisher
     * @param publicationDate The date the book was published
     * @param pages The book's page count
     * @param note User defined note regarding the book
     * @param readingStatus The current reading status of the book
     */
    private void saveBook(int collectionId, String title, String isbn, String author, String description,
                          String publisher, String publicationDate, String pages, String note, String readingStatus) {

        // Convert image to byte array
        String imagePath = image.getUrl();
        byte[] imageBytes = imageToBytes(imagePath);

        // If image conversion succeeds create a new book and insert it into the database
        if ( imageBytes.length != 0 ) {
            Book newBook = new Book(collectionId, title, isbn, author, description, publicationDate, publisher, Integer.parseInt(pages), note, imageBytes, readingStatus);
            BookDAO.getInstance().insert(newBook);
            clearFields();
        }


    }

    /**
     * Clears the input fields on the page after the book has saved.
     */
    private void clearFields() {
        titleTextField.clear();
        isbnTextField.clear();
        authorTextField.clear();
        descriptionTextField.clear();
        publisherTextField.clear();
        pagesTextField.clear();
        notesTextField.clear();
    }
}
