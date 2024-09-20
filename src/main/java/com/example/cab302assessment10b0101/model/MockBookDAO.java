package com.example.cab302assessment10b0101.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;
import java.util.List;

public class MockBookDAO implements BookDAOInterface {

    private List<Book> books = new ArrayList<>();
    private int currentId = 1;

    @Override
    public void insert(Book book) {
        book.setId(currentId++);  // Simulate auto-incrementing ID
        books.add(book);
    }

    @Override
    public ObservableList<Book> getAll() {
        return FXCollections.observableArrayList(books);  // Return an ObservableList of the books
    }

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
}
