package com.example.cab302assessment10b0101.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    private Connection connection;

    public BookDAO() {
        connection = DatabaseConnector.getInstance();
    }

    // Create the Books table if it doesn't already exist
    public void createTable() {
        try {
            Statement createTable = connection.createStatement();
            createTable.execute(
                    "CREATE TABLE IF NOT EXISTS Books (" +
                            "id INTEGER," +
                            "title TEXT NOT NULL," +
                            "author TEXT NOT NULL," +
                            "description TEXT NOT NULL," +
                            "publicationDate INTEGER," +
                            "publisher TEXT NOT NULL," +
                            "pages INTEGER," +
                            "notes TEXT NOT NULL" +
                            ");"
            );
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    // Insert a new book into the Books table
    public void insert(Book book) {
        try {
            PreparedStatement insertBook = connection.prepareStatement(
                    "INSERT INTO Books (id, title, author, description, publicationDate, publisher, pages, notes) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?);"
            );
            insertBook.setInt(1, book.getId());
            insertBook.setString(2, book.getTitle());
            insertBook.setString(3, book.getAuthor());
            insertBook.setString(4, book.getDescription());
            insertBook.setInt(5, book.getPublicationDate());
            insertBook.setString(6, book.getPublisher());
            insertBook.setInt(7, book.getPages());
            insertBook.setString(8, book.getNotes());
            insertBook.execute();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    // Retrieve all books from the Books table
    public List<Book> getAll() {
        List<Book> books = new ArrayList<>();
        try {
            Statement getAll = connection.createStatement();
            ResultSet rs = getAll.executeQuery("SELECT * FROM Books");
            while (rs.next()) {
                books.add(
                        new Book(
                                rs.getInt("id"),
                                rs.getString("title"),
                                rs.getString("author"),
                                rs.getString("description"),
                                rs.getInt("publicationDate"),
                                rs.getString("publisher"),
                                rs.getInt("pages"),
                                rs.getString("notes")
                        )
                );
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving books: " + e.getMessage());
        }
        return books;
    }
}