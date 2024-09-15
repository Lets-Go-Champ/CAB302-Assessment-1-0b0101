package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.model.Collection;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;


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
    public Button addBookButton;

    // Define error messages
    String noTitleErrorMessage = "Please enter a Title.";
    String noISBNMessage = "Please enter an ISBN.";
    String invalidISBNMessage = "The ISBN must only contain digits 0-9";
    String noAuthorErrorMessage = "Please enter an Author.";
    String noDescriptionMessage = "Please enter a description";
    String noPublisherMessage = "Please enter a publisher.";
    String noDateMessage = "Please enter a publication date (DD/MM/YYYY).";
    String noPagesMessage = "Please enter a page count.";
    String invalidPagesMessage = "Please enter a valid page count ( >0).";

    @FXML
    public void initialize() {
        // TODO add functionality here
        setupEventHandlers();
    }

    private void setupEventHandlers() {
        // TODO add functionality for ChoiceBox
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
        String pages = pagesTextField.getText();

        // Ensure that a date is selected
        try {
            int publishDay = dateDatePicker.getValue().getDayOfMonth();
            int publishMonth = dateDatePicker.getValue().getMonthValue();
            int publishYear = dateDatePicker.getValue().getYear();
        } catch (Exception e) {
            showAlert("Error: No Date", noDateMessage, AlertType.ERROR); return; }

        int publishDay = dateDatePicker.getValue().getDayOfMonth();
        int publishMonth = dateDatePicker.getValue().getMonthValue();
        int publishYear = dateDatePicker.getValue().getYear();

        // Ensure all fields have values and that the book is valid
        if (validateFields(title, isbn, author, description, publisher, publishDay, publishMonth, publishYear, pages)) {
            // TODO Ensure that the book does not already exist
            // TODO Save the book

            // Display a confirmation alert
            showAlert("Success", "Book has been added successfully!", AlertType.INFORMATION);

            // Clear fields after adding the book
            // TODO clearFields();
        }
    }

    /**
     * Determines if all the fields entered by are valid
     * @param title The title of the book
     * @param isbn The ISBN of the book
     * @param author The author of the book
     * @param description The description of the book
     * @param publisher The publisher of the book
     * @param publishDay The day the book was published
     * @param publisherMonth The month the book was published
     * @param publishYear The year the book was published
     * @param pages The book's page count
     * @return True if all fields are valid, False otherwise
     */
    private boolean validateFields(String title, String isbn, String author, String description, String publisher,
                                   int publishDay, int publisherMonth, int publishYear, String pages) {

        if (title.isEmpty()) {showAlert("Error: No Title", noTitleErrorMessage, AlertType.ERROR); return false;}
        if (isbn.isEmpty()) {showAlert("Error: No ISBN", noISBNMessage, AlertType.ERROR); return false;}
        if (!isValidISBN(isbn)) {showAlert("Error: Invalid ISBN", invalidISBNMessage, AlertType.ERROR); return false;}
        if (author.isEmpty()) {showAlert("Error: No Author", noAuthorErrorMessage, AlertType.ERROR); return false;}
        if (description.isEmpty()) {showAlert("Error: No Description", noDescriptionMessage, AlertType.ERROR); return false;}
        if (publisher.isEmpty()) {showAlert("Error: No Publisher", noPublisherMessage, AlertType.ERROR); return false;}
        if (pages.isEmpty()) {showAlert("Error: No Page Count", noPagesMessage, AlertType.ERROR); return false;}
        if (!isPagesValid(pages)) {showAlert("Error: Invalid Page Count", invalidPagesMessage, AlertType.ERROR); return false;}
        return true;
    }

    private boolean isValidISBN(String isbn) {
        try { int isbnToInt = Integer.parseInt(isbn); }
        catch (Exception e ) { return false; }

        // TODO functionality for validating an ISBN
        return true;
    }

    private boolean isPagesValid(String pages) {
        try { int pagesToInt = Integer.parseInt(pages); return ( pagesToInt > 0 );}
        catch (Exception e ) { return false; }
    }

    /**
     * Save the book to the database
     * @param collection the collection the book will be saved to
     * @param title the title of the book
     * @param isbn the isbn of the book
     * @param author the author of the book
     */
    private void saveBook(String collection, String title, String isbn, String author) {

        // TODO update to include new fields
        // TODO add functionality for populating the database with the book

        // Output the details of the book save
        System.out.println("Saving Book: ");
        // System.out.println("Collection: " + collection);
        System.out.println("Title: " + title);
        System.out.println("ISBN: " + isbn);
        System.out.println("Author: " + author);
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
