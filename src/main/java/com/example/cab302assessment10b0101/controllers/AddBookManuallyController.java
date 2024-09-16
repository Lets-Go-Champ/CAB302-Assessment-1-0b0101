package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import java.time.LocalDate;


public class AddBookManuallyController {

    @FXML
    public ChoiceBox<Collection> collectionChoiceBox;
    public TextField isbnTextField;
    public TextField titleTextField;
    public TextField authorTextField;
    public TextField descriptionTextField;
    public TextField publisherTextField;
    public DatePicker dateDatePicker;
    public TextField pagesTextField;
    public TextField notesTextField;
    public Button addBookButton;


    // Define error messages
    String noTitleErrorMessage = "Please enter a Title.";
    String noISBNMessage = "Please enter an ISBN.";
    String invalidISBNMessage = "The ISBN must only contain digits 0-9";
    String noAuthorErrorMessage = "Please enter an Author.";
    String noDescriptionMessage = "Please enter a description";
    String noPublisherMessage = "Please enter a publisher.";
    String noDateMessage = "Please enter a publication date.";
    String noPagesMessage = "Please enter a page count.";
    String invalidPagesMessage = "Please enter a valid page count ( >0).";
    String noNoteMessage = "Please eneter a note.";
    String bookExistsMessage = "The book with the given ISBN already exists.";

    // Declare DAO for interacting with Book Database
    private final BookDAO bookDAO = new BookDAO();

    // Collection for testing
    Collection testCollection = new Collection(1, "test", "test");

    // TODO include IMAGE functionality.


    @FXML
    public void initialize() {
        // TODO add functionality here
        setupEventHandlers();
    }

    private void setupEventHandlers() {
        // TODO add functionality for Collections ChoiceBox
        addBookButton.setOnAction(event -> handleAddBook());
    }

    /**
     * Manages adding the book to a collection
     */
    @FXML
    private void handleAddBook() {

        // TODO implement functionality for the collection choice box

        // String collection = collectionChoiceBox.getValue().getCollectionName();
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

        // Format the publication Date as a string
        String publicationDay = String.valueOf(dateDatePicker.getValue().getDayOfMonth());
        String publicationMonth = String.valueOf(dateDatePicker.getValue().getMonthValue());
        String publicationYear = String.valueOf(dateDatePicker.getValue().getYear());
        String formattedDate = publicationDay + "-" +publicationMonth + "-" + publicationYear;


        // Ensure all fields have values and that the book is valid
        if (validateFields(title, isbn, author, description, publisher, pages, notes)) {
            // Ensure that the book does not already exist
            if (bookExists(isbn)) { showAlert("Error: Book Already Exists", bookExistsMessage, AlertType.ERROR); }
            else {
                saveBook(testCollection, title, isbn, author, description, publisher, formattedDate, pages, notes);

                // Display a confirmation alert
                showAlert("Success", "Book has been added successfully!", AlertType.INFORMATION);

                // Clear fields after adding the book
                // TODO clearFields();
            }
        }
    }

    /**
     * Determines if all the fields entered by are valid
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

        if (title.isEmpty()) {showAlert("Error: No Title", noTitleErrorMessage, AlertType.ERROR); return false;}
        if (isbn.isEmpty()) {showAlert("Error: No ISBN", noISBNMessage, AlertType.ERROR); return false;}
        if (!isValidISBN(isbn)) {showAlert("Error: Invalid ISBN", invalidISBNMessage, AlertType.ERROR); return false;}
        if (author.isEmpty()) {showAlert("Error: No Author", noAuthorErrorMessage, AlertType.ERROR); return false;}
        if (description.isEmpty()) {showAlert("Error: No Description", noDescriptionMessage, AlertType.ERROR); return false;}
        if (publisher.isEmpty()) {showAlert("Error: No Publisher", noPublisherMessage, AlertType.ERROR); return false;}
        if (pages.isEmpty()) {showAlert("Error: No Page Count", noPagesMessage, AlertType.ERROR); return false;}
        if (!isPagesValid(pages)) {showAlert("Error: Invalid Page Count", invalidPagesMessage, AlertType.ERROR); return false;}
        if (notes.isEmpty()) {showAlert("Error: No Note", noNoteMessage, AlertType.ERROR); return false;}
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

    /**
     * Determines if the book already exists in the Database.
     * @param id The ISBN of the book
     * @return True if the book exists, false otherwise.
     */
    private boolean bookExists(String id) {
        // Double check this functionality - seems like it is not working as intended.
        return bookDAO.getAll().stream().anyMatch(book -> String.valueOf(book.getId()).equalsIgnoreCase(id));
    }

    /**
     * * Save the book to the database
     * @param collection The collection the book will be saved to
     * @param title The title of the book
     * @param isbn The isbn of the book (ID)
     * @param author The author of the book
     * @param description The book's description
     * @param publisher The book's publisher
     * @param publicationDate The date the book was published
     * @param pages The book's page count
     * @param note User defined note regarding the book
     */
    private void saveBook(Collection collection, String title, String isbn, String author, String description,
                          String publisher, String publicationDate, String pages, String note) {

        byte[] image = new byte[0]; // Fix this when implementing image grabbing.
        Book newBook = new Book(Integer.parseInt(isbn), title, author, description, publicationDate, publisher, Integer.parseInt(pages), note, image);
        bookDAO.insert(newBook);

        // Print the results to console for testing:
        System.out.println("Book Saved Successfully! Details: " + "\n" +
                            "ISBN: " + isbn + "\n" +
                            "Title: " + title + "\n" +
                            "Author: " + author + "\n" +
                            "Description: " + description + "\n" +
                            "Publication Date: " + publicationDate + "\n" +
                            "Publisher: " + publisher + "\n" +
                            "Pages: " + pages + "\n" +
                            "Note: " + note + "\n");
        /** Test Code
        for (Book book : bookDAO.getAll() ) {
           System.out.println("ID: " + book.getId() + "Title: " + book.getTitle() + "PubDate: " + book.getPublicationDate());
        }*/
    }

    /**
     * Show an alert dialog for a given message
     */
    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Clears all input fields after adding a book
     */
    private void clearFields() {
        titleTextField.clear();
        isbnTextField.clear();
        authorTextField.clear();
        // collectionChoiceBox.getSelectionModel().clearSelection();
        // collectionChoiceBox.getSelectionModel().selectFirst();
    }
}
