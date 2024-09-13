package com.example.cab302assessment10b0101.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class AddBookManuallyController {

    @FXML
    public ComboBox<String> collectionComboBox;

    @FXML
    public TextField titleTextField;

    @FXML
    public TextField isbnTextField;

    @FXML
    public TextField authorTextField;

    @FXML
    public void initialize() {
        // TODO add functionality here
    }

    /**
     * Add the book
     */
    @FXML
    private void handleAddBook() {
        String selectedCollection = collectionComboBox.getValue();
        String title = titleTextField.getText();
        String isbn = isbnTextField.getText();
        String author = authorTextField.getText();

        // Ensure all fields have values
        if ( title.isEmpty() || isbn.isEmpty() || author.isEmpty() || selectedCollection == null ) {
            showAlert("Error", "All fields must be filled out.", AlertType.ERROR);
            return;
        }

        // Save the book
        saveBook(selectedCollection, title, isbn, author);

        // Display a confirmation alert
        showAlert("Success", "Book has been added successfully!", AlertType.INFORMATION);

        // Clear fields after adding the book
        clearFields();
    }

    /**
     * Save the book to the database
     * @param collection the collection the book will be saved to
     * @param title the title of the book
     * @param isbn the isbn of the book
     * @param author the author of the book
     */
    private void saveBook(String collection, String title, String isbn, String author) {

        // TODO: add functionality for populating the database with the book

        // Output the details of the book save
        System.out.println("Saving Book: ");
        System.out.println("Collection: " + collection);
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
        collectionComboBox.getSelectionModel().clearSelection();
        collectionComboBox.getSelectionModel().selectFirst();
    }
}
