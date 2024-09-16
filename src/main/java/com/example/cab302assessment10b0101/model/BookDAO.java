package com.example.cab302assessment10b0101.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
                            "publicationDate TEXT NOT NULL," +
                            "publisher TEXT NOT NULL," +
                            "pages INTEGER," +
                            "notes TEXT NOT NULL," +
                            "image BLOB" +
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
                    "INSERT INTO Books (id, title, author, description, publicationDate, publisher, pages, notes, image) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?);"
            );
            insertBook.setInt(1, book.getId());
            insertBook.setString(2, book.getTitle());
            insertBook.setString(3, book.getAuthor());
            insertBook.setString(4, book.getDescription());
            insertBook.setString(5, book.getPublicationDate());
            insertBook.setString(6, book.getPublisher());
            insertBook.setInt(7, book.getPages());
            insertBook.setString(8, book.getNotes());
            insertBook.setBytes(9, book.getImage());
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
                                rs.getString("publicationDate"),
                                rs.getString("publisher"),
                                rs.getInt("pages"),
                                rs.getString("notes"),
                                rs.getBytes("image")
                        )
                );
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving books: " + e.getMessage());
        }
        return books;
    }

    // Read an image file and convert it to a byte array
    public byte[] readImageFile(String imagePath) throws IOException {
        File file = new File(imagePath);
        FileInputStream fis = new FileInputStream(file);
        byte[] imageBytes = new byte[(int) file.length()];
        fis.read(imageBytes);
        fis.close();
        return imageBytes;
    }

    // Method to add a new book with an image to the database
    public void addBookWithImage(int id, String title, String author, String description, String publicationDate,
                                 String publisher, int pages, String notes, String imagePath) {
        try {
            // Convert the image file to a byte array
            byte[] imageBytes = readImageFile(imagePath);

            // Create a new Book object with the image
            Book book = new Book(id, title, author, description, publicationDate, publisher, pages, notes, imageBytes);

            // Insert the book into the database
            insert(book);

            System.out.println("Book with image added to the database.");

        } catch (IOException e) {
            System.err.println("Error reading image file: " + e.getMessage());
        }
    }

}