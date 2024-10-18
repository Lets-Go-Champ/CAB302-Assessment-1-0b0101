package com.example.cab302assessment10b0101.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;
import java.util.List;

/**
 * The MockBookDAO class is a mock implementation of the BookDAOInterface.
 * It is used to imitate the behavior of BookDAO for unit testing in the BookDAOTest.
 * Instead of interacting with a real database, it uses in-memory storage (a List)
 * to simulate the insertion, retrieval, and update operations for Book objects.
 */
public class MockBookDAO implements BookDAOInterface {

    private List<Book> books = new ArrayList<>();
    private int currentId = 1;

    /**
     * Inserts a book into the mock data store.
     * Simulates an auto-incrementing ID by assigning the next available ID to the book.
     *
     * @param book The Book object to be inserted.
     */
    @Override
    public void insert(Book book) {
        book.setId(currentId++);  // Simulate auto-incrementing ID
        books.add(book);
    }

    /**
     * Retrieves all books from the mock data store.
     *
     * @return An ObservableList of all books in the mock data store.
     */
    @Override
    public ObservableList<Book> getAll() {
        return FXCollections.observableArrayList(books);  // Return an ObservableList of the books
    }

    /**
     * Retrieves all books that belong to a specific collection from the mock data store.
     * Filters the books by the given collectionId.
     *
     * @param collectionId The ID of the collection to filter books by.
     * @return An ObservableList of books that belong to the specified collection.
     */
    @Override
    public ObservableList<Book> getAllByCollection(int collectionId) {
        // Filter books by collectionId
        List<Book> filteredBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.getCollectionId() == collectionId) {
                filteredBooks.add(book);
            }
        }
        return FXCollections.observableArrayList(filteredBooks);
    }

    /**
     * Updates the details of an existing book in the mock data store.
     * Finds the book by its ID and replaces it with the updated Book object.
     *
     * @param updatedBook The Book object containing updated details.
     */
    @Override
    public void update(Book updatedBook) {
        // Find the book by its id and update its details
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            if (book.getId() == updatedBook.getId()) {
                books.set(i, updatedBook);  // Replace with the updated book
                break;
            }
        }
    }

    /**
     * Retrieves a book by its ID from the mock data store.
     * If the book is found, it is returned; otherwise, null is returned.
     *
     * @param bookId The ID of the book to retrieve.
     * @return The Book object with the specified ID, or null if not found.
     */
    @Override
    public Book getBookById(int bookId) {
        for (Book book : books) {
            if (book.getId() == bookId) {
                return book; // Return the book if found
            }
        }
        return null; // Return null if no book is found with the given ID
    }

    /**
     * Deletes a book by its title from the mock data store.
     * If the book is found, it is removed; otherwise, an IllegalArgumentException is thrown.
     *
     * @param title The title of the book to delete.
     * @throws IllegalArgumentException if the book with the specified title is not found.
     */
    @Override
    public void deleteBook(String title) {
        boolean removed = books.removeIf(book -> book.getTitle().equals(title));
        if (!removed) {
            throw new IllegalArgumentException("Book with title '" + title + "' not found.");
        }
    }
}
