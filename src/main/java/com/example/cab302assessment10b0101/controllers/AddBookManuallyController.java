package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.model.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import com.example.cab302assessment10b0101.model.BookDAO;

public class AddBookManuallyController {

    @FXML
    private ChoiceBox<Collection> collectionChoiceBox;
    @FXML
    private TextField isbnTextField;
    @FXML
    private TextField titleTextField;
    @FXML
    private TextField authorTextField;
    @FXML
    private TextField descriptionTextField;
    @FXML
    private TextField publisherTextField;
    @FXML
    private DatePicker dateDatePicker;
    @FXML
    private TextField pagesTextField;
    @FXML
    private TextField notesTextField;
    @FXML
    private Button addBookButton;
    @FXML
    private Button addImageButton;
    @FXML
    private Image image;

    // Define error messages
    final String noCollectionMessage = "Please select a collection.";
    final String noTitleErrorMessage = "Please enter a Title.";
    final String noISBNMessage = "Please enter an ISBN.";
    final String invalidISBNMessage = "The ISBN must only contain digits 0-9";
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


    @FXML
    public void initialize() {
        setupEventHandlers();
        populateCollections();
    }


    private void setupEventHandlers() {
        addImageButton.setOnAction(e -> handleUploadImage());
        addBookButton.setOnAction(event -> handleAddBook());
    }


    @FXML
    private void handleAddBook() {
        //System.out.println("\nAdding Book...");
        String collectionName = collectionChoiceBox.getSelectionModel().getSelectedItem().getCollectionName();
        //System.out.println("Collection Name = " + collectionName);
        int collectionId = CollectionDAO.getInstance().getCollectionsIDByUserAndCollectionName(UserManager.getInstance().getCurrentUser(), collectionName);

        //System.out.println("CollectionID = " + collectionId);
        if ( collectionId == -1 ) {
            System.out.println("No such collection Id"); return;
        }

        System.out.println("collection ID = " + collectionId);
        String title = titleTextField.getText();
        String isbn = isbnTextField.getText();
        String author = authorTextField.getText();
        String description = descriptionTextField.getText();
        String publisher = publisherTextField.getText();
        LocalDate publicationDate = dateDatePicker.getValue();
        String pages = pagesTextField.getText();
        String notes = notesTextField.getText();

        // Ensure that a date is selected
        try { publicationDate.getDayOfMonth();}
        catch (Exception e) { showAlert("Error: No Date", noDateMessage, AlertType.ERROR); return; }

        // Format the publication Date as a String (YYYY-MM-DD)
        String publicationDay = String.valueOf(dateDatePicker.getValue().getDayOfMonth());
        String publicationMonth = String.valueOf(dateDatePicker.getValue().getMonthValue());
        String publicationYear = String.valueOf(dateDatePicker.getValue().getYear());
        String formattedDate = publicationYear + "-" +publicationMonth + "-" + publicationDay;

        // Ensure all fields have values
        if (validateFields(title, isbn, author, description, publisher, pages, notes)) {

            // Save the book
            saveBook(collectionId, title, isbn, author, description, publisher, formattedDate, pages, notes);
            showAlert("Success", "Book has been added successfully!", AlertType.INFORMATION);
        }
    }

    private void populateCollections() {
        User currentUser = UserManager.getInstance().getCurrentUser();
        ObservableList<Collection> collections = currentUser.getCollections();
        collectionChoiceBox.setItems(collections);

        // Optionally set a default value
        if (!collections.isEmpty()) { collectionChoiceBox.getSelectionModel().selectFirst(); }
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
                                   String publisher, String pages, String notes) {

        if ( !collectionSelected() ) { showAlert("Error: No Collection", noCollectionMessage, AlertType.ERROR); return false; }
        if ( title.isEmpty() ) { showAlert("Error: No Title", noTitleErrorMessage, AlertType.ERROR); return false; }
        if ( isbn.isEmpty() ) { showAlert("Error: No ISBN", noISBNMessage, AlertType.ERROR); return false; }
        if ( !isValidISBN(isbn) ) { showAlert("Error: Invalid ISBN", invalidISBNMessage, AlertType.ERROR); return false; }
        if ( author.isEmpty() ) { showAlert("Error: No Author", noAuthorErrorMessage, AlertType.ERROR); return false; }
        if ( description.isEmpty() ) { showAlert("Error: No Description", noDescriptionMessage, AlertType.ERROR); return false; }
        if ( publisher.isEmpty() ) { showAlert("Error: No Publisher", noPublisherMessage, AlertType.ERROR); return false; }
        if ( pages.isEmpty() ) { showAlert("Error: No Page Count", noPagesMessage, AlertType.ERROR); return false; }
        if ( !isPagesValid(pages) ) { showAlert("Error: Invalid Page Count", invalidPagesMessage, AlertType.ERROR); return false; }
        if ( notes.isEmpty() ) { showAlert("Error: No Note", noNoteMessage, AlertType.ERROR); return false; }
        if ( image == null ) { showAlert("Error: No image", noImageMessage, AlertType.ERROR); return false; }
        return true;
    }

    private boolean isValidISBN(String isbn) {
        try { Integer.parseInt(isbn); }
        catch (Exception e ) { return false; }

        // Function assumes that an integer value is a valid ISBN
        // Functionality for further validating and ISBN can be implemented in future
        return true;
    }

    private boolean isPagesValid(String pages) {
        try { int pagesToInt = Integer.parseInt(pages); return ( pagesToInt > 0 );}
        catch (Exception e ) { return false; }
    }

    private boolean collectionSelected() {
        return collectionChoiceBox.getSelectionModel().getSelectedItem() != null;
    }

    private void handleUploadImage() {
        try {

            // FileChooser for uploading a book image.
            FileChooser fileChooser = new FileChooser();

            // Create a window to upload the image
            Stage dialogStage = new Stage();

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
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Image Loaded Successfully!");
            alert.setHeaderText("The selected image was successfully loaded.");
            alert.setGraphic(imageView);
            alert.showAndWait();

        } catch (Exception e) { showAlert("Error", noImageUploadMessage, AlertType.ERROR);}
    }

    private byte[] imageToBytes(String imagePath) {
        try {
            Path path = Paths.get(imagePath);
            return Files.readAllBytes(path);
        } catch (Exception e) {showAlert("Error", failedImageConversionMessage, AlertType.ERROR); return new byte[0];}
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
     */
    private void saveBook(int collectionId, String title, String isbn, String author, String description,
                          String publisher, String publicationDate, String pages, String note) {

        String imagePath = image.getUrl();
        byte[] imageBytes = imageToBytes(imagePath);

        if (imageBytes.length != 0) {
            Book newBook = new Book(collectionId, title,  Integer.parseInt(isbn), author, description, publicationDate, publisher, Integer.parseInt(pages), note, imageBytes);
            BookDAO.getInstance().insert(newBook);

            // Print the results to console for testing:
            System.out.println("Book Saved Successfully! Details: " + "\n" +
                    "Collection ID: " + collectionId + "\n" +
                    "ISBN: " + isbn + "\n" +
                    "Title: " + title + "\n" +
                    "Author: " + author + "\n" +
                    "Description: " + description + "\n" +
                    "Publication Date: " + publicationDate + "\n" +
                    "Publisher: " + publisher + "\n" +
                    "Pages: " + pages + "\n" +
                    "Note: " + note + "\n" +
                    "Image: " + imageBytes.toString()
            );
            clearFields();
        }
    }

    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

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
