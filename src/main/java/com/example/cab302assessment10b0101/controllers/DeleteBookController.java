package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.Utility.AlertManager;
import com.example.cab302assessment10b0101.model.*;
import com.example.cab302assessment10b0101.views.MenuOptions;
import javafx.scene.control.Alert;
import java.sql.SQLException;

/**
 * Controller class responsible for managing the deletion of books.
 * <p>
 * This class utilizes the BookDAO to perform delete operations on book records.
 * It ensures that proper checks are in place before attempting to delete a book.
 * </p>
 */
public class DeleteBookController {

    /**
     * Constructs a new DeleteBookController instance.
     */
    public DeleteBookController() {
    }

    /**
     * Deletes a book from the database.
     * <p>
     * This method attempts to delete a book based on its title. If the provided
     * book object is null, an IllegalArgumentException is thrown. It logs the
     * outcome of the deletion attempt and handles any SQL exceptions that may occur.
     * </p>
     *
     * @param book The Book object to be deleted. It must not be null.
     */
    public void deleteBook(Book book) {

        try {
            BookDAO.getInstance().deleteBook(book.getTitle());

            ViewManager.getInstance().getViewFactory().getUserSelectedMenuItem().set(MenuOptions.MYBOOKS);
        } catch ( SQLException e ) {
            AlertManager.getInstance().showAlert("Error: Deletion Failed", "Failed to delete the selected book.", Alert.AlertType.ERROR);
        }
    }
}


