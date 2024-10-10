package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.ResourceBundle;

import com.example.cab302assessment10b0101.model.BookDAO;

/**
 * The EditBookDetailsController class manages the user interface for editing book details
 * in the library management system. It handles user input, validates data, and updates
 * book information in the database. The controller is linked to the corresponding FXML
 * file for the edit book screen.
 */
public class EditBookDetailsController implements Initializable {

    // FXML UI elements that are linked to the corresponding elements in the view
    @FXML
    private ChoiceBox<Collection> collectionChoiceBox; //Dropdown for choosing a collection
    @FXML
    private TextField isbnTextField; //Input field for ISBN
    @FXML
    private TextField titleTextField; //Input field for book title
    @FXML
    private TextField authorTextField; //Input field for book author
    @FXML
    private TextField descriptionTextField; //Input field for book description
    @FXML
    private TextField publisherTextField; //Input field for publisher
    @FXML
    private DatePicker dateDatePicker; //Date picker for publication date
    @FXML
    private TextField pagesTextField; //Input field for page count
    @FXML
    private TextField notesTextField; //Input field for user notes
    @FXML
    private Button updateBookButton; //Button to add the book
    @FXML
    private Button addImageButton; //Button to upload a book cover image
    @FXML
    private ChoiceBox<String> readingStatusChoiceBox; // Dropdown for selecting reading status

    private Image image; //Image field for storing the uploaded book cover image
    private byte[] imageBytes; //Image as a byte array for storing/uploading the image

    private String originalTitle; //The original title of the book, before being updated.

    //Error messages for input validation
    final String noCollectionMessage = "Please select a collection.";
    final String noTitleErrorMessage = "Please enter a Title.";
    final String titleExistsMessage = "A book with the given title in your collections already exists. Please enter a unique title.";
    final String noISBNMessage = "Please enter an ISBN.";
    final String invalidISBNMessage = "The ISBN must be 10 digits long and only contain digits 0-9.";
    final String noAuthorErrorMessage = "Please enter an Author.";
    final String noDescriptionMessage = "Please enter a description";
    final String noPublisherMessage = "Please enter a publisher.";
    final String noPagesMessage = "Please enter a page count.";
    final String invalidPagesMessage = "Please enter a valid page count ( >0).";
    final String noNoteMessage = "Please enter a note.";
    final String noImageMessage = "Please select a cover image.";
    final String noImageUploadMessage = "Could not load an image.";
    final String failedImageConversionMessage = "Could note convert the image to a byte array.";
    final String formatDateErrorMessage = "Could not format the date; the date has been reset. Please select a new date";
    final String noReadingStatusMessage = "Please select a reading status.";

    /**
     * Sets the selected collection in the ChoiceBox based on the collection ID.
     *
     * @param collectionID The ID of the collection to set.
     */
    private void setCollectionChoiceBox(int collectionID) {
        for ( Collection collection : CollectionDAO.getInstance().getAll() ) {
            if ( collection.getId() == collectionID ) { collectionChoiceBox.setValue(collection); }
        }
    }
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
    public void initialize(URL url, ResourceBundle resourceBundle){
        setupEventHandlers();
        populateCollections();
        populateReadingStatus();
    }

    /**
     * Sets up the event handlers for the buttons.
     */
    private void setupEventHandlers() {
        addImageButton.setOnAction(e -> handleUploadImage());
        updateBookButton.setOnAction(event -> handleEditBook());
    }

    /**
     * Handles the edit book action when the update button is clicked.
     */
    @FXML
    private void handleEditBook() {

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

        // Ensure all fields have values
        if (validateFields(title, isbn, author, description, publisher, pages, notes, readingStatus)) {

            // Update the book
            updateBook(collectionId, title, isbn, author, description, publisher, formattedDate, pages, notes, readingStatus);
            showAlert("Success", "Book has been updated successfully!", AlertType.INFORMATION);
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
            try { parsedDate = LocalDate.parse(date, formatter); break; }
            catch (DateTimeParseException e) {}
        }

        // If parsedDate is not null, update the DatePicker; otherwise, handle error
        if (parsedDate != null) { setDateDatePicker(parsedDate); }
        else { showAlert("Error: Date Format", formatDateErrorMessage, AlertType.ERROR); }
    }

    /**
     * Populates the collections for the current user
     */
    private void populateCollections() {
        User currentUser = UserManager.getInstance().getCurrentUser();
        ObservableList<Collection> collections = currentUser.getCollections();
        collectionChoiceBox.setItems(collections);

        // Optionally set a default value
        if (!collections.isEmpty()) { collectionChoiceBox.getSelectionModel().selectFirst(); }
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
     * Determines if all the fields entered for a book are valid
     * @param title The title of the book
     * @param isbn The ISBN of the book
     * @param author The author of the book
     * @param description The description of the book
     * @param publisher The publisher of the book
     * @param pages The book's page count
     * @param notes The user added notes for the book
     * @return True if all fields are valid, False otherwise
     */
    private boolean validateFields(String title, String isbn, String author, String description,
                                   String publisher, String pages, String notes, String readingStatus) {

        if ( !collectionSelected() ) { showAlert("Error: No Collection", noCollectionMessage, AlertType.ERROR); return false; }
        if ( title.isEmpty() ) { showAlert("Error: No Title", noTitleErrorMessage, AlertType.ERROR); return false; }
        if ( titleExists(title) ) { showAlert("Error: Title Exists", titleExistsMessage, AlertType.ERROR); return false; }
        if ( isbn.isEmpty() ) { showAlert("Error: No ISBN", noISBNMessage, AlertType.ERROR); return false; }
        if ( !isValidISBN(isbn) ) { showAlert("Error: Invalid ISBN", invalidISBNMessage, AlertType.ERROR); return false; }
        if ( author.isEmpty() ) { showAlert("Error: No Author", noAuthorErrorMessage, AlertType.ERROR); return false; }
        if ( description.isEmpty() ) { showAlert("Error: No Description", noDescriptionMessage, AlertType.ERROR); return false; }
        if ( publisher.isEmpty() ) { showAlert("Error: No Publisher", noPublisherMessage, AlertType.ERROR); return false; }
        if ( pages.isEmpty() ) { showAlert("Error: No Page Count", noPagesMessage, AlertType.ERROR); return false; }
        if ( !isPagesValid(pages) ) { showAlert("Error: Invalid Page Count", invalidPagesMessage, AlertType.ERROR); return false; }
        if ( notes.isEmpty() ) { showAlert("Error: No Note", noNoteMessage, AlertType.ERROR); return false; }
        if ( image == null ) { showAlert("Error: No image", noImageMessage, AlertType.ERROR); return false; }
        if (readingStatus == null || readingStatus.isEmpty()) { showAlert("Error: No Reading Status", noReadingStatusMessage, AlertType.ERROR); return false; }
        return true;
    }

    /**
     * Validates if the ISBN consists of exactly 10 or 13 digits and is numeric.
     *
     * @param isbn The ISBN string.
     * @return True if valid, otherwise false.
     */
    private boolean isValidISBN(String isbn) {
        // Check if ISBN is exactly 10 or 13 digits long
        if (isbn == null || (isbn.length() != 10 && isbn.length() != 13)) {
            return false;
        }
        // Check if all characters in the string are digits
        for (int i = 0; i < isbn.length(); i++) {
            if (!Character.isDigit(isbn.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the provided pages value is valid (greater than zero)
     * @param pages The page count to validate
     * @return True if pages is a valid number greater than zero, False otherwise
     */
    private boolean isPagesValid(String pages) {
        try { int pagesToInt = Integer.parseInt(pages); return ( pagesToInt > 0 );}
        catch (Exception e ) { return false; }
    }

    /**
     * Determines if the given title is already assigned to a Book in the Users Books
     * @param title The new title for a book
     * @return True if title is assigned to another book; false otherwise
     */
    private boolean titleExists(String title) {
        User currentUser = UserManager.getInstance().getCurrentUser();
        List<Collection> userCollections = CollectionDAO.getInstance().getCollectionsByUser(currentUser);

        if ( !title.equals(originalTitle) ) {
            // Iterate over each book in the User's collection to determine if the title is in use
            for (Collection collection : userCollections) {
                ObservableList<Book> books = BookDAO.getInstance().getAllByCollection(collection.getId());
                for (Book book : books) { if (book.getTitle().equals(title)) { return true; } }
            }
        }
        return false;
    }

    /**
     * Checks if a collection is selected in the ChoiceBox
     * @return True if a collection is selected, False otherwise
     */
    private boolean collectionSelected() {
        return collectionChoiceBox.getSelectionModel().getSelectedItem() != null;
    }

    /**
     * Handles the upload of an image file and sets it as the book's cover image.
     * If the upload fails, an alert is shown to the user.
     */
    private void handleUploadImage() {
        try {
            // Create a window to upload the image
            FileChooser fileChooser = new FileChooser();
            Stage dialogStage = new Stage();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("JPG Files", "*.jpg"),
                    new FileChooser.ExtensionFilter("PNG Files", "*.png")
            );
            File selectedFile = fileChooser.showOpenDialog(dialogStage);

            // Save the contents of the image
            image = new Image(String.valueOf(selectedFile));
            String imagePath = image.getUrl();
            imageBytes = imageToBytes(imagePath);

            // Display the image that was uploaded
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(300);
            imageView.setFitHeight(400);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Image Loaded Successfully!");
            alert.setHeaderText("The selected image was successfully loaded.");
            alert.setGraphic(imageView);
            alert.showAndWait();

        } catch (Exception e) { showAlert("Error", noImageUploadMessage, AlertType.ERROR);}
    }

    /**
     * Handles turning an image into a BLOB for input into the database.
     * If the upload fails, an alert is shown to the user.
     */
    private byte[] imageToBytes(String imagePath) {
        try {
            Path path = Paths.get(imagePath);
            return Files.readAllBytes(path);
        } catch (Exception e) {showAlert("Error", failedImageConversionMessage, AlertType.ERROR); return new byte[0];}
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

        Book newBook = new Book(collectionId, title, isbn, author, description, publicationDate, publisher, Integer.parseInt(pages), note, imageBytes, readingStatus);
        BookDAO.getInstance().update(newBook, originalTitle);
        clearFields();
    }

    /**
     * Shows an alert dialog with the specified type, title, and message.
     * @param title     The title of the alert dialog.
     * @param message   The message content of the alert dialog.
     * @param alertType The type of alert.
     */
    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
