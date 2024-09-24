package com.example.cab302assessment10b0101.controllers;

import com.example.cab302assessment10b0101.model.Book;
import com.example.cab302assessment10b0101.model.BookDAO;
import java.sql.SQLException;

public class DeleteBookController {

    private BookDAO bookDao;

    // Constructor
    public DeleteBookController() {
        // Use the singleton instance of BookDAO
        this.bookDao = BookDAO.getInstance();
    }

    public void deleteBook(Book book) {
        System.out.println("Attempting to delete book with ISBN: " + book.getISBNAsString());
        if (book == null) {
            throw new IllegalArgumentException("Cannot delete a null book.");
        }
        try {
            bookDao.deleteBook(book.getISBNAsString());
            System.out.println("Book deleted successfully.");
        } catch (SQLException e) {
            System.out.println("Failed to delete the book: " + e.getMessage());
        }
    }
}


