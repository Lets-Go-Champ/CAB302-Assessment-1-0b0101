package com.example.cab302assessment10b0101;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    private Connection getConnection() {
        return DatabaseConnector.getInstance();
    }

    // Create the Books table if it doesn't already exist
    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Books (" +
                "id INTEGER," +
                "title TEXT NOT NULL," +
                "author TEXT NOT NULL," +
                "description TEXT NOT NULL," +
                "publicationDate INTEGER," +
                "publisher TEXT NOT NULL," +
                "pages INTEGER," +
                "notes TEXT NOT NULL" +
                ");";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Error creating Books table: " + e.getMessage());
        }
    }

    // Insert a new book into the Books table
    public void insert(Book book) {
        String sql = "INSERT INTO Books (title, author, description, publicationDate, publisher, pages, notes) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?);";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setString(3, book.getDescription());
            pstmt.setInt(4, book.getPublicationDate());
            pstmt.setString(5, book.getPublisher());
            pstmt.setInt(6, book.getPages());
            pstmt.setString(7, book.getNotes());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error inserting book: " + e.getMessage());
        }
    }

    // Retrieve all books from the Books table
    public List<Book> getAll() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM Books;";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Book book = new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("description"),
                        rs.getInt("publicationDate"),
                        rs.getString("publisher"),
                        rs.getInt("pages"),
                        rs.getString("notes")
                );
                books.add(book);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving books: " + e.getMessage());
        }

        return books;
    }
}