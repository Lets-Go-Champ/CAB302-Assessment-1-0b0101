package com.example.cab302assessment10b0101.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class BookDAO {

    private static BookDAO instance;
    private static Connection connection;

    /**
     * Private constructor to prevent direct instantiation.
     * Initializes the database connection.
     */
    private BookDAO() {
        connection = DatabaseConnector.getInstance();
    }

    /**
     * Singleton pattern to get the single instance of BookDAO.
     * Ensures that only one instance of this class is created throughout the application.
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
     * The table includes fields for book details and a foreign key reference to collections.
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
                            "FOREIGN KEY (collectionId) REFERENCES Collections(collectionId)" +
                            ");"
            );
        } catch (SQLException ex) {
            System.err.println("Error creating Books table: " + ex.getMessage());
        }
    }

    /**
     * Inserts a new book record into the Books table.
     *
     * @param book The book object containing all probably details to be inserted.
     */
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
            System.err.println("Error inserting book into database: " + ex.getMessage());
        }
    }

    /**
     * Updates the details of an existing book in the Books table.
     *
     * @param book The book object containing updated details to be stored.
     */
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
        } catch (SQLException ex) {
            System.err.println("Error updating book with ID " + book.getId() + ": " + ex.getMessage());
        }
    }

    /**
     * Retrieves all book records from the Books table.
     *
     * @return An ObservableList of Book objects representing all books in the database.
     */
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
            System.err.println("Error retrieving all books from the database: " + e.getMessage());
        }
        return books;
    }

    /**
     * Retrieves all books that belong to a specific collection based on the provided collection ID.
     *
     * @param collectionId The ID of the collection for which books are to be retrieved.
     * @return An ObservableList of Book objects that belong to the specified collection.
     */
    public ObservableList<Book> getAllByCollection(int collectionId) {
        ObservableList<Book> books = FXCollections.observableArrayList();

        String query = "SELECT * FROM Books WHERE collectionId = ?";
        try (PreparedStatement getAll = connection.prepareStatement(query)) {
            getAll.setInt(1, collectionId);

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
                        rs.getBytes("image")
                );
                books.add(book);
                bookCount++;
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving books for collection ID " + collectionId + ": " + e.getMessage());
        }
        return books;
    }
}