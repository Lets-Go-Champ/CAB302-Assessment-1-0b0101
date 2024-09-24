package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.model.Book;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.awt.event.ActionEvent;
import java.util.Optional;

/**
 * The BookDetailsController class is responsible for displaying detailed information about a selected book.
 * It binds the details of a Book object (title, author, description, etc.) to the corresponding UI elements in the view.
 */
public class BookDetailsController {

    //FXML UI components that are linked to the corresponding components in the view
    @FXML
    private Label mainTitle; //Label to display title of the page

    @FXML
    private Label titleLabel; //Label to display the book title

    @FXML
    private Label authorLabel; //Label to display the book author

    @FXML
    private Label descriptionLabel; //Label to display the book description

    @FXML
    private ImageView bookCoverImage; //ImageView to display the book cover image

    @FXML
    private Label publicationDateLabel; //Label to display the book publication date

    @FXML
    private Label publisherLabel; //Label to display the book publisher

    @FXML
    private Label pagesLabel; //Label to display the book's number of pages

    @FXML
    private Label notesLabel; //Label to display book's notes

    @FXML
    private Label isbnLabel; //Label to display the book's ISBN

    @FXML
    private Book currentBook;


    /**
     * Sets the book data of a given book to be displayed in the UI.
     * This method updates each UI element with the corresponding data from the Book object.
     *
     * @param book The Book object whose details are to be displayed.
     */
    public void setData(Book book){
        this.currentBook = book;
        mainTitle.setText(book.getTitle()); //Set the main page title to the book title
        publicationDateLabel.setText(book.getPublicationDate()); //Set the publication date label to the book's publication date
        publisherLabel.setText(book.getPublisher()); //Set the publisher label to the book's publisher
        titleLabel.setText(book.getTitle()); //Set the book title label to the book title
        isbnLabel.setText(book.getISBNAsString()); //Set the ISBN label to the book's ISBN
        authorLabel.setText(book.getAuthor()); //Set the author label to the book's author
        pagesLabel.setText(book.getPagesAsString()); //Set the pages label to the book's number of pages
        notesLabel.setText(book.getNotes()); //Set the notes label to the book's notes
        descriptionLabel.setText(book.getDescription()); //Set the description label to the book's description
        bookCoverImage.setImage(book.getImage()); //Set ImageView to the book's cover image.
    }

    /**
     * Handles the action triggered when the delete button is clicked.
     * <p>
     * This method checks if a book is currently selected for deletion. If no book is selected,
     * it displays an error alert to the user. If a book is selected, it shows a confirmation
     * dialog asking the user to confirm the deletion. If the user confirms, the book is deleted
     * and a success alert is displayed.
     * </p>
     *
     * @param actionEvent The ActionEvent triggered by the delete button click.
     */
    public void handleDeleteButtonAction(javafx.event.ActionEvent actionEvent) {
        if (currentBook == null) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("No book is selected for deletion.");
            errorAlert.showAndWait();
            return;  // Exit the method if there is no book to delete
        }

        // Show confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Book");
        alert.setHeaderText("Are you sure you want to delete this book?");
        alert.setContentText("This action cannot be undone.");

        // Wait for user response
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // User clicked OK, proceed with deletion
            DeleteBookController deleteBookController = new DeleteBookController();
            deleteBookController.deleteBook(currentBook);

            // Show success alert
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Book Deleted");
            successAlert.setHeaderText(null);
            successAlert.setContentText("The book has been successfully deleted.");
            successAlert.showAndWait();
        }
    }
}



