package com.example.cab302assessment10b0101.Utility;

import com.example.cab302assessment10b0101.model.*;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import java.util.List;

/**
 * The BookValidation class handles the verification of book credentials. These values are
 * parsed and validated by several error-checking and handling functions.
 */
public class BookValidation {

    // The single instance of BookValidation
    private static BookValidation instance;

    // Private constructor to prevent instantiation
    private BookValidation() {}

    // Get the single instance of BookValidation
    public static BookValidation getInstance() {
        if (instance == null) {
            instance = new BookValidation();
        }
        return instance;
    }


    // Error messages for input validation
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
    final String noReadingStatusMessage = "Please select a reading status.";

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
    public boolean validFields(String title, String isbn, String author, String description, String publisher, String pages, String notes, String readingStatus) {

        if ( title.isEmpty() ) { AlertManager.getInstance().showAlert("Error: No Title", noTitleErrorMessage, Alert.AlertType.ERROR); return false; }
        if ( titleExists(title) ) { AlertManager.getInstance().showAlert("Error: Title Exists", titleExistsMessage, Alert.AlertType.ERROR); return false; }
        if ( isbn.isEmpty() ) { AlertManager.getInstance().showAlert("Error: No ISBN", noISBNMessage, Alert.AlertType.ERROR); return false; }
        if ( !isValidISBN(isbn) ) { AlertManager.getInstance().showAlert("Error: Invalid ISBN", invalidISBNMessage, Alert.AlertType.ERROR); return false; }
        if ( author.isEmpty() ) { AlertManager.getInstance().showAlert("Error: No Author", noAuthorErrorMessage, Alert.AlertType.ERROR); return false; }
        if ( description.isEmpty() ) { AlertManager.getInstance().showAlert("Error: No Description", noDescriptionMessage, Alert.AlertType.ERROR); return false; }
        if ( publisher.isEmpty() ) { AlertManager.getInstance().showAlert("Error: No Publisher", noPublisherMessage, Alert.AlertType.ERROR); return false; }
        if ( pages.isEmpty() ) { AlertManager.getInstance().showAlert("Error: No Page Count", noPagesMessage, Alert.AlertType.ERROR); return false; }
        if ( !isPagesValid(pages) ) { AlertManager.getInstance().showAlert("Error: Invalid Page Count", invalidPagesMessage, Alert.AlertType.ERROR); return false; }
        if ( notes.isEmpty() ) { AlertManager.getInstance().showAlert("Error: No Note", noNoteMessage, Alert.AlertType.ERROR); return false; }
        if ( readingStatus == null || readingStatus.isEmpty() ) { AlertManager.getInstance().showAlert("Error: No Reading Status", noReadingStatusMessage, Alert.AlertType.ERROR); return false; }
        return true;
    }

    /**
     * Validates if the ISBN consists of exactly 10 digits and is numeric.
     * @param isbn The ISBN string.
     * @return True if valid, otherwise false.
     */
    private boolean isValidISBN(String isbn) {
        // Check if ISBN is exactly 10 digits long
        if ( isbn == null || isbn.length() != 10 ) { return false; }

        // Check if all characters in the string are digits
        for ( int i = 0; i < isbn.length(); i++ ) {
            if ( !Character.isDigit(isbn.charAt(i)) ) {
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
        try { int pagesToInt = Integer.parseInt(pages); return ( pagesToInt > 0 ); }
        catch ( Exception e ) { return false; }
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
        for ( Collection collection : userCollections ) {
            ObservableList<Book> books = BookDAO.getInstance().getAllByCollection(collection.getId());
            for ( Book book : books ) { if (book.getTitle().equals(title)) { return true; } }
        }
        return false;
    }
}
