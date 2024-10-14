package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.Alert.AlertManager;
import com.example.cab302assessment10b0101.model.*;
import com.example.cab302assessment10b0101.views.MenuOptions;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import com.example.cab302assessment10b0101.model.BookDAO;

/**
 * The AddBookManuallyController class handles the manual addition of a book in the system.
 * It retrieves data from the UI, validates it, and stores the book information in the database.
 */
public class AddBookManuallyController implements Initializable {



    //FXML UI components used for adding book details
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
    private VBox emptyStateView; //if there are no collections

    @FXML
    private VBox addBookForm; //content to display if there are collections

    @FXML
    private Hyperlink addCollectionLink;

    //Error messages for input validation
    final String noCollectionMessage = "Please select a collection.";
    final String noTitleErrorMessage = "Please enter a Title.";
    final String titleExistsMessage = "A book with the given title in your collections already exists. Please enter a unique title.";
    final String noISBNMessage = "Please enter an ISBN.";
    final String invalidISBNMessage = "The ISBN must be 10 digits long and only contain digits 0-9.";
    final String noAuthorErrorMessage = "Please enter an Author.";
    final String noDescriptionMessage = "Please enter a description";
    final String noPublisherMessage = "Please enter a publisher.";
    final String noDateMessage = "Please enter a publication date.";
    final String noPagesMessage = "Please enter a page count.";
    final String invalidPagesMessage = "Please enter a valid page count ( >0).";
    final String noNoteMessage = "Please enter a note.";
    final String noImageMessage = "Please select a cover image.";
    final String noImageUploadMessage = "Could not load an image.";
    final String failedImageConversionMessage = "Could note convert the image to a byte array.";
    final String noReadingStatusMessage = "Please select a reading status.";

    /**
     * Initializes the controller, setting up event handlers and populating the collections list.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        setupEventHandlers();
        populateCollections();
        populateReadingStatus();
        setupBindings();
    }

    /**
     * Sets up the event handlers for the buttons on the page.
     */
    private void setupEventHandlers() {
        addImageButton.setOnAction(e -> handleUploadImage()); //Handles image upload
        addBookButton.setOnAction(event -> handleAddBook()); //Handles adding the book
        addCollectionLink.setOnAction(event -> handleAddCollectionLink()); //Handles clicking on add collection link
    }

    /**
     * Handles the logic when the "Add Book" button is clicked.
     * Validates the user's input and saves the book to the database.
     */
    @FXML
    private void handleAddBook() {
        //Get the selected collection's name
        String collectionName = collectionChoiceBox.getSelectionModel().getSelectedItem().getCollectionName();
        //Get the selected collection's ID
        int collectionId = CollectionDAO.getInstance().getCollectionsIDByUserAndCollectionName(UserManager.getInstance().getCurrentUser(), collectionName);

        if ( collectionId == -1 ) {
            System.out.println("No such collection Id"); return;
        }

        //Get the input values from the fields
        String title = titleTextField.getText();
        String isbn = isbnTextField.getText();
        String author = authorTextField.getText();
        String description = descriptionTextField.getText();
        String publisher = publisherTextField.getText();
        LocalDate publicationDate = dateDatePicker.getValue();
        String pages = pagesTextField.getText();
        String notes = notesTextField.getText();
        String readingStatus = readingStatusChoiceBox.getSelectionModel().getSelectedItem();

        // Ensure that a date is selected
        try { publicationDate.getDayOfMonth();}
        catch (Exception e) { AlertManager.getInstance().showAlert("Error: No Date", noDateMessage, AlertType.ERROR); return; }

        // Format the publication Date as a String (YYYY-MM-DD)
        String publicationDay = String.valueOf(dateDatePicker.getValue().getDayOfMonth());
        String publicationMonth = String.valueOf(dateDatePicker.getValue().getMonthValue());
        String publicationYear = String.valueOf(dateDatePicker.getValue().getYear());
        String formattedDate = publicationYear + "-" +publicationMonth + "-" + publicationDay;

        // Validate all input fields
        if (validateFields(title, isbn, author, description, publisher, pages, notes, readingStatus)) {

            // Save the book to the database
            saveBook(collectionId, title, isbn, author, description, publisher, formattedDate, pages, notes, readingStatus);
            AlertManager.getInstance().showAlert("Success", "Book has been added successfully!", AlertType.INFORMATION);
        }
    }

    /**
     * Populates the reading status choice box with options: Unread, Reading, Read.
     */
    private void populateReadingStatus() {
        ObservableList<String> readingStatusOptions = FXCollections.observableArrayList("Unread", "Reading", "Read");
        readingStatusChoiceBox.setItems(readingStatusOptions);
    }

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

        // Populate the choice box if collections exist
        if (!collections.isEmpty()) {
            collectionChoiceBox.getSelectionModel().selectFirst(); // Set default selection
        }
    }

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
     * Validates if all the field values entered for a book are valid
     * @param title The title of the book
     * @param isbn The ISBN of the book
     * @param author The author of the book
     * @param description The description of the book
     * @param publisher The publisher of the book
     * @param pages The book's page count
     * @param notes The user added notes for the book
     * @param readingStatus The current reading status of the book
     * @return True if all fields are valid, False otherwise
     */
    private boolean validateFields(String title, String isbn, String author, String description,
                                   String publisher, String pages, String notes, String readingStatus) {

        if ( !collectionSelected() ) { AlertManager.getInstance().showAlert("Error: No Collection", noCollectionMessage, AlertType.ERROR); return false; }
        if ( title.isEmpty() ) { AlertManager.getInstance().showAlert("Error: No Title", noTitleErrorMessage, AlertType.ERROR); return false; }
        if ( titleExists(title) ) { AlertManager.getInstance().showAlert("Error: Title Exists", titleExistsMessage, AlertType.ERROR); return false; }
        if ( isbn.isEmpty() ) { AlertManager.getInstance().showAlert("Error: No ISBN", noISBNMessage, AlertType.ERROR); return false; }
        if ( !isValidISBN(isbn) ) { AlertManager.getInstance().showAlert("Error: Invalid ISBN", invalidISBNMessage, AlertType.ERROR); return false; }
        if ( author.isEmpty() ) { AlertManager.getInstance().showAlert("Error: No Author", noAuthorErrorMessage, AlertType.ERROR); return false; }
        if ( description.isEmpty() ) { AlertManager.getInstance().showAlert("Error: No Description", noDescriptionMessage, AlertType.ERROR); return false; }
        if ( publisher.isEmpty() ) { AlertManager.getInstance().showAlert("Error: No Publisher", noPublisherMessage, AlertType.ERROR); return false; }
        if ( pages.isEmpty() ) { AlertManager.getInstance().showAlert("Error: No Page Count", noPagesMessage, AlertType.ERROR); return false; }
        if ( !isPagesValid(pages) ) { AlertManager.getInstance().showAlert("Error: Invalid Page Count", invalidPagesMessage, AlertType.ERROR); return false; }
        if ( notes.isEmpty() ) { AlertManager.getInstance().showAlert("Error: No Note", noNoteMessage, AlertType.ERROR); return false; }
        if ( image == null ) { AlertManager.getInstance().showAlert("Error: No image", noImageMessage, AlertType.ERROR); return false; }
        if (readingStatus == null || readingStatus.isEmpty()) { AlertManager.getInstance().showAlert("Error: No Reading Status", noReadingStatusMessage, AlertType.ERROR); return false; }
        return true;
    }

    /**
     * Validates if the ISBN consists of exactly 10 digits and is numeric.
     *
     * @param isbn The ISBN string.
     * @return True if valid, otherwise false.
     */
    private boolean isValidISBN(String isbn) {
        // Check if ISBN is exactly 10 digits long
        if (isbn == null || isbn.length() != 10) {
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
     * Validates if the pages count is a positive integer.
     *
     * @param pages The page count as a string.
     * @return True if valid, otherwise false.
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

        // Iterate over each book in the User's collection to determine if the title is in use
        for (Collection collection : userCollections) {
            ObservableList<Book> books = BookDAO.getInstance().getAllByCollection(collection.getId());
            for (Book book : books) { if (book.getTitle().equals(title)) { return true; } }
        }
        return false;
    }

    /**
     * Checks if a collection is selected in the dropdown.
     *
     * @return True if a collection is selected, otherwise false.
     */
    private boolean collectionSelected() {
        return collectionChoiceBox.getSelectionModel().getSelectedItem() != null;
    }

    /**
     * Handles the image upload process.
     */
    private void handleUploadImage() {
        try {

            // FileChooser for uploading a book image.
            FileChooser fileChooser = new FileChooser();

            // Create a window to upload the image
            Stage dialogStage = new Stage();

            //Set allowed file types
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("JPG Files", "*.jpg"),
                    new FileChooser.ExtensionFilter("PNG Files", "*.png")
            );
            File selectedFile = fileChooser.showOpenDialog(dialogStage);

            // Display the image that was uploaded
            image = new Image(String.valueOf(selectedFile));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(300);
            imageView.setFitHeight(400);

            //Display success message with an image preview
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Image Loaded Successfully!");
            alert.setHeaderText("The selected image was successfully loaded.");
            alert.setGraphic(imageView);
            alert.showAndWait();

        } catch (Exception e) {
            AlertManager.getInstance().showAlert("Error", noImageUploadMessage, AlertType.ERROR);
        }
    }

    /**
     * Converts the image file at the specified path to a byte array.
     *
     * @param imagePath The file path of the image.
     * @return A byte array of the image or an empty array if conversion fails.
     */
    private byte[] imageToBytes(String imagePath) {
        try {
            Path path = Paths.get(imagePath);
            return Files.readAllBytes(path);
        } catch (Exception e) {
            AlertManager.getInstance().showAlert("Error", failedImageConversionMessage, AlertType.ERROR); return new byte[0];
        }
    }

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

        //Convert image to byte array
        String imagePath = image.getUrl();
        byte[] imageBytes = imageToBytes(imagePath);

        //If image conversion succeeds create a new book and insert it into the database
        if (imageBytes.length != 0) {
            Book newBook = new Book(collectionId, title,  isbn, author, description, publicationDate, publisher, Integer.parseInt(pages), note, imageBytes, readingStatus);
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
