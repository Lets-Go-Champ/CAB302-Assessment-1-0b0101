package com.example.cab302assessment10b0101.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

/**
 * The BookDAO class is responsible for interacting with the database to perform
 * CRUD (Create, Read, Update, Delete) operations for Book objects. It provides
 * methods for inserting, updating, and retrieving book records from the Books table.
 */
public class BookDAO {
    private static BookDAO instance;
    private static Connection connection;

    /**
     * Private constructor for singleton pattern to ensure only one instance of BookDAO is created.
     * It initializes the database connection using DatabaseConnector.
     */
    private BookDAO() {
        connection = DatabaseConnector.getInstance();
    }

    /**
     * Retrieves the singleton instance of BookDAO.
     * If an instance does not exist, it is created.
     *
     * @return The single instance of BookDAO.
     */

    public static synchronized BookDAO getInstance() {
        if (instance == null) {
            instance = new BookDAO();
        }
        return instance;
    }

    /**
     * Creates the Books table in the database if it doesn't already exist.
     * This table stores information about books such as their ID, title, author, etc.
     */
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
                            "readingStatus TEXT," +
                            "FOREIGN KEY (collectionId) REFERENCES Collections(collectionId)" +
                            ");"
            );
        } catch (SQLException ex) {
            System.err.println("Error creating table: " + ex.getMessage());
        }
    }

    /**
     * Inserts a new book record into the Books table.
     * The book details are stored as fields in the database.
     *
     * @param book The Book object containing details to be inserted into the table.
     */    public void insert(Book book) {
        try {
            PreparedStatement insertBook = connection.prepareStatement(
                    "INSERT INTO Books (collectionId, title, ISBN, author, description, publicationDate, publisher, pages, notes, image, readingStatus) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);"
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
            insertBook.setString(11, book.getReadingStatus());
            insertBook.execute();
        } catch (SQLException ex) {
            System.err.println("Error inserting book: " + ex.getMessage());
        }
    }

    /**
     * Updates the details of an existing book in the Books table.
     * The book's ID is used to identify which record to update.
     *
     * @param book The Book object containing updated details.
     */    public void update(Book book, String originalTitle) {
        try {
            PreparedStatement updateBook = connection.prepareStatement(
                    "UPDATE Books SET collectionId=?, title=?, isbn=?, author=?, description=?, publicationDate=?, publisher=?, pages=?, notes=?, image=? WHERE title=?"
            );
            updateBook.setInt(1, book.getCollectionId());
            updateBook.setString(2, book.getTitle());
            updateBook.setInt(3, book.getISBN());
            updateBook.setString(4, book.getAuthor());
            updateBook.setString(5, book.getDescription());
            updateBook.setString(6, book.getPublicationDate());
            updateBook.setString(7, book.getPublisher());
            updateBook.setInt(8, book.getPages());
            updateBook.setString(9, book.getNotes());
            updateBook.setBytes(10, book.getBytes());
            updateBook.setString(11, book.getReadingStatus());
            updateBook.setString(12, originalTitle);
            updateBook.execute();
        } catch (SQLException ex) {
            System.err.println("Error updating book: " + ex.getMessage());
        }
    }

    /**
     * Retrieves all book records from the Books table.
     * The results are returned as an ObservableList of Book objects.
     *
     * @return An ObservableList of all books in the database.
     */    public ObservableList<Book> getAll() {
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
                                rs.getBytes("image"),
                                rs.getString("readingStatus")
                        )
                );
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving books: " + e.getMessage());
        }
        return books;
    }

    /**
     * Retrieves all book records from a specific collection based on the provided collection ID.
     * The results are returned as an ObservableList of Book objects.
     *
     * @param collectionId The ID of the collection to filter books by.
     * @return An ObservableList of books belonging to the specified collection.
     */
    public ObservableList<Book> getAllByCollection(int collectionId) {
        ObservableList<Book> books = FXCollections.observableArrayList();

        String query = "SELECT * FROM Books WHERE collectionId = ?";
        try (PreparedStatement getAll = connection.prepareStatement(query)) {
            // Set the collectionId value
            getAll.setInt(1, collectionId);

            // Execute the query and process the result set
            ResultSet rs = getAll.executeQuery();

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
                        rs.getBytes("image"),
                        rs.getString("readingStatus")
                );
                books.add(book);
                bookCount++;
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving books: " + e.getMessage());
        }
        return books;
    }

    public void deleteBook(String isbn) throws SQLException {
        String sql = "DELETE FROM books WHERE isbn = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, isbn); // Set the ISBN to delete
            int rowsAffected = pstmt.executeUpdate(); // Execute the update query

            if (rowsAffected > 0) {
                System.out.println("Book with ISBN " + isbn + " deleted successfully.");
            } else {
                System.out.println("No book found with ISBN " + isbn + ".");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting book with ISBN " + isbn + ": " + e.getMessage());
            throw e; // Optionally rethrow the exception
        }
    }
}