package com.example.cab302assessment10b0101.model;

import javafx.collections.ObservableList;

public interface BookDAOInterface {
    void insert(Book book);
    ObservableList<Book> getAll();
    ObservableList<Book> getAllByCollection(int collectionId);
    void update(Book book);  // Adding the update method to the interface
}