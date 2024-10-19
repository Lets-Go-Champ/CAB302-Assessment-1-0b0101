package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.Utility.AlertManager;
import com.example.cab302assessment10b0101.model.Book;
import com.example.cab302assessment10b0101.model.ViewManager;
import com.example.cab302assessment10b0101.views.MenuOptions;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

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
    private Label readingStatusLabel; //Label to display the book's ISBN

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
        mainTitle.setText(book.getTitle()); // Set the main page title to the book title
        publicationDateLabel.setText(book.getPublicationDate()); // Set the publication date label to the book's publication date
        publisherLabel.setText(book.getPublisher()); // Set the publisher label to the book's publisher
        titleLabel.setText(book.getTitle()); // Set the book title label to the book title
        isbnLabel.setText(book.getISBNAsString()); // Set the ISBN label to the book's ISBN
        authorLabel.setText(book.getAuthor()); // Set the author label to the book's author
        pagesLabel.setText(book.getPagesAsString()); // Set the pages label to the book's number of pages
        notesLabel.setText(book.getNotes()); // Set the notes label to the book's notes
        descriptionLabel.setText(book.getDescription()); // Set the description label to the book's description
        bookCoverImage.setImage(book.getImage()); // Set ImageView to the book's cover image.
        readingStatusLabel.setText(book.getReadingStatus()); // Set the reading status label to the book's reading status
    }

    /**
     * Handles the deletion process when the delete button is clicked.
     * <p>
     * This method first checks if a book is selected. If no book is selected, it shows an error alert
     * to inform the user. If a book is selected, a confirmation dialog is presented asking the user
     * to confirm the deletion and reminding them to rescind any associated loans before proceeding.
     * Upon user confirmation, the selected book is deleted, the view is switched back to the "My Books" page,
     * and a success alert is shown to notify the user that the book was deleted.
     * </p>
     *
     */
    public void handleDeleteButtonAction() {
        // Exit the method if there is no book to delete
        if (currentBook == null) {
            AlertManager.getInstance().showAlert("Error", "No book is selected for deletion", Alert.AlertType.ERROR);
            return;
        }

        // Show confirmation dialog
        Optional<ButtonType> result = AlertManager.getInstance().showConfirmation("Delete Book", "Are you sure you want to delete this book?\n\nThis action cannot be undone.");

        // Check if the user clicked OK
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // User clicked OK, proceed with deletion
            DeleteBookController deleteBookController = new DeleteBookController();
            deleteBookController.deleteBook(currentBook);

            // After deletion, switch back to the "My Books" page
            ViewManager.getInstance().getViewFactory().getUserSelectedMenuItem().set(MenuOptions.MYBOOKS);

            // Show success alert
            AlertManager.getInstance().showAlert("Book Deleted", "The book has been successfully deleted.", Alert.AlertType.INFORMATION);
        }
    }

    /**
     * Handles the action of the edit book button. This method is triggered
     * when the user selects to edit a book's details. It updates the current
     * view to the edit book details page.
     *
     * <p>This method utilizes the ViewManager to change the current menu item
     * to the EDITBOOKDETAILS option, allowing the user to modify the selected
     * book's information.</p>
     */
    @FXML
    private void handleEditBookButton(){
        ViewManager.getInstance().getViewFactory().getUserSelectedMenuItem().set(MenuOptions.EDITBOOKDETAILS);
    }
}