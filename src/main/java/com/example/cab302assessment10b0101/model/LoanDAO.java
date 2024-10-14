package com.example.cab302assessment10b0101.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.time.LocalDate;

/**
 * The LoanDAO class provides data access methods for interacting
 * with the Loans table in the database.
 */
public class LoanDAO {

    // DAO for accessing loan data
    private static LoanDAO instance;

    // Database connection
    private static Connection connection;

    // DAO for accessing book data
    private BookDAO bookDAO;

    /**
     * Constructor to initialize the database connection and dependencies.
     */
    private LoanDAO() {
        connection = DatabaseConnector.getInstance();
        bookDAO = BookDAO.getInstance();
    }

    /**
     * Provides the singleton instance of LoanDAO. If the instance does not exist,
     * it creates a new one.
     *
     * @return The singleton instance of LoanDAO.
     */
    public static synchronized LoanDAO getInstance() {
        if (instance == null) {
            instance = new LoanDAO();
        }
        return instance;
    }

    /**
     * Creates the Loans table in the database if it does not already exist.
     * The table contains loan-related information, including user and book details.
     */
    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Loans (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "userId INTEGER NOT NULL," +
                "bookId INTEGER," +
                "borrowerName TEXT NOT NULL," +
                "borrowerContact TEXT," +
                "issueDate TEXT," +
                "FOREIGN KEY (bookId) REFERENCES Books(bookId) ON DELETE CASCADE" +
                ");";

        try (Statement createTable = connection.createStatement()) {
            createTable.execute(sql);
        } catch (SQLException ex) {
            System.err.println("Error creating Loans table: " + ex.getMessage());
        }
    }

    /**
     * Inserts a new loan into the Loans table.
     *
     * @param loan The Loan object to be inserted into the database.
     */
    public void insertLoan(Loan loan) {
        String sql = "INSERT INTO Loans (userId, bookId, borrowerName, borrowerContact, issueDate) " +
                "VALUES (?, ?, ?, ?, ?);";

        try (PreparedStatement insertLoan = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Set loan details in the prepared statement
            insertLoan.setInt(1, loan.getUserId());
            insertLoan.setInt(2, loan.getBook().getId());
            insertLoan.setString(3, loan.getBorrower());
            insertLoan.setString(4, loan.getBorrowerContact());
            insertLoan.setString(5, loan.getDateAsString());
            insertLoan.executeUpdate();

            // Retrieve the generated loan ID and set it in the Loan object
            try (ResultSet generatedKeys = insertLoan.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    loan.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error inserting loan: " + ex.getMessage());
        }
    }

    /**
     * Retrieves all loans associated with a specific user from the Loans table.
     *
     * @param userId The ID of the user whose loans are to be retrieved.
     * @return An ObservableList of Loan objects associated with the user.
     */
    public ObservableList<Loan> getAllLoansByUser(int userId) {
        ObservableList<Loan> loans = FXCollections.observableArrayList();

        String query = "SELECT * FROM Loans WHERE userId = ?";
        try (PreparedStatement getAll = connection.prepareStatement(query)) {
            getAll.setInt(1, userId);

            ResultSet rs = getAll.executeQuery();

            // Loop through the result set and create Loan objects
            while (rs.next()) {
                Book book = bookDAO.getBookById(rs.getInt("bookId"));
                Loan loan = new Loan(
                        rs.getInt("userId"),
                        rs.getString("borrowerName"),
                        rs.getString("borrowerContact"),
                        book,
                        LocalDate.parse(rs.getString("issueDate"))
                );
                loans.add(loan);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving all loans: " + e.getMessage());
        }
        return loans;
    }

    /**
     * Deletes a loan record from the Loans table.
     *
     * @param loan The Loan object to be deleted.
     * @throws SQLException If an error occurs during the deletion process.
     */
    public void deleteLoan(Loan loan) {
        String sql = "DELETE FROM Loans WHERE userId = ? AND bookId = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            // Set user ID and book ID in the prepared statement
            pstmt.setInt(1, loan.getUserId());
            pstmt.setInt(2, loan.getBook().getId());

            // Execute the delete statement
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting Loan: " + e.getMessage());
        }
    }
}