package com.example.cab302assessment10b0101.model;

import javafx.collections.ObservableList;

/**
 * The BookDAOInterface defines the contract for the data access operations
 * related to Book objects. It is specifically used for the MockBookDAO, which
 * imitates the behavior of BookDAO during unit testing in BookDAOTest.
 */
public interface BookDAOInterface {
    /**
     * Inserts a new book into the mock data store.
     *
     * @param book The Book object to be inserted.
     */
    void insert(Book book);

    /**
     * Retrieves all books from the mock data store.
     *
     * @return An ObservableList of all books.
     */
    ObservableList<Book> getAll();

    /**
     * Retrieves all books associated with a particular collection from the mock data store.
     *
     * @param collectionId The ID of the collection to filter books by.
     * @return An ObservableList of books that belong to the specified collection.
     */
    ObservableList<Book> getAllByCollection(int collectionId);

    /**
     * Updates the details of an existing book in the mock data store.
     *
     * @param book The updated Book object.
     */
    void update(Book book);  // Adding the update method to the interface

    /**
     * Retrieves a book by its ID.
     *
     * @param bookId The ID of the book to retrieve.
     * @return The Book object with the specified ID, or null if not found.
     */
    Book getBookById(int bookId);

    /**
     * Deletes a book by its title.
     *
     * @param title The title of the book to delete.
     * @throws IllegalArgumentException if the book is not found.
     */
    void deleteBook(String title);
}
