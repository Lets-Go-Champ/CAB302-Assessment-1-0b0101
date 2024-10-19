package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.Utility.AlertManager;
import com.example.cab302assessment10b0101.Utility.BookValidation;
import com.example.cab302assessment10b0101.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

/**
 * The EditBookDetailsController class manages the user interface for editing book details
 * in the library management system. It handles user input, validates data, and updates
 * book information in the database. The controller is linked to the corresponding FXML
 * file for the edit book screen.
 */
public class EditBookDetailsController extends BookForm implements Initializable {

    // FXML UI elements that are linked to the corresponding elements in the view
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
    private Button updateBookButton; // Button to add the book
    @FXML
    private Button addImageButton; // Button to upload a book cover image
    @FXML
    private ChoiceBox<String> readingStatusChoiceBox; // Dropdown for selecting reading status
    @FXML
    private ProgressIndicator progressIndicator; // ProgressIndicator when uploading an image
    @FXML
    private Image image; // Image field for storing the uploaded book cover image
    private byte[] imageBytes; // Image as a byte array for storing/uploading the image
    private String originalTitle; // The original title of the book, before being updated.

    // Sets the fields with the specified values
    private void setIsbnTextField(String isbn) { isbnTextField.setText(isbn); }
    private void setTitleTextField(String title) { titleTextField.setText(title); }
    private void setAuthorTextField(String author) { authorTextField.setText(author); }
    private void setDescriptionTextField(String description) { descriptionTextField.setText(description); }
    private void setPublisherTextField(String publisher) { publisherTextField.setText(publisher); }
    private void setDateDatePicker(LocalDate date) { dateDatePicker.setValue(date); }
    private void setPagesTextField(String pages) { pagesTextField.setText(pages); }
    private void setNotesTextField(String notes) { notesTextField.setText(notes); }
    private void setCoverImage(Image coverImage) { image = coverImage; }
    private void setImageBytes(byte[] imageBytes) { this.imageBytes = imageBytes; }

    /**
     * Initializes the controller, setting up event handlers and populating the collections list.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupEventHandlers();
        populateCollections();
        populateReadingStatus();
    }

    /**
     * Sets up the event handlers for the buttons.
     */
    private void setupEventHandlers() {
        addImageButton.setOnAction(event -> handleUpdateImage());
        updateBookButton.setOnAction(event -> handleEditBook());
    }

    /**
     * Handles the update image action when the update image button is clicked.
     */
    private void handleUpdateImage() {
        progressIndicator.setVisible(true);
        Image newImage = uploadImage();
        progressIndicator.setVisible(false);
        if ( newImage != null ) { setCoverImage(newImage); setImageBytes(imageToBytes(image.getUrl())); }
    }

    /**
     * Handles the edit book action when the update button is clicked.
     */
    @FXML
    private void handleEditBook() {

        // Get the input values from the fields
        String collectionName = collectionChoiceBox.getSelectionModel().getSelectedItem().getCollectionName();
        int collectionId = CollectionDAO.getInstance().getCollectionsIDByUserAndCollectionName(UserManager.getInstance().getCurrentUser(), collectionName);
        String title = titleTextField.getText();
        String isbn = isbnTextField.getText();
        String author = authorTextField.getText();
        String description = descriptionTextField.getText();
        String publisher = publisherTextField.getText();
        String pages = pagesTextField.getText();
        String notes = notesTextField.getText();
        String readingStatus = readingStatusChoiceBox.getSelectionModel().getSelectedItem();

        // Format the publication Date as a String (YYYY-MM-DD)
        String publicationDay = String.valueOf(dateDatePicker.getValue().getDayOfMonth());
        String publicationMonth = String.valueOf(dateDatePicker.getValue().getMonthValue());
        String publicationYear = String.valueOf(dateDatePicker.getValue().getYear());
        String formattedDate = publicationYear + "-" +publicationMonth + "-" + publicationDay;

        // Pre-Validate fields (increase processing speed)
        if ( image == null ) { AlertManager.getInstance().showAlert("Error: No image", "Please select a cover image.", Alert.AlertType.ERROR); return; }

        // Ensure all remaining fields have values
        boolean isNewTitle = ( !title.equals(originalTitle) );
        if ( BookValidation.getInstance().validFields(title, isNewTitle, isbn, author, description, publisher, pages, notes, readingStatus) ) {
            // Update the book
            updateBook(collectionId, title, isbn, author, description, publisher, formattedDate, pages, notes, readingStatus);
            AlertManager.getInstance().showAlert("Success", "Book has been updated successfully!", AlertType.INFORMATION);
        }
    }

    /**
     * Formats a given date string as a LocalDate with formatting for multiple date formats.
     * The date picker is updated to display this value.
     * @param date The string date of form YYYY-M(M)-D(D)
     */
    private void formatDate(String date) {
        // Define an array of possible date formats
        DateTimeFormatter[] formatters = new DateTimeFormatter[]{
                DateTimeFormatter.ofPattern("yyyy-M-d"),   // Format for yyyy-M-D
                DateTimeFormatter.ofPattern("yyyy-M-dd"),  // Format for yyyy-M-DD
                DateTimeFormatter.ofPattern("yyyy-MM-d"),  // Format for yyyy-MM-D
                DateTimeFormatter.ofPattern("yyyy-MM-dd")  // Format for yyyy-MM-DD
        };
        LocalDate parsedDate = null;

        // Try each formatter in the array
        for (DateTimeFormatter formatter : formatters) {
            try {
                parsedDate = LocalDate.parse(date, formatter);
                break;  // Exit the loop once a date is successfully parsed
            } catch (DateTimeParseException ignored) {
                // Catch the exception but don't alert immediately
            }
        }

        // If parsedDate is null, it means none of the formatters worked
        if (parsedDate == null) {
            // Show alert only once if no formatter works
            AlertManager.getInstance().showAlert("Error: Date Format", "Date tied to book formatted incorrectly; Date has been reset.\n\nPlease select a new date.", Alert.AlertType.ERROR);
        } else {
            // If the date was parsed successfully, update the DatePicker
            setDateDatePicker(parsedDate);
        }
    }

    /**
     * Populates the fields with the book's existing details
     */
    public void populateFields(Book book) {
        originalTitle = book.getTitle();
        setTitleTextField(book.getTitle());
        setCollectionChoiceBox(book.getCollectionId());
        setIsbnTextField(book.getISBN());
        setAuthorTextField(book.getAuthor());
        setDescriptionTextField(book.getDescription());
        setPublisherTextField(book.getPublisher());
        formatDate(book.getPublicationDate());
        setPagesTextField(Integer.toString(book.getPages()));
        setNotesTextField(book.getNotes());
        setCoverImage(book.getImage());
        setImageBytes(book.getBytes());
        setReadingStatusChoiceBox(book.getReadingStatus());
    }

    /**
     * Populates the collections for the current user
     */
    private void populateCollections() {
        User currentUser = UserManager.getInstance().getCurrentUser();
        ObservableList<Collection> collections = currentUser.getCollections();
        collectionChoiceBox.setItems(collections);

        // Optionally set a default value
        if ( !collections.isEmpty() ) { collectionChoiceBox.getSelectionModel().selectFirst(); }
    }

    /**
     * Populates the reading status combo box with options: Unread, Reading, Read.
     */
    private void populateReadingStatus() {
        ObservableList<String> readingStatusOptions = FXCollections.observableArrayList("Unread", "Reading", "Read");
        readingStatusChoiceBox.setItems(readingStatusOptions);
    }

    /**
     * Populates the reading status choice box with the reading status from the database
     */
    private void setReadingStatusChoiceBox(String readingStatus) {
        readingStatusChoiceBox.getSelectionModel().select(readingStatus);
    }

    /**
     * Sets the selected collection in the ChoiceBox based on the collection ID.
     * @param collectionID The ID of the collection to set.
     */
    private void setCollectionChoiceBox(int collectionID) {
        for ( Collection collection : CollectionDAO.getInstance().getAll() ) {
            if ( collection.getId() == collectionID ) { collectionChoiceBox.setValue(collection); }
        }
    }


    /**
     * Updates the book in the database
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
    private void updateBook(int collectionId, String title, String isbn, String author, String description,
                            String publisher, String publicationDate, String pages, String note, String readingStatus) {

        // Update the book in the database
        Book newBook = new Book(collectionId, title, isbn, author, description, publicationDate, publisher, Integer.parseInt(pages), note, imageBytes, readingStatus);
        BookDAO.getInstance().update(newBook, originalTitle);
        clearFields();
    }

    /**
     * Resets the input fields once a book has been updated.
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