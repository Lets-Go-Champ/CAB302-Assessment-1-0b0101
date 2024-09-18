package com.example.cab302assessment10b0101.model;

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
    public List<Book> getAll() {
        return new ArrayList<>(books);  // Return a copy of the list
    }
}
