package com.example.cab302assessment10b0101.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
                    "INSERT INTO Books (collectionId, title, ISBN, author, description, publicationDate, publisher, pages, notes, image) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);"
            );
            insertBook.setInt(1, book.getCollectionId());
            insertBook.setString(2, book.getTitle());
            insertBook.setInt(3, book.getISBN());
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

    // Update the book details in a table
    public void update(Book book) {
        try {
            PreparedStatement updateBook = connection.prepareStatement(
                    "UPDATE Books SET collectionId=?, title=?, id=?, author=?, description=?, publicationDate=?, publisher=?, pages=?, notes=?, image=? WHERE id=?"
            );
            updateBook.setInt(1, book.getCollectionId());
            updateBook.setString(2, book.getTitle());
            updateBook.setInt(3, book.getId());
            updateBook.setString(4, book.getAuthor());
            updateBook.setString(5, book.getDescription());
            updateBook.setString(6, book.getPublicationDate());
            updateBook.setString(7, book.getPublisher());
            updateBook.setInt(8, book.getPages());
            updateBook.setString(9, book.getNotes());
            updateBook.setBytes(10, book.getBytes());
            updateBook.setInt(11, book.getId());
            updateBook.execute();
        } catch (SQLException ex) { System.err.println(ex); }
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

    public ObservableList<Book> getAllByCollection(int collectionId) {
        ObservableList<Book> books = FXCollections.observableArrayList();
        System.out.println("calling getAllByCollection for collection: " + collectionId);

        String query = "SELECT * FROM Books WHERE collectionId = ?";
        try (PreparedStatement getAll = connection.prepareStatement(query)) {
            // Set the collectionId value
            getAll.setInt(1, collectionId);
            System.out.println("PreparedStatement set with collectionId: " + collectionId);

            // Execute the query
            ResultSet rs = getAll.executeQuery();
            System.out.println("Query executed, processing results...");

            // Iterate over the result set and add books to the list
            int bookCount = 0;
            while (rs.next()) {
                Book book = new Book(
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
                );
                books.add(book);
                bookCount++;
                System.out.println("Added book to list: " + book.getTitle());
            }

            System.out.println("Books loaded successfully. Number of books: " + bookCount);

        } catch (SQLException e) {
            System.err.println("Error retrieving books: " + e.getMessage());
        }

        System.out.println("Final book list size: " + books.size());
        return books;
    }
}