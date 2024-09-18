package com.example.cab302assessment10b0101.model;

import java.util.List;

public interface BookDAOInterface {
    void insert(Book book);
    List<Book> getAll();
}