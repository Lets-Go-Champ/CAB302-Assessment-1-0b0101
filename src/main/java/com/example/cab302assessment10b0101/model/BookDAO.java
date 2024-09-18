package com.example.cab302assessment10b0101.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;


public class BookDAO {
    private static BookDAO instance;
    private static Connection connection;

    private BookDAO() {
        connection = DatabaseConnector.getInstance();
    }

    public static synchronized BookDAO getInstance() {
        if (instance == null) {
            instance = new BookDAO();
        }
        return instance;
    }

    // Create the Books table if it doesn't already exist
    public void createTable() {
        try {
            Statement createTable = connection.createStatement();
            createTable.execute(
                    "CREATE TABLE IF NOT EXISTS Books (" +
                            "bookId INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "collectionId INTEGER," +
                            "title TEXT NOT NULL," +
                            "isbn INTEGER," +
                            "author TEXT," +
                            "description TEXT," +
                            "publicationDate TEXT," +
                            "publisher TEXT," +
                            "pages INTEGER," +
                            "notes TEXT," +
                            "image BLOB," +
                            "FOREIGN KEY (collectionId) REFERENCES Collections(collectionId)" +
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
                    "INSERT INTO Books (collectionId, title, author, description, publicationDate, publisher, pages, notes, image) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);"
            );
            insertBook.setInt(1, book.getCollectionId());
            insertBook.setString(3, book.getTitle());
            insertBook.setString(4, book.getAuthor());
            insertBook.setString(5, book.getDescription());
            insertBook.setString(6, book.getPublicationDate());
            insertBook.setString(7, book.getPublisher());
            insertBook.setInt(8, book.getPages());
            insertBook.setString(9, book.getNotes());
            insertBook.setBytes(10, book.getBytes());
            insertBook.execute();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }


    // Retrieve all books from the Books table
    public ObservableList<Book> getAll() {
        ObservableList<Book> books = FXCollections.observableArrayList();
        try {
            Statement getAll = connection.createStatement();
            ResultSet rs = getAll.executeQuery("SELECT * FROM Books");
            while (rs.next()) {
                books.add(
                        new Book(
                                rs.getInt("collectionId"),
                                rs.getInt("bookId"),
                                rs.getString("title"),
                                rs.getInt("isbn"),
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

}
    /*
    // Insert a new book into the Books table
    public void insert(Book book) {
        try {
            PreparedStatement insertBook = connection.prepareStatement(
                    "INSERT INTO Books (collectionName, title, id, author, description, publicationDate, publisher, pages, notes, image) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);"
            );
            insertBook.setString(1, book.getCollectionName());
            insertBook.setString(2, book.getTitle());
            insertBook.setInt(3, book.getId());
            insertBook.setString(4, book.getAuthor());
            insertBook.setString(5, book.getDescription());
            insertBook.setString(6, book.getPublicationDate());
            insertBook.setString(7, book.getPublisher());
            insertBook.setInt(8, book.getPages());
            insertBook.setString(9, book.getNotes());
            insertBook.setBytes(10, book.getBytes());
            insertBook.execute();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    // Retrieve all books from the Books table
    public ObservableList<Book> getAll() {
        ObservableList<Book> books = FXCollections.observableArrayList();
        try {
            Statement getAll = connection.createStatement();
            ResultSet rs = getAll.executeQuery("SELECT * FROM Books");
            while (rs.next()) {
                books.add(
                        new Book(
                                rs.getString("collectionName"),
                                rs.getString("title"),
                                rs.getInt("id"),
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
    public void addBookWithImage(String collectionName, String title, int id, String author, String description, String publicationDate,
                                 String publisher, int pages, String notes, String imagePath) {
        try {
            // Convert the image file to a byte array
            byte[] imageBytes = readImageFile(imagePath);

            // Create a new Book object with the image
            Book book = new Book(collectionName, title, id, author, description, publicationDate, publisher, pages, notes, imageBytes);

            // Insert the book into the database
            insert(book);

            System.out.println("Book with image added to the database.");

        } catch (IOException e) {
            System.err.println("Error reading image file: " + e.getMessage());
        }
    }
*/
